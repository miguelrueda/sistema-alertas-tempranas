package softwareparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoftwareTest {

    private static final Logger LOG = Logger.getLogger(SoftwareTest.class.getName());
    private static final String PRODSFILE = "/softwareparser/softwareproducts.csv";
    private static List<Software> swList;

    public static void main(String[] args) {
        swList = new ArrayList<Software>();
        Software temp;
        String [] record;
        BufferedReader br = null;
        try {
            File f = new File(SoftwareTest.class.getResource(PRODSFILE).getFile());
            LOG.log(Level.INFO, "Archivo obtenido correctamente");
            br = new BufferedReader(new FileReader(f));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
            }
        } catch (FileNotFoundException e) {
            LOG.log(Level.INFO, "Error al obtener el archivo");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, "Error al leer el archivo: " + ex);
        }
    }

}
/*

    private void iniciarLista() {
        swList = new ArrayList<Software>();
        Software sw;
        String[] record;
        try {
            File file = new File(SoftwareDAO.class.getResource(PRODSFILE).getFile());
            LOG.log(Level.INFO, "Archivo Leido Correctamente");
            CSVReader reader = new CSVReader(new FileReader(file));
            reader.readNext();
            int nr = 0;
            while ((record = reader.readNext()) != null) {
                sw = new Software();
                LOG.log(Level.INFO, record.toString());
                sw.setIdProducto(Integer.parseInt(record[0]));
                if (!(record[1].length() == 0)) {
                    sw.setProveedor(record[1]);
                }
                sw.setNombre(record[2]);
                if (!(record[3].length() == 0)) {
                    sw.setVersion(record[3]);
                } else {
                    sw.setVersion("-");
                }
                if (!(record[4].length() == 0)) {
                    sw.setTipo(Integer.parseInt(record[4]));
                }
                if (!(record[5].length() == 0)) {
                    sw.setEndoflife(Integer.parseInt(record[5]));
                } else {
                    sw.setEndoflife(-1);
                }
                swList.add(sw);
                nr++;
            }
            reader.close();
            this.noOfRecords = nr;
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch(java.lang.NumberFormatException nfe) {
            LOG.log(Level.INFO, "Error de Conversión: " + nfe.getMessage());
        }
    }
*/