/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.Buscador;
import dumogo.CodiErrors;
import dumogo.Llibre;
import dumogo.PestanyaLlistat;
import dumogo.Usuari;
import dumogo.Administrador;
import dumogo.PestanyaGrid;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class UsuariController implements Initializable {

    private ObservableList<Usuari> dataUsuaris;
    private Usuari usuariTemp;
    private Object element_temp;
    private Date tempsUltimClick;
    private Stage stageUsuari, stageAdministrador, stageBuscar, stageLlibre, stage1;
    private UsuariEdicioController usuariEdicioControlador;
    private AdminEdicioController adminEdicioControlador;    
    private LlibreEdicioController llibreEdicioControlador;
    private LlibreVistaController llibreVistaControlador;
    private HashMap<String, String> msg_in;
    private String codi_resposta;
    private String significat_codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private Alert alerta;
    private final static String USUARI_CASE = "usuaris";
    private final static String ADMINISTRADOR_CASE = "administradors";
    private final static String LLIBRE_CASE = "llibres";
    
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
            Image icon = new Image("/resources/dumogo_icon_neg_35.png");
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
    private void crearPestanyaLlistat(ActionEvent event)throws IOException, ClassNotFoundException {
        // Comprobem la seleccio del menu
        MenuItem menuItem = (MenuItem)event.getSource();
        String tipus_pestanya = menuItem.getId(); // Exemple: usuari o llibre
        String titol_pestanya = menuItem.getText(); // Exemple: Llistat d'usuaris o Llistat de llibres
        
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
            tb.setId(tipus_pestanya);
            Button butoModificar = pt.getButoModificar();
            butoModificar.setId(tipus_pestanya);
            Button butoAfegir = pt.getButoAfegir();
            butoAfegir.setId(tipus_pestanya);
            Button butoEliminar = pt.getButoEliminar();
            butoEliminar.setId(tipus_pestanya);
            Button butoActualitzar = pt.getButoActualitzar();
            butoActualitzar.setId(tipus_pestanya);
            Button butoFiltre = pt.getButoFiltre();

            
            // Configurem el EventHandler en cas de fer click a la taula
            tb.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        System.out.println(event.getSource().getClass().getSimpleName());
                        clickTaula(tb, tipus_pestanya);
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void crearPestanyaVistaLlibres(ActionEvent event)throws IOException, ClassNotFoundException {
        // Comprobem la seleccio del menu
        MenuItem menuItem = (MenuItem)event.getSource();
        String tipus_pestanya = menuItem.getId(); // Exemple: usuari o llibre
        String titol_pestanya = menuItem.getText(); // Exemple: Llistat d'usuaris o Llistat de llibres
        
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
            //PestanyaLlistat pt = new PestanyaLlistat(titol_pestanya, tipus_pestanya);
            PestanyaGrid pg = new PestanyaGrid(titol_pestanya, tipus_pestanya);
            
            // Obtenim els elements per controlar els clicks en els elements de la pestantya
            TilePane gp = pg.getTilePane();
            gp.setId(tipus_pestanya);
            /*
            Button butoModificar = pt.getButoModificar();
            butoModificar.setId(tipus_pestanya);
            Button butoAfegir = pt.getButoAfegir();
            butoAfegir.setId(tipus_pestanya);
            Button butoEliminar = pt.getButoEliminar();
            butoEliminar.setId(tipus_pestanya);
            Button butoActualitzar = pt.getButoActualitzar();
            butoActualitzar.setId(tipus_pestanya);
            Button butoFiltre = pt.getButoFiltre();
            

            
            // Configurem el EventHandler en cas de fer click a la taula
            tb.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        System.out.println(event.getSource().getClass().getSimpleName());
                        clickTaula(tb, tipus_pestanya);
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
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
            */
                    
            // Afegim la pestanya al grup de pestanyes
            tabPaneGeneral.getTabs().add(pg);
            // Seleccionem la pestanya creada
            tabPaneGeneral.getSelectionModel().select(pg);
            //tableViewUsuaris.setEditable(true);
        }
    }
    
    @FXML
    private void obrirFinestraBusqueda(ActionEvent event) throws IOException{
        // Comprobem la seleccio del menu
        MenuItem menuItem = (MenuItem)event.getSource();
        String tipus_busqueda = menuItem.getId(); // Exemple: usuari o administrador
        String titol_busqueda = menuItem.getText(); // Exemple: Buscar usuari o Buscar d'administrador
        
        Image icon = null;
        
        // En cas de que no s'hagi creat el stage (finestra oberta) el creem
        if (stageBuscar == null) { 
            stageBuscar = new Stage();
            switch(tipus_busqueda){
                case USUARI_CASE:
                    icon = new Image("/resources/usuari_icon_neg_16.png");
                    break;
                case ADMINISTRADOR_CASE:
                    icon = new Image("/resources/usuari_icon_neg_16.png");
                    break;
                case LLIBRE_CASE:
                    icon = new Image("/resources/llibre_icon_neg_16.png");
                    break;
            }
                    
            // Creem un buscador en funcio del tipus
            Buscador buscador = new Buscador(tipus_busqueda);
 
            // Obtenim els elements per controlar els clicks del buscador
            Button butoBuscar = buscador.getButoBuscar();
            Button butoCancelar = buscador.getButoCancelar();

            // Configurem el EventHandler en cas de fer click al buto buscar
            butoBuscar.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        String paraula_busqueda = buscador.getParaula();
                        if(paraula_busqueda != null){
                            buscarElement(paraula_busqueda, tipus_busqueda);
                            /*
                            if(buscarElement2(paraula_busqueda, tipus_busqueda)){
                                stageBuscar.close();
                            }
                            */
                        }else{
                            alerta.setAlertType(Alert.AlertType.ERROR);
                            alerta.setHeaderText("El camp no pot estar buit");
                            alerta.show();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
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
            //Image icon = new Image("/resources/usuari_icon_neg_16.png");
            stageBuscar.getIcons().add(icon);
            stageBuscar.setTitle(titol_busqueda);
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
    
    @FXML
    private void modificarPerfil(ActionEvent event) throws IOException{
        String paraula_busqueda = AccionsClient.getNom_user_actual();
        buscarElement(paraula_busqueda, USUARI_CASE);
    }

    private void buscarElement(String paraula_busqueda, String tipus_busqueda) throws IOException{
        
        try {
            // Comprobem que per buscar un llibre introduim un numero
            if(tipus_busqueda.equals(LLIBRE_CASE)){
                int test = Integer.valueOf(paraula_busqueda);
            }
            
            if(paraula_busqueda != null){
                msg_in = AccionsClient.buscarElement(paraula_busqueda, tipus_busqueda);
                System.out.println("RESULTAT BUSQUEDA:");
                System.out.println(msg_in.toString());

                // Obtenim el codi de resposta
                codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                // Comprobem el text del codi de resposta
                significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                // Sessio caducada
                if(codi_resposta.equals("10")){
                    sessioCaducada();
                // Element trobat
                }else if(codi_resposta.equals("5000") || codi_resposta.equals("6000") || codi_resposta.equals("1600")){
                    // Si la finestra per buscar esta oberta la tanquem
                    if(stageBuscar != null){
                        stageBuscar.close();
                    }
                    switch(tipus_busqueda){
                        case USUARI_CASE:
                            modificarElement(new Usuari((HashMap)msg_in));
                            break;
                        case ADMINISTRADOR_CASE:
                            modificarElement(new Administrador((HashMap)msg_in)); 
                            break;
                        case LLIBRE_CASE:
                            modificarElement(new Llibre((HashMap)msg_in)); 
                            break;
                    }
                // Error al trobar l'element
                }else{
                    alerta.setAlertType(Alert.AlertType.ERROR);
                    alerta.setHeaderText(significat_codi_resposta);
                    alerta.show();  
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuariController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            significat_codi_resposta = "Ha de ser un número";
            alerta.setAlertType(Alert.AlertType.ERROR);
            alerta.setHeaderText(significat_codi_resposta);
            alerta.show();
        }        
    }

    private void modificarElement(Object obj) throws IOException{
        obrirFinestraElement(USUARI_CASE);
        usuariEdicioControlador.modificarUsuari((Usuari)obj);
    }

    private void obrirFinestraElement(String tipus) throws IOException{                    
        switch(tipus){
            case USUARI_CASE:
                // En cas de que no s'hagi creat el stage (finestra oberta) el creem
                if (stageUsuari == null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UsuariEdicio.fxml"));
                    Parent root = (Parent) loader.load();
                    usuariEdicioControlador = loader.getController();
                    stageUsuari = new Stage();
                    stageUsuari.setScene(new Scene(root));
                    Image icon = new Image("/resources/usuari_icon_neg_20.png");
                    stageUsuari.getIcons().add(icon);
                    stageUsuari.setTitle("Usuari");
                    stageUsuari.setResizable(false);

                    // Quan es tanqui esborrem el stage de memòria                    
                    stageUsuari.setOnHiding(we -> stageUsuari = null);

                    // Mostrem la finestra del usuari a editar
                    stageUsuari.show();   

                // En cas de ja estigui creat el stage (finestra oberta) el portem al davant
                }else{
                    stageUsuari.toFront();
                }
                break;
            case LLIBRE_CASE:
                // En cas de que no s'hagi creat el stage (finestra oberta) el creem
                if (stageLlibre == null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LlibreEdicio.fxml"));
                    Parent root = (Parent) loader.load();
                    llibreEdicioControlador = loader.getController();
                    stageLlibre = new Stage();
                    stageLlibre.setScene(new Scene(root));
                    Image icon = new Image("/resources/usuari_icon_neg_16.png");
                    stageLlibre.getIcons().add(icon);
                    stageLlibre.setTitle("Llibre");
                    stageLlibre.setResizable(false);

                    // Quan es tanqui esborrem el stage de memòria                    
                    stageLlibre.setOnHiding(we -> stageLlibre = null);

                    // Mostrem la finestra del usuari a editar
                    stageLlibre.show();   

                // En cas de ja estigui creat el stage (finestra oberta) el portem al davant
                }else{
                    stageLlibre.toFront();
                }
                break;
        }
        
        
    }

    private void clickTaula(TableView tb, String tipus) throws IOException {
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
                modificarElement(element);
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
        Image icon = new Image("/resources/dumogo_icon_neg_35.png");
        stage.getIcons().add(icon);
        stage.setTitle("Dumo-Go");
        stage.setResizable(false);
        stage.show();   
    }

}
