<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page import="mx.org.banxico.sisal.entities.FuenteApp"%>
<%@page import="mx.org.banxico.sisal.dao.SourcesDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Fuente</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            $(document).ready(function() {
                $("#dialog-message").hide();
                /*
                 $("#namef").on("input", function() {
                 var input = $(this);
                 var isname = input.val();
                 if (isname) {
                 $("#namevalidation").hide();
                 $("#actualizar").attr("disabled", false);
                 } else {
                 $("#namevalidation").addClass("error");
                 $("#namevalidation").html("El nombre ingresado no es válido");
                 $("#namevalidation").show();
                 $("#actualizar").attr("disabled", true);
                 }
                 });
                 $("#urlf").on("input", function() {
                 var input = $(this);
                 if (input.length < 1) {
                 $("#actualizar").attr("disabled", false);
                 } else {
                 $("#actualizar").attr("disabled", true);
                 }
                 });
                 */
                $("#actualizar").click(function(e) {
                    var name = $("#namef").val();
                    var url = $("#urlf").val();
                    var a = $("#form").serialize();
                    var idf = $("#idf").val();
                    //alert("idf=" + idf + "&" + a);
                    $.ajax({
                        type: 'get',
                        url: '/sisalbm/admin/configuration.controller?action=edit&tipo=1',
                        data: "idf=" + idf + "&" + a,
                        success: function(result) {
                            if (result === "true") {
                                //alert("Edición Completa");
                                $("#dialog-message").attr("title", "Edición Completa");
                                var content = "<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La Fuente ha sido actualizada de forma exitosa.</p>";
                                $("#dialog-message").html(content);
                                $("#namef").attr("disabled", true);
                                $("#urlf").attr("disabled", true);
                                $("#actualizar").attr("disabled", true);
                            } else if (result === "nombre") {
                                $("#dialog-message").attr("title", "Error de entrada");
                                var content = "<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "El nombre ingresado no es válido</p>";
                                $("#dialog-message").html(content);
                            } else if (result === 'url') {
                                $("#dialog-message").attr("title", "Error de entrada");
                                var content = "<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La URL ingresada no es válida.<br />La URL debe cumplir con el prefijo: ([http|https]://)</p>";
                                $("#dialog-message").html(content);
                            }
                            else {
                                //alert("Edición Incompleta");
                                $("#dialog-message").attr("title", "Edición Incompleta");
                                var content = "<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "Ocurrio un error al realizar la actualización. Favor de intentarlo nuevamente.</p>";
                                $("#dialog-message").html(content);
                            }
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Ok: function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });
                        }
                    });
                });
            });
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
                    <nav>
                        <ul>
                            <li><a href="../../AppIndex.html">AppIndex</a></li>
                            <li>
                                <a href="#">Configuración</a>
                                <ul>
                                    <li><a href="../configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Vulnerabilidades</a>
                                <ul>
                                    <li><a href="../vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                    <li><a href="../vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                    <li><a href="../vulnerability.controller?action=view&tipo=3">Software Soportado</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="/sisalbm/admin/scanner/scan.jsp">Escaneo</a>
                                <!--
                                <ul>
                                    <li><a href="/sisalbm/admin/scanner/scan.jsp">Nuevo Escaneo</a></li>
                                </ul>
                                -->
                            </li>
                            <li>
                                <a href="#">Reportes</a>
                                <ul>
                                    <li><a href="#">Generar Reporte</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                    <div id="content_wrap">
                        <div id="content">
                            <div class="editForm">
                                <form id="form" name="editSrcForm" class="form" >
                                    <fieldset>
                                        <legend>Edición de la fuente:</legend>
                                        <%
                                            FuenteApp fuente = ((SourcesDAO) session.getAttribute("sourcesdao")).obtenerFuentePorId(id);
                                        %>
                                        <fmt:formatDate value="<%= fuente.getFechaActualizacion()%>" var="parsedDate" dateStyle="long" />
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <label for="idf">Id:</label>
                                                    </td>
                                                    <td>
                                                        <input name="idf" id="idf" type="text" value="<%= fuente.getId()%>" disabled="true" class="src" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Nombre:</label>
                                                    </td>
                                                    <td>
                                                        <input name="namef" id="namef" type="text" value="<%= fuente.getNombre()%>" class="src" />
                                                    </td>
                                                    <td>
                                                        <label for="namef" id="namevalidation"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Url:</label>
                                                    </td>
                                                    <td>
                                                        <input name="urlf" id="urlf" type="text" value="<%= fuente.getUrl()%>" class="src" />
                                                    </td>
                                                    <td>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label for="datef">Ultima Actualización:</label>
                                                    </td>
                                                    <td>
                                                        <input name="datef" id="datef" type="text" value="${parsedDate}" disabled="true" class="src" /><br />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                    <input id="actualizar" type="button" value="Actualizar Fuente" name="submit" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog-message">
            </div>
        </div>

    </body>
</html>