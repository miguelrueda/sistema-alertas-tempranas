package org.banxico.ds.sisal.prueba;

import org.banxico.ds.sisal.dao.SourcesDAO;

public class ActualizarCPE {
    
    public static void main(String[] args) {
        
        SourcesDAO sourcesDAO = new SourcesDAO();
        boolean flag = sourcesDAO.descargarFuente("5", "http://static.nvd.nist.gov/feeds/xml/cpe/dictionary/official-cpe-dictionary_v2.3.xml");
        if (flag) {
            System.out.println("Fuente actualizada correctamente");
        } else {
            System.out.println("La fuente no se pudo actualizar");
        }
        
    }
    
}
