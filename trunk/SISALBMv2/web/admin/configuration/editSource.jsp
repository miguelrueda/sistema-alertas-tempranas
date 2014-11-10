<%-- 
    JSP que muestra un formulario para editar la información de la fuente de vulnerabilidades,
    en este caso se utiliza una url de las que provee el nist, es el único campo editable
--%>
<%@page import="java.util.Date"%>
<%@page import="org.banxico.ds.sisal.entities.FuenteApp"%>
<%@page import="org.banxico.ds.sisal.dao.SourcesDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Fuente</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <link href="/sisalbm/resources/css/jquery.notice.css" type="text/css" rel="stylesheet"/>
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
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <div id="content">
                            <div class="editForm">
                                <br />
                                <form id="form" name="editSrcForm" class="form" action="" >
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
            <div id="dialog-message"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="/sisalbm/resources/js/jquery.notice.js"></script>
        <script>
            $(document).ready(function() {
                $("#dialog-message").hide();
                /*
                 * Función que se ejecuta una vez que se hace clic sobre el boton
                 * de actualizar fuente
                 */
                $("#actualizar").click(function(e) {
                    var name = $("#namef").val();
                    var url = $("#urlf").val();
                    //Se obtiene una referencia al formulario serializado y al id de la fuente que se va a editar
                    var formser = $("#form").serialize();
                    var idf = $("#idf").val();
                    $.ajax({
                        type: 'get',
                        url: '/sisalbm/admin/configuration.controller?action=edit&tipo=1',
                        data: "idf=" + idf + "&" + formser,
                        beforeSend: function() {
                            //Mostrar mensaje de aviso de procesamiento
                            jQuery.noticeAdd({
                                text: "Procesando petición <br /><center><img src='/sisalbm/resources/images/ajax-loader.gif' alt='Cargando...' /></center>",
                                stay: false,
                                type: 'info'
                            });
                        }, success: function(result) {
                            result = result.trim();
                            //A partir del resultado obtenido del servidor mostrar una alerta
                            if (result === "true") {
                                $("#dialog-message").attr("title", "Edición Completa");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La Fuente ha sido actualizada de forma exitosa.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                                //Deshabilitar los campos para evitar 
                                //$("#namef").attr("disabled", true);
                                //$("#urlf").attr("disabled", true);
                                //$("#actualizar").attr("disabled", true);
                            } else if (result === "nombre") {
                                //Se muestra este mensaje cuando el nombre ingresado no es válido
                                $("#dialog-message").attr("title", "Error de entrada");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "El nombre ingresado no es válido</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else if (result === 'url') {
                                //Se muestre éste mensaje cuando la url ingresada no es válida
                                $("#dialog-message").attr("title", "Error de entrada");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "La URL ingresada no es válida.<br />La URL debe cumplir con el prefijo: ([http|https]://)</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            }
                            else {
                                //Se muestre este mensaje cuando el servidor no retorna una respuesta favorable
                                $("#dialog-message").attr("title", "Edición Incompleta");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "Ocurrio un error al realizar la actualización. Favor de intentarlo nuevamente.</p>");
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
                            //Mensaje que se muestra cuando existe un error al ejecutar la petición
                            $("#dialog-message").attr("title", "Edición Incompleta");
                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "Ocurrio un error al realizar la petición al servidor. Intentelo nuevamente.</p>");
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Aceptar: function() {
                                        $(this).dialog("close");
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