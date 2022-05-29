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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private Stage stage;
    private Llibre llibre;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private static final String STRING_RESERVA_LLIURE = "LLIURE";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    
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
    private DatePicker datePickerDataAlta;               
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio;    
    @FXML
    private ImageView imageViewCaratula;
    @FXML
    private TableView tableViewComentaris;
    @FXML
    private ChoiceBox choiceBoxFiltre;
    
    private ArrayList<Comentari> llista_comentaris;
    private ObservableList<Comentari> data_comentaris;
    private FilteredList<Comentari> data_filtrada_comentaris;
    private Map<String, String> mapaNomCamps;
    private ObservableList<String> llistaFiltre;
    
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
        
        // Apliquem un listener per si canvia el camp del text del filtre que apliqui el filtre
        textFiltre.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltre();
        });
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
    }
    
    public void mostrarLlibre(Llibre ll){
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(ll);
        
        // Si la reserva esta 'LLIURE' mostrem el boto per poder reservar-lo
        if(llibre.getUser_name().equals(STRING_RESERVA_LLIURE)){
            butoAccio.setDisable(false);
        }else {
            butoAccio.setDisable(true);
        }
    }
    
    public void ferReserva(){
        try {
            // Creem el HashMap on rebrem el codi de resposta
            HashMap<String, String> msg_in;
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
    
    public boolean obtenirComentaris(){
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
            Logger.getLogger(PestanyaLlistat.class.getName()).log(Level.SEVERE, null, ex);            
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