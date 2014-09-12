<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<div id="cssmenu">
    <ul>
        <!--<li><a href="../AppIndex.html"><span>AppIndex</span></a></li>-->
        <li class="has-sub"><a href="#"><span>Configuración</span></a>
            <ul>
                <li class="has-sub"><a href="#"><span>Fuentes de Vulnerabilidades</span></a>
                    <ul>
                        <li><a href="/sisalbm/admin/configuration/agregarFuente.jsp"><span>Agregar Nueva Fuente</span></a></li>
                        <li><a href="/sisalbm/admin/configuration.controller?action=view&tipo=1"><span>Fuentes Registradas</span></a></li>
                    </ul>
                </li>
                <li class="has-sub"><a href="#"><span>Grupos de Software</span></a>
                    <ul>
                        <li><a href="/sisalbm/admin/configuration/agregarGrupo.jsp"><span>Agregar Nuevo Grupo</span></a></li>
                        <li><a href="/sisalbm/admin/configuration.controller?action=view&tipo=2"><span>Grupos Registrados</span></a></li>
                    </ul>
                </li>
                <li class="has-sub"><a href="#"><span>Software Disponible</span></a>
                    <ul>
                        <li><a href="/sisalbm/admin/vulnerabilities/addSW.jsp"><span>Agregar Nuevo Software</span></a></li>
                        <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=3"><span>Software Registrado</span></a></li>
                    </ul>
                </li>
                <li class="last"><a href="/sisalbm/JobServlet"><span>Tareas Programadas</span></a></li>
            </ul>
        </li>
        <li><a href="#"><span>Vulnerabilidades</span></a>
            <ul>
                <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=1"><span>Más Recientes</span></a></li>
                <li><a href="/sisalbm/admin/vulnerability.controller?action=view&tipo=2"><span>Archivo</span></a></li>
            </ul>
        </li>
        <li><a href="/sisalbm/admin/scanner/scan.jsp"><span>Escaneo</span></a></li>
        <li><a href="/sisalbm/admin/help.jsp"><span>Ayuda</span></a></li>
    </ul>
</div>
<!--
<div id="menu">
    <nav>
        <ul>
            <li><a href="../AppIndex.html">AppIndex</a></li>
            <li>
                <a href="#">Configuración</a>
                <ul>
                    <li><a href="../admin/configuration.controller?action=view&tipo=1">Administrar Fuentes</a></li>
                    <li><a href="../admin/configuration.controller?action=view&tipo=2">Administrar Grupos</a></li>
                </ul>
            </li>
            <li><a href="#">Vulnerabilidades</a>
                <ul>
                    <li><a href="../admin/vulnerability.controller?action=view&tipo=1">Más Recientes</a></li>
                    <li><a href="../admin/vulnerability.controller?action=view&tipo=2">Archivo</a></li>
                    <li><a href="../admin/vulnerability.controller?action=view&tipo=3">Software Registrado</a></li>
                </ul>
            </li>
            <li>
                <a href="../admin/scanner/scan.jsp">Escaneo</a>
            </li>
            <li>
                <a href="../admin/help.jsp">Ayuda</a>
            </li>
        </ul>
    </nav>
</div>
-->