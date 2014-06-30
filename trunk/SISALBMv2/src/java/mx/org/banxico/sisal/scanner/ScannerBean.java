package mx.org.banxico.sisal.scanner;

import java.util.ArrayList;
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

    public ScannerBean() {
    }

    public String doCompleteScan() {
        swdao = new SoftwareDAO();
        vulndao = new VulnerabilityDAO();
        List<Software> swList = swdao.obtenerTodos();
        List<CVE> cveList = vulndao.getArchivoCVE();//vulndao.retrieveAllCVEsFromFile();//
        resultados = new ArrayList<Result>();
        LOG.log(Level.INFO, "Encontre: {0} elementos de SW y {1} vulnerabilidades", new Object[]{swList.size(), cveList.size()});
        //SCANEAR
        int aux = 0;
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
            aux++;
        }//fore vuln
        //SCANEAR
        Set<Result> results = procesarLista(resultados);
        return "Encontre: " + results.size() + " amenzas.";
        //return results;
    }

    public List<Result> getResultados() {
        return resultados;
    }

    private Set<Result> procesarLista(List<Result> resultados) {
        Set<Result> diferentes = new LinkedHashSet<Result>();
        Set<Result> duplicados = new LinkedHashSet<Result>();
        int aux = 0;
        for (Result result : resultados) {
            if (diferentes.contains(result)) {
                aux++;
                duplicados.add(result);
            } else {
                diferentes.add(result);
            }
        }
        return diferentes;
    }

}
