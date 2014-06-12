package ejb.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import jpa.entities.SaFuentes;

@Stateless
public class SourcesEJB implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(SourcesEJB.class.getName());

    public boolean descargarActualizaciones(List<SaFuentes> sourcesList) {
        LOG.log(Level.INFO, "Recibiendo URLS");
        for (SaFuentes fuente : sourcesList) {
            LOG.log(Level.INFO, fuente.getFntUrl());
            descargarArchivo(fuente.getFntUrl());
        }
        LOG.log(Level.INFO, "Descarga Completada");
        return true;
    }

    private void descargarArchivo(String fntUrl) {
        LOG.log(Level.INFO, "Descargando Archivo: {0}", fntUrl);
        URL url;
        try {
            url = new URL(fntUrl);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            String path = SourcesEJB.class.getResource("/resources").getPath();
            File filePath = new File(path);
            LOG.log(Level.INFO, "PATH: {0}", filePath);
            String fileName = path + extraerNombre(fntUrl);
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            LOG.log(Level.INFO, "Voy a crear un archivo de nombre: {0}", fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine);
            }
            bw.close();
            br.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Ocurrio una excepción: {0}", e.getMessage());
        }
    }

    //http://nvd.nist.gov/download/nvdcve-2014.xml
    private String extraerNombre(String fntUrl) {
        StringTokenizer tokens = new StringTokenizer(fntUrl, "/");
        String [] datos = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            String str = tokens.nextToken();
            datos[i] = str;
            i++;
        }
        return datos[3];
    }

}