<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 align="center">Result JSP</h1>
        <p>
            <%
                List styles = (List) request.getAttribute("styles");
                Iterator it = styles.iterator();
                while (it.hasNext()) {
                    out.print("<br />try: " + it.next());
                }
            %>
        </p>
    </body>
</html>
