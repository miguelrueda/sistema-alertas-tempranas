package org.banxico.ds.sisal.dao;

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
import org.banxico.ds.sisal.db.ConnectionFactory;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.parser.CPEParser;

/**
 * Clase que se encarga de manejar el acceso a datos relacionados con la entidad
 * Software
 *
 * @author t41507
 * @version 07.08.2014
 */
public class SoftwareDAO {

    /**
     * Atributo LOGGER
     */
    private static final Logger LOG = Logger.getLogger(SoftwareDAO.class.getName());
    /**
     * Atributos de Conexión
     */
    private Connection connection;
    private PreparedStatement pstmt;
    /**
     * Atributos del DAO
     */
    private List<Software> swList;
    private int noOfRecords;
    private List<Software> softwareDisponible;
    private CPEParser parser = new CPEParser();
    //private static final String PRODSFILE = "/resources/softwareproducts.csv";
    //private static final String PRODSFILE = "/resources/swdb.csv";
    /**
     * Consultas SQL
     */
    private static final String sqlInsert = "INSERT INTO Software "
            + "(fabricante, nombre, version, tipo, end_of_life, UAResponsable, AnalistaResponsable) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    private static final String sqlUpdate = "UPDATE Software "
            + "SET fabricante = ?, nombre = ?, version = ?, tipo = ?, end_of_life = ?, UAResponsable = ?, AnalistaResponsable = ? "
            + "WHERE idSoftware = ?";
    private static final String sqlDelete = "DELETE FROM Software WHERE idSoftware = ?";
    private static final String searchQry = "SELECT s.idSoftware, s.fabricante, s.nombre, s.version, s.tipo, s.end_of_life, g.nombre, g.categoria "
            + "FROM Software s, Grupo g, Grupo_Software x "
            + "WHERE s.idSoftware = x.idSoftware AND g.idGrupo = x.idGrupo "
            + "AND (s.fabricante LIKE ? OR s.nombre LIKE ? OR g.nombre LIKE ?) "
            + " ORDER BY g.nombre";
    private static final String sqlRetrieveProductsLike = "SELECT nombre FROM Software WHERE nombre LIKE ?";
    private static final String sqlRetrieveProductsByVendor = "SELECT DISTINCT s.nombre FROM Software s WHERE s.fabricante LIKE ?";
    private static final String sqlRetrieveProductId = "SELECT * FROM Software WHERE nombre = ?";
    /* La manera de realizar un LIMIT es diferente dependiendo el proveedor del DBMS:
     * SQL LIMIT
     * >> MySQL
     * SELECT * FROM Students ORDER BY Name ASC LIMIT 20,10
     * >> SQL Server
     * SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY Name ASC) AS Row, * FROM Students) AS StudentsWithRowNumbers WHERE Row > 20 AND Row <= 30
     */

    /**
     * Constructor
     */
    public SoftwareDAO() {
        cargarElementosEnLista();
        softwareDisponible = parser.getList();
    }

    /**
     * Método que se encarga de inicializar la lista de elementos de SW
     */
    private void cargarElementosEnLista() {
        //se inicializa la lista declarada
        swList = new ArrayList<Software>();
        //Se crea una referencia a un objeto de tipo Software
        Software sw;
        try {
            //Se obtiene la conexión, se prepara y ejecuta el Query
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveAll);
            ResultSet rs = pstmt.executeQuery();
            int nr = 0;
            //Iterar los elementos del ResultSet e inicializar el objeto de tipo Software
            //con los elementos obtenidos
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
                //Agregar el objeto a la lista e incrementar la cuenta
                swList.add(sw);
                nr++;
            }
            //cerrar el conjunto de resultados
            rs.close();
            //Establecer el numero de registros
            this.noOfRecords = nr;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio un error con la conexión a BD: {0}", e.getMessage());
        } finally {
            //Cerrar el statement y la conexión
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
    }

    /**
     * Método para obtener la conexión a BD
     *
     * @return objeto de tipo Conexión que contiene la conexión a la BD
     */
    public Connection getConnection() {
        Connection nConn = ConnectionFactory.getInstance().getConnection();
        return nConn;
    }

    /**
     * Getter
     *
     * @return numero de registros encontrados
     */
    public int obtenerNumeroRegistros() {
        return noOfRecords;
    }

    /**
     * GETTER
     *
     * @return lista de todo el SW
     */
    public List<Software> obtenerListadeSoftware() {
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
        //Instanciar una lista para almacenar los grupos
        List<String> uas = new ArrayList<String>();
        try {
            //obtener la conexión y preparar el statement.
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
     * @return lista de fabricantes
     */
    public List<String> obtenerFabricantes() {
        //Instanciar una lista para almacenar a los fabricantes
        List<String> vendors = new ArrayList<String>();
        try {
            //Obtener la conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveVendors);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vendors.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
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
        //Instanciar una lista para almacenar los fabricantes
        List<String> vendors = new ArrayList<String>();
        try {
            //Obtener la conexión y preparar el statement
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
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return vendors;
    }

    //OPERACIONES CRUD de Software
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
            //Se obtiene la conexión y se prepara el Statement
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlInsert);
            pstmt.setString(1, sw.getFabricante());
            pstmt.setString(2, sw.getNombre());
            pstmt.setString(3, sw.getVersion());
            pstmt.setInt(4, sw.getTipo());
            pstmt.setInt(5, sw.getEndoflife());
            pstmt.setString(6, sw.getUAResponsable());
            pstmt.setString(7, sw.getAnalistaResponsable());
            //Se ejecuta la actualización
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
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
     * Método que se encarga de devolver los elelemtnso de SW para ser
     * presentados por el paginador Simula un LIMIT X, Y de MySQL
     *
     * @param offset no. desde el que inicia la consulta
     * @param noOfRecords limite a buscar de registros
     * @return
     */
    public List<Software> retrieveFromLimit(int offset, int noOfRecords) {
        //Instanciar una lista para almacenar el SW y un elemento de SW temporal
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
            //Obtener conexión y preparar el Statement
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveFromLimit);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, offset + noOfRecords);
            //Ejecutar la consulta, iterar los resultados y poblar la lista
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
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage() + "//" + e.getSQLState());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
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
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlDelete);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return res;
    }

    /**
     * Método que se encarga de buscar dentro de la lista de SW a traves de su
     * nombre, fabricante o grupo
     *
     * @param key parámetro de búsqueda ya sea nombre, fabricante o grupo
     * @return Lista de SW con las coincidencias encontradas:
     */
    public List<Software> searchSoftware(String key) {
        //Instanciar una lista para almacenar las incidencias
        List<Software> found = new ArrayList<Software>();
        try {
            //Obtener conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(searchQry);
            pstmt.setString(1, "%" + key + "%");
            pstmt.setString(2, "%" + key + "%");
            pstmt.setString(3, "%" + key + "%");
            //Ejecutar la consulta
            ResultSet rs = pstmt.executeQuery();
            //Iterar los resultados y poblar la lista de software
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
        //Evitar retornar una lista vacia
        if (!found.isEmpty()) {
            return found;
        }
        return new ArrayList<Software>();
    }

    /**
     * Método que devuelve una lista de productos a partir de el nombre de un
     * fabricante
     *
     * @param fabricante fabricante a buscar
     * @return lista de productos del fabricante
     */
    public List<String> obtenerProductosPorFabricante(String fabricante) {
        //Instanciar una lista para almacenar los resultados
        List<String> productos = new ArrayList<String>();
        try {
            //Obtner conexion y preparar el statement
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
        return productos;
    }
    
    /**
     * Método que realiza el autocompletado de fabricantes basado en una cadenade consulta
     *
     * @param query parametro de busqueda
     * @return lista de cadenas con las incidencias de la busqueda
     */
    public List<String> completarFabricantes(String query) {
        List<String> vendors = retrieveAllVendors();
        List<String> filter = new ArrayList<String>();
        for (String vendor : vendors) {
            vendor = vendor.toLowerCase();
            if (vendor.startsWith(query) || vendor.endsWith(query) || vendor.contains(query)) {
                String capVendor = aMayusculas(vendor);
                //vendor = vendor.substring(0, 1).toUpperCase() + vendor.substring(1);
                filter.add(capVendor);
            }
        }
        return filter;
    }
    
    private String aMayusculas(String minword) {
        StringBuilder sb = new StringBuilder();
        String [] array = minword.split(" ");
        for (String str : array) {
            char [] temp = str.trim().toCharArray();
            temp[0] = Character.toUpperCase(temp[0]);
            str = new String(temp);
            sb.append(str).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Método que se encarga de devolver una lista con todos los fabricantes
     * 
     * @return  lista de cadenas con el nombre de todos los fabricantes registrados
     */
    private List<String> retrieveAllVendors() {
        List<String> vendors = new ArrayList<String>();
        Set<String> diferentes = new LinkedHashSet<String>();
        Set<String> duplicados = new LinkedHashSet<String>();
        for (Software sw : softwareDisponible) {
            if (diferentes.contains(sw.getFabricante())) {
                duplicados.add(sw.getFabricante());
            } else {
                diferentes.add(sw.getFabricante());
            }
        }
        vendors.addAll(diferentes);
        return vendors;
    }

    /**
     * Método que se encarga de autocompletar los productos de un fabricante determinado
     *
     * @param vendor nombre del fabricante a buscar la lista
     * @return lista de cadenas con el nombre de los productos de un farbicante
     */
    public List<String> obtenerProductosPorFabricanteAC(String vendor) {
        List<String> productos = new ArrayList<String>();
        //Buscar los productos en la lista
        for (Software sw : softwareDisponible) {
            if (sw.getFabricante().equalsIgnoreCase(vendor.trim())) {
                //String temp = sw.getNombre().substring(0, 1).toUpperCase() + sw.getNombre().substring(1);
                String temp = aMayusculas(sw.getNombre());
                productos.add(temp);
            }
        }
        //Eliminar los duplicados
        Set<String> diferentes = new LinkedHashSet<String>();
        Set<String> duplicados = new LinkedHashSet<String>();
        for (String prod : productos) {
            if (diferentes.contains(prod)) {
                duplicados.add(prod);
            } else {
                diferentes.add(prod);
            }
        }
        //Agregar los diferentes en una nueva lista
        List<String> result = new ArrayList<String>();
        result.addAll(diferentes);
        return result;
    }

    /**
     * Métopdo que se encarga de obtener las versiones a partir del nombre de un producto
     *
     * @param product nombre del producto del cual se buscaran las versiones
     * @return lista de cadenas con las versiones del producto
     */
    public List<String> obtenerVersionesdeProducto(String product) {
        List<String> products = new ArrayList<String>();
        List<String> versions = new ArrayList<String>();
        //Buscar las versiones por software
        for (Software sw : softwareDisponible) {
            if (sw.getNombre().equalsIgnoreCase(product) && !"-".equals(sw.getNombre())) {
                versions.add(sw.getVersion());
            }
        }
        //Eliminar duplicados
        Set<String> diferentes = new LinkedHashSet<String>();
        Set<String> duplicados = new LinkedHashSet<String>();
        for (String version : versions) {
            if (diferentes.contains(version)) {
                duplicados.add(version);
            } else {
                diferentes.add(version);
            }
        }
        versions = new ArrayList<String>();
        versions.addAll(diferentes);
        return versions;
    }

    /**
     * Método que se encarga de realizar el autocompletado de productos con la BD
     *
     * @param prodq clave a buscar dentro de la BD
     * @return lista de cadenas con los productos similares
     */
    public List<String> completarProductos(String prodq) {
        //Instanciar la lista
        List<String> products = new ArrayList<String>();
        try {
            //Obtener la conexión, preparar la sentencia y establecer el parametro
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveProductsLike);
            pstmt.setString(1, "%" + prodq + "%");
            //Ejecutar la consulta y agregar las incidencias a la lista
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(rs.getString(1));
            }
            //Cerrar
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "SoftwareDAO#completarProductos() - Error al realizar la consulta: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "SoftwareDAO#completarProductos() - Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return products;
    }

    /**
     * Método que se encarga de obtener la llave de un producto seleccionado
     *
     * @param prodName clave del producto a buscar
     * @return String con la llave del producto encontrado
     */
    public String buscarIdProducto(String prodName) {
        int id = 0;
        try {
            //Obtener conexión, preparar el statement y establecer el parametro
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlRetrieveProductId);
            pstmt.setString(1, prodName);
            //Ejecutar la consulta y buscar el id retornado
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("idSoftware");
            }
            //Cerrar el conjunto
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "SoftwareDAO#buscarIdProducto() - Error al realizar la consulta: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "SoftwareDAO#buscarIdProducto() - Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        //LOG.log(Level.INFO, "Se encontro el ID: {0}", id);
        return (id + "");
    }

    /**
     * CODIGO BASURA
     */
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
     * @return lista de fabricantes temporal TODO: Eliminar esté método
     *
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
     * Método que se encarga de iniciar la lista de Softwares apartir de un
     * archivo TODO: ELiminar esté método
     *
     * private void iniciarLista() { swList = new ArrayList<Software>();
     * Software sw; String[] record; try { File file = new
     * File(SoftwareDAO.class.getResource(PRODSFILE).getFile()); CSVReader
     * reader = new CSVReader(new FileReader(file)); reader.readNext(); int nr =
     * 0; while ((record = reader.readNext()) != null) { sw = new Software();
     * sw.setIdSoftware(Integer.parseInt(record[0])); if (!(record[1].length()
     * == 0)) { sw.setFabricante(record[1]); } sw.setNombre(record[2]); if
     * (!(record[3].length() == 0)) { sw.setVersion(record[3]); } else {
     * sw.setVersion("-"); } if (!(record[4].length() == 0)) {
     * sw.setTipo(Integer.parseInt(record[4])); } if (!(record[5].length() ==
     * 0)) { sw.setEndoflife(Integer.parseInt(record[5])); } else {
     * sw.setEndoflife(-1); } if (!(record[6].length() == 0)) {
     * sw.setUAResponsable(record[6]); } sw.setAnalistaResponsable(record[7]);
     * swList.add(sw); nr++; } reader.close(); this.noOfRecords = nr; } catch
     * (FileNotFoundException e) { LOG.log(Level.SEVERE, null, e); } catch
     * (IOException ex) { LOG.log(Level.SEVERE, null, ex); } catch
     * (java.lang.NumberFormatException nfe) { LOG.log(Level.INFO, "Error de
     * Conversi\u00f3n: {0}", nfe.getMessage()); } }
     */
}
