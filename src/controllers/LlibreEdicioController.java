/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import dumogo.Llibre;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class LlibreEdicioController implements Initializable {

    private Stage stage;
    private Llibre llibre;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    
    @FXML
    private VBox raiz;    
    @FXML
    private TextField textFieldTitol; 
    @FXML
    private TextField textFieldAutor;        
    @FXML
    private TextField textFieldValoracio;       
    @FXML
    private TextField textFieldVots;        
    @FXML
    private TextArea textAreaDescripcio;   
    @FXML
    private TextField textFieldAdminAlta;  
    @FXML
    private TextField textFieldReservatDNI;
    @FXML
    private TextField textFieldAnyPublicacio;
    @FXML
    private TextField textFieldTipus;
    @FXML
    private DatePicker datePickerDataAlta;
    @FXML
    private ImageView imageViewCaratula;
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio;    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
    }    
    
    private void omplirDades(Llibre llibre){
        // Agafem el llibre i el guardem
        this.llibre = llibre;
        // Omplim els camps de pantalla amb l'usuari
        textFieldTitol.setText(llibre.getNom());        
        textFieldAutor.setText(llibre.getAutor());
        textFieldAnyPublicacio.setText(llibre.getAny_Publicacio());
        textFieldTipus.setText(llibre.getTipus());
        textFieldValoracio.setText(llibre.getValoracio());
        textFieldVots.setText(llibre.getVots());
        textAreaDescripcio.setText(llibre.getDescripcio());
        textFieldAdminAlta.setText(llibre.getAdmin_alta());
        datePickerDataAlta.setValue(LocalDate.parse(llibre.getData_alta()));
        textFieldReservatDNI.setText(llibre.getReservat_DNI());
    }
    
    private void esborrarDades(){
        // Esborrem el llibre de memoria
        this.llibre = null;
        // Esborrem els camps de pantalla
        textFieldTitol.setText("");        
        textFieldAutor.setText("");
        textFieldAnyPublicacio.setText("");
        textFieldTipus.setText("");
        textFieldValoracio.setText("");
        textFieldVots.setText("");
        textAreaDescripcio.setText("");
        textFieldAdminAlta.setText("");
        datePickerDataAlta.setValue(LocalDate.now());
        textFieldReservatDNI.setText("");
    }
    
    public void afegirLlibre(){
        // Esborrem dades en cas de que hi hagi
        esborrarDades();
        // Establim per defecte la data d'avui
        datePickerDataAlta.setValue(LocalDate.now());
        // Establim l'administrador que esta afegint l'usuari
        textFieldAdminAlta.setText(AccionsClient.getNom_user_actual());
        // Com es un alta nova desde l'administrador s'han de poder editar tots els camps
        //textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (afegir)
        butoAccio.setText("Afegir");
        // Configurem el EventHandler en cas de fer click al boto d'afegir
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                    llibre = obtenirLlibrePantalla();
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in;
                        // Fem l'accio d'afegir el llibre
                        msg_in = AccionsClient.afegirLlibre(llibre);
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        // Obtenim el sinificat del codi de resposta
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                        alerta.setTitle("Afegir llibre");
                        alerta.setHeaderText(significat_codi_resposta);
                        // Sessio caducada
                        if(codi_resposta.equals("10")){
                            sessioCaducada();
                        // Llibre afegit correctament
                        }else if(codi_resposta.equals("1400")){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.showAndWait();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        // Error a l'afegir el llibre
                        }else{
                            llibre = null;
                            alerta.setAlertType(Alert.AlertType.ERROR);
                            alerta.show();
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
    }
    
    public void modificarLlibre(Llibre ll){
        /** PER MODIFICAR PERFIL DESDE L'ADMINISTRADOR **/
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(ll);
        // Com es desde l'administrador s'han de poder editar tots els camps
        //textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (modificar)
        butoAccio.setText("Modificar");
        // Configurem el EventHandler en cas de fer click al boto de modificar
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                // Abans d'obtenir les dades del llibre guardem el seu nom ja que s'erveix d'identificador
                String titol_antic = llibre.getNom();
                // Obtenim les dades del llibre amb les dades de la pantalla
                llibre = obtenirLlibrePantalla();
                try {
                    // Creem el HashMap on rebrem el codi de resposta
                    HashMap<String, String> msg_in;
                    // Fem l'accio de modificar usuari
                    msg_in = AccionsClient.modificarLlibre(llibre, titol_antic);
                    // Obtenim el codi de resposta
                    codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                    // Obtenim el sinificat del codi de resposta
                    significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                    // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                    alerta.setTitle("Modificar llibre");
                    alerta.setHeaderText(significat_codi_resposta);
                    // Sessio caducada
                    if(codi_resposta.equals("10")){
                        sessioCaducada();
                    // Usuari modificat correctament
                    }else if(codi_resposta.equals("1800")){
                        alerta.setAlertType(Alert.AlertType.INFORMATION);
                        alerta.showAndWait();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    // Error al modificar l'usuari
                    }else{
                        alerta.setAlertType(Alert.AlertType.ERROR);
                        alerta.show();
                    }


                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    private Llibre obtenirLlibrePantalla(){
        // Creem un llibre obtenint les dades de la pantalla
        Llibre ll = new Llibre (
            new SimpleStringProperty(textFieldTitol.getText()),
            new SimpleStringProperty(textFieldAutor.getText()),
            new SimpleStringProperty(textFieldAnyPublicacio.getText()),
            new SimpleStringProperty(textFieldTipus.getText()),
            new SimpleStringProperty(datePickerDataAlta.getValue().toString()),
            new SimpleStringProperty(textFieldReservatDNI.getText()),
            new SimpleStringProperty(textFieldAdminAlta.getText()),
            new SimpleStringProperty(""),//imageViewCaratula            
            new SimpleStringProperty(textAreaDescripcio.getText()),
            new SimpleStringProperty(textFieldValoracio.getText()),            
            new SimpleStringProperty(textFieldVots.getText())
        );
        System.out.println();
        // Tornem el llibre creat
        return ll;
    }
    
    public void sessioCaducada() throws IOException {
        // En cas de retornar codi 10 (sessio caducada)
        // Obtenim el text de l'error
        //significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
        significat_codi_resposta = "Sessió caducada";
        // Establim alerta
        alerta.setTitle("Sessió caducada");
        alerta.setContentText("");
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.setHeaderText(significat_codi_resposta);
        // La mostrem i esperem click
        alerta.showAndWait();
        // Tanquem finestra
        //raiz.getScene().getWindow().hide();
        //Stage stage1 = stage.getScene().getWindow();
        //stage1.close();
        stage.getScene().getWindow();
        // Obrim finestra de login
        Parent parent = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(parent);
        stage1.setScene(scene);
        Image icon = new Image("/resources/icon.png");
        stage1.getIcons().add(icon);
        stage1.setTitle("Dumo-Go");
        stage1.setResizable(false);
        stage1.show();    
    }   

}
