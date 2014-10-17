package org.banxico.ds.sisal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banxico.ds.sisal.db.ConnectionFactory;
import org.banxico.ds.sisal.entities.GrupoVulnerabilidad;

public class GrupoVulnerabilidadDAO {

    private static final Logger LOG = Logger.getLogger(GrupoVulnerabilidadDAO.class.getName());
    private Connection conexion;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private GruposDAO gdao = new GruposDAO();
    private VulnerabilityDAO vdao = new VulnerabilityDAO();
    
    public static final String ObtenerTodoGrupoVulnerabilidad = "SELECT * FROM Grupo_Vulnerabilidad";
    public static final String ObtenerGrupoVulnerabilidadPorIDGrupo = "SELECT x.idGrupo, x.idVulnerabilidad, x.afecta, x.resuelto, x.fechaSolucion, x.avance "
            + "FROM Grupo g, Grupo_Vulnerabilidad x, Vulnerabilidad v "
            + "WHERE g.idGrupo = x.idGrupo AND x.idVulnerabilidad = v.idVulnerabilidad AND g.idGrupo = ?";
    public static final String InsertarGrupoVulnerabilidad = "INSERT INTO Grupo_Vulnerabilidad "
            + "(idGrupo, idVulnerabilidad, afecta, resuelto, fechaSolucion, avance) "
            + "VALUES(?, ?, ?, ?, ?, ?);";

    public List<GrupoVulnerabilidad> obtenerTodos() {
        List<GrupoVulnerabilidad> lista = null;
        try {
            conexion = obtenerConexion();
            pstmt = conexion.prepareStatement(ObtenerTodoGrupoVulnerabilidad);
            rs = pstmt.executeQuery();
            lista = new ArrayList<GrupoVulnerabilidad>();
            while (rs.next()) {
                lista.add(new GrupoVulnerabilidad(gdao.obtenerGrupoPorId(rs.getInt(1)), vdao.obtenerVulnerabilidadPorNombre(rs.getString(2)), rs.getInt(3), rs.getInt(4), rs.getDate(5), rs.getDouble(6)));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "GrupoVulnerabilidadDAO#obtenerTodos() - Ocurrio un problema al preparar la sentencia SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "GrupoVulnerabilidadDAO#obtenerTodos() - Ocurrio un problema al cerrar la conexión de BD: {0}", e.getMessage());
            }
        }
        return lista;
    }

    public List<GrupoVulnerabilidad> obtenerPorGrupo(int id) {
        List<GrupoVulnerabilidad> lista = null;
        try {
            conexion = obtenerConexion();
            pstmt = conexion.prepareStatement(ObtenerGrupoVulnerabilidadPorIDGrupo);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            lista = new ArrayList<GrupoVulnerabilidad>();
            while (rs.next()) {
                GrupoVulnerabilidad gv = new GrupoVulnerabilidad(gdao.obtenerGrupoPorId(rs.getInt(1)), vdao.obtenerVulnerabilidadPorNombre(rs.getString(2)), rs.getInt(3), rs.getInt(4), rs.getDate(5), rs.getDouble(6));
                lista.add(gv);
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "GrupoVulnerabilidadDAO#obtenerPorGrupo() - Ocurrio un problema al preparar la sentencia SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "GrupoVulnerabilidadDAO#obtenerPorGrupo() - Ocurrio un problema al cerrar la conexión de BD: {0}", e.getMessage());
            }
        }
        return lista;
    }
    
    public boolean registrarVulnerabilidadesEnGrupo(int idGrupo, String idVulnerabilidad) {
        boolean res = false;
        try {
            conexion = obtenerConexion();
            pstmt = conexion.prepareStatement(InsertarGrupoVulnerabilidad);
            pstmt.setInt(1, idGrupo);
            pstmt.setString(2, idVulnerabilidad);
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.setDate(5, null);
            pstmt.setInt(6, 0);
            LOG.log(Level.INFO, "GrupoVulnerabilidadDAO#registrarVulnerabilidadesEnGrupo() - Registrando en Grupo Vulnerabilidad ({0}, {1})", new Object[]{idGrupo, idVulnerabilidad});
            int guardado = pstmt.executeUpdate();
            if (guardado > 0) {
                res = true;
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "GrupoVulnerabilidadDAO#registrarVulnerabilidadesEnGrupo() - Ocurrio un problema al preparar la sentencia SQL: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "GrupoVulnerabilidadDAO#registrarVulnerabilidadesEnGrupo() - Ocurrio un problema al cerrar la conexión de BD: {0}", e.getMessage());
            }
        }
        return res;
    }

    private Connection obtenerConexion() {
        return ConnectionFactory.getInstance().getConnection();
    }

}
