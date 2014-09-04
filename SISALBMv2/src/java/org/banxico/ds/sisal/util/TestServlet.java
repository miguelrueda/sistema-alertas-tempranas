package org.banxico.ds.sisal.util;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.banxico.ds.sisal.dao.GruposDAO;
import org.banxico.ds.sisal.dao.SoftwareDAO;
import org.banxico.ds.sisal.entities.Grupo;
import org.banxico.ds.sisal.entities.Software;

public class TestServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (!request.getParameter("action").equals("")) {
            String action = request.getParameter("action");
            if (action.equalsIgnoreCase("editSoftware")) {
                String fabricante = request.getParameter("fabricante");
                String nombre = request.getParameter("nombre");
                String version = request.getParameter("version");
                String tipo = request.getParameter("tipo");
                String eol = request.getParameter("eol");
                out.print("OK");
            } else if (action.equalsIgnoreCase("searchGroup")) {
                GruposDAO gdao = new GruposDAO();
                String key = request.getParameter("key");
                List<Grupo> listaGrupos = gdao.buscarGrupo(key);
                if (listaGrupos.size() >= 1) {
                    StringBuilder sb = new StringBuilder();
                    for (Grupo grupo : listaGrupos) {
                        sb.append("<tr>");
                        sb.append("<td>")
                                .append(grupo.getNombre())
                                .append("</td>");
                        sb.append("<td colspan='2'>")
                                .append(grupo.getCategoria())
                                .append("</td>");
                        sb.append("</tr>");
                        sb.append("<tr>").append("<td colspan='3' style='text-align:center'>").append("Software Registrado en el Grupo").append("</td>").append("</tr>");
                        for (Software sw : gdao.obtenerSoftwaredeGrupo(grupo.getIdGrupo())) {
                            sb.append("<tr>");
                            sb.append("<td>").append(sw.getFabricante()).append("</td>");
                            sb.append("<td>").append(sw.getNombre()).append("</td>");
                            sb.append("<td>").append(sw.getVersion()).append("</td>");
                            sb.append("</tr>");
                        }
                    }
                    out.print(sb.toString());
                } else {
                    out.print("NOT_FOUND");
                }
            } else if (action.equalsIgnoreCase("deleteSW")) {
                String swkey = request.getParameter("swid");
                SoftwareDAO swdao = new SoftwareDAO();
                boolean flag = swdao.eliminarSoftware(Integer.parseInt(swkey));
                if (flag) {
                    out.print("OK");
                } else {
                    out.print("ERROR");
                }
            } else if (action.equalsIgnoreCase("deleteGroup")) {
                String gid = request.getParameter("gid");
                boolean flag = true;
                if (flag) {
                    out.print("OK");
                } else {
                    out.print("ERROR");
                }
            }
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