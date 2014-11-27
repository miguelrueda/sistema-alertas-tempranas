<%-- 
JSP que se encarga de mostrar un formulario para solicitar los datos y registrar un
nuevo software para ser monitoreado
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Software</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <link href="/sisalbm/resources/css/jquery.notice.css" type="text/css" rel="stylesheet"/>
        <style type="text/css">
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                top: 0;
                left: 0;
                height: 100%;
                width: 100%;
                float: left;
                background: rgba(255, 255, 255, .8)
                    url('http://sampsonresume.com/labs/pIkfp.gif') 
                    50% 50% 
                    no-repeat 
            }
            body.loading {
                overflow: hidden;
            }
            body.loading .modal {
                display: block
            }
            .addgrouptemplate {
                display: none;
            }
            .addRow{
                font-size: 10pt !important;
                font-weight: bold !important;
            }
            .ui-autocomplete {
                font-size: 90%;
                max-width: 400px;
                max-height: 200px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
                overflow-x: hidden;
            }
            html .ui-autocomplete {
                height: 100px;
            }
        </style>
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
                        <div id="page_title">Agregar Nuevo Software</div>
                        <div id="content">
                            <div id="swform">
                                <form class="form" id="addswform" name="addswform">
                                    <fieldset>
                                        <legend>Información del Software</legend>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <label>Nombre del Fabricante</label>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="fabricante" id="fabricante" 
                                                               style="width: 185px"
                                                               title="Se deben ingresar al menos 3 caracteres para desplegar la ayuda." />
                                                    </td>
                                                    <td>
                                                        <label for="fabricante" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Seleccionar Producto</label>
                                                    </td>
                                                    <td>
                                                        <select id="nombre" name="nombre" style="width: 195px">
                                                            <option value="0">Cargando productos . . .</option> 
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <label for="nombre" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Versión</label>
                                                    </td>
                                                    <td>
                                                        <select name="version" id="version" style="width: 195px">
                                                            <option value="0">Cargando versiones . . . </option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <label for="version" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>¿Añadir a Grupo?</label>
                                                    </td>
                                                    <td>
                                                        <input type="radio" name="grupo" value="si" id="grupo"
                                                               title="Se seleccionarán los grupos donde se asociará éste software" />Si
                                                        <br />
                                                        <input type="radio" name="grupo" value="no" id="grupo"
                                                               title="No se asociará el software a ningún grupo" />No
                                                        <br />
                                                    </td>
                                                    <td>
                                                        <label for="grupo" class="error"></label>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                    <div id="innerdiv">
                                        <fieldset>
                                            <legend>Selección de Grupos</legend>
                                            <p style="text-align: center; font-weight: bold">El software a registrar se asociará a los grupos seleccionados.</p>
                                            <div class="addgrouptemplate">
                                                <div class="controls controls-row">
                                                    <label class="span3" name="groupname">Seleccionar Grupo</label>
                                                    <select class="span2" name="grupo" id="cargarGrupos"></select>
                                                </div>
                                            </div>
                                            <div id="container"></div>
                                            <a href="#" id="addRow">
                                                + Agregar grupo
                                            </a>
                                        </fieldset>
                                    </div>
                                    <input type="submit" class="inputsubmit" 
                                           value="Agregar Software" id="addButton" />
                                    <br />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="dialog-message"></div>
                <div class="modal"></div>
            </div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script>
        <script src="/sisalbm/resources/js/jquery.notice.js"></script>
        <script type="text/javascript">
            /**
             * Función jQuery que se encarga de manejar la funcionalidad de la aplicación
             */
            $(document).ready(function() {
                var num_grupos = 0;
                $("#dialog-message").hide();
                $("#innerdiv").hide();
                var vendor = $("#fabricante");
                var vendorval = $("#fabricante").val();
                /**
                 * Función que se ejecuta cuando ocurre un error al cargar la información de los grupos
                 */
                $("#cargarGrupos").load("/sisalbm/autocomplete?action=getgrupos", function(response, status, xhr) {
                    if (status === "error") {
                        var msj = "Error inesperado!";
                        msj += " Sucedio un error al realizar la petición: " + xhr.status + " - " + xhr.statusText;
                    }
                });
                /**
                 * Función que realiza la petición ajax para el autocompletado del software
                 */
                $.get("/sisalbm/autocomplete?action=getvendor&vendorq=" + vendorval, function(data) {
                    var items = data.split("\n");
                    vendor.autocomplete({
                        source: items,
                        minLength: 2,
                        select: function(event, ui) {
                            cargarProductos(ui.item.value);
                        }
                    });
                });
                /**
                 * Función que se encarga de cargar los productos de un vendedor especifico
                 */
                function cargarProductos(vendor) {
                    $("#nombre").load("/sisalbm/autocomplete?action=getproduct&vendorq=" + vendor)
                }
                /**
                 * Función que se encarga de cargar la información de las versiones a partir del nombre de un software
                 */
                $("#nombre").on("change", function() {
                    $("#version").load("/sisalbm/autocomplete?action=getversion", {product: this.value});
                });
            });
            /*
             * Función para cargar el div de grupos
             */
            $("input:radio[name=grupo]").on("click", function() {
                var grupo = $("input:radio[name=grupo]:checked").val();
                if (grupo === 'si') {
                    $("#innerdiv").show();
                } else if (grupo === 'no') {
                    $("#innerdiv").hide();
                }
            });
            /*
             * Funcion para cargar el primer selector
             */
            /* $("<div />", {'class': 'extraGrupo', html: obtenerHTML() }).appendTo("#container"); */
            $("#addRow").click(function() {
                $("<div />", {
                    'class': 'extraGrupo',
                    html: obtenerHTML()
                }).hide().appendTo("#container").slideDown('slow');
            });
            /**
             * Función que se encarga de obtener la información del grupo al cual se quiere agregar el software
             */
            function obtenerHTML() {
                var len = $(".extraGrupo").length;
                var $html = $(".addgrouptemplate").clone();
                $html.find("[name=groupname]")[0].name = "groupname" + len;
                $html.find("[name=grupo]")[0].name = "grupo" + len;
                return $html.html();
            }
            /*
             * Validación del Formulario
             */
            $.validator.addMethod("valProduct", function(value) {
                return (value !== '0')
            }, "Seleccionar un elemento");
            $.validator.addMethod("valVersion", function(value) {
                return (value !== '0')
            }, "Seleccionar una versión");
            $("#addswform").validate({
                rules: {
                    fabricante: "required",
                    nombre: {
                        valProduct: true
                    },
                    version: {
                        valVersion: true
                    },
                    grupo: "required"
                }, messages: {
                    fabricante: "Ingresar el nombre del fabricante.",
                    grupo: "Seleccionar opción de grupos."
                }, submitHandler: function(form) {
                    //Procesamiento del formulario
                    var ng = $(".extraGrupo").length;
                    var formser = $(form).serialize();
                    formser += "&total=" + ng;
                    //alert(formser);
                    //Inicio de la petición AJAX
                    $.ajax({
                        type: 'POST',
                        url: '/sisalbm/admin/vulnerability.controller?action=add&tipo=2',
                        data: formser,
                        beforeSend: function() {
                            //Mostrar mensaje de aviso de procesamiento
                            jQuery.noticeAdd({
                                text: "Registrando software <br /><center><img src='/sisalbm/resources/images/ajax-loader.gif' alt='Cargando...' /></center>",
                                stay: false,
                                type: 'info'
                            });
                        }, success: function(response) {
                            response = response.trim();
                            if (response === 'EXISTENTE') {
                                //Mostrar mensaje cuando el software a registrar ya existe
                                $("#dialog-message").attr("title", "Software Existente");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "Los datos ingresados coinciden con un software ya registrado en la BD</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                            } else if (response === 'OK') {
                                //Mostrar mensaje cuando la petición se resuelve de manera correcta
                                $("#dialog-message").attr("title", "Software Registrado");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "El software ha sido registrado exitosamente!</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                                //Reiniciar variables
                                $("#addswform")[0].reset();
                                $("#innerdiv").hide();
                                $("#container").html("");
                            } else if (response === 'NOT') {
                                //Mostrar mensaje cuando el software no se registra por un error del servidor
                                $("#dialog-message").attr("title", "Software No Registrado");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "El software no fue registrado debido a un error de la aplicación, intentarlo nuevamente!</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                            } else if (response === 'ERROR') {
                                //Mostrar mensaje cuando ocurre un error de inserción del software
                                $("#dialog-message").attr("title", "Error de Inserción");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                        + "El software no fue registrado debido a un error de la aplicación, intentarlo nuevamente!</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                            $("#dialog-message").attr("title", "");
                                        }
                                    }
                                });
                            }
                        }, error: function() {
                            //Mostrar mensaje cuando la petición al servidor no se procesa debido a un error 
                            $("#dialog-message").attr("title", "Petición Incompleta");
                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>"
                                    + "Ocurrió un error al realizar la petición al servidor. Inténtelo nuevamente.</p>");
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Aceptar: function() {
                                        $(this).dialog("close");
                                        $("#dialog-message").attr("title", "");
                                    }
                                }
                            });
                        }
                    });
                    return false;
                }
            });
        </script>
    </body>
</html>