package org.banxico.ds.sisal.prueba;

import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.entities.Vulnerabilidad;
import java.util.List;

public class RecentsTest {
    
    public static void main(String[] args) {
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        List<Vulnerabilidad> recientes = vdao.obtenerVulnerabilidadesRecientes();
        for (Vulnerabilidad vuln : recientes) {
            System.out.println(vuln);
        }
    }
    
}
