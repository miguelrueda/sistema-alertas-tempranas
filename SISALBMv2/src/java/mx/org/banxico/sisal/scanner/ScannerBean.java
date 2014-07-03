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

public class ScannerBean implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(ScannerBean.class.getName());
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

    public ScannerBean() {
    }

    public void setPartialType(int partialType) {
        this.partialType = partialType;
    }

    public void setUA(String UA) {
        this.UA = UA;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setVendorType(int vendorType) {
        this.vendorType = vendorType;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
    
    public Set<Result> doCompleteScan() {
        vulndao = new VulnerabilityDAO();
        swdao = new SoftwareDAO();
        Set<Result> totales = doScan(vulndao.getArchivoCVE(), swdao.obtenerTodos());
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades en ese periodo", totales.size());
        return totales;
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
    public List<Result> getResultados() {
        return resultados;
    }

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
        return diferentes;
    }

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

    private List<CVE> filtrarListaPorFecha(List<CVE> archivoCVE, Date inicio, Date fin) {
        List<CVE> filtrados = new ArrayList<CVE>();
        for (CVE vuln : archivoCVE) {
            Date pub = vuln.getPublished();
            if (pub.compareTo(inicio) < 0) {
                //lesser
            } else if (pub.compareTo(inicio) > 0 && pub.compareTo(fin) < 0) {
                //between
                filtrados.add(vuln);
            } else if (pub.equals(inicio)) {
                filtrados.add(vuln);
            }
        }
        LOG.log(Level.INFO, "Existen: {0} vulnerabilidades entre: {1} y {2}", new Object[]{filtrados.size(), inicio.toString(), fin.toString()});
        return filtrados;
    }

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

    private List<Software> filtrarListaPorVendor(List<Software> filtrada, String vendor) {
        List<Software> res = new ArrayList<Software>();
        for (Software sw : filtrada) {
            if (sw.getFabricante().equalsIgnoreCase(vendor)) {
                res.add(sw);
            } else if (sw.getFabricante().toLowerCase().startsWith(vendor.toLowerCase())) {
                res.add(sw);
            }
        }
        LOG.log(Level.INFO, "Encontre: {0} elementos de tipo: {1}", new Object[]{res.size(), vendor});
        return res;
    }

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

}
