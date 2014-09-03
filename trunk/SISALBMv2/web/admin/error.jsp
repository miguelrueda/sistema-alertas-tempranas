<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="resources/css/general.css" type="text/css" rel="stylesheet" /> 
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="/sisalbm/resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="title">&nbsp;Error</div>
                <div id="workarea">
                    <div id="menu">
                        <nav>
                            <ul>
                                <li><a href="/admin/scanner/scan.jsp">Inicio</a></li>
                            </ul>
                        </nav>
                        <!--
                        <table>
                            <tr>
                                <td align="center" valign="top" width="15%"><a href="admin/Index.html">Página Administrativa</a></td>
                                <td align="center" valign="top" width="15%"><a href="consulta/Index.html">Página de Consulta</a></td>
                            </tr>
                        </table>
                        -->
                    </div>
                    <div id="content_wrap">
                        <div id="content">
                            <p>
                                <% if (response.getStatus() == 500) {%>
                                <font color="red">Error: <%= exception.getMessage()%></font><br />
                                <% } else {%>
                                Ha ocurrido un problema con la petición. <br/>
                                Código de Error: <%= response.getStatus()%><br />
                                <% }%>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>