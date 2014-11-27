<%-- 
    JSP que muestra listado con todas las fuentes registradas en el sistema, se da la opcón de editar
    o realizar la descarga de la actualización de las mismas
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Administración de Fuentes</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <link href="../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="../resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Fuentes de Información</div>
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>URL</th>
                                            <th>Ultima Actualización</th>
                                            <th colspan="2">Opciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${noOfRecords gt 0}">
                                                <c:forEach var="src" items="${fuentes}">
                                                    <fmt:formatDate value="${src.fechaActualizacion}"  var="parsedDate" dateStyle="long"/>
                                                    <tr>
                                                        <td>${src.nombre}</td>
                                                        <td id="dwnldurl">${src.url}</td>
                                                        <td id="fechaFuente">${parsedDate}</td>
                                                        <td>
                                                            <a href="configuration/editSource.jsp?id=${src.id}">
                                                                <img src="../resources/images/edit.png" alt="editar" id="tableicon" />
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <a href="#">
                                                                <img src="../resources/images/download.png" 
                                                                     alt="id=${src.id}&url=${src.url}" id="tableicon" class="dwnldBtn" />
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="5" style="text-align: center">No se encontraron registros en la fuente de datos</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div><!-- data grid -->
                        </div><!-- content -->
                        <br />
                    </div>
                </div>
            </div><!-- page content -->
            <div id="dialog-message"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <script>
            /**
             * Función de jQuery que maneja la funcionalidad de la página
             */
            $(document).ready(function() {
                $("#dialog-message").hide();
                /*
                 * Función que se ejecuta cuando se realiza clic sobre la opción ver detalle de una fuente
                 */
                $(".view").click(function() {
                    $("#thedialog").attr('src', $(this).attr("href"));
                    $("#dialogdiv").dialog({
                        width: 400,
                        height: 400,
                        modal: true,
                        resizable: false,
                        draggable: false,
                        open: function() {
                            $('.ui-widget-overlay').addClass('custom-overlay');
                        },
                        close: function() {
                            $("#thedialog").attr("src", "about:blank");
                        }
                    });
                    return false;
                });
                /**
                 * Función que se ejecuta cuando se hace clic sobre la opción de descarga de alguna de las 
                 * fuentes registradas
                 */
                $(".dwnldBtn").click(function(e) {
                    //Se obtienen los parametros del enlace del boton
                    var param = $(this).attr("alt");
                    var tokens = param.split("&");
                    var tk = tokens[1].split("=");
                    //Se inicia la petición al servidor
                    $.ajax({
                        type: 'GET',
                        url: '/sisalbm/admin/configuration.controller?action=download&' + param,
                        beforeSend: function() {
                            //Se muestra una nnotificación de la descarga
                            jQuery.noticeAdd({
                                text: "Procesando Descarga:" + //+ tk[1] + + 
                                        "<br /><center><img src='../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                                stay: true,
                                type: 'info'
                            });
                        }, success: function(result) {
                            result = result.trim();
                            if (result === 'UPDATED') {
                                //Si la fuente esta actualizada se muestra un mensaje
                                $("#dialog-message").attr("title", "Referencia Actualizada");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-minus' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La referencia está actualizada, no se requiere actualizar.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (result === 'OK') {
                                //Se muestra ésta ventana cuando la petición se completa satisfactoriamente
                                $("#dialog-message").attr("title", "Actualización Exitosa");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La descarga ha sido completada de forma exitosa.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (result === 'ERROR') {
                                //Se muestra ésta ventana cuando ocurre un error al realizar la descarga
                                $("#dialog-message").attr("title", "Error de Descarga");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "Ocurrió un error al realizar la descarga.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            }
                        }, error: function() {
                            //Se muestra ésta ventana cuando ocurre un error al realizar la petición hacia el servidor
                            $("#dialog-message").attr("title", "Petición Incompleta");
                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "Ocurrió un error al realizar la petición al servidor. Inténtelo nuevamente.</p>");
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Aceptar: function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });
                        }, complete: function(data) {
                            //Cuando se completa la petición eliminar la notificación
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