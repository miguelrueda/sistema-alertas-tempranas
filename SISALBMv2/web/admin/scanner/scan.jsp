<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Escaneo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.ui.datepicker-es.js" ></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script>
        <script>
            $(document).ready(function() {
                $(function() {
                    $.datepicker.setDefaults($.datepicker.regional['es']);
                });
                $("#UA").load("/sisalbm/scanner?action=retrieve&val=ua");
                $("#full").hide();
                $("#custom").hide();
                $($("input[name=tipo]")).on("click", function() {
                    var tipo = $("input:radio[name=tipo]:checked").val();
                    mostrarFormulario(tipo);

                });
                $("#fullForm").validate({
                    rules: {
                        fechaF: "required",
                        sdateF: "required",
                        edateF: "required"
                    },
                    messages: {
                        fechaF: 'Seleccionar un periodo para realizar el escaneo',
                        sdateF: 'Seleccionar una fecha de inicio',
                        edateF: 'Seleccionar una fecha de termino'
                    }
                });
                $.validator.addMethod("valueNotEquals", function(value) {
                    return (value !== '0');
                }, "Seleccionar un fabricante");
                $("#customForm").validate({
                    rules: {
                        vulnt: "required",
                        fab: "required",
                        vendor: {
                            valueNotEquals: true
                        },
                        fechaC: "required",
                        sdateC: "required",
                        edateC: "required"
                    },
                    messages: {
                        vulnt: 'Seleccionar un tipo de vulnerabilidades',
                        fab: 'Seleccionar un tipo de fabricante',
                        fechaC: 'Seleccionar un periodo para realizar el escaneo',
                        sdateC: 'Seleccionar una fecha de inicio',
                        edateC: 'Seleccionar una fecha de termino'
                    }
                });
            });
            function mostrarFormulario(tipo) {
                if (tipo === 'completo') {
                    $("#full").show();
                    $("#custom").hide();
                    $(".fechaFull").hide();
                    $("input:radio[name=fechaF]").on("click", function() {
                        var fechaF = $("input:radio[name=fechaF]:checked").val();
                        if (fechaF === 'partial') {
                            $(".fechaFull").show();
                            $("#sdateF").datepicker({
                                defaultDate: +0,
                                maxDate: +0,
                                changeMonth: true,
                                changeYear: true,
                                yearRange: '2012:2014',
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
                            $(".fechaFull").hide();
                            $("#sdateF").val("");
                            $("#edateF").val("");
                        }
                    });
                } else if (tipo === 'custom') {
                    $("#custom").show();
                    $("#full").hide();
                    $("#vendordiv").hide();
                    //$("#productrow").hide();
                    $(".fechaCustom").hide();
                    $("#UA").load("/sisalbm/scanner?action=retrieve&val=ua");
                    $("#vendor").load("/sisalbm/scanner?action=retrieve&val=vendor", {vendor:0});
                    /*
                    $("input:radio[name=vulnt]").change(function(){
                       var temp = $(this).val();
                       if (temp === 'recent') {
                           $("input:radio[name=fechaC]").attr("disabled", "disabled");
                        } else {
                            $("input:radio[name=fechaC]").removeAttr("disabled");
                        }
                    });
                    */
                    $("input:radio[name=fab]").on("click", function() {
                        var fab = $("input:radio[name=fab]:checked").val();
                        if (fab === 'single') {
                            $("#vendordiv").show();
                            //$("#productrow").show();
                        } else if (fab === 'multi') {
                            $("#vendordiv").hide();
                            //$("#productrow").hide();
                            $("#vendor").val("");
                        }
                    });
                    $("input:radio[name=fechaC]").on("click", function() {
                        var fechaC = $("input:radio[name=fechaC]:checked").val();
                        if (fechaC === 'partial') {
                            $(".fechaCustom").show();
                            $("#sdateC").datepicker({
                                defaultDate: +0,
                                maxDate: +0,
                                changeYear: true,
                                yearRange: '2012:2014',
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
                            $(".fechaCustom").hide();
                            $("#sdateC").val("");
                            $("#edateC").val("");
                        }
                    });
                }
            }
            function cargarFabricantes() {
                var ua = $("#UA").val();
                $("#vendor").load("/sisalbm/scanner?action=retrieve&val=vendor", {vendor:ua});
            }
            
            function cargarProductos() {
                var fab = $("#vendor").val();
                //alert("/sisalbm/scanner?action=retrieve&val=product&vendor=" + fab);
                $("#product").load("/sisalbm/scanner?action=retrieve&val=product", {vendor:fab});
            }
            
              function showProducts(){
                //obtiene los objetos productCode, 
                var code=$("#productCode").val(); //.. y se obtiene el valor
                //llama al servlet con el parametro seleccionado
                $("#product").load("ProductServlet", {productCode:code})
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
                    <div id="cssmenu">
                        <ul>
                            <li><a href="../../AppIndex.html"><span>AppIndex</span></a></li>
                            <li class="has-sub"><a href="#"><span>Configuración</span></a>
                                <ul>
                                    <li class="has-sub"><a href="#"><span>Fuentes</span></a>
                                        <ul>
                                            <li><a href="../configuration.controller?action=view&tipo=1"><span>Administrar</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="has-sub"><a href="#"><span>Grupos</span></a>
                                        <ul>
                                            <li><a href="../configuration.controller?action=view&tipo=2"><span>Administrar</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="has-sub"><a href="#"><span>Software</span></a>
                                        <ul>
                                            <li><a href="../vulnerabilities/addSW.jsp"><span>Agregar Software</span></a></li>
                                            <li><a href="../vulnerability.controller?action=view&tipo=3"><span>Software Registrado</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="last"><a href="../../JobServlet"><span>Tareas Programadas</span></a></li>
                                </ul>
                            </li>
                            <li><a href="#"><span>Vulnerabilidades</span></a>
                                <ul>
                                    <li><a href="../vulnerability.controller?action=view&tipo=1"><span>Más Recientes</span></a></li>
                                    <li><a href="../vulnerability.controller?action=view&tipo=2"><span>Archivo</span></a></li>
                                </ul>
                            </li>
                            <li><a href="../scanner/scan.jsp"><span>Escaneo</span></a></li>
                            <li><a href="../help.jsp"><span>Ayuda</span></a></li>
                        </ul>
                    </div>
                    <!--
                    <div id="menu">
                        <nav>
                            <ul>
                                <li><a href="../../AppIndex.html">AppIndex</a></li>
                                <li>
                                    <a href="#">Configuración</a>
                                    <ul>
                                        <li><a href="../configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                        <li><a href="../configuration.controller?action=view&tipo=2">Administrar Grupos</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">Vulnerabilidades</a>
                                    <ul>
                                        <li><a href="../vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                        <li><a href="../vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                        <li><a href="../vulnerability.controller?action=view&tipo=3">Software Registrado</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="../scanner/scan.jsp">Escaneo</a>
                                </li>
                                <li>
                                    <a href="../help.jsp">Ayuda</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    -->
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Realizar Escaneo</div>
                        <div id="content">
                            <form class="form" id="scanForm" >
                                <label for="tipo">Tipo de Escaneo:</label>
                                <input type="radio" name="tipo" value="completo" id="tipo" class="required" />Completo
                                <br/>
                                <input type="radio" name="tipo" value="custom" id="tipo" />Personalizado
                                <br />
                                <br />
                            </form>
                            <div id="full">
                                <form class="form" id="fullForm" method="post" action="/sisalbm/scanner?action=scan&tipo=completo">
                                    <p style="text-align: center">
                                        Esté escaneo analizará (todos los grupos/todas las UA) con el archivo completo de Vulnerabilidades.
                                    </p>
                                    <fieldset>
                                        <legend>Seleccionar periodo de escaneo: </legend>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <label>Periodo</label>
                                                    </td>
                                                    <td style="width: 200px">
                                                        <input type="radio" name="fechaF" value="full" id="date" />Completo
                                                        <br />
                                                        <input type="radio" name="fechaF" value="partial" id="date" />Específico
                                                        <br />
                                                    </td>
                                                    <td>
                                                        <label for="fechaF" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr class="fechaFull">
                                                    <td>
                                                        <label>Fecha de Inicio: </label>
                                                    </td>
                                                    <td>
                                                        <input type="text" id="sdateF" name="sdateF" />
                                                    </td>
                                                    <td>
                                                        <label for="sdateF" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr class="fechaFull">
                                                    <td>
                                                        <label>Fecha de Fin: </label>
                                                    </td>
                                                    <td>
                                                        <input type="text" id="edateF" name="edateF" />
                                                    </td>
                                                    <td>
                                                        <label for="edateF" class="error"></label>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                    <input type="submit" class="inputsubmit" value="Escanear" id="fullButton" />
                                    <br />
                                </form>
                            </div>
                            <div id="custom">
                                <form class="form" id="customForm" method="post" action="/sisalbm/scanner?action=scan&tipo=custom">
                                    <fieldset>
                                        <legend>Seleccionar parámetros para el escaneo</legend>
                                        <table>
                                            <tbody>
                                                <tr id="vulns">
                                                    <td>
                                                        <label>Tipo de Vulnerabilidades:</label>
                                                    </td>
                                                    <td style="width: 200px">
                                                        <input type="radio" name="vulnt" value="recent" id="vulnt" />Solo Recientes
                                                        <br />
                                                        <input type="radio" name="vulnt" value="todas" id="vulnt" />Archivo
                                                        <br />
                                                    </td>
                                                    <td>
                                                        <label for="vulnt" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label for="UA">Seleccionar Grupo/UA:</label>
                                                    </td>
                                                    <td>
                                                        <select name="UA" id="UA" onchange="cargarFabricantes()"></select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Tipo de Fabricante: </label>
                                                    </td>
                                                    <td>
                                                        <input type="radio" name="fab" value="multi" id="fab" />Todos los Fabricantes
                                                        <br />
                                                        <input type="radio" name="fab" value="single" id="fab" />Fabricante Específico
                                                        <br />
                                                    </td>
                                                    <td>
                                                        <label for="fab" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr id="vendordiv">
                                                    <td>
                                                        <label>Seleccionar Fabricante:</label>
                                                    </td>
                                                    <td>
                                                        <select name="vendor" id="vendor" onchange="cargarProductos()"></select>
                                                    </td>
                                                    <td>
                                                        <label for="vendor" class="error" ></label>
                                                    </td>
                                                </tr>
                                                <!--
                                                <tr id="productrow">
                                                    <td>
                                                        <label>Seleccionar Producto:</label>
                                                    </td>
                                                    <td>
                                                        <select name="product" id="product"></select>
                                                    </td>
                                                    <td>
                                                        <label for="product" class="error"></label>
                                                    </td>
                                                </tr>
                                                -->
                                                <tr id="sevDiv">
                                                    <td>
                                                        <label>Gravedad</label>
                                                    </td>
                                                    <td>
                                                        <select name="critic" id="critic">
                                                            <option value="0">Cualquier Nivel</option>
                                                            <option value="1">Baja</option>
                                                            <option value="2">Media</option>
                                                            <option value="3">Alta</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <label for="critic" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr id="periods">
                                                    <td>
                                                        <label>Periodo de Escaneo: </label>
                                                    </td>
                                                    <td>
                                                        <input type="radio" name="fechaC" value="full" id="date" />Periodo Completo
                                                        <br />
                                                        <input type="radio" name="fechaC" value="partial" id="date" />Periodo Específico
                                                        <br />
                                                    </td>
                                                    <td>
                                                        <label for="fechaC" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr class="fechaCustom">
                                                    <td>
                                                        <label>Fecha de Inicio: </label>
                                                    </td>
                                                    <td>
                                                        <input type="text" id="sdateC" name="sdateC" />
                                                    </td>
                                                    <td>
                                                        <label for="sdateC" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr class="fechaCustom">
                                                    <td>
                                                        <label>Fecha de Fin: </label>
                                                    </td>
                                                    <td>
                                                        <input type="text" id="edateC" name="edateC" />
                                                    </td>
                                                    <td>
                                                        <label for="edateC" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label></label>
                                                    </td>
                                                    <td>
                                                        <input type="checkbox" name="onlypub" value="onlypub" />Buscar en Modificadas
                                                    </td>
                                                    <td>
                                                        <label for="" class="error"></label>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                    <input type="submit" value="Escanear" id="customButton" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

