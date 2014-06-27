<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Escaneo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="../../resources/js/jquery.ui.datepicker-es.js" ></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            $(document).ready(function() {
                $("#custom").hide();
                $("#full").hide();
                $($("input[name=tipo]")).on("click", function() {
                    var tipo = $("input:radio[name=tipo]:checked").val();
                    if (tipo === 'custom') {
                        //TODO: SHOW BASIC
                        $("#custom").show();
                        $("#full").hide();
                        $("#vendordiv").hide();
                        $("#fechadiv").hide();
                        $("#UA").load("/sisalbm/scanner?action=retrieve&val=ua");
                        $("#vendor").load("/sisalbm/scanner?action=retrieve&val=vendor");
                        $("input:radio[name=fab]").on("click", function() {
                            var fab = $("input:radio[name=fab]:checked").val();
                            if (fab === "singleFab") {
                                $("#vendordiv").show();
                            } else {
                                $("#vendordiv").hide();
                            }
                        });
                        $("input:radio[name=fecha]").on("click", function() {
                            var fecha = $("input:radio[name=fecha]:checked").val();
                            if (fecha === "partialdate") {
                                $("#fechadiv").show();
                                $("#sdate").datepicker({
                                    defaultDate: +0,
                                    changeMonth: true,
                                    onClose: function(selectedDate) {
                                        $("#edate").datepicker("option", "minDate", selectedDate);
                                    }
                                });
                                $("#edate").datepicker({
                                    maxDate: +0,
                                    onClose: function(selectedDate) {
                                        $("#sdate").datepicker("option", "maxDate", selectedDate);
                                    }
                                });
                            } else {
                                $("#fechadiv").hide();
                            }
                        });
                        $("#basicButton").click(function(event) {
                            var sForm = $("#scanForm").serialize();
                            alert(sForm);
                        });
                    } else {
                        //TODO: SHOW COMPLETE
                        $("#custom").hide();
                        $("#full").show();
                        $("#fechaCdiv").hide();
                        $("input:radio[name=fechaC]").on("click", function() {
                            var fechaC = $("input:radio[name=fechaC]:checked").val();
                            if (fechaC === "partialdate") {
                                $("#fechaCdiv").show();
                                $("#sdateC").datepicker({
                                    defaultDate: +0,
                                    changeMonth: true,
                                    onClose: function(selectedDate) {
                                        $("#edateC").datepicker("option", "minDate", selectedDate);
                                    }
                                });
                                $("#edateC").datepicker({
                                    onClose: function(selectedDate) {
                                        $("#sdateC").datepicker("option", "maxDate", selectedDate);
                                    }
                                });
                            } else {
                                $("#fechaCdiv").hide();
                            }
                        });
                        $("#fullButton").click(function(event) {
                            var sForm = $("#scanForm").serialize();
                            alert(sForm);
                        });
                    }
                });
            });
        </script>
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
                        <div id="page_title">Realizar Escaneo</div>
                        <div id="content">
                            <form class="form" id="scanForm">
                                <label for="tipo">Tipo de Escaneo:</label>
                                <input type="radio" name="tipo" value="completo" id="tipo" />Completo
                                <br/>
                                <input type="radio" name="tipo" value="custom" id="tipo" />Personalizado
                                <br />
                                <br />
                                <div id="custom">
                                    <label for="UA">Seleccionar UA:</label>
                                    <select name="UA" id="UA"></select><br /><br />
                                    <label for="fab">Tipo de Fabricante: </label>
                                    <input type="radio" name="fab" value="multiFab" id="fab" />Todos los Fabricantes
                                    <br />
                                    <input type="radio" name="fab" value="singleFab" id="fab" />Fabricante Específico
                                    <br /><br />
                                    <div id="vendordiv">
                                        <label for="vendor">Seleccionar Fabricante:</label>
                                        <select name="vendor" id="vendor"></select>
                                        <br /><br />
                                    </div>
                                    <label for="fecha">Periodo de Escaneo: </label>
                                    <input type="radio" name="fecha" value="fulldate" id="date" />Periodo Completo
                                    <br />
                                    <input type="radio" name="fecha" value="partialdate" id="date" />Periodo Específico
                                    <br /><br />
                                    <div id="fechadiv">
                                        <label for="sdate">Fecha de Inicio: </label>
                                        <input type="text" id="sdate" name="sdate" />
                                        <br /><br />
                                        <label for="edate">Fecha de Fin: </label>
                                        <input type="text" id="edate" name="edate" />
                                        <br />
                                        <br />
                                    </div>
                                    <input type="button" value="Escanear" id="basicButton" />
                                    <br />
                                </div>
                                <div id="full">
                                    <p style="text-align: center">
                                        Esté escaneo analizará todas las UA con el archivo completo de Vulnerabilidades.
                                    </p>
                                    <label for="fecha">Periodo de Escaneo: </label>
                                    <input type="radio" name="fechaC" value="fulldate" id="date" />Periodo Completo
                                    <br />
                                    <input type="radio" name="fechaC" value="partialdate" id="date" />Periodo Específico
                                    <br /><br />
                                    <div id="fechaCdiv">
                                        <label for="sdateC">Fecha de Inicio: </label>
                                        <input type="text" id="sdateC" name="sdateC" />
                                        <br /><br />
                                        <label for="edateC">Fecha de Fin: </label>
                                        <input type="text" id="edateC" name="edateC" />
                                        <br />
                                        <br />
                                    </div>
                                    <input type="button" value="Escanear" id="fullButton" />
                                    <br />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>