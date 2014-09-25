<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Nuevo Grupo</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />
        <style type="text/css">
            .listaSeleccionados {
                max-width: 600px;
            }
            .listaSeleccionados .itemvalue {
                width: 400px;
            }
            .listaSeleccionados .li-img {
                width: 20px; 
                height: 20px;
                float: left;
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
                <!--<div id="title">&nbsp;Versión Adminstrativa</div>-->
                <div id="workarea">
                    <%@include file="../incfiles/menu.jsp" %>

                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Agregar Nuevo Grupo</div>
                        <br />
                        <div id="content">
                            <form class="form" id="addGroupSW" name="addGroupSW" action="" method="get">
                                <fieldset>
                                    <legend>Información del Grupo</legend>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <label>Nombre del Grupo</label>
                                                </td>
                                                <td>
                                                    <input type="text" name="nombre" id="nombre" style="width: 185px" 
                                                           title="El nombre se utilizará para identificar al grupo."/>
                                                </td>
                                                <td><label id="nombrewrn" for="nombre" class="error"></label></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Seleccionar Categoría</label>
                                                </td>
                                                <td>
                                                    <input type="radio" name="tipo" id="tipo" value="existente" />Existente
                                                    <br />
                                                    <input type="radio" name="tipo" id="tipo" value="nueva" />Nueva
                                                    <br />
                                                </td>
                                                <td><label for="tipo" class="error"></label></td>
                                            </tr>
                                            <tr id="cat-existente">
                                                <td><label>Categoría</label></td>
                                                <td>
                                                    <select id="categoria" name="categoria" style="width: 195px"></select>
                                                </td>
                                                <td><label for="categoria" class="error"></label></td>
                                            </tr>
                                            <tr id="cat-nueva">
                                                <td><label>Categoría</label></td>
                                                <td>
                                                    <input type="text" name="nuevacat" id="nuevacat" style=" width: 185px;" />
                                                </td>
                                                <td><label for="nuevacat" class="error"></label></td>
                                            </tr>
                                            <tr>
                                                <td>Buscar Producto</td>
                                                <td>
                                                    <input type="text" name="producto" id="producto" style="width: 185px" />
                                                </td>
                                                <td>
                                                    <input type="button" value="Añadir" id="addtolist" 
                                                           title="Se deben ingresar al menos 3 caracteres para desplegar la ayuda."/>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </fieldset>
                                <fieldset id="selectedList">
                                    <legend title="En está sección se agregarán los elementos seleccionados">Productos Seleccionados</legend>
                                    <ul id="listElements" class="listaSeleccionados"></ul>
                                </fieldset>
                                <input type="submit" class="inputsubmit" value="Agregar" id="addButton" />
                                <br  />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog-message"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script> 
        <script type="text/javascript">
            $(function() {
                $(document).tooltip();
            });
            $(document).ready(function() {
                $("#categoria").load("/sisalbm/autocomplete?action=getcats");
                $("#dialog-message").hide();
                $("#selectedList").hide();
                $("#cat-existente").hide();
                $("#cat-nueva").hide();
                var prod = $("#producto");
                var prodval = $("#producto").val();
                var prodid = 0;
                var ikeys = [];
                var tempdiv = $("#tempdiv");
                var flag = false;
                var i = 0;
                $("#addtolist").attr("disabled", true);
                $("input[name=tipo]").on("click", function() {
                    var tipo = $("input:radio[name=tipo]:checked").val();
                    if (tipo === 'nueva') {
                        $("#cat-nueva").show();
                        $("#cat-existente").hide();
                    } else if (tipo === 'existente') {
                        $("#cat-existente").show();
                        $("#cat-nueva").hide();
                    }
                });
                $.get("/sisalbm/autocomplete?action=acproduct&prodq=" + prodval, function(data) {
                    var items = data.split("\n");
                    prod.autocomplete({
                        source: items,
                        minLength: 3,
                        select: function(event, ui) {
                            $.get("/sisalbm/autocomplete?action=getProdId&prodName=" + ui.item.value, function(ret) {
                                prodid = Number(ret);
                                $("#addtolist").attr("disabled", false);
                            });
                        }
                    });
                });
                $("#addtolist").click(function() {
                    //Mostrar la lista de elementos
                    $("#selectedList").show();
                    //Iterar el arreglo buscando los elementos ya existentes
                    for (i = 0; i < ikeys.length; i++) {
                        //SI elemento i es igual al que se quiere guardar, cambiar bandera
                        if (ikeys[i] === prodid) {
                            flag = true;
                            break;
                        }
                    }
                    //No existe el elemento
                    if (!flag) {
                        ikeys[ikeys.length] = prodid;
                        var listElements = $("#listElements").html();
                        var selected = $("#producto").val();
                        listElements += "<li data-value='" + (i++) + "' data-key='" + prodid + "'>";
                        listElements += "<i id='icon-minus' class='icon-minus-sign'><img src='../../resources/images/remove.jpg' alt='#' class='li-img'/></i>";
                        listElements += "<label class='itemvalue'>" + selected + "</label><br/><br/>";
                        listElements += "</li>";
                        $("#listElements").html(listElements);
                    } else if (flag) {
                        //El elemento ya existe
                        $("#dialog-message").attr("title", "Elemento Duplicado");
                        var content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                "El producto seleccionado ya se encuentra en la lista.</p>";
                        $("#dialog-message").html(content);
                        $("#dialog-message").dialog({
                            modal: true,
                            buttons: {
                                Ok: function() {
                                    $(this).dialog("close");
                                }
                            }
                        });
                    }
                    flag = false;
                    prodid = 0;
                    $("#producto").val("");
                    $("#addtolist").attr("disabled", true);
                });
                $(document).on("click", "#icon-minus", function() {
                    var $list = $("#listElements"), listValue = $(this).parent().data("value"), listKey = $(this).parent().data("key");
                    var keyindex = ikeys.indexOf(listKey);
                    //alert(listKey + "/" + keyindex);
                    $list.find('li[data-value="' + listValue + '"]').slideUp("fast", function() {
                        $(this).remove();
                        if (keyindex > -1) {
                            ikeys.splice(keyindex, 1);
                        }
                    });
                });
                /*
                 * VALIDAR EL NOMBRE DEL GRUPO INGRESADO 
                 */
                var $nombrewrn = $("#nombrewrn");
                $("#nombre").focusout(function() {
                    var q = this;
                    $.ajax({
                        url: '/sisalbm/autocomplete?action=validarNombreGrupo',
                        type: 'POST',
                        data: 'nombregrupo=' + q.value,
                        success: function(result) {
                            if (result === 'INVALIDO') {
                                $nombrewrn.text("Ya existe un grupo con esté nombre.");
                                $nombrewrn.show();
                            }
                        }
                    });
                });
                //Fin validar nombre
                $.validator.addMethod("valCategoria", function(value) {
                    return (value !== '0')
                }, "Seleccionar una categoría");
                $("#addGroupSW").validate({
                    rules: {
                        nombre: "required",
                        tipo: "required",
                        nuevacat: "required",
                        categoria: {
                            valCategoria: true
                        }
                    },
                    messages: {
                        nombre: "Ingresar el nombre del grupo",
                        tipo: "Seleccionar un tipo de categoría",
                        nuevacat: "Ingresar una categoría"
                    },
                    submitHandler: function(form) {
                        $("#producto").val("");
                        var tipo = $("input:radio[name=tipo]:checked").val();
                        if (tipo === 'nueva') {
                            $("#categoria").val("");
                        } else if (tipo === 'existente') {
                            $("#nuevacat").val("");
                        }

                        if (ikeys.length > 0) {
                            var formserialized = $(form).serialize();
                            var jkeys = JSON.stringify(ikeys);
                            var sdata = formserialized + jkeys;
                            //alert(sdata);
                        } else {
                            $("#dialog-message").attr("title", "Lista Incompleta");
                            var content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "La lista debe contener al menos un elemento seleccionado</p>";
                            $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "La lista debe contener al menos un elemento seleccionado</p>");
                            $("#dialog-message").dialog({
                                modal: true,
                                buttons: {
                                    Ok: function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });
                            return false;
                        }
                        //url: "/sisalbm/autocomplete?action=test",
                        $.ajax({
                            url: "/sisalbm/admin/configuration.controller?action=addGroup",
                            type: "POST",
                            data: sdata,
                            success: function(response) {
                                var content = '';
                                if (response === 'OK') {
                                    $("#dialog-message").attr("title", "Grupo Agregado");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "El grupo fue agregado exitosamente.</p>");
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                    ikeys = [];
                                    $("#addGroupSW")[0].reset();
                                    $("#listElements").html("");
                                    //$("#dialog-message").hide();
                                    $("#selectedList").hide();
                                    $("#cat-existente").hide();
                                    $("#cat-nueva").hide();
                                } else if (response === 'ERROR') {
                                    $("#dialog-message").attr("title", "Error al agregar el grupo");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrio un error al intentar registrar el grupo.</p>");
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else if (response === 'NOMBRE_INVALIDO') {
                                    $("#dialog-message").attr("title", "Nombre de Grupo Existente");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrio un error al intentar registrar el grupo. Ya existe un grupo con el nombre seleccionado</p>");
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $("#dialog-message").attr("title", "");
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else {
                                    $("#dialog-message").attr("title", "Error al agregar el grupo");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrio un error desconocido al intentar registrar el grupo. Intentarlo nuevamente.</p>");
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                }
                            }, error: function() {
                                $("#dialog-message").attr("title", "Petición Incompleta");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                        "Ocurrio un error al realizar la petición al servidor. Intentelo nuevamente.</p>");
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