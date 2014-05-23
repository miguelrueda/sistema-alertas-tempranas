package test2;

import cve.entidades.CVE;
import cve.parser.CVEParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CVETest {

    private static final Logger LOG = Logger.getLogger(CVETest.class.getName());

    public static void main(String[] args) {
        CVEParser cveParser = new CVEParser();
        cveParser.setFiltro("");
        List<CVE> listaCVES = cveParser.getListCVE(manejarURL("http://nvd.nist.gov/download/nvdcve-recent.xml"));
        for (CVE cve : listaCVES) {
            System.out.println(cve);
        }
    }

    private static InputStream manejarURL(String mURL) {
        try {
            URL url = new URL(mURL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("URL NO VALIDA");
            }
            return connection.getInputStream();
        } catch (MalformedURLException e) {
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
