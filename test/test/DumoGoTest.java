/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package test;

import controllers.AdminController;
import dumogo.AccionsClient;
import dumogo.Usuari;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.TableView;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author marcd
 */
public class DumoGoTest {
    
    private static String nom_user_actual;
    private static String codi_connexio_client;
    private static final String STRING_CODI_CONNEXIO = "codi";
    private static int codi_resposta;
    private static final String STRING_CODI_RESPOSTA = "codi_resposta";
    
    public DumoGoTest() {
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

    /**
     * Test of getNom_user_actual method, of class AccionsClient.
     */
    @org.junit.Test
    public void testGetNom_user_actual() {
        System.out.println("getNom_user_actual");
        String expResult = "";
        String result = AccionsClient.getNom_user_actual();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ferLogIn method, of class AccionsClient.
     */
    @org.junit.Test
    public void testFerLogIn() {
        System.out.println("ferLogIn");
        String usuari = "Marc45";
        String pass = "12345";
        String tipus = "Usuari";
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"8000");
        HashMap result = AccionsClient.ferLogIn(usuari, pass, tipus);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of ferLogOut method, of class AccionsClient.
     */
    @org.junit.Test
    public void testFerLogOut() {
        System.out.println("ferLogOut");
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"20");
        HashMap result = AccionsClient.ferLogOut();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of agefirUsuari method, of class AccionsClient.
     */
    @org.junit.Test
    public void testAgefirUsuari() throws Exception {
        System.out.println("agefirUsuari");
        HashMap u = new HashMap();
        u.put("nom_user", "test2");
        u.put("password", "12345");
        u.put("dni", "70796493D");
        u.put("correu", "z@z.es");
        u.put("data_alta", "2022-04-19");
        u.put("admin_alta", "marc45");
        u.put("nom_cognoms", "test2 test2");
        
        Usuari usuari = new Usuari(u);
        System.out.println(usuari.getNom_user());
        
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"1000");
        
        HashMap result = AccionsClient.agefirUsuari(usuari);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of obtenirUsuari method, of class AccionsClient.
     */
    @org.junit.Test
    public void testObtenirUsuari() throws Exception {
        System.out.println("obtenirUsuari");
        HashMap expResult = null;
        HashMap result = AccionsClient.obtenirUsuari();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarUsuari method, of class AccionsClient.
     */
    @org.junit.Test
    public void testEliminarUsuari() throws Exception {
        System.out.println("eliminarUsuari");
        Usuari usuari = null;
        HashMap expResult = null;
        HashMap result = AccionsClient.eliminarUsuari(usuari);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificarUsuari method, of class AccionsClient.
     */
    @org.junit.Test
    public void testModificarUsuari() throws Exception {
        System.out.println("modificarUsuari");
        Usuari usuari = null;
        HashMap expResult = null;
        HashMap result = AccionsClient.modificarUsuari(usuari);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenirLlistatUsuaris method, of class AccionsClient.
     */
    @org.junit.Test
    public void testObtenirLlistatUsuaris() throws Exception {
        System.out.println("obtenirLlistatUsuaris");
        ArrayList expResult = null;
        ArrayList result = AccionsClient.obtenirLlistatUsuaris();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of usuariAHashmap method, of class AccionsClient.
     */
    @org.junit.Test
    public void testUsuariAHashmap() {
        System.out.println("usuariAHashmap");
        HashMap hashmap = null;
        Usuari usuari = null;
        AccionsClient.usuariAHashmap(hashmap, usuari);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
