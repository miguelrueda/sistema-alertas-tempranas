package org.banxico.ds.sisal.prueba;

import org.banxico.ds.sisal.dao.SourcesDAO;

public class ActualizarCVE {
    
    public static void main(String[] args) {
        SourcesDAO sourcesDAO = new SourcesDAO();
        boolean flag = sourcesDAO.descargarFuente("4", "https://nvd.nist.gov/download/nvdcve-2013.xml");
        if (flag) {
            System.out.println("Fuente actualizada");
        } else {
            System.out.println("Fuente no actualizada");
        }
    }
    
}