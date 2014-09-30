<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados</title>
        <link href="resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="resources/css/menu.css" type="text/css" rel="stylesheet" />
        <style type="text/css">
            .botonesjuntos {
                border: 1px solid #FFF;
                width: 400px;
                height: 60px;
                display: block !important;
                margin-left: auto !important;
                margin-right: auto !important;
            }
            .boton {
                margin: 10px;
                float: left;
            }
        </style>
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
                <!--<div id="title">&nbsp;Versión Adminstrativa</div>-->
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>
                    <br />
                    <div id="content_wrap">
                        <c:choose>
                            <c:when test="${noOfResults > 0}">
                                <div id="page_title">Se encontraron: ${noOfResults} posibles amenazas.</div>
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
                                                    <th>Gravedad</th>
                                                    <th>Fecha de Publicación</th>
                                                    <th>Grupos Afectados</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="res" items="${resultados}">
                                                    <fmt:formatDate value="${res.vulnerabilidad.published}"  var="parsedDate" dateStyle="long"/>
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
                                                        <td>${parsedDate}</td>
                                                        <td>
                                                            <table cellspacing="0" cellpadding="0" style="border: none">
                                                                <c:forEach var="grp" items="${res.gruposList}">
                                                                    <tr><td>${grp}</td></tr>
                                                                </c:forEach>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="5">${res.vulnerabilidad.description}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <br />
                                    <div class="botonesjuntos">
                                        <input type="button" class="mailButton boton" id="mailButton" value="Enviar Resultados por Correo"
                                               title="Los resultados serán enviados al administrador."/>
                                        <input type="submit" class="exportButton boton" id="exportButton" value="Versión Texto"/><br/>
                                        <input type="submit" class="exportButton" id="okButton" value="Cerrar" />
                                    </div>
                                    
                                    <div id="export" style="max-width: 800px; display: block; margin-left: auto; margin-right: auto;">
                                        <div id="export-content">
                                        </div>    
                                    </div>
                                </c:when>
                            </c:choose>
                            <br />
                            <div id="dialog-message">
                                ${exportBuffer}
                            </div>
                            <br />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            $(document).ready(function() {
                $("#dialog-message").hide();
                $("#export").hide();
                $("#okButton").hide();
                $("#okButton").click(function(e) {
                    $("#okButton").hide();
                    $("#export").hide();
                });
                $("#exportButton").click(function(e) {
                    $("#dialog-message").attr("title", "Resultados");
                    $("#dialog-message").dialog({
                        modal: true,
                        width: 800,
                        height: 800,
                        draggable: false,
                        resizable: false,
                        buttons: {
                            Aceptar: function() {
                                $(this).dialog("close");
                            }
                        }
                    });
                });
                $("#mailButton").on("click", function() {
                    $.ajax({
                        url: '/sisalbm/scanner?action=sendResults',
                        type: 'POST',
                        success: function(response) {
                            if(response === 'ENVIADO') {
                                $("#dialog-message").attr("title", "Correo Enviado");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "El correo fue enviado exitosamente. Los resultados fueron enviados al administrador.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                            } else if(response === 'NOENVIADO') {
                                $("#dialog-message").attr("title", "Correo No Enviado");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "El correo no pudo ser enviado, favor de intentarlo nuevamente.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                            } else if(response === 'ERROR') {
                                $("#dialog-message").attr("title", "Error del Sistema");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "Ocurrio un error en la aplicación, favor de intentarlo nuevamente.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                            }
                        }, error: function() {
                            $("#dialog-message").attr("title", "Petición Incompleta");
                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                    + "Ocurrio un error al realizar la petición al servidor. Intentelo nuevamente.</p>");
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Aceptar: function() {
                                        $(this).dialog("close");
                                        $("#dialog-message").attr("title", "");
                                    }
                                }
                            });
                        }
                    });
                });
            });
        </script>
    </body>
</html>