/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dumogo;

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * Test per iniciar sessió com usuari
     */
    @Test
    public void testFerLogInUsuari() {
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
     * Test per iniciar sessió com usuari
     */
    @Test
    public void testFerLogInAdministrador() {
        System.out.println("ferLogIn");
        String usuari = "marc45";
        String pass = "1234";
        String tipus = "Administrador";
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"8000");
        HashMap result = AccionsClient.ferLogIn(usuari, pass, tipus);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test per iniciar sessió
     */
    @Test
    public void testFerLogOut() {
        System.out.println("ferLogOut");
        HashMap expResult = new HashMap();        
        expResult.put(STRING_CODI_RESPOSTA,"20");
        HashMap result = AccionsClient.ferLogOut();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}
