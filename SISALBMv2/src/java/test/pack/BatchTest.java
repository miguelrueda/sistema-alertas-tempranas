package test.pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatchTest {

    private static final Logger LOG = Logger.getLogger(BatchTest.class.getName());

    public static void main(String[] args) {
        /*
        Connection miConexion = ConexionDB.getConnection("sisalbmdb");
        if (miConexion != null) {
            PreparedStatement pstmt;
            String insertquery = "insert into test(id, valor) values (?, ?)";
            String querytest = "SELECT * FROM test";
            int batchSize = 1000;
            int count = 0;
            int temp = 0;
            LOG.log(Level.INFO, "Query: {0}", querytest);
            try {
                pstmt = miConexion.prepareStatement(insertquery);
                for (int i = 0; i < 5000; i++) {
                    pstmt.setInt(1, (i + 1));
                    pstmt.setString(2, "Valor: " + (i + 1));
                    pstmt.addBatch();
                    if (++count % batchSize == 0) {
                        pstmt.executeBatch();
                        System.out.println("Ejecutando Batch: " + temp);
                        temp++;
                    }
                }
                pstmt.executeBatch();
                pstmt = miConexion.prepareStatement(querytest);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " - " + rs.getString(2));
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, e.getMessage());
            } finally {
                try {
                    miConexion.close();
                } catch (SQLException e) {
                }
            }
        } else {
            System.out.println("Error de Conexión");
        }
*/
    }
}