/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import dumogo.Comentari;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class ComentariController implements Initializable {

    Comentari comentari;
    private Alert alerta;
    private HashMap<String, String> msg_in;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private String codi_resposta;
    private String significat_codi_resposta;
    
    @FXML
    private Label labelComentari; 
    @FXML
    private Label labelUsuari;
    @FXML
    private Label labelData;
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        alerta.initStyle(StageStyle.UNDECORATED);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
    }    
    
    public void mostrarComentari(Comentari comentari){
        // Agafem el comentari i el guardem
        this.comentari = comentari;
        // Omplim els camps de pantalla amb el comentari
        labelComentari.setText(comentari.getComentari());        
        labelUsuari.setText(comentari.getUser_name());
        labelData.setText(comentari.getData());
        
        // Comprovem si l'usuari actual es el creador del comentari
        String user_actual = AccionsClient.getNom_user_actual();
        if(user_actual.equals(comentari.getUser_name())){
            Button buto_eliminar = new Button ("Eliminar");
            buto_eliminar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    eliminarComentari();
                }
            });
            hboxBotones.getChildren().add(0, buto_eliminar);
        }
    }
    
    private void eliminarComentari(){
        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
        alerta.setAlertType(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar comentari");      
        alerta.setHeaderText("Vols esborrar el comentari?");
        alerta.setContentText("S'esborrarà definitivament");
        
        Optional<ButtonType> option = alerta.showAndWait();
        if (option.get() == ButtonType.OK) {   
            try {
                // Fem l'accio d'eliminar el comentari
                msg_in = AccionsClient.eliminarElement(comentari);
            
                // Obtenim el codi de resposta
                codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                // Comprobem el text del codi de resposta
                significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                // Sessio caducada
                if(codi_resposta.equals("10")){
                    sessioCaducada();
                // Comentari eliminat correctament
                }else if(codi_resposta.equals("2800")){
                    Alert alerta2 = new Alert(Alert.AlertType.NONE);
                    alerta2.initStyle(StageStyle.UNDECORATED);
                    DialogPane dialogPane = alerta2.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
                    alerta2.setAlertType(Alert.AlertType.INFORMATION);
                    alerta2.setHeaderText(significat_codi_resposta);
                    alerta2.showAndWait();
                    labelComentari.getScene().getWindow().hide();
                // Error al eliminar el comentari
                }else{
                    alerta.setAlertType(Alert.AlertType.ERROR);
                    alerta.setHeaderText(significat_codi_resposta);
                    alerta.show();
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ComentariController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ComentariController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void sessioCaducada() throws IOException {
        // En cas de retornar codi 10 (sessio caducada)
        // Obtenim el text de l'error
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        // Establim alerta
        alerta.setTitle("Sessió caducada");
        alerta.setContentText("");
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.setHeaderText(significat_codi_resposta);
        // La mostrem i esperem click
        alerta.showAndWait();
        // Tanquem finestra
        labelComentari.getScene().getWindow().hide();
        // Obrim finestra de login
        Parent parent = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        Image icon = new Image("/resources/dumogo_window_icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Dumo-Go");
        stage.setResizable(false);
        stage.show();   
    }

}
