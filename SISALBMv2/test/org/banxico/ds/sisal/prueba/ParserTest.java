package org.banxico.ds.sisal.prueba;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.net.URL;
import org.banxico.ds.sisal.parser.VulnerabilityParser;
import org.banxico.ds.sisal.parser.entidades.CVE;

public class ParserTest {

    public static void main(String[] args) {
        try {
            VulnerabilityParser parser = new VulnerabilityParser();
            URL url = new URL("http://nvd.nist.gov/download/nvdcve-recent.xml");
            List<CVE> lista = parser.getListCVE(url.openStream());
            System.out.println("lista con " + lista.size() + " elementos");
            Collections.sort(lista);
            for (CVE cve : lista) {
                System.out.println(cve.getName());
            }
        } catch (MalformedURLException ex) {
            System.out.println("url malformada");
        } catch (IOException ex) {
            System.out.println("excepcion de flujo de entrada");
        }

    }

}
