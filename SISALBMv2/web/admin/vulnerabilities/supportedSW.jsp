<%-- 
JSP que se encarga de mostrar  la información del software que esta soportado por la aplicación
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Software Soportado</title>
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
            </div><!-- page header-->
            <div id="page_content">
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Software Registrado</div>
                        <br />
                        <!-- formulario de busqueda -->
                        <div class="searchdiv">
                            <form class="searchform">
                                <!--<button type="submit" id="addButton" class="addbutton">Agregar Software</button>
                                <a href="vulnerabilities/addSW.jsp" id="add" class="addbutton">Agregar Software</a>-->
                                <span class="espaciado" style="padding-left: 440px;"></span>
                                <input id="searchkey" class="searchinput right" type="text" placeholder="Nombre del SW" />
                                <input id="searchbutton" class="searchbutton" type="button" value="Buscar" />
                            </form>
                        </div><!-- busqueda --> <br /> <br /> <br />
                        <!-- div para mostrar los resultados -->
                        <div id="resultsdiv">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th>Fabricante</th>
                                            <th>Software</th>
                                            <th>Versión</th>
                                            <th>Opciones</th>
                                        </tr>
                                    </thead>
                                    <tbody id="resultbody">
                                    </tbody>
                                </table>
                                <br />
                                <input id="closesearch" class="" type="button" value="Terminar Búsqueda" />
                                <br />
                            </div>
                        </div><!-- resultados -->
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" id="tablestyle">
                                    <thead>
                                    <th>Fabricante</th>
                                    <th>Software</th>
                                    <th>Version</th>
                                    <th>Fin de Vida</th>
                                    <th>Opciones</th>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="supSW" items="${swList}">
                                            <tr>
                                                <td style="width: 150px">${supSW.fabricante}</td>
                                                <td style="width: 240px">${supSW.nombre}</td>
                                                <c:choose>
                                                    <c:when test="${supSW.version eq '-1'}">
                                                        <td style="width: 80px; text-align: center">ND</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td style="width: 80px; text-align: center">${supSW.version}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${supSW.endoflife eq '1'}">
                                                        <td style="width: 80px; text-align: center">Si</td>
                                                    </c:when>
                                                    <c:when test="${supSW.endoflife eq '0'}">
                                                        <td style="width: 80px; text-align: center">No</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td style="width: 80px; text-align: center">ND</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <!-- codigo para la edición del software
                                        <td><a href="configuration/editarSW.jsp?id=$ {supSW.idSoftware}">
                                                <img src="../resources/images/edit.png" alt="editar" id="tableicon" />
                                            </a></td>-->
                                                <td>
                                                    <a href="id=${supSW.idSoftware}&nombre=${supSW.nombre}" class="view">
                                                        <img src="../resources/images/trash.png" alt="id=${supSW.idSoftware}" id="tableicon" class="delbtn" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="pagination">
                                <table style="width: 100%; text-align: center">
                                    <tr>
                                        <c:if test="${currentPage != 1}">
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=1" class="page">Inicio</a></td>
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${currentPage - 1}" class="page">Anterior</a></td>
                                        </c:if>
                                        <c:forEach begin="${currentPage}" end="${currentPage + 9}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage lt swnoOfPages}">
                                                    <c:choose>
                                                        <c:when test="${currentPage eq i}">
                                                            <td class="page active">${i}</td>
                                                        </c:when>
                                                        <c:when test="${currentpage lt swnoOfPages}">
                                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${i}" class="page">${i}</a></td>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt swnoOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                        </c:if>
                                        <c:if test="${currentPage ne swnoOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${swnoOfPages}" class="page">Fin</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div><!-- paginador -->
                        </div><!-- content -->
                    </div>
                </div>
            </div><!--page content-->
            <div id="dialog-message"></div>
            <div id="dialog-eliminar"></div>
        </div><!--page container -->
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="../resources/js/jquery.notice.js"></script>
        <script>
            /**
             * Función jQuery que se encarga del manejjo de la funcionalidad de la pagina
             */
            $(document).ready(function() {
                var dialog = $("#dialog-form");
                $("#dialog-form").hide();
                                //$("#add").button({icons: {primary: 'ui-icon-circle-plus'}}).click(function(e) {
                    /* Dialogo
                     $("#dialog-form").dialog({ resizable: false, height: 440,width: 500, modal: true,
                     buttons: { 'Agregar': addSoftware, Cancelar: function() { $(this).dialog("close"); } } }); DIALOGO*/
                //});
                /**
                 *  Código para la busqueda de SW
                 */
                $("#resultsdiv").hide();
                $("#searchkey").val("");
                /**
                 * Función que implementa la busqueda de software
                 */
                $("#searchbutton").on("click", function() {
                    var val = $("#searchkey").val();
                    $.ajax({
                        url: '/sisalbm/admin/vulnerability.controller?action=search&type=swsearch',
                        type: 'GET',
                        data: "key=" + val,
                        beforeSend: function() {
                            //Mostrar mensaje de aviso de procesamiento
                            jQuery.noticeAdd({
                                text: "Realizando búsqueda <br /><center><img src='/sisalbm/resources/images/ajax-loader.gif' alt='Cargando...' /></center>",
                                stay: false,
                                type: 'info'
                            });
                        }, success: function(result) {
                            result = result.trim();
                            $("#content").hide();
                            $("#resultsdiv").show();
                            //Si la respuesta es nula establecer un mensaje de notificación
                            if (result === '') {
                                $("#resultbody").html("<tr><td colspan='4' style='text-align:center'>No se encontraron resultados para el criterio: " + val + "</td></tr>");
                                $("#dialog-message").attr("title", "Software No Encontrado");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "No se encontro el software.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else {
                                //Si la respuesta no es nula establecer el contenido
                                $("#resultbody").html(result);
                            }

                        }, error: function() {
                            //Mostrar mensaje cuando ocurre un error para completar la petición hacia el servidor
                            $("#dialog-message").attr("title", "Petición Incompleta");
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
                /**
                 * Función que termina la busqueda
                 */
                $("#closesearch").on("click", function() {
                    $("#content").show();
                    $("#resultsdiv").hide();
                    $("#searchkey").val("");
                });
                /**
                 * Código para eliminar un SW
                 */
                $(".view").on("click", function() {
                    //Obtener la referencia del software a eliminar
                    var id_name = $(this).attr("href");
                    var res = id_name.split("&");
                    var res0 = res[0].split("=");
                    var swid = res0[1];
                    var res1 = res[1].split("=");
                    //Mostrar una ventana de confirmacin
                    $("#dialog-message").attr("title", "Confirmar Eliminación");
                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 50px 0;'></span>" +
                            "¿Desea eliminar el software:<br /> '" + res1[1] + "'?</p>");
                    $("#dialog-message").dialog({
                        modal: true,
                        buttons: {
                            Eliminar: function() {
                                $(this).dialog("close");
                                //Si se hace clic sobre la opción eliminar, iniciar el procesamiento ajax
                                $.ajax({
                                    url: '/sisalbm/admin/configuration.controller?action=deleteSW',
                                    type: 'POST',
                                    data: 'swid=' + swid,
                                    beforeSend: function() {
                                        jQuery.noticeAdd({
                                            text: "Procesando la petición:" + //+ tk[1] + + 
                                                    "<br /><center><img src='../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                                            stay: false,
                                            type: 'info'
                                        });
                                    }, success: function(result) {
                                        var content = "";
                                        result = result.trim();
                                        if (result === 'OK') {
                                            //Si el resultado es correcto mostrar esta alerta
                                            $("#dialog-message").attr("title", "Software Eliminado");
                                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "El software '" + res1[1] + "' ha sido eliminado exitosamente.</p>");
                                            $("#dialog-message").dialog({
                                                modal: true,
                                                buttons: {
                                                    Aceptar: function() {
                                                        $(this).dialog("close");
                                                        location.reload();
                                                    }
                                                }
                                            });
                                        } else if (result === 'ERROR') {
                                            //Si ocurre un error mostrar esta alerta
                                            $("#dialog-message").attr("title", "Software No Eliminado");
                                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "Ocurrio un error al eliminar el software. Por favor, intentarlo nuevamente.</p>");
                                            $("#dialog-message").dialog({
                                                modal: true,
                                                buttons: {
                                                    Aceptar: function() {
                                                        $(this).dialog("close");
                                                    }
                                                }
                                            });
                                        } else {
                                            //En otro caso mostrar est aalerta
                                            $("#dialog-message").attr("title", "Software No Eliminado");
                                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "Ocurrio un error inesperado! Por favor, intentarlo nuevamente.</p>");
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
                                        //Esta alerta se muestra cuando ocurre un error para terminar la petición hacia el servidor
                                        $("#dialog-message").attr("title", "Petición Incompleta");
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
                            },
                            Cancelar: function() {
                                //Si la opción es cancelar solamente cerrar el dialogo
                                $(this).dialog("close");
                            }
                        }
                    });
                    return false;
                });
                /**
                 * Fin de codigo para eliminar
                 */
            });
            function eliminarSoftware(id, nombre) {
                $("#dialog-eliminar").attr("title", "Confirmar eliminación");;
                $("#dialog-eliminar").html("<p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 50px 0;'></span>" +
                            "¿Desea eliminar el software:<br /> '" + nombre + "'?</p>");
                $("#dialog-eliminar").dialog({
                    modal: true,
                    buttons: {
                        Eliminar: function() {
                            $(this).dialog("close");
                            $.ajax({
                                url: '/sisalbm/admin/configuration.controller?action=deleteSW',
                                    type: 'POST',
                                    data: 'swid=' + id,
                                    beforeSend: function() {
                                        jQuery.noticeAdd({
                                            text: "Eliminando software:" + 
                                                    "<br /><center><img src='../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                                            stay: false,
                                            type: 'info'
                                        });
                                    }, success: function(result) {
                                        result = result.trim();
                                        var content = "";
                                        if (result === 'OK') {
                                            //Si el resultado es correcto mostrar esta alerta
                                            $("#dialog-message").attr("title", "Software Eliminado");
                                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "El software '" + nombre + "' ha sido eliminado exitosamente.</p>");
                                            $("#dialog-message").dialog({
                                                modal: true,
                                                buttons: {
                                                    Aceptar: function() {
                                                        $(this).dialog("close");
                                                        location.reload();
                                                    }
                                                }
                                            });
                                        } else if (result === 'ERROR') {
                                            //Si ocurre un error mostrar esta alerta
                                            $("#dialog-message").attr("title", "Software No Eliminado");
                                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "Ocurrio un error al eliminar el software. Por favor, intentarlo nuevamente.</p>");
                                            $("#dialog-message").dialog({
                                                modal: true,
                                                buttons: {
                                                    Aceptar: function() {
                                                        $(this).dialog("close");
                                                    }
                                                }
                                            });
                                        } else {
                                            //En otro caso mostrar est aalerta
                                            $("#dialog-message").attr("title", "Software No Eliminado");
                                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "Ocurrio un error inesperado! Por favor, intentarlo nuevamente.</p>");
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
                                        //Esta alerta se muestra cuando ocurre un error para terminar la petición hacia el servidor
                                        $("#dialog-message").attr("title", "Petición Incompleta");
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
                        }, Cancelar: function() {
                            $(this).dialog("close");
                        }
                    }
                });
            }
        </script>

    </body>
</html>