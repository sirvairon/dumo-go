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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcd
 */
public class AccionsAdmin {
    
    private static String codi_connexio_client;
    private static String user_name;
    private static OutputStream yourOutputStream;
    private static ObjectOutputStream mapOutputStream;
    private static InputStream yourInputStream;
    private static ObjectInputStream mapInputStream;
    
    private static void establirConnexio(){
        
        int port = 7777;
            
        try {
            InetAddress addr1 = InetAddress.getByName("localhost"); 
            Socket socket = new Socket(addr1, port);
                        
            yourOutputStream = socket.getOutputStream();
            mapOutputStream = new ObjectOutputStream(yourOutputStream);
            
            yourInputStream = socket.getInputStream();
            mapInputStream = new ObjectInputStream(yourInputStream);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static String ferLogin(String usuari, String pass) {        

        try {
            // Establim connexio
            establirConnexio();
            
            // Creem els HashMaps per enviar i rebre les dades per fer login
            HashMap<String, String> msg_out = new HashMap<>();
            HashMap<String, String> msg_in;

            // Omplim el HasMap amb usuari, password i el codi de connexio buit
            msg_out.put("accio", "comprobar_usuari");
            msg_out.put("user_name", usuari);
            msg_out.put("password", pass);
            msg_out.put("codi_connexio_client", "");     
            
            // Enviem i rebem la informacio  
            mapOutputStream.writeObject(msg_out);            
            msg_in = (HashMap) mapInputStream.readObject();

            // Obtenim el codi de retorn de fer l'accio login
            String codi_retorn = (String) msg_in.get("codi_retorn");

            // Si el codi de retorn es correcte guardem el codi de connexio rebut del servidor
            // i el nom d'usuari per futures consultes
            if(codi_retorn.equals("1000")){
                codi_connexio_client = (String) msg_in.get("codi_connexio_servidor");
                user_name = usuari;
            }           

            // Tanquem connexio
            yourOutputStream.close();
            mapInputStream.close();

            // Retornem el codi de retorn
            return codi_retorn; 

        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }  
    
    public static HashMap obtenirUsuari() throws ClassNotFoundException{
        
        try {
            // Establim connexio
            establirConnexio();

            // Creem els HashMaps per enviar i rebre les dades per fer la accio
            HashMap<String, String> msg_out = new HashMap<>();
            HashMap<String, String> msg_in;

            // Omplim el HasMap amb usuari, password i el codi de connexio buit
            msg_out.put("accio", "obtenir_usuari");
            msg_out.put("user_name", user_name);
            msg_out.put("codi_connexio_client", codi_connexio_client);
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);            
            msg_in = (HashMap) mapInputStream.readObject();         
            
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
    
    public static HashMap obtenirLlistatUsuaris() throws ClassNotFoundException{
        
        try {
            // Establim connexio
            establirConnexio();

            // Creem els HashMaps per enviar i rebre les dades per fer la accio
            HashMap<String, String> msg_out = new HashMap<>();
            HashMap<String, String> msg_in;

            // Omplim el HasMap amb usuari, password i el codi de connexio buit
            msg_out.put("accio", "obtenir_llistat_usuaris");
            msg_out.put("codi_connexio_client", codi_connexio_client);
            
            // Enviem i rebem la informacio
            mapOutputStream.writeObject(msg_out);            
            msg_in = (HashMap) mapInputStream.readObject();         
            
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

}
