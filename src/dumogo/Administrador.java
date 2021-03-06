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
public class Administrador {
    
    private final SimpleStringProperty nom_admin;
    private SimpleStringProperty nom_admin_antic;
    private final SimpleStringProperty password;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty nom;  
    private final SimpleStringProperty cognoms;  
    private final SimpleStringProperty direccio;
    private final SimpleStringProperty pais;
    private final SimpleStringProperty telefon;
    private final SimpleStringProperty correu;
    private final SimpleStringProperty data_naixement; 
    private final SimpleStringProperty admin_alta;
    
    private final String nom_admin_label = "Nom d'administrador";
    private final String password_label = "Password";
    private final String dni_label = "DNI";
    private final String nom_label = "Nom";  
    private final String cognoms_label = "Cognoms";  
    private final String direccio_label = "Direcció";
    private final String pais_label = "País";
    private final String telefon_label = "Telèfon";
    private final String correu_label = "Correu";
    private final String data_naixement_label = "Data de Naixement"; 
    private final String admin_alta_label = "Administrador alta";

    static public final Map<String, String> mapaNomCamps = new HashMap<>();

    public Administrador(HashMap<String, SimpleStringProperty> map) {
        this.nom_admin = new SimpleStringProperty((String.valueOf(map.get("nom_admin"))));
        this.nom_admin_antic = null;
        this.password = new SimpleStringProperty((String.valueOf(map.get("password"))));
        this.dni = new SimpleStringProperty((String.valueOf(map.get("dni"))));
        this.data_naixement = new SimpleStringProperty((String.valueOf(map.get("data_naixement"))));
        this.nom = new SimpleStringProperty((String.valueOf(map.get("nom"))));
        this.cognoms = new SimpleStringProperty((String.valueOf(map.get("cognoms"))));
        this.direccio = new SimpleStringProperty((String.valueOf(map.get("direccio"))));
        this.pais = new SimpleStringProperty((String.valueOf(map.get("pais"))));
        this.telefon = new SimpleStringProperty((String.valueOf(map.get("telefon"))));     
        this.correu = new SimpleStringProperty((String.valueOf(map.get("correu"))));
        this.admin_alta = new SimpleStringProperty((String.valueOf(map.get("admin_alta"))));  
        generaMapNomCamps();
    }
    
    //public Usuari(SimpleStringProperty nom_admin, SimpleStringProperty numero_soci, SimpleStringProperty dni, SimpleStringProperty nom, SimpleStringProperty cognoms, SimpleStringProperty cognom2, SimpleStringProperty direccio, SimpleStringProperty codi_postal, SimpleStringProperty poblacio, SimpleStringProperty provincia, SimpleStringProperty telefon, SimpleStringProperty telefon2, SimpleStringProperty correu, SimpleStringProperty foto) {  
    public Administrador(SimpleStringProperty nom_admin, SimpleStringProperty password, SimpleStringProperty dni, SimpleStringProperty data_naixement,
            SimpleStringProperty nom, SimpleStringProperty cognoms, 
            SimpleStringProperty direccio, SimpleStringProperty pais, SimpleStringProperty telefon, 
            SimpleStringProperty correu, SimpleStringProperty admin_alta) {       
        this.nom_admin = nom_admin;
        this.nom_admin_antic = null;
        this.password = password;
        this.dni = dni;
        this.data_naixement = data_naixement;
        this.nom = nom;
        this.cognoms = cognoms;
        this.direccio = direccio;      
        this.pais = pais;
        this.telefon = telefon;     
        this.correu = correu;
        this.admin_alta = admin_alta; 
        generaMapNomCamps();
    }  

    public final String getNom() {
        return nom.get();
    }

    public final String getCognoms() {
        return cognoms.get();
    }

    public final String getNom_Admin() {
        return nom_admin.get();
    }
    
    public final String getNom_Admin_antic() {
        return nom_admin_antic.get();
    }

    public final String getDireccio() {
        return direccio.get();
    }
    
    public final String getTelefon() {
        return telefon.get();
    }

    public final String getDni() {
        return dni.get();
    }

    public final String getCorreu() {
        return correu.get();
    }
    
    public final String getPais() {
        return pais.get();
    }

    public final String getData_naixement() {
        return data_naixement.get();
    }
    
    public final String getAdmin_Alta() {
        return admin_alta.get();
    }
    
    public final String getPassword() {
        return password.get();
    }
    
    public SimpleStringProperty nom() {
        return nom;
    }

    public SimpleStringProperty cognoms() {
        return cognoms;
    }

    public SimpleStringProperty nom_admin() {
        return nom_admin;
    }
    
    public SimpleStringProperty nom_admin_antic() {
        return nom_admin_antic;
    }

    public SimpleStringProperty direccio() {
        return direccio;
    }
    
    public SimpleStringProperty telefon() {
        return telefon;
    }

    public SimpleStringProperty dni() {
        return dni;
    }

    public SimpleStringProperty correu() {
        return correu;
    }
    
    public SimpleStringProperty pais() {
        return pais;
    }

    public SimpleStringProperty data_naixement() {
        return data_naixement;
    }
    
    public SimpleStringProperty admin_alta() {
        return admin_alta;
    }
    
    public SimpleStringProperty password() {
        return password;
    }
    
    public void setNom_admin_antic(String nom1) {
        this.nom_admin_antic = new SimpleStringProperty(nom1);
    }

    private void generaMapNomCamps(){
        mapaNomCamps.put("nom_admin", nom_admin_label);
        mapaNomCamps.put("password", password_label);
        mapaNomCamps.put("DNI", dni_label);
        mapaNomCamps.put("nom", nom_label);
        mapaNomCamps.put("cognoms", cognoms_label);
        mapaNomCamps.put("direccio", direccio_label);
        mapaNomCamps.put("pais", pais_label);
        mapaNomCamps.put("telefon", telefon_label);
        mapaNomCamps.put("correu", correu_label);
        mapaNomCamps.put("data_naixement", data_naixement_label);
        mapaNomCamps.put("admin_alta", admin_alta_label);
    }
    
    public Map getNomCamps(){
        return mapaNomCamps;
    }
    
    public String getAdmin_name_label() {
        return nom_admin_label;
    }

    public String getPassword_label() {
        return password_label;
    }

    public String getDni_label() {
        return dni_label;
    }

    public String getNom_label() {
        return nom_label;
    }

    public String getCognoms_label() {
        return cognoms_label;
    }

    public String getDireccio_label() {
        return direccio_label;
    }

    public String getPais_label() {
        return pais_label;
    }

    public String getTelefon_label() {
        return telefon_label;
    }

    public String getCorreu_label() {
        return correu_label;
    }

    public String getData_naixement_label() {
        return data_naixement_label;
    }

    public String getAdmin_alta_label() {
        return admin_alta_label;
    }
    
}

