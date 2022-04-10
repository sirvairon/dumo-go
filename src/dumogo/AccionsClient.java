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
public class AccionsClient {
    
    static String codi_connexio_client;
    static String dni_usuari;
    
    public static String ferLogin(String usuari, String pass) throws ClassNotFoundException{
        
        int port = 7777;

        try {
            InetAddress addr1 = InetAddress.getByName("localhost"); 
            Socket socket = new Socket(addr1, port);
                        
            OutputStream yourOutputStream = socket.getOutputStream();
            ObjectOutputStream mapOutputStream = new ObjectOutputStream(yourOutputStream);
            
            InputStream yourInputStream = socket.getInputStream();
            ObjectInputStream mapInputStream = new ObjectInputStream(yourInputStream);

            // Creem el HashMap per enviar les dades per fer login
            HashMap<String, String> msg_out = new HashMap<String, String>();
            HashMap<String, String> msg_in = new HashMap<String, String>();

            // Omplim el HasMap amb usuari, password i el codi de connexió buit
            msg_out.put("accio", "comprobar_usuari");
            msg_out.put("user_name", usuari);
            msg_out.put("password", pass);
            msg_out.put("codi_connexio_client", "");
            
            // Enviem i rebem la informació
            mapOutputStream.writeObject(msg_out);            
            msg_in = (HashMap) mapInputStream.readObject();
            
            // Obtenim el codi de retorn de fer l'accio login
            String codi_retorn = (String) msg_in.get("codi_retorn");
            System.out.println(codi_retorn);
            
            // Si el codi de retorn es correcte guardem el codi de connexio rebut del servidor
            if(codi_retorn.equals("1000")){
                codi_connexio_client = (String) msg_in.get("codi_connexio_servidor");
            }           
            
            yourOutputStream.close();
            mapInputStream.close();
            
            // Retornem el codi de retorn
            return codi_retorn;            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccionsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;        
    }  
}
