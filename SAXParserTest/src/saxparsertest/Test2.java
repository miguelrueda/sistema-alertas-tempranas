package saxparsertest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Test2 {
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Read file from WEB");
        File f = new File("http://cve.mitre.org/data/downloads/allitems-cvrf-year-2014.xml");
        //Una linea de ejemplo
        if (f.canRead()) {
            System.out.println("Esperando lectura");
        } else {
            System.out.println("Cannot read");
        }
        URL url = new URL("http://cve.mitre.org/data/downloads/allitems-cvrf-year-2014.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String str = null;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
        br.close();
        //Scanner sc = new Scanner(url.openStream());
        //while (sc.hasNextLine()) {
            //System.out.println(sc.nextLine());
        //}
    }
    
}
