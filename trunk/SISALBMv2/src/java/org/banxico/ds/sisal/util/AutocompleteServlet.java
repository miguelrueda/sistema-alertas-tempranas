package org.banxico.ds.sisal.util;

import java.io.IOException;
import java.io.PrintWriter;
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
            } else if (action.equalsIgnoreCase("test")) {
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                String nombre = request.getParameter("nombre");
                LOG.log(Level.INFO, "Parametro nombre: " + nombre);
                sb.append("nombre: ").append(nombre).append(", ");
                String categoria = request.getParameter("categoria");
                LOG.log(Level.INFO, "parametro categoria: " + categoria);
                sb.append("categoria: ").append(categoria).append(", ");
                String keys = request.getParameter("producto");
                LOG.log(Level.INFO, "Parametro producto: " + keys);
                sb.append("llaves: ").append("{");
                StringTokenizer st = new StringTokenizer(keys, "[,]");
                int ntokens = st.countTokens();
                String [] temp = new String[ntokens];
                int i = 0;
                while (st.hasMoreTokens()) {
                    temp[i] = st.nextToken().replace("\"", "").replace("\\r\\n", "");
                    i++;
                }
                for (String string : temp) {
                    sb.append(string).append(",");
                }
                sb.append("}");
                sb.append("}");
                out.println(sb.toString());
            }
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
