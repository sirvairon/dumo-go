/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import dumogo.Comentari;
import dumogo.Llibre;
import dumogo.PestanyaLlistat;
import dumogo.Usuari;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class LlibreVistaController implements Initializable {

    private Stage stage, stageComentari;
    private Llibre llibre;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private static final String STRING_RESERVA_LLIURE = "LLIURE";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    private ArrayList<Comentari> llista_comentaris;
    private ObservableList<Comentari> data_comentaris;
    private FilteredList<Comentari> data_filtrada_comentaris;
    private Map<String, String> mapaNomCamps;
    private ObservableList<String> llistaFiltre;
    private Object element_temp;
    private Date tempsUltimClick;
    private ComentariController comentariControlador;
    
    @FXML
    private VBox raiz;    
    @FXML
    private Label labelTitol; 
    @FXML
    private Label labelAutor; 
    @FXML
    private Label labelAnyPublicacio;    
    @FXML
    private Label labelTipus;    
    @FXML
    private Label labelValoracio;       
    @FXML
    private Label labelVots;        
    @FXML
    private Label labelDescripcio;
    @FXML
    private Label resultat;   
    @FXML
    private Label resultatValor;
    @FXML
    private TextField textFieldAdminAlta;       
    @FXML
    private TextField textFieldReservatDNI;
    @FXML
    private TextField textFiltre;
    @FXML
    private TextArea textAreaComentari;
    @FXML
    private DatePicker datePickerDataAlta;               
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio;    
    @FXML
    private Button butoAccio2;
    @FXML
    private Button butoAccio3;
    @FXML
    private ImageView imageViewCaratula;
    @FXML
    private TableView tableViewComentaris;
    @FXML
    private ChoiceBox choiceBoxValoracio;
    @FXML
    private ChoiceBox choiceBoxFiltre;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
        
        // Configurem el EventHandler en cas de fer click a la taula
        tableViewComentaris.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    clickTaulaComentaris();
                } catch (IOException ex) {
                    Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        // Establim al buto de l'accio, l'accio que volem fer (fer reserva)
        butoAccio.setText("Reservar");
        // Configurem el EventHandler en cas de fer click al boto de fer reserva
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                //ferReserva();
                ferReserva();
            }
        });
        // Establim al buto de l'accio, l'accio que volem fer (fer comentari)
        butoAccio2.setText("Comentar");
        // Configurem el EventHandler en cas de fer click al boto de fer comentari
        butoAccio2.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                //ferReserva();
                afegirComentari();
            }
        });
        // Establim al buto de l'accio, l'accio que volem fer (fer comentari)
        butoAccio3.setText("Votar");
        // Configurem el EventHandler en cas de fer click al boto de fer comentari
        butoAccio3.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                //ferReserva();
                ferVotacio();
            }
        });
        
        // Apliquem un listener per si canvia el camp del text del filtre que apliqui el filtre
        textFiltre.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltre();
        });
        
        ObservableList<String> valoracio = FXCollections.observableArrayList("1","2","3","4","5");
        choiceBoxValoracio.setItems(valoracio);
    }    
    
    private void omplirDades(Llibre llibre){
        // Agafem el llibre i el guardem
        this.llibre = llibre;
        // Omplim els camps de pantalla amb l'usuari
        labelTitol.setText(llibre.getNom());        
        labelAutor.setText(llibre.getAutor());
        labelAnyPublicacio.setText(llibre.getAny_Publicacio());
        labelTipus.setText(llibre.getTipus());
        labelValoracio.setText(llibre.getValoracio());
        labelVots.setText(llibre.getVots());
        labelDescripcio.setText(llibre.getDescripcio());
        //textFieldAdminAlta.setText(llibre.getAdmin_alta());
        //datePickerDataAlta.setValue(LocalDate.parse(llibre.getData_alta()));
        textFieldReservatDNI.setText(llibre.getUser_name());
        
        String caratula = llibre.getCaratula();
        if(!caratula.equals("null")){
            // Per la caratula tenim que decodejar l'string
            BufferedImage buffer = AccionsClient.decodeToImage(caratula);
            //BufferedImage buffer = decodeToImage(caratula);
            Image imatge = SwingFXUtils.toFXImage(buffer, null);
            imageViewCaratula.setImage(imatge);
        }
        
        
        // Per obtenir els comentaris
        boolean ok = obtenirComentaris();
        // Creem el filtre
        crearFiltre(); 
        if(ok){
            // Apliquem el filtre
            aplicarFiltre();
        }
        // Creem les columnes de la taula
        columnesComentaris();
       
    }
    
    private void esborrarDades(){
        // Esborrem el llibre de memoria
        this.llibre = null;
        // Esborrem els camps de pantalla
        labelTitol.setText("");        
        labelAutor.setText("");
        labelAnyPublicacio.setText("");
        labelTipus.setText("");
        labelValoracio.setText("");
        labelVots.setText("");
        labelDescripcio.setText("");
        //textFieldAdminAlta.setText("");
        //datePickerDataAlta.setValue(LocalDate.now());
        textFieldReservatDNI.setText("");
        
        //data_comentaris.forEach(tableViewComentaris.getItems()::remove);
        if(data_comentaris != null){
            data_comentaris.forEach(tableViewComentaris.getItems()::remove);
        }
    }
    
    public void mostrarLlibre(Llibre ll){
        esborrarDades();
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(ll);
        
        // Si la reserva esta 'LLIURE' mostrem el boto per poder reservar-lo
        if(llibre.getUser_name().equals(STRING_RESERVA_LLIURE)){
            butoAccio.setDisable(false);
        }else {
            butoAccio.setDisable(true);
        }
    }
    
    private void ferReserva(){
        try {
            // Fem l'accio de fer la reserva
            msg_in = AccionsClient.ferReserva(llibre);
            // Obtenim el codi de resposta
            codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
            // Obtenim el sinificat del codi de resposta
            significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
            // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
            alerta.setTitle("Reserva llibre");
            alerta.setHeaderText(significat_codi_resposta);
            // Sessio caducada
            if(codi_resposta.equals("10")){
                sessioCaducada();
            // Rserva correcta
            }else if(codi_resposta.equals("2100")){
                alerta.setAlertType(Alert.AlertType.INFORMATION);
                alerta.showAndWait();
                raiz.getScene().getWindow().hide();
            // Error al fer la reserva
            }else{
                llibre = null;
                alerta.setAlertType(Alert.AlertType.ERROR);
                alerta.show();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void afegirComentari(){
        try {
            Comentari comentari;
            
            // Obtenim el comentari, la data actual i l'usuari
            SimpleStringProperty id_llibre = new SimpleStringProperty(llibre.getID());
            SimpleStringProperty text_comentari = new SimpleStringProperty(textAreaComentari.getText());
            SimpleStringProperty data_creacio = new SimpleStringProperty(LocalDate.now().toString());
            SimpleStringProperty user_name = new SimpleStringProperty(AccionsClient.getNom_user_actual());
            SimpleStringProperty id_comentari = new SimpleStringProperty("");
            
            if(!text_comentari.equals("") || text_comentari != null){
                comentari = new Comentari(id_comentari, id_llibre, user_name, text_comentari, data_creacio);               
                // Fem l'accio de fer la reserva
                msg_in = AccionsClient.afegirComentari(comentari);
                // Obtenim el codi de resposta
                codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                // Obtenim el sinificat del codi de resposta
                significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                alerta.setTitle("Afegir comentari");
                alerta.setHeaderText(significat_codi_resposta);
                // Sessio caducada
                if(codi_resposta.equals("10")){
                    sessioCaducada();
                // Rserva correcta
                }else if(codi_resposta.equals("2700")){
                    alerta.setAlertType(Alert.AlertType.INFORMATION);
                    alerta.showAndWait();
                    raiz.getScene().getWindow().hide();
                // Error al fer la reserva
                }else{
                    llibre = null;
                    alerta.setAlertType(Alert.AlertType.ERROR);
                    alerta.show();
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ferVotacio(){
        try {            
            // Obtenim el comentari, la data actual i l'usuari
            String puntuacio = choiceBoxValoracio.getSelectionModel().getSelectedItem().toString();
            System.out.println("puntuacio: " + puntuacio);
            
            if(!puntuacio.equals("") || puntuacio != null){
                // Fem l'accio de fer la reserva
                msg_in = AccionsClient.puntuarLlibre(llibre, puntuacio);
                // Obtenim el codi de resposta
                codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                // Obtenim el sinificat del codi de resposta
                significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                alerta.setTitle("Valorar llibre");
                alerta.setHeaderText(significat_codi_resposta);
                // Sessio caducada
                if(codi_resposta.equals("10")){
                    sessioCaducada();
                // Rserva correcta
                }else if(codi_resposta.equals("1900")){
                    alerta.setAlertType(Alert.AlertType.INFORMATION);
                    alerta.showAndWait();
                    raiz.getScene().getWindow().hide();
                // Error al fer la reserva
                }else{
                    llibre = null;
                    alerta.setAlertType(Alert.AlertType.ERROR);
                    alerta.show();
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
            alerta.setTitle("Valorar llibre");
            alerta.setHeaderText("Has de donar una valoració");
            alerta.setAlertType(Alert.AlertType.INFORMATION);
            alerta.show();
        }
    }

    
    private void veureComentari(Comentari c) throws IOException{
        // En cas de que no s'hagi creat el stage (finestra oberta) el creem
        if (stageComentari == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Comentari.fxml"));
            Parent root = (Parent) loader.load();
            comentariControlador = loader.getController();
            stageComentari = new Stage();
            stageComentari.setScene(new Scene(root));
            Image icon = new Image("/resources/usuari_icon_neg_16.png");
            stageComentari.getIcons().add(icon);
            stageComentari.setTitle("Comentari");
            stageComentari.setResizable(false);

            // Quan es tanqui esborrem el stage de memòria                    
            stageComentari.setOnHiding(we -> stageComentari = null);

            // Mostrem la finestra del usuari a editar
            stageComentari.show();   

        // En cas de ja estigui creat el stage (finestra oberta) el portem al davant
        }else{
            stageComentari.toFront();
        }
        // Actualitzem el controlador (finestra usuari) per mijtà del mètode dins del controlador
        comentariControlador.mostrarComentari(c);
    }
    
    private void clickTaulaComentaris() throws IOException {
        // Mirem l'element clickat
        Object element = tableViewComentaris.getSelectionModel().getSelectedItem();
        // Si està buit tornem
        if (element == null) return;
        // Si fem click un altre vegada comparem si es el mateix element clickat i generem un temps desde aquest click
        if(element != element_temp){
            element_temp = element;
            tempsUltimClick = new Date();
        // Si es el mateix element clickat i generem un altre temps desde aquest click i comparem temps entre clicks
        } else if(element.equals(element_temp)) {
            Date now = new Date();
            long diff = now.getTime() - tempsUltimClick.getTime();
            // Si ha sigut rapid (300 millis) es doble click
            if (diff < 300){ 
                veureComentari((Comentari)element);
            } else {
                // Si no, actualitzem el temps
                tempsUltimClick = new Date();
            }
        }else{
        }
    }
    
    private boolean obtenirComentaris(){
        try {
            // Esborrem l'informacio per carregar-la de nou
            data_comentaris = null;
            
            // Obtenim el llistat_d'elements
            llista_comentaris = AccionsClient.obtenirLlistatComentaris(llibre);          

            // Obtenim el nom dels camps per columnes, filtre,...
            mapaNomCamps = Comentari.mapaNomCamps;

            // Si la llista torna buida                    
            if(llista_comentaris.get(0).getID().equals("null")){ 
                tableViewComentaris.setPlaceholder(new Label("No hi han comentaris"));
                return false;
            }else{
                // El transformem en una ObservableList
                data_comentaris = FXCollections.observableArrayList(llista_comentaris); 
            }
            
            return true;
    
        } catch (NullPointerException ex) {
            alerta.setTitle("Error al carregar dades");
            alerta.setContentText("");
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText("Error al carregar les dades dels comentaris");
            alerta.show();
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LlibreVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    private void crearFiltre(){
        mapaNomCamps = Comentari.mapaNomCamps;
        llistaFiltre = FXCollections.observableArrayList(
                //mapaNomCamps.get("id"),
                //mapaNomCamps.get("id_llibre"),
                mapaNomCamps.get("user_name"),
                mapaNomCamps.get("comentari"), 
                mapaNomCamps.get("data")                        
                );
        // Introduim les opcions dins les opcions del filtre
        choiceBoxFiltre.setItems(llistaFiltre);
        // Deixem marcada l'opcio de la data del comentari
        choiceBoxFiltre.setValue(mapaNomCamps.get("data"));
        // Establim que la label dels resultats posi Comentaris
        resultat.setText("Comentaris:");
    }
    
    private void aplicarFiltre(){
        // Passem el string a trobar a minuscules
        String paraulaFiltre = textFiltre.getText().toLowerCase();
        
        // Obtenim a quin camp volem trobar la paraula
        String opcioFiltreTxt = choiceBoxFiltre.getSelectionModel().getSelectedItem().toString();
        
        // Filtrem les dades
        data_filtrada_comentaris = new FilteredList<>(data_comentaris, b -> true);        
        data_filtrada_comentaris.setPredicate(comentariFiltrat -> {
                // Si no hi ha paraula a filtrar/buscar mostrem tot
                if(paraulaFiltre.isEmpty() || paraulaFiltre == null){
                    return true;
                }

                if(opcioFiltreTxt.equals(mapaNomCamps.get("id"))){
                    if(comentariFiltrat.getID().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("id_llibre"))){
                    if(comentariFiltrat.getID_llibre().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("user_name"))){
                    if(comentariFiltrat.getUser_name().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("comentari"))){
                    if(comentariFiltrat.getComentari().toLowerCase().indexOf(paraulaFiltre) > -1){
                        return true;
                    } else {
                        return false;
                    }                    
                }else if(opcioFiltreTxt.equals(mapaNomCamps.get("data"))){
                    if(comentariFiltrat.getData().toLowerCase().indexOf(paraulaFiltre) > -1){
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
        SortedList<Comentari> sortedData = new SortedList<>(data_filtrada_comentaris);

        // Ara que tenim una SortedList separada, hem de vincular la classificació d'aquesta llista a la TableView. 
        // Enllaçem el comparador de la llista ordenada (SortedList) al comparador de la taula (taulaLlistat)
        sortedData.comparatorProperty().bind(tableViewComentaris.comparatorProperty());

        // Fiquem la llista ordenada (i filtrada) a les dades de la taula (taulaLlistat)
        tableViewComentaris.setItems(sortedData);

        // Obtenim el numero total de registres i la fiquem al label
        resultatValor.setText(String.valueOf(data_filtrada_comentaris.size()));
        
    }
    
    private void columnesComentaris(){
        // Per crear totes les columnes de la taula usuaris

        tableViewComentaris.getColumns().clear();
                
        TableColumn<Comentari,String> col_ID = new TableColumn<Comentari,String>(mapaNomCamps.get("id"));        
        col_ID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comentari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Comentari,String> p) {
                return p.getValue().id();
        }});

        TableColumn<Comentari,String> col_IDLlibre = new TableColumn<Comentari,String>(mapaNomCamps.get("id_llibre"));
        col_IDLlibre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comentari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Comentari,String> p) {
                return p.getValue().id_llibre();
        }});

        TableColumn<Comentari,String> col_NomUsuari = new TableColumn<Comentari,String>(mapaNomCamps.get("user_name"));
        col_NomUsuari.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comentari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Comentari,String> p) {
                return p.getValue().user_name();
        }});

        TableColumn<Comentari,String> col_Commentari = new TableColumn<Comentari,String>(mapaNomCamps.get("comentari"));
        col_Commentari.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comentari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Comentari,String> p) {
                return p.getValue().comentari();
        }});
        
        TableColumn<Comentari,String> col_Data = new TableColumn<Comentari,String>(mapaNomCamps.get("data"));
        col_Data.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Comentari,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Comentari,String> p) {
                return p.getValue().data();
        }});
        
        tableViewComentaris.getColumns().addAll(
            col_Data,
            col_NomUsuari, 
            col_Commentari            
        );         
        
        tableViewComentaris.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
   
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    public void sessioCaducada() throws IOException {
        // En cas de retornar codi 10 (sessio caducada)
        // Obtenim el text de l'error
        //significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        // Creem l'alerta que farem servir per informar d'errors o accions correctes
        alerta = new Alert(Alert.AlertType.NONE);
        // Per poder aplicar estil a les alertes hem de aplicar-les al dialogpane
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
        significat_codi_resposta = "Sessió caducada";
        // Establim alerta
        alerta.setTitle("Sessió caducada");
        alerta.setContentText("");
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.setHeaderText(significat_codi_resposta);
        // La mostrem i esperem click
        alerta.showAndWait();
        // Tanquem finestra
        //raiz.getScene().getWindow().hide();
        //Stage stage1 = stage.getScene().getWindow();
        //stage1.close();
        stage.getScene().getWindow();
        // Obrim finestra de login
        Parent parent = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));
        Stage stage1 = new Stage();
        Scene scene = new Scene(parent);
        stage1.setScene(scene);
        Image icon = new Image("/resources/dumogo_icon_neg_35.png");
        stage1.getIcons().add(icon);
        stage1.setTitle("Dumo-Go");
        stage1.setResizable(false);
        stage1.show();    
    }
    }   