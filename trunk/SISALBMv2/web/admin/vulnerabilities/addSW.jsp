<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Software</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../../resources/css/menu.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jquery.validate.js" ></script>
        <style type="text/css">
        </style>
        <script>
            $(document).ready(function() {
                $("#dialog-message").hide();
                $("#fabricante").load("/sisalbm/scanner?action=retrieve&val=vendor&vendor=0");
                $.validator.addMethod("valueNotEquals", function(value) {
                    return (value !== '0');
                }, "Seleccionar un fabricante");

                $.validator.addMethod("valFab", function(value) {
                    return (value !== '0');
                }, 'Seleccionar un fabricante');
                $.validator.addMethod("valTipo", function(value) {
                    return (value !== '0');
                }, 'Seleccionar un tipo del SW');
                $.validator.addMethod("valEOL", function(value) {
                    return (value !== '0');
                }, 'Seleccionar Fin de Vida');
                $("#addForm").validate({
                    rules: {
                        fabricante: {
                            valFab: true
                        },
                        nombre: "required",
                        version: "required",
                        tipo: {
                            valTipo: true
                        },
                        eol: {
                            valEOL: true
                        }
                    },
                    messages: {
                        nombre: 'Ingresar un nombre de SW',
                        version: 'Ingresar una versión del SW'
                    },
                    submitHandler: function(form) {
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
                                        Ok: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });  
                                $("#addForm")[0].reset();
                            }
                        });
                        return false;
                    }
                });
            });
        </script>        
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
                                            <li><a href="../configuration.controller?action=view&tipo=2"><span>Administrar</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="has-sub"><a href="#"><span>Software</span></a>
                                        <ul>
                                            <li><a href="../vulnerabilities/addSW.jsp"><span>Agregar Software</span></a></li>
                                            <li><a href="../vulnerability.controller?action=view&tipo=3"><span>Software Registrado</span></a></li>
                                        </ul>
                                    </li>
                                    <li class="last"><a href="/sisalbm/JobScheduleServlet"><span>Tareas Programadas</span></a></li>
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
                    <!--
                    <div id="menu">
                        <nav>
                            <ul>
                                <li><a href="../../AppIndex.html">AppIndex</a></li>
                                <li>
                                    <a href="#">Configuración</a>
                                    <ul>
                                        <li><a href="../configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                                        <li><a href="../configuration.controller?action=view&tipo=2">Administrar Grupos</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">Vulnerabilidades</a>
                                    <ul>
                                        <li><a href="../vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                                        <li><a href="../vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                                        <li><a href="../vulnerability.controller?action=view&tipo=3">Software Registrado</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="../scanner/scan.jsp">Escaneo</a>
                                </li>
                                <li>
                                    <a href="../help.jsp">Ayuda</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    -->
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Agregar Software</div>
                        <div id="content">
                            <div id="full">
                                <form class="form" id="addForm">
                                    <fieldset>
                                        <legend>Información del Software: </legend>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <label>Seleccionar Fabricante</label>
                                                    </td>
                                                    <td>
                                                        <select name="fabricante" id="fabricante"></select>
                                                    </td>
                                                    <td>
                                                        <label for="fabricante" class="error"></label>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>Nombre </label>
                                                    </td>
                                                    <td>
                                                        <input type="text" id="nombre" name="nombre" style="width: 185px" />
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
                                                        <input type="text" id="version" name="version" style="width: 185px" />
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
                                                        <select name="tipo" id="tipo">
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
                                                        <select name="eol" id="eol">
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
    </body>

</html>