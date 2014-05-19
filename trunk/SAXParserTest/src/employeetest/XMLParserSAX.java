package employeetest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author t41507
 */
public class XMLParserSAX {

    private static final Logger LOG = Logger.getLogger(XMLParserSAX.class.getName());

    public static void main(String[] args) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(XMLParserSAX.class.getResourceAsStream("employees.xml"), handler);
            //Get Employees List
            List<Employee> empList = handler.getEmpList();
            for (Employee employee : empList) {
                System.out.println(employee);
            }
        } catch (ParserConfigurationException | SAXException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

}
