package cveparser;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Clase que ejecuta una prueba para el parser CVE 1. Por medio del XML obtenido
 * de NVD http://nvd.nist.gov/download/nvdcve-2014.xml 2. Obtener la referencia
 * al IS del archivo 3. Obtener la referencia por medio del URL del archivo con
 * URL
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
    public static void main(String[] args) {

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
