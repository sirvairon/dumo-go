/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import dumogo.Usuari;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class UsuariEdicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
     
    private Stage stage;
    private Usuari usuari;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    private Alert alerta;
    private final ObservableList<String> olGenere = FXCollections.observableArrayList("Masculí","Femení","Altre");
    private final ObservableList<String> olTipusSoci = FXCollections.observableArrayList("Premium","Standard");
    
    @FXML
    private AnchorPane raiz;    
    @FXML
    private TextField textFieldNomUsuari;    
    @FXML
    private TextField textFieldNumSoci;    
    @FXML
    private TextField textFieldDNI;    
    @FXML
    private TextField textFieldNom;    
    @FXML
    private TextField textFieldCognom1;    
    @FXML
    private TextField textFieldCognom2;    
    @FXML
    private TextField textFieldTelefon1;    
    @FXML
    private TextField textFieldTelefon2;    
    @FXML
    private TextField textFieldDireccio;    
    @FXML
    private TextField textFieldPoblacio;    
    @FXML
    private TextField textFieldProvincia;    
    @FXML
    private TextField textFieldCodiPostal;    
    @FXML
    private TextField textFieldPais;    
    @FXML
    private TextField textFieldCorreu;    
    @FXML
    private ChoiceBox choiceBoxGenere;    
    @FXML
    private DatePicker datePickerDataAlta;    
    @FXML
    private ChoiceBox choiceBoxTipusSoci;    
    @FXML
    private TextField textFieldDataNaixement;            
    @FXML
    private TextArea textAreaObservacions;    
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio;
    @FXML
    private Label labelResposta;
    @FXML
    private TextField textFieldAdminAlta;
    @FXML
    private PasswordField passwordFieldPassword;    
/*            
    public void recuperarDades(){
        stage = (Stage) this.raiz.getScene().getWindow();
        usuari = (Usuari) stage.getUserData();
        omplirDades(usuari);               
    }
*/
    
    private void omplirDades(Usuari usuari){
        // Agafem l'usuari i el guardem
        this.usuari = usuari;
        // Omplim els camps de pantalla amb l'usuari
        textFieldNomUsuari.setText(usuari.getNom_user());
        textFieldNumSoci.setText(usuari.getNum_soci());
        textFieldDNI.setText(usuari.getDni());
        textFieldNom.setText(usuari.getNom());
        textFieldCognom1.setText(usuari.getCognom1());
        textFieldCognom2.setText(usuari.getCognom2());
        textFieldTelefon1.setText(usuari.getTelefon1());
        textFieldTelefon2.setText(usuari.getTelefon2());
        textFieldDireccio.setText(usuari.getDireccio());
        textFieldPoblacio.setText(usuari.getPoblacio());
        textFieldProvincia.setText(usuari.getProvincia());
        textFieldCodiPostal.setText(usuari.getCodi_postal());
        textFieldPais.setText(usuari.getPais());
        textFieldCorreu.setText(usuari.getCorreu());
        choiceBoxGenere.setValue(usuari.getGenere());
        datePickerDataAlta.setValue(LocalDate.parse(usuari.getData_Alta()));
        choiceBoxTipusSoci.setValue(usuari.getTipus_Soci());
        textFieldDataNaixement.setText(usuari.getData_naixement());
        textAreaObservacions.setText(usuari.getObservacions());    
        textFieldAdminAlta.setText(usuari.getAdmin_Alta()); 
        passwordFieldPassword.setText(usuari.getPassword());
    }
    
    private void esborrarDades(){
        // Esborrem l'usuari de memoria
        usuari = null;
        // Esborrem els camps de pantalla
        textFieldNomUsuari.setText("");
        textFieldNumSoci.setText("");
        textFieldDNI.setText("");
        textFieldNom.setText("");
        textFieldCognom1.setText("");
        textFieldCognom2.setText("");
        textFieldTelefon1.setText("");
        textFieldTelefon2.setText("");
        textFieldDireccio.setText("");
        textFieldPoblacio.setText("");
        textFieldProvincia.setText("");
        textFieldCodiPostal.setText("");
        textFieldPais.setText("");
        textFieldCorreu.setText("");
        choiceBoxGenere.setValue("");
        datePickerDataAlta.setValue(LocalDate.now());
        choiceBoxTipusSoci.setValue("");
        textFieldDataNaixement.setText("");
        textAreaObservacions.setText("");    
        textFieldAdminAlta.setText(""); 
        passwordFieldPassword.setText("");
    }
        
    public void afegirUsuari(){
        // Esborrem dades en cas de que hi hagi
        esborrarDades();
        // Establim per defecte la data d'avui
        datePickerDataAlta.setValue(LocalDate.now());
        // Establim l'administrador que esta afegint l'usuari
        textFieldAdminAlta.setText(AccionsClient.getNom_user_actual());
        // Si es un administrador qui modifica el perfi, pot modificar el DNI
        textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (afegir)
        butoAccio.setText("Afegir");
        // Configurem el EventHandler en cas de fer click al boto d'afegir
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                    usuari = obtenirUsuariPantalla();
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in;
                        // Fem l'accio d'afegir usuari
                        msg_in = AccionsClient.agefirUsuari(usuari);
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        codi_resposta = "10";
                        // Obtenim el sinificat del codi de resposta
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                        alerta.setTitle("Afegir usuari");
                        alerta.setHeaderText(significat_codi_resposta);
                        // Sessio caducada
                        if(codi_resposta.equals("10")){
                            sessioCaducada();
                        // Usuari afegit correctament
                        }else if(codi_resposta.equals("1000")){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.showAndWait();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        // Error a l'afegir l'usuari
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
    
    public void modificarUsuari(Usuari u){
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(u);
        // Establim al buto de l'accio, l'accio que volem fer (modificar)
        butoAccio.setText("Modificar");
        // Configurem el EventHandler en cas de fer click al boto de modificar
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                // Obtenim les dades del usuari amb les dades de la pantalla
                usuari = obtenirUsuariPantalla();
                try {
                    // Creem el HashMap on rebrem el codi de resposta
                    HashMap<String, String> msg_in;
                    // Fem l'accio de modificar usuari
                    msg_in = AccionsClient.modificarUsuari(usuari);
                    // Obtenim el codi de resposta
                    codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                    // Obtenim el sinificat del codi de resposta
                    significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                    // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                    alerta.setTitle("Modificar usuari");
                    alerta.setHeaderText(significat_codi_resposta);
                    // Sessio caducada
                    if(codi_resposta.equals("10")){
                        sessioCaducada();
                    // Usuari modificat correctament
                    }else if(codi_resposta.equals("5000")){
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
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    private Usuari obtenirUsuariPantalla(){
        // Creem un usuari obtenint les dades de la pantalla
        Usuari u = new Usuari(
            new SimpleStringProperty(textFieldNomUsuari.getText()),
            new SimpleStringProperty(passwordFieldPassword.getText()),
            new SimpleStringProperty(textFieldDNI.getText()),
            new SimpleStringProperty(textFieldDataNaixement.getText()),
            new SimpleStringProperty(textFieldNumSoci.getText()),
            new SimpleStringProperty(choiceBoxTipusSoci.getValue().toString()),
            new SimpleStringProperty(datePickerDataAlta.getValue().toString()),
            new SimpleStringProperty(textFieldNom.getText()),
            new SimpleStringProperty(textFieldCognom1.getText()),
            new SimpleStringProperty(textFieldCognom2.getText()),
            new SimpleStringProperty(choiceBoxGenere.getValue().toString()),
            new SimpleStringProperty(textFieldDireccio.getText()),
            new SimpleStringProperty(textFieldCodiPostal.getText()),
            new SimpleStringProperty(textFieldPoblacio.getText()),
            new SimpleStringProperty(textFieldProvincia.getText()),
            new SimpleStringProperty(textFieldPais.getText()),
            new SimpleStringProperty(textFieldTelefon1.getText()),
            new SimpleStringProperty(textFieldTelefon2.getText()),
            new SimpleStringProperty(textFieldCorreu.getText()),
            new SimpleStringProperty(textFieldCorreu.getText()),
            new SimpleStringProperty(textAreaObservacions.getText()),
            new SimpleStringProperty(textFieldAdminAlta.getText())            
        );
        // Tornem l'usuari creat
        return u;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Establim el contigut del llistat dels butons per escollir (genere, tipus de soci,...)
        choiceBoxGenere.setItems(olGenere);
        choiceBoxTipusSoci.setItems(olTipusSoci);
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
    } 
    
    private void sessioCaducada() throws IOException {
        // En cas de retornar codi 10 (sessio caducada)
        // Obtenim el text de l'error
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        // Establim alerta
        alerta.setTitle("Sessió caducada");
        alerta.setContentText("");
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.setHeaderText(significat_codi_resposta);
        // La mostrem i esperem click
        alerta.showAndWait();
        // Tanquem finestra
        //raiz.getScene().getWindow().hide();
        Stage stage1 = (Stage) raiz.getScene().getWindow();
        stage1.close();
        // Obrim finestra de login
        Parent parent = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        Image icon = new Image("/resources/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Dumo-Go");
        stage.setResizable(false);
        stage.show();    
    }   
    
}