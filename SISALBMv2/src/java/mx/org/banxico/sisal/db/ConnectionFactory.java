package mx.org.banxico.sisal.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    
    private static final Logger LOG = Logger.getLogger(ConnectionFactory.class.getName());
    private static final ConnectionFactory instance = new ConnectionFactory();
    //TODO: Cambiar por URL DE BM
    String url = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=sisalbmdb;user=sa;password=root;";
    //TODO: Comprobar Clase del Driver
    String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    private ConnectionFactory () {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Ocurrio un error al buscar la clase del Driver: {0}", e.getMessage());
        }
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }
    
    public Connection getConnection() {
        Connection connection = null;
        try {
            Properties prop = new Properties();
            InputStream is = ConnectionFactory.class.getResourceAsStream("db.properties");
            prop.load(is);
            String driver = prop.getProperty("driver");
            String purl = prop.getProperty("url");
            Class.forName(driver);
            connection = DriverManager.getConnection(purl);
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n al iniciar la conexi\u00f3n SQL: {0}", ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al buscar la clase del Driver: {0}", ex.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al leer el archivo de propiedades: {0}", ex.getMessage());
        }
        return connection;
    }
    
}