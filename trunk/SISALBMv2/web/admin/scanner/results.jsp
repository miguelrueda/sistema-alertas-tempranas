<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados</title>
        <link href="resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            $(document).ready(function() {
                $("#dialog-message").hide();
                $("#export").hide();
                $("#okButton").hide();
                $("#exportButton").click(function(e) {
                    $("#okButton").show();
                    $.ajax({
                        url: '/sisalbm/scanner?action=export',
                        type: 'GET',
                        success: function(result) {
                            $("#export").show();
                            $("#export").html(result);
                            /*
                            $("#dialog-message").attr("title", "Información");
                            var content = "<p><span class='ui-icon ui-icon-circle-check' style='float:left;margin0 7px 50px 0;'> </span>Descarga</p>";
                            $("#dialog-message").html(content);
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Ok: function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });*/
                        }
                    });
                });
                $("#okButton").click(function(e) {
                    alert("Boton clickeado");
                    $("#okButton").hide();
                    $("#export").hide();
                    $("#export").html("");
                });
            });
        </script>
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="title">&nbsp;Versión Adminstrativa</div>
                <div id="workarea">
                    <nav>
                        <ul>
                            <li>
                                <a href="#">Configuración</a>
                                <ul>
                                    <li><a href="/sisalbm/admin/configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Vulnerabilidades</a>
                                <ul>
                                    <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                    <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                    <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=3">Software Soportado</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="#">Escaneo</a>
                                <ul>
                                    <li><a href="/sisalbm/admin/scanner/scan.jsp">Nuevo Escaneo</a></li>
                                </ul>
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
                        <c:choose>
                            <c:when test="${noOfResults > 0}">
                                <div id="page_title">Encontre: ${noOfResults} posibles amenazas.</div>
                            </c:when>
                            <c:when test="${noOfResults eq 0}">
                                <div id="page_title">No se encontraron coincidencias con los parámetros seleccionados.</div>
                            </c:when>
                        </c:choose>
                        <div id="content">
                            <c:choose>
                                <c:when test="${noOfResults > 0}">
                                    <div class="datagrid">
                                        <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                            <thead>
                                                <tr>
                                                    <th>Vulnerabilidad</th>
                                                    <th>Software Afectado</th>
                                                    <th>Criticidad</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="res" items="${resultados}">
                                                    <tr>
                                                        <td>${res.vulnerabilidad.name}</td>
                                                        <td>
                                                            <table cellspacing="0" cellpadding="0" style="border: none">
                                                                <c:forEach var="sw" items="${res.swList}">
                                                                    <tr><td>${sw.nombre}</td></tr>
                                                                </c:forEach>
                                                            </table>
                                                        </td>
                                                        <c:choose>
                                                            <c:when test="${res.vulnerabilidad.severity eq 'High'}">
                                                                <td>Alta</td>
                                                            </c:when>
                                                            <c:when test="${res.vulnerabilidad.severity eq 'Medium'}">
                                                                <td>Media</td>
                                                            </c:when>
                                                            <c:when test="${res.vulnerabilidad.severity eq 'Low'}">
                                                                <td>Baja</td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>Sin Definir</td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="3">${res.vulnerabilidad.description}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <p>
                                        <input type="submit" class="exportButton" id="exportButton" value="Versión Texto" /><br/>
                                        <input type="submit" class="exportButton" id="okButton" value="Cerrar" />
                                    </p>
                                    <div id="export" style="max-width: 800px; display: block; margin-left: auto; margin-right: auto;">
                                    </div>
                                </c:when>
                            </c:choose>
                            <br />
                            <div id="dialog-message">
                            </div>
                            <br />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
    </body>
</html>