/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class UsuariController implements Initializable {

    /**
     * Initializes the controller class.
     */
      
    @FXML
    private TextField textFieldNomUsuari;
    
    @FXML
    private TextField textFieldNumSoci;
    
    @FXML
    private TextField textFieldDNI;
    
    @FXML
    private TextField textFieldNom;
    
    @FXML
    private TextField textFieldCognom;
    
    @FXML
    private TextField textFieldCognom2;
    
    @FXML
    private TextField textFieldTelefon;
    
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
    private TextField textFieldEmail;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void volverButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        Image icon = new Image("/resources/icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Dumo-Go");
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void verButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {
        
        // Creem el HashMap on rebrem les dades de l'usuari
        HashMap<String, String> msg_in;
        
        // Cridem el metode per rebre les dades de l'usuari
        msg_in = AccionsClient.obtenirUsuari();
        System.out.println(msg_in.toString());
        // Omplim els labels amb les dades rebudes
        textFieldNumSoci.setText(msg_in.get("num_soci"));
        textFieldDNI.setText(msg_in.get("dni"));
        textFieldNom.setText(msg_in.get("nom"));
        textFieldCognom.setText(msg_in.get("cognom1"));
        textFieldCognom2.setText(msg_in.get("cognom2"));
        textFieldTelefon.setText(msg_in.get("telefon"));
        textFieldTelefon2.setText(msg_in.get("telefon"));
        textFieldDireccio.setText(msg_in.get("direccio"));
        textFieldPoblacio.setText(msg_in.get("poblacio"));
        textFieldProvincia.setText(msg_in.get("provincia"));
        textFieldCodiPostal.setText(msg_in.get("codi_postal"));
        textFieldEmail.setText(msg_in.get("email"));
        textFieldNomUsuari.setText(msg_in.get("user_name"));
    }
    
    @FXML
    private void modificarButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {


    }
    
}
