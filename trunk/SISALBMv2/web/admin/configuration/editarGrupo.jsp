<%-- 
    JSP que muestra un formulario para editar la información de un grupo, se puede editar
    el nombre, la categoria, la bandera que indica si el grupo es reportado, el correo del grupo
    así como el software que contiene el grupo
--%>
<%@page import="org.banxico.ds.sisal.entities.Grupo"%>
<%@page import="org.banxico.ds.sisal.dao.GruposDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar Grupo</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
        <link href="/sisalbm/resources/css/jquery.notice.css" type="text/css" rel="stylesheet"/>
        <link href="../../resources/css/simplegrid.css" type="text/css" rel="stylesheet" />
        <style type="text/css">
            /*
                Estilo que se usa para la lista de software mostrado
            */
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
            .ui-autocomplete {
                font-size: 90%;
                max-width: 400px;
                max-height: 400px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
                overflow-x: hidden;
            }
            html .ui-autocomplete {
                height: 100px;
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
                            <input type="hidden" name="reportaoculto" value="<%= g.getReporta()%>" id="reportaoculto" />
                            <input type="hidden" name="correooculto" value="<%= g.getCorreo()%>" id="correooculto" />
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
                                                           value="<%= g.getCorreo()%>"/>
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
                            <input type="submit" class="inputsubmit" value="Guardar Cambios" id="editButton" />
                        </div>
                    </div>
                </div>
            </div>
            <div id="dialog-message"></div>
            <div id="dialog-nomodificado"></div>
            <div id="dialog-error"></div>
        </div>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="/sisalbm/resources/js/jquery.notice.js"></script>
        <script>
            $(document).ready(function() {
                //$("#categoria").load("/sisalbm/autocomplete?action=getcats");
                //$("#selectedList").hide();
                //Se obtiene la información de los grupos de los campos ocultos establecidos
                var $idoculto = $("#idoculto").val();
                var $nombreoculto = $("#nombreoculto").val();
                var $categoculto = $("#categoculto").val();
                var reportaoculto = $("#reportaoculto").val();
                var correooculto = $("#correooculto").val();
                //Se establece el valor del select de la bandera se reporta a partir del valor del campo oculto
                $("#reportable").val(reportaoculto);
                //Se inicia un arreglo para almacenar las llaves de los productos de software
                var array = [];
                //Bandera que indica si el grupo ya fue modificado o no
                var group_modified = false;
                /**
                 * Función que se encarga de obtener el software registrado en el grupo mediante un JSON
                 * obtenido del servidor
                 */
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
                        //Se obtiene la cadena del servidor
                        var mydata = jQuery.parseJSON(response);
                        for (i = 0; i < mydata.length; i++) {
                            //cada elemento obtenido se va insertando en el arreglo de llaves
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
                        $("#dialog-error").attr("title", "Error al procesar la solicitud");
                        $("#dialog-error").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                "Ocurrió un error al obtener la información del software perteneciente al grupo.</p>");
                        $("#dialog-error").dialog({
                            modal: true,
                            buttons: {
                                Aceptar: function() {
                                    $(this).dialog("close");
                                }
                            }
                        });
                    }
                });
                $("#addtolist").attr("disabled", true);
                //Variables relacionadas con un software que se peude agregar a la lista de software
                var prod = $("#producto");
                var prodval = $("#producto").val();
                var prodid = 0;
                var flag = false;
                /*
                 * Función que se encarga de realizar un autocompletado a partir del nombre del software que
                 * ingresa el usuario
                 */
                $.get("/sisalbm/autocomplete?action=acproduct&prodq=" + prodval, function(data) {
                    data = data.trim();
                    //Se dividen los elementos por un salto de linea
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
                /**
                 * Función que se encarga de agregar un elemento seleccionado a la
                 * lista de software del grupo
                 */
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
                    //En este caso el elemento no existe dentro del ya registrado
                    if (!flag) {
                        //En este caso el grupo si se modifica y se cambia la bandera
                        group_modified = true;
                        array[array.length] = prodid;
                        var listElements = $("#listElements").html();
                        //Codigo que se genera y se haceun append al codigo html existente de la lista
                        var selected = $("#producto").val();
                        listElements += "<li data-value='" + (i++) + "' data-key='" + prodid + "'>";
                        listElements += "<i id='icon-minus' class='icon-minus-sign'><img src='../../resources/images/remove.jpg' alt='#' class='li-img'/></i>";
                        listElements += "<label class='itemvalue'>" + selected + "</label><br/><br/>";
                        listElements += "</li>";
                        $("#listElements").html(listElements);
                    } else if (flag) {
                        //El elemento ya existe dentro del software registrado
                        $("#dialog-message").attr("title", "Elemento Duplicado");
                        $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                "El producto seleccionado ya se encuentra en la lista.</p>");
                        $("#dialog-message").dialog({
                            modal: true,
                            buttons: {
                                Aceptar: function() {
                                    $(this).dialog("close");
                                }
                            }
                        });
                    }
                    //Se reinician los valores y se deshabilita el boton de agregar
                    flag = false;
                    prodid = 0;
                    $("#producto").val("");
                    $("#addtolist").attr("disabled", true);
                });
                /**
                 * Función que se encarga de eliminar un elemento de la lista de software
                 */
                $(document).on("click", "#icon-minus", function() {
                    var $list = $("#listElements"),
                            listValue = $(this).parent().data("value"),
                            listKey = $(this).parent().data("key");
                    //Se obtiene el indice donde se encuentra la llave del elemento que se quiere eliminar
                    var keyindex = array.indexOf(listKey);
                    //alert("listValue: " + listValue + " - listKey: " + listKey);
                    $list.find('li[data-value="' + listValue + '"]').slideUp("fast", function() {
                        //Cuando se encuentra el elemento se elimina de la lista y se indica que el grupo ya ha sido modificado
                        $(this).remove();
                        group_modified = true;
                        if (keyindex > -1) {
                            array.splice(keyindex, 1);
                        }
                    });
                });
                /**
                 * 
                 */
                $("#editButton").on("click", function() {
                    //Obtener los valores de reportable y el correo del grupo
                    var valSel = $("#reportable").val();
                    var correogrupo = $("#correogrupo").val();
                    //Si los valores cambiaron respecto al valor oculto, el grupo fue modificado y cambia la bandera
                    if (valSel !== reportaoculto || correogrupo !== correooculto) {
                        group_modified = true;
                    }
                    //Si el grupo se modifico procesar el formulario
                    if (group_modified) {
                        //Obtener los valores del formulario
                        var $nuevonombre = $("#nombre").val();
                        var $nuevacat = $("#categoria").val();
                        var reportable = $("#reportable").val();
                        var correogrupo = $("#correogrupo").val();
                        //Convertir el valor del arreglo a cadena
                        var jkeys = JSON.stringify(array);
                        //Generar parametros que se enviaran al formulario
                        var sdata = "id=" + $idoculto + "&nombre=" + $nuevonombre + "&categoria=" + $nuevacat
                                + "&reportable=" + reportable + "&correogrupo=" + correogrupo + "&productos=";
                        //alert(sdata + jkeys);
                        //Se inicia la petición ajax al servidor
                        $.ajax({
                            type: "POST",
                            url: "/sisalbm/admin/configuration.controller?action=editGroup",
                            data: sdata + jkeys,
                            cache: false,
                            beforeSend: function() {
                                //notificar al usuario que se esta realizando una petición
                                jQuery.noticeAdd({
                                    text: "Procesando la petición:" + //+ tk[1] + + 
                                            "<br /><center><img src='../../resources/images/ajax-loader.gif' alt='Imagen' /></center>",
                                    stay: false,
                                    type: 'info'
                                });
                            }, success: function(response) {
                                response = response.trim();
                                //A partir de la respuesta del servidor mostrar un mensaje de alerta
                                if (response === "OK") {
                                    //Petición correcta
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
                                    //Ocurrio un error al realizar la petición
                                    $("#dialog-message").attr("title", "Error al agregar el grupo");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrió un error al intentar registrar el grupo.</p>");
                                    $("#dialog-message").dialog({
                                        modal: true,
                                        buttons: {
                                            Aceptar: function() {
                                                $(this).dialog("close");
                                            }
                                        }
                                    });
                                } else if (response === "NOMBRE_INVALIDO") {
                                    //el nombre ingresado no es válido
                                    $("#dialog-message").attr("title", "Nombre de Grupo Existente");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrió un error al intentar registrar el grupo. Ya existe un grupo con el nombre seleccionado</p>");
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
                                    //Error no conocido
                                    $("#dialog-message").attr("title", "Error al agregar el grupo");
                                    $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                            "Ocurrió un error desconocido al intentar registrar el grupo. Intentarlo nuevamente.</p>");
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
                                //Error al completar la petición hacia el servidor
                                $("#dialog-message").attr("title", "Petición Incompleta");
                                $("#dialog-message").html("<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                        "Ocurrió un error al realizar la petición al servidor. Inténtelo nuevamente.</p>");
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Aceptar: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            }, complete: function(data) {
                                //eliminar la notificación al completar la petición hacia el servidor
                                setInterval(function() {
                                    jQuery.noticeRemove($('.notice-item-wrapper'), 400);
                                }, 4000);
                            }
                        });
                        //Procesar formulario
                        //enviar datos del grupo y arreglo de llaves
                    } else { //Grupo no modificado
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
                });//Procesamiento del formulario
            });
        </script>
    </body>
</html>