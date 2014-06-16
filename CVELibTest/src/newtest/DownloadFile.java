package newtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadFile {
    
    private static final Logger LOG = Logger.getLogger(DownloadFile.class.getName());
    private static final String recentsURL = "http://nvd.nist.gov/download/nvdcve-recent.xml";
    private static final String fileName = "RECENTS.xml";
    
    public static void main(String[] args) {
        LOG.log(Level.INFO, "Prueba de descarga");
        URL url;
        URLConnection conn;
        BufferedReader br;
        String inputLine;
        String path;
        BufferedWriter bw;
        try {
            url = new URL(recentsURL);
            conn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            path = DownloadFile.class.getResource("/resources/").getPath();
            LOG.log(Level.INFO, "Path obtenido: {0}", path);
            String mFName  = path + fileName;
            File file = new File(mFName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine + "\n");
            }
            LOG.log(Level.INFO, "Archivo guardado en: {0}", file.getAbsolutePath());
            bw.close();
            br.close();
        } catch (MalformedURLException e) {
            LOG.log(Level.INFO, "La url no es válida");
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Error al abrir la conexion");
        }
    }
    
}

/**
public class SourcesEJB implements java.io.Serializable {
    private void descargarArchivo(String fntUrl) {
        try {
            String path = SourcesEJB.class.getResource("/resources").getPath();
            File filePath = new File(path);
            LOG.log(Level.INFO, "PATH: {0}", filePath);
            String fileName = path + extraerNombre(fntUrl);
            File file = new File(extraerNombre(fntUrl));
            if (!file.exists()) {
                file.createNewFile();
            }
            LOG.log(Level.INFO, "Voy a crear un archivo de nombre: {0}", fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine + "\n");
            }
            bw.close();
            br.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Ocurrio una excepción: {0}", e.getMessage());
        }
    }
    
  
 */