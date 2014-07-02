package mx.org.banxico.sisal.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.org.banxico.sisal.db.ConnectionFactory;
import mx.org.banxico.sisal.entities.FuenteApp;

public class SourcesDAO implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(SourcesDAO.class.getName());
    /**
     * Atributos del DAO
     */
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private List<FuenteApp> fuentes;
    private int noFuentes;

    public SourcesDAO() {
        //iniciarFuentes();

        conn = ConnectionFactory.getInstance().getConnection();
        if (conn != null) {
            LOG.log(Level.INFO, "Conexión con BD exitosa!");
            iniciarFuentes();
        } else {
            iniciarFuentesTemp();
        }

    }

    //TODO: Eliminar esté método
    public Connection getConnection() {
        Connection nConn = ConnectionFactory.getInstance().getConnection();
        return nConn;
    }

    private static final String sqlRetrieveAll = "SELECT * FROM FuenteApp";
    private static final String sqlRetrieveDate = "SELECT fecha_actualizacion FROM FuenteApp WHERE idFuenteApp = ?";
    private static final String sqlInsert = "INSERT INTO FuenteApp(nombre, url, fecha_actualizacion) VALUES (?, ?, ?)";
    private static final String sqlUpdate = "UPDATE FuenteApp SET nombre=?, url=? WHERE idFuenteApp = ?";
    private static final String sqlUpdateDate = "UPDATE FuenteApp SET fecha_actualizacion = ? WHERE idFuenteApp = ?";
    private static final String sqlDelete = "DELETE FROM FuenteApp WHERE idFuenteApp = ?";

    private void iniciarFuentes() {
        fuentes = new ArrayList<FuenteApp>();
        FuenteApp fuente;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlRetrieveAll);
            rs = pstmt.executeQuery();
            int nr = 0;
            while (rs.next()) {
                fuente = new FuenteApp();
                fuente.setId(rs.getInt(1));
                fuente.setNombre(rs.getString(2));
                fuente.setUrl(rs.getString(3));
                fuente.setFechaActualizacion(rs.getDate(4));
                //LOG.log(Level.INFO, "Agregando Fuente:  {0} {1}", new Object[]{fuente.getId(), fuente.getNombre()});
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

    public int getNoFuentes() {
        return noFuentes;
    }

    public boolean crearFuente(String nombre, String url) {
        boolean res = false;
        try {
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setString(1, nombre);
            pstmt.setString(2, url);
            pstmt.setDate(3, new java.sql.Date(new Date().getTime()));

            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
        }
        return res;
    }

    public List<FuenteApp> obtenerFuentes() {
        if (!fuentes.isEmpty()) {
            return fuentes;
        }
        return new ArrayList<FuenteApp>();
    }

    public FuenteApp obtenerFuentePorId(int id) {
        for (FuenteApp src : fuentes) {
            if (src.getId() == id) {
                LOG.log(Level.INFO, "Fuente encontrada: {0}", src.getNombre());
                return src;
            }
        }
        return new FuenteApp();
    }

    //public boolean editarFuente(FuenteApp fuente) {
    public boolean editarFuente(int id, String nombreN, String urlN) {
        boolean res = false;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setString(1, nombreN);
            pstmt.setString(2, urlN);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
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
        return res;
    }

    public boolean eliminarFuente(int id) {
        boolean res = false;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlDelete);
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
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return res;
    }

    public Date obtenerFechaActualizacion(String id) {
        Date res = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlRetrieveDate);
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                res = rs.getDate(1);
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
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
        return res;
    }

    //TODO: Eliminar este método y la inicialización estática
    private static List<FuenteApp> testList;

    static {
        testList = new ArrayList<FuenteApp>();
        testList.add(new FuenteApp(1, "Vulnerabilidades Recientes", "http://nvd.nist.gov/download/nvdcve-recent.xml", new Date()));
        testList.add(new FuenteApp(2, "Archivo de Vulnerabilidades", "http://nvd.nist.gov/download/nvdcve-2014.xml", new Date()));
    }

    //TODO: Eliminar esté método
    /*
     public boolean editarFuente(int id, String name, String url) {
     boolean res = true;
     FuenteApp edit = this.obtenerFuentePorId(id);
     LOG.log(Level.INFO, "Editando la fuente: {0}", edit.getId());
     edit.setNombre(name);
     edit.setUrl(url);
     edit.setFechaActualizacion(new Date());
     return res;
     }
     */
    public boolean descargarFuente(String id, String url) {
        boolean res = false;
        LOG.log(Level.INFO, "Descargando Fuente: {0}", url);
        URL fileurl;
        URLConnection conn;
        BufferedReader br;
        String inputLine;
        String path;
        BufferedWriter bw;
        try {
            fileurl = new URL(url);
            conn = fileurl.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            path = SourcesDAO.class.getResource("/resources/").getPath();
            File filepath = new File(path);
            LOG.log(Level.INFO, "FilePath: {0}", filepath);
            String fileName = path + extraerNombre(url);
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            while ((inputLine = br.readLine()) != null) {
                bw.write(inputLine + "\n");
            }
            bw.close();
            br.close();
            res = true;
            actualizarFecha(id);
        } catch (MalformedURLException e) {
            LOG.log(Level.INFO, "La URL seleccionada no tiene un formato correcto: {0}", e.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ocurrio un error al abrir la conexi\u00f3n: {0}", ex.getMessage());
        }
        return res;
    }

    private String extraerNombre(String url) {
        StringTokenizer tokenizer = new StringTokenizer(url, "/");
        String[] tokens = new String[tokenizer.countTokens()];
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            tokens[i] = token;
            i++;
        }
        return tokens[3];
    }

    private void iniciarFuentesTemp() {
        fuentes = new ArrayList<FuenteApp>();
        FuenteApp fuente;
        try {
            int nr = 0;
            for (FuenteApp src : testList) {
                fuente = new FuenteApp();
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

    private void actualizarFecha(String id) {
        Integer idI = Integer.parseInt(id);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlUpdateDate);
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            pstmt.setInt(2, idI);
            pstmt.executeQuery();
            LOG.log(Level.INFO, "Fecha actualizada correctamente");
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage());
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

}
