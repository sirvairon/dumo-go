/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import controllers.AdminController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author marcd
 */
public class PestanyaLlistat extends Tab {

    private final TableView taulaLlistat;
    private final HBox hbResultat, hbButons, hbFiltre;
    private final VBox vbContigut;
    private final Label resultat, resultatValor;
    private final TextField textFiltre;
    private final ChoiceBox opcioFiltre;
    private final Button butoActualitzar, butoAfegir, butoModificar, butoEliminar, butoFiltre; // Actualitzar
    private final String tipusLlista;
    private ArrayList<Usuari> llista;
    private ObservableList<Usuari> data;
    
    public PestanyaLlistat(String nomLlista, String tipusLlista) throws ClassNotFoundException {
        super(nomLlista);
        
        this.tipusLlista = tipusLlista;
        taulaLlistat = new TableView();

        // Creem el HBox que contindrà els labels amb informació del resultat total
        hbResultat = new HBox();
        hbResultat.getStyleClass().add("hBoxResultat");
        resultat = new Label(""); // Usuaris
        resultatValor = new Label(""); // Valor
        hbResultat.getChildren().addAll(resultat, resultatValor);
        
        // Creem el HBox per filtrar que contindrà el buto, el ChoiceBox i el el texte del filtre
        hbFiltre = new HBox();
        hbFiltre.getStyleClass().add("hBoxFiltre");
        butoFiltre = new Button("Filtre");
        opcioFiltre = new ChoiceBox();
        textFiltre = new TextField();
        hbFiltre.setHgrow(hbFiltre, Priority.ALWAYS);
        hbFiltre.getChildren().addAll(butoFiltre, opcioFiltre, textFiltre);
        
        // Creem el HBox que contindrà HBox per filtrar i els botons per editar l'informacio de la taula
        hbButons = new HBox();
        hbButons.getStyleClass().add("hboxBotonesEdicion");        
        butoActualitzar = new Button("Actualitzar");
        butoAfegir = new Button("Afegir");
        butoModificar = new Button("Modificar");
        butoEliminar = new Button("Eliminar");      
        hbButons.getChildren().addAll(hbFiltre, butoActualitzar, butoAfegir, butoModificar, butoEliminar);
            
        // Creem el VBox que contindra la taula i els dos HBox que hem creat
        vbContigut = new VBox();
        vbContigut.getStyleClass().add("vbLlistat");
        vbContigut.setVgrow(taulaLlistat, Priority.ALWAYS);
        vbContigut.getChildren().addAll(hbButons, taulaLlistat, hbResultat);
        
        // Afegim el VBox que conte tot a la pestanya l'arrel
        this.setContent(vbContigut);
        
        crearColumnesTaula();
        
        actualitzaDadesTaula();
        
        }
  
    public TableView getTaula() {
        return taulaLlistat;
    }
    
    public Button getButoModificar() {
        return butoModificar;
    }
        
    public Button getButoAfegir() {
        return butoAfegir;
    }
            
    public Button getButoEliminar() {
        return butoEliminar;
    }
                
    public Button getButoActualitzar() {
        return butoActualitzar;
    }
    
    public Button getButoFiltre() {
        return butoFiltre;
    }
    
    public void actualitzaDadesTaula() throws ClassNotFoundException {
        // Esborrem l'informacio per carregar-la de nou
        data = null;

        // Obtenim el llistat_d'elements
        llista = AccionsClient.obtenirLlistat(tipusLlista);         

        // El transformem en una ObservableList
        data = FXCollections.observableArrayList(llista);

        // Introduim l'informació (ObservableList) a la taula
        taulaLlistat.setItems(data);      
        
        // Mostrem en la label el numero de resultats tornats
        switch(tipusLlista){
            case "usuaris":               
                resultat.setText("Usuaris:");
                break;
            case "administradors":
                resultat.setText("Administradors:"); 
                break;
            case "llibres":
                break;
        }
        resultatValor.setText(String.valueOf(llista.size()));
        
    }
    
    private void crearColumnesTaula() {               
        
        // Per crear totes les columnes de la taula usuaris
        
        TableColumn<Usuari,String> col_Nom = new TableColumn<Usuari,String>("Nom");
        col_Nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().nom();
        }});

        TableColumn<Usuari,String> col_Cognom = new TableColumn<Usuari,String>("Cognom");
        col_Cognom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().cognom1();
        }});

        TableColumn<Usuari,String> col_Poblacio = new TableColumn<Usuari,String>("Població");
        col_Poblacio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().poblacio();
        }});

        TableColumn<Usuari,String> col_NomUsuari = new TableColumn<Usuari,String>("Nom d'usuari");
        col_NomUsuari.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().nom_user();
        }});

        TableColumn<Usuari,String> col_Provincia = new TableColumn<Usuari,String>("Província");
        col_Provincia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().provincia();
        }});

        TableColumn<Usuari,String> col_Cognom2 = new TableColumn<Usuari,String>("Segon cognom");
        col_Cognom2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().cognom2();
        }});

        TableColumn<Usuari,String> col_NumSoci = new TableColumn<Usuari,String>("Núm. soci");
        col_NumSoci.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().num_soci();
        }});

        TableColumn<Usuari,String> col_Direccio = new TableColumn<Usuari,String>("Direcció");
        col_Direccio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().direccio();
        }});      

        TableColumn<Usuari,String> col_Telefon = new TableColumn<Usuari,String>("Telèfon 1");
        col_Telefon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().telefon1();
        }});

        TableColumn<Usuari,String> col_CodiPostal = new TableColumn<Usuari,String>("Codi Postal");
        col_CodiPostal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().codi_postal();
        }});

        TableColumn<Usuari,String> col_DNI = new TableColumn<Usuari,String>("DNI");
        col_DNI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().dni();
        }});

        TableColumn<Usuari,String> col_Correu = new TableColumn<Usuari,String>("Correu");
        col_Correu.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().correu();
        }});

        TableColumn<Usuari,String> col_Telefon2 = new TableColumn<Usuari,String>("Telèfon 2");
        col_Telefon2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().telefon2();
        }});
        
        TableColumn<Usuari,String> col_Pais = new TableColumn<Usuari,String>("País");
        col_Pais.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().pais();
        }}); 
        
        TableColumn<Usuari,String> col_Genere = new TableColumn<Usuari,String>("Gènere");
        col_Genere.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().genere();
        }});  
        
        TableColumn<Usuari,String> col_DataNaixament = new TableColumn<Usuari,String>("Data de naixement");
        col_DataNaixament.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().data_naixement();
        }});  
        
        TableColumn<Usuari,String> col_Observacions = new TableColumn<Usuari,String>("Observacions");
        col_Observacions.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().observacions();
        }});  
        
        TableColumn<Usuari,String> col_TipusSoci = new TableColumn<Usuari,String>("Tipus de soci");
        col_TipusSoci.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().tipus_soci();
        }});  
        
        TableColumn<Usuari,String> col_DataAlta = new TableColumn<Usuari,String>("Dalta d'alta");
        col_DataAlta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().data_alta();
        }});  
        
        TableColumn<Usuari,String> col_AdminAlta = new TableColumn<Usuari,String>("Dalta d'alta");
        col_AdminAlta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari, String> p) {
                return p.getValue().admin_alta();
        }}); 
        

        // Afegim les columnes a la taula    
        switch(tipusLlista){
            case "usuaris":
                taulaLlistat.getColumns().addAll(
                        col_DNI, 
                        col_NomUsuari, 
                        col_NumSoci, 
                        col_TipusSoci, 
                        col_DataAlta, 
                        col_Nom, 
                        col_Cognom, 
                        col_Cognom2, 
                        col_DataNaixament, 
                        col_Genere, 
                        col_Direccio, 
                        col_CodiPostal, 
                        col_Poblacio, 
                        col_Provincia, 
                        col_Pais, 
                        col_Telefon, 
                        col_Telefon2, 
                        col_Correu, 
                        col_AdminAlta, 
                        col_Observacions
                        );    
                break;
            case "administradors":
                taulaLlistat.getColumns().addAll(
                        col_DNI, 
                        col_NomUsuari, 
                        col_NumSoci, 
                        col_TipusSoci, 
                        col_DataAlta, 
                        col_Nom, 
                        col_Cognom, 
                        col_Cognom2, 
                        col_DataNaixament, 
                        col_Genere, 
                        col_Direccio, 
                        col_CodiPostal, 
                        col_Poblacio, 
                        col_Provincia, 
                        col_Pais, 
                        col_Telefon, 
                        col_Telefon2, 
                        col_Correu, 
                        col_AdminAlta, 
                        col_Observacions
                        ); 
                break;
        }
    }

    /*
    private void clickTaulaUsuaris() throws IOException {
        // Mirem l'element clickat
        Object element = taulaLlistat.getSelectionModel().getSelectedItem();
        Object element_temp = null;
        Date tempsUltimClick = null;
        
        // Si està buit tornem
        if (element == null) {
            //return;
        // Si fem click un altre vegada comparem si es el mateix element clickat i generem un temps desde aquest click
        }else if(element != element_temp){
            element_temp = element;
            tempsUltimClick = new Date();
        // Si es el mateix element clickat i generem un altre temps desde aquest click i comparem temps entre clicks
        } else if(element == element_temp) {
            Date now = new Date();
            long diff = now.getTime() - tempsUltimClick.getTime();
            if (diff < 300){ // Si ha sigut rapid (300 millis) es doble click
                System.out.println("DOBLE CLICK");

                
                if(element.getClass().getName().equals("Usuari")){
                    System.out.println("OK");
                    usuari_fila = (Usuari)element;
                    // Obrim la finestra usuari 
                    //modificarUsuari(usuari_fila);
                }                
            } else {
                tempsUltimClick = new Date();
            }
        }
    }
    */

}
