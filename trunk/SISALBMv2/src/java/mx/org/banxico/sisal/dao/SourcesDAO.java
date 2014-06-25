package mx.org.banxico.sisal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.org.banxico.sisal.db.ConnectionFactory;
import mx.org.banxico.sisal.entities.AppSource;

public class SourcesDAO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(SourcesDAO.class.getName());
    private Connection conn;
    private PreparedStatement pstmt;
    private List<AppSource> fuentes;
    private int noFuentes;

    public SourcesDAO() {
        //iniciarFuentes();
        //TODO: Eliminar este método
        iniciarFuentesTemp();
    }

    private void iniciarFuentes() {
        String srcsQuery = "SELECT * FROM appsouces";
        LOG.log(Level.INFO, "Query: {0}", srcsQuery);
        fuentes = new ArrayList<AppSource>();
        AppSource fuente;
        try {
            this.conn = getConn();
            pstmt = conn.prepareStatement(srcsQuery);
            ResultSet rs = pstmt.executeQuery();
            int nr = 0;
            while (rs.next()) {
                fuente = new AppSource();
                fuente.setId(rs.getInt(1));
                fuente.setNombre(rs.getString(2));
                fuente.setUrl(rs.getString(3));
                fuente.setFechaActualizacion(new Date());
                LOG.log(Level.INFO, "Agregando Fuente:  {0} {1}", new Object[]{fuente.getId(), fuente.getNombre()});
                fuentes.add(fuente);
                nr++;
            }
            rs.close();
            this.noFuentes = nr;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Error a obtener las fuentes: {0}", e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
    }

    public Connection getConn() {
        Connection nConn = ConnectionFactory.getInstance().getConnection();
        if (nConn != null) {
            LOG.log(Level.INFO, "Conexi\u00f3n con BD Exitosa. {0}", nConn.toString());
        }
        return nConn;
    }

    public int getNoFuentes() {
        return noFuentes;
    }

    public List<AppSource> retrieveAll() {
        return this.fuentes;
    }
    
    private static List<AppSource> testList;
    static {
        testList = new ArrayList<AppSource>();
        testList.add(new AppSource(1, "Vulnerabilidades Recientes", "http://nvd.nist.gov/download/nvdcve-recent.xml", new Date()));
        testList.add(new AppSource(2, "Archivo de Vulnerabilidades", "http://nvd.nist.gov/download/nvdcve-2014.xml", new Date()));
    }

    private void iniciarFuentesTemp() {
        fuentes = new ArrayList<AppSource>();
        AppSource fuente;
        try {
            int nr = 0;
            for (AppSource src : testList) {
                fuente = new AppSource();
                fuente.setId(src.getId());
                fuente.setNombre(src.getNombre());
                fuente.setUrl(src.getUrl());
                fuente.setFechaActualizacion(src.getFechaActualizacion());
                fuentes.add(src);
                nr++;
            }
            this.noFuentes = nr;
        } catch (Exception e) {
            LOG.log(Level.INFO, "Error al iniciar la lista temporal: {0}", e.getMessage());
        }
    }
    
    public AppSource getFuente(int id) {
        for (AppSource src : fuentes) {
            if (src.getId() == id) {
                LOG.log(Level.INFO, "Fuente encontrada: {0}", src.getNombre());
                return src;
            }
        }
        return new AppSource();
    }
    
    public boolean editarFuente(int id, String name, String url) {
        boolean res = true;
        AppSource edit = this.getFuente(id);
        LOG.log(Level.INFO, "Editando la fuente: {0}", edit.getId());
        edit.setNombre(name);
        edit.setUrl(url);
        edit.setFechaActualizacion(new Date());
        return res;
    }
    
    public boolean descargarFuente(String url) {
        boolean res = false;
        LOG.log(Level.INFO, "Descargando Fuente: {0}", url);
        //doDownload();
        return res;
    }

}