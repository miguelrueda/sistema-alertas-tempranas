package org.banxico.ds.sisal.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.banxico.ds.sisal.db.ConnectionFactory;
import org.banxico.ds.sisal.entities.Grupo;
import org.banxico.ds.sisal.entities.Software;

/**
 * Clase que se encarga de manejar el acceso a datos relacionados con la entidad
 * Grupos
 * 
 * @author t41507
 * @version 13.08.2014
 */
public class GruposDAO {

    /**
     * Atributo LOGGER
     */
    private static final Logger LOG = Logger.getLogger(GruposDAO.class.getName());
    /**
     * Atributos de Conexión a BD
     */
    private Connection connection;
    private PreparedStatement pstmt;
    /**
     * Atributos del DAO
     */
    private List<Grupo> listaGrupos;
    private int numRegistros;
    /**
     * Statements de SQL
     */
    private static final String retrieveAllGroups = "SELECT * FROM Grupo";
    private static final String retrieveGroupsFromLimit = "SELECT * FROM ( "
            + "SELECT g.idGrupo, g.nombre, g.categoria, ROW_NUMBER() OVER(ORDER BY g.idGrupo) as row "
            + "FROM Grupo g ) z "
            + "WHERE z.row > ? and z.row <= ?";
    private static final String retrieveAllSoftwareFromGroup = "SELECT s.idSoftware, s.fabricante, s.nombre, s.version, s.end_of_life, g.idGrupo, "
            + "g.nombre, g.categoria FROM Software s, Grupo g, Grupo_Software x WHERE s.idSoftware = x.idSoftware "
            + "AND g.idGrupo = x.idGrupo AND g.idGrupo = ?";
    private static final String retrieveGroupById = "SELECT * FROM Grupo WHERE idGrupo = ?";
    
    /**
     * Constructor
     */
    public GruposDAO() {
        //Inicializar la lista de grupos
        cargarGruposEnLista();
    }
    
    /**
     * Método que se encarga de establecer conexión a BD, y obtener la lista de todos
     * los grupos
     */
    private void cargarGruposEnLista() {
        //Inicializar la lista e instanciar un grupo
        listaGrupos = new ArrayList<Grupo>();
        Grupo gp;
        try {
            //OBtener conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveAllGroups);
            //Ejecutar query e iterar los resultados para poblar la lista 
            ResultSet rs = pstmt.executeQuery();
            int nr = 0;
            while (rs.next()) {
                gp = new Grupo();
                gp.setIdGrupo(rs.getInt(1));
                gp.setNombre(rs.getString(2));
                gp.setCategoria(rs.getString(3));
                listaGrupos.add(gp);
                nr++;
            }
            //Cerrar resultados y establecer numero de registros
            rs.close();
            this.numRegistros = nr;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio un error con la conexión a BD: {0}", e.getMessage());
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
    }
    
    /**
     * Método que obtiene una instancia de la conexión a BD
     * 
     * @return objeto de tipo Connection con la conexión
     */
    private Connection getConnection() {
        return ConnectionFactory.getInstance().getConnection();
    }

    /**
     * Getter
     *
     * @return lista con los grupos obtenidos
     */
    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }
    
    /**
     * Getter
     *
     * @return entero con la cantidad de grupos registrados
     */
    public int obtenerNumeroGrupos() {
        return numRegistros;
    }

    /**
     * Obtener grupos de la lista, con un parametro de inicio y cantidad de registros
     * *Es como una función LIMIT de mysql
     *
     * @param offset entero que indica el registro de inicio
     * @param noreg entero que indica la cantidad de registros a obtener
     * @return lista con los grupos solicitados
     */
    public List<Grupo> retrieveGroupsFromLimit(int offset, int noreg) {
        //Inicializar ya lista e instanciar un grupo
        List<Grupo> grupos = new ArrayList<Grupo>();
        Grupo gp;
        try {
            //Obtener la conexión y preparar el statement, agregando los parametros
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveGroupsFromLimit);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, offset + noreg);
            //Ejecutar el query e iterar los resultados para poblar la lista
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                gp = new Grupo();
                gp.setIdGrupo(rs.getInt(1));
                gp.setNombre(rs.getString(2));
                gp.setCategoria(rs.getString(3));
                grupos.add(gp);
            }
            //Cerrar el ResultSet
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
        return grupos;
    }
    
    /**
     * Método que se encarga de devolver el código HTML que contiene la descripción
     * de un grupo solicitado
     *
     * @param id identificador del grupo a describir
     * @return Buffer representado como cadena devolviendo la descripción del grupo
     */
    public String describirGrupo(int id) {
        //Buscar el grupo por su ID
        Grupo temp = obtenerGrupoPorId(id);
        //Iniciar el buffer y agregar plantilla HTML
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        sb.append("<thead>");
        sb.append("<th colspan='2'>");
        sb.append(temp.getNombre());
        sb.append("</th>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>")
                .append("Categoría")
                .append("</td>");
        sb.append("<td>")
                .append(temp.getCategoria())
                .append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>")
                .append("Software Registrado")
                .append("</td>");
        sb.append("<td>");
        //Obtener el software asociado al grupo seleccionado
        List<Software> productos = obtenerSoftwaredeGrupo(temp.getIdGrupo());
        sb.append("<table style=\"border:0\">");
        sb.append("<tbody>");
        sb.append("<tr>")
                .append("<td>")
                .append("Fabricante")
                .append("</td>")
                .append("<td>")
                .append("Producto")
                .append("</td>")
                .append("<td>")
                .append("Versión")
                .append("</td>")
                .append("<td>")
                .append("Fin de Vida")
                .append("</td>")
                .append("</tr>");
        //Iterar la lista de productos obtenida para agregarlos a la descripción
        for (Software sw : productos) {
            sb.append("<tr>");
            sb.append("<td>")
                    .append(sw.getFabricante())
                    .append("</td>");
            sb.append("<td>")
                    .append(sw.getNombre())
                    .append("</td>");
            sb.append("<td>")
                    .append(sw.getVersion())
                    .append("</td>");
            switch (sw.getEndoflife()) {
                case -1:
                    sb.append("<td>").append("ND").append("</td>");
                    break;
                case 0:
                    sb.append("<td>").append("No").append("</td>");
                    break;
                case 1:
                    sb.append("<td>").append("Si").append("</td>");
                    break;
            }
            sb.append("</tr>");
        }
        //Codigo HTML
        sb.append("</tbody>");
        sb.append("</table>");
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }

    /**
     * Obtener la referencia a un grupo a partir de su identificador
     * 
     * @param id identificador del grupo a buscar
     * @return referencia del objeto grupo
     */
    private Grupo obtenerGrupoPorId(int id) {
        Grupo gp = null;
        try {
            //Inicializar el grupo
            gp = new Grupo();
            //Obtener la conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveGroupById);
            pstmt.setInt(1, id);
            //Ejecutar el query e iterar los resultados para obtener el objeto
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                gp.setIdGrupo(rs.getInt(1));
                gp.setNombre(rs.getString(2));
                gp.setCategoria(rs.getString(3));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio un error al establecer la conexi\u00f3n con la BD: {0}", e.getMessage());
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
        return gp;
    }

    /**
     * Obtener todos los productos de software relacionados con un grupo
     * 
     * @param idGrupo identificador del grupo a buscar
     * @return lista de software con los elementos asociados a ese grupo
     */
    private List<Software> obtenerSoftwaredeGrupo(int idGrupo) {
        //Crear una nueva lista y un software para la iteración
        ArrayList<Software> lista = new ArrayList<Software>();
        Software temp;
        try {
            //Obtener la conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveAllSoftwareFromGroup);
            pstmt.setInt(1, idGrupo);
            //Ejecutar el query
            ResultSet rs = pstmt.executeQuery();
            //Iterar los resultados y establecer los parametros en la referencia del objeto
            while (rs.next()) {
                temp = new Software();
                temp.setIdSoftware(rs.getInt(1));
                temp.setFabricante(rs.getString(2));
                temp.setNombre(rs.getString(3));
                temp.setVersion(rs.getString(4));
                temp.setEndoflife(rs.getInt(5));
                //Añadir a la lista
                lista.add(temp);
            }
            //Cerrar resultset
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "ocurrio un error de SQL: {0}", e.getMessage());
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
        return lista;
    }

}