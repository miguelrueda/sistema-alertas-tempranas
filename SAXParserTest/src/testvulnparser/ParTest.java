package testvulnparser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class ParTest {

    private static final Logger LOG = Logger.getLogger(ParTest.class.getName());

    public static void main(String[] args) {
        /*try {
             SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
             VulnHandler handler = new VulnHandler();
             parser.parse(ParTest.class.getResourceAsStream("test.xml"), handler);
             System.out.println(handler.getListVulnSoft());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }*/
        String prod = "cpe:/o:canonical:ubuntu_linux:12.04:-:lts";//"cpe:/a:mariadb:mariadb:5.5.34";
        String [] name_vendor = new String[5];
        String [] spl_prod = prod.split(":/a:|:/o:");
        for (String tm : spl_prod) {
            System.out.println(tm);
        }
        String [] spl_name = spl_prod[1].split(":");
        System.out.println("Length: " + spl_name.length);
        for (String tm : spl_name) {
            System.out.println(tm);
        }
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
    }

}/*

        String[] name_vendor = new String[5];
        String[] spl_prod = vulnProduct.split(":/a:");
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
        }*/