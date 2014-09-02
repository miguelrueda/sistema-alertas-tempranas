<%@page import="org.banxico.ds.sisal.dao.SoftwareDAO"%>
<%@page import="org.banxico.ds.sisal.entities.Software"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Software</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />
    </head>
    <%
        String swid = request.getParameter("id");
        int id = Integer.parseInt(swid);
    %>
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
                    </div><!--css menu-->
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Editar Software</div>
                        <br />
                        <div id="content">
                            <form class="form" id="editSW" name="editSW">
                                <fieldset>
                                    <legend>Información del Software</legend>
                                    <% Software s = ((SoftwareDAO) session.getAttribute("swdao")).obtenerSoftwarePorId(id); %>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <label>Ingresar Fabricante</label>
                                                </td>
                                                <td>
                                                    <input name="fabricante" id="fabricante" type="text" value="<%= s.getFabricante() %>" />
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
                                                    <input name="nombre" id="nombre" type="text" value="<%= s.getNombre() %>"  />
                                                </td>
                                                <td>
                                                    <label for="nombre" class="error"></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Seleccionar Versión</label>
                                                </td>
                                                <td>
                                                    <input name="version" id="version" type="text" value="<%= s.getVersion() %>" />
                                                </td>
                                                <td>
                                                    <label for="version" class="error"></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Seleccionar Tipo</label>
                                                </td>
                                                <td>
                                                    <input name="tipo" id="tipo" type="text" value="<%= s.getTipo() %>" />
                                                </td>
                                                <td>
                                                    <label for="tipo" class="error"></label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Seleccionar Fin de Vida</label>
                                                </td>
                                                <td>
                                                    <input name="eol" id="eol" type="text" value="<%= s.getEndoflife() %>" />
                                                </td>
                                                <td>
                                                    <label for="eol" class="error"></label>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </fieldset>
                                <input type="submit" class="inputsubmit" value="Guardar Cambios" id="addButton" />
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
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script> 
        <script type="text/javascript">
            $(document).ready(function() {
                $("#editSW").validate({
                    rules: {
                        fabricante: "required",
                        nombre: "required",
                        version: "required",
                        tipo: "required",
                        eol: "required"
                    },
                    messages: {
                        fabricante: "Seleccionar el nombre del fabricante",
                        nombre: "Seleccionar producto",
                        version: "Seleccionar versión",
                        tipo: "Seleccionar tipo",
                        eol: "Seleccionar fin de vida"
                    },
                    submitHandler: function(form) {
                        var formserialized = $(form).serialize();
                        alert(formserialized);
                        $.ajax({
                            //cambiar esta url
                            url: '/sisalbm/test?action=editSoftware',
                            type: 'POST',
                            data: formserialized,
                            success: function(response) {
                                var content = '';
                                if (response === 'OK') {
                                    $("#dialog-message").attr("title", "Edición Completa");
                                    content = "<p><span class='ui-icon ui-icon-check' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "La información del Software fue modificada correctamente.</p>";
                                } else if(response === 'ERROR') {
                                    $("#dialog-message").attr("title", "Edición No Completada");
                                    $("#addFuente")[0].reset();
                                    var content = "<p><span class='ui-icon ui-icon-alert' style='float:left;margin:0 7px 50px 0;'></span>" +
                                    "La información del Software no pudo ser modificada.</p>";
                                }
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        OK: function() {
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