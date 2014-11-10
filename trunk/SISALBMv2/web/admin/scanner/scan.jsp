<%-- 
    JSP que contiene el formulario para realizar la búsqueda de vulnerabilidades bajo 
    diversos parametros
--%>
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
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Realizar Escaneo</div>
                        <div id="content">
                            <!-- parametros iniciales del formulario -->
                            <form class="form" id="scanForm" >
                                <label for="tipo">Tipo de Escaneo:</label>
                                <input type="radio" name="tipo" value="completo" id="tipo" class="required" 
                                       title="Se realiza una búsqueda con todos los registros de vulnerabilidades existentes." />Completo
                                <br/>
                                <input type="radio" name="tipo" value="custom" id="tipo" 
                                       title="Se realiza una búsqueda solo con vulnerabilidades de un periodo no mayor a 8 días."/>Personalizado
                                <br />
                                <br />
                            </form>
                            <div id="full"><!-- div que contiene las opciones para hacer una búsqueda completa de vulnerabilidades -->
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
                                                        <input type="radio" name="fechaF" value="full" id="date" 
                                                               title="Contempla búscar en todo el periodo desde 2013 hasta la fecha actual."/>Completo
                                                        <br />
                                                        <input type="radio" name="fechaF" value="partial" id="date" 
                                                               title="Contempla un periodo seleccionado entre 2013 y la fecha actual."/>Específico
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
                                    <input type="submit" class="inputsubmit" value="Buscar Vulnerabilidades" id="fullButton" />
                                    <br />
                                </form>
                            </div><!-- div full -->
                            <div id="custom"><!-- div que se muestra cuando se quiere una busqueda más específica -->
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
                                                        <input type="radio" name="vulnt" value="recent" id="vulnt" 
                                                               title="Periodo no mayor a 8 días"/>Solo Recientes
                                                        <br />
                                                        <input type="radio" name="vulnt" value="todas" id="vulnt"
                                                               title="Vulnerabilidades desde 1 de Enero de 2013"/>Archivo
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
                                                        <label>Fabricantes del Grupo: </label>
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
                                                        <input type="radio" name="fechaC" value="full" id="date" 
                                                               title="Contempla búscar en todo el periodo reciente(8 días)."/>Periodo Completo
                                                        <br />
                                                        <input type="radio" name="fechaC" value="partial" id="date" 
                                                               title="Contempla un periodo seleccionado entre 2013 y la fecha actual."/>Periodo Específico
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
                                                        <input type="checkbox" name="onlypub" value="onlypub" 
                                                               title="Se realiza la búsqueda tomando en cuenta vulnerabilidades 
                                                               que han sido modificadas durante el periodo seleccionado." />Buscar en Modificadas
                                                    </td>
                                                    <td>
                                                        <label for="" class="error"></label>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                    <input type="submit" value="Buscar Vulnerabilidades" id="customButton" />
                                </form>
                            </div><!-- div custom -->
                        </div><!-- content -->
                    </div>
                </div>
            </div><!-- page content -->
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.ui.datepicker-es.js" ></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script>
        <script type="text/javascript">
            /**
             * Función que inicializa el tooltip
             */
            $(function() { $(document).tooltip();});
            /**
             * Función jQuery que maneja la funcionalidad de la página
             */
            $(document).ready(function() {
                //Inicializar el calendario a español
                $(function() {
                    $.datepicker.setDefaults($.datepicker.regional['es']);
                });
                $("#UA").load("/sisalbm/scanner?action=retrieve&val=ua");
                $("#full").hide();
                $("#custom").hide();
                //Se solicita el campo de tipo de busqueda y a partir de ese parametro se carga 
                //el formulario correspondiente
                $($("input[name=tipo]")).on("click", function() {
                    var tipo = $("input:radio[name=tipo]:checked").val();
                    mostrarFormulario(tipo);

                });
                //validación del formulario completo
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
                //validación para el formulario custom
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
            /**
             * Funcióno que se encarga de mostrar el formulario de acuerdo
             * al tipo de formulario que se seleccione del formulario principal
             */
            function mostrarFormulario(tipo) {
                if (tipo === 'completo') {
                    $("#full").show();
                    $("#custom").hide();
                    $(".fechaFull").hide();
                    //Mostrar los campos de fecha a partir del tipo de fecha seleccionado
                    $("input:radio[name=fechaF]").on("click", function() {
                        var fechaF = $("input:radio[name=fechaF]:checked").val();
                        //Si la fecha es parcial mostrar los calendarios de jQuery
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
                            //Si la fecha es completa establecer los valores date a vacio
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
                    //Cargar los grupos en el formulario, y para el grupo 1 cargar sus fabricantes
                    $("#UA").load("/sisalbm/scanner?action=retrieve&val=ua");
                    $("#vendor").load("/sisalbm/scanner?action=retrieve&val=vendor", {vendor: 0});
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
                        //A partir del tipo de fabricante selecionado mostrar el div para seleccionar uno especifico
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
                        //Script que muestra los calendarios en caso de que se solicite una fecha especifica
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
            /**
             * Función que se encarga de cargar los fabricantes en el campo select
             * a partir de los fabricantes existenes en un grupo
             */
            function cargarFabricantes() {
                var ua = $("#UA").val();
                $("#vendor").load("/sisalbm/scanner?action=retrieve&val=vendor", {
                    vendor: ua
                });
            }
            /**
             * Función que se encarga de cargar los productos de un fabricante
             */
            function cargarProductos() {
                var fab = $("#vendor").val();
                $("#product").load("/sisalbm/scanner?action=retrieve&val=product", {
                    vendor: fab
                });
            }
            /**
             * Función no utilizada
             */
            function showProducts() {
                //obtiene los objetos productCode, 
                var code = $("#productCode").val(); //.. y se obtiene el valor
                //llama al servlet con el parametro seleccionado
                $("#product").load("ProductServlet", {
                    productCode: code
                });
            }
        </script>
    </body>
</html>