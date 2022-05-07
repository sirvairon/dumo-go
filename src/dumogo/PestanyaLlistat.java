/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import z_borrar.Usuari_;
import controllers.AdminController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author marcd
 */
public final class PestanyaLlistat extends Tab {

    private final static String USUARI_CASE = "usuaris";
    private final static String ADMINISTRADOR_CASE = "administradors";
    private final static String LLIBRES_CASE = "llibres";
    private final String tipusLlista;
    
    private final TableView taulaLlistat;
    private final HBox hbResultat, hbButons, hbFiltre;
    private final VBox vbContigut;
    private final Label resultat, resultatValor;
    private final TextField textFiltre;
    private final ChoiceBox opcioFiltre;
    private ObservableList<String> llistaFiltre;
    private final Button butoActualitzar, butoAfegir, butoModificar, butoEliminar, butoFiltre;    
    private ArrayList<Usuari_> llista;
    private ObservableList<Usuari_> data;
    private FilteredList<Usuari_> data_filtrada;
    private Map<String, String> mapaNomCamps;
    
    public PestanyaLlistat(String nomLlista, String tipusLlista) throws ClassNotFoundException {
        super(nomLlista);
        
        this.tipusLlista = tipusLlista;
        taulaLlistat = new TableView();
        taulaLlistat.setPlaceholder(new Label("No hi ha registres"));
        
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
        // Apliquem un listener per si canvia el camp del text del filtre que apliqui el filtre
        textFiltre.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltre();
        });
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
        
        // Obtenim les dades
        obtenirDades();
        // Creem el filtre
        crearFiltre(); 
        // Apliquem el filtre
        aplicarFiltre();
        // Creem les columnes de la taula
        crearColumnesTaula();                
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
 
    private void obtenirDades() throws ClassNotFoundException{
        switch(tipusLlista){
            case USUARI_CASE:
                // Esborrem l'informacio per carregar-la de nou
                data = null;

                // Obtenim el llistat_d'elements
                llista = AccionsClient.obtenirLlistat(tipusLlista);         

                // El transformem en una ObservableList
                data = FXCollections.observableArrayList(llista); 
                
                // Obtenim el nom dels camps per columnes, filtre,...
                mapaNomCamps = Usuari_.mapaNomCamps;
            case ADMINISTRADOR_CASE:
                // Esborrem l'informacio per carregar-la de nou
                data = null;

                // Obtenim el llistat_d'elements
                llista = AccionsClient.obtenirLlistat(tipusLlista);         

                // El transformem en una ObservableList
                data = FXCollections.observableArrayList(llista); 

                // Obtenim el nom dels camps per columnes, filtre,...
                mapaNomCamps = Usuari_.mapaNomCamps;
            break;            
        }
    }
        
    private void crearFiltre(){
        // Actualitzem les opcions del filtre en funcio del tipus de llista i mostrem en la label el tipus de resultats tornats
        switch(tipusLlista){
            case USUARI_CASE:  
                // Carreguem les opcions dins la llista de les opcions del filtre
                llistaFiltre = FXCollections.observableArrayList(
                        mapaNomCamps.get("DNI"), 
                        mapaNomCamps.get("user_name"),
                        mapaNomCamps.get("num_soci"),
                        mapaNomCamps.get("tipus_soci"),
                        mapaNomCamps.get("data_alta"),
                        mapaNomCamps.get("nom"), 
                        mapaNomCamps.get("cognom1"), 
                        mapaNomCamps.get("cognom2"), 
                        mapaNomCamps.get("data_naixement"),
                        mapaNomCamps.get("direccio"), 
                        mapaNomCamps.get("codi_postal"),
                        mapaNomCamps.get("poblacio"),
                        mapaNomCamps.get("provincia"), 
                        mapaNomCamps.get("pais"),
                        mapaNomCamps.get("telefon1"), 
                        mapaNomCamps.get("telefon2"),
                        mapaNomCamps.get("correu"),
                        mapaNomCamps.get("admin_alta")
                        );
                // Introduim les opcions dins les opcions del filtre
                opcioFiltre.setItems(llistaFiltre);
                // Deixem marcada l'opcio del DNI
                opcioFiltre.setValue(mapaNomCamps.get("DNI"));
                // Establim que la label dels resultats posi Usuaris
                resultat.setText("Usuaris:");
                break;
            case ADMINISTRADOR_CASE:
                mapaNomCamps = Usuari_.mapaNomCamps;
                System.out.println(mapaNomCamps);
                llistaFiltre = FXCollections.observableArrayList(
                        mapaNomCamps.get("DNI"), 
                        mapaNomCamps.get("user_name"),
                        mapaNomCamps.get("num_soci"),
                        mapaNomCamps.get("tipus_soci"),
                        mapaNomCamps.get("data_alta"),
                        mapaNomCamps.get("nom"), 
                        mapaNomCamps.get("cognom1"), 
                        mapaNomCamps.get("cognom2"), 
                        mapaNomCamps.get("data_naixement"),
                        mapaNomCamps.get("direccio"), 
                        mapaNomCamps.get("codi_postal"),
                        mapaNomCamps.get("poblacio"),
                        mapaNomCamps.get("provincia"), 
                        mapaNomCamps.get("pais"),
                        mapaNomCamps.get("telefon1"), 
                        mapaNomCamps.get("telefon2"),
                        mapaNomCamps.get("correu"),
                        mapaNomCamps.get("admin_alta")
                        );
                // Introduim les opcions dins les opcions del filtre
                opcioFiltre.setItems(llistaFiltre);
                // Deixem marcada l'opcio del nom del administrador
                opcioFiltre.setValue(mapaNomCamps.get("user_name"));
                // Establim que la label dels resultats posi Administradors
                resultat.setText("Administradors:");
                break;
            case "llibres":
                break;
        }
    }
    
    public void aplicarFiltre(){
        // Passem el string a trobar a minuscules
        String paraulaFiltre = textFiltre.getText().toLowerCase();
        
        // Obtenim a quin camp volem trobar la paraula
        String opcioFiltreTxt = opcioFiltre.getSelectionModel().getSelectedItem().toString();
        
        // Filtrem les dades
        data_filtrada = new FilteredList<>(data, b -> true);        
        data_filtrada.setPredicate(usuariFiltrat -> {
                // Si no hi ha paraula a filtrar/buscar mostrem tot
                if(paraulaFiltre.isEmpty() || paraulaFiltre == null){
                    return true;
                }
                
                if(opcioFiltreTxt.equals(mapaNomCamps.get("DNI"))){
                    if(usuariFiltrat.getDni().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("user_name"))){
                    if(usuariFiltrat.getNom_user().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("data_alta"))){
                    if(usuariFiltrat.getData_Alta().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("nom"))){
                    if(usuariFiltrat.getNom().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("cognom1"))){
                    if(usuariFiltrat.getCognom1().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("data_naixement"))){
                    if(usuariFiltrat.getData_naixement().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("direccio"))){
                    if(usuariFiltrat.getDireccio().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("pais"))){
                    if(usuariFiltrat.getPais().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("telefon1"))){
                    if(usuariFiltrat.getTelefon1().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("correu"))){
                    if(usuariFiltrat.getCorreu().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("admin_alta"))){
                    if(usuariFiltrat.getAdmin_Alta().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }
                /*
                else if(opcioFiltreTxt.equals(mapaNomCamps.get("cognom2"))){
                    if(usuariFiltrat.getCognom2().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("num_soci"))){
                    if(usuariFiltrat.getNum_soci().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("tipus_soci"))){
                    if(usuariFiltrat.getTipus_Soci().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("codi_postal"))){
                    if(usuariFiltrat.getCodi_postal().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("poblacio"))){
                    if(usuariFiltrat.getPoblacio().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("provincia"))){
                    if(usuariFiltrat.getProvincia().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("telefon2"))){
                    if(usuariFiltrat.getTelefon2().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }
                */
                // No s'ha trobat res
                return false;
            });
        
        // Introduim l'informació filtrada (SortedList) a la taula
        taulaLlistat.setItems(data_filtrada);
        // Obtenim el numero total de registres i la fiquem al label
        resultatValor.setText(String.valueOf(data_filtrada.size()));
    }
    
    public void actualitzarDades() throws ClassNotFoundException {        
        // Obtenim les dades
        obtenirDades();
        // Filtrem les dades
        aplicarFiltre();        
    }
    
    private void crearColumnesTaula() {               
        
        //switch(tipusLlista){
        //    case USUARI_CASE:
        
        // Per crear totes les columnes de la taula usuaris
        
        TableColumn<Usuari_,String> col_Nom = new TableColumn<Usuari_,String>(mapaNomCamps.get("nom"));        
        col_Nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().nom();
        }});
        //col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Usuari_,String> col_Cognom = new TableColumn<Usuari_,String>(mapaNomCamps.get("cognom1"));
        col_Cognom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().cognom1();
        }});

        TableColumn<Usuari_,String> col_NomUsuari = new TableColumn<Usuari_,String>(mapaNomCamps.get("user_name"));
        col_NomUsuari.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().nom_user();
        }});

        TableColumn<Usuari_,String> col_Direccio = new TableColumn<Usuari_,String>(mapaNomCamps.get("direccio"));
        col_Direccio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().direccio();
        }});      

        TableColumn<Usuari_,String> col_Telefon = new TableColumn<Usuari_,String>(mapaNomCamps.get("telefon1"));
        col_Telefon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().telefon1();
        }});

        TableColumn<Usuari_,String> col_DNI = new TableColumn<Usuari_,String>(mapaNomCamps.get("DNI"));
        col_DNI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().dni();
        }});

        TableColumn<Usuari_,String> col_Correu = new TableColumn<Usuari_,String>(mapaNomCamps.get("correu"));
        col_Correu.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().correu();
        }});

        TableColumn<Usuari_,String> col_Pais = new TableColumn<Usuari_,String>(mapaNomCamps.get("pais"));
        col_Pais.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().pais();
        }}); 
        
        TableColumn<Usuari_,String> col_DataNaixament = new TableColumn<Usuari_,String>(mapaNomCamps.get("data_naixement"));
        col_DataNaixament.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().data_naixement();
        }});  
        
        TableColumn<Usuari_,String> col_DataAlta = new TableColumn<Usuari_,String>(mapaNomCamps.get("data_alta"));
        col_DataAlta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().data_alta();
        }});  
        
        TableColumn<Usuari_,String> col_AdminAlta = new TableColumn<Usuari_,String>(mapaNomCamps.get("admin_alta"));
        col_AdminAlta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().admin_alta();
        }}); 
        
        /*
        TableColumn<Usuari_,String> col_Poblacio = new TableColumn<Usuari_,String>(mapaNomCamps.get("poblacio"));
        col_Poblacio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().poblacio();
        }});

        TableColumn<Usuari_,String> col_Provincia = new TableColumn<Usuari_,String>(mapaNomCamps.get("provincia"));
        col_Provincia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().provincia();
        }});

        TableColumn<Usuari_,String> col_Cognom2 = new TableColumn<Usuari_,String>(mapaNomCamps.get("cognom2"));
        col_Cognom2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().cognom2();
        }});

        TableColumn<Usuari_,String> col_NumSoci = new TableColumn<Usuari_,String>(mapaNomCamps.get("num_soci"));
        col_NumSoci.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().num_soci();
        }});
        
        TableColumn<Usuari_,String> col_CodiPostal = new TableColumn<Usuari_,String>(mapaNomCamps.get("codi_postal"));
        col_CodiPostal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().codi_postal();
        }});
        
        TableColumn<Usuari_,String> col_Telefon2 = new TableColumn<Usuari_,String>(mapaNomCamps.get("telefon2"));
        col_Telefon2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().telefon2();
        }});
        
        TableColumn<Usuari_,String> col_Genere = new TableColumn<Usuari_,String>(mapaNomCamps.get("genere"));
        col_Genere.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().genere();
        }}); 
        
        TableColumn<Usuari_,String> col_Observacions = new TableColumn<Usuari_,String>(mapaNomCamps.get("observacions"));
        col_Observacions.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().observacions();
        }});  
        
        TableColumn<Usuari_,String> col_TipusSoci = new TableColumn<Usuari_,String>(mapaNomCamps.get("tipus_soci"));
        col_TipusSoci.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari_,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari_,String> p) {
                return p.getValue().tipus_soci();
        }}); 
        */
        
        // Afegim les columnes a la taula    
        switch(tipusLlista){
            case USUARI_CASE:
                taulaLlistat.getColumns().addAll(
                        col_DNI, 
                        col_NomUsuari, 
                        //col_NumSoci, 
                        //col_TipusSoci, 
                        col_DataAlta, 
                        col_Nom, 
                        col_Cognom, 
                        //col_Cognom2, 
                        col_DataNaixament, 
                        //col_Genere, 
                        col_Direccio, 
                        //col_CodiPostal, 
                        //col_Poblacio, 
                        //col_Provincia, 
                        col_Pais, 
                        col_Telefon, 
                        //col_Telefon2, 
                        col_Correu, 
                        col_AdminAlta 
                        //col_Observacions
                        );    
                break;
            case ADMINISTRADOR_CASE:
                taulaLlistat.getColumns().addAll(
                        col_DNI, 
                        col_NomUsuari, 
                        //col_NumSoci, 
                        //col_TipusSoci, 
                        col_DataAlta, 
                        col_Nom, 
                        col_Cognom, 
                        //col_Cognom2, 
                        col_DataNaixament, 
                        //col_Genere, 
                        col_Direccio, 
                        //col_CodiPostal, 
                        //col_Poblacio, 
                        //col_Provincia, 
                        col_Pais, 
                        col_Telefon, 
                        //col_Telefon2, 
                        col_Correu, 
                        col_AdminAlta 
                        //col_Observacions
                        );    
                break;
        }
    }
}
