/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcd
 */
public class AccionsClient {
    
    private static String nom_user_actual;
    private static String codi_connexio_client;
    private static final String STRING_CODI_CONNEXIO = "codi";
    private static final String STRING_NOM_USUARI = "user_name";
    private static final String STRING_NOM_ADMINISTRADOR = "nom_admin";
    private static final String STRING_ID_LLIBRE = "id";
    private static int codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private final static String USUARI_CASE = "usuaris";
    private final static String ADMINISTRADOR_CASE = "administradors";
    private final static String LLIBRE_CASE = "llibres";
    private static OutputStream yourOutputStream;
    private static ObjectOutputStream mapOutputStream;
    private static InputStream yourInputStream;
    private static ObjectInputStream mapInputStream;
    private static HashMap<String, String> msg_out;
    private static HashMap<String, String> msg_in;

    
    public static String getNom_user_actual() {
        return nom_user_actual;
    }
        
    private static int establirConnexio(){
        
        int port = 7777;
            
        try {
            InetAddress addr1 = InetAddress.getByName("localhost"); 
            Socket socket = new Socket(addr1, port);
                        
            yourOutputStream = socket.getOutputStream();
            mapOutputStream = new ObjectOutputStream(yourOutputStream);
            
            yourInputStream = socket.getInputStream();
            mapInputStream = new ObjectInputStream(yourInputStream);
            
            return 1;
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
        
    public static HashMap ferLogIn(String usuari, String pass, String tipus) {  
        
        // Creem els HashMaps per enviar i rebre les dades per fer login
        msg_out = new HashMap<>();
        msg_in = new HashMap<>();
        codi_resposta = 0;
            
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Omplim el HasMap amb l'accio, l'usuari, el password i el codi de connexio buit
            if(tipus.equals("Administrador")){
                msg_out.put("accio", "comprobar_admin");
                msg_out.put("nom_admin", usuari);
            }else if(tipus.equals("Usuari")){
                msg_out.put("accio", "comprobar_usuari");
                msg_out.put(STRING_NOM_USUARI, usuari);
            }            
            msg_out.put("password", pass);
            msg_out.put(STRING_CODI_CONNEXIO, "");     
            
            // Enviem i rebem la informacio  
            mapOutputStream.writeObject(msg_out);            
            //msg_in = (HashMap) mapInputStream.readObject();

            // Obtenim el codi de retorn de fer l'accio login
            //String codi_resposta = (String) msg_in.get(STRING_CODI_RESPOSTA);
            codi_resposta = (int) mapInputStream.readObject();   
            //codi_resposta = mapInputStream.readInt();
            System.out.println("(ACCIONS CLIENT)codi_resposta:" + codi_resposta);
            // Si el codi de retorn es correcte guardem el codi de connexio rebut del servidor
            // i el nom d'usuari per futures consultes
            if(codi_resposta == 8010 || codi_resposta == 8020 || codi_resposta == 8030 || codi_resposta == 7010 || codi_resposta == 7020 || codi_resposta == 7030 || codi_resposta == 0){
                System.out.println("(ACCIONS CLIENT 2)codi_resposta:" + String.valueOf(codi_resposta));
                msg_in.put(STRING_CODI_RESPOSTA, String.valueOf(codi_resposta));               
            }else{                
                /*System.out.println("HE ENTRAT");*/
                msg_in.put(STRING_CODI_RESPOSTA, "8000");
                codi_connexio_client = String.valueOf(codi_resposta);
                nom_user_actual = usuari; 

            }      
            // Tanquem connexio
            yourOutputStream.close();
            mapInputStream.close();

            // Retornem el codi de retorn
            return msg_in; 

        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        msg_in.put(STRING_CODI_RESPOSTA, "0");
        return msg_in;        
    }  
  
    public static HashMap ferLogOut() {  
        
        // Creem els HashMaps per enviar i rebre les dades per fer login
        msg_out = new HashMap<>();
        msg_in = new HashMap<>();
        codi_resposta = 0;
            
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Omplim el HasMap amb l'accio, l'usuari i el codi de connexio 
            msg_out.put("accio", "tancar_sessio");
            msg_out.put("codi", codi_connexio_client);
            msg_out.put(STRING_CODI_CONNEXIO, String.valueOf(codi_connexio_client));
                 
            // Enviem i rebem la informacio  
            mapOutputStream.writeObject(msg_out);            
            //msg_in = (HashMap) mapInputStream.readObject();

            // Obtenim el codi de retorn de fer l'accio login
            //String codi_resposta = (String) msg_in.get(STRING_CODI_RESPOSTA);
            codi_resposta = (int) mapInputStream.readObject();   
            //codi_resposta = mapInputStream.readInt();
            System.out.println("(ACCIONS CLIENT)codi_resposta:" + codi_resposta);
            // Si el codi de retorn es correcte guardem el codi de connexio rebut del servidor
            // i el nom d'usuari per futures consultes
            if(codi_resposta == 20 || codi_resposta == 10){
                codi_connexio_client = null;
                nom_user_actual = null;                
            }
            msg_in.put(STRING_CODI_RESPOSTA, String.valueOf(codi_resposta));
            // Tanquem connexio
            yourOutputStream.close();
            mapInputStream.close();

            // Retornem el codi de retorn
            return msg_in; 

        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        msg_in.put(STRING_CODI_RESPOSTA, "0");
        return msg_in;        
    } 
                
    public static HashMap buscarElement(String paraula, String tipus_busqueda) throws ClassNotFoundException{
        
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Creem els HashMaps per enviar i rebre les dades per fer la accio
            msg_out = new HashMap<>();

            // Omplim el HasMap amb l'accio, la paraula i el codi de connexio
            msg_out.put(STRING_CODI_CONNEXIO, String.valueOf(codi_connexio_client));
            
            switch(tipus_busqueda){
                case USUARI_CASE:
                    msg_out.put("accio", "mostra_usuari");
                    msg_out.put(STRING_NOM_USUARI, paraula);
                    break;
                case ADMINISTRADOR_CASE:
                    msg_out.put("accio", "mostra_admin");
                    msg_out.put(STRING_NOM_ADMINISTRADOR, paraula);
                    break;
                case LLIBRE_CASE:
                    msg_out.put("accio", "mostra_llibre");
                    msg_out.put(STRING_ID_LLIBRE, paraula);
                    break;
            }
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);           
            msg_in = (HashMap) mapInputStream.readObject(); 
            System.out.println(msg_in.toString()); 
            
            // Establim connexio
            yourOutputStream.close();
            mapInputStream.close();
            
            // Retornem el HashMap de la informacio rebuda amb tota la informacio de l'usuari
            //return msg_in; 
            return msg_in; 
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            msg_in.put("accio", "mostra_llibre");
        }
        
        return null;        
    }  

    public static HashMap afegirElement(Object obj) throws ClassNotFoundException{
        
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Creem els HashMaps per enviar i rebre les dades per fer la accio
            msg_out = new HashMap<>();
            msg_in = new HashMap<>();
            
            // Mirem quin tipus d'objecte volem esborrar
            String tipus = obj.getClass().getSimpleName();
            
            // Omplim el HasMap amb l'accio, l'element a afegir i el codi de connexio 
            switch(tipus){
                case "Usuari":
                    msg_out.put("accio", "afegir_usuari");
                    usuariAHashmap(msg_out, (Usuari)obj);
                    break;
                case "Administrador":
                    msg_out.put("accio", "afegir_admin");
                    administradorAHashmap(msg_out, (Administrador)obj);
                    break;
                case "Llibre":
                    msg_out.put("accio", "afegir_llibre");
                    // Afegim les dades de l'usuari al hashmap
                    llibreAHashmap(msg_out, (Llibre)obj);
                    break;
            }       
            msg_out.put(STRING_CODI_CONNEXIO, String.valueOf(codi_connexio_client));
            
            System.out.println("ELEMENT A ENVIAR (" + tipus + "):");
            System.out.println(msg_out);
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);     
            codi_resposta = (int) mapInputStream.readObject();
            msg_in.put(STRING_CODI_RESPOSTA, String.valueOf(codi_resposta));        
            
            // Establim connexio
            yourOutputStream.close();
            mapInputStream.close();
            
            // Retornem el HashMap de la informacio rebuda amb tota la informacio de l'usuari
            return msg_in;            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }  
        
    public static HashMap eliminarElement(Object obj) throws ClassNotFoundException{
        
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Creem els HashMaps i les dades per enviar i rebre les dades per fer la accio
            msg_out = new HashMap<>();
            msg_in = new HashMap<>();

            // Mirem quin tipus d'objecte volem esborrar
            String tipus = obj.getClass().getSimpleName();
            
            // Omplim el HasMap amb l'accio, l'element a eliminiar i el codi de connexio 
            switch(tipus){
                case "Usuari":
                    msg_out.put("accio", "esborrar_usuari");
                    // Afegim les dades de l'usuari al hashmap
                    Usuari u = (Usuari)obj;
                    msg_out.put(STRING_NOM_USUARI, u.getUser_name());
                    break;
                case "Administrador":
                    msg_out.put("accio", "esborrar_admin");
                    // Afegim les dades de l'administrador al hashmap
                    Administrador a = (Administrador)obj;
                    msg_out.put(STRING_NOM_USUARI, a.getNom_Admin());
                    break;
                case "Llibre":
                    msg_out.put("accio", "esborrar_llibre");
                    // Afegim les dades de l'usuari al hashmap
                    Llibre ll = (Llibre)obj;
                    msg_out.put(STRING_ID_LLIBRE, ll.getID());                    
                    break;
            }            
            msg_out.put(STRING_CODI_CONNEXIO, String.valueOf(codi_connexio_client));
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);     
            codi_resposta = (int) mapInputStream.readObject();
            msg_in.put(STRING_CODI_RESPOSTA, String.valueOf(codi_resposta));        
            
            // Establim connexio
            yourOutputStream.close();
            mapInputStream.close();
            
            // Retornem el HashMap de la informacio rebuda amb tota la informacio de l'usuari
            return msg_in;            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }  

    public static HashMap modificarElement(Object obj) throws ClassNotFoundException{
        
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Creem els HashMaps per enviar i rebre les dades per fer la accio
            msg_out = new HashMap<>();
            msg_in = new HashMap<>();
            
            // Omplim el HasMap amb l'accio, l'element a modificar i el codi de connexio 
            if(obj instanceof Usuari){
                msg_out.put("accio", "modifica_usuari");
                usuariAHashmap(msg_out, (Usuari)obj);
            }else if(obj instanceof Administrador){
                msg_out.put("accio", "modifica_admin");
                administradorAHashmap(msg_out, (Administrador)obj);
            }else if(obj instanceof Llibre){
                msg_out.put("accio", "modifica_llibre");
                llibreAHashmap(msg_out, (Llibre)obj);
            }            
            msg_out.put(STRING_CODI_CONNEXIO, String.valueOf(codi_connexio_client));
                   
            System.out.println("ELEMENT A ENVIAR (" + obj.getClass().getSimpleName() + "):");
            System.out.println(msg_out);
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);     
            codi_resposta = (int) mapInputStream.readObject();
            msg_in.put(STRING_CODI_RESPOSTA, String.valueOf(codi_resposta));        
            
            // Establim connexio
            yourOutputStream.close();
            mapInputStream.close();
            
            // Retornem el HashMap de la informacio rebuda amb tota la informacio de l'usuari
            return msg_in;            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }  

    public static HashMap modificarPassword(Object obj, String password) throws ClassNotFoundException {
        
        try {
            // Establim connexio
            if(establirConnexio() == -1){
                msg_in.put(STRING_CODI_RESPOSTA, "-1");
                return msg_in;
            }

            // Creem els HashMaps per enviar i rebre les dades per fer la accio
            msg_out = new HashMap<>();
            msg_in = new HashMap<>();

            // Omplim el HasMap amb l'accio i el codi de connexio 
            msg_out.put("accio", "canvia_password");
            msg_out.put(STRING_CODI_CONNEXIO, String.valueOf(codi_connexio_client));
            
            // Afegim el nom d'usuari i password a modificar al hashmap
            if(obj instanceof Usuari){                
                msg_out.put(STRING_NOM_USUARI, ((Usuari) obj).getUser_name());
            }else if(obj instanceof Administrador){
                msg_out.put(STRING_NOM_ADMINISTRADOR, ((Administrador) obj).getNom_Admin());
            }
            msg_out.put("password_nou", password);            
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);     
            codi_resposta = (int) mapInputStream.readObject();
            msg_in.put(STRING_CODI_RESPOSTA, String.valueOf(codi_resposta));        
            
            // Establim connexio
            yourOutputStream.close();
            mapInputStream.close();
            
            // Retornem el HashMap de la informacio rebuda amb tota la informacio de l'usuari
            return msg_in;            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;          
    }
    
    public static ArrayList obtenirLlistat(String tipus) throws ClassNotFoundException{
        
        try {
            // Establim connexio
            establirConnexio();

            // Creem el HashMap per enviar i el List per rebre les dades per fer l'accio
            msg_out = new HashMap<>();
            List<HashMap> llistat_hashmaps;
            // Creem l'ArrayList on guardarem tots els usuaris
            ArrayList llistat = new ArrayList<Usuari>();
            
            // Omplim el HasMap amb l'accio del llistat que volem i el codi de connexio
            switch(tipus){
                case USUARI_CASE:                    
                    msg_out.put("accio", "llista_usuaris");
                    break;
                case ADMINISTRADOR_CASE:
                    msg_out.put("accio", "llista_admins");
                    break;
                case LLIBRE_CASE:
                    msg_out.put("accio", "llista_llibres");
                    break;
                    
            }
            msg_out.put(STRING_CODI_CONNEXIO, codi_connexio_client);

            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);            
            llistat_hashmaps = (List) mapInputStream.readObject();
            
            if(tipus.equals(USUARI_CASE)){
                // Omplim el llistat d'usuaris amb els usuaris de cada HashMap dins del List tornat
                for (HashMap obj:llistat_hashmaps) {
                    Usuari u = new Usuari(obj);
                    llistat.add(u);
                }
            }else if(tipus.equals(ADMINISTRADOR_CASE)){
                // Omplim el llistat d'usuaris amb els administradors de cada HashMap dins del List tornat
                for (HashMap obj:llistat_hashmaps) {
                    Administrador a = new Administrador(obj);
                    llistat.add(a);
                }
            }else if(tipus.equals(LLIBRE_CASE)){
                // Omplim el llistat d'usuaris amb els usuaris de cada HashMap dins del List tornat
                for (HashMap obj:llistat_hashmaps) {
                    Llibre ll = new Llibre(obj);
                    llistat.add(ll);
                }   
            }
            

            // Tanquem connexio
            yourOutputStream.close();
            mapInputStream.close();

            // Retornem el llistat d'usuaris
            return llistat; 
           
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }

    public static void usuariAHashmap(HashMap hashmap, Usuari usuari){        
        // Afegim les dades de l'usuari al hashmap
        hashmap.put(STRING_NOM_USUARI, usuari.getUser_name());
        hashmap.put("dni", usuari.getDni());
        hashmap.put("data_naixement", usuari.getData_naixement());
        hashmap.put("numero_soci", usuari.getNum_soci());
        hashmap.put("data_alta", usuari.getData_Alta());
        hashmap.put("nom", usuari.getNom());
        hashmap.put("cognoms", usuari.getCognoms());
        hashmap.put("direccio", usuari.getDireccio());       
        hashmap.put("pais", usuari.getPais());
        hashmap.put("telefon", usuari.getTelefon());     
        hashmap.put("correu", usuari.getCorreu());
        hashmap.put("admin_alta", usuari.getAdmin_Alta());
        hashmap.put("password", usuari.getPassword());
        
        // Si es una modificació el camp "user_name" modifiquem els camps
        if(usuari.getUser_name_antic() != usuari.getUser_name()){
            // Canviem el valor de "user_name" per el user_name antic ja que es l'identificador
            msg_out.replace(STRING_NOM_USUARI, usuari.getUser_name_antic());
            // Afegim "nou_user_name" el texte del titol del llibre que estem modificant
            msg_out.put("nou_user_name", usuari.getUser_name());
        }
    }
    
    public static void administradorAHashmap(HashMap hashmap, Administrador administrador){        
        // Afegim les dades de l'usuari al hashmap
        hashmap.put(STRING_NOM_ADMINISTRADOR, administrador.getNom_Admin());
        hashmap.put("dni", administrador.getDni());
        hashmap.put("data_naixement", administrador.getData_naixement());
        hashmap.put("nom", administrador.getNom());
        hashmap.put("cognoms", administrador.getCognoms());
        hashmap.put("direccio", administrador.getDireccio());       
        hashmap.put("pais", administrador.getPais());
        hashmap.put("telefon", administrador.getTelefon());     
        hashmap.put("correu", administrador.getCorreu());
        hashmap.put("admin_alta", administrador.getAdmin_Alta());
        hashmap.put("password", administrador.getPassword());

        // Si es una modificació el camp "nom_admin" modifiquem els camps
        if(administrador.getNom_Admin_antic() != administrador.getNom_Admin()){
            // Canviem el valor de "nom_admin" per el nom_admin antic ja que es l'identificador
            msg_out.replace(STRING_NOM_ADMINISTRADOR, administrador.getNom_Admin_antic());
            // Afegim "nom_admin_nou" el texte del titol del llibre que estem modificant
            msg_out.put("nou_nom_admin", administrador.getNom_Admin());
        }
    }

    public static void llibreAHashmap(HashMap hashmap, Llibre llibre){        
        // Afegim les dades de l'usuari al hashmap
        hashmap.put("id", llibre.getID());
        hashmap.put("nom", llibre.getNom());
        hashmap.put("autor", llibre.getAutor());
        hashmap.put("any_publicacio", llibre.getAny_Publicacio());
        hashmap.put("tipus", llibre.getTipus());
        hashmap.put("data_alta", llibre.getData_alta());
        hashmap.put("reservat_dni", llibre.getReservat_DNI());
        hashmap.put("admin_alta", llibre.getAdmin_alta());       
        hashmap.put("caratula", llibre.getCaratula());
        hashmap.put("descripcio", llibre.getDescripcio());     
        hashmap.put("valoracio", llibre.getValoracio());  
        hashmap.put("vots", llibre.getVots());
        
        // Si es una modificació el camp "titol" modifiquem els camps
        if(llibre.getNom_Antic() != llibre.getNom()){
            // Canviem el valor de "nom" per el nom antic ja que es l'identificador
            msg_out.replace("nom", llibre.getNom_Antic());
            // Afegim "nom_nou" el texte del titol del llibre que estem modificant
            msg_out.put("nou_nom", llibre.getNom());
        }  
    }

}
