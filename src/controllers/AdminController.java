/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

//import z_borrar.UsuariBuscarController;
import dumogo.AccionsClient;
import dumogo.Buscador;
import dumogo.CodiErrors;
import dumogo.PestanyaLlistat;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    private Object element_temp;
    private Date tempsUltimClick;
    private Stage stageUsuari, stageBuscar;
    private UsuariEdicioController usuariEdicioControlador;
    //private UsuariBuscarController UsuariBuscarController;
    private HashMap<String, String> msg_in;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    private Alert alerta;
    
    
    
    //@FXML
    //private TableView tableViewUsuaris;
    
    @FXML
    private TabPane tabPaneGeneral;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alerta = new Alert(Alert.AlertType.NONE);
        alerta.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/alertes.css").toExternalForm());        
    }

    @FXML
    private void crearPestanyaLlistat(ActionEvent event)throws IOException, ClassNotFoundException {
        // Comprobem la seleccio del menu
        MenuItem menuItem = (MenuItem)event.getSource();
        String tipus_pestanya = menuItem.getId(); // Exemple: ususari o administrador
        String titol_pestanya = menuItem.getText(); // Exemple: Llistat d'usuaris o Llistat d'administradors
        
        // Comprobem si ja esta creada
        boolean existeix = false;
        for(int i = 0; i<tabPaneGeneral.getTabs().size(); i++){
            if(tabPaneGeneral.getTabs().get(i).getText().equals(titol_pestanya)){
                // En cas de esta creada la seleccionem
                tabPaneGeneral.getSelectionModel().select(i);
                existeix = true;
            }            
        }
        
        // Si no esta creada la creem
        if(!existeix){
            // Creem una pestanya en funcio del tipus
            PestanyaLlistat pt = new PestanyaLlistat(titol_pestanya, tipus_pestanya);

            // Obtenim els elements per controlar els clicks en els elements de la pestantya
            TableView tb = pt.getTaula();
            Button butoModificar = pt.getButoModificar();
            Button butoAfegir = pt.getButoAfegir();
            Button butoEliminar = pt.getButoEliminar();
            Button butoActualitzar = pt.getButoActualitzar();
            Button butoFiltre = pt.getButoFiltre();

            // Configurem el EventHandler en cas de fer click a la taula
            tb.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        clickTaula(tb);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Configurem el EventHandler en cas de fer click al boto actualitzar
            butoActualitzar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        //Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                        //dataUsuaris = null;
                        //pt.actualitzaDadesTaula();
                        pt.actualitzarDades();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Configurem el EventHandler en cas de fer click al boto modificar
            butoModificar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        // Mirem l'element clickat
                        Object element = tb.getSelectionModel().getSelectedItem();
                        //Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                        if(element == null){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Selecció");
                            alerta.setHeaderText("No hi ha cap element seleccionat");
                            alerta.setContentText("Es necessari seleccionar un element");
                            alerta.show();
                        }else{
                            if(element.getClass().getSimpleName().equals("Usuari")){
                                // Obrim la finestra usuari 
                                modificarUsuari((Usuari)element);
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            // Configurem el EventHandler en cas de fer click al boto eliminar
            butoEliminar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        // Mirem l'element clickat
                        Object element = tb.getSelectionModel().getSelectedItem();
                        //Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                        if(element == null){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.setTitle("Selecció");
                            alerta.setHeaderText("No hi ha cap element seleccionat");
                            alerta.setContentText("Es necessari seleccionar un element"); 
                            alerta.show();
                        }else{
                            alerta.setAlertType(AlertType.CONFIRMATION);
                            if(element.getClass().getSimpleName().equals("Usuari")){
                                Usuari usuari_fila = (Usuari) element;                            
                                alerta.setTitle("Eliminar registre");
                                alerta.setHeaderText("Vols esborrar el registre " + usuari_fila.getNom_user() + " ?");
                                alerta.setContentText("S'esborrarà definitivament");
                                Optional<ButtonType> option = alerta.showAndWait();
                                if (option.get() == ButtonType.OK) {
                                    //this.label.setText("File deleted!");
                                    eliminarUsuari(usuari_fila);
                                }
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

            // Configurem el EventHandler en cas de fer click al boto actualitzar
            butoFiltre.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    //Usuari usuari_fila = (Usuari) tb.getSelectionModel().getSelectedItem();
                    //dataUsuaris = null;
                    //pt.actualitzaDadesTaula();
                    pt.aplicarFiltre();
                }
            });

            // Afegim la pestanya al grup de pestanyes
            tabPaneGeneral.getTabs().add(pt);
            // Seleccionem la pestanya creada
            tabPaneGeneral.getSelectionModel().select(pt);
            //tableViewUsuaris.setEditable(true);
        }
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
            tabPaneGeneral.getScene().getWindow().hide();
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
    private void afegirUsuari() throws IOException{
        // Obrim la finestra usuari
        obrirFinestraUsuari();
        // Esborrem dades en cas de que hi hagi alguna
        usuariEdicioControlador.afegirUsuari();
    }
    
    @FXML
    private void buscarUsuari() throws IOException{
        
        Usuari usuari;
        
        // En cas de que no s'hagi creat el stage (finestra oberta) el creem
        if (stageBuscar == null) { 
            
            // Creem un buscador en funcio del tipus
            Buscador buscador = new Buscador("usuari");
 
            // Obtenim els elements per controlar els clicks del buscador
            Button butoBuscar = buscador.getButoBuscar();
            Button butoCancelar = buscador.getButoCancelar();

            // Configurem el EventHandler en cas de fer click al buto buscar
            butoBuscar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        String nom_usuari = buscador.getParaula();
                        if(nom_usuari != null){
                            msg_in = AccionsClient.buscarUsuari(nom_usuari);
                            System.out.println("buscarUsuari msg_in:");
                            System.out.println(msg_in.toString());
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            // Configurem el EventHandler en cas de fer click al buto cancelar
            butoCancelar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    stageBuscar.close();
                }
            });
            
            stageBuscar = new Stage();
            stageBuscar.initModality(Modality.WINDOW_MODAL);
            Image icon = new Image("/resources/usuari_icon.png");
            stageBuscar.getIcons().add(icon);
            stageBuscar.setTitle("Buscar usuari");
            stageBuscar.setResizable(false);
            stageBuscar.setScene(new Scene(buscador));
            stageBuscar.initOwner( tabPaneGeneral.getScene().getWindow() );
            stageBuscar.setOnHiding(we -> stageBuscar = null);
            stageBuscar.show();
            
        // En cas de ja estigui creat el stage (finestra oberta) el portem al davant
        }else{
            stageBuscar.toFront();
        }
    }
    
    private void modificarUsuari(Usuari usuari) throws IOException{ 
         // Obrim la finestra usuari 
        obrirFinestraUsuari();
        // Actualitzem el controlador (finestra usuari) per mijtà del mètode dins del controlador
        usuariEdicioControlador.modificarUsuari(usuari);
    }
    
    private void eliminarUsuari(Usuari usuari) throws IOException, ClassNotFoundException, InterruptedException{      
        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
        alerta.setTitle("Eliminar usuari");
        alerta.setContentText("");
        // Fem l'accio d'eliminar usuari
        msg_in = AccionsClient.eliminarUsuari(usuari);
        //Obtenim el codi de resposta
        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
        // codi_resposta = "10";
        // Comprobem el text del codi de resposta
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        // Sessio caducada
        if(codi_resposta.equals("10")){
            sessioCaducada();
        // Usuari eliminat correctament
        }else if(codi_resposta.equals("3000")){
            alerta.setAlertType(Alert.AlertType.INFORMATION);
            alerta.setHeaderText(significat_codi_resposta);
            alerta.show();
        // Error al eliminar usuari
        }else{
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText(significat_codi_resposta);
            alerta.show();
        }
        
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
    
    private void clickTaula(TableView tb) throws IOException {
        // Mirem l'element clickat
        Object element = tb.getSelectionModel().getSelectedItem();

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
                if(element.getClass().getSimpleName().equals("Usuari")){
                    // Obrim la finestra usuari 
                    modificarUsuari((Usuari)element);
                }
            } else {
                // Si no, actualitzem el temps
                tempsUltimClick = new Date();
            }
        }else{
        }
    }
    
    private void sessioCaducada() throws IOException {
        // En cas de retornar codi 10 (sessio caducada)
        // Obtenim el text de l'error
        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
        // Establim alerta
        alerta.setTitle("Sessió caducada");
        alerta.setContentText("");
        alerta.setAlertType(Alert.AlertType.ERROR);
        alerta.setHeaderText(significat_codi_resposta);
        // La mostrem i esperem click
        alerta.showAndWait();
        // Tanquem finestra
        tabPaneGeneral.getScene().getWindow().hide();
        // Obrim finestra de login
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

}
