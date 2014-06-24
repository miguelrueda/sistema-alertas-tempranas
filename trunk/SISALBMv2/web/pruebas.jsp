<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script>
            $(document).ready(function() {
                $("#submit").click(function(event) {
                    var username = $("#user").val();
                    alert(username);
                    $("#welcometext").html('');
                    $.get('admin/url/Servlet.do', {user: username, age: 22}, function(responseText) {
                        $("#welcometext").text(responseText);
                        alert(responseText);
                    });
                });
            });
        </script>
    </head>
    <body>
        <form id="form1">
            <h1>Prueba de AJAX</h1>
            Ingresa tu nombre: <input type="text" id="user" />
            <input type="button" id="submit" value="Ajax Submit" />
            <br />
            <div id="welcometext"></div>
        </form>
    </body>
</html>