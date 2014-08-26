package org.banxico.ds.sisal.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.banxico.ds.sisal.db.ConnectionFactory;
import org.banxico.ds.sisal.entities.FuenteApp;

/**
 * Clase que se encarga de manejar el acceso a datos relacionados con la entidad
 * Fuente
 *
 * @author t41507
 * @version 07.08.2014
 */
public class SourcesDAO {

    /**
     * Atributo LOGGER
     */
    private static final Logger LOG = Logger.getLogger(SourcesDAO.class.getName());
    /**
     * Atributos de Conexión
     */
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    /**
     * Atributos del DAO
     */
    private List<FuenteApp> fuentes;
    private int noFuentes;
    /**
     * Consultas SQL
     */
    private static final String sqlRetrieveAll = "SELECT * FROM FuenteApp";
    private static final String sqlRetrieveDate = "SELECT fecha_actualizacion FROM FuenteApp WHERE idFuenteApp = ?";
    private static final String sqlInsert = "INSERT INTO FuenteApp(nombre, url, fecha_actualizacion) VALUES (?, ?, ?)";
    private static final String sqlUpdate = "UPDATE FuenteApp SET nombre=?, url=? WHERE idFuenteApp = ?";
    private static final String sqlUpdateDate = "UPDATE FuenteApp SET fecha_actualizacion = ? WHERE idFuenteApp = ?";
    private static final String sqlDelete = "DELETE FROM FuenteApp WHERE idFuenteApp = ?";
    
    /**
     * Constructor del dao
     */
    public SourcesDAO() {
        /*
        urlconn = ConnectionFactory.getInstance().getConnection();
        if (urlconn != null) {
            LOG.log(Level.INFO, "Conexión con BD exitosa!");
            iniciarFuentes();
        } else {
            iniciarFuentesTemp();
        }*/
        iniciarFuentes();
    }

    /**
     * Método que se encarga de obtener las fuentes de la BD
     */
    private void iniciarFuentes() {
        //Inicializar la lista e instanciar un objeto de tipo fuente
        fuentes = new ArrayList<FuenteApp>();
        FuenteApp fuente;
        try {
            //Obtener conexión y preparar el statement
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlRetrieveAll);
            rs = pstmt.executeQuery();
            int nr = 0;
            //Iterar los resultados y poblar la lista
            while (rs.next()) {
                fuente = new FuenteApp();
                fuente.setId(rs.getInt(1));
                fuente.setNombre(rs.getString(2));
                fuente.setUrl(rs.getString(3));
                fuente.setFechaActualizacion(rs.getDate(4));
                fuentes.add(fuente);
                nr++;
            }
            rs.close();
            //Establecer el no de registros
            this.noFuentes = nr;
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio un error en la conexión a BD: {0}", e.getMessage());
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
    
    /**
     * Método que se encarga de obtener la conexión de BD
     *
     * @return objeto de tipo Connection
     */
    public Connection getConnection() {
        Connection nConn = ConnectionFactory.getInstance().getConnection();
        return nConn;
    }

    /**
     * Método que devuleve el total de fuentes en la BD
     *
     * @return no. de fuentes
     */
    public int obtenerNumeroFuentes() {
        return noFuentes;
    }
    
    /**
     * Método que se encarga de obtener la lista de fuentes
     *
     * @return lista de fuentes obtenidas
     */
    public List<FuenteApp> obtenerFuentes() {
        if (!fuentes.isEmpty()) {
            return fuentes;
        }
        return new ArrayList<FuenteApp>();
    }
    
    /**
     * Método que obtiene la referencia de una fuente a partir de su di
     *
     * @param id identificador de la fuente
     * @return referencia de la fuente
     */
    public FuenteApp obtenerFuentePorId(int id) {
        for (FuenteApp src : fuentes) {
            if (src.getId() == id) {
                return src;
            }
        }
        return new FuenteApp();
    }
    
    /**
     * Método que obtiene la fecha de actualización de una fuente a partir de su id
     *
     * @param id identificador de la fuente
     * @return fecha de actualización de la fuente
     */
    public Date obtenerFechaActualizacion(String id) {
        Date res = null;
        try {
            //Obtener conexión y preparar el statement
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
    
    //OPERACIONES CRUD

    /**
     * Método que se encarga de crear una nueva fuente
     *
     * @param nombre nombre de la fuente
     * @param url direccion del archivo de la fuente
     * @return true cuando se crea la fuente y false cuando no se crea.
     */
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

    /**
     * Método que se encarga de actualizar la fuente
     *
     * @param id identificador de la fuente a actualizar
     * @param nombreN numero nombre ade la fuente
     * @param urlN nueva url de la fuente
     * 
     * @return bandera con la ejecución del resultado
     */
    public boolean editarFuente(int id, String nombreN, String urlN) {
        boolean res = false;
        try {
            //Obtener conexión y preparar el statement
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
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
        return res;
    }

    /**
     * Método que se encarga de la eliminación de las fuentes
     *
     * @param id identificador de la fuente
     * 
     * @return bandera con el resultado de la ejecución
     */
    public boolean eliminarFuente(int id) {
        boolean res = false;
        try {
            //Obtener conexión y preparar el statement
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

    /**
     * Método que se encarga de descargar las actualizaciones de la fuente
     *
     * @param id identificador de la fuente a actualizar
     * @param url la url a descargar
     * @return bandera con el resultado de la ejecución
     */
    public boolean descargarFuente(String id, String url) {
        boolean res = false;
        LOG.log(Level.INFO, "Se realizar\u00e1 la descarga de la Fuente: {0} - {1}", new Object[]{id, url});
        //Variables para manejar la conexión y descarga del archivo
        URL fileurl = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        String inputLine = null;
        String path = null;
        BufferedWriter bw = null;
        try {
            //Inicializar la variable con la URL y obtener una conexión
            fileurl = new URL(url);
            urlconn = fileurl.openConnection();
            br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            //Obtener el path y crear una ruta
            path = SourcesDAO.class.getResource("/resources/").getPath();
            File filepath = new File(path);
            String fileName = path + extraerNombre(url);
            File file = new File(fileName);
            String nuevopath = file.getAbsolutePath();
            LOG.log(Level.INFO, "Guardando archivo en: {0}", nuevopath);
            PrintWriter printWriter = null;
            //Comprobar existencia del archivo
            if (!file.exists()) {
                file.createNewFile();
            } else if (file.exists()) {
                file.delete();
            }
            //Crear un escritor de flujo y un buffer
            printWriter = new PrintWriter(new File(nuevopath));
            StringBuilder buffer = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                buffer.append(inputLine).append("\n");
            }
            //Escribir el flujo en el archivo
            printWriter.print(buffer);
            printWriter.close();
            res = true;
            LOG.log(Level.INFO, "Descarga de la fuente: {0}Completada", id);
            //Actualizar el registro de la fecha
            actualizarFecha(id);
        } catch (MalformedURLException e) {
            LOG.log(Level.INFO, "La URL seleccionada no tiene un formato correcto: {0}", e.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ocurrio un error al abrir la conexi\u00f3n: {0}", ex.getMessage());
        }
        return res;
    }
    
    private static final String SAVE_DIR = "FuentesDescargadas";
    
    /**
     * Método que se encarga de descargar las fuentes a un directorio especifico
     *
     * @param id llave de la fuente a descargar
     * @param url direccion del recurso
     * @param appPath path de la aplicación
     * @return
     */
    public boolean descargarFuente(String id, String url, String appPath) {
        boolean res = false;
        //Crear ruta de guardado con el nombre del directorio
        String savePath = appPath + File.separator + SAVE_DIR;
        File saveDir = new File(savePath);
        //Si el directorio no existe, crearlo
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        try {
            //Obtener la url, abrir una conexión y obtener su flujo de entrada.
            URL fileurl = new URL(url);
            URLConnection urlconn = fileurl.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            //Generar nombre del archivo y crearlo
            String fileName = savePath + File.separator + extraerNombre(url);
            File file = new File(fileName);
            //Si el archivo no existe crearlo, en otro caso eliminarlo
            if (!file.exists()) {
                file.createNewFile();
            } else if (file.exists()) {
                file.delete();
            }
            //Abrir los flujos de escritura
            PrintWriter pw  = new PrintWriter(file.getAbsolutePath());
            StringBuilder buffer = new StringBuilder();
            String inputLine = null;
            //Iterar el flujo para obtener el contenido y almacenarlo en el buffer
            while ((inputLine = br.readLine()) != null) {
                buffer.append(inputLine).append("\n");
            }
            //Imprimir el buffer en el flujo y cerrar el flujo
            pw.print(buffer);
            pw.close();
            LOG.log(Level.INFO, "SourcesDAO#descargarFuente() - Archivo({0}) guardado satisfactoriamente!!!", id);
            res = true;
            actualizarFecha(id);
        } catch (IOException e) {
            LOG.log(Level.INFO, "SourcesDAO#descargarFuente() - Ocurrio un problema al descargar el archivo: {0}", e.getMessage());
        }
        return res;
    }
    
    /**
     * Método para obtener el nombre del archivo a partir de una url
     * 
     * @param url url a analizar
     * 
     * @return cadena con el nombre del archivo
     */
    private String extraerNombre(String url) {
        //Tokenizer para dividir la cadena
        StringTokenizer tokenizer = new StringTokenizer(url, "/");
        //Crear un arreglo para almacenar los tokens
        String[] tokens = new String[tokenizer.countTokens()];
        int i = 0;
        //Iterar los elementos encontrados para poblar el arreglo
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            tokens[i] = token;
            i++;
        }
        //Retornar el ultimo token que contiene el nombre
        return tokens[3];
    }

    /**
     * Método que se encarga de actualizar el registro de la fecha
     * 
     * @param id identificador de la fuente a actualizar
     */
    private void actualizarFecha(String id) {
        //Parsear el identificador a Entero
        Integer idI = Integer.parseInt(id);
        try {
            //Obtener la conexión y preparar el Statement
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlUpdateDate);
            pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
            pstmt.setInt(2, idI);
            pstmt.executeUpdate();
            LOG.log(Level.INFO, "Fecha actualizada correctamente");
        } catch (SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n de SQL: {0}", e.getMessage() + e.getSQLState());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar la conexi\u00f3n: {0}", e.getMessage());
            }
        }
    }
    
    /**
     * CODIGO BASURA
     */

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
    
    /**
     * TODO: Eliminar esté metodo
     */
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

}
