/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import dumogo.Usuari;
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
    private final static String LLIBRE_CASE = "llibres";
    private final String tipusLlista;
    
    private final TableView taulaLlistat;
    private final HBox hbResultat, hbButons, hbFiltre;
    private final VBox vbContigut;
    private final Label resultat, resultatValor;
    private final TextField textFiltre;
    private final ChoiceBox opcioFiltre;
    private ObservableList<String> llistaFiltre;
    private final Button butoActualitzar, butoAfegir, butoModificar, butoEliminar, butoFiltre;    
    private ArrayList<Usuari> llista_usuaris;
    private ArrayList<Llibre> llista_llibres;
    private ObservableList<Usuari> data_usuaris;
    private ObservableList<Llibre> data_llibres;
    private FilteredList<Usuari> data_filtrada_usuaris;
    private FilteredList<Llibre> data_filtrada_llibres;
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
                data_usuaris = null;

                // Obtenim el llistat_d'elements
                llista_usuaris = AccionsClient.obtenirLlistat(tipusLlista);         

                // El transformem en una ObservableList
                data_usuaris = FXCollections.observableArrayList(llista_usuaris); 
                
                // Obtenim el nom dels camps per columnes, filtre,...
                mapaNomCamps = Usuari.mapaNomCamps;
                break;
            case ADMINISTRADOR_CASE:
                // Esborrem l'informacio per carregar-la de nou
                data_usuaris = null;

                // Obtenim el llistat_d'elements
                llista_usuaris = AccionsClient.obtenirLlistat(tipusLlista);         

                // El transformem en una ObservableList
                data_usuaris = FXCollections.observableArrayList(llista_usuaris); 

                // Obtenim el nom dels camps per columnes, filtre,...
                mapaNomCamps = Usuari.mapaNomCamps;
                break;       
            case LLIBRE_CASE:
                // Esborrem l'informacio per carregar-la de nou
                data_llibres = null;

                // Obtenim el llistat_d'elements
                llista_llibres = AccionsClient.obtenirLlistat(tipusLlista);         

                // El transformem en una ObservableList
                data_llibres = FXCollections.observableArrayList(llista_llibres); 

                // Obtenim el nom dels camps per columnes, filtre,...
                mapaNomCamps = Llibre.mapaNomCamps;
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
                        mapaNomCamps.get("numero_soci"),
                        mapaNomCamps.get("data_alta"),
                        mapaNomCamps.get("nom"), 
                        mapaNomCamps.get("cognoms"), 
                        mapaNomCamps.get("data_naixement"),
                        mapaNomCamps.get("direccio"), 
                        mapaNomCamps.get("pais"),
                        mapaNomCamps.get("telefon"), 
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
                mapaNomCamps = Usuari.mapaNomCamps;
                System.out.println(mapaNomCamps);
                llistaFiltre = FXCollections.observableArrayList(
                        mapaNomCamps.get("DNI"), 
                        mapaNomCamps.get("user_name"),
                        mapaNomCamps.get("numero_soci"),
                        mapaNomCamps.get("data_alta"),
                        mapaNomCamps.get("nom"), 
                        mapaNomCamps.get("cognoms"), 
                        mapaNomCamps.get("data_naixement"),
                        mapaNomCamps.get("direccio"), 
                        mapaNomCamps.get("pais"),
                        mapaNomCamps.get("telefon"), 
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
            case LLIBRE_CASE:
                mapaNomCamps = Llibre.mapaNomCamps;
                System.out.println(mapaNomCamps);
                llistaFiltre = FXCollections.observableArrayList(
                        mapaNomCamps.get("nom"), 
                        mapaNomCamps.get("autor"),
                        mapaNomCamps.get("any_publicacio"),
                        mapaNomCamps.get("tipus"),
                        mapaNomCamps.get("data_alta"), 
                        mapaNomCamps.get("reservat_dni"), 
                        mapaNomCamps.get("admin_alta"),
                        mapaNomCamps.get("caratula"), 
                        mapaNomCamps.get("descripcio"),
                        mapaNomCamps.get("valoracio"), 
                        mapaNomCamps.get("vots")
                        );
                // Introduim les opcions dins les opcions del filtre
                opcioFiltre.setItems(llistaFiltre);
                // Deixem marcada l'opcio del nom del administrador
                opcioFiltre.setValue(mapaNomCamps.get("nom"));
                // Establim que la label dels resultats posi Administradors
                resultat.setText("Llibres:");
                break;
        }
    }
    
    public void aplicarFiltre(){
        // Passem el string a trobar a minuscules
        String paraulaFiltre = textFiltre.getText().toLowerCase();
        
        // Obtenim a quin camp volem trobar la paraula
        String opcioFiltreTxt = opcioFiltre.getSelectionModel().getSelectedItem().toString();
        if(tipusLlista.equals(ADMINISTRADOR_CASE) || tipusLlista.equals(USUARI_CASE)){            
        
            //data_filtrada_llibres
            // Filtrem les dades
            data_filtrada_usuaris = new FilteredList<>(data_usuaris, b -> true);        
            data_filtrada_usuaris.setPredicate(usuariFiltrat -> {
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
                        if(usuariFiltrat.getUser_name().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("numero_soci"))){
                        if(usuariFiltrat.getNum_soci().toLowerCase().indexOf(paraulaFiltre) > -1){
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
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("cognoms"))){
                        if(usuariFiltrat.getCognoms().toLowerCase().indexOf(paraulaFiltre) > -1){
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
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("telefon"))){
                        if(usuariFiltrat.getTelefon().toLowerCase().indexOf(paraulaFiltre) > -1){
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
                    // No s'ha trobat res
                    return false;
                });

            // Introduim l'informació filtrada (SortedList) a la taula
            taulaLlistat.setItems(data_filtrada_usuaris);
            // Obtenim el numero total de registres i la fiquem al label
            resultatValor.setText(String.valueOf(data_filtrada_usuaris.size()));
        }else if(tipusLlista.equals(LLIBRE_CASE)){            
        
            //data_filtrada_llibres
            // Filtrem les dades
            data_filtrada_llibres = new FilteredList<>(data_llibres, b -> true);        
            data_filtrada_llibres.setPredicate(llibreFiltrat -> {
                    // Si no hi ha paraula a filtrar/buscar mostrem tot
                    if(paraulaFiltre.isEmpty() || paraulaFiltre == null){
                        return true;
                    }

                    if(opcioFiltreTxt.equals(mapaNomCamps.get("nom"))){
                        if(llibreFiltrat.getNom().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("autor"))){
                        if(llibreFiltrat.getAutor().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("any_publicacio"))){
                        if(llibreFiltrat.getAny_Publicacio().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("tipus"))){
                        if(llibreFiltrat.getTipus().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("data_alta"))){
                        if(llibreFiltrat.getData_alta().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("reservat_dni"))){
                        if(llibreFiltrat.getReservat_DNI().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("admin_alta"))){
                        if(llibreFiltrat.getAdmin_alta().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("caratula"))){
                        if(llibreFiltrat.getCaratula().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("descripcio"))){
                        if(llibreFiltrat.getDescripcio().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("valoracio"))){
                        if(llibreFiltrat.getValoracio().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }else if(opcioFiltreTxt.equals(mapaNomCamps.get("vots"))){
                        if(llibreFiltrat.getVots().toLowerCase().indexOf(paraulaFiltre) > -1){
                            return true;
                        } else {
                            return false;
                        }                    
                    }
                    // No s'ha trobat res
                    return false;
                });

            // Introduim l'informació filtrada (SortedList) a la taula
            taulaLlistat.setItems(data_filtrada_llibres);
            // Obtenim el numero total de registres i la fiquem al label
            resultatValor.setText(String.valueOf(data_filtrada_llibres.size()));
        }
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
        
        TableColumn<Usuari,String> col_Nom = new TableColumn<Usuari,String>(mapaNomCamps.get("nom"));        
        col_Nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().nom();
        }});
        //col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Usuari,String> col_Cognom = new TableColumn<Usuari,String>(mapaNomCamps.get("cognoms"));
        col_Cognom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().cognoms();
        }});

        TableColumn<Usuari,String> col_NomUsuari = new TableColumn<Usuari,String>(mapaNomCamps.get("user_name"));
        col_NomUsuari.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().user_name();
        }});

        TableColumn<Usuari,String> col_NumSoci = new TableColumn<Usuari,String>(mapaNomCamps.get("numero_soci"));
        col_NumSoci.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().numero_soci();
        }});
        
        TableColumn<Usuari,String> col_Direccio = new TableColumn<Usuari,String>(mapaNomCamps.get("direccio"));
        col_Direccio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().direccio();
        }});      

        TableColumn<Usuari,String> col_Telefon = new TableColumn<Usuari,String>(mapaNomCamps.get("telefon"));
        col_Telefon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().telefon();
        }});

        TableColumn<Usuari,String> col_DNI = new TableColumn<Usuari,String>(mapaNomCamps.get("DNI"));
        col_DNI.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().dni();
        }});

        TableColumn<Usuari,String> col_Correu = new TableColumn<Usuari,String>(mapaNomCamps.get("correu"));
        col_Correu.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().correu();
        }});

        TableColumn<Usuari,String> col_Pais = new TableColumn<Usuari,String>(mapaNomCamps.get("pais"));
        col_Pais.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().pais();
        }}); 
        
        TableColumn<Usuari,String> col_DataNaixament = new TableColumn<Usuari,String>(mapaNomCamps.get("data_naixement"));
        col_DataNaixament.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().data_naixement();
        }});  
        
        TableColumn<Usuari,String> col_DataAlta = new TableColumn<Usuari,String>(mapaNomCamps.get("data_alta"));
        col_DataAlta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().data_alta();
        }});  
        
        TableColumn<Usuari,String> col_AdminAlta = new TableColumn<Usuari,String>(mapaNomCamps.get("admin_alta"));
        col_AdminAlta.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().admin_alta();
        }}); 
        
        // Per crear totes les columnes de la taula llibres
        
        TableColumn<Llibre,String> col_NomLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("nom"));
        col_NomLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().nom();
        }}); 
        
        TableColumn<Llibre,String> col_AutorLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("autor"));
        col_AutorLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().autor();
        }});
        
        TableColumn<Llibre,String> col_AnyPublicacioLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("any_publicacio"));
        col_AnyPublicacioLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().any_publicacio();
        }});
        
        TableColumn<Llibre,String> col_TipusLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("tipus"));
        col_TipusLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().tipus();
        }});
        
        TableColumn<Llibre,String> col_DataAltaLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("data_alta"));
        col_DataAltaLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().data_alta();
        }});
        
        TableColumn<Llibre,String> col_ReservatDNILlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("reservat_dni"));
        col_ReservatDNILlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().reservat_dni();
        }});
        
        TableColumn<Llibre,String> col_AdminAltaLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("admin_alta"));
        col_AdminAltaLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().admin_alta();
        }});
        
        TableColumn<Llibre,String> col_CaratulaLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("caratula"));
        col_CaratulaLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().caratula();
        }});
        
        TableColumn<Llibre,String> col_DescripcioLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("descripcio"));
        col_DescripcioLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().descripcio();
        }});
        
        TableColumn<Llibre,String> col_ValoracioLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("valoracio"));
        col_ValoracioLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().valoracio();
        }});
        
        TableColumn<Llibre,String> col_VotsLlibre = new TableColumn<Llibre,String>(mapaNomCamps.get("vots"));
        col_VotsLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Llibre,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Llibre,String> p) {
                return p.getValue().vots();
        }});
        
        // Afegim les columnes a la taula    
        switch(tipusLlista){
            case USUARI_CASE:
                taulaLlistat.getColumns().addAll(
                        col_NumSoci,
                        col_NomUsuari,
                        col_DNI, 
                        col_DataAlta, 
                        col_Nom, 
                        col_Cognom, 
                        col_DataNaixament, 
                        col_Direccio,  
                        col_Pais, 
                        col_Telefon, 
                        col_Correu, 
                        col_AdminAlta 
                        );    
                break;
            case ADMINISTRADOR_CASE:
                taulaLlistat.getColumns().addAll(
                        col_NumSoci,
                        col_NomUsuari,
                        col_DNI, 
                        col_DataAlta, 
                        col_Nom, 
                        col_Cognom, 
                        col_DataNaixament, 
                        col_Direccio,  
                        col_Pais, 
                        col_Telefon, 
                        col_Correu, 
                        col_AdminAlta 
                        );    
                break;
            case LLIBRE_CASE:
                taulaLlistat.getColumns().addAll(
                        col_NomLlibre,
                        col_AutorLlibre,
                        col_AnyPublicacioLlibre, 
                        col_TipusLlibre, 
                        col_DataAltaLlibre, 
                        col_ReservatDNILlibre, 
                        col_AdminAltaLlibre, 
                        col_CaratulaLlibre,  
                        col_DescripcioLlibre, 
                        col_ValoracioLlibre, 
                        col_VotsLlibre
                        );    
                break;
        }
    }
}
