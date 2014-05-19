package cveparser;

import java.io.InputStream;

/**
 * Clase que ejecuta una prueba para el parser CVE
 *  1. Por medio del XML obtenido de NVD http://nvd.nist.gov/download/nvdcve-2014.xml
 *  *2. Obtener la referencia con URL
 * 
 * @author t41507
 * @version 19.05.2014
 */
public class MainTest {

    /**
     * Método Main
     * @param args 
     */
    public static void main(String[] args) {

        String url = MainTest.class.getResource("").getPath();
        System.out.println("PATH -> " + url);

        //InputStream is = MainTest.class.getResourceAsStream("nvdcve-2014.xml");

        CVEParser mCVEParser = new CVEParser();
        mCVEParser.doParse();

    }

}
