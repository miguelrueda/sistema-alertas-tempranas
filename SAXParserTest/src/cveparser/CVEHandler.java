package cveparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Clase que crea el manejador para el XML
 *
 * @author t41507
 * @version 20.05.2014
 */
public class CVEHandler extends DefaultHandler {

    /**
     * Atributos
     */
    private List<CVE> cveList = null;
    private CVE nuevoCVE;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<CVEReference> listRefs;
    private CVEReference nRef;
    private List<VulnSoftware> listVulnSoft;
    private VulnSoftware nVulnSoft;
    private List<Version> listVersions;
    private Version nVersion;
    private static final Logger LOG = Logger.getLogger(CVEHandler.class.getName());
    boolean bDescript = false;

    /**
     * GETTER
     *
     * @return la lista de todos los CVES parseados
     */
    public List<CVE> getCveList() {
        return cveList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("ENTRY")) {
            String name = attributes.getValue("name");
            nuevoCVE = new CVE(name);
            String severity = attributes.getValue("severity");
            String published = attributes.getValue("published");
            String modified = attributes.getValue("modified");
            String cvss_score = attributes.getValue("CVSS_score");
            try {
                nuevoCVE.setPublished(formatter.parse(published));
                nuevoCVE.setModified(formatter.parse(modified));
                nuevoCVE.setSeverity(severity);
                nuevoCVE.setCVSS_score(cvss_score);
            } catch (ParseException e) {
                LOG.log(Level.SEVERE, e.getMessage());
            }
            if (cveList == null) {
                cveList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("DESCRIPT")) {
            bDescript = true;
        } else if (qName.equalsIgnoreCase("REF")) {
            String url = attributes.getValue("url");
            String source = attributes.getValue("source");
            nRef = new CVEReference(url, source);
            if (listRefs == null) {
                listRefs = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("PROD")) {
            String vendor = attributes.getValue("vendor");
            String name = attributes.getValue("name");
            nVulnSoft = new VulnSoftware(vendor, name);
            if (listVulnSoft == null) {
                listVulnSoft = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("VERS")) {
            String num = attributes.getValue("num");
            nVersion = new Version(num);
            String edition = attributes.getValue("edition");
            if (edition != null) {
                nVersion.setEdition(edition);
            } else {
                nVersion.setEdition("-1");
            }
            if (listVersions == null) {
                listVersions = new ArrayList<>();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (bDescript) {
            nuevoCVE.setDescription(new String(ch, start, length));
            bDescript = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("REF")) {
            listRefs.add(nRef);
        } else if (qName.equalsIgnoreCase("PROD")) {
            nVulnSoft.setVersion(listVersions);
            listVulnSoft.add(nVulnSoft);
            listVersions = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("VERS")) {
            listVersions.add(nVersion);
        }
        if (qName.equalsIgnoreCase("ENTRY")) {
            nuevoCVE.setReferences(listRefs);
            nuevoCVE.setVuln_soft(listVulnSoft);
            listRefs = new ArrayList<>();
            listVersions = new ArrayList<>();
            listVulnSoft = new ArrayList<>();
            cveList.add(nuevoCVE);
        }
    }

}
