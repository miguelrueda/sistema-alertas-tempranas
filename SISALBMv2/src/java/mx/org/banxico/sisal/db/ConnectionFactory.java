package mx.org.banxico.sisal.db;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Logger LOG = Logger.getLogger(ConnectionFactory.class.getName());
    private static ConnectionFactory instance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Properties prop = new Properties();
            InputStream is = ConnectionFactory.class.getResourceAsStream("db.properties");
            prop.load(is);
            String driverClass = prop.getProperty("driver");
            String connUrl = prop.getProperty("url");
            Class.forName(driverClass);
            LOG.log(Level.INFO, "Driver obtenido correctamente");
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Ocurrio un error al buscar la clase del Driver: {0}", e.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al abrir el archivo de propiedades: {0}", ex.getMessage());
        }
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Properties prop = new Properties();
            InputStream is = ConnectionFactory.class.getResourceAsStream("db.properties");
            prop.load(is);
            String connUrl = prop.getProperty("url");
            connection = DriverManager.getConnection(connUrl);
            LOG.log(Level.INFO, "BD - Conexión establecida correctamente");
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n al iniciar la conexi\u00f3n SQL: {0}", ex.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al abrir el archivo de propiedades: {0}", ex.getMessage());
        } catch (SecurityException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

}
