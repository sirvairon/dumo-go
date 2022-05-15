/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import dumogo.Llibre;
import dumogo.Usuari;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class LlibreVistaController implements Initializable {

    private Stage stage;
    private Llibre llibre;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    
    @FXML
    private VBox raiz;    
    @FXML
    private Label labelTitol; 
    @FXML
    private Label labelAutor; 
    @FXML
    private Label labelAnyPublicacio;    
    @FXML
    private Label labelTipus;    
    @FXML
    private Label labelValoracio;       
    @FXML
    private Label labelVots;        
    @FXML
    private Label labelDescripcio;   
    @FXML
    private TextField textFieldAdminAlta;       
    @FXML
    private TextField textFieldReservatDNI;
    @FXML
    private DatePicker datePickerDataAlta;               
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio;    
    @FXML
    private ImageView imageViewCaratula;
    
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
        labelTitol.setText(llibre.getNom());        
        labelAutor.setText(llibre.getAutor());
        labelAnyPublicacio.setText(llibre.getAny_Publicacio());
        labelTipus.setText(llibre.getTipus());
        labelValoracio.setText(llibre.getValoracio());
        labelVots.setText(llibre.getVots());
        labelDescripcio.setText(llibre.getDescripcio());
        textFieldAdminAlta.setText(llibre.getAdmin_alta());
        datePickerDataAlta.setValue(LocalDate.parse(llibre.getData_alta()));
        textFieldReservatDNI.setText(llibre.getReservat_DNI());
    }
    
    private void esborrarDades(){
        // Esborrem el llibre de memoria
        this.llibre = null;
        // Esborrem els camps de pantalla
        labelTitol.setText("");        
        labelAutor.setText("");
        labelAnyPublicacio.setText("");
        labelTipus.setText("");
        labelValoracio.setText("");
        labelVots.setText("");
        labelDescripcio.setText("");
        textFieldAdminAlta.setText("");
        datePickerDataAlta.setValue(LocalDate.now());
        textFieldReservatDNI.setText("");
    }
    
    public void mostrarLlibre(Llibre ll){
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(ll);
        // Com es desde l'administrador s'han de poder editar tots els camps
        //textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (modificar)
        butoAccio.setVisible(false);
    }
    
    /*
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
                        // Fem l'accio d'afegir usuari
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
                        // Usuari afegit correctament
                        }else if(codi_resposta.equals("1400")){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.showAndWait();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        // Error a l'afegir l'usuari
                        }else{
                            llibre = null;
                            alerta.setAlertType(Alert.AlertType.ERROR);
                            alerta.show();
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
    }
        */
    /*
    public void modificarLlibre(Llibre ll){
        / PER MODIFICAR PERFIL DESDE L'ADMINISTRADOR /
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
                // Obtenim les dades del usuari amb les dades de la pantalla
                llibre = obtenirLlibrePantalla();
                try {
                    // Creem el HashMap on rebrem el codi de resposta
                    HashMap<String, String> msg_in;
                    // Fem l'accio de modificar usuari
                    msg_in = AccionsClient.modificarLlibre(llibre);
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
                    Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
*/
    /*
    private Llibre obtenirLlibrePantalla(){
        // Comprobem si alguns camps estan buits
        String data;
        try{
            data = datePickerDataPublicacio.getValue().toString();
        }catch (NullPointerException ex) {
            data = "null";
        }
        System.out.println("data: " + data);
        // Creem un usuari obtenint les dades de la pantalla
        Llibre ll = new Llibre (
            new SimpleStringProperty(textFieldTitol.getText()),
            new SimpleStringProperty(textFieldAutor.getText()),
            new SimpleStringProperty(datePickerDataPublicacio.getValue().toString()),
            new SimpleStringProperty(data),//choiceBoxTipus
            new SimpleStringProperty(datePickerDataAlta.getValue().toString()),
            new SimpleStringProperty(textFieldReservatDNI.getText()),
            new SimpleStringProperty(""),//imageViewCaratula
            new SimpleStringProperty(textFieldAdminAlta.getText()),
            new SimpleStringProperty(textAreaDescripcio.getText()),
            new SimpleStringProperty(textFieldValoracio.getText()),            
            new SimpleStringProperty(textFieldVots.getText())
        );
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
        Image icon = new Image("/resources/dumogo_icon_neg_35.png");
        stage1.getIcons().add(icon);
        stage1.setTitle("Dumo-Go");
        stage1.setResizable(false);
        stage1.show();    
*/
    }   