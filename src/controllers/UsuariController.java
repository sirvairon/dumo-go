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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class UsuariController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private HashMap<String, String> msg_in;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    private Alert alerta;
    private UsuariEdicioController usuariEdicioControlador;
    private Stage stageUsuari;
    
    @FXML
    private GridPane grid_libros;     
    
    @FXML
    private ImageView book_image; 
    
    @FXML
    private Label book_title; 
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        // TODO
        Label label1 = new Label("A");
        Label label2 = new Label("B");
        Label label3 = new Label("C");
        Label label4 = new Label("D");
        //grid_libros.add(label4, 0, 0, 1, 1);
        //grid_libros.add(label1, 1, 0, 1, 1);
        //grid_libros.add(label2, 2, 0, 1, 1);
        //grid_libros.add(label3, 3, 0, 1, 1);
        ColumnConstraints column = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        column.setHalignment(HPos.CENTER);
        row.setValignment(VPos.BOTTOM);
        row.setVgrow(Priority.ALWAYS);
        grid_libros.getRowConstraints().addAll(row);
        grid_libros.getColumnConstraints().addAll(column);
        Image image1 = new Image(getClass().getResourceAsStream("/resources/book.jpg"));
        for(int i = 0; i<60; i++){
            for(int j = 0; j<4; j++){
                //grid_libros.addRow(i, new Label("Id"));
                VBox vbox_libro = new VBox();
                vbox_libro.getChildren().add(new ImageView(image1));
                vbox_libro.getChildren().add(new Label("Id"+(j+i)));
                grid_libros.add(vbox_libro,j,i);
                //grid_libros.add(new ImageView(image1),j,i);
                //grid_libros.add(new Label("Id"+(j+i)), j, i);                
            }           
        }
        //grid_libros.addRow(1, new Label("Id"));
        //grid_libros.addRow(2, new Label("Id"));
        //grid_libros.addRow(3, new Label("Id"));
*/        
        alerta = new Alert(Alert.AlertType.NONE);
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);

    }    
    
    @FXML
    private void ferLogout(ActionEvent event) throws IOException{
        // Fem l'accio de fer tancar sessio
        msg_in = AccionsClient.ferLogOut();
        // Obtenim codi de resposta
        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
        // Obtenim significat del codi de resposta
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);        
        // Comprobem si ha sigut correcte tancar sessio
        if(codi_resposta.equals("20")){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            Image icon = new Image("/resources/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Dumo-Go");
            stage.setResizable(false);
            stage.show();      
        }else{
            // Configurem l'alerta per indicar que hi ha hagut un error
            alerta.setTitle("Tancar sessió");
            alerta.setContentText("");
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText(significat_codi_resposta);
            alerta.show();
        }
    }
    
    @FXML
    private void veureUsuariButtonAction(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Usuari.fxml"));
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
    private void mostrarPerfil(ActionEvent event) throws IOException {
         // Obrim la finestra usuari 
        obrirFinestraUsuari();
        // Actualitzem el controlador (finestra usuari) per mijtà del mètode dins del controlador
        usuariEdicioControlador.mostrarPerfil();
    } 
    
    private void obrirFinestraUsuari() throws IOException{ // Per modificar
        // En cas de que no s'hagi creat el stage (finestra oberta) el creem
        if (stageUsuari == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UsuariEdicio.fxml"));
            Parent root = (Parent) loader.load();
            usuariEdicioControlador = loader.getController();
            stageUsuari = new Stage();
            stageUsuari.setScene(new Scene(root));
            Image icon = new Image("/resources/usuari_icon.png");
            //stageUsuari.getIcons().add(icon);
            //stageUsuari.setTitle("Dumo-Go2");
            stageUsuari.setResizable(false);

            // Quan es tanqui esborrem el stage de memòria                    
            stageUsuari.setOnHiding(we -> stageUsuari = null);

            // Mostrem la finestra del usuari a editar
            stageUsuari.show();   
            
        // En cas de ja estigui creat el stage (finestra oberta) el portem al davant
        }else{
            stageUsuari.toFront();
        }
    }
    
}