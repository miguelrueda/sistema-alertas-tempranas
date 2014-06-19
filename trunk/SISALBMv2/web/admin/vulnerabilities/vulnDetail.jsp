<%@page import="mx.org.banxico.sisal.dao.VulnerabilityDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
    </head>
    <%
        String name = request.getParameter("name");
        String tipo = request.getParameter("tipo");
        int type = Integer.parseInt(tipo);
    %>
    <body>
        <div class="datagrid">
            <%
                out.print(((VulnerabilityDAO) session.getAttribute("vulndao")).describirCVE(type, name));
            %>
        </div>
    </body>
</html>
