/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class PasswordController extends UsuariEdicioController implements Initializable  {

    private Alert alerta;
    private String pass1, pass2;
    private HashMap<String, String> msg_in;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    
    @FXML
    private PasswordField passwordFieldPassword1;
    
    @FXML
    private PasswordField passwordFieldPassword2;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void modificarPassword(ActionEvent event) throws IOException, ClassNotFoundException{
        pass1 = passwordFieldPassword1.getText();
        pass2 = passwordFieldPassword2.getText();
        
        alerta = new Alert(Alert.AlertType.NONE);
        alerta.setTitle("Modificar password");
        alerta.setContentText("");        
        if(pass1.equals(pass2)){
            sessioCaducada();
            msg_in = AccionsClient.modificarPassword(pass1);
            codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
            // Comprobem el text del codi de resposta
            significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
            // Sessio caducada
            if(codi_resposta.equals("10")){
                //sessioCaducada();
            // Usuari eliminat correctament
            }else if(codi_resposta.equals("9000")){
                alerta.setAlertType(Alert.AlertType.INFORMATION);
                alerta.setHeaderText(significat_codi_resposta);
                alerta.showAndWait();
                // Tanquem finestra
                passwordFieldPassword1.getScene().getWindow().hide();
            // Error al eliminar usuari
            }else{
                alerta.setAlertType(Alert.AlertType.ERROR);
                alerta.setHeaderText(significat_codi_resposta);
                alerta.show();
            }
        }else{
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText("La contrasenya no coincideix");
            alerta.show();   
        }
    }
    
}