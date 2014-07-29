<%-- 
    Document   : index
    Created on : 28/07/2014, 12:04:11 PM
    Author     : t41507
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <title>JSP Page</title>
        <script>
            $(document).ready(function() {
                $("#consulta").load("SchedServlet");
                var refreshId = setInterval(function() {
                    $("#consulta").load("SchedServlet");
                }, 30000);
                $.ajaxSetup({cache: false});
                /*
                setInterval(updateDiv, 30000);
                function updateDiv() {
                    $.ajax({
                        method: 'get',
                        url: 'ScheServlet',
                        success: function(data) {
                            $("#consulta").html(data);
                            counter++;
                            $("#contador").html(counter);
                        },
                        error: function() {
                            alert("Ocurrio un error al actualizar el contenido");
                        }
                    });
                }*/
            });
        </script>        
    </head>
    <body>
        <!--
        <h1>Hello World!</h1>
        <form method="get" action="SchedServlet">
            <input type="submit" value="Prueba" />
        </form>
        -->
        <div id="consulta"></div>
        <br />
        <div id="contador"></div>
    </body>
</html>