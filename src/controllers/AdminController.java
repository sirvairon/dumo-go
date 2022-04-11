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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class AdminController implements Initializable {

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    
    @FXML
    private TableView tableViewUsuaris;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void tancarSessioButtonAction(ActionEvent event) throws IOException {
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
    }    
    
    
    @FXML
    private void obtenirLlistatUsuaris(ActionEvent event) throws IOException, ClassNotFoundException {
        // Creem el HashMap on rebrem les dades de l'usuari
        List<HashMap> llistat_usuaris;
        
        // Cridem el metode per rebre el llistat de totes les dades dels usuaris
        llistat_usuaris = AccionsClient.obtenirLlistatUsuaris();
        construirDades(llistat_usuaris);
        tableViewUsuaris.setItems(data);
        //System.out.println(msg_in.toString());
    }    
    
    public void construirDades(List<HashMap> list) {
        data = FXCollections.observableArrayList();

        for (HashMap obj:list) {
            // formato clasico
            //System.out.println(obj.getClass());
            
            //System.out.println(obj.toString());
            
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            TableColumn col_1 = new TableColumn("Nom");
            col_1.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(1).toString());
                }
            });

            tableViewUsuaris.getColumns().addAll(col_1);
                /*

            
            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */

            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= obj.size(); i++) {
                //Iterate Column
                row.add((String)obj.get("nom"));
            }
            System.out.println("Row [1] added " + row);
            data.add(row);
 
        }

        /*
        list.forEach((p)-> {
            System.out.println(p.get("Nom"));
            System.out.println(p.get("num_soci"));
            System.out.println((String)p.get("provincia"));
        });;
        */
        
    }
    /*
        //CONNECTION DATABASE
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = c.createStatement().executeQuery(SQL);
 
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * PARA EL NUMERO DE COLUMNAS
             *********************************
             *
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
 
                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
 
            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             *
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
 
            }
 
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    */
}
