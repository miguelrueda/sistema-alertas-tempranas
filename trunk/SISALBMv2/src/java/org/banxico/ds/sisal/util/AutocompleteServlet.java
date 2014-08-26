package org.banxico.ds.sisal.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.banxico.ds.sisal.dao.GruposDAO;
import org.banxico.ds.sisal.dao.SoftwareDAO;

public class AutocompleteServlet extends HttpServlet {
    
    private SoftwareDAO swdao;
    private GruposDAO gdao;
    private static final Logger LOG = Logger.getLogger(AutocompleteServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        PrintWriter out = response.getWriter();
        swdao = new SoftwareDAO();
        gdao = new GruposDAO();
        try {
            String action = request.getParameter("action");
            String vendorq = request.getParameter("vendorq");
            //LOG.log(Level.INFO, "Parametros recibidos: action={0}&vendorq={1}", new Object[]{action, vendorq});
            if (action.equalsIgnoreCase("getvendor")) {
                List<String> vendorList = swdao.completarFabricantes(vendorq.toLowerCase());
                for (String vendor : vendorList) {
                    out.println(vendor);
                }
            } else if (action.equalsIgnoreCase("getproduct")) {
                List<String> products = swdao.obtenerProductosPorFabricanteAC(vendorq.toLowerCase());
                out.println("<option value='0'>Seleccionar Producto</option>");
                for (String prod : products) {
                    out.println("<option value='" + prod + "'>" + prod + "</option>");
                }
            } else if (action.equalsIgnoreCase("getversion")) {
                String product = request.getParameter("product");
                List<String> versiones = swdao.obtenerVersionesdeProducto(product.toLowerCase());
                out.println("<option value='0'>Seleccionar Versión</option>");
                for (String version : versiones) {
                    out.println("<option value='" + version + "'>" + version + "</option>");
                }
            } else if (action.equalsIgnoreCase("getcats")) {
                List<String> listaCats = gdao.obtenerCategorias();
                out.println("<option value='0'>Seleccionar Categoría</option>");
                for (String cat : listaCats) {
                    out.println("<option value='" + cat + "'>" + cat + "</option>");
                }
            } else if (action.equalsIgnoreCase("acproduct")) {
                String prodq = request.getParameter("prodq");
                List<String> prodsList = swdao.completarProductos(prodq.toLowerCase());
                for (String prod : prodsList) {
                    out.println(prod);
                } //action=getProdId&prodName
            } else if (action.equalsIgnoreCase("getProdId")) {
                String prodName = request.getParameter("prodName");
                String prodid = swdao.buscarIdProducto(prodName);
                out.println(prodid);
            } 
            /*
            else if (action.equalsIgnoreCase("test")) {
                String nombre = request.getParameter("nombre");
                String tipo = request.getParameter("tipo");
                String categoria = null;
                if (tipo.equalsIgnoreCase("existente")) {
                    categoria = request.getParameter("categoria");
                } else if (tipo.equalsIgnoreCase("nueva")) {
                    categoria = request.getParameter("nuevacat");
                }
                String keys = request.getParameter("producto");
                StringTokenizer st = new StringTokenizer(keys, "[,]");
                int ntokens = st.countTokens();
                Integer [] temp = new Integer[ntokens];
                int i = 0;
                while (st.hasMoreTokens()) {
                    temp[i] = Integer.parseInt(st.nextToken().replace("\"", "").replace("\\r\\n", ""));
                    i++;
                }
                boolean created = false;
                try {
                    created = gdao.crearGrupo(nombre, categoria, temp);
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, "ocurrio un error al crear el grupo: {0}", ex.getMessage());
                }
                if (created) {
                    out.print("OK");
                } else if (!created) {
                    out.print("ERROR");
                } else {
                    out.print("UNKNOWN");
                }
            }*/
        } finally {
            out.close();
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

}
