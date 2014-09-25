package org.banxico.ds.sisal.scanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banxico.ds.sisal.dao.SoftwareDAO;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.parser.entidades.CVE;
import org.banxico.ds.sisal.parser.entidades.Version;
import org.banxico.ds.sisal.parser.entidades.VulnSoftware;

/**
 * Clase que se encarga de realizar las operaciones del escaneo y retornar los
 * resultados como una lista de la clase Result
 *
 * @author t41507
 * @version 04072014
 */
public class ScannerBean implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ScannerBean.class.getName());
    /**
     * Atributos principales de la clase
     */
    private SoftwareDAO swdao;
    private VulnerabilityDAO vulndao;
    private List<Result> resultados;
    private int partialType;
    private int vendorType;
    private String dateType;
    private String UA;
    private String vendor;
    private String severity;
    private String edate;
    private String sdate;
    private boolean modificadas = false;

    /**
     * Constructor
     */
    public ScannerBean() {
    }

    /**
     * Setter
     *
     * @param partialType entero con el tipo de escaneo parcial 1para recientes
     * 2 para archivo
     */
    public void setPartialType(int partialType) {
        this.partialType = partialType;
    }

    /**
     * Setter
     *
     * @param UA cadena con la UA a filtrar
     */
    public void setUA(String UA) {
        this.UA = UA;
    }

    /**
     * Setter
     *
     * @param vendor cadena con el fabricante a filtrar
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * Setter
     *
     * @param vendorType entero con el tipo de fabricante 1 unico 2 multiple
     */
    public void setVendorType(int vendorType) {
        this.vendorType = vendorType;
    }

    /**
     * Setter
     *
     * @param severity cadena con la criticdad a filtrar
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * Setter
     *
     * @param dateType cadena con el tipo de fecha total o parcial
     */
    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    /**
     * Setter
     *
     * @param edate cadena con la fecha de inicio
     */
    public void setEdate(String edate) {
        this.edate = edate;
    }

    /**
     * Setter
     *
     * @param sdate cadena con la fecha de fin
     */
    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    /**
     * Getter
     *
     * @return la bandera de fechas modificadas
     */
    public boolean isModificadas() {
        return modificadas;
    }

    /**
     * Setter
     *
     * @param modificadas la bandera de fechas modificadas
     */
    public void setModificadas(boolean modificadas) {
        this.modificadas = modificadas;
    }

    /**
     * Getter
     *
     * @return Lista de filtradaultados de tipo Result
     */
    public List<Result> getResultados() {
        return resultados;
    }

    /**
     * M´étodo que se encarga de filtrar la lista de sw de acuerdo a la UA
     *
     * @param swList lista de software a filtrar
     * @param filtro filtro a aplicar a la lista
     * @return lista de software filtrada
     */
    private List<Software> filtrarListaSW(List<Software> swList, String filtro) {
        //Crear una lista para agregar los scanResults
        List<Software> filtrada = new ArrayList<Software>();
        //Iterar todos los elementos de la lista recibida
        for (Software sw : swList) {
            //Si la UA/Grupo es igual al filtrado agregarlo a la lista
            if (sw.getUAResponsable().equalsIgnoreCase(filtro)) {
                filtrada.add(sw);
            }
            //Otro caso ignorar
        }
        return filtrada;
    }

    /**
     * Método que filtra la lista de sw por fabricante
     *
     * @param swList lista de sw
     * @param vendor filtro a aplicar
     * @return lista de SW filtrada
     */
    private List<Software> filtrarListaPorVendor(List<Software> swList, String vendor) {
        //Crear una lista para almacenar los elementos filtrada
        List<Software> filtrada = new ArrayList<Software>();
        //Iterar toda la lista de elementos recibidos
        for (Software sw : swList) {
            //Si el fabricante del SW es igual al del filtro agregarlo a la lista
            if (sw.getFabricante().equalsIgnoreCase(vendor)) {
                filtrada.add(sw);
                //En caso de que tengan un nombre similar agregarlo a la lista Ej. Apache y Apache Software Foundation
            } else if (sw.getFabricante().toLowerCase().startsWith(vendor.toLowerCase())) {
                filtrada.add(sw);
            }
        }
        return filtrada;
    }

    /**
     * Método que se encarga de filtrar la lista de SW por nivel de criticidad
     *
     * @param vulns lista de vulnerabilidades
     * @param severity filtro de criticidad
     * @return lista de vulnerabilidades filtrada
     */
    private List<CVE> filtrarListaPorCriticidad(List<CVE> vulns, String severity) {
        //Crear una lista para almacenar las vulnerabilidades filtradas
        List<CVE> filtrada = new ArrayList<CVE>();
        //Iterar la lista de elementos recibida
        for (CVE vuln : vulns) {
            //Si la criticidad de la vulnerabilidad es igual a la del filtro agregarla a la lista Otro Caso ignorar
            if (vuln.getSeverity().equalsIgnoreCase(severity)) {
                filtrada.add(vuln);
            }
        }
        return filtrada;
    }

    /**
     * Método que se encarga de filtrar la lista de vulnerabilidades a partir de
     * las fechas ingredas
     *
     * @param vulnerabilidades lista de vulnerabilidades
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de vulnerabilidades filtrada
     */
    private List<CVE> filtrarListaPorFecha(List<CVE> vulnerabilidades, Date inicio, Date fin) {
        //Crear una lista para agregar los elementos filtrada
        List<CVE> filtrada = new ArrayList<CVE>();
        //Conocer el valor de la bandera 'modificadas'
        LOG.log(Level.INFO, "Buscar en modificadas: {0}", modificadas);
        //Si se selecciono buscar en modificadas filtrar por fechas de publicación y modificación
        if (isModificadas()) {
            //Iterar lista de vulnerabilidades
            for (CVE vuln : vulnerabilidades) {
                //Obtener fechas de publicación y modificación de la vulnerabilidad
                Date pub = vuln.getPublished();
                Date mod = vuln.getModified();
                //Comparar con fechas recibidas si estan dentro del rango agregar a la lista
                if ((pub.after(inicio) && pub.before(fin)) || (mod.after(inicio) && mod.before(fin))) {
                    filtrada.add(vuln);
                }
            }
            //Otro caso buscar solo en fechas de publicación - Valor por defecto
        } else {
            //Iterar lista de vulnerabilidades
            for (CVE vuln : vulnerabilidades) {
                //Obtener fecha de publicación de la vulnerabilidad
                Date pub = vuln.getPublished();
                //Si la fecha esta dentro del rango agregar a la lista
                if (pub.after(inicio) && pub.before(fin)) {
                    filtrada.add(vuln);
                }
            }
        }
        return filtrada;
    }

    /**
     * Método que realiza el escaneo más simple: Todas las vulnerabilidades
     * contra todo el Software
     *
     * @return Conjunto de tipo Result con los filtradaultados del escaneo
     */
    public Set<Result> doCompleteScan() {
        //Iniciar DAOs de vulnerabilidades y de Software
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        //Crear un conjunto para recibir los scanResults, llamar al metodo doScan()
        Set<Result> totales = doScan(vulndao.obtenerListaArchivo(), swdao.obtenerListadeSoftware());
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades en el escaneo completo.", totales.size());
        return totales;
    }
    
    /**
     * Método que realiza el escaneo solo de vulnerabilidades recientes
     *
     * @return incidencias sobre las vulnerabilidades recientes
     */
    public Set<Result> doRecentScan() {
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        Set<Result> totales = doScan(vulndao.obtenerListaRecientes(), swdao.obtenerListadeSoftware());
        return totales;
    }
    /**
     * Método para realizar el analisis de vulnerabilidades recientes a partir de la fecha actual
     * 
     * @param actual la fecha en que se solicita el analisis
     * @return Conjuntio con resultados cuando no es sabado o domingo
     */
    public Set<Result> doRecentScan(Date actual) {
        //Inicializar daos
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        //Instanciar un calendario y establecer como tiempo la fecha actual
        Calendar cal = Calendar.getInstance();
        Calendar cfin = Calendar.getInstance();
        cal.setTime(actual);
        cfin.setTime(actual);
        //Instanciar una fecha para el inicio
        Date inicio = new Date();
        Date fin = new Date();
        //Obtener el numero de dia de la semana
        int ndia = cal.get(Calendar.DAY_OF_WEEK);
        //Comparar el numero de dia para filtrar la lista
        switch (ndia) {
            case 1: //Domingo
                return new LinkedHashSet<Result>();
            case 2: //Lunes Restar 3 dias por Viernes, Sabado y Domingo
                cal.add(Calendar.DAY_OF_WEEK, -3);
                break;
            case 3: //Martes
                cal.add(Calendar.DAY_OF_WEEK, -1);
                break;
            case 4: //Miercoles
                cal.add(Calendar.DAY_OF_WEEK, -1);
                break;
            case 5: //Jueves
                cal.add(Calendar.DAY_OF_WEEK, -1);
                break;
            case 6: //Viernes
                cal.add(Calendar.DAY_OF_WEEK, -1);
                break;
            case 7:  //Sabado
                return new LinkedHashSet<Result>();
        }
        //La fecha de inicio sera el calendario - el tiempo que se resto
        cal.set(Calendar.HOUR_OF_DAY, 1);
        //cal.add(Calendar.HOUR_OF_DAY, -8);
        inicio = cal.getTime();
        fin = cfin.getTime();
        //Obtener la lista de vulnerabilidades
        LOG.log(Level.INFO, "Buscando vulnerabilidades entre: {0} y {1}", new Object[]{inicio, fin});
        List<CVE> filtrada = this.filtrarListaPorFecha(vulndao.obtenerListaRecientes(), inicio, fin);
        Set<Result> totales = doScan(filtrada, swdao.obtenerListadeSoftware());
        return totales;
    }
    
    /**
     * Método que realiza el escaneo a partir de las fecha ingfiltradaadas por
     * el usuario
     *
     * @param start fecha de inicio
     * @param end fecha de termino
     * @return
     */
    public Set<Result> doCompleteScan(String start, String end) {
        //Objeto formater para simplificar la fecha
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date inicio = null;
        Date fin = null;
        try {
            //Parseo de las fechas recibidas
            inicio = format.parse(start);
            fin = format.parse(end);
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al realizar el parseo de las fechas: {0}", ex.getMessage());
        }
        //Iniciar DAOs de Vulnerabilidades y de Software
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        //Obteer la lista de vulnerabilidades y filtrarlas por la fecha elegida
        List<CVE> vulns = filtrarListaPorFecha(vulndao.obtenerListaArchivo(), inicio, fin);
        //Llamar al metodo para realizar el escaneo
        Set<Result> totales = doScan(vulns, swdao.obtenerListadeSoftware());
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades en ese periodo", totales.size());
        return totales;
    }
    
    /**
     * Método que realiza el escaneo mensual
     *
     * @return conjunto de resultados del escaneo mensual
     */
    public Set<Result> doMonthlyScan() {
        //Obtener una isntancial del calendario y apuntarla al primer dia del mes
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //Establecerla como parametro fecha de inicio
        Date inicio = cal.getTime();
        //Obtener una referencia a la fecha actual
        Date now = new Date();
        //Establecer el tiempo como el fin de mes
        cal.setTime(now);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DATE, -1);
        //Establecer la referncia como el fin de mes
        Date fin = cal.getTime();
        //Iniciar los daos
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        List<CVE> vulns = filtrarListaPorFecha(vulndao.obtenerListaArchivo(), inicio, fin);
        //Ejecutar ele scaneo.
        Set<Result> totales = doScan(vulns, swdao.obtenerListadeSoftware());
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades en el periodo: {1}/{2}", new Object[]{totales.size(), inicio.toString(), fin.toString()});
        return totales;
    }
    
    /**
     * Método que realiza el escaneo a partir de los parámetros ingfiltradaados
     *
     * @return Conjunto de filtradaultados
     */
    public Set<Result> doPartialScan() {
        //Iniciar DAOs de Software y de vulnerabilidades
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        //Instanciar una lista de vulnerabilidades para obtener las recientes o el archivo
        List<CVE> vulns = null;
        //Obtener una referencia para la lista de Sws o la lista filtrada
        List<Software> swList;
        List<Software> filtrada = null;
        Set<Result> scanResults;
        //Si el tipo es 1 obtener Vulnerabilidades recientes
        if (partialType == 1) {
            vulns = vulndao.obtenerListaRecientes();
        } //Si el tipo es 2 obtener el Archivo de Vulnerabilidades
        else if (partialType == 2) {
            vulns = vulndao.obtenerListaArchivo();
        }
        //El valor 0 implica que no hay filtro por grupo
        if (UA.equals("0")) {
            swList = swdao.obtenerListadeSoftware();
        } else {
            //Obtener el SW de la UA/Grupo seleccionado
            swList = swdao.obtenerListadeSoftware();
            filtrada = filtrarListaSW(swList, UA);
        }
        //Si el tipo de proveedor es 1 se va a filtrar por vendedor
        if (vendorType == 1) {
            LOG.log(Level.INFO, "Filtrando lista, buscando elementos de: {0}", vendor);
            //Si el tipo es 0 se va a filtrar sobre toda la lista
            if (UA.equals("0")) {
                swList = filtrarListaPorVendor(swList, vendor);
                //En otro caso se va a buscar sobre la lista previamente filtrada - Fabricantes en Grupo
            } else {
                filtrada = filtrarListaPorVendor(filtrada, vendor);
            }
        }
        //Si la severidad es diferente de 'nd' se va a filtra por gravedad
        if (!severity.equalsIgnoreCase("nd")) {
            vulns = filtrarListaPorCriticidad(vulns, severity);
        }
        //Si el tipo de fecha es parcial se va a filtrar la lista de acuerdo al rango establecido
        if (dateType.equalsIgnoreCase("partial")) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date inicio = null;
            Date fin = null;
            try {
                inicio = format.parse(sdate);
                fin = format.parse(edate);
            } catch (ParseException ex) {
                LOG.log(Level.SEVERE, "Ocurrio un error al realizar el parseo de las fechas: {0}", ex.getMessage());
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(inicio);
            cal.set(Calendar.HOUR_OF_DAY, 1);
            Calendar cfin = Calendar.getInstance();
            cfin.setTime(fin);
            cfin.set(Calendar.HOUR_OF_DAY, 12);
            LOG.log(Level.INFO, "Buscando vulnerabilidades entre: {0} y {1}", new Object[]{cal.getTime(), cfin.getTime()});
            vulns = filtrarListaPorFecha(vulns, cal.getTime(), fin);
        }
        //Si la UA o el grupo es '0' ejecutar escaneo en toda la lista
        if (UA.equals("0")) {
            scanResults = doScan(vulns, swList);
            //Otro caso buscarlo sobre la filtrada
        } else {
            scanResults = doScan(vulns, filtrada);
        }
        LOG.log(Level.INFO, "Existen: {0} posibles amenazas con el criterio seleccionado.", scanResults.size());
        return scanResults;
    }

    /**
     * Método que realiza el escaneo general
     *
     * @param vulns lista de vulnerabilidades
     * @param sws lista de software a comparar
     * @return conjunto de filtradaultados del escaneo
     */
    private Set<Result> doScan(List<CVE> vulns, List<Software> sws) {
        //Instanciar una lista para almacenar los resultados
        List<Result> res = new ArrayList<Result>();
        LOG.log(Level.INFO, "Se van a comparar: {0} vulnerabilidades y {1} productos.", new Object[]{vulns.size(), sws.size()});
        //Iterar la lista de Vulnerabilidades
        for (CVE vuln : vulns) {
            //Para cada vulnerabilidad obtener la lista de SW Vulnerable
            List<VulnSoftware> vulnswList = vuln.getVuln_soft();
            //Realizar escaneo si la lista de SW no esta vacia
            if (!vulnswList.isEmpty()) {
                //Iterar lista de SW obtenida(vuln) - vulnsw es de recientes/archivo
                for (VulnSoftware vulnsw : vulnswList) {
                    // Iterar para los elementos de la lista recibida
                    for (int i = 0; i < sws.size(); i++) {
                        //Obtener el fabricante(SW) de la vulnerabilidad
                        //Si el fabricante del SW(vuln) es igual al de la Lista recibida - Buscar incidencia en producto
                        if (vulnsw.getVendor().toLowerCase().equalsIgnoreCase(sws.get(i).getFabricante())) {
                            //Obtener el nombre del SW de la vulnerabilidad y reemplazar _ con ' '- Ej: microsoft_windows --> microsoft windows
                            String name = vulnsw.getName().toLowerCase().replace("_", " ");
                            //Filtro para productos Microsoft
                            //Si el nombre del SW de la Vuln es: Windows XXXX o Internet Explorer Iterar para buscar version(es)
                            if (name.contains("windows server 2003") || name.contains("windows server 2008")
                                    || name.contains("windows server 2012") || name.contains("windows 7")
                                    || name.contains("windows 8") || name.contains("windows 8.1")
                                    || name.contains("internet explorer")) {
                                //Iterar la o las versiones del sw de la vulnerabilidad
                                for (Version version : vulnsw.getVersion()) {
                                    //Obtener el elemento i de la lista de SW recibida y buscar coincidencias con el nombre del SW de la vulnerabilidad
                                    if (sws.get(i).getNombre().toLowerCase().contains(name)) {
                                        //Si hay incidencia obtener la version del elemento 'i' y compararla con la version del SW de la Vulnerabilidad
                                        //Primera Caso Ej. 8.x contiene a 8.2.x
                                        //>if (sws.get(i).getVersion().contains(version.getNumber())) {
                                        if (sws.get(i).getVersion().startsWith(version.getNumber())) {
                                            Result nres = new Result(vuln, sws.get(i));
                                            res.add(nres);
                                            //Segundo Caso Ej. 8.x = 8.x
                                        } else if (sws.get(i).getVersion().equals(version.getNumber())) {
                                            //LOG.log(Level.INFO, "ScannerBean#doScan() - Caso1");
                                            Result nres = new Result(vuln, sws.get(i));
                                            res.add(nres);
                                            //Filtro especifico para windows 8 y windows server 2012 - SOLO AQUI FUNCIONA
                                        } else if (name.contains("windows 8") || name.contains("windows server 2012") || name.contains("internet explorer")) {
                                            Result nres = new Result(vuln, sws.get(i));
                                            res.add(nres);
                                        }
                                    } //if nombre/name
                                } //FOREACH Version
                            } // IF productos microsoft
                            //Si el nombre del SW de la lista contiene al Nombre del SW de la vulnerabilidad Ej. Microsoft Windows 8 contiene windows 8
                            if (sws.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
                                //Iterar sobre las versiones del SW de la vulnerabilidad
                                for (Version version : vulnsw.getVersion()) {
                                    //Filtro para Microsoft
                                    /*
                                     if (name.equalsIgnoreCase("windows server 2003") || name.equalsIgnoreCase("windows server 2008")
                                     || name.equalsIgnoreCase("windows server 2012") || name.equalsIgnoreCase("windows 7") || name.equalsIgnoreCase("windows 8")
                                     || name.equalsIgnoreCase("windows 8.1") || name.equalsIgnoreCase("internet explorer")) {
                                     Result nres = new Result(vuln, sws.get(i));
                                     filtrada.add(nres);
                                     }*/
                                    //Si la version del SW de la vulnerabilidad es igual a la version del SW de la lista
                                    if (version.getNumber().equals(sws.get(i).getVersion())) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                        //2do caso si la version del SW de la vulnerabilidad contiene a la versiond el SW de la lista
                                    //} else if (version.getNumber().contains(sws.get(i).getVersion().replace("x", ""))) {
                                    } else if (version.getNumber().startsWith(sws.get(i).getVersion().replace("x", ""))) {
                                        //LOG.log(Level.INFO, "ScannerBean#doScan() - Caso2");
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    }
                                }
                            } //if nombre contains name
                        } //if vendor fabricante equalsIgnoreCase
                        //2do caso. Si el nombre del Fabricante de la lista recibida empieza con el nombre del SW de la vulnerabilidad Ej. Apache Software Foundation empieza con Apache
                        else if (sws.get(i).getFabricante().toLowerCase().startsWith(vulnsw.getVendor().toLowerCase())) {
                            //Obtener el nombre del SW de la vulnerabilidad y reemplazar _ con ' '
                            String name = vulnsw.getName().replace("_", " ");
                            /*
                             if (name.equalsIgnoreCase("windows server 2003") || name.equalsIgnoreCase("windows server 2008")
                             || name.equalsIgnoreCase("windows server 2012") || name.equalsIgnoreCase("windows 7") || name.equalsIgnoreCase("windows 8")
                             || name.equalsIgnoreCase("windows 8.1") || name.equalsIgnoreCase("internet explorer")) {
                             System.out.println(name + "/" + sws.get(i).getNombre());
                             }*/
                            //Si el nombre del SW de la lista contiene el nombre del SW de la vulnerabilidad
                            if (sws.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
                                //Iterar las versiones del sw de la vulnerabilidad
                                for (Version version : vulnsw.getVersion()) {
                                    /*Filtro de Microsoft, aqui no afecta
                                     if (name.equalsIgnoreCase("windows server 2003") || name.equalsIgnoreCase("windows server 2008")
                                     || name.equalsIgnoreCase("windows server 2012") || name.equalsIgnoreCase("windows 7") || name.equalsIgnoreCase("windows 8")
                                     || name.equalsIgnoreCase("windows 8.1") || name.equalsIgnoreCase("internet explorer")) {
                                     Result nres = new Result(vuln, sws.get(i));
                                     filtrada.add(nres);
                                     }
                                     */
                                    //Si la version del SW de la vulnerabilidad es igual a la version del SW de la lista
                                    if (version.getNumber().equals(sws.get(i).getVersion())) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                        //2do caso, Si la version del SW de la vulnerabilidad contiene al SW de la version de la lista
                                    } else if (version.getNumber().contains(sws.get(i).getVersion().replace("x", ""))) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    }
                                }
                            } // if nombre contains 
                        } //CASO: canonical ltd. starts with canonical
                    } //for de lista de SW recibida
                } //foreach swlist(vuln)
            } //iflistempty
        } //foreach
        Set<Result> total = procesarListaResultados(res);
        //Set<Result> total = new LinkedHashSet<Result>();
        //total.addAll(res);
        return total;
    }

    /**
     * Método que se encarga de procesar la lista de filtradaultados: se encarga
     * de filtrar los elementos repetidos del conjunto de filtradaultados
     *
     * @param resultados Lista de filtradaultados
     * @return Conjunto de filtradaultados
     */
    private Set<Result> procesarListaResultados(List<Result> resultados) {
        //Instanciar conjuntos para obtener Resultados diferentes y duplicados
        Set<Result> diferentes = new LinkedHashSet<Result>();
        Set<Result> duplicados = new LinkedHashSet<Result>();
        //Obtener una lista de resultados con la union de grupos y lista de SW
        List<Result> listaConGrupos = obtenerResultadosConGruposYSW(resultados);
        //Eliminar duplicados en caso de existir
        for (Result result : listaConGrupos) {
            //Si el resultado ya existen en el conjunto de resultados
            if (diferentes.contains(result)) {
                duplicados.add(result);
            } else {
                diferentes.add(result);
            }
        }
        List<Result> order = new ArrayList<Result>();
        order.addAll(diferentes);
        Collections.sort(order);
        diferentes = new LinkedHashSet<Result>();
        diferentes.addAll(order);
        return diferentes;
    }

    /**
     * Método que se encarga de juntar SW y Grupos para vulnerabilidades con el mismo nombre
     * @param listaAFiltrar Recibe la lista de resultados obtenida del escaneo
     * @return lista de resultados con vulnerabilidades que tienen agrupados su SW y grupo
     */
    private List<Result> obtenerResultadosConGruposYSW(List<Result> listaAFiltrar) {
        //Instanciar una lista para retornar la lista filtrada
        List<Result> listaFiltrada = new ArrayList<Result>();
        //Instanciar una lista para almacenar los grupos y otra para el Software
        List<String> gruposList = new ArrayList<String>();
        List<Software> swlist = new ArrayList<Software>();
        //Iterar la lista recibida
        for (int i = 0; i < listaAFiltrar.size(); i++) {
            //Obtener una referencia al resultado actual, a la siguiente y una nueva
            Result actual = listaAFiltrar.get(i);
            Result siguiente = new Result();
            Result nuevo = new Result();
            //Si i + 1 es menor que el tamaño buscar para agrupar
            if ((i + 1) < listaAFiltrar.size()) {
                //Iniciar referencia a siguiente con el valor del siguiente resultado
                siguiente = listaAFiltrar.get(i + 1);
                //Si la vulnerabilidad actual y la siguiente son las mismas, entonces juntar su SW y obtener su Grupo
                if (actual.getVulnerabilidad().equals(siguiente.getVulnerabilidad())) {
                    gruposList.add(actual.getSw().getUAResponsable());
                    swlist.add(actual.getSw());
                } else {
                    //Añadir el grupo y SW actual a la lista
                    swlist.add(actual.getSw()); //agregado
                    gruposList.add(actual.getSw().getUAResponsable());
                    //Al resultado nuevo agregar la vulnerabilidad, la lista de grupos, lista de SW
                    nuevo.setVulnerabilidad(actual.getVulnerabilidad());
                    nuevo.setGruposList(gruposList);
                    nuevo.setSwList(swlist);
                    //A la lista de resultados agregar el nuevo resultado
                    listaFiltrada.add(nuevo);
                    //reiniciar las listas de SW y Grupos
                    gruposList = new ArrayList<String>();
                    swlist = new ArrayList<Software>();
                }
                //En otro caso si es el ultimo elemento
            } else if (i < listaAFiltrar.size()) {
                //Añadir el ultimo elemento a la lista de grupos y de SW
                gruposList.add(actual.getSw().getUAResponsable());
                swlist.add(actual.getSw());
                //Al resultado nuevo agregar la vulnerabilidad, la lista de grupos, lista de SW
                nuevo.setVulnerabilidad(actual.getVulnerabilidad());
                nuevo.setGruposList(gruposList);
                nuevo.setSwList(swlist);
                //A la lista de resultados agregar el nuevo resultado
                listaFiltrada.add(nuevo);
            }
        }
        //Una vez que se completa se tiene una lista con SW  y Grupos duplicados
        //Iterar la lista para eliminar los duplicados
        for (Result res : listaFiltrada) {
            List<String> filtrada = eliminarDuplicadosLista(res.getGruposList());
            res.setGruposList(filtrada);
            List<Software> filtradasw = eliminarDuplicadosListaSw(res.getSwList());
            res.setSwList(filtradasw);
        }
        return listaFiltrada;
    }

    /**
     * Método que elimina los grupos duplicados dentro de la lista de Grupos
     * @param listaGrupos Lista de Grupos a filtrar
     * @return  Lista de Grupos sin duplicados
     */
    private List<String> eliminarDuplicadosLista(List<String> listaGrupos) {
        //Instanciar conjuntos duplicados y diferentes
        Set<String> duplicados = new LinkedHashSet<String>();
        Set<String> diferentes = new LinkedHashSet<String>();
        //Instanciar una lista de String para almacenar el resultado
        List<String> result = new ArrayList<String>();
        for (String res : listaGrupos) {
            if (diferentes.contains(res)) {
                duplicados.add(res);
            } else {
                diferentes.add(res);
            }
        }
        result.addAll(diferentes);
        return result;
    }

    /**
     * Método que se encarga de eliminar duplicados dentro de una lista de SW
     * @param listaConDuplicados lista con elementos duplicados
     * @return  Lista siin elementos duplicados
     */
    private List<Software> eliminarDuplicadosListaSw(List<Software> listaConDuplicados) {
        //Instanciar conjuntos duplicados y diferentes
        Set<Software> duplicados = new LinkedHashSet<Software>();
        Set<Software> diferentes = new LinkedHashSet<Software>();
        //Instanciar una lista para almacenar los resultados
        List<Software> result = new ArrayList<Software>();
        for (Software software : listaConDuplicados) {
            if (diferentes.contains(software)) {
                duplicados.add(software);
            } else {
                diferentes.add(software);
            }
        }
        result.addAll(diferentes);
        return result;
    }
    /*
     Filtrar SW
    
     for (Result filtrada : listafinal) {
     Set<Software> difs = new LinkedHashSet<>();
     Set<Software> dups = new LinkedHashSet<>();
     List<Software> swslist = filtrada.getSwList();
     for (Software sw : swslist) {
     if (difs.contains(sw)) {
     dups.add(sw);
     } else {
     difs.add(sw);
     }
     }
     List<Software> resSw = new ArrayList<>();
     resSw.addAll(difs);
     filtrada.setSwList(resSw);
     }
     */

    /*
     public Set<Result> doCompleteScan() {
     swdao = new SoftwareDAO();
     vulndao = new VulnerabilityDAO();
     List<Software> swList = swdao.obtenerListadeSoftware();
     List<CVE> cveList = vulndao.obtenerListaArchivo();//vulndao.retrieveAllCVEsFromFile();//
     scanResults = new ArrayList<Result>();
     LOG.log(Level.INFO, "Encontre: {0} elementos de SW y {1} vulnerabilidades", new Object[]{swList.size(), cveList.size()});
     //SCANEAR
     for (CVE vuln : cveList) {
     List<VulnSoftware> vulnSWList = vuln.getVuln_soft();
     if (!vulnSWList.isEmpty()) {
     for (VulnSoftware vulnsw : vulnSWList) {
     for (int i = 0; i < swList.size(); i++) {
     //Caso: Mismo nombre como Cisco/cisco
     if (vulnsw.getVendor().equalsIgnoreCase(swList.get(i).getFabricante())) {
     String name = vulnsw.getName().replace("_", " ");
     if (swList.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
     for (Version version : vulnsw.getVersion()) {
     if (version.getNumber().equals(swList.get(i).getVersion())) {
     //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
     LOG.log(Level.INFO, "Agregado: {0}/{1}/{2}", new Object[]{swList.get(i).getFabricante(), swList.get(i).getNombre(), swList.get(i).getVersion()});
     Result nResult = new Result(vuln, swList.get(i));
     scanResults.add(nResult);
     } else if (version.getNumber().contains(swList.get(i).getVersion().replace("x", ""))) {
     //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
     LOG.log(Level.INFO, "Agregado: {0}/{1}/{2}", new Object[]{swList.get(i).getFabricante(), swList.get(i).getNombre(), swList.get(i).getVersion()});
     Result nResult = new Result(vuln, swList.get(i));
     scanResults.add(nResult);
     }
     }
     }
     } //Caso: Mismo vendedor nombre similar: Apache Software Foundation/apache
     else if (swList.get(i).getFabricante().toLowerCase().startsWith(vulnsw.getVendor().toLowerCase())) {
     String name = vulnsw.getName().replace("_", " ");
     if (swList.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
     for (Version version : vulnsw.getVersion()) {
     //System.out.println("*** Versiones: " + version.getNumber() + "/" + swList.get(i).getVersion());
     if (version.getNumber().equals(swList.get(i).getVersion())) {
     //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
     LOG.log(Level.INFO, "Agregado: {0}/{1}/{2}", new Object[]{swList.get(i).getFabricante(), swList.get(i).getNombre(), swList.get(i).getVersion()});
     Result nResult = new Result(vuln, swList.get(i));
     scanResults.add(nResult);
     } else if (version.getNumber().contains(swList.get(i).getVersion().replace("x", ""))) {
     //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
     LOG.log(Level.INFO, "Agregado: {0}/{1}/{2}", new Object[]{swList.get(i).getFabricante(), swList.get(i).getNombre(), swList.get(i).getVersion()});
     Result nResult = new Result(vuln, swList.get(i));
     scanResults.add(nResult);
     }
     }
     }
     }
     }
     }
     } //if not empty
     }//fore vuln
     //SCANEAR
     Set<Result> results = procesarListaResultados(scanResults);
     //return "Encontre: " + results.size() + " amenzas.";
     return results;
     }
     */
}
