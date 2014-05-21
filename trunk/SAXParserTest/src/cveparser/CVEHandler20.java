package cveparser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CVEHandler20 extends DefaultHandler {

    private static final Logger LOG = Logger.getLogger(CVEHandler20.class.getName());
    private List<CVE> cveList = null;
    private CVE nuevoCVE;
    private List<CVEReference> listRefs;
    private CVEReference nRef;
    private List<VulnSoftware> listVulnSoft;
    private VulnSoftware nVulnSoft;
    private List<Version> listVersions;
    private Version nVersion;
    private CVSS nCVSS;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean bPublished = false;
    private boolean bModified = false;
    private boolean bSummary = false;
    private boolean bCVScore = false;
    private boolean bVSource = false;
    private boolean bVProduct = false;
    private boolean bBaseMetrics = false;
    private boolean bAV = false;
    private boolean bAC = false;
    private boolean bAu = false;
    private boolean bC = false; //Confidentialy impact
    private boolean bI = false; //Integrity
    private boolean bAI = false; //Availability Impact
    private String stVector;

    public List<CVE> getCveList() {
        return cveList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //System.out.println("ABRE: " + qName);
        if (qName.equalsIgnoreCase("ENTRY")) {
            String name = attributes.getValue("id");
            nuevoCVE = new CVE(name);
            if (null == cveList) {
                cveList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("vuln:published-datetime")) {
            bPublished = true;
        } else if (qName.equalsIgnoreCase("vuln:last-modified-datetime")) {
            bModified = true;
        } else if (qName.equalsIgnoreCase("vuln:summary")) {
            bSummary = true;
        } else if (qName.equalsIgnoreCase("cvss:score")) {
            bCVScore = true;
        } else if (qName.equalsIgnoreCase("vuln:source")) {
            nRef = new CVEReference();
            if (null == listRefs) {
                listRefs = new ArrayList<>();
            }
            bVSource = true;
        } else if (qName.equalsIgnoreCase("vuln:reference")) {
            String url = attributes.getValue("href");
            nRef.setUrl(url);
        } else if (qName.equalsIgnoreCase("vuln:product")) {
            nVulnSoft = new VulnSoftware();
            if (null == listVulnSoft) {
                listVulnSoft = new ArrayList<>();
            }
            if (null == listVersions) {
                listVersions = new ArrayList<>();
            }
            bVProduct = true;
        } else if (qName.equalsIgnoreCase("cvss:base_metrics")) {
            stVector = "";
            bBaseMetrics = true;
        } else if (qName.equalsIgnoreCase("cvss:access-vector")) {
            bAV = true;
        } else if (qName.equalsIgnoreCase("cvss:access-complexity")) {
            bAC = true;
        } else if (qName.equalsIgnoreCase("cvss:authentication")) {
            bAu = true;
        } else if (qName.equalsIgnoreCase("cvss:confidentiality-impact")) {
            bC = true;
        } else if (qName.equalsIgnoreCase("cvss:integrity-impact")) {
            bI = true;
        } else if (qName.equalsIgnoreCase("cvss:availability-impact")) {
            bAI = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.println("CIERRA: " + qName);
        if (qName.equalsIgnoreCase("ENTRY")) {
            nuevoCVE.setSeverity("ND");
            nuevoCVE.setReferences(listRefs);
            nuevoCVE.setVuln_soft(listVulnSoft);
            listRefs = new ArrayList<>();
            listVulnSoft = new ArrayList<>();
            cveList.add(nuevoCVE);
        } else if (qName.equalsIgnoreCase("vuln:references")) {
            listRefs.add(nRef);
        } else if (qName.equalsIgnoreCase("vuln:product")) {
            listVulnSoft.add(nVulnSoft);
            listVersions = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("cvss:base_metrics")) {
            nCVSS.setVector("(" + stVector + ")");
            stVector = "";
            nuevoCVE.setCVSS(nCVSS);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (bPublished) {
            try {
                //2014-01-31T18:55:04.503-05:0
                nuevoCVE.setPublished(formatter.parse(getFormatedDate(new String(ch, start, length))));
                bPublished = false;
            } catch (ParseException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } else if (bModified) {
            try {
                nuevoCVE.setModified(formatter.parse(getFormatedDate(new String(ch, start, length))));
                bModified = false;
            } catch (ParseException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } else if (bSummary) {
            nuevoCVE.setDescription(new String(ch, start, length));
            bSummary = false;
        } else if (bCVScore) {
            nCVSS = new CVSS(new String(ch, start, length), "");
            bCVScore = false;
        } else if (bVSource) {
            nRef.setSource(new String(ch, start, length));
            bVSource = false;
        } else if (bVProduct) {
            //cpe:/a:mariadb:mariadb:5.5.34
            String vulnProduct = new String(ch, start, length);
            String[] name_vendor = getNameVendor(vulnProduct);
            nVulnSoft.setVendor(name_vendor[0]);
            nVulnSoft.setName(name_vendor[1]);
            nVersion = new Version(name_vendor[2]);
            nVersion.setEdition(name_vendor[3]);
            listVersions.add(nVersion);
            nVulnSoft.setVersion(listVersions);
            bVProduct = false;
        } else if (bAV) {
            stVector += "AV:" + new String(ch, start, length).charAt(0) + "/";
            bAV = false;
        } else if (bAC) {
            stVector += "AC:" + new String(ch, start, length).charAt(0) + "/";
            bAC = false;
        } else if (bAu) {
            stVector += "Au:" + new String(ch, start, length).charAt(0) + "/";
            bAu = false;
        } else if (bC) {
            stVector += "C:" + new String(ch, start, length).charAt(0) + "/";
            bC = false;
        } else if (bI) {
            stVector += "I:" + new String(ch, start, length).charAt(0) + "/";
            bI = false;
        } else if (bAI) {
            stVector += "A:" + new String(ch, start, length).charAt(0) + "/";
            bAI = false;
        }
    }

    private String getFormatedDate(String datetime) {
        String[] spl_datetime = datetime.split("T");
        String[] spl_time = spl_datetime[1].split("\\.");
        return spl_datetime[0] + " " + spl_time[0];
    }

    //cpe:/a:mariadb:mariadb:5.5.34
    private String getFName(String vulnProduct) {
        String[] spl_prod = vulnProduct.split(":");
        for (String string : spl_prod) {
            System.out.println(string);
        }
        return "temp";
    }

    //cpe:/a:mariadb:mariadb:5.5.34
    //cpe:/a:apache:camel:2.0.0:m1
    private String[] getNameVendor(String vulnProduct) {
        String[] name_vendor = new String[5];
        String[] spl_prod = vulnProduct.split(":/a:|:/o:");
        String[] spl_name = spl_prod[1].split(":");
        name_vendor[0] = spl_name[0];
        name_vendor[1] = spl_name[1];
        name_vendor[2] = spl_name[2];
        if (spl_name.length == 3) {
            name_vendor[3] = "-1";
        } else if (spl_name.length == 4) {
            name_vendor[3] = spl_name[3];
        } else if (spl_name.length == 5) {
            name_vendor[3] = spl_name[4];
        }
        return name_vendor;
    }

}