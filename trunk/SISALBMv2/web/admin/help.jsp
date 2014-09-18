<!DOCTYPE html>
<html>
    <head>
        <title>Admin Index</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <style type="text/css">
            #pdf {
                width: 80%;
                height: 850px;
                margin: 1em auto;
                border: 5px solid #797ca3; 
            }
            #pdf object {
                display: block;
            }
        </style>
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
                    <%@include file="incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <div id="content">
                            <br />
                            <br />
                            <div id="pdf" style=" z-index: 100">
                                <object data="../resources/pdf/manual.pdf"
                                        type="application/pdf" width="100%" height="100%"></object>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

 <!--
                    <div id="cssmenu">
                        <ul>
                            <li><a href="../AppIndex.html"><span>AppIndex</span></a></li>
                            <li class="has-sub"><a href="#"><span>Configuración</span></a>
                                <ul style="z-index: 999">
                                    <li class="has-sub"><a href="#"><span>Fuentes</span></a>
                                        <ul>
                                            <li><a href="configuration.controller?action=view&tipo=1"><span>Administrar</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="has-sub"><a href="#"><span>Grupos</span></a>
                                        <ul>
                                            <li><a href="configuration.controller?action=view&tipo=2"><span>Administrar</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="has-sub"><a href="#"><span>Software</span></a>
                                        <ul>
                                            <li><a href="#"><span>Agregar Software</span></a></li>
                                            <li><a href="vulnerability.controller?action=view&tipo=3"><span>Software Registrado</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="last"><a href="/sisalbm/JobScheduleServlet"><span>Tareas Programadas</span></a></li>
                                </ul>
                            </li>
                            <li><a href="#"><span>Vulnerabilidades</span></a>
                                <ul>
                                    <li><a href="vulnerability.controller?action=view&tipo=1"><span>Más Recientes</span></a></li>
                                    <li><a href="vulnerability.controller?action=view&tipo=2"><span>Archivo</span></a></li>
                                </ul>
                            </li>
                            <li><a href="scanner/scan.jsp"><span>Escaneo</span></a></li>
                            <li><a href="#"><span>Ayuda</span></a></li>
                        </ul>
                    </div>-->
                    <!--
                    <div id="menu">
                        <nav>
                            <ul style=" z-index: 999">
                                <li><a href="../AppIndex.html">AppIndex</a></li>
                                <li>
                                    <a href="Index.html">Configuración</a>
                                    <ul>
                                        <li><a href="configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                        <li><a href="configuration.controller?action=view&tipo=2">Administrar Grupos</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">Vulnerabilidades</a>
                                    <ul>
                                        <li><a href="vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                        <li><a href="vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                        <li><a href="vulnerability.controller?action=view&tipo=3">Software Registrado</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="scanner/scan.jsp">Escaneo</a>
                                </li>
                                <li>
                                    <a href="../help.jsp">Ayuda</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    -->