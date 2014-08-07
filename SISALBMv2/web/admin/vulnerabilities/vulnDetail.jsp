<%@page import="org.banxico.ds.sisal.dao.VulnerabilityDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../resources/css/general.css" type="text/css" rel="stylesheet" /> 
        <!--<script src="//code.jquery.com/jquery-1.10.2.js"></script>-->
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="../../resources/js/jspdf.min.js"></script>
        <script>
            function toPDF(title) {
                var ndesc = $("#desc").html();
                var doc = new jsPDF('p', 'in', 'letter')
                        , sizes = [12]
                        , fonts = [['Times', 'Roman']]
                        , font, size, lines
                        , margin = 0.5 // inches on a 8.5 x 11 inch sheet.
                        , verticalOffset = 3.7
                        , text = '' + ndesc;
                doc.setProperties({
                    title: title,
                    subject: 'Reporte de la Vulnerabilidad: ' + title,
                    author: 'Banxico',
                    keywords: 'cve, ' + title,
                    creator: 't41507'
                });
                doc.setFont('times');
                doc.setTextColor(250, 150, 65);
                doc.setFontSize(16);
                doc.text(0.5, 0.5, title);

                doc.setDrawColor(120, 125, 165);
                doc.setLineWidth(0.1);
                doc.line(0.5, 0.7, 7.5, 0.7); // horizontal line
                doc.setTextColor(0);
                /*
                 doc.setDrawColor(0, 255, 0)
                 .setLineWidth(1 / 72)
                 .line(margin, margin, margin, 11 - margin)
                 .line(8.5 - margin, margin, 8.5 - margin, 11 - margin);
                 */
                doc.setFontSize(12);
                doc.text(0.5, 1.0, "Fecha de Publicación: ");
                var pub = $("#pubdate").html();
                doc.text(3, 1.0, pub);
                var mod = $("#moddate").html();
                doc.text(0.5, 1.5, "Fecha de Modificación: ");
                doc.text(3, 1.5, mod);
                var crit = $("#criticidad").html();
                doc.text(0.5, 2, "Gravedad: ");
                doc.text(3, 2, crit);
                var score = $("#cvsscore").html();
                doc.text(0.5, 2.5, "Calificación CVSS: ");
                doc.text(3, 2.5, score);
                var vector = $("#cvssvector").html();
                doc.text(0.5, 3, "Vector CVSS: ");
                doc.text(3, 3, vector);
                doc.text(0.5, 3.5, "Descripción: ");
                for (var i in fonts) {
                    if (fonts.hasOwnProperty(i)) {
                        font = fonts[i];
                        size = sizes[i];

                        lines = doc.setFont(font[0], font[1])
                                .setFontSize(size)
                                .splitTextToSize(text, 7.5);
                        doc.text(0.5, verticalOffset + size / 72, lines);

                        verticalOffset += (lines.length + 0.5) * size / 72;
                    }
                }
                doc.addPage();
                var refs = $("#hiddenRefs").val();
                var rsRefs = refs.split("¿");
                doc.text(0.5, 1, "Referencias: ");
                //doc.text(0.5, 1.5, refs);
                verticalOffset = 1.2;
                for (var i in fonts) {
                    for (var j in rsRefs) {
                        if (fonts.hasOwnProperty(i)) {
                            font = fonts[i];
                            size = sizes[i];
                            lines = doc.setFont(font[0], font[1])
                                    .setFontSize(size)
                                    .splitTextToSize(rsRefs[j], 7.5);
                            doc.text(0.5, verticalOffset + size / 72, lines);
                            verticalOffset += (lines.length + 0.5) * size / 72;
                        }
                    }
                }
                doc.addPage();
                var vulnsw = $("#hiddenSW").val();
                var res = vulnsw.split("/");
                doc.text(0.5, 1, "Software Vulnerable: ");
                verticalOffset = 1.2;
                for (var i in fonts) {
                    for (var j in res) {
                        if (fonts.hasOwnProperty(i)) {
                            font = fonts[i];
                            size = sizes[i];
                            lines = doc.setFont(font[0], font[1])
                                    .setFontSize(size)
                                    .splitTextToSize(res[j], 7.5);
                            doc.text(0.5, verticalOffset + size / 72, lines);
                            verticalOffset += (lines.length + 0.5) * size / 72;
                        }
                    }
                }
                //doc.text(0.5, 1.5, res);

                //doc.text(20, 20, title);
                //doc.text(20, 20, 'Hello World!');
                //doc.text(20, 30, 'This is client-side Javascript, pumping out a PDF.');
                //doc.addPage();
                //doc.text(20, 20, 'Do you like that');
                doc.save(title + '.pdf');
            }
        </script>
    </head>
    <%
        String name = request.getParameter("name");
        String tipo = request.getParameter("tipo");
        int type = Integer.parseInt(tipo);
    %>
    <body>
        <div class="datagrid" id="dataexport">
            <%
                out.print(((VulnerabilityDAO) session.getAttribute("vulndao")).describirCVE(type, name));
            %>
        </div>
        <div id="res">

        </div>
    </body>
</html>