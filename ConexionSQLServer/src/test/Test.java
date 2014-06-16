package test;

import database.ConexionDB;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Test {
    
    public static void main(String[] args) {
        Connection miConexion = ConexionDB.getConnection();
        if (miConexion != null) {
            JOptionPane.showMessageDialog(null, "Conexión Realizada Exitosamente.");
        }
    }
    
}
