package mx.org.banxico.sisal.dao;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.org.banxico.sisal.db.ConnectionFactory;
import mx.org.banxico.sisal.entities.Software;

public class SoftwareDAO implements java.io.Serializable {

    private static final Logger LOG = Logger.getLogger(SoftwareDAO.class.getName());
    private static final long serialVersionUID = -1L;
    private static final String PRODSFILE = "/resources/softwareproducts.csv";
    private Connection connection;
    private PreparedStatement pstmt;
    private List<Software> swList;
    private int noOfRecords;

    public SoftwareDAO() {
        iniciarLista();
    }

    public Connection getConnection() {
        Connection nConn = ConnectionFactory.getInstance().getConnection();
        if (nConn != null) {
            LOG.log(Level.INFO, "Se ha establecido conexi\u00f3n con la BD: {0}", nConn.toString());
        }
        return nConn;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public List<Software> retrieveAll() {
        return this.swList;
    }

    private void iniciarLista() {
        swList = new ArrayList<>();
        Software sw;
        String[] record;
        try {
            File file = new File(SoftwareDAO.class.getResource(PRODSFILE).getFile());
            CSVReader reader = new CSVReader(new FileReader(file));
            reader.readNext();
            int nr = 0;
            while ((record = reader.readNext()) != null) {
                sw = new Software();
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
        }
    }

    public Software getSoftware(int index) {
        return swList.get(index);
    }

    public List<Software> retrieveFromList(int offset, int noOfRecords) {
        List<Software> temp = new ArrayList<>();
        Software sw;
        for (int i = offset; i < offset + noOfRecords; i++) {
            if (i >= this.noOfRecords) {
                break;
            }
            sw = swList.get(i);
            LOG.log(Level.INFO, "Agregando: {0}", sw.getIdProducto());
            temp.add(sw);
        }
        return temp;
    }

}