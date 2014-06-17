<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vulnerabilidades Más Recientes</title>
        <title>Admin Index</title>
        <link href="../css/general.css" type="text/css" rel="stylesheet" /> 
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="../resources/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="title">&nbsp;Versión Adminstrativa</div>
                <div id="workarea">
                    <div id="menu">
                        <nav>
                            <ul>
                                <li><a href="../AppIndex.html">AppIndex</a></li>
                                <li>
                                    <a href="Index.html">Configuración</a>
                                    <ul>
                                        <li><a href="#">Administrar Fuentes</a></li>
                                        <li><a href="#">Listas de Software</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">Vulnerabilidades</a>
                                    <ul>
                                        <li><a href="vulnerability.controller">Más Recientes</a></li>
                                        <li><a href="#">Archivo</a></li>
                                        <li><a href="#">Software Soportado</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#">Escaneo</a>
                                    <ul>
                                        <li><a href="#">Nuevo Escaneo</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#">Reportes</a>
                                    <ul>
                                        <li><a href="#">Generar Reporte</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <div id="content_wrap">
                        <div id="content">
                            <table border="1" cellpadding="5" cellspacing="5" style="max-width: 1024px; width: 100%">
                                <tr>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Severity</th>
                                </tr>
                                <c:forEach var="vuln" items="${cveList}">
                                    <tr>
                                        <td>${vuln.name}</td>
                                        <td style="width: 500px;">${vuln.description}</td>
                                        <td>${vuln.severity}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div class="pagination">
                                <table style="width: 100%; text-align: center">
                                    <tr>
                                        <c:if test="${currentPage != 1}">
                                            <td><a href="vulnerability.controller?page=${currentPage - 1}" class="page">Previous</a></td>
                                        </c:if>
                                        <c:forEach begin="1" end="${noOfPages}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage eq i}">
                                                    <td class="page active">${i}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><a href="vulnerability.controller?page=${i}" class="page">${i}</a></td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt noOfPages}">
                                            <td><a href="vulnerability.controller?page=${currentPage + 1}" class="page">Next</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
