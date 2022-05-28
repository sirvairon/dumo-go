/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package z_borrar;

import dumogo.AccionsClient;
import dumogo.Administrador;
import dumogo.Llibre;
import dumogo.Usuari;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author marcd
 */
// public final class PestanyaGrid extends Tab {
public final class PestanyaGrid extends Tab {

    private final static String USUARI_CASE = "usuaris";
    private final static String ADMINISTRADOR_CASE = "administradors";
    private final static String LLIBRE_CASE = "llibres";
    private final String tipusLlista;
    
    //private final TableView taulaLlistat;
    private final HBox hbResultat, hbButons, hbFiltre;
    private final VBox vbContigut;
    private final Label resultat, resultatValor;
    private final TextField textFiltre;
    private final ChoiceBox opcioFiltre;
    private ObservableList<String> llistaFiltre;
    private final Button butoActualitzar, butoAfegir, butoModificar, butoEliminar, butoFiltre;  
    
    private ArrayList<Usuari> llista_usuaris;
    private ArrayList<Administrador> llista_administradors;
    private ArrayList<Llibre> llista_llibres;
    
    private ObservableList<Usuari> data_usuaris;
    private ObservableList<Administrador> data_administradors;
    private ObservableList<Llibre> data_llibres;
    
    private FilteredList<Usuari> data_filtrada_usuaris;
    private FilteredList<Administrador> data_filtrada_administradors;
    private FilteredList<Llibre> data_filtrada_llibres;
    
    private Map<String, String> mapaNomCamps;
    
    private Alert alerta;
    
    private static final double ELEMENT_SIZE = 100;
    private static final double GAP = ELEMENT_SIZE / 10;

    private TilePane tilePane;
    private Group display;
    private int nRows;
    private int nCols;
        
    
    public PestanyaGrid(String nomLlista, String tipusLlista) throws ClassNotFoundException {
        //super(nomLlista);
        
        this.tipusLlista = tipusLlista;        
        this.tilePane = new TilePane();
        this.display = new Group(tilePane);        
        this.nRows = 4; 
        this.nCols = 4;
        
        tilePane.setHgap(GAP);
        tilePane.setVgap(GAP);
        setColumns(nCols);
        setRows(nRows);
        
        
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
        /*
        textFiltre.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltre();
        });
        */
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
        vbContigut.setVgrow(tilePane, Priority.ALWAYS);
        vbContigut.getChildren().addAll(hbButons, tilePane, hbResultat);
        
        // Afegim el VBox que conte tot a la pestanya l'arrel
        this.setContent(vbContigut);
        
        // Configurem l'alerta en cas d'error o informar
        alerta = new Alert(Alert.AlertType.NONE);
        alerta.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        
        
        
        // Obtenim les dades
        obtenirDades();
        // Creem el filtre
        crearFiltre(); 
        // Apliquem el filtre
        aplicarFiltre();
        // Creem les columnes de la taula
        //crearColumnesTaula();   
        

    }        

    public void setColumns(int newColumns) {
        nCols = newColumns;
        tilePane.setPrefColumns(nCols);
        //createElements();
    }

    public void setRows(int newRows) {
        nRows = newRows;
        tilePane.setPrefRows(nRows);
        //createElements();
    }

    public Group getDisplay() {
        return display;
    }

    private void createElements() {
        tilePane.getChildren().clear();
        for (int i = 0; i < nCols; i++) {
            for (int j = 0; j < nRows; j++) {
                tilePane.getChildren().add(createElement());
            }
        }
    }

    private Rectangle createElement() {
        Rectangle rectangle = new Rectangle(ELEMENT_SIZE, ELEMENT_SIZE);
        rectangle.setStroke(Color.ORANGE);
        rectangle.setFill(Color.STEELBLUE);

        return rectangle;
    }   
  
    public TilePane getTilePane() {
        return tilePane;
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
    
    private void crearFiltre(){
        // Actualitzem les opcions del filtre en funcio del tipus de llista i mostrem en la label el tipus de resultats tornats
        switch(tipusLlista){
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
        if(tipusLlista.equals(LLIBRE_CASE)){            
        
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
                        if(llibreFiltrat.getUser_name().toLowerCase().indexOf(paraulaFiltre) > -1){
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

            // Normalment, quan fem click al header de la columna canvia l'ordre de la TableView pero como ara te una FilteredList
            // no es pot modificar, per lo que no es pot canviar l'ordre. Hem de ficar-la dins una SortedList per porder ordenar-la.

            // Fiquem la llistra filtrada (FilteredList) dins la llista ordenada (SortedList)
            SortedList<Llibre> sortedData = new SortedList<>(data_filtrada_llibres);
		
            // Ara que tenim una SortedList separada, hem de vincular la classificació d'aquesta llista a la TableView. 
            // Enllaçem el comparador de la llista ordenada (SortedList) al comparador de la taula (taulaLlistat)
            //sortedData.comparatorProperty().bind(taulaLlistat.comparatorProperty());
		
            // Fiquem la llista ordenada (i filtrada) a les dades de la taula (taulaLlistat)
            //taulaLlistat.setItems(sortedData);

            //GridPane grid = new GridPane();
            //grid.
            //tilePane.getChildren().clear();
            //ObservableList list = tilePane.getChildren();
            //tilePane.getChildren() = list.get(nRows);
            //tilePane.getChildren().addAll(data_llibres);
            
            
            
            
            int total = data_filtrada_llibres.size();
            Label text;
            for (int i = 0; i < total; i++) {
                VBox vbox = vBoxLlibre(data_filtrada_llibres.get(i));
                //text = new Label();
                //text.setText(data_filtrada_llibres.get(i).getNom());
                tilePane.getChildren().add(vbox);
            }
            
            // Obtenim el numero total de registres i la fiquem al label
            resultatValor.setText(String.valueOf(data_filtrada_llibres.size()));
        }
    }
    
    /*
    private FilteredList<Node> inRow(RowConstraints row) {
        final int index = grid.getRowConstraints().indexOf(row);
        return grid.getChildren()
            .filtered(node -> {
              final Integer rowIndex = GridPane.getRowIndex(node);
              return rowIndex != null && index == GridPane.getRowIndex(node);
            });
        }    
    
    */
    
    private void obtenirDades() throws ClassNotFoundException{
        // En cas de retornar null hi hagut problemes al obtenir el llistat
        try{
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
                    data_administradors = null;

                    // Obtenim el llistat_d'elements
                    llista_administradors = AccionsClient.obtenirLlistat(tipusLlista);         

                    // El transformem en una ObservableList
                    data_administradors = FXCollections.observableArrayList(llista_administradors); 

                    // Obtenim el nom dels camps per columnes, filtre,...
                    mapaNomCamps = Administrador.mapaNomCamps;
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
        } catch (NullPointerException ex) {
            alerta.setTitle("Error al carregar dades");
            alerta.setContentText("");
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText("Error al carregar les dades dels " + tipusLlista);
            alerta.show();
            Logger.getLogger(PestanyaGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       

    private VBox vBoxLlibre(Llibre ll){
        VBox vbox_llibre = new VBox();
        vbox_llibre.getStyleClass().add("vBoxLlibre");
        Label titol = new Label(ll.getNom());
        Label autor = new Label(ll.getAutor());
        Label vots = new Label(ll.getVots());
        Label valoracio = new Label(ll.getValoracio());
        Label disponibilitat = new Label(ll.getUser_name());        
        Image i = new Image("/resources/book.jpg");
        ImageView caratula = new ImageView(i);
        
        vbox_llibre.getChildren().addAll(caratula,titol, autor, vots, disponibilitat);
        
        vbox_llibre.setOnMouseClicked(e -> System.out.println(ll.getNom()));
        
        return vbox_llibre;
    }
    
    public void actualitzarDades() throws ClassNotFoundException {        
        // Obtenim les dades
        obtenirDades();
        // Filtrem les dades
        aplicarFiltre();        
    }
    
    /*
    private void crearColumnesTaula() {               
        
        //switch(tipusLlista){
        //    case USUARI_CASE:
        
        // Per crear totes les columnes de la taula usuaris
        
        TableColumn<Usuari,String> col_Nom = new TableColumn<Usuari,String>(mapaNomCamps.get("nom"));        
        col_Nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuari,String> p) {
                return p.getValue().nom();
        }});

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
        
        // Per crear totes les columnes de la taula d'administradors
        
        TableColumn<Administrador,String> col_NomAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("nom"));        
        col_NomAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().nom();
        }});

        TableColumn<Administrador,String> col_CognomAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("cognoms"));
        col_CognomAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().cognoms();
        }});

        TableColumn<Administrador,String> col_Nom_Admin = new TableColumn<Administrador,String>(mapaNomCamps.get("nom_admin"));
        col_Nom_Admin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().nom_admin();
        }});     

        TableColumn<Administrador,String> col_TelefonAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("telefon"));
        col_TelefonAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().telefon();
        }});

        TableColumn<Administrador,String> col_DNIAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("DNI"));
        col_DNIAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().dni();
        }});

        TableColumn<Administrador,String> col_CorreuAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("correu"));
        col_CorreuAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().correu();
        }});

        TableColumn<Administrador,String> col_PaisAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("pais"));
        col_PaisAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().pais();
        }}); 
        
        TableColumn<Administrador,String> col_DataNaixamentAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("data_naixement"));
        col_DataNaixamentAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().data_naixement();
        }});   
        
        TableColumn<Administrador,String> col_AdminAltaAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("admin_alta"));
        col_AdminAltaAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().admin_alta();
        }});
        
        TableColumn<Administrador,String> col_DireccioAdmin = new TableColumn<Administrador,String>(mapaNomCamps.get("direccio"));
        col_DireccioAdmin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador,String> p) {
                return p.getValue().direccio();
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
                        col_Nom_Admin,
                        col_DNIAdmin, 
                        col_NomAdmin, 
                        col_CognomAdmin, 
                        col_DataNaixamentAdmin, 
                        col_DireccioAdmin,  
                        col_PaisAdmin, 
                        col_TelefonAdmin, 
                        col_CorreuAdmin, 
                        col_AdminAltaAdmin 
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
        taulaLlistat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    */
    
}