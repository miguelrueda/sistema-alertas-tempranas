<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vulnerabilidades Más Recientes</title>
        <title>Admin Index</title>
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <!--
        <script type="text/css" src="../resources/js/jquery-2.1.1.js"></script>
        <script type="text/css" src="../resources/js/jquery-ui-1.10.4.custom.js"></script>
        -->
        <!--
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        -->
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>        
        <script>
            $(document).ready(function() {
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
                        <br />
                        <div id="page_title">Vulnerabilidades Más Recientes</div>
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Afecta a:</th>
                                            <th>Fecha de Publicación</th>
                                            <!--<th>Calificación**</th>-->
                                            <th>Gravedad</th>
                                            <th>Opciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="vuln" items="${cveList}">
                                            <fmt:formatDate value="${vuln.published}"  var="parsedDate" dateStyle="long"/>
                                            <tr style="text-align: center">
                                                <td>${vuln.name}</td>
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
                                                <td>${parsedDate}</td>
                                                <!--<td>$ {vuln.CVSS.score}</td> -->
                                                <c:choose>
                                                    <c:when test="${vuln.severity eq 'High'}">
                                                        <td style="text-align:center">Alta</td>
                                                    </c:when>
                                                    <c:when test="${vuln.severity eq 'Medium'}">
                                                        <td style="text-align:center">Media</td>
                                                    </c:when>
                                                    <c:when test="${vuln.severity eq 'Low'}">
                                                        <td  style="text-align:center">Baja</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td  style="text-align:center">${vuln.severity}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>
                                                    <a href="vulnerabilities/vulnDetail.jsp?action=view&tipo=1&name=${vuln.name}" class="view">
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
                                            <td><a href="vulnerability.controller?action=view&tipo=1&page=${currentPage - 1}" class="page">Anterior</a></td>
                                        </c:if>
                                        <c:forEach begin="1" end="${noOfPages}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage eq i}">
                                                    <td class="page active">${i}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><a href="vulnerability.controller?action=view&tipo=1&page=${i}" class="page">${i}</a></td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt noOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=1&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div>
                            <div id="dialogdiv" title="Detalle de la Vulnerabilidad" style=" display: none">
                                <iframe id="thedialog" width="750" height="700"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
