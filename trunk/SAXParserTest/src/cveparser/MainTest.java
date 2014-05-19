package cveparser;

import java.io.InputStream;
import java.util.Scanner;

/*
    Clase que prueba el parser para obtener las CVEs de la pagina NVD
    http://nvd.nist.gov/download/nvdcve-2014.xml
*/

public class MainTest {
    
    public static void main(String[] args) {
        
        CVEParser mCVEParser = new CVEParser();
        //mCVEParser.doParse();
        
        String url = MainTest.class.getResource("").getPath();
        System.out.println("PATH -> " + url);
        
        InputStream is = MainTest.class.getResourceAsStream("nvdcve-2014.xml");
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        
    }
    
}