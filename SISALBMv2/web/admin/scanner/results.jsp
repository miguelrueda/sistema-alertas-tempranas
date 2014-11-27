<%-- 
    JSP que incluye el contenido del menu al que todos los JSP hacen referencia
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Resultados</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="resources/css/menu.css" type="text/css" rel="stylesheet" />
        <link href="/sisalbm/resources/css/jquery.notice.css" type="text/css" rel="stylesheet"/>
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
                                           title="Los resultados serán enviados al administrador." />
                                    </div>
                                    <!--<input type="submit" class="exportButton boton" id="exportButton" value="Versión Texto"/><br/>-->
                                        <!--<input type="submit" class="exportButton" id="okButton" value="Cerrar" />-->
                                    <div id="export" style="max-width: 800px; display: block; margin-left: auto; margin-right: auto;">
                                        <div id="export-content">
                                        </div>    
                                    </div>
                                </c:when>
                            </c:choose>
                            <br />
                            <div id="dialog-message">
                            </div>
                            <div id="mensaje-correo"></div>
                            <br />
                        </div><!-- content -->
                    </div>
                </div>
            </div><!-- page content -->
        </div>
        <br />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="/sisalbm/resources/js/jquery.notice.js"></script>
        <script>
            /**
             * Función jQuery que se encarga de manejar la funcionalidad de la aplicación
             */
            $(document).ready(function() {
                //Ocultar algunos elementos de la vista
                $("#dialog-message").hide();
                $("#mensaje-correo").hide();
                $("#export").hide();
                $("#okButton").hide();
                $("#okButton").click(function(e) {
                    $("#okButton").hide();
                    $("#export").hide();
                });
                /*
                 * Función que se muestra cuando se visualiza la versión texto
                 * de las vulnerabilidades
                 */
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
                /**
                 * Función que se ejecuta cuando se solicita enviar los resultados por correo
                 */
                $("#mailButton").on("click", function() {
                    $.ajax({
                        url: '/sisalbm/scanner?action=sendResults',
                        type: 'POST',
                        beforeSend: function() {
                            //Notificación de envio
                            jQuery.noticeAdd({
                                text: "Preparando correo <br /><center><img src='/sisalbm/resources/images/ajax-loader.gif' alt='Cargando...' /></center>",
                                stay: true,
                                type: 'info'
                            });
                        }, success: function(respuesta) {
                            respuesta = respuesta.trim();
                            if (respuesta.trim() === "ENVIADO") {
                                //Mensaje que se muestra cuando se envia de forma correcta el correo
                                $("#mensaje-correo").attr("title", "Correo Enviado");
                                $("#mensaje-correo").html("<p><span class='ui-icon ui-icon-check' style='float:left; margin:0 7px 50px 0;'>" +
                                        "</span>El correo fue enviado exitosamente.<br /> Los resultados fueron enviados al administrador.</p>");
                                $("#mensaje-correo").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (respuesta.trim() === 'NOENVIADO') {
                                //Mensaje que se muestra cuando no se puede enviar de forma correcta el correo
                                $("#mensaje-correo").attr("title", "Correo No Enviado");
                                $("#mensaje-correo").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "El correo no pudo ser enviado, favor de intentarlo nuevamente.</p>");
                                $("#mensaje-correo").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (respuesta.trim() === "ERROR") {
                                //Mensaje cque se muestra cuando el servidor retorna un error
                                $("#mensaje-correo").attr("title", "Error del Sistema");
                                $("#mensaje-correo").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "Ocurrió un error en la aplicación, favor de intentarlo nuevamente.</p>");
                                $("#mensaje-correo").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            }
                        }, error: function() {
                            //Mensaje que se muestra cuando ocurre un error al realizar la petición al servidor
                            $("#mensaje-correo").attr("title", "Petición Incompleta");
                            $("#mensaje-correo").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                    + "Ocurrió un error al realizar la petición al servidor. Inténtelo nuevamente.</p>");
                            $("#mensaje-correo").dialog({
                                modal: true,
                                buttons: {
                                    Aceptar: function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });
                        }, complete: function(data) {
                            //Cuando se completa la petición se  cierra la notificación
                            setInterval(function() {
                                jQuery.noticeRemove($('.notice-item-wrapper'), 400);
                            }, 5000);
                        }
                    });
                });
            });
        </script>
    </body>
</html>