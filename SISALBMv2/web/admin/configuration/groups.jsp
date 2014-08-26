<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Grupos de Software</title>
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="../resources/css/menu.css" type="text/css" rel="stylesheet" /> 
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.notice.js" ></script>
        <link href="../resources/css/jquery.notice.css" type="text/css" rel="stylesheet" />        
        <script>
            $(document).ready(function() {
               $(".view").click(function() {
                   $("#dialog").attr('src', $(this).attr("href"));
                   $("#dialogdiv").dialog({
                       width: 800,
                       height: 800,
                       modal: true,
                       resizable: false,
                       draggable: false,
                       open: function() {
                           $(".ui-widget-overlay").addClass('custom-overlay');
                       }, 
                       close: function() {
                           $("#dialog").attr("src", "about:blank");                       
                       }
                   });
                   return false;
               }); 
            });            
        </script>        
    </head>
    <body>
        <div id="page_container">
            <div id="page_header">
                <table id="header">
                    <tr>
                        <td><img src="../resources/images/app_header.png" alt="BMLogo" /></td>
                    </tr>
                </table>
            </div>
            <div id="page_content">
                <div id="title">&nbsp;Versión Adminstrativa</div>
                <div id="workarea">
                    <%@include  file="../incfiles/menu.jsp" %>
                    <div id="content_wrap">
                        <br />
                        <div id="page_title">Grupos de SW</div>
                        <br />
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" id="tablestyle">
                                    <thead>
                                    <th>Nombre</th>
                                    <th>Categoría</th>
                                    <th>Opciones</th>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="grupo" items="${listaGrupos}">
                                            <tr>
                                                <td style="width: 300px">${grupo.nombre}</td>
                                                <td style="width: 300px">${grupo.categoria}</td>
                                                <td>
                                                    <a href="configuration/detalleGrupo.jsp?action=view&tipo=grupo&id=${grupo.idGrupo}" class="view">
                                                        <img src="../resources/images/search.png" alt="magni" id="tableicon" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!--PAGINATION-->
                            <div class="pagination">
                                <table style="width: 100%; text-align: center">
                                    <tr>
                                        <c:if test="${currentPage != 1}">
                                            <td><a href="configuration.controller?action=view&tipo=2&page=${currentPage - 1}" class="page">Anterior</a></td>
                                        </c:if>
                                        <c:forEach begin="1" end="${grnoOfPages}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage eq i}">
                                                    <td class="page active">${i}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><a href="configuration.controller?action=view&tipo=2&page=${i}" class="page">${i}</a></td>
                                                    </c:otherwise>


                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${currentPage lt grnoOfPages}">
                                            <td><a href="configuration.controller?action=view&tipo=2&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div>
                            <!--PAGINATION-->
                            <!--Dialogo con detalle-->
                            <div id="dialogdiv" title="Detalle del Grupo" style="display: none">
                                <iframe id="dialog" width="750" height="700"></iframe>
                            </div>
                            <!--Dialogo-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>