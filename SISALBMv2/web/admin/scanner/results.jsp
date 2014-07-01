<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados</title>
        <link href="resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="title">&nbsp;Versión Adminstrativa</div>
                <div id="workarea">
                    <nav>
                        <ul>
                            <li>
                                <a href="#">Configuración</a>
                                <ul>
                                    <li><a href="/sisalbm/admin/configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Vulnerabilidades</a>
                                <ul>
                                    <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                    <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                    <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=3">Software Soportado</a></li>
                                </ul>
                            </li>
                            <li>
                                <a href="#">Escaneo</a>
                                <ul>
                                    <li><a href="/sisalbm/admin/scanner/scan.jsp">Nuevo Escaneo</a></li>
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
                    <div id="content_wrap">
                        <div id="page_title">Encontre: ${noOfResults} posibles amenazas.</div>
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th>Vulnerabilidad</th>
                                            <th>Software Afectado</th>
                                            <th>Criticidad</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="res" items="${resultados}">
                                            <tr>
                                                <td>${res.vulnerabilidad.name}</td>
                                                <td>${res.sw.nombre}</td>
                                                <td>${res.vulnerabilidad.severity}</td>
                                            </tr>
                                            <tr>
                                                <td colspan="3">${res.vulnerabilidad.description}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
    </body>
</html>