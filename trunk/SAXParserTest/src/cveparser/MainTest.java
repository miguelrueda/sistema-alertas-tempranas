package cveparser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase que ejecuta una prueba para el parser CVE 1. Por medio del XML obtenido
 * de NVD http://nvd.nist.gov/download/nvdcve-2014.xml *2. Obtener la referencia
 * con URL
 *
 * @author t41507
 * @version 19.05.2014
 */
public class MainTest {

    private static final Logger LOG = Logger.getLogger(MainTest.class.getName());
    private static FileHandler fh = null;

    /**
     * Método Main
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        
        long time_start, time_end;

        String url = MainTest.class.getResource("").getPath();
        System.out.println("PATH -> " + url);

        time_start = System.currentTimeMillis();
        //InputStream is = MainTest.class.getResourceAsStream("nvdcve-2014.xml");

        CVEParser mCVEParser = new CVEParser();
        mCVEParser.doParse();
        time_end = System.currentTimeMillis();
        System.out.println("Time elapsed = {" + (time_end - time_start) + "}");
    }

    /*
     Nota:
     Time from is = 140 - 190 - 22 seconds
     Time from file = 150 - 208 - 22 seconds
     */
}