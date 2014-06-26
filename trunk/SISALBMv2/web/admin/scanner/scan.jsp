<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Escaneo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
    </head>
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
                <div id="title">&nbsp;Nuevo Escaneo</div>
                <div id="workarea">
                    <div id="menu">
                        <nav>
                            <ul>
                                <li><a href="../../AppIndex.html">AppIndex</a></li>
                                <li>
                                    <a href="#">Configuración</a>
                                    <ul>
                                        <li><a href="../configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                        <!--<li><a href="../configuration.controller?action=view&tipo=2">Listas de Software</a></li>-->
                                    </ul>
                                </li>
                                <li><a href="#">Vulnerabilidades</a>
                                    <ul>
                                        <li><a href="../vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                        <li><a href="../vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                        <li><a href="../vulnerability.controller?action=view&tipo=3">Software Soportado</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#">Escaneo</a>
                                    <ul>
                                        <li><a href="../scanner/scan.jsp">Nuevo Escaneo</a></li>
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
                            <p>
                               Contenido 
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>