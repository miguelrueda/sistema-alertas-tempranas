package cveparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * Clase que lleva a cabo el parseo de la referencia de los CVEs
 *
 * @author t41507
 * @version 19.05.2014
 */
public class CVEParser {

    private InputStream is;
    private static final Logger LOG = Logger.getLogger(CVEParser.class.getName());
    private final int total_entradas = 0;

    /**
     * Método que se encarga de ejecutar el parseo del flujo de entrada
     *
     * @param is_ref referencia al flujo de entrada del archivo
     */
    public void doParse(InputStream is_ref) {
        is = is_ref;
        try {
            if (is.available() != 0) {
                System.out.println("Available: " + is.available());
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método de prueba que lee directamente el archivo
     */
    public void doParse() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            CVEHandler cveHandler = new CVEHandler();
            saxParser.parse(CVEParser.class.getResourceAsStream("nvdcve-2014.xml"), cveHandler);
            List<CVE> cveList = cveHandler.getCveList();
            System.out.println("Se encontraron: " + cveList.size() + " entradas.");
            for (CVE cve : cveList) {
                System.out.println(cve);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

}
