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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        this.usuari = usuari;
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
        usuari = null;
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
        datePickerDataAlta.setValue(LocalDate.now());
        textFieldAdminAlta.setText(AccionsClient.getNom_user_actual());
        //butoModificar = null;
        textFieldDNI.setDisable(false);

        butoAccio.setText("Afegir");
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                    usuari = obtenirUsuariPantalla();
                    try {
                        // Creem HashMap<String, String> msg_in;el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in;

                        msg_in = AccionsClient.agefirUsuari(usuari);
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        System.out.println("Codi de resposta:" + codi_resposta + " - " + significat_codi_resposta);
                        alerta.setTitle("Afegir usuari");
                        alerta.setHeaderText(significat_codi_resposta);
                        if(codi_resposta.equals("1000")){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.showAndWait();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        }else{
                            alerta.setAlertType(Alert.AlertType.ERROR);
                            alerta.show();
                        }

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
    }
    
    public void modificarUsuari(Usuari u){
        // Omplim les dades per les obtingudes
        omplirDades(u);
        butoAccio.setText("Modificar");
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                usuari = obtenirUsuariPantalla();
                try {
                    // Creem HashMap<String, String> msg_in;el HashMap on rebrem el codi de resposta
                    HashMap<String, String> msg_in;

                    msg_in = AccionsClient.modificarUsuari(usuari);
                    codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                    significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                    System.out.println("Codi de resposta:" + codi_resposta + " - " + significat_codi_resposta);
                    alerta.setTitle("Modificar usuari");
                    alerta.setHeaderText(significat_codi_resposta);
                    if(codi_resposta.equals("5000")){
                        alerta.setAlertType(Alert.AlertType.INFORMATION);
                        alerta.showAndWait();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    }else{
                        alerta.setAlertType(Alert.AlertType.ERROR);
                        alerta.show();
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    private Usuari obtenirUsuariPantalla(){
        
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

        return u;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Establim el contigut del llistat dels butons per escollir (genere, tipus de soci,...)
        choiceBoxGenere.setItems(olGenere);
        choiceBoxTipusSoci.setItems(olTipusSoci);
        //choiceBoxGenere.setValue("Masculí");
        alerta = new Alert(Alert.AlertType.NONE);
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
    } 
        
}