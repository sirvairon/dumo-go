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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author marcd
 */
public class LogInController implements Initializable {
    
    @FXML
    private Label labelLogInMissatge;
    @FXML
    private Button buttonLogin;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldUsuari;
    
    @FXML
    private void logInButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {
        
        // Obtenim les dades d'usuari i password
        String usuari = textFieldUsuari.getText();
        String password = passwordFieldPassword.getText();
        
        // Creem el Hashmap per passar les dades
        HashMap usuari_pass = new HashMap<String, String>();
        usuari_pass.put(usuari,password);
        
        // Creem el Hashmap per obtenir el codi de resposta
        HashMap resposta2 = new HashMap<String, String>();
        String resposta;
                        
        // Cridem el mètode per fer LogIn i guardem el codi de retorn obtingut
        String codi_retorn = AccionsClient.ferLogin(usuari, password);      

        // En cas d'error mostrem l'error en la label destinada per aquest us
        if(!codi_retorn.equals("1000")){
            labelLogInMissatge.setText(CodiErrors.ComprobarCodiError(codi_retorn));
        // En cas d'error mostrem l'error en la label destinada per aquest us
        }else{
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.setResizable(false);
            Image icon = new Image("/resources/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Dumo-Go");
            stage.show();
        }

/*
        }elseif(textFieldUsuari.getText().equals("admin")){
            Parent parent = FXMLLoader.load(getClass().getResource("/views/Admin.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            
            stage.setScene(scene);
            stage.setResizable(false);
            Image icon = new Image("/resources/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Dumo-Go - Administrador");
            stage.show();
            //labelLogInMissatge.setText("OK");
        }
*/
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
