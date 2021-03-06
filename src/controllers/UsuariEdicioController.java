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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    private String user_name_actual;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    
    @FXML
    private VBox raiz;    
    @FXML
    private TextField textFieldUserName; 
    @FXML
    private TextField textFieldNumSoci; 
    @FXML
    private TextField textFieldDNI;    
    @FXML
    private TextField textFieldNom;    
    @FXML
    private TextField textFieldCognoms;       
    @FXML
    private TextField textFieldTelefon;        
    @FXML
    private TextField textFieldDireccio;   
    @FXML
    private TextField textFieldPais;    
    @FXML
    private TextField textFieldCorreu;       
    @FXML
    private DatePicker datePickerDataAlta;       
    @FXML
    private DatePicker datePickerDataNaixement;               
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
    @FXML
    private HBox hBoxAdministrador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
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
        // Establim al buto de l'accio, l'accio que volem fer (afegir)
        butoAccio.setText("Afegir");
        // Configurem el EventHandler en cas de fer click al boto d'afegir
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                    // Obtenim l'usuari de pantalla
                    usuari = obtenirUsuariPantalla();
                    // Establim que el user_name_antic i el user_name siguien el mateix
                    usuari.setUser_name_antic(usuari.getUser_name());
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in;
                        // Fem l'accio d'afegir usuari
                        msg_in = AccionsClient.afegirElement(usuari);
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
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
                            usuari = null;
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
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(u);
        // Com es desde l'administrador s'han de poder editar tots els camps
        textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (modificar)
        butoAccio.setText("Modificar");
        // Configurem el EventHandler en cas de fer click al boto de modificar
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                // Obtenim les dades de l'administrador amb les dades de la pantalla
                usuari = obtenirUsuariPantalla();
                if(usuari!=null){
                    // Omplim el camp "nom_admin_antic" del Usuari amb el nom anterior
                    usuari.setUser_name_antic(user_name_actual);
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in = null;
                        msg_in = AccionsClient.modificarElement(usuari);
                        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                        alerta.setTitle("Modificar usuari");
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        // Obtenim el sinificat del codi de resposta
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        alerta.setHeaderText(significat_codi_resposta);
                        switch (codi_resposta) {
                            case "10": // Sessio caducada
                                sessioCaducada();
                                // Usuari modificat correctament
                                break;
                            case "1300": // Correcte
                                alerta.setAlertType(Alert.AlertType.INFORMATION);
                                alerta.showAndWait();
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                                // Error al modificar l'usuari
                                break;
                            default: // Error
                                alerta.setAlertType(Alert.AlertType.ERROR);
                                alerta.show();
                                break;
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private Usuari obtenirUsuariPantalla(){
        // Comprobem si alguns camps estan buits
        String data;
        if(textFieldUserName.getText().equals("") || textFieldUserName.getText() == null){
            // Configurem l'alerta que ens confirmara que hi ha hagut error a l'obtenir les dades de l'usuari a pantalla
            alerta.setTitle("Nom d'usuari");
            alerta.setHeaderText("Es obligatori introduir un nom d'usuari");  
            alerta.setAlertType(Alert.AlertType.ERROR);
            // La mostrem i esperem click
            alerta.show();
            return null;
        }else if(datePickerDataNaixement.getValue() == null){
            // Configurem l'alerta que ens confirmara que hi ha hagut error a l'obtenir les dades de l'usuari a pantalla
            alerta.setTitle("Data de naixement");
            alerta.setHeaderText("Es obligatori introduir la data de naixement");  
            alerta.setAlertType(Alert.AlertType.ERROR);
            // La mostrem i esperem click
            alerta.show();
            return null;
        }else{
            Usuari u = new Usuari(
                new SimpleStringProperty(textFieldUserName.getText()),
                new SimpleStringProperty(passwordFieldPassword.getText()),
                new SimpleStringProperty(textFieldDNI.getText()),
                new SimpleStringProperty(datePickerDataNaixement.getValue().toString()),
                new SimpleStringProperty(textFieldNumSoci.getText()),            
                new SimpleStringProperty(datePickerDataAlta.getValue().toString()),
                new SimpleStringProperty(textFieldNom.getText()),
                new SimpleStringProperty(textFieldCognoms.getText()),
                new SimpleStringProperty(textFieldDireccio.getText()),
                new SimpleStringProperty(textFieldPais.getText()),
                new SimpleStringProperty(textFieldTelefon.getText()),
                new SimpleStringProperty(textFieldCorreu.getText()),
                new SimpleStringProperty(textFieldAdminAlta.getText())
            );
            u.setUser_name_antic("");
            // Tornem l'usuari creat
            return u;
        }
    }
    
    private void omplirDades(Usuari usuari){
        // Agafem l'usuari i el guardem
        this.usuari = usuari;
        // Omplim els camps de pantalla amb l'usuari
        textFieldUserName.setText(usuari.getUser_name());        
        textFieldDNI.setText(usuari.getDni());
        textFieldNom.setText(usuari.getNom());
        textFieldCognoms.setText(usuari.getCognoms());
        textFieldTelefon.setText(usuari.getTelefon());
        textFieldDireccio.setText(usuari.getDireccio());
        textFieldPais.setText(usuari.getPais());
        textFieldCorreu.setText(usuari.getCorreu());
        if(!usuari.getData_Alta().equals("null")){
            datePickerDataAlta.setValue(LocalDate.parse(usuari.getData_Alta()));
        }
        if(!usuari.getData_naixement().equals("null")){
            datePickerDataNaixement.setValue(LocalDate.parse(usuari.getData_naixement()));
        }
        textFieldAdminAlta.setText(usuari.getAdmin_Alta()); 
        passwordFieldPassword.setText(usuari.getPassword());
        textFieldNumSoci.setText(usuari.getNum_soci());
        user_name_actual = usuari.getUser_name();
    }
    
    private void esborrarDades(){
        // Esborrem l'usuari de memoria
        this.usuari = null;
        // Esborrem els camps de pantalla
        textFieldUserName.setText("");
        textFieldNumSoci.setText("");
        textFieldDNI.setText("");
        textFieldNom.setText("");
        textFieldCognoms.setText("");
        textFieldTelefon.setText("");
        textFieldDireccio.setText("");
        textFieldPais.setText("");
        textFieldCorreu.setText("");
        datePickerDataAlta.setValue(LocalDate.now());
        datePickerDataNaixement.setValue(null);
        textFieldAdminAlta.setText(""); 
        passwordFieldPassword.setText("");
        user_name_actual = null;
    }
    
    @FXML
    private void modificarPassword(ActionEvent event) throws IOException, ClassNotFoundException {
        // Creem la finestra per comprovar que s'introdueix el password desitjat        
        VBox vbox_pass1 = new VBox();
        Label label_pass1 = new Label("Contrasenya");
        PasswordField passfield_pass1 = new PasswordField();
        vbox_pass1.getChildren().addAll(label_pass1,passfield_pass1);
        
        VBox vbox_pass2 = new VBox();
        Label label_pass2 = new Label("Repeteix contrasenya");
        PasswordField passfield_pass2 = new PasswordField();
        vbox_pass2.getChildren().addAll(label_pass2,passfield_pass2);
        
        VBox vbox_resultat = new VBox();
        Label label_resultat = new Label("");
        
        HBox hbox_resultat = new HBox();        
        Button buto_aceptar = new Button("Aceptar");        
        buto_aceptar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        // Obtenim el texte dels dos passwords
                        String pass1 = passfield_pass1.getText();
                        String pass2 = passfield_pass2.getText();
                        // Comparem i fem en funci?? dels textfields passwords
                        // Si es null o esta buit
                        if(pass1 == null || pass2 == null || pass1.equals("") || pass2.equals("")){
                            label_resultat.setText("S'han d'omplir el dos camps");
                        // Si son iguals els dos camps
                        }else if(pass1.equals(pass2)){
                            //Comprovem si estem afegint o modificant usuari
                            if(usuari!=null){
                                // Si la variable usuari no esta buida es que estem modificat el password de l'usuari
                                msg_in = AccionsClient.modificarPassword(usuari, pass1);
                                codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                                // Comprobem el text del codi de resposta
                                significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                                // Sessio caducada
                                if(codi_resposta.equals("10")){
                                    sessioCaducada();
                                // Passord modificat correctament
                                }else if(codi_resposta.equals("9000")){
                                    alerta.setAlertType(Alert.AlertType.INFORMATION);
                                    alerta.setHeaderText(significat_codi_resposta);
                                    alerta.showAndWait();
                                    passwordFieldPassword.setText(pass1);
                                    // Tanquem finestra
                                    stage.close();
                                // Error al eliminar usuari
                                }else{
                                    label_resultat.setText(significat_codi_resposta);
                                }
                            }else{
                                // Si la variable usuari esta buida es que estem afegint un usuari nou
                                passwordFieldPassword.setText(pass1);
                                stage.close();
                            }
                        // Si no son iguals els dos camps del password
                        }else{
                            label_resultat.setText("La contrasenya no coincideix");
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        // Per sortir de la finestra
        Button buto_cancelar = new Button("Cancelar");
        buto_cancelar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    stage.close();
                }
            });
        hbox_resultat.getChildren().addAll(buto_aceptar,buto_cancelar);
        vbox_resultat.getChildren().addAll(label_resultat,hbox_resultat);

        VBox vbox = new VBox();
        vbox.getStyleClass().add("password");
        String cssFile1 = this.getClass().getResource("/styles/general.css").toExternalForm();
        String cssFile2 = this.getClass().getResource("/styles/password.css").toExternalForm();        
        vbox.getStylesheets().addAll(cssFile1,cssFile2);
        vbox.getChildren().addAll(vbox_pass1,vbox_pass2,vbox_resultat);
        
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Image icon = new Image("/resources/editar_icon_neg_16.png");
        stage.getIcons().add(icon);
        stage.setTitle("Verificar password");
        stage.setResizable(false);
        stage.setScene(new Scene(vbox));
        stage.initOwner( raiz.getScene().getWindow() );
        stage.setOnHiding(we -> stage = null);
        stage.show();
        
    }
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
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
        significat_codi_resposta = "Sessi?? caducada";
        // Establim alerta
        alerta.setTitle("Sessi?? caducada");
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
    }   
}