/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dumogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    
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
        String usuari = "Pep25";
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
     * Test per tancar el llistat de usuaris
     */
    @Test
    public void testFerLlistatUsuaris() {        
        System.out.println("Llistat usuaris");
        String tipus = "usuaris";
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
     * Test per tancar el llistat de administradors
     */
    @Test
    public void testFerLlistatAdministradors() {        
        System.out.println("Llistat administdaors");
        String tipus = "administradors";
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
     * Test per tancar el llistat de llibres
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
    
}
