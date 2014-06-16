<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><html>
    <HEAD>
        <TITLE>Directorio Telefonico</TITLE>
        <link rel="STYLESHEET" type="text/css" href="../css/style.css"/>
        <script  type="text/javascript"  LANGUAGE="JavaScript1.2" SRC="../js/f_info_extra.js"></script>
        <script  type="text/javascript"  LANGUAGE="JavaScript">
            function focus(criterios) {
                document.FrontPage_Form1.empleado.focus();
                if (document.FrontPage_Form1.clave.value.length > 0 ||
                        document.FrontPage_Form1.extension.value.length > 0 ||
                        document.FrontPage_Form1.edificio.value.length > 0 ||
                        document.FrontPage_Form1.referencia.value.length > 0 ||
                        document.FrontPage_Form1.piso.value.length > 0 ||
                        document.FrontPage_Form1.dependencia.value.length > 0) {
                    var obj = document.getElementById("DIV_0");
                    if (obj != null)
                        obj.style.display = 'block';
                    eval("document.IMG_0.src = '../images/open.gif'");
                    obj = document.getElementById("DIV_BUSQUEDA");
                    if (obj != null)
                        obj.style.display = 'block';
                    obj = document.getElementById("DIV_BUSQUEDA_A");
                    if (obj != null)
                        obj.style.display = 'none';
                }
                if (criterios == 1) {
                    var obj = document.getElementById("DIV_0");
                    if (obj != null)
                        obj.style.display = 'block';
                    eval("document.IMG_0.src = '../images/open.gif'");
                    obj = document.getElementById("DIV_BUSQUEDA");
                    if (obj != null)
                        obj.style.display = 'none';
                    obj = document.getElementById("DIV_BUSQUEDA_A");
                    if (obj != null)
                        obj.style.display = 'block';
                }
            }
            var img = new Image();
            var img2 = new Image();
            function cambia_busqueda() {
                var img = new Image();
                var img2 = new Image();
                img.src = "../images/close.gif";
                img2.src = "../images/close.gif";
                if (eval("document.IMG_0.src == img.src")) {
                    var obj = document.getElementById("DIV_BUSQUEDA");
                    if (obj != null)
                        obj.style.display = 'none';
                    obj = document.getElementById("DIV_BUSQUEDA_A");
                    if (obj != null)
                        obj.style.display = 'block';
                } else {

                    var obj = document.getElementById("DIV_BUSQUEDA");
                    if (obj != null)
                        obj.style.display = 'block';
                    obj = document.getElementById("DIV_BUSQUEDA_A");
                    if (obj != null)
                        obj.style.display = 'none';
                }
            }
        </script>
    </HEAD>

    <body onload="focus(0);">
        <script type="text/javascript" >
            var IMAGENESX = new Array();
            IMAGENESX[0] = new Image();
            IMAGENESX[1] = new Image();
            IMAGENESX[0].src = "/dirtel_consulta/images/bullet_flecha_02.gif";
            IMAGENESX[1].src = "/dirtel_consulta/images/bullet_flecha_02_giro_90_derecha.gif";
            function menu(forma) {

                var div = document.getElementById('DIV_MENU');
                if (forma == 1) {
                    div.style.display = "block";
                    eval("document.IMG_MENU.src = IMAGENESX[1].src");
                } else {
                    div.style.display = "none";
                    eval("document.IMG_MENU.src = IMAGENESX[0].src");
                }
            }
        </script>
        <div id="p_wrap">
            <div id="wrap">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td colspan="100%"><img src="/dirtel_consulta/images/app_header.png"  border="0" alt="BMLogo"></img></td>
                    </tr>
                </table>
                <div class="column workarea">

                    <div class="module">
                        <div class="title left">&nbsp;Directorio telef&oacute;nico</div>
                        <div class="info">

                            <table width="100%" cellpadding="1" cellspacing="1" border="0">
                                <tr>
                                    <!-- td align="center" valign="top" width="15%"><a href="/dirtel_consulta/directorio/communicator.jsp">Communicator</a></td-->
                                    <td align="center" valign="top" width="15%"><a href="/dirtel_consulta/directorio/busquedaempleado.jsp?empleado=accesodijo">Acceso_edificios</a></td>
                                    <td align="center" valign="top" width="11%"><a href="/dirtel_consulta/entidad/BusquedaEntidad.do">Sucursales</a></td>
                                    <td align="center" valign="top" width="15%"><a href="http://webinterno/dirtelE_consulta/">Entidades externas</a></td>
                                    <td align="center" valign="top" width="15%"><a href="/dirtel_consulta/directorio/preguntasfrecuentes.jsp">Preguntas frecuentes</a></td>

                                    <td align="center" valign="top" width="15%"  onMouseOver="javascript:menu(1)" onMouseOut="javascript:menu(2)">
                                        <img alt="" src="/dirtel_consulta/images/bullet_flecha_02.gif" id="IMG_MENU" name="IMG_MENU" /><a href="javascript:menu(1);">Utilerias</a>
                                        <div style="display:none" id="DIV_MENU" align="left">
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="" src="/dirtel_consulta/images/bullet_point_01.gif" /> <a href="/dirtel_consulta/reporte/BusquedaPermisoReporte.dirtel?loginSSO=yes">Guardar en excel</a><br/>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="" src="/dirtel_consulta/images/bullet_point_01.gif" /> <a href="/dirtel_consulta/directorio/actualizacion_index.jsp">Actualizaciones</a>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <br />
                            <BR/>
                            <center>
                                <!--table width="100%" cellpadding="1" cellspacing="0" border="0">
                                  <tr>
                                    <td align="justify">
                                     Para consultar favor de llenar el campo con los datos conocidos, no tienen que
                                  ser palabras completas. Para mayor velocidad de consulta se recomienda usar
                                  palabras con m·s de 4 caracteres cuando aplique.
                                    </td>
                                  </tr>
                                </table-->
                            </center>
                            <BR/>
                            <br />
                            <center>
                                <form action="BusquedaEmpleado.dirtel" method="post"
                                      onsubmit="return FrontPage_Form1_Validator(this)"
                                      name="FrontPage_Form1">
                                    <input  type="hidden" name="opc" value="null"/>
                                    <table border="0" >
                                        <tr>                
                                            <td >
                                                <input type="text" size="100" name="empleado" class="texto_tabla">
                                                    <a href="javascript:cambia_prin(0);cambia_busqueda()" >
                                                        <img src="../images/close.gif" alt="B˙squeda avanzada" align="middle" border="0" name="IMG_0"></a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <div  id="DIV_0"

                                                      style="display: none"

                                                      >
                                                    <table>
                                                        <tr>
                                                            <td width="81">Clave:</td>
                                                            <td width="87">
                                                                <input type="text"  size="20" maxlength="20" name="clave" class="texto_tabla">
                                                            </td>
                                                            <td width="75">Extensi&oacute;n:</td>
                                                            <td width="130">
                                                                <input type="text" name="extension" size="7" class="texto_tabla">
                                                            </td>
                                                            <td width="61">&nbsp;</td>
                                                            <td width="25">&nbsp;</td>
                                                        </tr>
                                                        <tr>
                                                            <td width="81">Edificio:</td>
                                                            <td colspan="9">
                                                                <input type="text" size="60" name="edificio" class="texto_tabla">
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="61">Piso:</td>
                                                            <td width="15">
                                                                <input type="text" name="piso" size="5" class="texto_tabla">
                                                            </td>
                                                            <td width="81">Referencia:</td>
                                                            <td width="87">
                                                                <input type="text" size="6" name="referencia" class="texto_tabla">
                                                            </td>
                                                            <td width="61">Titulares:</td>
                                                            <td width="25">
                                                                <input type="checkbox" name="titular" value="S" class="texto_tabla">
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="81">Dependencia:</td>
                                                            <td colspan="9">
                                                                <input type="text" size="60" name="dependencia" class="texto_tabla">
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <p>
                                        <input type="submit" value="Buscar" class="texto_tabla" />
                                        &nbsp;
                                        <input type="reset" value="Limpiar" class="texto_tabla" />
                                    </p>
                                    <br /><br /><br />
                                </form>
                            </center>
                            <script  type="text/javascript"  language="JavaScript">



                                function refresh()
                                {
                                    document.FrontPage_Form1.empleado.focus();

                                }




                                function FrontPage_Form1_Validator(theForm)
                                {
                                    if (isFieldBlank(theForm.empleado))
                                    {
                                        if (isFieldBlank(theForm.dependencia))
                                        {
                                            if (isFieldBlank(theForm.referencia))
                                            {
                                                if (isFieldBlank(theForm.clave))
                                                {
                                                    if (isFieldBlank(theForm.extension))
                                                    {
                                                        if (!theForm.titular.checked)
                                                        {
                                                            if (isFieldBlank(!theForm.edificio))
                                                            {
                                                                if (isFieldBlank(!theForm.piso))
                                                                {
                                                                    alert("Debe de indicar por lo menos alg˙n criterio para realizar las busquedas");
                                                                    theForm.empleado.focus();
                                                                    return (false);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (!validaLetras(theForm.empleado.value) ||
                                            !validaLetras(theForm.edificio.value) ||
                                            !validaLetras(theForm.dependencia.value))
                                    {
                                        theForm.empleado.focus();
                                        return (false);
                                    }

                                    //var checkOK = "ABCDEFGHIJKLMN—OPQRSTUVWXYZabcdefghijklmnÒopqrstuvwxyz¸‹‰ƒÔÎÀœˆ÷ˆ \t\r\n\f";
                                    var checkOK = "ABCDEFGHIJKLMN—OPQRSTUVWXYZabcdefghijklmnÒopqrstuvwxyz\u0192\u0160\u0152\u0161\u0153\u0178¿¡¬√ƒ≈∆«»… ÀÃÕŒœ–—“”‘’÷ÿŸ⁄€‹›ﬁﬂ‡·‚„‰ÂÊÁËÈÍÎÏÌÓÔÒÚÛÙıˆ¯˘˙˚¸˝˛0123456789- \t\r\n\f";

                                    var checkStr = theForm.empleado.value;
                                    var allValid = true;
                                    var newStr = "";



                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        if (ch == "¡")
                                            newStr = newStr + "A";
                                        else
                                        if (ch == "⁄")
                                            newStr = newStr + "U";
                                        else
                                        if (ch == "…")
                                            newStr = newStr + "E";
                                        else
                                        if (ch == "Õ")
                                            newStr = newStr + "I";
                                        else
                                        if (ch == "”")
                                            newStr = newStr + "O";
                                        else
                                        if (ch == "·")
                                            newStr = newStr + "a";
                                        else
                                        if (ch == "˙")
                                            newStr = newStr + "u";
                                        else
                                        if (ch == "È")
                                            newStr = newStr + "e";
                                        else
                                        if (ch == "Ì")
                                            newStr = newStr + "i";
                                        else
                                        if (ch == "Û")
                                            newStr = newStr + "o";
                                        else
                                            newStr = newStr + ch;
                                    }
                                    theForm.empleado.value = newStr;
                                    checkStr = theForm.empleado.value;

                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        for (j = 0; j < checkOK.length; j++)
                                            if (ch == checkOK.charAt(j))
                                            {
                                                newStr = newStr + ch;
                                                break;
                                            }
                                        if (j == checkOK.length)
                                        {
                                            allValid = false;
                                            break;
                                        }
                                    }
                                    if (!allValid)
                                    {
                                        alert("Por favor proporcione sÛlo letras y/o espacios en blanco en el campo \"empleado\" .");
                                        theForm.empleado.focus();
                                        return (false);
                                    }

                                    var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\u0192\u0160\u0152\u0161\u0153\u0178¿¡¬√ƒ≈∆«»… ÀÃÕŒœ–—“”‘’÷ÿŸ⁄€‹›ﬁﬂ‡·‚„‰ÂÊÁËÈÍÎÏÌÓÔÒÚÛÙıˆ¯˘˙˚¸˝˛0123456789- \t\r\n\f";
                                    var checkStr = theForm.dependencia.value;
                                    var newStr = "";
                                    var allValid = true;

                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        if (ch == "¡")
                                            newStr = newStr + "A";
                                        else
                                        if (ch == "⁄")
                                            newStr = newStr + "U";
                                        else
                                        if (ch == "…")
                                            newStr = newStr + "E";
                                        else
                                        if (ch == "Õ")
                                            newStr = newStr + "I";
                                        else
                                        if (ch == "”")
                                            newStr = newStr + "O";
                                        else
                                        if (ch == "·")
                                            newStr = newStr + "a";
                                        else
                                        if (ch == "˙")
                                            newStr = newStr + "u";
                                        else
                                        if (ch == "È")
                                            newStr = newStr + "e";
                                        else
                                        if (ch == "Ì")
                                            newStr = newStr + "i";
                                        else
                                        if (ch == "Û")
                                            newStr = newStr + "o";
                                        else
                                            newStr = newStr + ch;
                                    }

                                    theForm.dependencia.value = newStr;
                                    checkStr = theForm.dependencia.value;


                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        for (j = 0; j < checkOK.length; j++)
                                            if (ch == checkOK.charAt(j))
                                                break;
                                        if (j == checkOK.length)
                                        {
                                            allValid = false;
                                            break;
                                        }
                                    }
                                    if (!allValid)
                                    {
                                        alert("Por favor proporcione sÛlo letras, dÌgitos y espacios en blanco en el campo \"Dependencia\" .");
                                        theForm.dependencia.focus();
                                        return (false);
                                    }


                                    var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_l ";
                                    var checkStr = theForm.referencia.value;
                                    var allValid = true;
                                    var newStr = "";
                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        if (ch == "¡")
                                            newStr = newStr + "A";
                                        else
                                        if (ch == "⁄")
                                            newStr = newStr + "U";
                                        else
                                        if (ch == "…")
                                            newStr = newStr + "E";
                                        else
                                        if (ch == "Õ")
                                            newStr = newStr + "I";
                                        else
                                        if (ch == "”")
                                            newStr = newStr + "O";
                                        else
                                        if (ch == "·")
                                            newStr = newStr + "a";
                                        else
                                        if (ch == "˙")
                                            newStr = newStr + "u";
                                        else
                                        if (ch == "È")
                                            newStr = newStr + "e";
                                        else
                                        if (ch == "Ì")
                                            newStr = newStr + "i";
                                        else
                                        if (ch == "Û")
                                            newStr = newStr + "o";
                                        else
                                            newStr = newStr + ch;
                                    }

                                    theForm.referencia.value = newStr;
                                    checkStr = theForm.referencia.value;


                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        for (j = 0; j < checkOK.length; j++)
                                            if (ch == checkOK.charAt(j))
                                                break;
                                        if (j == checkOK.length)
                                        {
                                            allValid = false;
                                            break;
                                        }
                                    }
                                    if (!allValid)
                                    {
                                        alert("Por favor proporcione sÛlo letras y/o dÌgitos en el campo \"Referencia\" .");
                                        theForm.referencia.focus();
                                        return (false);
                                    }


                                    var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-";
                                    var checkStr = theForm.clave.value;
                                    var allValid = true;
                                    var newStr = "";
                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        if (ch == "¡")
                                            newStr = newStr + "A";
                                        else
                                        if (ch == "⁄")
                                            newStr = newStr + "U";
                                        else
                                        if (ch == "…")
                                            newStr = newStr + "E";
                                        else
                                        if (ch == "Õ")
                                            newStr = newStr + "I";
                                        else
                                        if (ch == "”")
                                            newStr = newStr + "O";
                                        else
                                        if (ch == "·")
                                            newStr = newStr + "a";
                                        else
                                        if (ch == "˙")
                                            newStr = newStr + "u";
                                        else
                                        if (ch == "È")
                                            newStr = newStr + "e";
                                        else
                                        if (ch == "Ì")
                                            newStr = newStr + "i";
                                        else
                                        if (ch == "Û")
                                            newStr = newStr + "o";
                                        else
                                            newStr = newStr + ch;
                                    }

                                    theForm.clave.value = newStr;
                                    checkStr = theForm.clave.value;

                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        for (j = 0; j < checkOK.length; j++)
                                            if (ch == checkOK.charAt(j))
                                                break;
                                        if (j == checkOK.length)
                                        {
                                            allValid = false;
                                            break;
                                        }
                                    }
                                    if (!allValid)
                                    {
                                        alert("Por favor proporcione sÛlo letras y/o dÌgitos en el campo \"Clave de Empleado\" .");
                                        theForm.clave.focus();
                                        return (false);
                                    }


                                    var checkOK = "*#0123456789-\t\r\n\f";
                                    var checkStr = theForm.extension.value;
                                    var allValid = true;
                                    var newStr = "";

                                    checkStr = theForm.extension.value;

                                    for (i = 0; i < checkStr.length; i++)
                                    {
                                        ch = checkStr.charAt(i);
                                        for (j = 0; j < checkOK.length; j++)
                                            if (ch == checkOK.charAt(j))
                                                break;
                                        if (j == checkOK.length)
                                        {
                                            allValid = false;
                                            break;
                                        }
                                    }
                                    if (!allValid)
                                    {
                                        alert("Por favor proporcione sÛlo dÌgitos en el campo \"ExtensiÛn\" .");
                                        theForm.extension.focus();
                                        return (false);
                                    }

                                    return (true);
                                }

                                function isFieldBlank(theField) {
                                    if (theField.value == "")
                                        return true;
                                    else
                                        return false;
                                }

                                function validaLetras(cadena) {
                                    var cesp = 0;
                                    var clet = 0;
                                    for (i = 0; i < cadena.length; i++)
                                    {
                                        ch = cadena.charAt(i);
                                        if (ch == ' ')
                                            cesp++;
                                        else
                                            clet++;
                                    }

                                    if (cesp == 0 && clet == 0)
                                        return true;
                                    if (clet == 0)
                                        return true;
                                    if (clet < 2) {
                                        alert("Los criterios de b˙squeda Empleado,Edificio,Dependencia \ndeben tener por lo menos 2 caracteres diferentes\n y al menos una palabra de dos letras");
                                        return false;
                                    }
                                    var dif = 0;
                                    var c = cadena.charAt(0);
                                    for (i = 0; i < cadena.length; i++)
                                    {
                                        ch = cadena.charAt(i);
                                        if (ch != ' ' && ch != c)
                                            dif = 1;
                                    }

                                    if (dif != 1)
                                    {
                                        alert("Los criterios de b˙squeda Empleado,Edificio,Dependencia \ndeben tener por lo menos 2 caracteres diferentes\ny al menos una palabra de dos letras");
                                        return false;
                                    }

                                    var unaPalabra = false;
                                    var letra = 0;
                                    for (var i = 0; i < 20; i++)
                                    {
                                        var palabra = '';
                                        for (; letra < cadena.length; letra++)
                                        {
                                            if (cadena.charAt(letra) == ' ')
                                                break;
                                            palabra = palabra + cadena.charAt(letra);
                                        }
                                        if (palabra.length >= 2)
                                            unaPalabra = true;
                                        letra++;
                                    }
                                    if (unaPalabra)
                                        return true;

                                    alert("Los criterios de b˙squeda Empleado,Edificio,Dependencia \ndeben tener por lo menos 2 caracteres diferentes\ny al menos una palabra de dos letras");
                                    return false;


                                }

                                //-->

                            </script><!--webbot bot="GeneratedScript" endspan -->
                            <!--fin codigo-->
                            <!--</TD>
                            </TR>
                            </TABLE>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </BODY>
</HTML>
