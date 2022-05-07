/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package z_borrar;

import dumogo.AccionsClient;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class UsuariBuscarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField textFieldNomUsuari; 
    
    @FXML
    private Button butoBuscar;
    
    @FXML
    private Button butoCancelar;

    public String getNomUsuari() {
        return textFieldNomUsuari.getText();
    }

    public Button getButoBuscar() {
        return butoBuscar;
    }

    public Button getButoCancelar() {
        return butoCancelar;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        getButoBuscar().setOnAction(e -> {
            try {
                buscarUsuari();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuariBuscarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    
    @FXML
    private void buscarUsuari() throws ClassNotFoundException{
        // Obtenim el text per pantalla
        String nom_usuari = textFieldNomUsuari.getText();
        // Creem el HashMap on rebrem el codi de resposta
        HashMap<String, String> msg_in;
        // Fem l'accio de buscar usuari
        msg_in = AccionsClient.buscarUsuari(nom_usuari);
        System.out.println(msg_in.toString());   
    }
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    
}
