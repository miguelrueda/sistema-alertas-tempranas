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
        String prod = "cpe:/a:mariadb:mariadb:5.5.34";
        String [] name_vendor = new String[5];
        String [] spl_prod = prod.split(":/a:");
        for (String tm : spl_prod) {
            System.out.println(tm);
        }
        String [] spl_name = spl_prod[1].split(":");
        for (String tm : spl_name) {
            System.out.println(tm);
        }
        name_vendor[0] = spl_name[0];
        name_vendor[1] = spl_name[1];
    }

}