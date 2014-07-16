<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de Fuentes</title>
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <!--
        <script type="text/javascript" src="../resources/js/jquery.growl.js" ></script>
        <link href="../resources/css/jquery.growl.css" type="text/css" rel="stylesheet" />
        -->
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <link href="../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />
        <script>
            $(document).ready(function() {
                $("#dialog-message").hide();
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
                $(".dwnldBtn").click(function(e) {
                    var param = $(this).attr("alt");
                    //$.growl.notice({title: "Info", message: "Descargando el archivo: " + url});
                    var tokens = param.split("&");
                    var tk = tokens[1].split("=");
                    //alert(tk[1]);
                    //alert("/sisalbm/admin/configuration.controller?action=download&" + param);
                    //alert(param);
                    $.ajax({
                        type: 'GET',
                        url: '/sisalbm/admin/configuration.controller?action=download',
                        data: param,
                        beforeSend: function() {
                            jQuery.noticeAdd({
                                text: "Procesando Descarga:" + //+ tk[1] + + 
                                        "<br /><center><img src='../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                                stay: false,
                                type: 'info'
                            });
                        },
                        success: function(result) {
                            //alert(result);
                            var title = "";
                            if (result === 'UPDATED') {
                                $("#dialog-message").attr("title", "Referencia Actualizada");
                                title = "Referencia Actualizada";
                                var content = "<p><span class='ui-icon ui-icon-circle-minus' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La referencia está actualizada, no se requiere actualizar.</p>";
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Ok: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (result === 'OK') {
                                title = "Descarga Exitosa";
                                $("#dialog-message").attr("title", "Descarga Exitosa");
                                var content = "<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La descarga ha sido completada de forma exitosa.</p>";
                                $("#dialog-message").html(content);
                                /*jQuery.noticeAdd({text: 'La descarga se realizo exitosamente',stay: true,type: 'success'});*/
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Ok: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (result === 'ERROR') {
                                title = "Error de Descarga";
                                $("#dialog-message").attr("title", "Error de Descarga");
                                var content = "<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "Ocurrio un error al realizar la descarga.</p>";
                                $("#dialog-message").html(content);
                                /*
                                 jQuery.noticeAdd({
                                 text: 'Ocurrio un error al realizar la descarga',
                                 stay: true,
                                 type: 'error'
                                 });*/
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Ok: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            }
                            //$("#dialog-message").attr("title", title);
                        },
                        error: function() {
                            jQuery.noticeAdd({
                                text: 'Ocurrio un error al procesar la petición',
                                stay: true,
                                type: 'error'
                            });
                        },
                        complete: function(data) {
                            setInterval(function() {
                                jQuery.noticeRemove($('.notice-item-wrapper'), 400);
                            }, 5000);

                        }
                    });
                });
            });

        </script>
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
                <div id="title">&nbsp;Versión Adminstrativa</div>
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <div id="page_title">Fuentes de la aplicación</div>
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
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            <div id="dialog-message">
            </div>
        </div>
    </body>
</html>