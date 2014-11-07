<%@page import="org.banxico.ds.sisal.entities.Grupo"%>
<%@page import="org.banxico.ds.sisal.dao.GruposDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Grupo</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
        <link href="/sisalbm/resources/css/jquery.notice.css" type="text/css" rel="stylesheet"/>
        <link href="../../resources/css/simplegrid.css" type="text/css" rel="stylesheet" />
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
            .gridcontent {
                background: #FFF;
                //border: 1px solid #000;
            }
            .scrollable {
                height: 600px;
                overflow-y: scroll;
            }
        </style>
    </head>
    <%
        String group = request.getParameter("id");
        int idgrupo = Integer.parseInt(group);
    %>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="../../resources/images/app_header.png" alt="BMLogo"/></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Edición de Grupo</div>
                        <br />
                        <div id="content">
                            <% Grupo g = ((GruposDAO) session.getAttribute("gruposdao")).obtenerGrupoPorId(idgrupo);%>
                            <input type="hidden" name="idoculto" value="<%= g.getIdGrupo()%>" id="idoculto" />
                            <input type="hidden" name="nombreoculto" value="<%= g.getNombre()%>" id="nombreoculto" />
                            <input type="hidden" name="categoculto" value="<%= g.getCategoria()%>" id="categoculto" />
                            <input type="hidden" name="reportaoculto" value="<%= g.getReporta() %>" id="reportaoculto" />
                            <input type="hidden" name="correooculto" value="<%= g.getCorreo() %>" id="correooculto" />
                            <form class="form" id="editarGrupo" name="editarGrupo">
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
                                                           value="<%= g.getNombre()%>"
                                                           title="El nombre se utilizará para identificar al grupo." />
                                                </td>
                                                <td>
                                                    <label id="nombrewrn" for="nombre" class="error"></label>
                                                </td>
                                            </tr>
                                            <tr id="cat-existente">
                                                <td>
                                                    <label>Categoría</label>
                                                </td>
                                                <td>
                                                    <input type="text" name="categoria" id="categoria" style="width: 185px"
                                                           value="<%= g.getCategoria()%>" />
                                                </td>
                                                <td>
                                                    <label for="categoria" class="error"></label>
                                                </td>
                                            </tr>
                                            <!-- TEST -->
                                            <tr>
                                                <td>
                                                    <label>Se reporta:</label>
                                                </td>
                                                <td>
                                                    <select id="reportable" name="reportable" style="width: 185px">
                                                        <option value="-">Seleccionar opción</option>
                                                        <option value="0">No</option>
                                                        <option value="1">Sí</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <label for="reportable" class="error"></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Correo del grupo</label>
                                                </td>
                                                <td>
                                                    <input type="text" name="correogrupo" id="correogrupo" style="width: 185px"
                                                           value="<%= g.getCorreo() %>"/>
                                                </td>
                                                <td>
                                                    <label for="correogrupo" class="error"></label>
                                                </td>
                                            </tr>
                                            <!-- TEST -->
                                        </tbody>
                                    </table>
                                </fieldset>
                            </form>
                            <div class="grid grid-pad">
                                <div class="col-1-3 mobile-col-1-3">
                                    <div class="subtitle">Agregar productos a la lista</div>
                                    <br />
                                    <form id="swform">
                                        <div class="gridcontent">
                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td>Buscar Producto</td>
                                                        <td>
                                                            <input type="text" name="producto" id="producto" style="width: 185px" />
                                                        </td>
                                                        <td>
                                                            <input type="button" value="Añadir" id="addtolist" 
                                                                   accept=""title="Se deben ingresar al menos 3 caracteres para desplegar la ayuda."/>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </form>
                                </div>
                                <div class="col-1-2 mobile-col-1-2 scrollable">
                                    <div class="subtitle">Software del Grupo</div>
                                    <br />
                                    <div class="gridcontent">
                                        <ul id="listElements" class="listaSeleccionados"></ul>
                                    </div>
                                </div>
                            </div>
                            <input type="submit" class="inputsubmit" value="Guardar Cambios" id="addButton" />
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog-message"></div>
            <div id="dialog-nomodificado"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="/sisalbm/resources/js/jquery.notice.js"></script>
        <script>
            $(document).ready(function() {
                //$("#categoria").load("/sisalbm/autocomplete?action=getcats");
                //$("#selectedList").hide();
                $idoculto = $("#idoculto").val();
                $nombreoculto = $("#nombreoculto").val();
                $categoculto = $("#categoculto").val();
                var reportaoculto = $("#reportaoculto").val();
                var correooculto = $("#correooculto").val();
                //alert(reportaoculto);
                $("#reportable").val(reportaoculto);
                var array = [];
                var group_modified = false;
                $.ajax({
                    type: 'GET',
                    url: '/sisalbm/admin/configuration.controller?action=getSWengpo&gid=' + $idoculto,
                    cache: false,
                    contentType: 'application/json; charset=utf-8',
                    beforeSend: function() {
                        jQuery.noticeAdd({
                            text: "Cargando software del grupo:" + //+ tk[1] + + 
                                    "<br /><center><img src='../../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                            stay: false,
                            type: 'info'
                        });
                    }, success: function(response) {
                        var mydata = jQuery.parseJSON(response);
                        for (i = 0; i < mydata.length; i++) {
                            array.push(mydata[i].id);
                            var lista = $("#listElements").html();
                            var nombre = "nombreS";
                            lista += "<li data-value='" + (i) + "' data-key='" + mydata[i].id + "'>";
                            lista += "<i id='icon-minus' class='icon-minus-sign'><img src='../../resources/images/remove.jpg' alt='#' class='li-img' /></i>";
                            lista += "<label class='itemvalue'>" + mydata[i].nombre + "</label><br /><br />";
                            lista += "</li>";
                            $("#listElements").html(lista);
                        }
                    }
                    , error: function(response) {
                        alert("Ocurrio un error al procesar la solicitud . . . ");
                    }
                });
                $("#addtolist").attr("disabled", true);
                var prod = $("#producto");
                var prodval = $("#producto").val();
                var prodid = 0;
                var flag = false;
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
                    for (i = 0; i < array.length; i++) {
                        //SI elemento i es igual al que se quiere guardar, cambiar bandera
                        if (array[i] === prodid) {
                            flag = true;
                            break;
                        }
                    }
                    //No existe el elemento
                    if (!flag) {
                        group_modified = true;
                        array[array.length] = prodid;
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
                    var $list = $("#listElements"),
                            listValue = $(this).parent().data("value"),
                            listKey = $(this).parent().data("key");
                    var keyindex = array.indexOf(listKey);
                    //alert("listValue: " + listValue + " - listKey: " + listKey);
                    $list.find('li[data-value="' + listValue + '"]').slideUp("fast", function() {
                        $(this).remove();
                        group_modified = true;
                        if (keyindex > -1) {
                            array.splice(keyindex, 1);
                        }
                    });
                });

                $("#addButton").on("click", function() {
                    var valSel = $("#reportable").val();
                    var correogrupo = $("#correogrupo").val();
                    if(valSel !== reportaoculto || correogrupo !== correooculto) {
                        group_modified = true;
                    }
                    if (group_modified) {
                        var $nuevonombre = $("#nombre").val();
                        var $nuevacat = $("#categoria").val();
                        var reportable = $("#reportable").val();
                        var correogrupo = $("#correogrupo").val();
                        var jkeys = JSON.stringify(array);
                        var sdata = "id=" + $idoculto + "&nombre=" + $nuevonombre + "&categoria=" + $nuevacat 
                                + "&reportable=" + reportable + "&correogrupo=" + correogrupo + "&productos=";
                        //alert(sdata + jkeys);
                        $.ajax({
                            type: "POST",
                            url: "/sisalbm/admin/configuration.controller?action=editGroup",
                            data: sdata + jkeys,
                            cache: false,
                            beforeSend: function() {
                                jQuery.noticeAdd({
                                    text: "Procesando la petición:" + //+ tk[1] + + 
                                            "<br /><center><img src='../../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                                    stay: false,
                                    type: 'info'
                                });
                            }, success: function(response) {
                                if (response === "OK") {
                                    $("#dialog-message").attr("title", "Grupo Modificado");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "La información del Grupo fue modificada exitosamente.</p>");
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else if (response === "ERROR") {
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
                                } else if (response === "NOMBRE_INVÁLIDO") {
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
                            }, complete: function(data) {
                                setInterval(function() {
                                    jQuery.noticeRemove($('.notice-item-wrapper'), 400);
                                }, 4000);
                            }
                        });
                        //Procesar formulario
                        //enviar datos del grupo y arreglo de llaves
                    } else {
                        $("#dialog-nomodificado").attr("title", "Grupo no modificado");
                        $("#dialog-nomodificado").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                        "El grupo no fue modificado, no se guardaran los cambios.</p>");
                        $("#dialog-nomodificado").dialog({
                            modal: true,
                            buttons: {
                                Aceptar: function() {
                                    $(this).dialog("close");
                                }
                            }
                        });
                    }
                    return false;
                });
                //alert($categoculto);
                //$("#categoria").val($categoculto);
            });

        </script>
    </body>
</html>