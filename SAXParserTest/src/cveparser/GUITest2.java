package cveparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
RUTA 1.0
http://nvd.nist.gov/download/nvdcve-2014.xml

RUTA 2.0
http://static.nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2014.xml
*/

public class GUITest2 extends JFrame{
    
    public GUITest2 () {
        String s = (String) JOptionPane.showInputDialog(this, "Ingresa la URL: ", "URL", JOptionPane.PLAIN_MESSAGE);
        if ((s != null) && (s.length() > 0)) {
            //System.out.println(s);
            analizarURL(s);
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new GUITest2();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void analizarURL(String s) {
        try {
            URL murl = new URL(s);
            System.out.println("La URL es: " + murl);
            /*
            System.out.println("El protocolo es: " + murl.getProtocol());
            System.out.println("La autoridad es: " + murl.getAuthority());
            System.out.println("El nombre del archivo es: " + murl.getFile());
            System.out.println("El host es: " + murl.getHost());
            System.out.println("El path es: " + murl.getPath());
            System.out.println("El puerto es: " + murl.getPort());
            System.out.println("El puerto por defecto es: " + murl.getDefaultPort());
            System.out.println("La consulta es: " + murl.getQuery());
            System.out.println("La referencia es: " + murl.getRef());
            */
            URLConnection urlConnection = murl.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("Ingresar una URL");
                return;
            }
            InputStream is = connection.getInputStream();
            CVEParser cveParser = new CVEParser();
            cveParser.doParse(is);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GUITest2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUITest2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}