/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dumogo;

/**
 *
 * @author marcd
 */
public class CodiErrors {
    
    private static String significat;
    
    public static String ComprobarCodiError (String num_codi){
        significat = "Error desconegut";
        
        switch(num_codi){
            case "-1":
                significat = "No es pot connectar amb el servidor";
                break;
                
            case "0":
                significat = "Error desconegut";
                break;
                
            /* accio = afegir_usuari */
            case "1000":
                significat = "Usuari afegit correctament";
                break;
            case "1010":
                significat = "Usuari no vàlid";
                break;
            case "1020":
                significat = "contrasenya no valida";
                break;
            case "1030":
                significat = "Format DNI Incorrecte";
                break;
            case "1031":
                significat = "DNI Repetit";
                break;
            case "1040":
                significat = "email incorrecte";
                break;
            case "1041":
                significat = "email ja existeix";
                break;
                
            /* accio = afegir_admin */
            case "2000":
                significat = "Admin afegit correctament";
                break;
            case "2010":
                significat = "Usuari no vàlid";
                break;
            case "2020":
                significat = "contrasenya no valida";
                break;
            case "2030":
                significat = "Format DNI Incorrecte";
                break;
            case "2031":
                significat = "DNI Repetit";
                break;
            case "2040":
                significat = "email incorrecte";
                break;
            case "2041":
                significat = "email ja existeix";
                break;
                
            /* accio = esborrar_usuari */
            case "3000":
                significat = "Esborrat correctament";
                break;
            case "3010":
                significat = "usuari inexistent";
                break;
            case "3020":
                significat = "ERROR: no s'ha pogut esborrar";
                break;      
                
            /* accio = esborrar_admin */
            case "4000":
                significat = "admin esborrat";
                break;
            case "4010":
                significat = "usuari inexistent";
                break;
            case "4020":
                significat = "ERROR: no s'ha pogut esborrar";
                break;
                
            /* accio = modificar_usuari */
            case "5000":
                significat = "usuari modificat correctament";
                break;
            case "5010":
                significat = "dades no vàlides";
                break;
            case "5020":
                significat = "ERROR: no s'ha pogut modificar";
                break;
                
            /* accio = modificar_admin */
            case "6000":
                significat = "Modificat correctament";
                break;
            case "6010":
                significat = "dades no vàlides";
                break;
            case "6020":
                significat = "ERROR: no s'ha pogut modificar";
                break;
                
            /* accio = comprobar_admin */
            case "7000":
                significat = "login ok";
                break;
            case "7010":
                significat = "admin no existent";
                break;
            case "7020":
                significat = "contrasenya incorrecte";
                break;
            case "7030":
                significat = "admin ja connectat";
                break;
                
            /* accio = comprobar_usuari */
            case "8000":
                significat = "login ok";
                break;
            case "8010":
                significat = "usuari no existent";
                break;
            case "8020":
                significat = "contrasenya incorrecte";
                break;
            case "8030":
                significat = "usuari ja connectat";
                break;
                
            /* accio = llista_usuaris */
            case "1100":
                significat = "Llista retornada correctament";
                break;
            
            /* accio = llista_admins */
            case "1200":
                significat = "Llista retornada correctament";
                break;
                
            /* accio = modifica_usuari/admin */
            case "1300":
                significat = "Usuari Modificat";
                break;
            case "1310":
                significat = "Format_email_incorrecte";
                break;
            case "1320":
                significat = "format_dni_incorrecte";
                break;
            case "1330":
                significat = "format_password_incorrecte";
                break;
                
            /* accio = canvia_password */
            case "9000":
                significat = "Canvi realitzat";
                break;
                
            /* accio = afegir_llibre */
            case "1400":
                significat = "Llibre afegit";
                break;
                
            /* accio = esborrar_llibre */
            case "1500":
                significat = "Llibre esborrat";
                break;
            case "1510":
                significat = "No s'ha trobat llibre amb aquest id";
                break;
            
            /* accio = tancar_sessio */
            case "20":
                significat = "Sessio tancada correctament";
                break;
                
            case "10":
                significat = "Sessio caducada";
                break;
                
            default:
                significat = "Error desconegut";
                break;
        }
        
        return significat;
    
    }
}
