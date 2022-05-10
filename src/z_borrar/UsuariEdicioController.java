/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package z_borrar;

import controllers.*;
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
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox vBoxAdminAlta;
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
        textFieldNomUsuari.setText(usuari.getUser_name());
        
        textFieldDNI.setText(usuari.getDni());
        textFieldNom.setText(usuari.getNom());
        textFieldCognom1.setText(usuari.getCognoms());
        textFieldTelefon1.setText(usuari.getTelefon());
        textFieldDireccio.setText(usuari.getDireccio());
        textFieldPais.setText(usuari.getPais());
        textFieldCorreu.setText(usuari.getCorreu());
        datePickerDataAlta.setValue(LocalDate.parse(usuari.getData_Alta()));
        textFieldDataNaixement.setText(usuari.getData_naixement());
        textFieldAdminAlta.setText(usuari.getAdmin_Alta()); 
        passwordFieldPassword.setText(usuari.getPassword());
        //textFieldNumSoci.setText(usuari.getNum_soci());
        //textFieldCognom2.setText(usuari.getCognom2());
        //textFieldTelefon2.setText(usuari.getTelefon2());
        //textFieldPoblacio.setText(usuari.getPoblacio());
        //textFieldProvincia.setText(usuari.getProvincia());
        //textFieldCodiPostal.setText(usuari.getCodi_postal());
        //choiceBoxGenere.setValue(usuari.getGenere());
        //choiceBoxTipusSoci.setValue(usuari.getTipus_Soci());
        //textAreaObservacions.setText(usuari.getObservacions());
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
        // Com es un alta nova desde l'administrador s'han de poder editar tots els camps
        textFieldDNI.setDisable(false);
        textFieldNumSoci.setDisable(false);
        choiceBoxTipusSoci.setDisable(false);
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
                        msg_in = AccionsClient.afegirUsuari(usuari);
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
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                    Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        });
    }
    
    public void modificarUsuari(Usuari u){
        /** PER MODIFICAR PERFIL DESDE L'ADMINISTRADOR **/
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(u);
        // Com es desde l'administrador s'han de poder editar tots els camps
        textFieldDNI.setDisable(false);
        textFieldNumSoci.setDisable(false);
        choiceBoxTipusSoci.setDisable(false);
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
                    Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
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
            //new SimpleStringProperty(choiceBoxTipusSoci.getValue().toString()),
            new SimpleStringProperty(datePickerDataAlta.getValue().toString()),
            new SimpleStringProperty(textFieldNom.getText()),
            new SimpleStringProperty(textFieldCognom1.getText()),
            //new SimpleStringProperty(textFieldCognom2.getText()),
            //new SimpleStringProperty(choiceBoxGenere.getValue().toString()),
            new SimpleStringProperty(textFieldDireccio.getText()),
            //new SimpleStringProperty(textFieldCodiPostal.getText()),
            //new SimpleStringProperty(textFieldPoblacio.getText()),
            //new SimpleStringProperty(textFieldProvincia.getText()),
            new SimpleStringProperty(textFieldPais.getText()),
            new SimpleStringProperty(textFieldTelefon1.getText()),
            //new SimpleStringProperty(textFieldTelefon2.getText()),
            new SimpleStringProperty(textFieldCorreu.getText()),
            //new SimpleStringProperty(textFieldCorreu.getText()),
            //new SimpleStringProperty(textAreaObservacions.getText()),
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
    
    public void mostrarPerfil(){
        try {
            /** PER MODIFICAR PERFIL DESDE L'USUARI**/
            // Creem el HashMap on rebrem el codi de resposta
            HashMap<String, String> msg_in;
            // Fem l'accio de modificar usuari
            msg_in = AccionsClient.obtenirUsuari();
            // Obtenim el codi de resposta
            codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
            // Obtenim el sinificat del codi de resposta
            significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
            if(!codi_resposta.equals("5000")){
                // Configurem l'alerta que ens confirmara que hi ha hagut error a l'obtenir les dades de perfil
                alerta.setTitle("Mostrar perfil");
                alerta.setHeaderText(significat_codi_resposta);  
                alerta.setAlertType(Alert.AlertType.ERROR);
                // La mostrem i esperem click
                alerta.showAndWait();
                // Tanquem finestra
                butoAccio.getScene().getWindow().hide();
            }else{
                msg_in.remove(STRING_CODI_RESPOSTA);
                //HashMap<String, SimpleStringProperty> msg_in2 = (HashMap)msg_in;
                Usuari u = new Usuari((HashMap)msg_in);
                // Omplim les dades per les dades obtingudes de l'usuari
                omplirDades(u);
                // Amagem camps que no ha de veure l'usuari
                vBoxAdminAlta.setVisible(false);
            }
            
            
            
            
            
            
            /*
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
                        msg_in = AccionsClient.mostraUsuari();
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
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void modificarPassword(ActionEvent event) throws IOException, ClassNotFoundException {
        //((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Password.fxml"));
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