/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import static dumogo.Usuari.mapaNomCamps;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author marcd
 */
public class Llibre {
    private final SimpleStringProperty nom;
    private final SimpleStringProperty autor;
    private final SimpleStringProperty any_publicacio;
    private final SimpleStringProperty tipus;
    private final SimpleStringProperty data_alta;    
    private final SimpleStringProperty reservat_dni;  
    private final SimpleStringProperty admin_alta;  
    private final SimpleStringProperty caratula;
    private final SimpleStringProperty descripcio;
    private final SimpleStringProperty valoracio;
    private final SimpleStringProperty vots;
    
    private final String nom_label = "Títol";
    private final String autor_label = "Autor";
    private final String any_publicacio_label = "Any publicació";
    private final String tipus_label = "Tipus";
    private final String data_alta_label = "Data d'alta";    
    private final String reservat_dni_label = "Reserva DNI";  
    private final String admin_alta_label = "Admin alta";  
    private final String caratula_label = "Carátula";
    private final String descripcio_label = "Descripció";
    private final String valoracio_label = "Valoració";
    private final String vots_label = "Vots";
    
    static public final Map<String, String> mapaNomCamps = new HashMap<>();
        
    public Llibre(HashMap<String, SimpleStringProperty> map) {
        this.nom = new SimpleStringProperty((String.valueOf(map.get("nom"))));
        this.autor = new SimpleStringProperty((String.valueOf(map.get("autor"))));
        this.any_publicacio = new SimpleStringProperty((String.valueOf(map.get("any_publicacio"))));
        this.tipus = new SimpleStringProperty((String.valueOf(map.get("tipus"))));
        this.data_alta = new SimpleStringProperty((String.valueOf(map.get("data_alta"))));
        this.reservat_dni = new SimpleStringProperty((String.valueOf(map.get("reservat_dni"))));
        this.admin_alta = new SimpleStringProperty((String.valueOf(map.get("admin_alta"))));
        this.caratula = new SimpleStringProperty((String.valueOf(map.get("caratula"))));
        this.descripcio = new SimpleStringProperty((String.valueOf(map.get("descripcio"))));
        this.valoracio = new SimpleStringProperty((String.valueOf(map.get("valoracio"))));
        this.vots = new SimpleStringProperty((String.valueOf(map.get("vots"))));     
        generaMapNomCamps();
    }
    
    public Llibre(SimpleStringProperty nom, SimpleStringProperty autor, SimpleStringProperty any_publicacio, SimpleStringProperty tipus,
            SimpleStringProperty data_alta, SimpleStringProperty reservat_dni, SimpleStringProperty admin_alta, 
            SimpleStringProperty caratula, SimpleStringProperty descripcio, SimpleStringProperty valoracio, 
            SimpleStringProperty vots) {       
        this.nom = nom;
        this.autor = autor;
        this.any_publicacio = any_publicacio;
        this.tipus = tipus;
        this.data_alta = data_alta;
        this.reservat_dni = reservat_dni;
        this.admin_alta = admin_alta;
        this.caratula = caratula;
        this.descripcio = descripcio;      
        this.valoracio = valoracio;
        this.vots = vots;         
        generaMapNomCamps();
    }

    public final String getNom() {
        return nom.get();
    }
    
    public final String getAutor() {
        return autor.get();
    }
    
    public final String getAny_Publicacio() {
        return any_publicacio.get();
    }
    
    public final String getTipus() {
        return tipus.get();
    }
    
    public final String getData_alta() {
        return data_alta.get();
    }
    
    public final String getReservat_DNI() {
        return reservat_dni.get();
    }
    
    public final String getAdmin_alta() {
        return admin_alta.get();
    }
    
    public final String getCaratula() {
        return caratula.get();
    }
    
    public final String getDescripcio() {
        return descripcio.get();
    }
    
    public final String getValoracio() {
        return valoracio.get();
    }
    
    public final String getVots() {
        return vots.get();
    }
    
    public SimpleStringProperty nom() {
        return nom;
    }

    public SimpleStringProperty autor() {
        return autor;
    }

    public SimpleStringProperty any_publicacio() {
        return any_publicacio;
    }

    public SimpleStringProperty tipus() {
        return tipus;
    }

    public SimpleStringProperty data_alta() {
        return data_alta;
    }

    public SimpleStringProperty reservat_dni() {
        return reservat_dni;
    }

    public SimpleStringProperty admin_alta() {
        return admin_alta;
    }

    public SimpleStringProperty caratula() {
        return caratula;
    }

    public SimpleStringProperty descripcio() {
        return descripcio;
    }

    public SimpleStringProperty valoracio() {
        return valoracio;
    }

    public SimpleStringProperty vots() {
        return vots;
    }

    private void generaMapNomCamps(){
        mapaNomCamps.put("nom", nom_label);
        mapaNomCamps.put("autor", autor_label);
        mapaNomCamps.put("any_publicacio", any_publicacio_label);
        mapaNomCamps.put("tipus", tipus_label);
        mapaNomCamps.put("data_alta", data_alta_label);
        mapaNomCamps.put("reservat_dni", reservat_dni_label);
        mapaNomCamps.put("admin_alta", admin_alta_label);
        mapaNomCamps.put("caratula", caratula_label);
        mapaNomCamps.put("descripcio", descripcio_label);
        mapaNomCamps.put("valoracio", valoracio_label);
        mapaNomCamps.put("vots", vots_label);
    }
    
    public String getNom_label() {
        return nom_label;
    }

    public String getAutor_label() {
        return autor_label;
    }

    public String getAny_publicacio_label() {
        return any_publicacio_label;
    }

    public String getTipus_label() {
        return tipus_label;
    }

    public String getData_alta_label() {
        return data_alta_label;
    }

    public String getReservat_dni_label() {
        return reservat_dni_label;
    }

    public String getAdmin_alta_label() {
        return admin_alta_label;
    }

    public String getCaratula_label() {
        return caratula_label;
    }

    public String getDescripcio_label() {
        return descripcio_label;
    }

    public String getValoracio_label() {
        return valoracio_label;
    }

    public String getVots_label() {
        return vots_label;
    }

    public Map getNomCamps(){
        return mapaNomCamps;
    }
    
    
    
}
