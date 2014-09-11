package org.banxico.ds.sisal.prueba;

import org.banxico.ds.sisal.dao.VulnerabilityDAO;

public class DataBaseConnectionTest {

    public static void main(String[] args) {
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        int res = vdao.comprobarExistenciaVulnerabilidad("CVE-2015-0001");
        System.out.println("RESULTADO: " + res);
    }

}