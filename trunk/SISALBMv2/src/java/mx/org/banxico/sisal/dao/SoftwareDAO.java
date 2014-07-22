package mx.org.banxico.sisal.dao;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.org.banxico.sisal.db.ConnectionFactory;
import mx.org.banxico.sisal.entities.Software;

public class SoftwareDAO { //implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final Logger LOG = Logger.getLogger(SoftwareDAO.class.getName());
    //private static final long serialVersionUID = -1L;
    /**
     * Atributos del DAO
     */
    //private static final String PRODSFILE = "/resources/softwareproducts.csv";
    private static final String PRODSFILE = "/resources/swdb.csv";
    private Connection connection;
    private PreparedStatement pstmt;
    private List<Software> swList;
    private int noOfRecords;

    public SoftwareDAO() {
        //iniciarLista();
        //Iniciar la conexión a BD AQUI
        connection = ConnectionFactory.getInstance().getConnection();
        if (connection != null) {
            LOG.log(Level.INFO, "Se ha establecido conexi\u00f3n con la BD");
            cargarTodos();
        } else {
            iniciarLista();
        }
    }

    private void cargarTodos() {
        swList = new ArrayList<Software>();
        Software sw;
        try {
            pstmt = connection.prepareStatement(sqlRetrieveAll);
            ResultSet rs = pstmt.executeQuery();
            int nr = 0;
            while (rs.next()) {
                sw = new Software();
                sw.setIdSoftware(rs.getInt(1));
                sw.setFabricante(rs.getString(2));
                sw.setNombre(rs.getString(3));
                sw.setVersion(rs.getString(4));
                sw.setTipo(rs.getInt(5));
                sw.setEndoflife(rs.getInt(6));
                sw.setUAResponsable(rs.getString(7));
                sw.setAnalistaResponsable("ND");
                swList.add(sw);
                nr++;
            }
            rs.close();
            this.noOfRecords = nr;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Error a obtener las fuentes: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
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
    private static final String sqlRetrieveAll = "SELECT s.idSoftware, s.fabricante, s.nombre, s.version, s.tipo, s.end_of_life, g.nombre \n"
            + "FROM Software s, Grupo g, Grupo_Software x \n"
            + "WHERE s.idSoftware = x.idSoftware AND g.idGrupo = x.idGrupo \n"
            + "ORDER BY g.nombre";
    private static final String sqlRetrieveUAs = "SELECT DISTINCT nombre FROM Grupo g ORDER BY g.nombre";
    private static final String sqlRetrieveVendors = "SELECT DISTINCT fabricante FROM Software ORDER BY fabricante";
    private static final String sqlRetrieveVendorsByGroup = "SELECT DISTINCT s.fabricante FROM Software s, Grupo g, Grupo_Software x " +
            "WHERE s.idSoftware = x.idSoftware AND g.idGrupo = x.idGrupo AND g.nombre LIKE ? ORDER BY s.fabricante ";
    /*
     * SQL LIMIT
     * --MySQL
     * SELECT * FROM Students ORDER BY Name ASC LIMIT 20,10
     * --SQL Server
     * SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY Name ASC) AS Row, * FROM Students) AS StudentsWithRowNumbers WHERE Row > 20 AND Row <= 30
     */

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

    public List<String> obtenerUAs() {
        List<String> uas = new ArrayList<String>();
        try {
            pstmt = connection.prepareStatement(sqlRetrieveUAs);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                uas.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        return uas;
    }

    public Set<String> obtenerUAsTemp() {
        List<String> uas = new ArrayList<String>();
        for (Software software : swList) {
            uas.add(software.getUAResponsable());
        }
        return filtrarUAS(uas);
    }

    private Set<String> filtrarUAS(List<String> uas) {
        Set<String> result = new LinkedHashSet<String>();
        Set<String> duplicados = new LinkedHashSet<String>();
        for (String ua : uas) {
            if (duplicados.contains(ua)) {
                duplicados.add(ua);
            } else {
                result.add(ua);
            }
        }
        return result;
    }

    public List<String> obtenerFabricantes() {
        List<String> vendors = new ArrayList<String>();
        try {
            pstmt = connection.prepareStatement(sqlRetrieveVendors);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vendors.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Excepci\u00f3n al cerrar el Statement: {0}", e.getMessage());
        }
        return vendors;
    }

    public List<String> obtenerFabricantes(String vendor) {
        List<String> vendors = new ArrayList<String>();
        try {
            pstmt = connection.prepareStatement(sqlRetrieveVendorsByGroup);
            pstmt.setString(1, "%" + vendor + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vendors.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Excepci\u00f3n al cerrar el Statement: {0}", e.getMessage());
        }
        return vendors;
    }

    public Set<String> obtenerFabricantesTemp() {
        List<String> vendors = new ArrayList<String>();
        /*
         vendors.add("Adobe Systems");
         vendors.add("Apache Software Foundation");
         vendors.add("Canonical");
         vendors.add("Cisco");
         vendors.add("IBM");
         vendors.add("Microsoft");*/
        for (Software sw : swList) {
            vendors.add(sw.getFabricante());
        }
        return filtrarVendors(vendors);
    }

    private Set<String> filtrarVendors(List<String> vendors) {
        Set<String> result = new LinkedHashSet<String>();
        Set<String> duplicados = new LinkedHashSet<String>();
        for (String vendor : vendors) {
            if (duplicados.contains(vendor)) {
                duplicados.add(vendor);
            } else {
                result.add(vendor);
            }
        }
        return result;
    }

    public List<Software> retrieveFromLimit(int offset, int noOfRecords) {
        String qry = "SELECT * FROM ( SELECT s.idSoftware, s.fabricante, s.nombre, "
                + "s.version, s.tipo, s.end_of_life, g.nombre as gnombre, g.categoria as gcategoria, "
                + "ROW_NUMBER() OVER(ORDER BY s.idSoftware) as row FROM Software s, "
                + "Grupo_Software x, Grupo g WHERE s.idSoftware = x.idSoftware AND x.idGrupo = g.idGrupo) z "
                + "WHERE z.row > ? and z.row <= ?";        
        List<Software> temp = new ArrayList<Software>();
        Software sw;
        /*
        for (int i = offset; i < offset + noOfRecords; i++) {
            if (i >= this.noOfRecords) {
                break;
            }
            sw = swList.get(i);
            temp.add(sw);
        }*/
        try {
            pstmt = connection.prepareStatement(qry);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, offset + noOfRecords);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sw = new Software();
                sw.setIdSoftware(rs.getInt("idSoftware"));
                sw.setFabricante(rs.getString("fabricante"));
                sw.setNombre(rs.getString("nombre"));
                sw.setVersion(rs.getString("version"));
                sw.setTipo(rs.getInt("tipo"));
                sw.setEndoflife(rs.getInt("end_of_life"));
                sw.setUAResponsable(rs.getString("gnombre"));
                sw.setAnalistaResponsable(rs.getString("gcategoria"));
                temp.add(sw);
            }
            rs.close();
            LOG.log(Level.INFO, "Consulta realizada, agregados: {0}", (offset + noOfRecords));
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Excepci\u00f3n de SQL: {0}", e.getMessage());
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
                if (!(record[6].length() == 0)) {
                    sw.setUAResponsable(record[6]);
                }
                sw.setAnalistaResponsable(record[7]);
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

    public List<Software> searchSoftware(String key) {
        List<Software> found = new ArrayList<Software>();
        for (Software sw : swList) {
            if (sw.getFabricante().equalsIgnoreCase(key) || sw.getNombre().equalsIgnoreCase(key) 
                    || sw.getFabricante().toLowerCase().startsWith(key.toLowerCase()) || sw.getNombre().toLowerCase().startsWith(key.toLowerCase())
                    || sw.getNombre().toLowerCase().contains(key.toLowerCase())
                    || sw.getUAResponsable().toLowerCase().contains(key.toLowerCase())) {
                found.add(sw);
            }
        }
        if (!found.isEmpty()) {
            return found;
        }
        return new ArrayList<Software>();
    }

}
