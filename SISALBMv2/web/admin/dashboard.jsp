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
            #canvas {
                border: 1px solid #444444;
                padding-left: 0;
                padding-right: 0;
                margin-left: auto;
                margin-right: auto;
                display: block;
                width: 800px;
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
                                    <h5>Algo extra</h5>
                                    <div id="vulnsPfab"></div>
                                </div>
                                <div id="tabs-3">
                                    <p class="tabtitle">Vulnerabilidades por Fabricante</p>
                                    <div id="chartdiv" style="margin: 0px auto 0px auto;">
                                        <canvas id="canvas" width="800" height="600"></canvas> 
                                    </div>
                                    <p class="tabtitle">Gravedad de Vulnerabilidades</p>
                                    <div id="severitydiv">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Frame para desplegar los detalles -->
                <div id="dialogdiv" title="Detalle de la Vulnerabilidad" style="display: none">
                </div>
            </div><!-- Contenido de la pagina -->
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js" ></script>
        <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js" ></script>
        <script src="/sisalbm/resources/js/Chart.min.js"></script>
        <script>
            $(document).ready(function() {
                /*
                 ctx.fillStyle = "#000";
                 ctx.fillRect(0, 0, 600, 400);
                 ctx.fillStyle = "#fff";
                 ctx.font = "bold 20px sans-serif";
                 ctx.fillText('Example', 20, 20);
                 */
                var labels;
                 $.ajax({
                    type: 'POST',
                    url: '/sisalbm/dash?action=genchart',
                    cache: false,
                    success: function(data) {
                        alert(data);
                    }
                 });
                //var array = labels.split(" ");
                //alert(array);
                var data = {
                    labels: ["apr", "may", "jun", "jul", "aug", "sep"],
                    datasets: [{
                            fillColor: "rgba(0, 135, 190, 1)",
                            strokeColor: "rgba(0, 135, 190, 1)",
                            data: [456, 479, 324, 569, 202, 600]
                        }, {
                            fillColor: "rgba(0,26,255,1)",
                            strokeColor: "rgba(0,26,255,1)",
                            data: [364, 504, 80, 400, 345, 320]
                        }
                    ]
                };
                var cvs = document.getElementById('canvas');
                var ctx = cvs.getContext('2d');
                var chart = new Chart(ctx).Bar(data);

            });
        </script>
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
            function verDetalle(nombre) {
                var content = "";
                $.ajax({
                    url: '/sisalbm/dash?action=retrieveby',
                    type: 'GET',
                    data: 'nombre=' + nombre,
                    success: function(result) {
                        content = result;
                        $("#dialogdiv").html(content);
                        $("#dialogdiv").dialog({
                            width: 800,
                            height: 800,
                            resizable: false,
                            draggable: false,
                            buttons: {
                                Aceptar: function() {
                                    $(this).dialog("close");
                                }
                            }
                        });
                    }
                });
            }
        </script>
    </body>
</html>