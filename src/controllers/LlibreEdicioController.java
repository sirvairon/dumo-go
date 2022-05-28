/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dumogo.AccionsClient;
import dumogo.CodiErrors;
import dumogo.Llibre;
import dumogo.Prestec;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class LlibreEdicioController implements Initializable {

    private Stage stage;
    private Llibre llibre;
    private String nom_llibre_actual;
    private String codi_resposta;
    private String significat_codi_resposta;
    private final static String LLIBRE_CASE = "llibres";
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private static final String STRING_RESERVA_LLIURE = "LLIURE";
    private Alert alerta;
    private HashMap<String, String> msg_in;
    
    @FXML
    private VBox raiz;    
    @FXML
    private TextField textFieldTitol; 
    @FXML
    private TextField textFieldAutor;        
    @FXML
    private TextField textFieldValoracio;       
    @FXML
    private TextField textFieldVots;        
    @FXML
    private TextArea textAreaDescripcio;   
    @FXML
    private TextField textFieldAdminAlta;  
    @FXML
    private TextField textFieldReservatDNI;
    @FXML
    private TextField textFieldAnyPublicacio;
    @FXML
    private TextField textFieldTipus;
    @FXML
    private DatePicker datePickerDataAlta;
    @FXML
    private ImageView imageViewCaratula;
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio; 
    @FXML
    private Button butoAccio2;
    
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
    }    
    
    private void omplirDades(Llibre llibre){
        // Agafem el llibre i el guardem
        this.llibre = llibre;
        // Omplim els camps de pantalla amb l'usuari
        textFieldTitol.setText(llibre.getNom());        
        textFieldAutor.setText(llibre.getAutor());
        textFieldAnyPublicacio.setText(llibre.getAny_Publicacio());
        textFieldTipus.setText(llibre.getTipus());
        textFieldValoracio.setText(llibre.getValoracio());
        textFieldVots.setText(llibre.getVots());
        textAreaDescripcio.setText(llibre.getDescripcio());
        textFieldAdminAlta.setText(llibre.getAdmin_alta());
        datePickerDataAlta.setValue(LocalDate.parse(llibre.getData_alta()));
        textFieldReservatDNI.setText(llibre.getUser_name());
        nom_llibre_actual = llibre.getNom();
        
        String caratula = llibre.getCaratula();
        if(!caratula.equals("null")){
            // Per la caratula tenim que decodejar l'string
            BufferedImage buffer = AccionsClient.decodeToImage(caratula);
            //BufferedImage buffer = decodeToImage(caratula);
            Image imatge = SwingFXUtils.toFXImage(buffer, null);
            imageViewCaratula.setImage(imatge);
        }
    }
    
    private void esborrarDades(){
        // Esborrem el llibre de memoria
        this.llibre = null;
        // Esborrem els camps de pantalla
        textFieldTitol.setText("");        
        textFieldAutor.setText("");
        textFieldAnyPublicacio.setText("");
        textFieldTipus.setText("");
        textFieldValoracio.setText("");
        textFieldVots.setText("");
        textAreaDescripcio.setText("");
        textFieldAdminAlta.setText("");
        datePickerDataAlta.setValue(LocalDate.now());
        textFieldReservatDNI.setText("");
        nom_llibre_actual = null;
    }
    
    public void afegirLlibre(){
        // Esborrem dades en cas de que hi hagi
        esborrarDades();
        // Establim per defecte la data d'avui
        datePickerDataAlta.setValue(LocalDate.now());
        // Establim l'administrador que esta afegint l'usuari
        textFieldAdminAlta.setText(AccionsClient.getNom_user_actual());
        // Com es un alta nova desde l'administrador s'han de poder editar tots els camps
        //textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (afegir)
        butoAccio.setText("Afegir");
        // Configurem el EventHandler en cas de fer click al boto d'afegir
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                    // Obtenim el llibre de pantalla
                    llibre = obtenirLlibrePantalla();
                    // Establim que el nom_antic i el nom siguien el mateix
                    llibre.setNom_Antic(llibre.getNom());
                    try {
                        // Creem el HashMap on rebrem el codi de resposta
                        HashMap<String, String> msg_in;
                        // Fem l'accio d'afegir el llibre
                        msg_in = AccionsClient.afegirElement(llibre);
                        // Obtenim el codi de resposta
                        codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                        // Obtenim el sinificat del codi de resposta
                        significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                        // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                        alerta.setTitle("Afegir llibre");
                        alerta.setHeaderText(significat_codi_resposta);
                        // Sessio caducada
                        if(codi_resposta.equals("10")){
                            sessioCaducada();
                        // Llibre afegit correctament
                        }else if(codi_resposta.equals("1400")){
                            alerta.setAlertType(Alert.AlertType.INFORMATION);
                            alerta.showAndWait();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        // Error a l'afegir el llibre
                        }else{
                            llibre = null;
                            alerta.setAlertType(Alert.AlertType.ERROR);
                            alerta.show();
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
        
        hboxBotones.getChildren().remove(butoAccio2);
        
    }
    
    public void modificarLlibre(Llibre ll){
        /** PER MODIFICAR PERFIL DESDE L'ADMINISTRADOR **/
        // Omplim les dades per les dades obtingudes de l'usuari
        omplirDades(ll);
        // Com es desde l'administrador s'han de poder editar tots els camps
        //textFieldDNI.setDisable(false);
        // Establim al buto de l'accio, l'accio que volem fer (modificar)
        butoAccio.setText("Modificar");
        // Configurem el EventHandler en cas de fer click al boto de modificar
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                // Abans d'obtenir les dades del llibre guardem el seu nom ja que serveix d'identificador
                //String titol_antic = llibre.getNom();                
                // Obtenim les dades del llibre amb les dades de la pantalla
                llibre = obtenirLlibrePantalla();
                System.out.println("nom_llibre_actual: "+ nom_llibre_actual);
                llibre.setNom_Antic(nom_llibre_actual);
                try {
                    // Creem el HashMap on rebrem el codi de resposta
                    HashMap<String, String> msg_in;
                    // Fem l'accio de modificar el llibre
                    msg_in = AccionsClient.modificarElement(llibre);
                    // Obtenim el codi de resposta
                    codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
                    // Obtenim el sinificat del codi de resposta
                    significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
                    // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
                    alerta.setTitle("Modificar llibre");
                    alerta.setHeaderText(significat_codi_resposta);
                    // Sessio caducada
                    if(codi_resposta.equals("10")){
                        sessioCaducada();
                    // Llibre modificat correctament
                    }else if(codi_resposta.equals("1800")){
                        alerta.setAlertType(Alert.AlertType.INFORMATION);
                        alerta.showAndWait();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    // Error al modificar l'usuari
                    }else{
                        alerta.setAlertType(Alert.AlertType.ERROR);
                        alerta.show();
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LlibreEdicioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
                
        // Establim al buto de l'accio, l'accio que volem fer (retornar)
        butoAccio2.setText("Retornar");
        // Si no esta lliure es pot retornar
        if(!ll.getUser_name().equals(STRING_RESERVA_LLIURE)){      
            butoAccio2.setDisable(false);
            butoAccio2.setOnMouseClicked( new EventHandler() {
                @Override
                public void handle(Event event) {
                    tornarPrestec();
                }
            });
        }

    }
    
    @FXML
    private void tancarFinestra(ActionEvent event) throws IOException, ClassNotFoundException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    private Llibre obtenirLlibrePantalla(){
        // Creem un llibre obtenint les dades de la pantalla 
        Image image = imageViewCaratula.imageProperty().get();
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        String imagen = AccionsClient.encodeToString(bImage,"jpg");
        //String imagen = encodeToString(bImage,"jpg");
        
        Llibre ll = new Llibre (
            new SimpleStringProperty(""), // ID
            new SimpleStringProperty(textFieldTitol.getText()),
            new SimpleStringProperty(textFieldAutor.getText()),
            new SimpleStringProperty(textFieldAnyPublicacio.getText()),
            new SimpleStringProperty(textFieldTipus.getText()),
            new SimpleStringProperty(datePickerDataAlta.getValue().toString()),
            new SimpleStringProperty(textFieldReservatDNI.getText()),
            new SimpleStringProperty(textFieldAdminAlta.getText()),
            new SimpleStringProperty(imagen),//imageViewCaratula            
            new SimpleStringProperty(textAreaDescripcio.getText()),
            new SimpleStringProperty(textFieldValoracio.getText()),            
            new SimpleStringProperty(textFieldVots.getText())
        );
        //ll.setNom_Antic("");
        // Tornem el llibre creat
        return ll;
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
        Image icon = new Image("/resources/dumogo_icon_window.png");
        stage1.getIcons().add(icon);
        stage1.setTitle("Dumo-Go");
        stage1.setResizable(false);
        stage1.show();    
    }   
    
    @FXML
    public Image carregarImatge() throws IOException{        
        Image imatge;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");        
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            BufferedImage bufferedImage = ImageIO.read(file);
            imatge = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewCaratula.setImage(imatge);
            return imatge;
        }
        return null;
    }
       
    public void tornarPrestec(){
        try {
            // Creem el HashMap on rebrem el codi de resposta
            HashMap<String, String> msg_in;
            // Fem l'accio de fer la reserva
            msg_in = AccionsClient.tornarReserva(llibre);
            // Obtenim el codi de resposta
            codi_resposta = msg_in.get(STRING_CODI_RESPOSTA);
            // Obtenim el sinificat del codi de resposta
            significat_codi_resposta = CodiErrors.ComprobarCodiError(codi_resposta);
            // Configurem l'alerta que ens confirmara que ha sigut correcte o hi ha hagut error
            alerta.setTitle("Retornar llibre");
            alerta.setHeaderText(significat_codi_resposta);
            // Sessio caducada
            if(codi_resposta.equals("10")){
                sessioCaducada();
            // Rserva correcta
            }else if(codi_resposta.equals("2200")){
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
}