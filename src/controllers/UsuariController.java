/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
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
    private TextField textFieldSegonCognom;
    
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
    
    private void verButtonAction(ActionEvent event) throws IOException, ClassNotFoundException {
        
        String usuari = textFieldNomUsuari.getText();
        
        AccionsClient accions_client = new AccionsClient();
        
        //resposta = connexio_client.establirConnexio(usuari, password);
        //resposta = connexio_client.obtenirUsuari(usuari);
        //labelLogInMissatge.setText(resposta);
        HashMap resposta2 = new HashMap<String, String>();
        //resposta2 = accions_client.obtenirUsuari(usuari);      
        
        textFieldDireccio.setText((String)resposta2.get("direccio"));
    }
}
