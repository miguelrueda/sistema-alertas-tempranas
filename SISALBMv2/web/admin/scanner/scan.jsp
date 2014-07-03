<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Escaneo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.ui.datepicker-es.js" ></script>
        <script>
            $(document).ready(function() {
                $(function(){
                    $.datepicker.setDefaults($.datepicker.regional['es']);
                });
                $("#full").hide();
                $("#custom").hide();
                $($("input[name=tipo]")).on("click", function() {
                    var tipo = $("input:radio[name=tipo]:checked").val();
                    mostrarFormulario(tipo);

                });
                $("#fullForm").submit(function(event) {
                    //var serForm = $("#fullForm").serialize();
                    //alert(serForm);
                });
                $("#customForm").submit(function(event){
                    
                });
                /*
                $("#fullButton").click(function(event){
                    var serForm = $("#fullForm").serialize();
                    alert(serForm);
                });
                $("#customButton").click(function(event){
                    var serForm = $("#customForm").serialize();
                    alert(serForm);
                });
                */
            });
            function mostrarFormulario(tipo) {
                if (tipo === 'completo') {
                    $("#full").show();
                    $("#custom").hide();
                    $("#fechaFull").hide();
                    $("input:radio[name=fechaF]").on("click", function(){
                        var fechaF = $("input:radio[name=fechaF]:checked").val();
                        if (fechaF === 'partial') {
                            $("#fechaFull").show();
                            $("#sdateF").datepicker({
                                defaultDate: +0,
                                maxDate: +0,
                                changeMonth: true,
                                onClose: function(selectedDate) {
                                    $("#edateF").datepicker("option", "minDate", selectedDate);
                                }
                            });
                            $("#edateF").datepicker({
                                maxDate: +0,
                                changeMonth: true,
                                onClose: function(selectedDate) {
                                    $("#sdateF").datepicker("option", "maxDate", selectedDate);
                                }
                            });
                        } else if (fechaF === 'full') {
                            $("#fechaFull").hide();
                            $("#sdateF").val("");
                            $("#edateF").val("");
                        }
                    });
                } else if (tipo === 'custom') {
                    $("#custom").show();
                    $("#full").hide();
                    $("#vendordiv").hide();
                    $("#fechaCustom").hide();
                    $("#UA").load("/sisalbm/scanner?action=retrieve&val=ua");
                    $("#vendor").load("/sisalbm/scanner?action=retrieve&val=vendor");
                    $("input:radio[name=fab]").on("click", function(){
                        var fab = $("input:radio[name=fab]:checked").val();
                        if (fab === 'single') {
                            $("#vendordiv").show();
                        } else if (fab === 'multi') {
                            $("#vendordiv").hide();
                            $("#vendor").val("");
                        }
                    });
                    $("input:radio[name=fechaC]").on("click", function(){
                        var fechaC = $("input:radio[name=fechaC]:checked").val();
                        if (fechaC === 'partial') {
                            $("#fechaCustom").show();
                            $("#sdateC").datepicker({
                                defaultDate: +0,
                                maxDate: +0,
                                changeMonth: true,
                                onClose: function(selectedDate) {
                                    $("#edateC").datepicker("option", "minDate", selectedDate);
                                }
                            });
                            $("#edateC").datepicker({
                                maxDate: +0,
                                onClose: function(selectedDate) {
                                    $("#sdateC").datepicker("option", "maxDate", selectedDate);
                                }
                            });
                        } else if (fechaC === 'full') {
                            $("#fechaCustom").hide();
                            $("#sdateC").val("");
                            $("#edateC").val("");
                        }
                    });
                }
            }
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
                                <form class="form" id="fullForm" method="post" action="/sisalbm/scanner?action=scan&tipo=completo">
                                    <p style="text-align: center">
                                        Esté escaneo analizará todas las UA con el archivo completo de Vulnerabilidades.
                                    </p>
                                    <label for="fecha">Periodo de Escaneo: </label>
                                    <input type="radio" name="fechaF" value="full" id="date" />Periodo Completo
                                    <br />
                                    <input type="radio" name="fechaF" value="partial" id="date" />Periodo Específico
                                    <br /><br />
                                    <div id="fechaFull">
                                        <label for="sdateF">Fecha de Inicio: </label>
                                        <input type="text" id="sdateF" name="sdateF" />
                                        <br /><br />
                                        <label for="edateF">Fecha de Fin: </label>
                                        <input type="text" id="edateF" name="edateF" />
                                        <br />
                                        <br />
                                    </div>
                                    <input type="submit" value="Escanear" id="fullButton" />
                                    <br />
                                </form>
                            </div>
                            <div id="custom">
                                <form class="form" id="customForm" method="post" action="/sisalbm/scanner?action=scan&tipo=custom">
                                    <div id="vulns">
                                        <label for="vulnt">Tipo de Vulnerabilidades:</label>
                                        <input type="radio" name="vulnt" value="recent" id="vulnt" />Solo Recientes
                                        <br />
                                        <input type="radio" name="vulnt" value="todas" id="vulnt" />Archivo
                                        <br /><br/>
                                    </div>
                                    <label for="UA">Seleccionar UA:</label>
                                    <select name="UA" id="UA"></select><br /><br />
                                    <label for="fab">Tipo de Fabricante: </label>
                                    <input type="radio" name="fab" value="multi" id="fab" />Todos los Fabricantes
                                    <br />
                                    <input type="radio" name="fab" value="single" id="fab" />Fabricante Específico
                                    <br /><br />
                                    <div id="vendordiv">
                                        <label for="vendor">Seleccionar Fabricante:</label>
                                        <select name="vendor" id="vendor"></select>
                                        <br /><br />
                                    </div>
                                    <div id="sevDiv">
                                        <label for="critic">Criticidad</label>
                                        <select name="critic" id="critic">
                                            <option value="0">Cualquier Nivel</option>
                                            <option value="1">Baja</option>
                                            <option value="2">Media</option>
                                            <option value="3">Alta</option>
                                        </select>
                                    </div>
                                    <br />
                                    <label for="fecha">Periodo de Escaneo: </label>
                                    <input type="radio" name="fechaC" value="full" id="date" />Periodo Completo
                                    <br />
                                    <input type="radio" name="fechaC" value="partial" id="date" />Periodo Específico
                                    <br /><br />
                                    
                                    <div id="fechaCustom">
                                        <label for="sdateC">Fecha de Inicio: </label>
                                        <input type="text" id="sdateC" name="sdateC" />
                                        <br /><br />
                                        <label for="edateC">Fecha de Fin: </label>
                                        <input type="text" id="edateC" name="edateC" />
                                        <br />
                                        <br />
                                    </div>
                                    <input type="submit" value="Escanear" id="customButton" />
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

