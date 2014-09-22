<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Nueva Fuente</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="../../resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div><!--page header-->
            <div id="page_content">
                <!--<div id="title">&nbsp;Versión Administrativa</div>-->
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Agregar Nueva Fuente</div>
                        <br />
                        <div id="content">
                            <form class="form" id="addFuente" name="addFuente" action="">
                                <fieldset>
                                    <legend>Información de la Fuente</legend>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <label>Nombre de la Fuente</label>
                                                </td>
                                                <td>
                                                    <input type="text" name="nombre" id="nombre" style="width: 200px" 
                                                           title="El nombre se utilizará para identificar a la fuente ej. Vulnerabilidades de 201X."/>
                                                </td>
                                                <td>
                                                    <label for="nombre" class="error"></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>URL de la Fuente</label>
                                                </td>
                                                <td>
                                                    <input type="text" name="url" id="url" style="width: 200px" 
                                                           title="La url del sitio nvd.nist que contiene la información de vulnerabilidades deseadas."/>
                                                </td>
                                                <td>
                                                    <label for="url" class="error"></label>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </fieldset>
                                <input type="submit" class="inputsubmit" value="Agregar" id="addButton" />
                                <br />
                            </form>
                        </div><!--content-->
                    </div>
                </div><!--work area-->
            </div><!--page content-->
            <div id="dialog-message"></div>
        </div><!--page container-->
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.notice.js" ></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script> 
        <script type="text/javascript">
            $(document).ready(function() {
                $("#addFuente").validate({
                    rules: {
                        nombre: "required",
                        url: "required"
                    },
                    messages: {
                        nombre: 'Ingresar el nombre de la fuente',
                        url: 'Ingresar la url de la fuente'
                    },
                    submitHandler: function(form) {
                        var formserialized = $(form).serialize();
                        $.ajax({
                            url: '/sisalbm/admin/configuration.controller?action=addFuente',
                            type: 'POST',
                            data: formserialized,
                            beforeSend: function() {
                                jQuery.noticeAdd({
                                    text: "Procesando Solicitud: " +
                                            "<br /><center><img src='../../resources/images/ajax-loader.gif' alt='loading' /></center>",
                                    stay: true,
                                    type: 'info'
                                });
                            },
                            success: function(response) {
                                var content = '';
                                if (response === 'URL INVALIDA') {
                                    $("#dialog-message").attr("title", "Url Invalida");
                                    content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "La URL es incorrecta o el formato no es válido.</p>";
                                    $("#dialog-message").html(content);
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else if (response === 'OK') {
                                    $("#dialog-message").attr("title", "Fuente Agregada");
                                    $("#addFuente")[0].reset();
                                    var content = "<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "La fuente de información fue agregada exitosamente.</p>";
                                    $("#dialog-message").html(content);
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else if (response === 'ERROR') {
                                    $("#dialog-message").attr("title", "Error al crear fuente");
                                    content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrio un error al intentar agregar la fuente. Intentalo Nuevamente.</p>";
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
                                /*
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                                */
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