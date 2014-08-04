package mx.org.banxico.sisal.servlets;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.logging.Level;
import mx.org.banxico.sisal.util.GenerarPDF;

public class TestReportServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(TestReportServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String tempDirFilePath = tempDirectory.getAbsolutePath();
        String fileName = "Reporte_" + System.currentTimeMillis() + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "max-age=0");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        try {
            GenerarPDF.createPDF(tempDirFilePath + "\\" + fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = convertirPDFaByteArray(tempDirFilePath + "\\" + fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (IOException e) {
            LOG.log(Level.INFO, "Ocurrio un error en la generaci\u00f3n del pdf: {0}", e.getMessage());
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

    private ByteArrayOutputStream convertirPDFaByteArray(String filename) {
        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(filename);
            byte [] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            LOG.log(Level.INFO, "No se encontro el archivo: {0}", e.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error de I/O: {0}", ex.getMessage());
        } finally {
            try {
             if (inputStream != null) {
                 inputStream.close();
            }   
            } catch (IOException e) {
                LOG.log(Level.INFO, "Ocurrio un error al cerrar el flujo de entrada: {0}", e.getMessage());
            }
        }
        return baos;
    }

}