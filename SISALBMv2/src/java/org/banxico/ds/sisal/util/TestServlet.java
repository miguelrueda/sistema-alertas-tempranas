package org.banxico.ds.sisal.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class TestServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (!request.getParameter("action").equals("")) {
            if (request.getParameter("action").equalsIgnoreCase("editSoftware")) {
                String fabricante = request.getParameter("fabricante");
                String nombre = request.getParameter("nombre");
                String version = request.getParameter("version");
                String tipo = request.getParameter("tipo");
                String eol = request.getParameter("eol");
                out.print("OK");
            } else {
                out.print("ERROR");
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