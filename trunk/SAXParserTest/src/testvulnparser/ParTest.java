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
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            VulnHandler handler = new VulnHandler();
            parser.parse(ParTest.class.getResourceAsStream("test.xml"), handler);
            System.out.println(handler.getListVulnSoft());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
}
