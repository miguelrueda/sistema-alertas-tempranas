package mx.org.banxico.sisal.dao;

//import au.com.bytecode.opencsv.CSVReader;
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

/**
 * Clase que se encarga de manejar el acceso a datos relacionados con la entidad
 * Software
 *
 * @author t41507
 * @version 23.07.2014
 */
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

    /**
     * Constructor
     */
    public SoftwareDAO() {
        //iniciarLista();
        //if (connection != null) {
            //LOG.log(Level.INFO, "Se ha establecido conexi\u00f3n con la BD");
            //cargarTodos();
        //} else {
            //iniciarLista();
            cargarTodos();
        //}
    }

    private void cargarTodos() {
        swList = new ArrayList<Software>();
        Software sw;
        try {
            connection = getConnection();
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
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
    }

    /**
     * Getter
     *
     * @return numero de registros encontrados
     */
    public int getNoOfRecords() {
        return noOfRecords;
    }

    /**
     * Consultas SQL
     */
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
    private static final String sqlRetrieveVendorsByGroup = "SELECT DISTINCT s.fabricante FROM Software s, Grupo g, Grupo_Software x "
            + "WHERE s.idSoftware = x.idSoftware AND g.idGrupo = x.idGrupo AND g.nombre LIKE ? ORDER BY s.fabricante ";
    private static final String sqlRetrieveFromLimit = "SELECT * FROM ( SELECT s.idSoftware, s.fabricante, s.nombre, "
            + "s.version, s.tipo, s.end_of_life, g.nombre as gnombre, g.categoria as gcategoria, "
            + "ROW_NUMBER() OVER(ORDER BY s.idSoftware) as row FROM Software s, "
            + "Grupo_Software x, Grupo g WHERE s.idSoftware = x.idSoftware AND x.idGrupo = g.idGrupo) z "
            + "WHERE z.row > ? and z.row <= ?";

    /*
     * SQL LIMIT
     * --MySQL
     * SELECT * FROM Students ORDER BY Name ASC LIMIT 20,10
     * --SQL Server
     * SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY Name ASC) AS Row, * FROM Students) AS StudentsWithRowNumbers WHERE Row > 20 AND Row <= 30
     */
    /**
     * Método agregar SW se encarga de tomar un objeto de la entidad SW y darle
     * persistencia dentro de la BD
     *
     * @param sw referencia al objeto de SW
     * @return bandera de estado de la operación
     */
    public boolean agregarSoftware(Software sw) {
        boolean res = false;
        try {
            connection = getConnection();
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
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return res;
    }

    /**
     * GETTER
     *
     * @return lista de todo el SW
     */
    public List<Software> obtenerTodos() {
        return this.swList;
    }

    /**
     * Getter
     *
     * @param index llave del elemento a buscar
     * @return referencia a un objeto de SW
     */
    public Software obtenerSoftwarePorId(int index) {
        return swList.get(index);
    }

    /**
     * Getter
     *
     * @return lista con todos los Grupos o UA
     */
    public List<String> obtenerUAs() {
        List<String> uas = new ArrayList<String>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveUAs);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                uas.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return uas;
    }

    /**
     * Getter
     *
     * @return Método de prueba para cuando no existe conexxión a BD TODO:
     * Eliminar este método
     */
    public Set<String> obtenerUAsTemp() {
        List<String> uas = new ArrayList<String>();
        for (Software software : swList) {
            uas.add(software.getUAResponsable());
        }
        return filtrarUAS(uas);
    }

    /**
     * Método que se encarga de filtrar los grupos o UAS
     *
     * @param uas refencia a la lista de grupos
     * @return conjunto de grupos filtrado
     */
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

    /**
     * Getter
     *
     * @return lista de fabricantes
     */
    public List<String> obtenerFabricantes() {
        List<String> vendors = new ArrayList<String>();
        try {
            connection = getConnection();
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
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return vendors;
    }

    /**
     * Getter
     *
     * @param vendor referncia de la clave del fabricante
     * @return lista de fabricantes
     */
    public List<String> obtenerFabricantes(String vendor) {
        List<String> vendors = new ArrayList<String>();
        try {
            connection = getConnection();
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
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return vendors;
    }

    /**
     * Getter
     *
     * @return lista de fabricantes temporal TODO: Eliminar esté método
     */
    public Set<String> obtenerFabricantesTemp() {
        List<String> vendors = new ArrayList<String>();
        for (Software sw : swList) {
            vendors.add(sw.getFabricante());
        }
        return filtrarVendors(vendors);
    }

    /**
     * Método que se encarga de eliminar duplicados dentro de una lista de
     * fabricantes
     *
     * @param vendors referncia del fabricante
     * @return conjunto filtrado de fabricantes
     */
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

    /**
     * Método que se encarga de devolver los elelemtnso de SW para ser
     * presentados por el paginador Simula un LIMIT X, Y de MySQL
     *
     * @param offset no. desde el que inicia la consulta
     * @param noOfRecords limite a buscar de registros
     * @return
     */
    public List<Software> retrieveFromLimit(int offset, int noOfRecords) {
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
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveFromLimit);
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
            LOG.log(Level.INFO, "Excepci\u00f3n de SQL: {0}", e.getMessage() + "//" + e.getSQLState());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return temp;
    }

    /**
     * Método que se encarga de editar un SW a partir de la referncia del mismo
     *
     * @param sw referencia del SW a editar
     * @return bandera con el resultado de la operación
     */
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

    /**
     * Método que se encarga de eliminar un elemento de SW por su clave
     *
     * @param id clave del SW
     * @return bandera con el resultado de la operación
     */
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

    /**
     * Método para obtener la conexión a BD
     *
     * @return objeto de tipo Conexión con la conexión a la BD
     */
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
    
    private static final String searchQry = "SELECT s.idSoftware, s.fabricante, s.nombre, s.version, s.tipo, s.end_of_life, g.nombre, g.categoria "
            + "FROM Software s, Grupo g, Grupo_Software x "
            + "WHERE s.idSoftware = x.idSoftware AND g.idGrupo = x.idGrupo "
            + "AND (s.fabricante LIKE ? OR s.nombre LIKE ? OR g.nombre LIKE ?) "
            + " ORDER BY g.nombre";

    /**
     * Método que se encarga de buscar dentro de la lista de SW a traves de su
     * nombre, fabricante o grupo
     *
     * @param key parámetro de búsqueda ya sea nombre, fabricante o grupo
     * @return Lista de SW con las coincidencias encontradas:
     */
    public List<Software> searchSoftware(String key) {
        //Mandar parametro como %param%
        LOG.log(Level.INFO, "parametro de b\u00fasqueda: {0}", key);
        List<Software> found = new ArrayList<Software>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(searchQry);
            pstmt.setString(1, "%" + key + "%");
            pstmt.setString(2, "%" + key + "%");
            pstmt.setString(3, "%" + key + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Software nuevo = new Software();
                nuevo.setIdSoftware(rs.getInt(1));
                nuevo.setFabricante(rs.getString(2));
                nuevo.setNombre(rs.getString(3));
                nuevo.setVersion(rs.getString(4));
                nuevo.setTipo(Integer.parseInt(rs.getString(5)));
                nuevo.setEndoflife(Integer.parseInt(rs.getString(6)));
                nuevo.setUAResponsable(rs.getString(7));
                nuevo.setAnalistaResponsable(rs.getString(8));
                found.add(nuevo);
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio un error al realizar la consulta: {0}", e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        /*
        for (Software sw : swList) {
            if (sw.getFabricante().equalsIgnoreCase(key) || sw.getNombre().equalsIgnoreCase(key)
                    || sw.getFabricante().toLowerCase().startsWith(key.toLowerCase()) || sw.getNombre().toLowerCase().startsWith(key.toLowerCase())
                    || sw.getNombre().toLowerCase().contains(key.toLowerCase())
                    || sw.getUAResponsable().toLowerCase().contains(key.toLowerCase())) {
                found.add(sw);
            }
        }*/
        if (!found.isEmpty()) {
            return found;
        }
        return new ArrayList<Software>();
    }
    
    private static final String sqlRetrieveProductsByVendor = "SELECT DISTINCT s.nombre FROM Software s WHERE s.fabricante LIKE ?";

    public List<String> obtenerProductos(String fabricante) {
        List<String> productos = new ArrayList<String>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveProductsByVendor);
            pstmt.setString(1, "%" + fabricante + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                productos.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Error al realizar la consulta: {0}", e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return productos;
    }

}