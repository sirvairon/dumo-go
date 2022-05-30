/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dumogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcd
 */
public class AccionsClientTest {
    
    private static String nom_user_actual;
    private static String codi_connexio_client;
    private static final String STRING_CODI_CONNEXIO = "codi";
    private static int codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_retorn";
    private static HashMap<String, String> msg_out;
    private static HashMap<String, String> msg_in;
    private final static String USUARI_CASE = "usuaris";
    private final static String ADMINISTRADOR_CASE = "administradors";
    private final static String LLIBRE_CASE = "llibres";
    private final static String PRESTEC_CASE = "prestecs";
    private final static String PRESTEC_USUARI_CASE = "prestecs_usuari";
    private final static String PRESTEC_NO_TORNATS_CASE = "prestecs_no_tornats";
    private final static String PRESTEC_LLEGITS_CASE = "prestecs_llegits";
    
    public AccionsClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    public HashMap loginUsuari(){
        String usuari = "Test";
        String pass = "12345";
        String tipus = "Usuari";
        HashMap result = AccionsClient.ferLogIn(usuari, pass, tipus);
        return result;
    }
    
    public HashMap loginAdministrador(){
        String usuari = "marc45";
        String pass = "1234";
        String tipus = "Administrador";
        HashMap result = AccionsClient.ferLogIn(usuari, pass, tipus);
        return result;
    }
    
    public Object buscarElement(String paraula, String tipus) throws ClassNotFoundException {  
        msg_in = AccionsClient.buscarElement(paraula, tipus);
        switch(tipus){
            case USUARI_CASE:
                Usuari u = new Usuari((HashMap)msg_in);
                return u;
            case ADMINISTRADOR_CASE:
                Administrador a = new Administrador((HashMap)msg_in); 
                return a;
            case LLIBRE_CASE:
                Llibre ll = new Llibre((HashMap)msg_in); 
                return ll;
        }
        return null;
    }
    
    /**
     * Test per iniciar sessió com usuari
     */
    @Test
    public void testFerLogInUsuari() {
        System.out.println("ferLogIn - Usuari");
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"8000");
        HashMap result = loginUsuari();
        System.out.println(result);
        assertEquals(expResult, result);
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per tancar sessió com usuari
     */
    @Test
    public void testFerLogOutUsuari() {  
        System.out.println("ferLogOut - Usuari");
        loginAdministrador();
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"20");
        HashMap result = AccionsClient.ferLogOut();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test per iniciar sessió com administrador
     */
    @Test
    public void testFerLogInAdministrador() {
        System.out.println("ferLogIn - Administrador");
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"8000");
        HashMap result = loginAdministrador();
        System.out.println(result);
        assertEquals(expResult, result);
        AccionsClient.ferLogOut();
    }

    /**
     * Test per tancar sessió com administrador
     */
    @Test
    public void testFerLogOutAdministrador() {        
        System.out.println("ferLogOut - Administrador");
        loginAdministrador();
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"20");
        HashMap result = AccionsClient.ferLogOut();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test per rebre el llistat de usuaris
     */
    @Test
    public void testFerLlistatUsuaris() {        
        System.out.println("Llistat usuaris");
        String tipus = USUARI_CASE;
        loginAdministrador();
        ArrayList<Usuari> llista = null;
        try {
            llista = AccionsClient.obtenirLlistat(tipus);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Numero d'usuaris: " + llista.size());
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per rebre el llistat de administradors
     */
    @Test
    public void testFerLlistatAdministradors() {        
        System.out.println("Llistat administdaors");
        String tipus = ADMINISTRADOR_CASE;
        loginAdministrador();
        ArrayList<Usuari> llista = null;
        try {
            llista = AccionsClient.obtenirLlistat(tipus);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Numero d'administradors: " + llista.size());
        AccionsClient.ferLogOut();
    }

    /**
     * Test per rebre el llistat de llibres
     */
    @Test
    public void testFerLlistatLlibres() {        
        System.out.println("Llistat llibres");
        String tipus = "llibres";
        loginAdministrador();
        ArrayList<Llibre> llista = null;
        try {
            llista = AccionsClient.obtenirLlistat(tipus);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Numero de llibres: " + llista.size());
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per rebre el llistat de llibres
     */
    @Test
    public void testFerMostrarLlibre() throws ClassNotFoundException {        
        System.out.println("Mostrar llibre");
        loginAdministrador();
        
        Llibre ll = (Llibre)buscarElement("66", LLIBRE_CASE);

        System.out.println("Titol del llibre: " + ll.getNom());
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per rebre el llistat de llibres
     */
    @Test
    public void testFerMostrarUsuari() throws ClassNotFoundException {        
        System.out.println("Mostrar usuari");
        loginAdministrador();
        
        Usuari u = (Usuari)buscarElement("Test", USUARI_CASE);

        System.out.println("Nom de l'usuari: " + u.getNom());
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per rebre el llistat de llibres
     */
    @Test
    public void testFerMostrarAdministrador() throws ClassNotFoundException {        
        System.out.println("Mostrar administrador");
        loginAdministrador();
        
        Administrador u = (Administrador)buscarElement("marc45", ADMINISTRADOR_CASE);

        System.out.println("Nom de l'administrador: " + u.getNom());
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per rebre el llistat de prestecs
     */
    @Test
    public void testFerLlistatPrestecs() {        
        System.out.println("Llistat prestecs");
        String tipus = "prestecs";
        loginAdministrador();
        ArrayList<Llibre> llista = null;
        try {
            llista = AccionsClient.obtenirLlistat(tipus);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Numero de llibres: " + llista.size());
        AccionsClient.ferLogOut();
    }

    
    /**
     * Test per reservar i tornar un prestec
     */
    @Test
    public void testReservariTornarPrestec() throws ClassNotFoundException {  
        System.out.println("Realitzar prestec");
        loginUsuari();
        Llibre ll = (Llibre)buscarElement("66", LLIBRE_CASE);
        HashMap expResult1 = new HashMap();        
        expResult1.put(STRING_CODI_RESPOSTA,"2100");
        msg_in = AccionsClient.ferReserva(ll);
        System.out.println(msg_in);
        assertEquals(expResult1, msg_in);        
        AccionsClient.ferLogOut();
        
        System.out.println("Reservar i Tornar prestec");
        loginAdministrador();
        HashMap expResult2 = new HashMap();        
        expResult2.put(STRING_CODI_RESPOSTA,"2200");
        msg_in = AccionsClient.tornarReserva(ll);
        System.out.println(msg_in);
        assertEquals(expResult2, msg_in);        
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per rebre el llistat de comentaris d'un llibre
     */
    @Test
    public void testFerLlistatComentaris() throws ClassNotFoundException {  
        System.out.println("Llistat comentaris");
        loginUsuari();
        Llibre ll = (Llibre)buscarElement("61", LLIBRE_CASE);       
        ArrayList<Usuari> llista = null;
        try {
            llista = AccionsClient.obtenirLlistatComentaris(ll);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Numero de comentaris: " + llista.size());
        AccionsClient.ferLogOut();
    }
    
    /**
     * Test per afegir un comentari
     */
    @Test
    public void testAfegirComentari() throws ClassNotFoundException {  
        System.out.println("Afegir comentari");
        loginUsuari();
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"2700");
  
        Comentari c = new Comentari(new SimpleStringProperty(""), new SimpleStringProperty("66"), new SimpleStringProperty("Test"), new SimpleStringProperty("comentari test"), new SimpleStringProperty("2022/05/30"));
        
        try {
            msg_in = AccionsClient.afegirComentari(c);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccionsClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }      

        System.out.println(msg_in);
        assertEquals(expResult, msg_in);        
        AccionsClient.ferLogOut();
    }
    
}
