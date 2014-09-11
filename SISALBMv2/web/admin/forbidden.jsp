<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prohibido</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="/sisalbm/resources/css/general.css" type="text/css" rel="stylesheet" /> 
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
                <div id="title">&nbsp;Recurso Prohibido</div>
                <div id="workarea">
                    <div id="content_wrap">
                        <div id="content">
                            <p style=" text-align: center; text-transform: uppercase; font-size: 1.4em; color: #003366;">
                                <% if (response.getStatus() == 500) {%>
                                <font color="red">Error: <%= exception.getMessage()%></font><br />
                                <% } else {%>
                                No tienes permisos suficientes para acceder a la aplicación. <br/>
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