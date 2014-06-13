package ejb.model;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import jpa.entities.Producto;

@Stateless
public class SoftwareEJB implements java.io.Serializable {
    
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(SoftwareEJB.class.getName());
    private List<Producto> listaProductos;
    private static final String PRODSFILE = "/resources/softwareproducts.csv";

    public List<Producto> obtenerProductos() {
        /*
        parseCVS(listaProductos);
        if (listaProductos.isEmpty()) {
            return new ArrayList<>();
        }
        return listaProductos;*/
        return parseCVS();
    }

    private List<Producto> parseCVS() {
        try {
            listaProductos = new ArrayList<>();
            File file = new File(SoftwareEJB.class.getResource(PRODSFILE).getFile());
            CSVReader reader = new CSVReader(new FileReader(file));
            String [] record = null;
            reader.readNext();
            while ((record = reader.readNext()) != null) {
                Producto prod = new Producto(Integer.parseInt(record[0]));
                if (!(record[1].length() == 0)) {
                    prod.setVendor(record[1]);
                } else {
                    prod.setVendor("Not defined");
                }
                prod.setProduct(record[2]);
                prod.setVersion(record[3]);
                if (!(record[4].length() == 0)) {
                    prod.setType(Integer.parseInt(record[4]));
                }
                if (!(record[5].length() == 0)) {
                    prod.setEndOfLife(Integer.parseInt(record[5]));
                } else {
                    prod.setEndOfLife(-1);
                }
                listaProductos.add(prod);
            }
            reader.close();
            return listaProductos;
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, "Archivo no encontrado: {0}", ex.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Excepci\u00f3n de I/O{0}", ex);
        } finally {
        }
        return new ArrayList<>();
    }
    

}
/**
    private static List<Software> parseFileLineByLine() throws FileNotFoundException, IOException {
        while ((record = reader.readNext()) != null) {
            Software sw = new Software();
            if (!(record[0].length() == 0)) {
                sw.setVendor(record[0]);
            } else {
                sw.setVendor("Not Defined");
            }
            sw.setProduct(record[1]);
            sw.setVersion(record[2]);
            if (!(record[3].length() == 0)) {
                sw.setType(Integer.parseInt(record[3]));
            }
            if (!(record[4].length() == 0)) {
                sw.setEndoflife(Integer.parseInt(record[4]));
            } else {
                sw.setEndoflife(-1);
            }
            
            swsList.add(sw);
        }
        reader.close();
        System.out.println(swsList);
        return swsList;
    }
 */