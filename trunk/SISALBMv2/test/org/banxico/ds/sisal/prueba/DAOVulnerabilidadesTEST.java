package org.banxico.ds.sisal.prueba;

import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.parser.entidades.CVE;
import java.util.List;

public class DAOVulnerabilidadesTEST {
    
    public static void main(String[] args) {
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        List<CVE> lista = vdao.obtenerListaArchivo();
        for (CVE cve : lista) {
            System.out.println(cve.getName());
        }
    }
    
}
