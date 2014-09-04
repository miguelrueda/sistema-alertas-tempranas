<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Archivo de Vulnerabilidades</title>
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../resources/css/menu.css" type="text/css" rel="stylesheet" />
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
                        <br />
                        <div id="page_title">Archivo de Vulnerabilidades</div>
                        <br />
                        <div class="searchdiv">
                            <form class="searchform">
                                <span style="padding-left: 440px"></span>
                                <input id="searchkey" class="searchinput" type="text" placeholder="CVE-2014-XXXX" />
                                <input id="searchbutton" class="searchbutton" type="button" value="Buscar" />
                            </form>
                        </div>
                        <br />
                        <br />
                        <br />
                        <div id="resultsdiv">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th style="width: 130px">Nombre</th>
                                            <th>Fecha de Publicación</th>
                                            <th>Ultima Modificación</th>
                                            <th>Calificación</th>
                                            <th>Gravedad</th>
                                        </tr>
                                    </thead>
                                    <tbody id="resultbody">
                                    </tbody>
                                </table>
                                <br />
                                <input id="closesearch" class="" type="button" value="Terminar Búsqueda" />
                                <br />
                            </div>
                        </div>
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle" >
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Afecta a</th>
                                            <th>Fecha de Publicación</th>
                                            <!--<th>Calificación**</th>-->
                                            <th>Gravedad</th>
                                            <th>Opciones</th>
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
                                                                        ${vulnsw.name}
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
                                        <c:forEach begin="${currentPage}" end="${currentPage + 9}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage lt arnoOfPages}">
                                                    <c:choose>
                                                        <c:when test="${currentPage eq i}">
                                                            <td class="page active">${i}</td>
                                                        </c:when>
                                                        <c:when test="${currentpage lt arnoOfPages}">
                                                            <td><a href="vulnerability.controller?action=view&tipo=2&page=${i}" class="page">${i}</a></td>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt arnoOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=2&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                        </c:if>
                                        <c:if test="${currentPage ne arnoOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=2&page=${arnoOfPages}" class="page">Fin</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div>
                            <br />
                            <p style="text-align: center">
                                Existen: ${total} vulnerabilidades.
                            </p>
                            <div id="dialogdiv" title="Detalle de la Vulnerabilidad" style=" display: none; z-index: 999;">
                                <iframe id="thedialog" width="750" height="700"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog-message">
            </div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            $(document).ready(function() {
                $("#resultsdiv").hide();
                $("#searchkey").val("");
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
                        },
                        close: function() {
                            $("#thedialog").attr("src", "about:blank");
                        }
                    });
                    return false;
                });
                /*
                 $("#searchkey").autocomplete({ source: function(request, response) {
                 $.ajax({ url: '/sisalbm/admin/vulnerability.controller?action=search', type: 'GET', data: { term: request.term },
                 dataType: "json", success: function(data) { response(data); } }); }});*/
                $("#searchbutton").on("click", function() {
                    var val = $("#searchkey").val();
                    $.ajax({
                        url: '/sisalbm/admin/vulnerability.controller?action=search&type=vulnsearch',
                        type: 'GET',
                        data: "key=" + val,
                        success: function(result) {
                            $("#content").hide();
                            $("#resultsdiv").show();
                            if (result === 'notfound') {
                                var notResult = "<tr><td colspan='5' style='text-align:center'>No se encontraron resultados para el criterio: " + val + "</td></tr>";
                                $("#resultbody").html(notResult);
                                $("#dialog-message").attr("title", "Vulnerabilidad No Encontrada");
                                var content = "<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "No se encontro la vulnerabilidad.</p>";
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
            });
        </script>
    </body>
</html>
