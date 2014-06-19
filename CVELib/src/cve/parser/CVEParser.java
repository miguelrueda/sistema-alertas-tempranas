package cve.parser;

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
import cve.entidades.*;
import java.io.InputStreamReader;
import java.io.Reader;
import org.xml.sax.InputSource;

/**
 *
 * Clase que lleva a cabo el parseo de la referencia de los CVEs El parseo puede
 * ser de 3 formas: 1. Por archivo extensión .xml 2. Por el flujo de entrada de
 * un archivo 3. Por lectura de URL
 *
 * @author t41507
 * @version 19.05.2014
 */
public class CVEParser implements java.io.Serializable {

    /**
     * Atributos
     */
    private InputStream isEntrada;
    private static final Logger LOG = Logger.getLogger(CVEParser.class.getName());
    private static final long serialVersionUID = -1L;
    private SAXParserFactory saxParserFactory;
    private SAXParser saxParser;
    private CVEHandler cveHandler;
    private CVEHandler20 cveHandler20;
    private String filtro;

    /**
     * GETTER
     *
     * @return filtro establecido por el usuario
     */
    public String getFiltro() {
        return filtro;
    }

    /**
     * SETTER
     *
     * @param filtro filtro establecido por el usuario
     */
    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    /**
     * Mét odo de prueba que se encarga de ejecutar el parseo mediante la
     * recepción del archivo CAMBIOS PENDIENTES 23.05.2014
     */
    public void doParse() {
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            saxParser = saxParserFactory.newSAXParser();
            cveHandler = new CVEHandler();
            saxParser.parse(CVEParser.class.getResourceAsStream("cvetest.xml"), cveHandler);
            List<CVE> cveList = cveHandler.getCveList();
            LOG.log(Level.INFO, "Se encontraron: {0} entradas.", cveList.size());
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
                LOG.log(Level.INFO, "IS: Disponible: {0}", isEntrada.available());
                saxParser = saxParserFactory.newSAXParser();
                LOG.log(Level.INFO, "Creando el Manejador!");
                cveHandler = new CVEHandler();
                LOG.log(Level.INFO, "Parseando el flujo de entrada");
                saxParser.parse(isEntrada, cveHandler);
                List<CVE> cveList = cveHandler.getCveList();
                LOG.log(Level.INFO, "Lista obtenida - Se encontraron: {0} entradas.", cveList.size());
                for (CVE cve : cveList) {
                    System.out.println(cve);
                }
            }

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException | SAXException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Mét odo que se encarga de obtener la lista de CVEs a partir de un flujo de
     * entrada establecido por un XML o por una URL
     *
     * @param is_ref Referencia al flujo de entrada
     * @return Lista de CVEs
     */
    public List<CVE> getListCVE(InputStream is_ref) {
        isEntrada = is_ref;
        saxParserFactory = SAXParserFactory.newInstance();
        List<CVE> cveList = new ArrayList<>();
        try {
            if (isEntrada.available() != 0) {
                LOG.log(Level.INFO, "IS: Disponible: {0}", isEntrada.available());
                saxParser = saxParserFactory.newSAXParser();
                LOG.log(Level.INFO, "Creando el manejador!");
                cveHandler = new CVEHandler();
                LOG.log(Level.INFO, "Parseando el flujo de entrada");
                //TEST
                Reader reader = new InputStreamReader(isEntrada, "UTF-8");
                InputSource issrc = new InputSource(reader);
                issrc.setEncoding("UTF-8");
                //saxParser.parse(isEntrada, cveHandler);
                saxParser.parse(issrc, cveHandler);
                cveList = cveHandler.getCveList();
                LOG.log(Level.INFO, "Lista obtenida - Se encontraron {0} entradas", cveList.size());
                if (filtro.length() == 0) {
                    LOG.log(Level.INFO, "No se encontro filtro!");
                    return cveList;
                } else {
                    LOG.log(Level.INFO, "Filtrando la lista con el parámetro: {0}", filtro);
                    return filtrar(cveList);
                }
            } else {
                LOG.log(Level.SEVERE, "No se pudo obtener el flujo de entrada");
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOG.log(Level.SEVERE, "Ocurrio una excepción en el parser: {0}", e.getMessage());
        }
        LOG.log(Level.SEVERE, "Retornando lista vacia!");
        return new ArrayList<>();
    }
    
    /**
     * File file = new File("c:\\file-utf.xml");
InputStream inputStream= new FileInputStream(file);
Reader reader = new InputStreamReader(inputStream,"UTF-8");
 
InputSource is = new InputSource(reader);
is.setEncoding("UTF-8");
 
saxParser.parse(is, handler);
     */

    /**
     * Mét odo que se encarga de realizar el parseo para el xml version 1.1
     */
    public void doParse20() {
        saxParserFactory = SAXParserFactory.newInstance();
        try {
            saxParser = saxParserFactory.newSAXParser();
            LOG.log(Level.INFO, "Creando el manejador 2.0");
            cveHandler20 = new CVEHandler20();
            saxParser.parse(CVEParser.class.getResourceAsStream("test20.xml"), cveHandler20);
            List<CVE> cveList = cveHandler20.getCveList();
            LOG.log(Level.INFO, "Lista obtenida - Se encontraron {0} entradas.", cveList.size());
            for (CVE cve : cveList) {
                System.out.println(cve);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.log(Level.SEVERE, "Ocurrio una excepción en el parser 2.0, {0}", e.getMessage());
        }
    }

    /**
     * private List<CVE> filtrar(List<CVE> cveList, String filtro) {
     * System.out.println("Referencia obtenida - Filtro: " + filtro); List<CVE>
     * resultList = cveList; System.out.println("Iterar en todos los elementos
     * de la lista"); for (CVE cve : cveList) { System.out.println("De cada
     * elemento obtener su lista de SW vulnerable"); List<VulnSoftware> listaV =
     * cve.getVuln_soft(); System.out.println("Ejecutando para filtrar"); for
     * (int i = 0; i < listaV.size(); i++) { if
     * (!listaV.get(i).getVendor().contains(filtro)) { cveList.remove(cve); } }
     * } return cveList; }
     *
     */
    /**
     * Mét odo que se encarga de realizar el filtro a la lista obtenida de CVEs
     *
     * @param cveList una lista de cves obtenida de un flujo de entrada
     * @return una lista de CVEs filtrada a partir de un parámetro ingresado por
     * el usuario
     */
    private List<CVE> filtrar(List<CVE> cveList) {
        for (CVE cve : cveList) {
            LOG.log(Level.INFO, cve.getName());
        }
        LOG.log(Level.INFO, "Filtrando con el parámetro: {0}", filtro);
        //Crear una lista para agregar los elementos que cumplen con los valores del filtro
        //LOG.log(Level.INFO, "Creando lista filtrada");
        List<CVE> filtrada = new ArrayList<>();
        //Iterar todos los elementos de la lista obtenida por la referencia
        //LOG.log(Level.INFO, "Iterando la lista de cves");
        for (CVE cve : cveList) {
            //Obtener la lista de SW vulnerable de cada elemento de la lista
            //LOG.log(Level.INFO, "Obteniendo lista de SW vulnerable");
            List<VulnSoftware> temp = cve.getVuln_soft();
            if (!(temp == null || temp.isEmpty())) {
                //LOG.log(Level.INFO, "Iterando los elementos de la lista de SW vulnerable");
                for (int i = 0; i < temp.size(); i++) {
                //Variable temporal para almacenar el software vulnerable que se esta iterando

                    VulnSoftware aux = temp.get(i);
                //LOG.log(Level.INFO, "Obteniendo el sw vulnerable {0}", cve.getName());
                    //Si el proveedor del SW se parece al filtro ingresado ingresarlo a la lista de resultado
                    if (aux.getVendor().toLowerCase().contains(filtro.toLowerCase())) {
                        LOG.log(Level.INFO, "Elemento agregado: {0}", cve.getName());
                        filtrada.add(cve);
                    }
                }
            } else {
                LOG.log(Level.SEVERE, "No hay SW vulnerable");
            }
            //Iterar todos los elementos en la lista de SW vulnerable
        }
        LOG.log(Level.INFO, "Retornando la lista filtrada - Se encontraron {0}", filtrada.size());
        return filtrada;
    }

}
