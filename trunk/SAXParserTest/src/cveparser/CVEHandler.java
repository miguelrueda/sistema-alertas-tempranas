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
 * @version
 */
public class CVEHandler extends DefaultHandler {

    private List<CVE> cveList = null;
    private CVE nuevoCVE = null;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<CVEReference> refList;
    private CVEReference nuevaRef;
    private List<VulnSoftware> vulnSoftList;
    private VulnSoftware nuevoVulnSoft;
    private List<Version> verList;
    private Version nuevaVer;
    private static final Logger LOG = Logger.getLogger(CVEHandler.class.getName());

    public List<CVE> getCveList() {
        return cveList;
    }

    boolean bDescript = false;
    boolean bRef = false;

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
            nuevaRef = new CVEReference(url, source);
            if (refList == null) {
                refList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("PROD")) {
            String vendor = attributes.getValue("vendor");
            String name = attributes.getValue("name");
            nuevoVulnSoft = new VulnSoftware(vendor, name, null);
            if (vulnSoftList == null) {
                vulnSoftList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("VERS")) {
            String num = attributes.getValue("num");
            nuevaVer = new Version(num);
            String edition = attributes.getValue("edition");
            if (edition != null) {
                nuevaVer.setEdition(edition);
            }
            if (verList == null) {
                verList = new ArrayList<>();
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
            refList.add(nuevaRef);
        } else if (qName.equalsIgnoreCase("PROD")) {
            nuevoVulnSoft.setVersion(verList);
            vulnSoftList.add(nuevoVulnSoft);
        } else if (qName.equalsIgnoreCase("VERS")) {
            verList.add(nuevaVer);
        }
        if (qName.equalsIgnoreCase("ENTRY")) {
            nuevoCVE.setReferences(refList);
            nuevoCVE.setVuln_soft(vulnSoftList);
            refList = new ArrayList<>();
            verList = new ArrayList<>();
            vulnSoftList = new ArrayList<>();
            cveList.add(nuevoCVE);
        }
    }

}
