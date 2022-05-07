/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package z_borrar;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author marcd
 */
public class Usuari_ {
    
    private final SimpleStringProperty nom_user;
    private final SimpleStringProperty password;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty num_soci;
    private final SimpleStringProperty tipus_soci;
    private final SimpleStringProperty data_alta;    
    private final SimpleStringProperty nom;  
    private final SimpleStringProperty cognom1;  
    private final SimpleStringProperty cognom2;
    private final SimpleStringProperty direccio;
    private final SimpleStringProperty codi_postal;
    private final SimpleStringProperty poblacio;    
    private final SimpleStringProperty provincia;  
    private final SimpleStringProperty pais;
    private final SimpleStringProperty telefon1;
    private final SimpleStringProperty telefon2;   
    private final SimpleStringProperty correu;
    private final SimpleStringProperty foto;     
    private final SimpleStringProperty data_naixement; 
    private final SimpleStringProperty genere;
    private final SimpleStringProperty observacions;
    private final SimpleStringProperty admin_alta;
    
    private final String nom_user_label = "Nom d'usuari";
    private final String password_label = "Password";
    private final String dni_label = "DNI";
    private final String num_soci_label = "Núm. soci";
    private final String tipus_soci_label = "Tipus de soci" ;
    private final String data_alta_label = "Data d'alta";    
    private final String nom_label = "Nom";  
    private final String cognom1_label = "Primer Cognom";  
    private final String cognom2_label = "Segon Cognom";
    private final String direccio_label = "Direcció";
    private final String codi_postal_label = "Codi Postal";
    private final String poblacio_label = "Població";    
    private final String provincia_label = "Provincia";  
    private final String pais_label = "País";
    private final String telefon1_label = "Telèfon 1";
    private final String telefon2_label = "Telèfon 2";   
    private final String correu_label = "Correu";
    private final String foto_label = "Foto";     
    private final String data_naixement_label = "Data de Naixement"; 
    private final String genere_label = "Gènere";
    private final String observacions_label = "Observacions";
    private final String admin_alta_label = "Administrador alta";

    static public final Map<String, String> mapaNomCamps = new HashMap<>();
        
    public Usuari_(HashMap<String, SimpleStringProperty> map) {
        this.nom_user = new SimpleStringProperty((String.valueOf(map.get("nom_user"))));
        this.password = new SimpleStringProperty((String.valueOf(map.get("password"))));
        this.dni = new SimpleStringProperty((String.valueOf(map.get("dni"))));
        this.data_naixement = new SimpleStringProperty((String.valueOf(map.get("data_naixement"))));
        this.num_soci = new SimpleStringProperty((String.valueOf(map.get("num_soci"))));
        this.tipus_soci = new SimpleStringProperty((String.valueOf(map.get("tipus_soci"))));
        this.data_alta = new SimpleStringProperty((String.valueOf(map.get("data_alta"))));
        this.nom = new SimpleStringProperty((String.valueOf(map.get("nom"))));
        this.cognom1 = new SimpleStringProperty((String.valueOf(map.get("cognom1"))));
        this.cognom2 = new SimpleStringProperty((String.valueOf(map.get("cognom2"))));
        this.genere = new SimpleStringProperty((String.valueOf(map.get("genere"))));
        this.direccio = new SimpleStringProperty((String.valueOf(map.get("direccio"))));
        this.codi_postal = new SimpleStringProperty((String.valueOf(map.get("codi_postal"))));
        this.poblacio = new SimpleStringProperty((String.valueOf(map.get("poblacio"))));        
        this.provincia = new SimpleStringProperty((String.valueOf(map.get("provincia"))));        
        this.pais = new SimpleStringProperty((String.valueOf(map.get("pais"))));
        this.telefon1 = new SimpleStringProperty((String.valueOf(map.get("telefon1"))));     
        this.telefon2 = new SimpleStringProperty((String.valueOf(map.get("telefon2"))));
        this.correu = new SimpleStringProperty((String.valueOf(map.get("correu"))));
        this.foto = new SimpleStringProperty((String.valueOf(map.get("foto"))));
        this.observacions = new SimpleStringProperty((String.valueOf(map.get("observacions"))));
        this.admin_alta = new SimpleStringProperty((String.valueOf(map.get("admin_alta"))));   
        generaMapNomCamps();
    }
    
    //public Usuari(SimpleStringProperty nom_user, SimpleStringProperty num_soci, SimpleStringProperty dni, SimpleStringProperty nom, SimpleStringProperty cognom1, SimpleStringProperty cognom2, SimpleStringProperty direccio, SimpleStringProperty codi_postal, SimpleStringProperty poblacio, SimpleStringProperty provincia, SimpleStringProperty telefon1, SimpleStringProperty telefon2, SimpleStringProperty correu, SimpleStringProperty foto) {  
    public Usuari_(SimpleStringProperty nom_user, SimpleStringProperty password, SimpleStringProperty dni, SimpleStringProperty data_naixement, SimpleStringProperty num_soci, 
            SimpleStringProperty tipus_soci, SimpleStringProperty data_alta, SimpleStringProperty nom, SimpleStringProperty cognom1, 
            SimpleStringProperty cognom2, SimpleStringProperty genere, SimpleStringProperty direccio, SimpleStringProperty codi_postal, 
            SimpleStringProperty poblacio, SimpleStringProperty provincia, SimpleStringProperty pais, SimpleStringProperty telefon1, 
            SimpleStringProperty telefon2, SimpleStringProperty correu, SimpleStringProperty foto, SimpleStringProperty observacions,
            SimpleStringProperty admin_alta) {       
        this.nom_user = nom_user;
        this.password = password;
        this.dni = dni;
        this.data_naixement = data_naixement;
        this.num_soci = num_soci;
        this.tipus_soci = tipus_soci;
        this.data_alta = data_alta;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.genere = genere;
        this.direccio = direccio;
        this.codi_postal = codi_postal;
        this.poblacio = poblacio;        
        this.provincia = provincia;        
        this.pais = pais;
        this.telefon1 = telefon1;     
        this.telefon2 = telefon2;
        this.correu = correu;
        this.foto = foto;
        this.observacions = observacions;
        this.admin_alta = admin_alta;    
        generaMapNomCamps();
    }  

    public final String getNom() {
        return nom.get();
    }

    public final String getCognom1() {
        return cognom1.get();
    }

    public final String getCognom2() {
        return cognom2.get();
    }

    public final String getPoblacio() {
        return poblacio.get();
    }

    public final String getNom_user() {
        return nom_user.get();
    }

    public final String getProvincia() {
        return provincia.get();
    }

    public final String getNum_soci() {
        return num_soci.get();
    }

    public final String getDireccio() {
        return direccio.get();
    }

    public final String getFoto() {
        return foto.get();
    }

    public final String getTelefon1() {
        return telefon1.get();
    }

    public final String getCodi_postal() {
        return codi_postal.get();
    }

    public final String getDni() {
        return dni.get();
    }

    public final String getCorreu() {
        return correu.get();
    }

    public final String getTelefon2() {
        return telefon2.get();
    }
    
    public final String getPais() {
        return pais.get();
    }

    public final String getData_naixement() {
        return data_naixement.get();
    }

    public final String getGenere() {
        return genere.get();
    }
    
    public final String getData_Alta() {
        return data_alta.get();
    }
    
    public final String getObservacions() {
        return observacions.get();
    }
    
    public final String getTipus_Soci() {
        return tipus_soci.get();
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

    public SimpleStringProperty cognom2() {
        return cognom2;
    }

    public SimpleStringProperty poblacio() {
        return poblacio;
    }

    public SimpleStringProperty nom_user() {
        return nom_user;
    }

    public SimpleStringProperty provincia() {
        return provincia;
    }

    public SimpleStringProperty num_soci() {
        return num_soci;
    }

    public SimpleStringProperty direccio() {
        return direccio;
    }

    public SimpleStringProperty foto() {
        return foto;
    }

    public SimpleStringProperty telefon1() {
        return telefon1;
    }

    public SimpleStringProperty codi_postal() {
        return codi_postal;
    }

    public SimpleStringProperty dni() {
        return dni;
    }

    public SimpleStringProperty correu() {
        return correu;
    }

    public SimpleStringProperty telefon2() {
        return telefon2;
    }
    
    public SimpleStringProperty pais() {
        return pais;
    }

    public SimpleStringProperty data_naixement() {
        return data_naixement;
    }

    public SimpleStringProperty genere() {
        return genere;
    }
    
    public SimpleStringProperty data_alta() {
        return data_alta;
    }
    
    public SimpleStringProperty observacions() {
        return observacions;
    }
    
    public SimpleStringProperty tipus_soci() {
        return tipus_soci;
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
        mapaNomCamps.put("num_soci", num_soci_label);
        mapaNomCamps.put("tipus_soci", tipus_soci_label);
        mapaNomCamps.put("data_alta", data_alta_label);
        mapaNomCamps.put("nom", nom_label);
        mapaNomCamps.put("cognom1", cognom1_label);
        mapaNomCamps.put("cognom2", cognom2_label);
        mapaNomCamps.put("direccio", direccio_label);
        mapaNomCamps.put("codi_postal", codi_postal_label);
        mapaNomCamps.put("poblacio", poblacio_label);
        mapaNomCamps.put("provincia", provincia_label);
        mapaNomCamps.put("pais", pais_label);
        mapaNomCamps.put("telefon1", telefon1_label);
        mapaNomCamps.put("telefon2", telefon2_label);
        mapaNomCamps.put("correu", correu_label);
        mapaNomCamps.put("foto", foto_label);
        mapaNomCamps.put("data_naixement", data_naixement_label);
        mapaNomCamps.put("genere", genere_label);
        mapaNomCamps.put("observacions", observacions_label);
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

    public String getNum_soci_label() {
        return num_soci_label;
    }

    public String getTipus_soci_label() {
        return tipus_soci_label;
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

    public String getCognom2_label() {
        return cognom2_label;
    }

    public String getDireccio_label() {
        return direccio_label;
    }

    public String getCodi_postal_label() {
        return codi_postal_label;
    }

    public String getPoblacio_label() {
        return poblacio_label;
    }

    public String getProvincia_label() {
        return provincia_label;
    }

    public String getPais_label() {
        return pais_label;
    }

    public String getTelefon1_label() {
        return telefon1_label;
    }

    public String getTelefon2_label() {
        return telefon2_label;
    }

    public String getCorreu_label() {
        return correu_label;
    }

    public String getFoto_label() {
        return foto_label;
    }

    public String getData_naixement_label() {
        return data_naixement_label;
    }

    public String getGenere_label() {
        return genere_label;
    }

    public String getObservacions_label() {
        return observacions_label;
    }

    public String getAdmin_alta_label() {
        return admin_alta_label;
    }
}

