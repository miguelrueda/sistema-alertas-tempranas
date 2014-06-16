package newtest;

import cve.entidades.CVE;
import cve.parser.CVEParser;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TryParse {

    private static final Logger LOG = Logger.getLogger(TryParse.class.getName());

    public static void main(String[] args) {
        CVEParser mCVEParser = new CVEParser();
        mCVEParser.setFiltro("");
        List<CVE> listaCVES = mCVEParser.getListCVE(TryParse.class.getResourceAsStream("/resources/RECENTS.xml"));
        for (CVE cve : listaCVES) {
            System.out.println(cve.toString());
        }
    }

}