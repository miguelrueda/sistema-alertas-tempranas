<%@page import="org.banxico.ds.sisal.dao.GruposDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle del Grupo</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
    </head>
    <%
        String tipo = request.getParameter("tipo");
        String id = request.getParameter("id");
        int idgrupo = Integer.parseInt(id);
    %>
    <body>
        <div class="datagrid">
            <%
                out.print(((GruposDAO) session.getAttribute("gruposdao")).describirGrupo(idgrupo));
            %>
        </div>
    </body>
</html>

