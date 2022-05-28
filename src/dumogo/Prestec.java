/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author marcd
 */
public class Prestec {
    private final SimpleStringProperty id_reserva;
    private final SimpleStringProperty id_llibre;
    private final SimpleStringProperty nom_llibre;
    private final SimpleStringProperty data_reserva;
    private final SimpleStringProperty data_retorn_teoric;
    private final SimpleStringProperty data_retorn_real;
    private final SimpleStringProperty user_name;    
    private final SimpleStringProperty avis_programat;
    
    private final String id_reserva_label = "ID";
    private final String id_llibre_label = "ID Llibre";
    private final String nom_llibre_label = "Titol Llibre";
    private final String data_reserva_label = "Data reserva";
    private final String data_retorn_teoric_label = "Data de retorn programat";
    private final String data_retorn_real_label = "Data de retorn";
    private final String user_name_label = "Reservat per";    
    private final String avis_programat_label = "Avis programat";  
    
    static public final Map<String, String> mapaNomCamps = new HashMap<>();
        
    public Prestec(HashMap<String, SimpleStringProperty> map) {
        this.id_reserva = new SimpleStringProperty((String.valueOf(map.get("id_reserva"))));
        this.id_llibre = new SimpleStringProperty((String.valueOf(map.get("id_llibre"))));
        this.nom_llibre = new SimpleStringProperty((String.valueOf(map.get("nom_llibre"))));
        this.data_reserva = new SimpleStringProperty((String.valueOf(map.get("data_reserva"))));
        this.data_retorn_teoric = new SimpleStringProperty((String.valueOf(map.get("data_retorn_teoric"))));
        this.data_retorn_real = new SimpleStringProperty((String.valueOf(map.get("data_retorn_real"))));
        this.user_name = new SimpleStringProperty((String.valueOf(map.get("user_name"))));
        this.avis_programat = new SimpleStringProperty((String.valueOf(map.get("avis_programat"))));  
        generaMapNomCamps();
    }
    
    public Prestec(SimpleStringProperty id_reserva, SimpleStringProperty id_llibre, SimpleStringProperty nom_llibre, SimpleStringProperty data_reserva, SimpleStringProperty data_retorn_teoric, 
            SimpleStringProperty data_retorn_real, SimpleStringProperty user_name, SimpleStringProperty avis_programat ) {   
        this.id_reserva = id_reserva;
        this.id_llibre = id_llibre;
        this.nom_llibre = nom_llibre;
        this.data_reserva = data_reserva;
        this.data_retorn_teoric = data_retorn_teoric;
        this.data_retorn_real = data_retorn_real;
        this.user_name = user_name;
        this.avis_programat = avis_programat;       
        generaMapNomCamps();
    }

    public final String getID_reserva() {
        return id_reserva.get();
    }
    
    public final String getID_llibre() {
        return id_llibre.get();
    }
    
    public final String getNom_llibre() {
        return nom_llibre.get();
    }
    
    public final String getData_reserva() {
        return data_reserva.get();
    }
    
    public final String getData_retorn_teoric() {
        return data_retorn_teoric.get();
    }
    
    public final String getData_retorn_real() {
        return data_retorn_real.get();
    }
    
    public final String getUser_name() {
        return user_name.get();
    }
    
    public final String getAvis_programat() {
        return avis_programat.get();
    }
        
    public SimpleStringProperty id_reserva() {
        return id_reserva;
    }
    
    public SimpleStringProperty id_llibre() {
        return id_llibre;
    }
    
    public SimpleStringProperty nom_llibre() {
        return nom_llibre;
    }
    
    public SimpleStringProperty data_reserva() {
        return data_reserva;
    }

    public SimpleStringProperty data_retorn_teoric() {
        return data_retorn_teoric;
    }

    public SimpleStringProperty data_retorn_real() {
        return data_retorn_real;
    }

    public SimpleStringProperty user_name() {
        return user_name;
    }

    public SimpleStringProperty avis_programat() {
        return avis_programat;
    }

    private void generaMapNomCamps(){
        mapaNomCamps.put("id_reserva", id_reserva_label);
        mapaNomCamps.put("id_llibre", id_llibre_label);
        mapaNomCamps.put("nom_llibre", nom_llibre_label);
        mapaNomCamps.put("data_reserva", data_reserva_label);
        mapaNomCamps.put("data_retorn_teoric", data_retorn_teoric_label);
        mapaNomCamps.put("data_retorn_real", data_retorn_real_label);
        mapaNomCamps.put("user_name", user_name_label);
        mapaNomCamps.put("avis_programat", avis_programat_label);
    }
    
    public String getID_Reserva_label() {
        return id_reserva_label;
    }
    
    public String getNom_llibre_label() {
        return nom_llibre_label;
    }
    
    public String getID_llibre_label() {
        return id_llibre_label;
    }

    public String getData_reserva_label() {
        return data_reserva_label;
    }

    public String getData_retorn_teoric_label() {
        return data_retorn_teoric_label;
    }

    public String getData_retorn_real_label() {
        return data_retorn_real_label;
    }
    
    public String getUser_name_label() {
        return user_name_label;
    }

    public String getAvis_programat_label() {
        return avis_programat_label;
    }

    public Map getNomCamps(){
        return mapaNomCamps;
    }
    
}
