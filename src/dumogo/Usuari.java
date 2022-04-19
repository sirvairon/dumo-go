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
public class Usuari {
    
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

    public Usuari(HashMap<String, SimpleStringProperty> map) {
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
    }
    
    //public Usuari(SimpleStringProperty nom_user, SimpleStringProperty num_soci, SimpleStringProperty dni, SimpleStringProperty nom, SimpleStringProperty cognom1, SimpleStringProperty cognom2, SimpleStringProperty direccio, SimpleStringProperty codi_postal, SimpleStringProperty poblacio, SimpleStringProperty provincia, SimpleStringProperty telefon1, SimpleStringProperty telefon2, SimpleStringProperty correu, SimpleStringProperty foto) {  
    public Usuari(SimpleStringProperty nom_user, SimpleStringProperty password, SimpleStringProperty dni, SimpleStringProperty data_naixement, SimpleStringProperty num_soci, 
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
    
}

