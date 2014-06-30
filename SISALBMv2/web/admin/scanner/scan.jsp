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
                $("#full").hide();
                $("#custom").hide();
                $($("input[name=tipo]")).on("click", function() {
                    var tipo = $("input:radio[name=tipo]:checked").val();
                    if (tipo === 'completo') {
                        $("#full").show();
                        $("#custom").hide();
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
                            } else if (fechaC === "fulldate") {
                                $("#fechaCdiv").hide();
                            }
                        }); //onclick fecha
                        $("#fullButton").click(function(event) {
                            //var sForm = $("#scanForm").serialize();
                            var param = "tipo=" + tipo + "&";
                            var fecha = $("input:radio[name=fechaC]:checked").val();
                            if (fecha === "fulldate") {
                                param += "fecha=" + fecha;
                            } else if (fecha === "partialdate") {
                                param += "fecha=" + fecha + "&";
                                param += "sdate=" + $("#sdateC").val() + "&";
                                param += "edate=" + $("#edateC").val();
                            }
                            //alert(param);

                            $.ajax({
                                type: 'get',
                                url: "/sisalbm/scanner?action=scan",
                                data: param,
                                //beforeSend: function() {
                                //alert("/sisalbm/scanner?action=scan&" + param);  
                                //},

                                success: function(result) {
                                    alert(result);
                                }
                            });
                            param = "";
                        });
                    } else if (tipo === 'custom') {
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
                        $("#customButton").click(function(event) {
                            //var sForm = $("#scanForm").serialize();
                            //alert(url + "\n" + sForm);
                            var url = "/sisalbm/scanner?action=scan&tipo=" + tipo + "&";
                            var ua = $("#UA").val();
                            url += "UA=" + ua + "&";
                            var fab = $("input:radio[name=fab]:checked").val();
                            if (fab === "singleFab") {
                                url += "fab=" + fab + "&";
                                var vendor = $("#vendor").val();
                                url += "vendor=" + vendor + "&";
                            } else {
                                url += "fab=" + fab + "&";
                            }
                            var fecha = $("input:radio[name=fecha]:checked").val();
                            if (fecha === "fulldate") {
                                url += "fecha=" + fecha;
                            } else if (fecha === "partialdate") {
                                url += "fecha=" + fecha + "&";
                                url += "sdate=" + $("#sdate").val() + "&";
                                url += "edate=" + $("#edate").val();
                            }
                            alert(url);
                            url = "";
                        });
                    } //else if
                }); //onclick tipo
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
                            <form class="form" id="scanForm" >
                                <label for="tipo">Tipo de Escaneo:</label>
                                <input type="radio" name="tipo" value="completo" id="tipo" />Completo
                                <br/>
                                <input type="radio" name="tipo" value="custom" id="tipo" />Personalizado
                                <br />
                                <br />
                            </form>
                            <div id="full">
                                <form class="form" id="fullForm">
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
                                </form>
                            </div>
                            <div id="custom">
                                <form class="form" id="customForm">
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
                                    <input type="button" value="Escanear" id="customButton" />
                                    <br />
                                </form>
                            </div>

                            <!--</form>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

