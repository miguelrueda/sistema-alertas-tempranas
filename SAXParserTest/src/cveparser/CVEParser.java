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
 * Clase que lleva a cabo el parseo de la referencia de los CVEs El parseo puede
 * ser de 3 formas: 1. Por archivo extensión .xml 2. Por el flujo de entrada de
 * un archivo 3. Por lectura de URL
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
    private String filtro;

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    /**
     * Mét odo que se encarga de ejecutar el parseo mediante la recepción del
     * archivo
     */
    public void doParse() {
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            saxParser = saxParserFactory.newSAXParser();
            cveHandler = new CVEHandler();
            saxParser.parse(CVEParser.class.getResourceAsStream("cvetest.xml"), cveHandler);
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
     * Mét odo que se encarga de ejecutar el parseo del flujo de entrada
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
            if (filtro == null) {
                return cveList;
            }
            return filtrar(cveList);
            
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

    private List<CVE> filtrar(List<CVE> cveList, String filtro) {
        System.out.println("Referencia obtenida - Filtro: " + filtro);
        List<CVE> resultList = cveList;
        System.out.println("Iterar en todos los elementos de la lista");
        for (CVE cve : cveList) {
            System.out.println("De cada elemento obtener su lista de SW vulnerable");
            List<VulnSoftware> listaV = cve.getVuln_soft();
            System.out.println("Ejecutando para filtrar");
            for (int i = 0; i < listaV.size(); i++) {
                if (!listaV.get(i).getVendor().contains(filtro)) {
                    cveList.remove(cve);
                }
            }
        }
        return cveList;
    }

    private List<CVE> filtrar(List<CVE> cveList) {
        LOG.log(Level.INFO, "Estoy filtrando: {0}", filtro);
        //LOG.log(Level.INFO, "Creando lista filtrada");
        List<CVE> filtrada = new ArrayList<>();
        //LOG.log(Level.INFO, "Iterar todos los elementos de la lista");
        VulnSoftware aux;
        for (CVE cve : cveList) {
            //LOG.log(Level.INFO, "Obteniendo todos los elementos de software vulnerable del elemento {0}", cve.getName());
            List<VulnSoftware> temp = cve.getVuln_soft();
            //LOG.log(Level.INFO, "Iterando todos los elementos de la lista de SW Vuln");
            for (int i = 0; i < temp.size(); i++) {
                aux = temp.get(i);
                //LOG.log(Level.INFO, "Obteniendo referencia al elemento {0}", aux.getVendor());
                if (aux.getVendor().contains(filtro)) {
                    LOG.log(Level.INFO, "Agregado: {0}", cve.getName());
                    filtrada.add(cve);
                }
            }
        }
        LOG.log(Level.INFO, "Retornar la lista filtrada");
        return filtrada;
    }

}
