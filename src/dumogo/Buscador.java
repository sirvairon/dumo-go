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
    
    public Buscador(String concepte_busqueda) {
        //vbContigut = new VBox();
        vbBusqueda = new VBox();
        hbButons = new HBox();   
        tipus = new Label();
        paraula = new TextField();
        butoBuscar = new Button("Buscar");
        butoCancelar = new Button("Cancelar");
        
        if(concepte_busqueda.equals("usuari")){
            tipus.setText("Nom de usuari");
        }
        
        vbBusqueda.getChildren().addAll(tipus, paraula);
        hbButons.getChildren().addAll(butoBuscar, butoCancelar);
        
        this.getStyleClass().add("buscador");
        //this.getStyleClass().addAll("root","mainFxmlClass");
        this.getChildren().addAll(vbBusqueda, hbButons);
        String cssFile1 = this.getClass().getResource("/styles/general.css").toExternalForm();
        String cssFile2 = this.getClass().getResource("/styles/buscador.css").toExternalForm();
        
        this.getStylesheets().addAll(cssFile1,cssFile2);
        //this.getStylesheets().add(cssFile2);
        
        //vbContigut.getChildren().addAll(vbBusqueda, hbButons);
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
