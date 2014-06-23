package mx.org.banxico.sisal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    
    private static ConnectionFactory instance = new ConnectionFactory();
    String url = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=sisalbmdb;user=sa;password=root;";
    String user = "USER";
    String password = "PASSWORD";
    String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final Logger LOG = Logger.getLogger(ConnectionFactory.class.getName());
    
    private ConnectionFactory () {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.INFO, "No se encontro la clase: {0}", driverClass);
        }
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }
    
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n SQL: {0}", ex.getMessage());
        }
        return connection;
    }
    
}