<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Software Soportado</title>
        <link href="../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <link href="../resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script>
            $(document).ready(function(){
                $("#resultsdiv").hide();
                $("#searchkey").val("");
                $("#searchbutton").on("click", function() {
                    var val = $("#searchkey").val();
                    $.ajax({
                        url: '/sisalbm/admin/vulnerability.controller?action=search&type=swsearch',
                        type: 'GET',
                        data: "key=" + val,
                        success: function(result) {
                            $("#content").hide();
                            $("#resultsdiv").show();
                            if (result === '') {
                                var notResult = "<tr><td colspan='4' style='text-align:center'>No se encontraron resultados para el criterio: " + val + "</td></tr>";
                                $("#resultbody").html(notResult);
                                $("#dialog-message").attr("title", "Software No Encontrada");
                                var content = "<p><span class='ui-icon ui-icon-circle-close' style='float:left; margin:0 7px 50px 0;'></span>" +
                                        "No se encontro el software.</p>";
                                $("#dialog-message").html(content);
                                $("#dialog-message").dialog({
                                    modal: true,
                                    buttons: {
                                        Ok: function() {
                                            $(this).dialog("close");
                                        }
                                    }
                                });
                            } else {
                                $("#resultbody").html(result);
                            }

                        }
                    });
                });
                $("#closesearch").on("click", function() {
                    $("#content").show();
                    $("#resultsdiv").hide();
                    $("#searchkey").val("");
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
                        <div id="page_title">Software Soportado</div>
                        <br />
                        <div class="searchdiv">
                            <form class="searchform">
                                <input id="searchkey" class="searchinput" type="text" placeholder="Nombre del SW de Grupo" />
                                <input id="searchbutton" class="searchbutton" type="button" value="Buscar" />
                            </form>
                        </div>
                        <br />
                        <br />
                        <br />
                        <div id="resultsdiv">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" cellspacing="5" id="tablestyle">
                                    <thead>
                                        <tr>
                                            <th>Fabricante</th>
                                            <th>Software</th>
                                            <th>Versión</th>
                                            <th>Grupo</th>
                                        </tr>
                                    </thead>
                                    <tbody id="resultbody">
                                    </tbody>
                                </table>
                                <br />
                                <input id="closesearch" class="" type="button" value="Terminar Búsqueda" />
                                <br />
                            </div>
                        </div>
                        <div id="content">
                            <div class="datagrid">
                                <table border="1" cellpadding="5" id="tablestyle">
                                    <thead>
                                    <th>Fabricante</th>
                                    <th>Software</th>
                                    <th>Version</th>
                                    <!--<th>Tipo</th>-->
                                    <th>Grupo</th>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="supSW" items="${swList}">
                                            <tr>
                                                <td style="width: 150px">${supSW.fabricante}</td>
                                                <td style="width: 240px">${supSW.nombre}</td>
                                                <c:choose>
                                                    <c:when test="${supSW.version eq '-1'}">
                                                        <td style="width: 80px; text-align: center">ND</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td style="width: 80px; text-align: center">${supSW.version}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <%--
                                                <c:choose>
                                                    <c:when test="${supSW.tipo eq 1}">
                                                        <td style="width: 100px">OS</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td style="width: 100px">SW</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                --%>
                                                <td style="width: 200px">${supSW.UAResponsable}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="pagination">
                                <table style="width: 100%; text-align: center">
                                    <tr>
                                        <c:if test="${currentPage != 1}">
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=1" class="page">Inicio</a></td>
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${currentPage - 1}" class="page">Anterior</a></td>
                                        </c:if>
                                        <c:forEach begin="${currentPage}" end="${currentPage + 9}" var="i">
                                            <c:choose>
                                                <c:when test="${currentPage lt swnoOfPages}">
                                                    <c:choose>
                                                        <c:when test="${currentPage eq i}">
                                                            <td class="page active">${i}</td>
                                                        </c:when>
                                                        <c:when test="${currentpage lt swnoOfPages}">
                                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${i}" class="page">${i}</a></td>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt swnoOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${currentPage + 1}" class="page">Siguiente</a></td>
                                        </c:if>
                                        <c:if test="${currentPage ne swnoOfPages}">
                                            <td><a href="vulnerability.controller?action=view&tipo=3&page=${swnoOfPages}" class="page">Fin</a></td>
                                        </c:if>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>