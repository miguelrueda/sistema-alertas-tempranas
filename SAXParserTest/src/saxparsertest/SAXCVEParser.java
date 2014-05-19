//http://www.journaldev.com/1198/java-sax-parser-example-tutorial-to-parse-xml-to-list-of-objects
package saxparsertest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class SAXCVEParser {
    
    public static void main(String[] args) {
        try {
            SAXParserFactory __factory = SAXParserFactory.newInstance();
            SAXParser __parser = __factory.newSAXParser();
            VulnerabilityHandler handler = new VulnerabilityHandler();
            __parser.parse("LOAD", handler);
            List<Vulnerability> vulList = handler.getVulList();
            for (Vulnerability vulnerability : vulList) {
                System.out.println(vulnerability);
            }
        } catch (ParserConfigurationException | SAXException e) {
        } catch (IOException ex) {
            Logger.getLogger(SAXCVEParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
