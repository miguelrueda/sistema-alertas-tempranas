package cvsstest;

import java.util.StringTokenizer;

public class CVSSParser {

    public static void main(String[] args) {
        String vector = "(AV:L/AC:H/Au:N/C:N/I:P/A:C)"; //"(AV:A/AC:L/Au:M/C:C/I:N/A:P)"; //(AV:L/AC:H/Au:N/C:N/I:P/A:C)";
        StringTokenizer tokens = new StringTokenizer(vector, "(:/)");
        int nDatos = tokens.countTokens();
        String[] vec = new String[nDatos];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            vec[i] = tokens.nextToken().toString();
            i++;
        }
        vec[0] = "Vector de Acceso: ";
        switch (vec[1].toString()) {
            case "L":
                vec[1] = "Acceso Local";
                break;
            case "A":
                vec[1] = "Red Adyacente";
                break;
            case "N":
                vec[1] = "Red";
                break;
        }
        vec[2] = "Complejidad de Acceso: ";
        switch (vec[3].toString()) {
            case "H":
                vec[3] = "Alta";
                break;
            case "M":
                vec[3] = "Media";
                break;
            case "L":
                vec[3] = "Baja";
                break;
        }
        vec[4] = "Autenticación: ";
        switch (vec[5].toString()) {
            case "N":
                vec[5] = "No requerida";
                break;
            case "S":
                vec[5] = "Requiere una instancia";
                break;
            case "M":
                vec[5] = "Requiere múltiples instancias";
                break;
        }
        vec[6] = "Impacto en Confidencialidad: ";
        switch (vec[7].toString()) {
            case "N":
                vec[7] = "No tiene";
                break;
            case "P":
                vec[7] = "Parcial";
                break;
            case "C":
                vec[7] = "Completo";
                break;
        }
        vec[8] = "Impacto en integridad: ";
        //N = None, P = Partial, C = Complete
        switch (vec[9].toString()) {
            case "N":
                vec[9] = "No tiene";
                break;
            case "P":
                vec[9] = "Parcial";
                break;
            case "C":
                vec[9] = "Completo";
                break;
        }
        vec[10] = "Impacto en Disponibilidad: ";
        // N = None, P = Partial, C = Complete
        switch (vec[11].toString()) {
            case "N":
                vec[11] = "No tiene";
                break;
            case "P":
                vec[11] = "Parcial";
                break;
            case "C":
                vec[11] = "Completo";
                break;
        }
        for (String ele1 : vec) {
            System.out.println(ele1);
        }
    }

}

/**
 *     //(AV:[L,A,N]/AC:[H,M,L]/Au:[N,S,M]/C:[N,P,C]/I:[N,P,C]/A:[N,P,C]) private
 * String describirVector(String vector) {
 *
 * return tokens.toString(); }
 *
 */
