/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.Administrador;
import dumogo.CodiErrors;
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
public class AdminEdicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
     
    private Stage stage;
    private Administrador administrador;
    private String nom_admin_actual;
    private String codi_resposta;    
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    
    @FXML
    private VBox raiz;    
    @FXML
    private TextField textFieldAdminName; 
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
    } 

    public void afegirAdministrador(){
        // Esborrem dades en cas de que hi hagi
        esborrarDades();
        // Establim l'administrador que esta afegint l'administrador
        textFieldAdminAlta.setText(AccionsClient.getNom_user_actual());
        // Com es un alta nova desde l'administrador s'han de poder editar tots els camps
        textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (afegir)
        butoAccio.setText("Afegir");
        // Configurem el EventHandler en cas de fer click al boto d'afegir
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                    // Obtenim l'administrador de pantalla
                    administrador = obtenirAdministradorPantalla();
                    // Establim que el nom_admin_antic i el nom_admin siguien el mateix
                    administrador.setNom_admin_antic(administrador.getNom_Admin());
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in;
                        // Fem l'accio d'afegir l'administrador
                        msg_in = AccionsClient.afegirElement(administrador);
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        // Obtenim el sinificat del codi de resposta
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                        alerta.setTitle("Afegir administrador");
                        alerta.setHeaderText(significat_codi_resposta);
                        // Sessio caducada
                        if(codi_resposta.equals("10")){
                            sessioCaducada();
                        // Administrador afegit correctament
                        }else if(codi_resposta.equals("2000")){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.showAndWait();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        // Error a l'afegir l'administrador
                        }else{
                            administrador = null;
                            alerta.setAlertType(Alert.AlertType.ERROR);
                            alerta.show();
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AdminEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
    }
    
    public void modificarAdministrador(Administrador a){
        /** PER MODIFICAR PERFIL DESDE L'ADMINISTRADOR **/
        // Omplim les dades per les dades obtingudes de l'administrador
        omplirDades(a);
        // Com es desde l'administrador s'han de poder editar tots els camps
        textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (modificar)
        butoAccio.setText("Modificar");
        // Configurem el EventHandler en cas de fer click al boto de modificar
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                // Obtenim les dades de l'administrador amb les dades de la pantalla
                administrador = obtenirAdministradorPantalla();
                if(administrador!=null){
                    // Omplim el camp "nom_admin_antic" del Administrador amb el nom anterior
                    System.out.println("nom_admin_actual: "+ nom_admin_actual);
                    administrador.setNom_admin_antic(nom_admin_actual);
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in = null;
                        msg_in = AccionsClient.modificarElement(administrador);
                        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                        alerta.setTitle("Modificar administrador");
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        // Obtenim el sinificat del codi de resposta
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        alerta.setHeaderText(significat_codi_resposta);
                        switch (codi_resposta) {
                            case "10": // Sessio caducada
                                sessioCaducada();
                                // Administrador modificat correctament
                                break;
                            case "1300": // Correcte
                                alerta.setAlertType(Alert.AlertType.INFORMATION);
                                alerta.showAndWait();
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                                // Error al modificar l'administrador
                                break;
                            default: // Error
                                alerta.setAlertType(Alert.AlertType.ERROR);
                                alerta.show();
                                break;
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AdminEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private Administrador obtenirAdministradorPantalla(){
        // Comprobem si alguns camps estan buits
        String data;
        if(textFieldAdminName.getText().equals("") || textFieldAdminName.getText() == null){
            // Configurem l'alerta que ens confirmara que hi ha hagut error a l'obtenir les dades de l'administrador a pantalla
            alerta.setTitle("Nom d'administrdador");
            alerta.setHeaderText("Es obligatori introduir un nom d'administrador");  
            alerta.setAlertType(Alert.AlertType.ERROR);
            // La mostrem i esperem click
            alerta.show();
            return null;
        }else if(datePickerDataNaixement.getValue() == null){
            // Configurem l'alerta que ens confirmara que hi ha hagut error a l'obtenir les dades de l'administrador a pantalla
            alerta.setTitle("Data de naixement");
            alerta.setHeaderText("Es obligatori introduir la data de naixement");  
            alerta.setAlertType(Alert.AlertType.ERROR);
            // La mostrem i esperem click
            alerta.show();
            return null;
        }else{
            Administrador a = new Administrador(
                new SimpleStringProperty(textFieldAdminName.getText()),
                new SimpleStringProperty(passwordFieldPassword.getText()),
                new SimpleStringProperty(textFieldDNI.getText()),
                new SimpleStringProperty(datePickerDataNaixement.getValue().toString()),
                new SimpleStringProperty(textFieldNom.getText()),
                new SimpleStringProperty(textFieldCognoms.getText()),
                new SimpleStringProperty(textFieldDireccio.getText()),
                new SimpleStringProperty(textFieldPais.getText()),
                new SimpleStringProperty(textFieldTelefon.getText()),
                new SimpleStringProperty(textFieldCorreu.getText()),
                new SimpleStringProperty(textFieldAdminAlta.getText())
            );
            a.setNom_admin_antic("");
            // Tornem l'administrador creat
            return a;
        }
    }
    
    private void omplirDades(Administrador administrador){
        // Agafem l'administrador i el guardem
        this.administrador = administrador;
        // Omplim els camps de pantalla amb l'administrador
        textFieldAdminName.setText(administrador.getNom_Admin());        
        textFieldDNI.setText(administrador.getDni());
        textFieldNom.setText(administrador.getNom());
        textFieldCognoms.setText(administrador.getCognoms());
        textFieldTelefon.setText(administrador.getTelefon());
        textFieldDireccio.setText(administrador.getDireccio());
        textFieldPais.setText(administrador.getPais());
        textFieldCorreu.setText(administrador.getCorreu());
        if(!administrador.getData_naixement().equals("null")){
            datePickerDataNaixement.setValue(LocalDate.parse(administrador.getData_naixement()));
        }
        textFieldAdminAlta.setText(administrador.getAdmin_Alta()); 
        passwordFieldPassword.setText(administrador.getPassword());
        nom_admin_actual = administrador.getNom_Admin();
    }
    
    private void esborrarDades(){
        // Esborrem l'administrador de memoria
        this.administrador = null;
        // Esborrem els camps de pantalla
        textFieldAdminName.setText("");
        textFieldDNI.setText("");
        textFieldNom.setText("");
        textFieldCognoms.setText("");
        textFieldTelefon.setText("");
        textFieldDireccio.setText("");
        textFieldPais.setText("");
        textFieldCorreu.setText("");
        datePickerDataNaixement.setValue(null);
        textFieldAdminAlta.setText(""); 
        passwordFieldPassword.setText("");
        nom_admin_actual = null;
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
                        // Comparem i fem en funció dels textfields passwords
                        // Si es null o esta buit
                        if(pass1 == null || pass2 == null || pass1.equals("") || pass2.equals("")){
                        label_resultat.setText("S'han d'omplir el dos camps");
                        // Si son iguals els dos camps
                        }else if(pass1.equals(pass2)){
                            //Comprovem si estem afegint o modificant usuari
                            if(administrador!=null){
                                // Si la variable usuari no esta buida es que estem modificat el password de l'usuari
                                msg_in = AccionsClient.modificarPassword(administrador, pass1);
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
                                System.out.println("modificarpassword msg_in:");
                                System.out.println(msg_in.toString());
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
                        Logger.getLogger(AdminEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminEdicioController.class.getName()).log(Level.SEVERE, null, ex);
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
        stage.getScene().getWindow();
        // Obrim finestra de login
        Parent parent = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(parent);
        stage1.setScene(scene);
        Image icon = new Image("/resources/dumogo_icon_window.png");
        stage1.getIcons().add(icon);
        stage1.setTitle("Dumo-Go");
        stage1.setResizable(false);
        stage1.show();    
    }   
}