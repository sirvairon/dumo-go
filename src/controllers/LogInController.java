/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author marcd
 */
public class LogInController implements Initializable {
    
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    
    @FXML
    private Label labelLogInMissatge;
    @FXML
    private Button buttonLogin;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldUsuari;
    @FXML
    private ToggleButton buttonOpcioEntrada;
       
    @FXML
    private void logInButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {
        logIn();
    }

    @FXML
    private void logIn() throws IOException, ClassNotFoundException {
        
        // Obtenim les dades d'usuari i password
        String usuari = textFieldUsuari.getText();
        String password = passwordFieldPassword.getText();
        String tipus_inici = buttonOpcioEntrada.getText();
        //String password2 = AccionsClient.getMD5(password);
        // Creem el Hashmap per obtenir el codi de resposta
        HashMap msg_in;
                       
        // Cridem el mètode per fer LogIn i guardem el codi de retorn obtingut 
        msg_in = AccionsClient.ferLogIn(usuari, password, tipus_inici);
        codi_resposta = (String) msg_in.get(STRING_CODI_RESPOSTA);
        
        System.out.println("(LOGIN CONTROLLER)codi_resposta:" + codi_resposta);
        // En cas d'error comprobem el significat i mostrem l'error en la label destinada per aquest us
        if(codi_resposta.equals("8000")){
            
            ((Node) labelLogInMissatge).getScene().getWindow().hide();
            
            Parent parent;            
            if(tipus_inici.equals("Administrador")){
                parent = FXMLLoader.load(getClass().getResource("/views/Admin.fxml"));
            }else {
                parent = FXMLLoader.load(getClass().getResource("/views/Usuari.fxml"));
            }
            
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);

            // Establim que si tanquem la finestra farem logout abans de tancar l'aplicacio
            stage.setOnCloseRequest(
                new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                      AccionsClient.ferLogOut();
                      Platform.exit();
                    }
                }
            );
            
            //stage.setResizable(false);
            Image icon = new Image("/resources/dumogo_window_icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Dumo-Go");
            stage.setMaximized(true);
            stage.show();
        }else if(codi_resposta.equals("-1")){
            significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
            System.out.println("Codi de resposta: " + codi_resposta + " - " + significat_codi_resposta);
            Alert alerta = new Alert(Alert.AlertType.NONE);
            DialogPane dialogPane = alerta.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
            alerta.initStyle(StageStyle.UNDECORATED);
            alerta.setTitle("Error de connexió");
            alerta.setHeaderText(significat_codi_resposta);
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.show();
        }else{
            significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
            System.out.println("Codi de resposta: " + codi_resposta + " - " + significat_codi_resposta);
            labelLogInMissatge.setText(significat_codi_resposta);
        }
    }
    
    @FXML
    private void buttonOpcioEntradaAction(ActionEvent event) throws IOException, ClassNotFoundException {
        String text = buttonOpcioEntrada.getText();
        if (text.equals("Usuari"))
            buttonOpcioEntrada.setText("Administrador");
        else{
            buttonOpcioEntrada.setText("Usuari");
        }
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EventHandler enter = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent ke) {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    logIn();
                } catch (IOException ex) {
                    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }};
        textFieldUsuari.setOnKeyPressed(enter);
        passwordFieldPassword.setOnKeyPressed(enter);
    }    
    
}
