package org.banxico.ds.sisal.dao;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private ResultSet rs;
    /**
     * Atributos del DAO
     */
    private List<Grupo> listaGrupos;
    private int numRegistros;
    /**
     * Statements de SQL
     */
    private static final String sqlObtenerTodoslosGrupos = "SELECT * FROM Grupo g ORDER BY g.nombre";
    private static final String retrieveGroupsFromLimit = "SELECT * FROM ( "
            + "SELECT g.idGrupo, g.nombre, g.categoria, g.se_reporta, g.correo, ROW_NUMBER() OVER(ORDER BY g.nombre) as row "
            + "FROM Grupo g ) z "
            + "WHERE z.row > ? and z.row <= ?";
    private static final String retrieveAllSoftwareFromGroup = "SELECT s.idSoftware, s.fabricante, s.nombre, s.version, s.end_of_life, g.idGrupo, "
            + "g.nombre, g.categoria FROM Software s, Grupo g, Grupo_Software x WHERE s.idSoftware = x.idSoftware "
            + "AND g.idGrupo = x.idGrupo AND g.idGrupo = ?";
    private static final String retrieveGroupById = "SELECT * FROM Grupo WHERE idGrupo = ?";
    private static final String retrieveAllCategorias = "SELECT DISTINCT(categoria) FROM Grupo";
    private static final String sqlInsertarGrupo = "INSERT INTO Grupo(nombre, categoria, se_reporta, correo) VALUES(?, ?, ?, ?);";
    private static final String sqlInsertGroupSoftwareValue = "INSERT INTO Grupo_Software VALUES(?, ?);";
    private static final String sqlInsertGroupTest = "INSERT INTO TestTable VALUES(?,?);"; //ELIMINAR
    private static final String sqlSearchGroupsQuery = "SELECT * FROM Grupo g WHERE (g.nombre LIKE ? OR g.categoria LIKE ?)";
    private static final String sqlDeleteGroupWithId = "DELETE FROM Grupo WHERE idGrupo = ?;";
    private static final String sqlEditarGrupoPorId = "UPDATE Grupo SET nombre = ?, categoria = ?, se_reporta = ?, correo = ? WHERE idGrupo = ?";
    private static final String sqlEliminarInfoGrupoSoftware = "DELETE FROM Grupo_Software WHERE idGrupo = ?";
    private static final String sqlBuscarGrupoPorNombre = "SELECT g.idGrupo, g.nombre, g.categoria FROM Grupo g WHERE g.nombre LIKE ?";

    /**
     * Constructor
     */
    public GruposDAO() {
        //Inicializar la lista de grupos
        cargarGruposEnLista();
    }

    /**
     * Método que se encarga de establecer conexión a BD, y obtener la lista de
     * todos los grupos
     */
    private void cargarGruposEnLista() {
        //Inicializar la lista e instanciar un grupo
        listaGrupos = new ArrayList<Grupo>();
        Grupo gp;
        try {
            //OBtener conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlObtenerTodoslosGrupos);
            //Ejecutar query e iterar los resultados para poblar la lista 
            rs = pstmt.executeQuery();
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
     * Obtener grupos de la lista, con un parametro de inicio y cantidad de
     * registros *Es como una función LIMIT de mysql
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
                gp.setReporta(rs.getInt(4));
                gp.setCorreo(rs.getString(5));
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
     * Método que se encarga de devolver el código HTML que contiene la
     * descripción de un grupo solicitado
     *
     * @param id identificador del grupo a describir
     * @return Buffer representado como cadena devolviendo la descripción del
     * grupo
     */
    public String describirGrupo(int id) {
        //Buscar el grupo por su ID
        Grupo grupo = obtenerGrupoPorId(id);
        //Iniciar el buffer y agregar plantilla HTML
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        sb.append("<thead>");
        sb.append("<th colspan='2'>");
        sb.append(grupo.getNombre());
        sb.append("</th>");
        sb.append("</thead>");
        sb.append("<tbody>");
        sb.append("<tr>");
        sb.append("<td>")
                .append("Categoría")
                .append("</td>");
        sb.append("<td>")
                .append(grupo.getCategoria())
                .append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>")
                .append("Software Registrado")
                .append("</td>");
        sb.append("<td>");
        //Obtener el software asociado al grupo seleccionado
        List<Software> productos = obtenerSoftwaredeGrupo(grupo.getIdGrupo());
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
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("Reporta");
        sb.append("</td>");
        sb.append("<td>");
        int reportable = grupo.getReporta();
        if (reportable == 0) {
            sb.append("El grupo no se reporta");
        } else if (reportable == 1) {
            sb.append("El grupo se reporta");
        }
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>");
        sb.append("Correo del Grupo");
        sb.append("</td>");
        sb.append("<td>");
        sb.append(grupo.getCorreo());
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
    public Grupo obtenerGrupoPorId(int id) {
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
                gp.setReporta(rs.getInt(4));
                gp.setCorreo(rs.getString(5));
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
    public List<Software> obtenerSoftwaredeGrupo(int idGrupo) {
        //Crear una nueva lista y un software para la iteración
        ArrayList<Software> lista = new ArrayList<Software>();
        Software temp;
        try {
            //Obtener la conexión y preparar el statement
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveAllSoftwareFromGroup);
            pstmt.setInt(1, idGrupo);
            //Ejecutar el query
            rs = pstmt.executeQuery();
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

    /**
     * Método que devuleve la lista de categorias registradas en la BD
     *
     * @return lista de cadenas con los nombre de las categorias
     */
    public List<String> obtenerCategorias() {
        //Instanciar lista para almacenar las categorias
        List<String> cats = new ArrayList<String>();
        try {
            //Obtener la conexión, preparar la sentencia y ejecutar la consulta
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveAllCategorias);
            rs = pstmt.executeQuery();
            //Iterar el conjunto de resultados para almacenarlos en la lista
            while (rs.next()) {
                cats.add(rs.getString(1));
            }
            //Cerrar Conjunto
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "GruposDAO#obtenerCategorias() - ocurrio un error de SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "GruposDAO#obtenerCategorias() - Ocurrio una excepci\u00f3n al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return cats;
    }

    /**
     * Método que registra un grupo en la bd a partir de los parametros
     * recibidos
     *
     * @param nombre_grupo cadena con el nombre del grupo a crear
     * @param categoria_grupo cadena con la categoria para registrar el grupo
     * @param reportable
     * @param correo
     * @param llaves arreglo de enteros con las llaves del software a registrar
     * en el grupo
     * @return bandera con el valor de la creación del grupo
     * @throws java.sql.SQLException cuando no se puede ejecutar de forma
     * correcta la transacción
     */
    public boolean crearGrupo(String nombre_grupo, String categoria_grupo, int reportable, String correo, Integer[] llaves) throws SQLException {
        //Banderas de resultado
        boolean group_created = false;
        int generated_key = 0;
        try {
            //Obtener la conexión a la base de datos y preparar la sentencia
            connection = getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(sqlInsertarGrupo, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nombre_grupo);
            pstmt.setString(2, categoria_grupo);
            pstmt.setInt(3, reportable);
            pstmt.setString(4, correo);
            Integer num = pstmt.executeUpdate();
            //LOG.log(Level.INFO, "GruposDAO#crearGrupo() retorna: {0}", num);
            //OEjecutar la consulta para obtener la llave generada
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                generated_key = rs.getInt(1);
            }
            //LOG.log(Level.INFO, "GruposDAO#crearGrupo() La llave generada es: {0}", generated_key);
            rs.close();
            //Si la llave generada es diferente de cero, cambiar la bandera de grupo creado
            if (generated_key != 0) {
                group_created = true;
            }
            //EN este punto ya tenemos el id del grupo
            //DO M/N INSERT
            //Prepara la nueva sentencia de inserción
            pstmt = connection.prepareStatement(sqlInsertGroupSoftwareValue);
            //Ordernar las llaves
            Arrays.sort(llaves);
            //Iterar el arreglo de llaves y establecer los parametros en la sentencia y posteriormente
            //Ejecutar la sentencia
            for (Integer key : llaves) {
                pstmt.setInt(1, generated_key);
                pstmt.setInt(2, key);
                //LOG.log(Level.INFO, "GruposDAO#crearGrupo() Insertando registro ({0},{1})", new Object[]{generated_key, key});
                Integer temp = pstmt.executeUpdate();
            }
            //Confirmar la transacción
            connection.commit();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "ocurrio un error de SQL: {0}", e.getMessage());
            //En caso de fallo de transacción, realizar un rollback
            connection.rollback();
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
        return group_created;
    }

    /**
     * Métod que se encarga de buscar la información de un grupo a partir de su
     * llave
     *
     * @param key parametro de búsqueda
     * @return Lista con los grupos que coinciden con la llave
     */
    public List<Grupo> buscarGrupo(String key) {
        List<Grupo> found = new ArrayList<Grupo>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlSearchGroupsQuery);
            pstmt.setString(1, "%" + key + "%");
            pstmt.setString(2, "%" + key + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Grupo g = new Grupo();
                g.setIdGrupo(rs.getInt("idGrupo"));
                g.setNombre(rs.getString("nombre"));
                g.setCategoria(rs.getString("categoria"));
                found.add(g);
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "GruposDAO#buscarGrupo() - Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "GruposDAO#buscarGrupo() - Ocurrio una excepci\u00f3n al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return found;
    }

    /**
     * Método que se encarga de eliminar un grupo en base a su identifiacador
     *
     * @param id identificador del grupo
     * @return bandar con el resultado de la opreación true para eliminado,
     * false para error
     */
    public boolean eliminarGrupo(int id) {
        boolean res = false;
        try {

            connection = getConnection();
            pstmt = connection.prepareStatement(sqlDeleteGroupWithId);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            LOG.log(Level.INFO, "GruposDAO#eliminarGrupo() - Grupo Eliminado Correctamente: {0}", id);
            res = true;
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "GruposDAO#eliminarGrupo() - Ocurrio un problema con la sentencia SQL: {0}", ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "GruposDAO#eliminarGrupo() - Ocurrio un problema al cerrar la conexión: {0}", e.getMessage());
            }
        }
        return res;
    }

    /**
     * Método que se encarga de editar la información de un grupo
     *
     * @param idgrupo
     * @param nombre
     * @param categoria
     * @param reportable
     * @param correo
     * @param llaves
     * @return
     * @throws SQLException
     */
    public boolean editarGrupo(int idgrupo, String nombre, String categoria, int reportable, String correo, Integer[] llaves) throws SQLException {
        boolean res = false;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(sqlEditarGrupoPorId);
            pstmt.setString(1, nombre);
            pstmt.setString(2, categoria);
            pstmt.setInt(3, reportable);
            pstmt.setString(4, correo);
            pstmt.setInt(5, idgrupo);
            Integer updated = pstmt.executeUpdate();
            pstmt = connection.prepareStatement(sqlEliminarInfoGrupoSoftware);
            pstmt.setInt(1, idgrupo);
            Integer deleted = pstmt.executeUpdate();
            pstmt = connection.prepareStatement(sqlInsertGroupSoftwareValue);
            Arrays.sort(llaves);
            for (Integer key : llaves) {
                pstmt.setInt(1, idgrupo);
                pstmt.setInt(2, key);
                Integer insert = pstmt.executeUpdate();
            }
            res = true;
            connection.commit();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "GruposDAO#editarGrupo() - Ocurrio un problema con la sentencia de SQL: {0}", e.getMessage());
            connection.rollback();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "GruposDAO#editarGrupo() - Ocurrio un problema al cerrar la conexión: {0}", e.getMessage());
            }
        }
        return res;
    }

    /**
     * Método que se encarga de obtener un grupo por la referencia de su nombre
     *
     * @param nombre
     * @return
     */
    public Grupo obtenerGrupoPorNombre(String nombre) {
        Grupo encontrado = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sqlBuscarGrupoPorNombre);
            pstmt.setString(1, nombre);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                encontrado = new Grupo();
                encontrado.setIdGrupo(rs.getInt("idGrupo"));
                encontrado.setNombre(rs.getString("nombre"));
                encontrado.setCategoria(rs.getString("categoria"));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio un error al preparar la sentencia: {0}", e.getMessage());
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
        return encontrado;
    }

}
