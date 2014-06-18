<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<div id="menu">
    <nav>
        <ul>
            <li><a href="../AppIndex.html">AppIndex</a></li>
            <li>
                <a href="Index.html">Configuración</a>
                <ul>
                    <li><a href="configuration.controller?tipo=1">Administrar Fuentes</a></li>
                    <li><a href="configuration.controller?tipo=2">Listas de Software</a></li>
                </ul>
            </li>
            <li><a href="#">Vulnerabilidades</a>
                <ul>
                    <li><a href="vulnerability.controller?tipo=1">Más Recientes</a></li>
                    <li><a href="vulnerability.controller?tipo=2">Archivo</a></li>
                    <li><a href="vulnerability.controller?tipo=3">Software Soportado</a></li>
                </ul>
            </li>
            <li>
                <a href="#">Escaneo</a>
                <ul>
                    <li><a href="#">Nuevo Escaneo</a></li>
                </ul>
            </li>
            <li>
                <a href="#">Reportes</a>
                <ul>
                    <li><a href="#">Generar Reporte</a></li>
                </ul>
            </li>
        </ul>
    </nav>
    <!--
    <table>
        <tr>
            <td align="center" valign="top" width="15%"><a href="admin/Index.html">Página Administrativa</a></td>
            <td align="center" valign="top" width="15%"><a href="consulta/Index.html">Página de Consulta</a></td>
        </tr>
    </table>
    -->
</div>