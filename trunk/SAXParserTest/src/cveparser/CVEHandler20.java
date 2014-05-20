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

    private List<CVE> cveList = null;
    private CVE nuevoCVE;
    private boolean bPublished = false;

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
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.println("CIERRA: " + qName);
        if (qName.equalsIgnoreCase("ENTRY")) {
            nuevoCVE.setCVSS_score("-1");
            nuevoCVE.setDescription("NON");
            nuevoCVE.setModified(new Date());
            nuevoCVE.setReferences(new ArrayList<CVEReference>());
            nuevoCVE.setSeverity("NON");
            nuevoCVE.setVuln_soft(new ArrayList<VulnSoftware>());
            cveList.add(nuevoCVE);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (bPublished) {
            try {
                //2014-01-31T18:55:04.503-05:00
                String published_date = new String(ch, start, length);
                String [] spl_pub = published_date.split("T");
                String date = spl_pub[0];
                //System.out.println(date);
                String time = spl_pub[1];
                //System.out.println(time);
                String [] spl_time = time.split("\\.");
                String res_time = spl_time[0];
                //System.out.println(res_time);
                String datetime = date + " " + res_time;
                
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                nuevoCVE.setPublished(formatter.parse(datetime));
                bPublished = false;
            } catch (ParseException ex) {
                Logger.getLogger(CVEHandler20.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
