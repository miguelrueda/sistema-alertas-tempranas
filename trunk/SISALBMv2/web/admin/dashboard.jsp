<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" />
        <style type="text/css">
            .ui-tabs .ui-tabs-nav li a {
                font-size: 9pt !important;
            }
            .ui-tabs p {
                font-size: 10pt !important;
            }
            .ui-tabs vulnsPfab {
                font-size: 6pt !important;
            }
            table.center {
                max-width: 800px; 
                width: 100%;
                margin-left: auto;
                margin-right: auto;
            }
            /*
            table, th, td {
                border: 1px solid black; 
                width: 1000px;
                font-size: 11pt !important;
            }
            .td-left {
                text-align: left;
            }
            .td-right {
                text-align: center !important;
            }*/
            .tabtitle {
                height: 40px;
                font-size: 15px;
                color: #fb9340;
                text-transform: uppercase;
                font-weight: bold;
                float: left;
                width: 100%;
                text-align: center;
            }
            .grid {
                font: normal 12px/150%;
                background: #ffffff;
                -webkit-border-radius: 3px; 
                -moz-border-radius: 3px; 
                border-radius: 3px;
            }
            .grid table td, .grid table th {
                padding: 5px 5px;
            }
            .grid table thead td {
                background: #797ca3;
                color: #ffffff;
                font-size: 13px;
                font-weight: bold;
                border: 1px solid #000000;
            }
            .grid table tbody td {
                color: #000000;
                font-size: 12px;
                font-weight: normal;
            }
            .grid table {
                border-collapse: collapse;
                text-align: left;
                border: 1px solid #000000;
            }
            .innergrid tr{
                border-collapse: collapse;
                border: 1px solid #FFF;
            }
        </style>
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="/sisalbm/resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div><!-- Encabezado de pagina -->
            <div id="page_content">
                <div id="workarea">
                    <%@include file="incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <div id="content">
                            <div id="tabs">
                                <ul>
                                    <li><a href="#tabs-1">Ultimas Vulnerabilidades</a></li>
                                    <li><a href="#tabs-2">Vulnerabilidades por Fabricante</a></li>
                                    <li><a href="#tabs-3">Estadística 3</a></li>
                                </ul>
                                <div id="tabs-1">
                                    <p class="tabtitle">
                                        Vulnerabilidades Encontradas
                                    </p>
                                    <center><h5>Se incluyen vulnerabilidades de un periodo no mayor a 8 días.</h5></center>
                                    <div id="recientes" class="grid"></div>
                                </div>
                                <div id="tabs-2">
                                    <p class="tabtitle">
                                        Vulnerabilidades por Fabricante
                                    </p>
                                    <div id="vulnsPfab"></div>
                                </div>
                                <div id="tabs-3">
                                    <p>Estadisticas por gravedad o tipo de ataque o vulnerabilidades más graves</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Frame para desplegar los detalles -->
                <div id="dialogdiv" title="Detalle de la Vulnerabilidad" style="display: none">
                    <iframe id="thedialog" width="750" height="700"></iframe>
                </div>
            </div><!-- Contenido de la pagina -->
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js" ></script>
        <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js" ></script>
        <script>
            $(function() {
                $("#tabs").tabs();
            });
            $(function() {
                $("#vulnsPfab").load("/sisalbm/admin/dash?action=cuentaFabs");
            });
            $(function() {
                $("#recientes").load("/sisalbm/dash?action=recientes");
            });
        </script>
    </body>
</html>