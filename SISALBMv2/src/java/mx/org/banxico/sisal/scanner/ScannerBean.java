package mx.org.banxico.sisal.scanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.org.banxico.sisal.dao.SoftwareDAO;
import mx.org.banxico.sisal.dao.VulnerabilityDAO;
import mx.org.banxico.sisal.entities.Software;
import mx.org.banxico.sisal.parser.entidades.CVE;
import mx.org.banxico.sisal.parser.entidades.Version;
import mx.org.banxico.sisal.parser.entidades.VulnSoftware;

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

    public boolean isModificadas() {
        return modificadas;
    }

    public void setModificadas(boolean modificadas) {
        this.modificadas = modificadas;
    }

    /**
     * Método que realiza el escaneo más simple: Todas las vulnerabilidades
     * contra todo el Software
     *
     * @return Conjunto de tipo Result con los resultados del escaneo
     */
    public Set<Result> doCompleteScan() {
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        Set<Result> totales = doScan(vulndao.getArchivoCVE(), swdao.obtenerTodos());
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades en el escaneo completo.", totales.size());
        return totales;
    }

    /**
     * Getter
     *
     * @return Lista de resultados de tipo Result
     */
    public List<Result> getResultados() {
        return resultados;
    }

    /**
     * Método que se encarga de procesar la lista de resultados: se encarga de
     * filtrar los elementos repetidos del conjunto de resultados
     *
     * @param resultados Lista de resultados
     * @return Conjunto de resultados
     */
    private Set<Result> procesarListaResultados(List<Result> resultados) {
        Set<Result> diferentes = new LinkedHashSet<Result>();
        Set<Result> duplicados = new LinkedHashSet<Result>();
        for (Result result : resultados) {
            if (diferentes.contains(result)) {
                duplicados.add(result);
            } else {
                diferentes.add(result);
            }
        }

        List<Result> nueva = new ArrayList<Result>();   //nueva --> lista diferentes
        nueva.addAll(diferentes);
        List<Result> listafinal = new ArrayList<Result>();
        List<Software> swlist = new ArrayList<Software>();  //swlist --> addswlist
        for (int i = 0; i < nueva.size(); i++) {
            Result actual = nueva.get(i);
            Result siguiente = new Result();
            Result nuevo = new Result();
            if ((i + 1) < nueva.size()) {
                siguiente = nueva.get(i + 1);
                if (actual.getVulnerabilidad().equals(siguiente.getVulnerabilidad())) {
                    swlist.add(actual.getSw());
                } else {
                    swlist.add(actual.getSw());
                    nuevo.setVulnerabilidad(actual.getVulnerabilidad());
                    nuevo.setSwList(swlist);
                    listafinal.add(nuevo);
                    swlist = new ArrayList<Software>();
                }
            } else if (i < nueva.size()) {
                swlist.add(actual.getSw());
                nuevo.setVulnerabilidad(actual.getVulnerabilidad());
                nuevo.setSwList(swlist);
                listafinal.add(nuevo);
            }
        } // for
        duplicados = new LinkedHashSet<Result>();
        diferentes = new LinkedHashSet<Result>();
        for (Result res : listafinal) {
            if (diferentes.contains(res)) {
                duplicados.add(res);
            } else {
                diferentes.add(res);
            }
        }
        return diferentes;
    }
    /*
     Filtrar SW
    
     for (Result res : listafinal) {
     Set<Software> difs = new LinkedHashSet<>();
     Set<Software> dups = new LinkedHashSet<>();
     List<Software> swslist = res.getSwList();
     for (Software sw : swslist) {
     if (difs.contains(sw)) {
     dups.add(sw);
     } else {
     difs.add(sw);
     }
     }
     List<Software> resSw = new ArrayList<>();
     resSw.addAll(difs);
     res.setSwList(resSw);
     }
     */

    /**
     * Método que realiza el escaneo a partir de las fecha ingresadas por el
     * usuario
     *
     * @param start fecha de inicio
     * @param end fecha de termino
     * @return
     */
    public Set<Result> doCompleteScan(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date inicio = null;
        Date fin = null;
        try {
            inicio = format.parse(start);
            fin = format.parse(end);
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, "Error al convertir las fechas!!");
        }
        LOG.log(Level.INFO, "Buscar entre vulnerabilidades del: {0} al {1}", new Object[]{inicio, fin});
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        List<CVE> vulns = filtrarListaPorFecha(vulndao.getArchivoCVE(), inicio, fin);
        Set<Result> totales = doScan(vulns, swdao.obtenerTodos());
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades en ese periodo", totales.size());
        return totales;
    }

    /**
     * Método que realiza el escaneo general
     *
     * @param vulns lista de vulnerabilidades
     * @param sws lista de software a comparar
     * @return conjunto de resultados del escaneo
     */
    private Set<Result> doScan(List<CVE> vulns, List<Software> sws) {
        List<Result> res = new ArrayList<Result>();
        LOG.log(Level.INFO, "Comparando: {0} vulnerabilidades y {1} productos.", new Object[]{vulns.size(), sws.size()});
        for (CVE vuln : vulns) {
            List<VulnSoftware> vulnswList = vuln.getVuln_soft();
            if (!vulnswList.isEmpty()) {
                for (VulnSoftware vulnsw : vulnswList) {
                    for (int i = 0; i < sws.size(); i++) {
                        if (vulnsw.getVendor().equalsIgnoreCase(sws.get(i).getFabricante())) {
                            String name = vulnsw.getName().replace("_", " ");
                            if (sws.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
                                for (Version version : vulnsw.getVersion()) {
                                    //Filtro para Microsoft
                                    if (name.equalsIgnoreCase("windows server 2003") || name.equalsIgnoreCase("windows server 2008")
                                            || name.equalsIgnoreCase("windows server 2012") || name.equalsIgnoreCase("windows 7") || name.equalsIgnoreCase("windows 8")
                                            || name.equalsIgnoreCase("windows 8.1") || name.equalsIgnoreCase("internet explorer")) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    }
                                    if (version.getNumber().equals(sws.get(i).getVersion())) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    } else if (version.getNumber().contains(sws.get(i).getVersion().replace("x", ""))) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    }
                                }
                            }
                        } //if vendor fabricante
                        else if (sws.get(i).getFabricante().toLowerCase().startsWith(vulnsw.getVendor().toLowerCase())) {
                            String name = vulnsw.getName().replace("_", " ");
                            if (sws.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
                                for (Version version : vulnsw.getVersion()) {
                                    if (name.equalsIgnoreCase("windows server 2003") || name.equalsIgnoreCase("windows server 2008")
                                            || name.equalsIgnoreCase("windows server 2012") || name.equalsIgnoreCase("windows 7") || name.equalsIgnoreCase("windows 8")
                                            || name.equalsIgnoreCase("windows 8.1") || name.equalsIgnoreCase("internet explorer")) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    }
                                    if (version.getNumber().equals(sws.get(i).getVersion())) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    } else if (version.getNumber().contains(sws.get(i).getVersion().replace("x", ""))) {
                                        Result nres = new Result(vuln, sws.get(i));
                                        res.add(nres);
                                    }
                                }
                            }
                        } //CASO: canonical ltd. starts with canonical
                    }
                }
            }
        }
        Set<Result> total = procesarListaResultados(res);
        return total;
    }

    /**
     * Método que realiza el escaneo a partir de los parámetros ingresados
     *
     * @return Conjunto de resultados
     */
    public Set<Result> doPartialScan() {
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        List<CVE> vulns = null;
        List<Software> swList;
        List<Software> filtrada = null;
        Set<Result> totales;
        //Vulnerabilidades recientes
        if (partialType == 1) {
            vulns = vulndao.getRecents();
        } //Archivo de Vulnerabilidades
        else if (partialType == 2) {
            vulns = vulndao.getArchivoCVE();
        }
        if (UA.equals("0")) {
            swList = swdao.obtenerTodos();
        } else {
            //Obtener el sw de la UA seleccionada
            //Prueba OSI
            swList = swdao.obtenerTodos();
            filtrada = filtrarListaSW(swList, UA);
        }
        if (vendorType == 1) {
            LOG.log(Level.INFO, "Filtrando lista, buscando elementos de: {0}", vendor);
            if (UA.equals("0")) {
                swList = filtrarListaPorVendor(swList, vendor);
            } else {
                filtrada = filtrarListaPorVendor(filtrada, vendor);
            }
        }
        if (!severity.equalsIgnoreCase("nd")) {
            vulns = filtrarListaPorCriticidad(vulns, severity);
        }
        if (dateType.equalsIgnoreCase("partial")) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date inicio = null;
            Date fin = null;
            try {
                inicio = format.parse(sdate);
                fin = format.parse(edate);
            } catch (ParseException ex) {
                LOG.log(Level.SEVERE, "Error al convertir las fechas!!");
            }
            vulns = filtrarListaPorFecha(vulns, inicio, fin);
        }
        if (UA.equals("0")) {
            totales = doScan(vulns, swList);
        } else {
            totales = doScan(vulns, filtrada);
        }
        LOG.log(Level.INFO, "Existen: {0} posibles amenazas con el criterio seleccionado", totales.size());
        return totales;
    }

    /**
     * Método que se encarga de filtrar la lista de vulnerabilidades a partir de
     * las fechas ingredas
     *
     * @param archivoCVE lista de vulnerabilidades
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de vulnerabilidades filtrada
     */
    private List<CVE> filtrarListaPorFecha(List<CVE> archivoCVE, Date inicio, Date fin) {
        List<CVE> filtrados = new ArrayList<CVE>();
        int pubs = 0, mods = 0;
        LOG.log(Level.INFO, "Buscar en modificadas: {0}", modificadas);
        if (isModificadas()) {
            LOG.log(Level.INFO, "Buscar en todas las fechas");
            for (CVE vuln : archivoCVE) {
                Date pub = vuln.getPublished();
                Date mod = vuln.getModified();
                if ((pub.after(inicio) && pub.before(fin)) || (mod.after(inicio) && mod.before(fin))) {
                    filtrados.add(vuln);
                }
            }
        } else {
            LOG.log(Level.INFO, "Buscar solo en fechas publicación");
            for (CVE vuln : archivoCVE) {
                Date pub = vuln.getPublished();
                Date mod = vuln.getModified();
                if (pub.after(inicio) && pub.before(fin)) {
                    filtrados.add(vuln);
                }
            }
        }

        //LOG.log(Level.INFO, "Soy: {0} mi publicacion: {1} fui modificada: {2}", new Object[]{vuln.getName(), pub.toString(), mod.toString()});
        //if (pub.compareTo(inicio) < 0) {
        //Fecha Menor
        //} else 
            /*
         if (pub.compareTo(inicio) > 0 && pub.compareTo(fin) < 0) {
         filtrados.add(vuln);
         pubs++;
         }  
         else if (pub.equals(inicio) || mod.equals(inicio) || pub.equals(fin) || mod.equals(fin)) {
         filtrados.add(vuln);
         pubs++;
         }
         */
        /*
         for (CVE vuln : archivoCVE) {
         Date mod = vuln.getModified();
         if (mod.compareTo(inicio) > 0 && mod.compareTo(fin) < 0) {
         //Fecha de publicacion entre 1-XX y 30-XX o fecha de modificacion entre 1-xx y 30-xx
         filtrados.add(vuln);
         mods++;
         }
         }*/
        return filtrados;
    }

    /**
     * M´étodo que se encarga de filtrar la lista de sw de acuerdo a la UA
     *
     * @param swList lista de software a filtrar
     * @param filtro filtro a aplicar a la lista
     * @return lista de software filtrada
     */
    private List<Software> filtrarListaSW(List<Software> swList, String filtro) {
        List<Software> filtrada = new ArrayList<Software>();
        for (Software sw : swList) {
            if (sw.getUAResponsable().equalsIgnoreCase(filtro)) {
                filtrada.add(sw);
            }
        }
        LOG.log(Level.INFO, "Encontre: {0} elementos de: " + filtro, filtrada.size());
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
        List<Software> res = new ArrayList<Software>();
        for (Software sw : swList) {
            if (sw.getFabricante().equalsIgnoreCase(vendor)) {
                res.add(sw);
            } else if (sw.getFabricante().toLowerCase().startsWith(vendor.toLowerCase())) {
                res.add(sw);
            }
        }
        LOG.log(Level.INFO, "Encontre: {0} elementos de tipo: {1}", new Object[]{res.size(), vendor});
        return res;
    }

    /**
     * Método que se encarga de filtrar la lista de SW por nivel de criticidad
     *
     * @param vulns lista de vulnerabilidades
     * @param severity filtro de criticidad
     * @return lista de vulnerabilidades filtrada
     */
    private List<CVE> filtrarListaPorCriticidad(List<CVE> vulns, String severity) {
        List<CVE> res = new ArrayList<CVE>();
        for (CVE vuln : vulns) {
            if (vuln.getSeverity().equalsIgnoreCase(severity)) {
                res.add(vuln);
            }
        }
        LOG.log(Level.INFO, "Encontre: {0} vulnerabilidades con criticidad: {1}", new Object[]{res.size(), severity});
        return res;
    }

    /*
     public Set<Result> doCompleteScan() {
     swdao = new SoftwareDAO();
     vulndao = new VulnerabilityDAO();
     List<Software> swList = swdao.obtenerTodos();
     List<CVE> cveList = vulndao.getArchivoCVE();//vulndao.retrieveAllCVEsFromFile();//
     resultados = new ArrayList<Result>();
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
     resultados.add(nResult);
     } else if (version.getNumber().contains(swList.get(i).getVersion().replace("x", ""))) {
     //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
     LOG.log(Level.INFO, "Agregado: {0}/{1}/{2}", new Object[]{swList.get(i).getFabricante(), swList.get(i).getNombre(), swList.get(i).getVersion()});
     Result nResult = new Result(vuln, swList.get(i));
     resultados.add(nResult);
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
     resultados.add(nResult);
     } else if (version.getNumber().contains(swList.get(i).getVersion().replace("x", ""))) {
     //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
     LOG.log(Level.INFO, "Agregado: {0}/{1}/{2}", new Object[]{swList.get(i).getFabricante(), swList.get(i).getNombre(), swList.get(i).getVersion()});
     Result nResult = new Result(vuln, swList.get(i));
     resultados.add(nResult);
     }
     }
     }
     }
     }
     }
     } //if not empty
     }//fore vuln
     //SCANEAR
     Set<Result> results = procesarListaResultados(resultados);
     //return "Encontre: " + results.size() + " amenzas.";
     return results;
     }
     */
}
