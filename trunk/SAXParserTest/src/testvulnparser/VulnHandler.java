package testvulnparser;

import cveparser.Version;
import cveparser.VulnSoftware;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class VulnHandler extends DefaultHandler {

    private List<VulnSoftware> listVulnSoft;
    private VulnSoftware vulnSoft;
    private List<Version> listVersion;
    private Version nVersion;

    public List<VulnSoftware> getListVulnSoft() {
        return listVulnSoft;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        /*
        System.out.print("<" + qName);
        if (attributes.getLength() == 0) {
            System.out.print(">\n");
        } else {
            System.out.print(" ");
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(attributes.getLocalName(i) + " => " + attributes.getValue(i) + " ");
            }
            System.out.print(">\n");
        }
                */
        if (qName.equalsIgnoreCase("PROD")) {
            String vendor = attributes.getValue("vendor");
            String name = attributes.getValue("name");
            vulnSoft = new VulnSoftware(vendor, name);
            if (null == listVulnSoft) {
                listVulnSoft = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("VERS")) {
            String num = attributes.getValue("num");
            nVersion = new Version(num);
            if (attributes.getLength() == 1) {
                nVersion.setEdition("-1");
            } else {
                nVersion.setEdition(attributes.getValue("edition"));
            }
            if (null == listVersion) {
                listVersion = new ArrayList<>();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.print("</ " + qName + ">\n");
        if (qName.equalsIgnoreCase("PROD")) {
            //Agregar lista versiones
            vulnSoft.setVersion(listVersion);
            listVulnSoft.add(vulnSoft);
            listVersion = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("VERS")) {
            listVersion.add(nVersion);
        }
    }

}
