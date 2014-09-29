<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Software</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <link href="../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />        
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
                <!--<div id="title">&nbsp;Versión Adminstrativa</div>-->
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Agregar Nuevo Software</div>
                        <div id="content">
                            <div id="full">
                                <form class="form" id="addSWForm" name="addSWForm" action="" method="get">
                                    <fieldset>
                                        <legend>Información del Software: </legend>
                                        <table>
                                            <tbody id="cuerpotabla">
                                                <tr>
                                                    <td>
                                                        <label>Ingresar Fabricante</label>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="fabricante" id="fabricante" style="width: 185px"
                                                               title="Se deben ingresar al menos 3 caracteres para desplegar la ayuda."/>
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
                                                        <!--<input type="text" id="nombre" name="nombre" style="width: 185px" />-->
                                                        <select id="nombre" name="nombre" style=" width: 195px"></select>
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
                                                        <!--<input type="text" id="version" name="version" style="width: 185px" />-->
                                                        <select name="version" id="version" style=" width: 195px"></select>
                                                    </td>
                                                    <td>
                                                        <label for="version" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label  title="Seleccionar si el software es un Sistema operativo o una aplicación">Tipo</label>
                                                    </td>
                                                    <td>
                                                        <select name="tipo" id="tipo" style=" width: 195px"> 
                                                            <option value="0">Seleccionar tipo</option>
                                                            <option value="1">Sistema Operativo</option>
                                                            <option value="2">Aplicación</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <label for="tipo" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label title="Este campo es para conocer si el software todavía tiene soporte.">Fin de Vida</label>
                                                    </td>
                                                    <td>
                                                        <select name="eol" id="eol" style=" width: 195px">
                                                            <option value="0">Seleccionar Fin</option>
                                                            <option value="1">Si</option>
                                                            <option value="2">No</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <label for="eol" class="error"></label>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                    <fieldset>
                                        <legend>Grupos</legend>
                                    </fieldset>
                                    <input type="submit" class="inputsubmit" value="Agregar" id="addButton" />
                                    <br />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="dialog-message"><p></p></div>
                <div class="modal"></div>
            </div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script>
        <script type="text/javascript">

            //$(function() {$(document).tooltip();});
            $(document).ready(function() {
                $("#dialog-message").hide();
                $(".agregarAGrupo").hide();
                var vendor = $("#fabricante");
                var vendorval = $("#fabricante").val();
                $.get("/sisalbm/autocomplete?action=getvendor&vendorq=" + vendorval, function(data) {
                    var items = data.split("\n");
                    vendor.autocomplete({
                        source: items, minLength: 3,
                        select: function(event, ui) {
                            cargarProductos(ui.item.value);
                        }
                    });
                });
                function cargarProductos(vendor) {
                    //$("body").addClass("loading");
                    $("#nombre").load("/sisalbm/autocomplete?action=getproduct&vendorq=" + vendor);
                    //$("body").removeClass("loading"); 
                }
                $("#nombre").on("change", function() {
                    $("#version").load("/sisalbm/autocomplete?action=getversion", {product: this.value})
                });

                $("input:radio[name=add2grp]").on("click", function() {
                    var opcion = $("input:radio[name=add2grp]:checked").val();
                    if (opcion === 'si') {
                        $(".agregarAGrupo").show();
                        $("#addgrpBtn").on("click", function() {
                            var content = "<tr class='agregarAGrupo'>"
                                    + "<td>"
                                    + "<label>Seleccionar Grupo: </label>"
                                    + "</td>"
                                    + "<td>"
                                    + "<select name='grpsel' id='grpsel'>"
                                    + "<option value='grp1'>Grupo 1</option>"
                                    + "<option value='grp2'>Grupo 2</option>"
                                    + "</select>"
                                    + "</td>"
                                    + "<td></td>"
                                    + "</tr>";
                            $("<tr class='agregarAGrupo'>"
                                    + "<td>"
                                    + "<label>Seleccionar Grupo: </label>"
                                    + "</td>"
                                    + "<td>"
                                    + "<select name='grpsel' id='grpsel'>"
                                    + "<option value='grp1'>Grupo 1</option>"
                                    + "<option value='grp2'>Grupo 2</option>"
                                    + "</select>"
                                    + "</td>"
                                    + "<td></td>"
                                    + "</tr>").appendTo('cuerpotabla');
                            content = '';
                        });
                    }
                });
                $.validator.addMethod("valProduct", function(value) {
                    return (value !== '0')
                }, "Seleccionar un Producto");
                $.validator.addMethod("valVersion", function(value) {
                    return (value !== '0')
                }, "Seleccionar una versión");
                $.validator.addMethod("valTipo", function(value) {
                    return (value !== '0');
                }, 'Seleccionar un tipo del SW');
                $.validator.addMethod("valEOL", function(value) {
                    return (value !== '0');
                }, 'Seleccionar Fin de Vida');
                $("#addSWForm").validate({
                    rules: {
                        fabricante: "required",
                        nombre: {
                            valProduct: true
                        },
                        version: {
                            valVersion: true
                        },
                        tipo: {
                            valTipo: true
                        },
                        eol: {
                            valEOL: true
                        }
                    },
                    messages: {
                        fabricante: "Ingresar el nombre del fabricante"
                    },
                    submitHandler: function(form) {
                        //alert($(form).serialize());
                        $.ajax({
                            type: 'POST',
                            url: "/sisalbm/admin/vulnerability.controller?action=add&tipo=2",
                            data: $(form).serialize(),
                            success: function(response) {
                                var content = '';
                                if (response === 'OK') {
                                    $("#dialog-message").attr('title', 'Software Agregado');
                                    $("#addSWForm")[0].reset();
                                    var content = "<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "El software fue registrado exitosamente.</p>";
                                    $("#dialog-message").html(content);
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else if (response === 'NOT') {
                                    $("#dialog-message").attr("title", "Software No Agregado");
                                    content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "El Software no pudo ser agregado, debido a un error de la petición. Por favor, intentarlo nuevamente.</p>";
                                    $("#dialog-message").html(content);
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else {
                                    $("#dialog-message").attr("title", "Error de Inserción");
                                    content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrio un error inesperado. Por favor, intentarlo nuevamente.</p>";
                                    $("#dialog-message").html(content);
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                }
                                $("#addSWForm")[0].reset();
                            }, error: function() {
                                $("#dialog-message").attr("title", "Petición Incompleta");
                                var content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                        "Ocurrio un error al realizar la petición al servidor. Intentelo nuevamente.</p>";
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            }
                        });
                        return false;
                    }
                });
            });
        </script>        
    </body>
</html>