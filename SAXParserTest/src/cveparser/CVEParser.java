package cveparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
 * El parseo puede ser de 3 formas:
 * 1. Por archivo extensión .xml
 * 2. Por el flujo de entrada de un archivo
 * 3. Por lectura de URL
 *
 * @author t41507
 * @version 19.05.2014
 */
public class CVEParser {

    private InputStream isEntrada;
    private static final Logger LOG = Logger.getLogger(CVEParser.class.getName());
    private SAXParserFactory saxParserFactory;
    private SAXParser saxParser;
    private CVEHandler cveHandler;
    private CVEHandler20 cveHandler20;
    
    /**
     * Método que se encarga de ejecutar el parseo mediante la recepción del archivo
     */
    public void doParse() {
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            saxParser = saxParserFactory.newSAXParser();
            cveHandler = new CVEHandler();
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

    /**
     * Método que se encarga de ejecutar el parseo del flujo de entrada
     *
     * @param is_ref referencia al flujo de entrada del archivo
     */
    public void doParse(InputStream is_ref) {
        isEntrada = is_ref;
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            if (isEntrada.available() != 0) {
                System.out.println("Available: " + isEntrada.available());
            }
            saxParser = saxParserFactory.newSAXParser();
            cveHandler = new CVEHandler();
            saxParser.parse(isEntrada, cveHandler);
            List<CVE> cveList = cveHandler.getCveList();
            System.out.println("Se encontraron: " + cveList.size() + " entradas.");
            for (CVE cve : cveList) {
                System.out.println(cve);
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException | SAXException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    public List<CVE> getListCVE(InputStream is_ref) {
        isEntrada = is_ref;
        saxParserFactory = SAXParserFactory.newInstance();
        List<CVE> cveList = null;
        try {
            if (isEntrada.available() != 0) {
                System.out.println("Available: " + isEntrada.available());
            }
            saxParser = saxParserFactory.newSAXParser();
            cveHandler = new CVEHandler();
            saxParser.parse(isEntrada, cveHandler);
            cveList = cveHandler.getCveList();
            return cveList;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOG.log(Level.SEVERE, "Excepción en el parser: {0}", e);
        }
        return new ArrayList<>();
    }
    
    public void doParse20() {
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            saxParser = saxParserFactory.newSAXParser();
            cveHandler20 = new CVEHandler20();
            saxParser.parse(CVEParser.class.getResourceAsStream("test20.xml"), cveHandler20);
            List<CVE> cveList = cveHandler20.getCveList();
            System.out.println("Se encontraron: " + cveList.size() + " entradas.");
            for (CVE cve : cveList) {
                System.out.println(cve);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
    /*
    public void doParse20(InputStream is_ref) {
        
    }*/


}
