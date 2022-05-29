/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package dumogo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author marcd
 */
public class DumoGo extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Carreguem la finestra principal per fer login
        Parent root = FXMLLoader.load(getClass().getResource("/views/LogIn.fxml"));        
        Scene scene = new Scene(root);
        // Establim icono aplicació
        Image icon = new Image("/resources/dumogo_window_icon.png");
        stage.getIcons().add(icon);
        // Establim titol aplicació i altres propietats
        stage.setTitle("Dumo-Go");
        stage.setResizable(false);
        // L'afegim a l'stage i la mostrem
        stage.setScene(scene);
        stage.show();
        //System.out.println(System.getProperties());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        
        // Afegim un sleep per mostrar un temps la pantalla de benvinguda
        Thread.sleep(2500);
    }
}
