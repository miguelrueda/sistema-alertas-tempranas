package mx.org.banxico.sisal.dao;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.org.banxico.sisal.db.ConnectionFactory;
import mx.org.banxico.sisal.entities.Software;

public class SoftwareDAO implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final Logger LOG = Logger.getLogger(SoftwareDAO.class.getName());
    private static final long serialVersionUID = -1L;
    /**
     * Atributos del DAO
     */
    private static final String PRODSFILE = "/resources/softwareproducts.csv";
    private Connection connection;
    private PreparedStatement pstmt;
    private List<Software> swList;
    private int noOfRecords;

    public SoftwareDAO() {
        iniciarLista();
        //Iniciar la conexión a BD AQUI
        //connection = ConnectionFactory.getInstance().getConnection();
    }

    public Connection getConnection() {
        Connection nConn = ConnectionFactory.getInstance().getConnection();
        if (nConn != null) {
            LOG.log(Level.INFO, "Se ha establecido conexi\u00f3n con la BD: {0}", nConn.toString());
        }
        return nConn;
    }

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
                sw.setIdSoftware(Integer.parseInt(record[0]));
                if (!(record[1].length() == 0)) {
                    sw.setFabricante(record[1]);
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
        } catch (java.lang.NumberFormatException nfe) {
            LOG.log(Level.INFO, "Error de Conversi\u00f3n: {0}", nfe.getMessage());
        }
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    private static final String sqlInsert = "INSERT INTO Software "
            + "(fabricante, nombre, version, tipo, end_of_life, UAResponsable, AnalistaResponsable) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String sqlUpdate = "UPDATE Software "
            + "SET fabricante = ?, nombre = ?, version = ?, tipo = ?, end_of_life = ?, UAResponsable = ?, AnalistaResponsable = ? "
            + "WHERE idSoftware = ?";
    private static final String sqlDelete = "DELETE FROM Software WHERE idSoftware = ?";

    public boolean agregarSoftware(Software sw) {
        boolean res = false;
        try {
            pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setString(1, sw.getFabricante());
            pstmt.setString(2, sw.getNombre());
            pstmt.setString(3, sw.getVersion());
            pstmt.setInt(4, sw.getTipo());
            pstmt.setInt(5, sw.getEndoflife());
            pstmt.setString(6, sw.getUAResponsable());
            pstmt.setString(7, sw.getAnalistaResponsable());
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        return res;
    }

    public List<Software> obtenerTodos() {
        return this.swList;
    }

    public Software obtenerSoftwarePorId(int index) {
        return swList.get(index);
    }

    public List<Software> retrieveFromList(int offset, int noOfRecords) {
        List<Software> temp = new ArrayList<Software>();
        Software sw;
        for (int i = offset; i < offset + noOfRecords; i++) {
            if (i >= this.noOfRecords) {
                break;
            }
            sw = swList.get(i);
            temp.add(sw);
        }
        return temp;
    }

    public boolean editarSoftware(Software sw) {
        boolean res = false;
        try {
            pstmt = connection.prepareStatement(sqlUpdate);
            pstmt.setString(1, sw.getFabricante());
            pstmt.setString(2, sw.getNombre());
            pstmt.setString(3, sw.getVersion());
            pstmt.setInt(4, sw.getTipo());
            pstmt.setInt(5, sw.getEndoflife());
            pstmt.setString(6, sw.getUAResponsable());
            pstmt.setString(7, sw.getAnalistaResponsable());
            pstmt.setInt(8, sw.getIdSoftware());
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        return res;
    }

    public boolean eliminarSoftware(int id) {
        boolean res = false;
        try {
            pstmt = connection.prepareStatement(sqlDelete);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        return res;
    }

}
