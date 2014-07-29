package scheduler;

import java.io.IOException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Date;
import java.io.InputStream;
import java.util.Properties;

public class AddJob implements Job {

    private static final Logger LOG = Logger.getLogger(AddJob.class.getName());
    private Random rd;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        rd = new Random();
        Integer s1 = rd.nextInt(1000);
        Integer s2 = rd.nextInt(1000);
        Integer res = s1 + s2;
        LOG.log(Level.INFO, "{0} + {1} = {2}", new Object[]{s1, s2, res});
        conn = getConnection();
        actualizarFecha(1, jec.getFireTime(), rd.nextInt(10));
        //Guardar TimeStamp en DB
        /*
         long delay = 5000L;
         try {
         System.out.println("Durmiendo");
         Thread.sleep(delay);
         } catch (InterruptedException e) {
         LOG.log(Level.INFO, "Ocurrio un error en el hilo de espera: {0}", e.getMessage());
         }
         System.out.println("Delay terminado");
         */
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            Properties prop = new Properties();
            InputStream is = AddJob.class.getResourceAsStream("db.properties");
            prop.load(is);
            String driver = prop.getProperty("driver");
            Class.forName(driver);
            String connUrl = prop.getProperty("url");
            conn = DriverManager.getConnection(connUrl);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            LOG.log(Level.SEVERE, "Ocurrio una excepci\u00f3n al obtener la conexi\u00f3n: {0}", e.getMessage());
        }
        return conn;
    }
    
    private static final String updateQry = "UPDATE pruebaquartzeliminar SET fecha = ?, valortemporal = ? WHERE id = ?";

    private void actualizarFecha(int id, Date fireTime, int valortemporal) {
        try {
            System.out.println("Guardare el valor: " + valortemporal);
            pstmt = conn.prepareStatement(updateQry);
            pstmt.setDate(1, new java.sql.Date(fireTime.getTime()));
            pstmt.setInt(2, valortemporal);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            LOG.log(Level.INFO, "Actualización Realizada");
            pstmt.close();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.log(Level.INFO, "Error al terminar la conexi\u00f3n de la BD: {0}", e.getMessage());
            }
        }
    }

}