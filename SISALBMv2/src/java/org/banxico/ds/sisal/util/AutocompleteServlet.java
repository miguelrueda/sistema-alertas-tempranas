package org.banxico.ds.sisal.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.banxico.ds.sisal.dao.GruposDAO;
import org.banxico.ds.sisal.dao.SoftwareDAO;
import org.banxico.ds.sisal.entities.Grupo;

/**
 * Servlet que sirve como utilidad para realizar los autocompletados que se encuentren en la aplicación
 *
 * @author t41507
 * @version 26.08.2014
 */
public class AutocompleteServlet extends HttpServlet {
    
    /**
     * Atributo Logger
     */
    private static final Logger LOG = Logger.getLogger(AutocompleteServlet.class.getName());
    /**
     * DAOS
     */
    private SoftwareDAO swdao;
    private GruposDAO gdao;
    
    /**
     * Método que se encarga de procesar todas las solicitudes que se reciban
     *
     * @param request referencia al objeto de solicitud
     * @param response referencia al objeto de respuesta
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Establecer parametros de la respuesta
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        PrintWriter out = response.getWriter();
        //Inicializar el DAO
        swdao = new SoftwareDAO();
        gdao = new GruposDAO();
        try {
            //Obtener la accion a realizar y la llave del fabricante
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
            } else if(action.equalsIgnoreCase("validarNombreGrupo")) {
                String nombregrupo = request.getParameter("nombregrupo");
                LOG.log(Level.INFO, "Buscando el grupo: {0}", nombregrupo);
                List<Grupo> listaGrupos = gdao.getListaGrupos();
                boolean flag = false;
                for (Grupo grupo : listaGrupos) {
                    if (grupo.getNombre().toLowerCase().equals(nombregrupo.toLowerCase())) {
                        LOG.log(Level.INFO, "Se encontro el grupo: {0}", grupo.getNombre());
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    out.print("INVALIDO");
                } else {
                    LOG.log(Level.INFO, "No se encontro el grupo, registrarlo");
                    out.print("VALIDO");
                }
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
    
    /**
     * Metodo doGet
     *
     * @param request refernecia al objeto de solicitud
     * @param response referencia al objeto de respuesta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Metodo doPost
     *
     * @param request refernecia al objeto de solicitud
     * @param response referencia al objeto de respuesta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
