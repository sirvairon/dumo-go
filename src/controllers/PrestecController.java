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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
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

/**
 * FXML Controller class
 *
 * @author marcd
 */
public class PrestecController implements Initializable {

    private Stage stage;
    private Prestec prestec;
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
    private Label labelTitol; 
    @FXML
    private TextField textFieldReserva;
    @FXML
    private DatePicker datePickerDataReserva;
    @FXML
    private DatePicker datePickerDataReservaPrevist;
    @FXML
    private DatePicker datePickerDataReservaReal;
    @FXML
    private HBox hboxBotones;    
    @FXML
    private Button butoCancelar;    
    @FXML
    private Button butoAccio; 
    
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
    
    private void omplirDades(Prestec prestec){
        // Agafem el prestec i el guardem
        this.prestec = prestec;
        // Omplim els camps de pantalla amb l'usuari
        labelTitol.setText(prestec.getNom_llibre());        
        textFieldReserva.setText(prestec.getUser_name());
        if(!prestec.getData_reserva().equals("null")){
            datePickerDataReserva.setValue(LocalDate.parse(prestec.getData_reserva()));
        }
        if(!prestec.getData_retorn_teoric().equals("null")){
            datePickerDataReservaPrevist.setValue(LocalDate.parse(prestec.getData_retorn_teoric()));
        }
        if(!prestec.getData_retorn_real().equals("null")){
            datePickerDataReservaReal.setValue(LocalDate.parse(prestec.getData_retorn_real()));
        }else{
            datePickerDataReservaReal.setValue(LocalDate.now());
        }
    }
    
    
    public void modificarPrestec(Prestec p){
        /** PER MODIFICAR PERFIL DESDE L'ADMINISTRADOR **/
        // Omplim les dades per les dades obtingudes del prestec
        omplirDades(p);
        // En cas de que el prestec no tingui data de retorn real es que no s'ha retornat
        if(p.getData_retorn_real().equals("") || p.getData_retorn_real().equals("null") || p.getData_retorn_real() == null){
            butoAccio.setDisable(false);
        }
        // Establim al buto de l'accio, l'accio que volem fer (tornar)
        butoAccio.setText("Retornar");
        // Configurem el EventHandler en cas de fer click al boto de retornar
        butoAccio.setOnMouseClicked( new EventHandler() {
            @Override
            public void handle(Event event) {
                tornarPrestec();
            }
        });

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
        Image icon = new Image("/resources/dumogo_icon_window.png");
        stage1.getIcons().add(icon);
        stage1.setTitle("Dumo-Go");
        stage1.setResizable(false);
        stage1.show();    
    }   
          
    public void tornarPrestec(){
        try {
            // Creem el HashMap on rebrem el codi de resposta
            HashMap<String, String> msg_in;
            // Fem l'accio de fer la reserva
            msg_in = AccionsClient.tornarReserva(prestec);
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
                prestec = null;
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
