package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.Scheduler;
import scheduler.MyScheduler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.InputStream;
import java.util.Properties;

public class SchedServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SchedServlet.class.getName());

    @Override
    public void init() throws ServletException {
        try {
            LOG.log(Level.INFO, "Iniciando servlet de prueba");
            MyScheduler mSched = new MyScheduler();
            mSched.start();
        } catch (Exception e) {
            LOG.log(Level.INFO, "Problema al iniciar el scheduler: {0}", e.getMessage());
        }
    }

    private Scheduler sched;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SchedServlet</title>");
            out.println("</head>");
            out.println("<body>");
            //Leer registro de DB
            
            out.println("<table style='border: 1px solid #CBCBCB; width:100%'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<td>");
            out.println("Id");
            out.println("</td>");
            out.println("<td>");
            out.println("Ultima Actualización");
            out.println("</td>");
            out.println("<td>");
            out.println("Valor Temporal");
            out.println("</td>");
            out.println("</tr>");
            out.println("</thead>");
            
            conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM pruebaquartzeliminar;");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>");
                out.println(rs.getInt(1));
                out.println("</td>");
                out.println("<td>");
                out.println(rs.getDate(2));
                out.println("</td>");
                out.println("<td>");
                out.println(rs.getInt(3));
                out.println("</td>");
                out.println("</tr>");
            }
            rs.close();
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Connection getConnection() {
        Connection connection = null;
        try {
            Properties prop = new Properties();
            InputStream is = SchedServlet.class.getResourceAsStream("db.properties");
            prop.load(is);
            String driverClass = prop.getProperty("driver");
            Class.forName(driverClass);
            String connUrl = prop.getProperty("url");
            connection = DriverManager.getConnection(connUrl);
        } catch (IOException | SQLException e) {
            LOG.log(Level.INFO, "Ocurrio una excepci\u00f3n al iniciar la conexi\u00f3n: {0}", e.getMessage());
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, "No se encontro el driver: {0}", ex.getMessage());
        }
        return connection;
    }

}