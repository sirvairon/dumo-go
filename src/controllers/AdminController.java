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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class AdminController implements Initializable {

    private ObservableList<Usuari> dataUsuaris;
    private Usuari usuariTemp;
    private Date tempsUltimClick;
    private Stage stageUsuari;
    private UsuariEdicioController usuariEdicioControlador;
    private HashMap<String, String> msg_in;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    private Alert alerta;
    
    
    @FXML
    private TableView tableViewUsuaris;
    
    @FXML
    private TabPane tabPaneGeneral;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alerta = new Alert(Alert.AlertType.NONE);
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());
        alerta.initStyle(StageStyle.UNDECORATED);
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
    private void veureLlistatUsuaris(ActionEvent event) throws IOException, ClassNotFoundException {
        // Creem la pestanya
        Tab tab1 = new Tab("Llistat d'usuaris");

        // Creem la taula per mostrar l'informacio dels usuaris
        TableView tb = crearTaulaUsuaris();
        
        // Creem el HBox que contindrà els labels amb informació del resultat total
        HBox hbResultat = new HBox();
        hbResultat.getStyleClass().add("hBoxResultat");
        Label resultat = new Label("Usuaris:");
        Label resultatValor = new Label("Valor");
        hbResultat.getChildren().addAll(resultat, resultatValor);
        
        // Creem el HBox que contindrà els botons per editar la informacio de la taula
        HBox hbButons = new HBox();
        hbButons.getStyleClass().add("hboxBotonesEdicion");
        
        Button butoActualitzar = new Button("Actualitzar");        
        // Configurem el EventHandler en cas de fer click al boto afegir
        butoActualitzar.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    //Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                    dataUsuaris = null;
                    omplirDadesTaulaUsuaris(tb,resultatValor);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Button butoAfegir = new Button("Afegir");        
        // Configurem el EventHandler en cas de fer click al boto afegir
        butoAfegir.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    //Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                    afegirUsuari();
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Button butoModificar = new Button("Modificar");
        // Configurem el EventHandler en cas de fer click al boto modificar
        butoModificar.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                    // Obrim la finestra usuari 
                    if(usuari_fila == null){
                        alerta.setAlertType(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Selecció d'usuari");
                        alerta.setHeaderText("No hi ha cap usuari seleccionat");
                        alerta.setContentText("Es necessari seleccionar un usuari");
                        alerta.show();
                    }else{
                        modificarUsuari(usuari_fila);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Button butoEliminar = new Button("Eliminar");
        // Configurem el EventHandler en cas de fer click al boto eliminar
        butoEliminar.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                    if(usuari_fila == null){
                        alerta.setAlertType(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Selecció d'usuari");
                        alerta.setHeaderText("No hi ha cap usuari seleccionat");
                        alerta.setContentText("Es necessari seleccionar un usuari"); 
                        alerta.show();
                    }else{
                        alerta.setAlertType(AlertType.CONFIRMATION);
                        alerta.setTitle("Selecció d'usuari");
                        alerta.setHeaderText("Vols esborrar l'usuari " + usuari_fila.getNom_user() + " ?");
                        alerta.setContentText("S'esborrarà definitivament");
                        Optional<ButtonType> option = alerta.showAndWait();
                        if (option.get() == ButtonType.OK) {
                            //this.label.setText("File deleted!");
                            eliminarUsuari(usuari_fila);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hbButons.getChildren().addAll(butoActualitzar, butoAfegir, butoModificar, butoEliminar);
            
        // Creem el vbox que contindra la taula, els botons d'edicio i una label per mostrar resultat totals
        VBox vbUsuaris = new VBox();
        vbUsuaris.getStyleClass().add("vbUsuaris");
        vbUsuaris.setVgrow(tb, Priority.ALWAYS);
        vbUsuaris.getChildren().addAll(tb,hbResultat, hbButons);
        
        // Omplim la taula amb la informació i la label amb el resultat total
        omplirDadesTaulaUsuaris(tb, resultatValor); 
        
        // Afegim el vBUsuaris que conte tot a la pestanya (tab) creada
        tab1.setContent(vbUsuaris);
        // Afegim la pestanya al grup de pestanyes
        tabPaneGeneral.getTabs().add(tab1);
        // Seleccionem la pestanya creada
        tabPaneGeneral.getSelectionModel().select(tab1);
        //tableViewUsuaris.setEditable(true);
    }    

    private void clickTaulaUsuaris(TableView tb) throws IOException {
        // Mirem l'element clickat
        Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
        // Si està buit tornem
        if (usuari_fila == null) return;
        // Si fem click un altre vegada comparem si es el mateix element clickat i generem un temps desde aquest click
        if(usuari_fila != usuariTemp){
            usuariTemp = usuari_fila;
            tempsUltimClick = new Date();
        // Si es el mateix element clickat i generem un altre temps desde aquest click i comparem temps entre clicks
        } else if(usuari_fila == usuariTemp) {
            Date now = new Date();
            long diff = now.getTime() - tempsUltimClick.getTime();
            if (diff < 300){ // Si ha sigut rapid (300 millis) es doble click
                System.out.println("DOBLE CLICK");
                // Obrim la finestra usuari 
                modificarUsuari(usuari_fila);
            } else {
                tempsUltimClick = new Date();
            }
        }
    }
    
    private TableView crearTaulaUsuaris() {               

        TableView tb = new TableView();
        
        // Per crear totes les columnes de la taula usuaris
        
        TableColumn<Usuari,String> col_Nom = new TableColumn<Usuari,String>("Nom");
        col_Nom.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().nom();
        }});

        TableColumn<Usuari,String> col_Cognom = new TableColumn<Usuari,String>("Cognom");
        col_Cognom.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().cognom1();
        }});

        TableColumn<Usuari,String> col_Poblacio = new TableColumn<Usuari,String>("Població");
        col_Poblacio.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().poblacio();
        }});

        TableColumn<Usuari,String> col_NomUsuari = new TableColumn<Usuari,String>("Nom d'usuari");
        col_NomUsuari.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().nom_user();
        }});

        TableColumn<Usuari,String> col_Provincia = new TableColumn<Usuari,String>("Província");
        col_Provincia.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().provincia();
        }});

        TableColumn<Usuari,String> col_Cognom2 = new TableColumn<Usuari,String>("Segon cognom");
        col_Cognom2.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().cognom2();
        }});

        TableColumn<Usuari,String> col_NumSoci = new TableColumn<Usuari,String>("Núm. soci");
        col_NumSoci.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().num_soci();
        }});

        TableColumn<Usuari,String> col_Direccio = new TableColumn<Usuari,String>("Direcció");
        col_Direccio.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().direccio();
        }});      

        TableColumn<Usuari,String> col_Telefon = new TableColumn<Usuari,String>("Telèfon 1");
        col_Telefon.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().telefon1();
        }});

        TableColumn<Usuari,String> col_CodiPostal = new TableColumn<Usuari,String>("Codi Postal");
        col_CodiPostal.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().codi_postal();
        }});

        TableColumn<Usuari,String> col_DNI = new TableColumn<Usuari,String>("DNI");
        col_DNI.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().dni();
        }});

        TableColumn<Usuari,String> col_Correu = new TableColumn<Usuari,String>("Correu");
        col_Correu.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().correu();
        }});

        TableColumn<Usuari,String> col_Telefon2 = new TableColumn<Usuari,String>("Telèfon 2");
        col_Telefon2.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().telefon2();
        }});
        
        TableColumn<Usuari,String> col_Pais = new TableColumn<Usuari,String>("País");
        col_Pais.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().pais();
        }}); 
        
        TableColumn<Usuari,String> col_Genere = new TableColumn<Usuari,String>("Gènere");
        col_Genere.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().genere();
        }});  
        
        TableColumn<Usuari,String> col_DataNaixament = new TableColumn<Usuari,String>("Data de naixement");
        col_DataNaixament.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().data_naixement();
        }});  
        
        TableColumn<Usuari,String> col_Observacions = new TableColumn<Usuari,String>("Observacions");
        col_Observacions.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().observacions();
        }});  
        
        TableColumn<Usuari,String> col_TipusSoci = new TableColumn<Usuari,String>("Tipus de soci");
        col_TipusSoci.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().tipus_soci();
        }});  
        
        TableColumn<Usuari,String> col_DataAlta = new TableColumn<Usuari,String>("Dalta d'alta");
        col_DataAlta.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().data_alta();
        }});  
        
        TableColumn<Usuari,String> col_AdminAlta = new TableColumn<Usuari,String>("Dalta d'alta");
        col_AdminAlta.setCellValueFactory(new Callback<CellDataFeatures<Usuari, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Usuari, String> p) {
                return p.getValue().admin_alta();
        }}); 
        

        // Afegim les columnes a la taula
        tb.getColumns().addAll(col_DNI);
        tb.getColumns().addAll(col_NomUsuari);
        tb.getColumns().addAll(col_NumSoci);      
        tb.getColumns().addAll(col_TipusSoci);
        tb.getColumns().addAll(col_DataAlta);
        tb.getColumns().addAll(col_Nom);
        tb.getColumns().addAll(col_Cognom);
        tb.getColumns().addAll(col_Cognom2);
        tb.getColumns().addAll(col_DataNaixament);
        tb.getColumns().addAll(col_Genere);
        tb.getColumns().addAll(col_Direccio);
        tb.getColumns().addAll(col_CodiPostal);
        tb.getColumns().addAll(col_Poblacio);
        tb.getColumns().addAll(col_Provincia);   
        tb.getColumns().addAll(col_Pais);
        tb.getColumns().addAll(col_Telefon);
        tb.getColumns().addAll(col_Telefon2);          
        tb.getColumns().addAll(col_Correu);
        tb.getColumns().addAll(col_AdminAlta);        
        tb.getColumns().addAll(col_Observacions);
        
        // Configurem el EventHandler en cas de fer click a la taula
        tb.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    clickTaulaUsuaris(tb);
                } catch (IOException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        return tb;
    }

    private void omplirDadesTaulaUsuaris(TableView tb, Label resultat) throws ClassNotFoundException {
        // Obtenim el llistat_d'usuaris
        ArrayList<Usuari> llistat_usuaris = AccionsClient.obtenirLlistatUsuaris();         

        // El transformem en una ObservableList
        dataUsuaris = FXCollections.observableArrayList(llistat_usuaris);

        // Introduim l'informació (ObservableList) a la taula
        tb.setItems(dataUsuaris);      
        
        // Mostrem en la label el numero de resultats tornats
        resultat.setText(String.valueOf(llistat_usuaris.size()));
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
    
    private void modificarUsuari(Usuari usuari) throws IOException{ 
         // Obrim la finestra usuari 
        obrirFinestraUsuari();
        // Actualitzem el controlador (finestra usuari) per mijtà del mètode dins del controlador
        usuariEdicioControlador.modificarUsuari(usuari);
    }
    
    @FXML
    private void afegirUsuari() throws IOException{
        // Obrim la finestra usuari
        obrirFinestraUsuari();
        // Esborrem dades en cas de que hi hagi alguna
        usuariEdicioControlador.afegirUsuari();
    }
    
    private void eliminarUsuari(Usuari usuari) throws IOException, ClassNotFoundException, InterruptedException{      
        alerta.setTitle("Eliminar usuari");
        alerta.setContentText("");
        msg_in = AccionsClient.eliminarUsuari(usuari);
        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        if(codi_resposta.equals("3000")){
            alerta.setAlertType(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(significat_codi_resposta);
            alerta.show();
        }else{
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText(significat_codi_resposta);
            alerta.show();
        }
    }
    
    @FXML
    private void ferLogout(ActionEvent event) throws IOException{
        msg_in = AccionsClient.ferLogOut();
        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        alerta.setTitle("Tancar sessió");
        alerta.setContentText("");
        if(codi_resposta.equals("20") || codi_resposta.equals("10")){
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
    
}
