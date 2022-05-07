/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author marcd
 */
public class Usuari {
    
    private final SimpleStringProperty nom_user;
    private final SimpleStringProperty password;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty data_alta;    
    private final SimpleStringProperty nom;  
    private final SimpleStringProperty cognom1;  
    private final SimpleStringProperty direccio;
    private final SimpleStringProperty pais;
    private final SimpleStringProperty telefon1;
    private final SimpleStringProperty correu;
    private final SimpleStringProperty data_naixement; 
    private final SimpleStringProperty admin_alta;
    
    private final String nom_user_label = "Nom d'usuari";
    private final String password_label = "Password";
    private final String dni_label = "DNI";
    private final String data_alta_label = "Data d'alta";    
    private final String nom_label = "Nom";  
    private final String cognom1_label = "Cognoms";  
    private final String direccio_label = "Direcció";
    private final String pais_label = "País";
    private final String telefon1_label = "Telèfon";
    private final String correu_label = "Correu";
    private final String data_naixement_label = "Data de Naixement"; 
    private final String admin_alta_label = "Administrador alta";

    static public final Map<String, String> mapaNomCamps = new HashMap<>();
        
    public Usuari(HashMap<String, SimpleStringProperty> map) {
        this.nom_user = new SimpleStringProperty((String.valueOf(map.get("nom_user"))));
        this.password = new SimpleStringProperty((String.valueOf(map.get("password"))));
        this.dni = new SimpleStringProperty((String.valueOf(map.get("dni"))));
        this.data_naixement = new SimpleStringProperty((String.valueOf(map.get("data_naixement"))));
        this.data_alta = new SimpleStringProperty((String.valueOf(map.get("data_alta"))));
        this.nom = new SimpleStringProperty((String.valueOf(map.get("nom"))));
        this.cognom1 = new SimpleStringProperty((String.valueOf(map.get("cognom1"))));
        this.direccio = new SimpleStringProperty((String.valueOf(map.get("direccio"))));
        this.pais = new SimpleStringProperty((String.valueOf(map.get("pais"))));
        this.telefon1 = new SimpleStringProperty((String.valueOf(map.get("telefon1"))));     
        this.correu = new SimpleStringProperty((String.valueOf(map.get("correu"))));
        this.admin_alta = new SimpleStringProperty((String.valueOf(map.get("admin_alta"))));   
        generaMapNomCamps();
    }
    
    //public Usuari(SimpleStringProperty nom_user, SimpleStringProperty num_soci, SimpleStringProperty dni, SimpleStringProperty nom, SimpleStringProperty cognom1, SimpleStringProperty cognom2, SimpleStringProperty direccio, SimpleStringProperty codi_postal, SimpleStringProperty poblacio, SimpleStringProperty provincia, SimpleStringProperty telefon1, SimpleStringProperty telefon2, SimpleStringProperty correu, SimpleStringProperty foto) {  
    public Usuari(SimpleStringProperty nom_user, SimpleStringProperty password, SimpleStringProperty dni, SimpleStringProperty data_naixement, 
            SimpleStringProperty data_alta, SimpleStringProperty nom, SimpleStringProperty cognom1, 
            SimpleStringProperty direccio, SimpleStringProperty pais, SimpleStringProperty telefon1, 
            SimpleStringProperty correu, SimpleStringProperty admin_alta) {       
        this.nom_user = nom_user;
        this.password = password;
        this.dni = dni;
        this.data_naixement = data_naixement;
        this.data_alta = data_alta;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.direccio = direccio;      
        this.pais = pais;
        this.telefon1 = telefon1;     
        this.correu = correu;
        this.admin_alta = admin_alta;    
        generaMapNomCamps();
    }  

    public final String getNom() {
        return nom.get();
    }

    public final String getCognom1() {
        return cognom1.get();
    }

    public final String getNom_user() {
        return nom_user.get();
    }

    public final String getDireccio() {
        return direccio.get();
    }

    public final String getTelefon1() {
        return telefon1.get();
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
    
    public final String getData_Alta() {
        return data_alta.get();
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

    public SimpleStringProperty cognom1() {
        return cognom1;
    }

    public SimpleStringProperty nom_user() {
        return nom_user;
    }

    public SimpleStringProperty direccio() {
        return direccio;
    }

    public SimpleStringProperty telefon1() {
        return telefon1;
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
    
    public SimpleStringProperty data_alta() {
        return data_alta;
    }
    
    public SimpleStringProperty admin_alta() {
        return admin_alta;
    }
    
    public SimpleStringProperty password() {
        return password;
    }

    private void generaMapNomCamps(){
        mapaNomCamps.put("user_name", nom_user_label);
        mapaNomCamps.put("password", password_label);
        mapaNomCamps.put("DNI", dni_label);
        mapaNomCamps.put("data_alta", data_alta_label);
        mapaNomCamps.put("nom", nom_label);
        mapaNomCamps.put("cognom1", cognom1_label);
        mapaNomCamps.put("direccio", direccio_label);
        mapaNomCamps.put("pais", pais_label);
        mapaNomCamps.put("telefon1", telefon1_label);
        mapaNomCamps.put("correu", correu_label);
        mapaNomCamps.put("data_naixement", data_naixement_label);
        mapaNomCamps.put("admin_alta", admin_alta_label);
    }
    
    public Map getNomCamps(){
        return mapaNomCamps;
    }
    
    public String getNom_user_label() {
        return nom_user_label;
    }

    public String getPassword_label() {
        return password_label;
    }

    public String getDni_label() {
        return dni_label;
    }

    public String getData_alta_label() {
        return data_alta_label;
    }

    public String getNom_label() {
        return nom_label;
    }

    public String getCognom1_label() {
        return cognom1_label;
    }

    public String getDireccio_label() {
        return direccio_label;
    }

    public String getPais_label() {
        return pais_label;
    }

    public String getTelefon1_label() {
        return telefon1_label;
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

