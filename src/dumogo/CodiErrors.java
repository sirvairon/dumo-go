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
                significat = "Contrasenya no vàlida";
                break;
            case "1030":
                significat = "Format del DNI incorrecte";
                break;
            case "1031":
                significat = "El DNI està repetit";
                break;
            case "1040":
                significat = "Email incorrecte";
                break;
            case "1041":
                significat = "L'email ja existeix";
                break;
                
            /* accio = afegir_admin */
            case "2000":
                significat = "Administrador afegit correctament";
                break;
            case "2010":
                significat = "Administrador no vàlid";
                break;
            case "2020":
                significat = "Contrasenya no vàlida";
                break;
            case "2030":
                significat = "Format del DNI incorrecte";
                break;
            case "2031":
                significat = "El DNI està repetit";
                break;
            case "2040":
                significat = "Email incorrecte";
                break;
            case "2041":
                significat = "L'email ja existeix";
                break;
                
            /* accio = esborrar_usuari */
            case "3000":
                significat = "Usuari esborrat correctament";
                break;
            case "3010":
                significat = "L'usuari no existeix";
                break;   
                
            /* accio = esborrar_admin */
            case "4000":
                significat = "Administrador esborrat correctament";
                break;
            case "4010":
                significat = "L'administrador no existeix";
                break;
                
            /* accio = mostra_usuari */
            case "5000":
                significat = "Usuari retornat correctament";
                break;
            case "5010":
                significat = "Les dades de l'usuari no son vàlides";
                break;
                
            /* accio = mostra_admin */
            case "6000":
                significat = "Administrador retornat correctament";
                break;
            case "6010":
                significat = "Les dades de l'administrador no son vàlides";
                break;
                
            /* accio = comprobar_admin */
            case "7000":
                significat = "Administrador connectat correctament";
                break;
            case "7010":
                significat = "No existeix l'administrador";
                break;
            case "7020":
                significat = "Contrasenya incorrecte";
                break;
            case "7030":
                significat = "Administrador ja connectat";
                break;
                
            /* accio = comprobar_usuari */
            case "8000":
                significat = "Usuari connectat correctament";
                break;
            case "8010":
                significat = "No existeix l'usuari";
                break;
            case "8020":
                significat = "Contrasenya incorrecte";
                break;
            case "8030":
                significat = "Usuari ja connectat";
                break;
                
            /* accio = canvia_password */
            case "9000":
                significat = "Contrasenya canviada correctament";
                break;
            case "9010":
                significat = "Contrasenya no vàlida";
                break;
                
            /* accio = llista_usuaris */
            case "1100":
                significat = "Llista d'usuaris retornada correctament";
                break;
            
            /* accio = llista_admins */
            case "1200":
                significat = "Llista d'administradors retornada correctament";
                break;
                
            /* accio = modifica_usuari/admin */
            case "1300":
                significat = "Usuari/Administrador modificat correctament";
                break;
            case "1310":
                significat = "Email incorrecte";
                break;
            case "1320":
                significat = "Format del DNI incorrecte";
                break;
            case "1330":
                significat = "Contrasenya no vàlida";
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
                significat = "No s'ha trobat el llibre";
                break;
                
            /* accio = mostra_llibre */
            case "1600":
                significat = "Dades retornades correctament";
                break;
                
            /* accio = llista_llibres */
            case "1700":
                significat = "Llista de llibres retornada correctament";

            /* accio = modifica_llibre */
            case "1800":
                significat = "Modificaciò del llibre realitzada correctament";
                break;
            case "1810":
                significat = "No s'ha trobat cap llibre amb aquest nom";
                break;
            
            /* accio = puntua_llibre */
            case "1900":
                significat = "Valoracio afegida";
                break;
                
            /* accio = reserva_llibre */
            case "2100":
                significat = "Llibre reservat";
                break;
                
            /* accio = retorna_llibre */
            case "2200":
                significat = "Llibre retornat";
                break;
            case "2210":
                significat = "Llibre no prestat";
                break;
                
            /* accio = llista_prestecs i llista_prestecs_no_retornats */
            case "2300":
                significat = "Llista de prestecs retornada correctament";
                break;
                
            /* accio = llista_prestecs_usuari */
            case "2400":
                significat = "Llista de prestecs de l'usuari retornada correctament";
                break;
                
            /* accio = llista_llegits */
            case "2500":
                significat = "Llista de llibres llegits retornada correctament";
                break;
                
            /* accio = llista_prestecs_urgents */
            case "2600":
                significat = "Llista de prestecs urgents retornada correctament";
                break;
                
            /* accio = afegeix_comentari */
            case "2700":
                significat = "Comentari afegit";
                break;
                
            /* accio = elimina_comentari */
            case "2800":
                significat = "Comentari eliminat";
                break;
            case "2810":
                significat = "No existeix el comentari";
                break;
            case "2820":
                significat = "No tens permissos";
                break;
                
            /* accio = llista_comentaris */
            case "2900":
                significat = "Llista de comentaris retornada correctament";
                break;
                
            /* accio = tancar_sessio */
            case "20":
                significat = "Desconnectat correctament";
                break;
                
            case "10":
                significat = "Sessió caducada";
                break;
                
            default:
                significat = "Error desconegut";
                break;
        }
        
        return significat;
    
    }
}
