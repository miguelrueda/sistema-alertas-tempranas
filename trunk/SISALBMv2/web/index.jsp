<%-- 
    INDEX de la aplicación, se encarga de hacer la redirección hacia el jsp de dashboard
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String url = "/sisalbm/admin/dashboard.jsp";
            response.sendRedirect(url);
        %>
    </body>
</html>
