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
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script> 
        <script type="text/javascript">
            $(document).ready(function() {
                $("#categoria").load("/sisalbm/autocomplete?action=getcats");
                $("#selectedList").hide();
                var prod = $("#producto");
                var prodval = $("#producto").val();
                var prodid = 0;
                var ikeys = [];
                var tempdiv = $("#tempdiv");
                var flag = false;
                $("#addtolist").attr("disabled", true);
                $.get("/sisalbm/autocomplete?action=acproduct&prodq=" + prodval, function(data) {
                    var items = data.split("\n");
                    prod.autocomplete({
                        source: items,
                        minLength: 3,
                        select: function(event, ui) {
                            $.get("/sisalbm/autocomplete?action=getProdId&prodName=" + ui.item.value, function(ret) {
                                prodid = ret;
                                $("#addtolist").attr("disabled", false);
                                
                                /*
                                if (!flag) {
                                    alert("Agregar elemento");
                                    ikeys[ikeys.length] = ret;
                                } 
                                */
                            });
                        }
                    });
                });
                var i = 0;
                $("#addtolist").click(function() {
                    //Mostrar la lista de elementos
                    $("#selectedList").show();
                    //Iterar el arreglo buscando los elementos ya existentes
                    for(i = 0; i < ikeys.length; i++) {
                        //SI elemento i es igual al que se quiere guardar, cambiar bandera
                        if(ikeys[i] === prodid) {
                            flag = true;
                            break;
                        }
                    }
                    //No existe el elelemtno
                    if (!flag) {
                        alert("Elemento Agregado");
                        ikeys[ikeys.length] = prodid;
                        var listElements = $("#listElements").html();
                        var selected = $("#producto").val();
                        listElements += "<li data-value='" + (i++) + "'>";
                        listElements += "<i id='icon-minus' class='icon-minus-sign'><img src='../../resources/images/remove.jpg' alt='#' class='li-img'/></i>";
                        listElements += "<label class='itemvalue'>" + selected + " (" + prodid + ")</label><br/><br/>";
                        listElements += "</li>";
                        $("#listElements").html(listElements);
                    } else if (flag) {
                        //El elemento ya existe
                        alert("El producto seleccionado ya se encuentra en la lista.");
                    }
                    flag = false;
                    prodid = 0;
                    $("#producto").val("");
                    $("#addtolist").attr("disabled", true);
                });
                $(document).on("click", "#icon-minus", function() {
                    var $list = $("#listElements"), listValue = $(this).parent().data("value");
                    $list.find('li[data-value="' + listValue + '"]').slideUp("fast", function() {
                        $(this).remove();
                    });
                });
               $.validator.addMethod("valCategoria", function(value){
                   return (value !== '0')
               }, "Seleccionar una categoría");
                $("#addGroupSW").validate({
                    rules: {
                        nombre: "required",
                        categoria: {
                            valCategoria: true
                        }
                    }, 
                    messages: {
                        nombre: "Ingresar el nombre del grupo"
                    },
                    submitHandler: function(form) {
                        $("#producto").val("");
                        var formserialized = $(form).serialize();
                        var jkeys = JSON.stringify(ikeys);
                        var sdata = formserialized + jkeys;
                        alert(sdata);
                        $.ajax({
                            url: "/sisalbm/autocomplete?action=test",
                            type: "POST",
                            data: sdata,
                            success: function(response) {
                                alert(response);
                                ikeys = [];
                                $("#addGroupSW")[0].reset();
                                 $("#listElements").html("");
                            }
                        });
                        return false;
                    }
                });
            });
        </script>
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
                <div id="title">&nbsp;Versión Adminstrativa</div>
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
                                            <li><a href="../configuration/agregarGrupo.jsp"><span>Agregar Grupo</span></a></li>
                                            <li><a href="../configuration.controller?action=view&tipo=2"><span>Ver Grupos</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="has-sub"><a href="#"><span>Software</span></a>
                                        <ul>
                                            <li><a href="../vulnerabilities/addSW.jsp"><span>Agregar Software</span></a></li>
                                            <li><a href="../vulnerability.controller?action=view&tipo=3"><span>Software Registrado</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="last"><a href="/sisalbm/JobServlet"><span>Tareas Programadas</span></a></li>
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
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Agregar Grupo</div>
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
                                                    <input type="text" name="nombre" id="nombre" style="width: 185px" />
                                                </td>
                                                <td><label for="nombre" class="error"></label></td>
                                            </tr>
                                            <tr>
                                                <td><label>Categoría</label></td>
                                                <td>
                                                    <select id="categoria" name="categoria" style="width: 195px"></select>
                                                </td>
                                                <td><label for="categoria" class="error"></label></td>
                                            </tr>
                                            <tr>
                                                <td>Buscar Producto</td>
                                                <td>
                                                    <input type="text" name="producto" id="producto" style="width: 185px" />
                                                </td>
                                                <td>
                                                    <input type="button" value="Añadir" id="addtolist" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </fieldset>
                                <fieldset id="selectedList">
                                    <legend>Productos Seleccionados</legend>
                                    <ul id="listElements" class="listaSeleccionados"></ul>
                                </fieldset>
                                <input type="submit" class="inputsubmit" value="Agregar" id="addButton" />
                                <br  />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>