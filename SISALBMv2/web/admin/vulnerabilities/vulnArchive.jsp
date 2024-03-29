<%-- 
JSP que se encarga de mostrar  la información del archivo de vulnerabilidades que se va almacenando
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html>
    <head>
        <title>Archivo de Vulnerabilidades</title>
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
                        <div id="page_title">Archivo de Vulnerabilidades</div>
                        <center><h5>Vulnerabilidades disponibles desde 1 de Enero de 2013 hasta la fecha actual.</h5></center>
                            <c:choose>
                                <c:when test="${arcveList.isEmpty() eq true}">
                                <center>
                                    <h3>No se encontró el contenido o no se encuentra disponible</h3>
                                </center>
                            </c:when>
                            <c:when test="${arcveList.isEmpty() eq false}">
                                <div class="searchdiv">
                                    <form class="searchform">
                                        <span style="padding-left: 440px"></span>
                                        <input id="searchkey" class="searchinput" type="text" placeholder="CVE-2014-XXXX" />
                                        <input id="searchbutton" class="searchbutton" type="button" value="Buscar" />
                                    </form>
                                </div><!-- div de busqueda --> <br /><br /><br />
                                <div id="resultsdiv">
                                    <div class="datagrid">
                                        <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                            <thead>
                                                <tr>
                                                    <th style="width: 130px">Identificador</th>
                                                    <th>Fecha de Publicación</th>
                                                    <th>Ultima Modificación</th>
                                                    <th>Calificación</th>>
                                                    <th>Gravedad</th>
                                                </tr>
                                            </thead>
                                            <tbody id="resultbody"></tbody>
                                        </table>
                                        <br />
                                        <input id="closesearch" class="" type="button" value="Terminar Búsqueda" />
                                        <br />
                                    </div>
                                </div><!-- div de resultados -->
                                <div id="content">
                                    <div class="datagrid">
                                        <table border="1" cellpadding="5" cellspacing="5" id="tablestyle" >
                                            <thead>
                                                <tr>
                                                    <th>Identificador</th>
                                                    <th>Posible Software Afectado</th>
                                                    <th>Fecha de Publicación</th>
                                                    <th>Gravedad</th>
                                                    <th>Detalles</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="vuln" items="${arcveList}">
                                                    <fmt:formatDate value="${vuln.published}"  var="parsedDate" dateStyle="long"/>
                                                    <tr style="text-align: center">
                                                        <td>${vuln.name}</td>
                                                        <c:if test="${fn:length(vuln.vuln_soft) > 0}">
                                                            <td>
                                                                <table style="border: 0; max-width: 150px">
                                                                    <c:forEach var="vulnsw" items="${vuln.vuln_soft}">
                                                                        <tr>
                                                                            <td>
                                                                                ${vulnsw.vendor} ${vulnsw.name}
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </table>
                                                            </td>
                                                        </c:if>
                                                        <c:if test="${fn:length(vuln.vuln_soft) eq 0}">
                                                            <td style="text-align: left">
                                                                No definido
                                                            </td>
                                                        </c:if>
                                                        <td>${parsedDate}</td>
                                                        <!--<td>$ {vuln.CVSS.score}</td>-->
                                                        <c:choose>
                                                            <c:when test="${vuln.severity eq 'High'}">
                                                                <td>Alta</td>
                                                            </c:when>
                                                            <c:when test="${vuln.severity eq 'Medium'}">
                                                                <td>Media</td>
                                                            </c:when>
                                                            <c:when test="${vuln.severity eq 'Low'}">
                                                                <td>Baja</td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>${vuln.severity}</td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td>
                                                            <a href="vulnerabilities/vulnDetail.jsp?tipo=2&name=${vuln.name}" class="view">
                                                                <img src="../resources/images/search.png" alt="magni" id="tableicon" />
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
                                                    <td><a href="vulnerability.controller?action=view&tipo=2&page=1" class="page">Inicio</a></td>
                                                    <td><a href="vulnerability.controller?action=view&tipo=2&page=${currentPage - 1}" class="page">Anterior</a></td>
                                                </c:if>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${currentPage eq 1}">
                                                            <select name="cambiador" id="cambiador" onchange="cambiarPagina()" class="page active" style="width: 120px">
                                                                <option selected="true" value="${currentPage}">Página ${currentPage}</option>
                                                                <c:forEach var="i" begin="1" end="4">
                                                                    <option value="${currentPage + i}">Página ${currentPage + i}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </c:when>
                                                        <c:when test="${(currentPage gt 1) and (currentPage lt arnoOfPages)}">
                                                            <select name="cambiador" id="cambiador" onchange="cambiarPagina()" class="page active" style="width: 120px">
                                                                <c:forEach var="i" begin="1" end="4">
                                                                    <c:set var="j" value="${4 - i + 1}" scope="page"></c:set>
                                                                    <c:if test="${currentPage - j > 0}">
                                                                        <option value="${currentPage - j}">Página ${currentPage - j}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <option selected="true" value="${currentPage}">Página ${currentPage}</option>
                                                                <c:forEach var="i" begin="1" end="4">
                                                                    <c:if test="${currentPage + i <= arnoOfPages}">
                                                                        <option value="${currentPage + i}">Página ${currentPage + i}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </c:when>
                                                        <c:when test="${currentPage eq arnoOfPages}">
                                                            <select name="cambiador" id="cambiador" onchange="cambiarPagina()" class="page active" style="width: 120px">
                                                                <c:forEach var="i" begin="1" end="4">
                                                                    <c:set var="j" value="${4 - i + 1}" scope="page"></c:set>
                                                                    <option value="${currentPage - j}">Página ${currentPage - j}</option>
                                                                </c:forEach>
                                                                <option selected="true" value="${currentPage}">Página ${currentPage}</option>
                                                            </select>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <c:if test="${currentPage lt arnoOfPages}">
                                                    <td><a href="vulnerability.controller?action=view&tipo=2&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                                </c:if>
                                                <c:if test="${currentPage ne arnoOfPages}">
                                                    <td><a href="vulnerability.controller?action=view&tipo=2&page=${arnoOfPages}" class="page">Fin</a></td>
                                                </c:if>
                                            </tr>
                                        </table>
                                    </div><!-- paginador -->
                                    <br />
                                    <p style="text-align: center">
                                        Existen: ${total} vulnerabilidades en ${arnoOfPages} páginas.
                                    </p>
                                    <div id="dialogdiv" title="Detalle de la Vulnerabilidad" style=" display: none; z-index: 999;">
                                        <iframe id="thedialog" width="750" height="700"></iframe>
                                    </div>
                                </div><!-- content -->
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div><!-- page content -->
            <div id="dialog-message"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="../resources/js/jquery.notice.js"></script>
        <script>
            function cambiarPagina() {
                var valor = $("#cambiador").val();
                if (valor === null || valor === 0) {
                    window.location = "vulnerability.controller?action=view&tipo=2&page=1";
                }
                window.location = "vulnerability.controller?action=view&tipo=2&page=" + valor;
            }
        </script>
        <script>
            /**
             * Función jQuery que maneja la funcionalidad de la aplicación
             */
            $(document).ready(function() {
                $("#resultsdiv").hide();
                $("#searchkey").val("");
                /**
                 * Función que se ejecuta cuando se hace clic sobre un elemento con clase view
                 */
                $(".view").click(function() {
                    $("#thedialog").attr('src', $(this).attr("href"));
                    $("#dialogdiv").dialog({
                        width: 800,
                        height: 800,
                        modal: true,
                        resizable: false,
                        draggable: false,
                        open: function() {
                            $('.ui-widget-overlay').addClass('custom-overlay');
                        }, close: function() {
                            $("#thedialog").attr("src", "about:blank");
                        }
                    });
                    return false;
                });
                /*
                 $("#searchkey").autocomplete({ source: function(request, response) {
                 $.ajax({ url: '/sisalbm/admin/vulnerability.controller?action=search', type: 'GET', data: { term: request.term },
                 dataType: "json", success: function(data) { response(data); } }); }});*/
                /**
                 * Función que ejecuta la busqueda al realizar clic sobre el botón
                 */
                $("#searchbutton").on("click", function() {
                    var val = $("#searchkey").val();
                    $.ajax({
                        url: '/sisalbm/admin/vulnerability.controller?action=search&type=vulnsearch',
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
                            if (result === 'notfound') {
                                //Si no se encuentra la vulnerabilidad mostrar un mensaje
                                $("#resultbody").html("<tr><td colspan='5' style='text-align:center'>No se encontraron resultados para el criterio: " + val + "</td></tr>");
                                $("#dialog-message").attr("title", "Vulnerabilidad No Encontrada");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "No se encontro la vulnerabilidad.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else {
                                //En otro caso hacer un append con el resultado obtenido
                                $("#resultbody").html(result);
                            }
                        }, error: function() {
                            //Ejecutar este codigo cuando la petición no se completa por problemas del servidor
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
                        }
                    });
                });
                /**
                 * Función que se ejecuta cuando se finaliza la busqueda
                 */
                $("#closesearch").on("click", function() {
                    $("#content").show();
                    $("#resultsdiv").hide();
                    $("#searchkey").val("");
                });
            });
        </script>
    </body>
</html>