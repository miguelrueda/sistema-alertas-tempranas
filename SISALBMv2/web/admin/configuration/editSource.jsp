<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page import="mx.org.banxico.sisal.entities.AppSource"%>
<%@page import="mx.org.banxico.sisal.dao.SourcesDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            /*$.get('admin/EditSource.do?action=edit&tipo=1', {id: id, nombre: nombre, url: url}, function(responseText) {
             $("#resdiv").text(responseText);
             alert(responseText);
             $.get(
             "admin/configuration.controller?action=edit&tipo=1",
             {name: "Miguel"},
             function(data) {
             alert(data);
             }
             );
             });
             */
        </script>
    </head>
    <%
        String srcId = request.getParameter("id");
        int id = Integer.parseInt(srcId);
    %>
    <body>


        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="../../resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="title">&nbsp;Versión Adminstrativa</div>
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <div id="page_title">Editar</div>
                        <div id="content">
                            <% out.print(id); %>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
   <div class="editForm">
            <form id="editSrcForm" name="editSrcForm">
                <%
                    AppSource fuente = ((SourcesDAO) session.getAttribute("sourcesdao")).getFuente(id);
                %>
                <fmt:formatDate value="<%= fuente.getFechaActualizacion()%>" var="parsedDate" dateStyle="long" />
                <label for="idf">Id:</label>
                <input name="idf" id="idf" type="text" value="<%= fuente.getId()%>" disabled="true" /><br />
                <label for="namef">Nombre:</label>
                <input name="namef" id="namef" type="text" value="<%= fuente.getNombre()%>" /><br />
                <label for="urlf">Url:</label>
                <input name="urlf" id="urlf" type="text" value="<%= fuente.getUrl()%>" /><br />
                <label for="datef">Ultima Actualización:</label>
                <input name="datef" id="datef" type="text" value="${parsedDate}" disabled="true" /><br />
                <input id="actualizar" type="button" value="Actualizar Fuente" name="submit" />
            </form>
        </div>