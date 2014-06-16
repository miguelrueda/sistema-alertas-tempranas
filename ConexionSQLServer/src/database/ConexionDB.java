package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionDB {
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=netbeanstest;user=sa;password=root";
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Error en la conexión de BD: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            conn = null;
        } 
        return conn;
    }
    
}