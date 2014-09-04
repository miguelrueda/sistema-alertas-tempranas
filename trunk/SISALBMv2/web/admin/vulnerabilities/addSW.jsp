<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Software</title>
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
                <!--<div id="title">&nbsp;Versión Adminstrativa</div>-->
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Agregar Software</div>
                        <div id="content">
                            <div id="full">
                                <form class="form" id="addSWForm" name="addSWForm" action="" method="get">
                                    <fieldset>
                                        <legend>Información del Software: </legend>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <label>Ingresar Fabricante</label>
                                                    </td>
                                                    <td>
                                                        <!--<select name="fabricante" id="fabricante"></select>-->
                                                        <input type="text" name="fabricante" id="fabricante" style="width: 185px"/>
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
                                                        <label>Tipo</label>
                                                    </td>
                                                    <td>
                                                        <select name="tipo" id="tipo" style=" width: 195px">
                                                            <option value="0">Seleccionar tipo</option>
                                                            <option value="1">Sistema Operativo</option>
                                                            <option value="2">Software</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <label for="tipo" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Fin de Vida</label>
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
                                    <input type="submit" class="inputsubmit" value="Agregar" id="addButton" />
                                    <br />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="dialog-message"><p>Content</p></div>
            </div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#dialog-message").hide();
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
                    $("#nombre").load("/sisalbm/autocomplete?action=getproduct&vendorq=" + vendor);
                }
                $("#nombre").on("change", function() {
                    $("#version").load("/sisalbm/autocomplete?action=getversion", {product: this.value})
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
                                $("#dialog-message").attr('title', 'Agregar Software');
                                var content = '';
                                if (response === 'OK') {
                                    content = 'El Software ha sido agregado exitosamente.';
                                } else if (response === 'NOT') {
                                    content = 'Ocurrio un error al intentar agregar el Software, Intentelo más tarde.';
                                } else {
                                    content = 'Ocurrio un error inesperado.';
                                }
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
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