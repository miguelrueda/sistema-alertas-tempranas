package newtest;

import java.util.StringTokenizer;

public class SeparateURL {

    public static void main(String[] args) {
        String url = "http://nvd.nist.gov/download/nvdcve-2014.xml";
        StringTokenizer tokens = new StringTokenizer(url, "/");
        int nDatos = tokens.countTokens();
        String[] datos = new String[nDatos];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            String str = tokens.nextToken();
            datos[i] = str;
            i++;
        }
        System.out.println(datos[3]);

        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
    }
}
