/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author marcd
 */
public class Comentari {
    private final SimpleStringProperty id;
    private final SimpleStringProperty id_llibre;
    private final SimpleStringProperty user_name;
    private final SimpleStringProperty comentari;
    private final SimpleStringProperty data;
    
    private final String id_label = "ID";
    private final String id_llibre_label = "ID Llibre";
    private final String user_name_label = "Usuari";  
    private final String comentari_label = "Comentari";
    private final String data_label = "Data";    
    
    static public final Map<String, String> mapaNomCamps = new HashMap<>();
        
    public Comentari(HashMap<String, SimpleStringProperty> map) {
        this.id = new SimpleStringProperty((String.valueOf(map.get("id"))));
        this.id_llibre = new SimpleStringProperty((String.valueOf(map.get("id_llibre"))));
        this.user_name = new SimpleStringProperty((String.valueOf(map.get("user_name"))));
        this.comentari = new SimpleStringProperty((String.valueOf(map.get("comentari"))));
        this.data = new SimpleStringProperty((String.valueOf(map.get("data"))));   
        generaMapNomCamps();
    }
    
    public Comentari(SimpleStringProperty id, SimpleStringProperty id_llibre, SimpleStringProperty user_name, SimpleStringProperty comentari, SimpleStringProperty data) {   
        this.id = id;
        this.id_llibre = id_llibre;
        this.user_name = user_name;
        this.comentari = comentari;
        this.data = data;       
        generaMapNomCamps();
    }

    public final String getID() {
        return id.get();
    }
    
    public final String getID_llibre() {
        return id_llibre.get();
    }
    
    public final String getUser_name() {
        return user_name.get();
    }
    
    public final String getComentari() {
        return comentari.get();
    }
    
    public final String getData() {
        return data.get();
    }
        
    public SimpleStringProperty id() {
        return id;
    }
    
    public SimpleStringProperty id_llibre() {
        return id_llibre;
    }
    
    public SimpleStringProperty user_name() {
        return user_name;
    }
    
    public SimpleStringProperty comentari() {
        return comentari;
    }

    public SimpleStringProperty data() {
        return data;
    }

    private void generaMapNomCamps(){
        mapaNomCamps.put("id", id_label);
        mapaNomCamps.put("id_llibre", id_llibre_label);
        mapaNomCamps.put("user_name", user_name_label);
        mapaNomCamps.put("comentari", comentari_label);
        mapaNomCamps.put("data", data_label);
    }
    
    public String getID_label() {
        return id_label;
    }
    
    public String getNom_id_llibre_label() {
        return id_llibre_label;
    }
    
    public String getID_user_name_label() {
        return user_name_label;
    }

    public String getData_comentari_label() {
        return comentari_label;
    }

    public String getData_label() {
        return data_label;
    }

    public Map getNomCamps(){
        return mapaNomCamps;
    }
    
    public TableView columnesComentaris(TableView tb){
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
        
        tb.getColumns().addAll(
            col_ID,
            col_IDLlibre,
            col_NomUsuari, 
            col_Commentari, 
            col_Data
        ); 
        
        return tb;
    }
    
}
