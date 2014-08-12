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

public class GruposDAO {

    private static final Logger LOG = Logger.getLogger(GruposDAO.class.getName());
    private Connection connection;
    private PreparedStatement pstmt;
    private List<Grupo> listaGrupos;
    private int numRegistros;
    
    private static final String retrieveAllGroups = "SELECT * FROM Grupo";
    private static final String retrieveGroupsFromLimit = "SELECT * FROM ( "
            + "SELECT g.idGrupo, g.nombre, g.categoria, ROW_NUMBER() OVER(ORDER BY g.idGrupo) as row "
            + "FROM Grupo g ) z "
            + "WHERE z.row > ? and z.row <= ?";
    private static final String retrieveAllSoftwareFromGroup = "SELECT s.idSoftware, s.fabricante, s.nombre, s.version, g.idGrupo, "
            + "g.nombre, g.categoria FROM Software s, Grupo g, Grupo_Software x WHERE s.idSoftware = x.idSoftware "
            + "AND g.idGrupo = x.idGrupo AND g.idGrupo = ?";
    private static final String retrieveGroupById = "SELECT * FROM Grupo WHERE idGrupo = ?";
    
    public GruposDAO() {
        cargarGruposEnLista();
    }
    
    private void cargarGruposEnLista() {
        listaGrupos = new ArrayList<Grupo>();
        Grupo gp;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveAllGroups);
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
    
    private Connection getConnection() {
        return ConnectionFactory.getInstance().getConnection();
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }
    
    public int obtenerNumeroGrupos() {
        return numRegistros;
    }

    public List<Grupo> retrieveGroupsFromLimit(int offset, int noreg) {
        List<Grupo> grupos = new ArrayList<Grupo>();
        Grupo gp;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveGroupsFromLimit);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, offset + noreg);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                gp = new Grupo();
                gp.setIdGrupo(rs.getInt(1));
                gp.setNombre(rs.getString(2));
                gp.setCategoria(rs.getString(3));
                grupos.add(gp);
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
        return grupos;
    }
    
    public String describirGrupo(int id) {
        Grupo temp = obtenerGrupoPorId(id);
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        sb.append("<thead>");
        sb.append("<th>");
        sb.append(temp.getNombre());
        sb.append("</th>");
        sb.append("</thead>");
        
        sb.append("<tbody>");
        //retrieveAllSoftwareFromGroup
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }

    private Grupo obtenerGrupoPorId(int id) {
        Grupo gp = null;
        try {
            gp = new Grupo();
            connection = getConnection();
            pstmt = connection.prepareStatement(retrieveGroupById);
            pstmt.setInt(1, id);
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

}