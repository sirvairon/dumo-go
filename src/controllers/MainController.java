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
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private HashMap<String, String> msg_in;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    private Alert alerta;
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
    private void tancarSessioButtonAction(ActionEvent event) throws IOException{
        msg_in = AccionsClient.ferLogOut();
        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        alerta.setTitle("Tancar sessió");
        alerta.setContentText("");
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
    
}
