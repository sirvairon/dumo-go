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
        significat = "Error no contemplat";
        
        switch(num_codi){
            /* accio = afegir_usuari */
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
            case "3010":
                significat = "usuari inexistent";
                break;
            case "3020":
                significat = "ERROR: no s'ha pogut esborrar";
                break;      
                
            /* accio = esborrar_admin */
            case "4010":
                significat = "usuari inexistent";
                break;
            case "4020":
                significat = "ERROR: no s'ha pogut esborrar";
                break;
                
            /* accio = modificar_usuari */
            case "5010":
                significat = "dades no vàlides";
                break;
            case "5020":
                significat = "ERROR: no s'ha pogut modificar";
                break;
                
            /* accio = modificar_admin */
            case "6010":
                significat = "dades no vàlides";
                break;
            case "6020":
                significat = "ERROR: no s'ha pogut modificar";
                break;
                
            /* accio = comprobar_usuari */
            case "7010":
                significat = "usuari no existent";
                break;
            case "7020":
                significat = "contrasenya incorrecte";
                break;
            case "7030":
                significat = "ERROR DB";
                break;
                
            /* accio = comprobar_usuari */
            case "8010":
                significat = "usuari no existent";
                break;
            case "8020":
                significat = "contrasenya incorrecte";
                break;
            case "8030":
                significat = "ERROR DB";
                break;
                
            default:
                significat = "OK";
                break;
        }
        
        return significat;
    
    }
}
