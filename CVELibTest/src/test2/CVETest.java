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
        URL file = CVETest.class.getResource("/resources/nvdcve-2014.xml");
        LOG.log(Level.INFO, file.toString());
        CVEParser cveParser = new CVEParser();
        cveParser.setFiltro("");
        List<CVE> listaCVES = cveParser.getListCVE(CVETest.class.getResourceAsStream("/resources/nvdcve-2014.xml"));
        for (CVE cve : listaCVES) {
            System.out.println(cve.toString());
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

/**
 * URL defaultImage = ClassA.class.getResource("/packageA/subPackage/image-name.png");
File imageFile = new File(defaultImage.toURI());
 */