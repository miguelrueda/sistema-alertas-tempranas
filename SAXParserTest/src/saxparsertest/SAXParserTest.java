package saxparsertest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserTest {

    private static final Logger LOG = Logger.getLogger(SAXParserTest.class.getName());

    public static void main(String[] args) {
        String url = SAXParserTest.class.getResource("").getPath();
        System.out.println("URL -->" + url);

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxparser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {

                boolean bfname = false;
                boolean blname = false;
                boolean bnname = false;
                boolean bsalary = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    System.out.println("Start element: " + qName);
                    if (qName.equalsIgnoreCase("FIRSTNAME")) {
                        bfname = true;
                    }
                    if (qName.equalsIgnoreCase("LASTNAME")) {
                        blname = true;
                    }
                    if (qName.equalsIgnoreCase("NICKNAME")) {
                        blname = true;
                    }
                    if (qName.equalsIgnoreCase("SALARY")) {
                        bsalary = true;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    System.out.println("End element: " + qName);
                }

                @Override
                public void characters(char ch[], int start, int length) {
                    if (bfname) {
                        System.out.println("First Name: " + new String(ch, start, length));
                        bfname = false;
                    }
                    if (blname) {
                        System.out.println("Last Name: " + new String(ch, start, length));
                        blname = false;
                    }
                    if (bnname) {
                        System.out.println("NickName: " + new String(ch, start, length));
                        bnname = false;
                    }
                    if (bsalary) {
                        System.out.println("Salary: " + new String(ch, start, length));
                        bsalary = false;
                    }
                }
            };

            saxparser.parse(SAXParserTest.class.getResourceAsStream("/resources/test.xml"), handler);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(SAXParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
