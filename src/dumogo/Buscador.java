/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author marcd
 */
public class Buscador extends VBox {
    
    //private final VBox vbContigut, vbBusqueda;
    private final VBox vbBusqueda;
    private final HBox hbButons;    
    private final Label tipus;
    private final TextField paraula;
    private final Button butoBuscar, butoCancelar;
    private final static String USUARI_CASE = "usuaris";
    private final static String ADMINISTRADOR_CASE = "administradors";
    private final static String LLIBRE_CASE = "llibres";
    
    public Buscador(String tipus_busqueda) {
        //vbContigut = new VBox();
        vbBusqueda = new VBox();
        hbButons = new HBox();   
        tipus = new Label();
        paraula = new TextField();
        butoBuscar = new Button("Buscar");
        butoCancelar = new Button("Cancelar");
        
        switch(tipus_busqueda){
            case USUARI_CASE:
                tipus.setText("Nom de l'usuari");
                break;
            case ADMINISTRADOR_CASE:
                tipus.setText("Nom de l'administrador");
                break;
            case LLIBRE_CASE:
                tipus.setText("ID del llibre");
                break;
        }
        
        vbBusqueda.getChildren().addAll(tipus, paraula);
        hbButons.getChildren().addAll(butoBuscar, butoCancelar);
        
        this.getStyleClass().add("buscador");
        this.getChildren().addAll(vbBusqueda, hbButons);
        String cssFile1 = this.getClass().getResource("/styles/general.css").toExternalForm();
        String cssFile2 = this.getClass().getResource("/styles/buscador.css").toExternalForm();
        
        this.getStylesheets().addAll(cssFile1,cssFile2);
    }

    public Button getButoBuscar() {
        return butoBuscar;
    }

    public Button getButoCancelar() {
        return butoCancelar;
    }

    public String getParaula() {
        return paraula.getText();
    }
    
}
