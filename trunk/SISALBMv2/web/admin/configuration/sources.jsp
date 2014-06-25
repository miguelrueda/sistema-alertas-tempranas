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
            $(document).ready(function(){
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
                $(".dwnldBtn").click(function(e){
                    var url = $(this).attr("alt");
                    //$.growl.notice({title: "Info", message: "Descargando el archivo: " + url});
                    jQuery.noticeAdd({
                        text: "Descargando el archivo: " + url,
                        stay: false,
                    });
                    //alert("/sisalbm/admin/configuration.controller?action=download&url=" + url);
                    $.ajax({
                        type: 'get',
                        url: '/sisalbm/admin/configuration.controller?action=download',
                        data: 'url=' + url,
                        success: function(result) {
                            alert(result);
                            if (result === 'OK') {
                                jQuery.noticeAdd({
                                    text: 'La descarga se realizo exitosamente',
                                    stay: true,
                                    type: 'success'
                                });
                            } else if(result === 'ERROR') {
                                jQuery.noticeAdd({
                                    text: 'Ocurrio un error al realizar la descarga',
                                    stay: true,
                                    type: 'error'
                                });
                            }
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
                                            <th>Fecha de Actualización</th>
                                            <th colspan="2">Opciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="src" items="${fuentes}">
                                            <fmt:formatDate value="${src.fechaActualizacion}"  var="parsedDate" dateStyle="long"/>
                                            <tr>
                                                <td>${src.nombre}</td>
                                                <td id="dwnldurl">${src.url}</td>
                                                <td>${parsedDate}</td>
                                                <td>
                                                    <a href="configuration/editSource.jsp?id=${src.id}">
                                                        <img src="../resources/images/edit.png" alt="editar" id="tableicon" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="#">
                                                        <img src="../resources/images/download.png" alt="${src.url}" id="tableicon" class="dwnldBtn" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div id="dialogdiv" title="Editar Fuente" style=" display: none">
                                <iframe id="thedialog" width="360" height="330"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>