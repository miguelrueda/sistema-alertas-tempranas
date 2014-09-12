<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Grupos de Software</title>
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
                <!--<div id="title">&nbsp;Versión Adminstrativa</div>-->
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Grupos de Software</div>
                        <br />
                        <div class="searchdiv">
                            <form class="searchform">
                                <span class="espaciado" style="padding-left: 440px"></span>
                                <input id="searchkey" class="searchinput right" type="text" placeholder="Nombre del Grupo" />
                                <input id="searchbutton" class="searchbutton" type="button" value="Buscar" />
                            </form>
                        </div><!--search div-->
                        <br /><br /><br />
                        <div id="resultsdiv">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th>Nombre del Grupo</th>
                                            <th colspan="2">Categoría</th>
                                        </tr>
                                    </thead>
                                    <tbody id="resultbody"></tbody>
                                </table>   
                                <br />
                                <input id="closesearch" class="" type="button" value="Terminar Búsqueda" />
                                <br />
                            </div><!-- data grid -->
                        </div><!--results div-->
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" id="tablestyle">
                                    <thead>
                                    <th>Nombre</th>
                                    <th>Categoría</th>
                                    <th colspan='2'>Opciones</th>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="grupo" items="${listaGrupos}">
                                            <tr>
                                                <td style="width: 300px">${grupo.nombre}</td>
                                                <td style="width: 300px">${grupo.categoria}</td>
                                                <td>
                                                    <a href="configuration/detalleGrupo.jsp?action=view&tipo=grupo&id=${grupo.idGrupo}" class="view">
                                                        <img src="../resources/images/search.png" alt="magni" id="tableicon" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="id=${grupo.idGrupo}&nombre=${grupo.nombre}" class="del">
                                                        <img src="../resources/images/trash.png" alt="id=${grupo.idGrupo}" id="tableicon" class="delgrp" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!--PAGINATION-->
                            <div class="pagination">
                                <table style="width: 100%; text-align: center">
                                    <tr>
                                        <c:if test="${currentPage != 1}">
                                            <td><a href="configuration.controller?action=view&tipo=2&page=${currentPage - 1}" class="page">Anterior</a></td>
                                        </c:if>
                                        <c:forEach begin="1" end="${grnoOfPages}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage eq i}">
                                                    <td class="page active">${i}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><a href="configuration.controller?action=view&tipo=2&page=${i}" class="page">${i}</a></td>
                                                    </c:otherwise>


                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${currentPage lt grnoOfPages}">
                                            <td><a href="configuration.controller?action=view&tipo=2&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div>
                            <!--PAGINATION-->
                            <!--Dialogo con detalle-->
                            <div id="dialogdiv" title="Detalle del Grupo" style="display: none">
                                <iframe id="dialog" width="750" height="700"></iframe>
                            </div>
                            <!--Dialogo-->
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog-message"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <script>
            $(document).ready(function() {
                $(".view").click(function() {
                    $("#dialog").attr('src', $(this).attr("href"));
                    $("#dialogdiv").dialog({
                        width: 800,
                        height: 800,
                        modal: true,
                        resizable: false,
                        draggable: false,
                        open: function() {
                            $(".ui-widget-overlay").addClass('custom-overlay');
                        },
                        close: function() {
                            $("#dialog").attr("src", "about:blank");
                        }
                    });
                    return false;
                });
                /**
                 * Código para realizar la búsqueda de grupos
                 */
                $("#resultsdiv").hide();
                $("#searchkey").val("");
                $("#searchbutton").on("click", function() {
                    var val = $("#searchkey").val();
                    $.ajax({
                        url: '/sisalbm/admin/configuration.controller?action=searchGroup',
                        type: 'GET',
                        data: "key=" + val,
                        success: function(result) {
                            $("#content").hide();
                            $("#resultsdiv").show();
                            if (result === 'NOT_FOUND') {
                                var notResult = "<tr><td colspan='4' style='text-align:center'>No se encontraron resultados para el criterio: " + val + "</td></tr>";
                                $("#resultbody").html(notResult);
                                $("#dialog-message").attr("title", "Grupo No Encontrado");
                                var content = "<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "No se encontro información del grupo solicitado.</p>";
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else {
                                $("#resultbody").html(result);
                            }
                        }, error: function() {
                            $("#dialog-message").attr("title", "Petición Incompleta");
                            var content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "Ocurrio un error al realizar la petición al servidor. Intentelo nuevamente.</p>";
                            $("#dialog-message").html(content);
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
                $("#closesearch").on("click", function() {
                    $("#content").show();
                    $("#resultsdiv").hide();
                    $("#searchkey").val("");
                });
                /**
                 * Código para eliminar grupo
                 */
                $(".del").on("click", function() {
                    var id_name = $(this).attr("href");
                    var res = id_name.split("&");
                    var idsplit = res[0].split("=");
                    var namesplit = res[1].split("=");
                    var content = "<p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 50px 0;'></span>¿Desea eliminar el grupo:<br /> '" + namesplit[1] + "'?</p>";
                    $("#dialog-message").attr("title", "Confirmar Eliminación");
                    $("#dialog-message").html(content);
                    $("#dialog-message").dialog({
                        modal: true,
                        buttons: {
                            Eliminar: function() {
                                $(this).dialog("close");
                                $.ajax({
                                    url: '/sisalbm/admin/configuration.controller?action=deleteGroup',
                                    type: 'POST',
                                    data: 'gid=' + idsplit[1],
                                    success: function(result) {
                                        var content = "";
                                        if (result === 'OK') {
                                            $("#dialog-message").attr("title", "Grupo Eliminado");
                                            content = "<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "El grupo '" + namesplit[1] + "' ha sido eliminado exitosamente.</p>";
                                            $("#dialog-message").html(content);
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
                                            $("#dialog-message").attr("title", "Error de Eliminación");
                                            content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "Ocurrio un error al eliminar el grupo. Por favor, intentarlo nuevamente.</p>";
                                            $("#dialog-message").html(content);
                                            $("#dialog-message").dialog({
                                                modal: true,
                                                buttons: {
                                                    Aceptar: function() {
                                                        $(this).dialog("close");
                                                    }
                                                }
                                            });
                                        } else {
                                            $("#dialog-message").attr("title", "Error del Servidor");
                                            content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                    "Ocurrio un error inesperado! Por favor, intentarlo nuevamente.</p>";
                                            $("#dialog-message").html(content);
                                            $("#dialog-message").dialog({
                                                modal: true,
                                                buttons: {
                                                    Aceptar: function() {
                                                        $(this).dialog("close");
                                                        location.reload();
                                                    }
                                                }
                                            });
                                        }
                                    }, error: function() {
                                        $("#dialog-message").attr("title", "Petición Incompleta");
                                        var content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                                "Ocurrio un error al realizar la petición al servidor. Intentelo nuevamente.</p>";
                                        $("#dialog-message").html(content);
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
                                $(this).dialog("close");
                            }
                        }
                    });
                    return false;
                });
            });
        </script>        
    </body>
</html>