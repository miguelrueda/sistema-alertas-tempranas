package org.banxico.ds.sisal.prueba;

import java.text.ParseException;
import org.banxico.ds.sisal.dao.GrupoVulnerabilidadDAO;
import java.util.List;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import org.banxico.ds.sisal.parser.entidades.CVE;
import java.text.SimpleDateFormat;

public class GVDAOTest {
    
    public static void main(String[] args) throws ParseException {
        GrupoVulnerabilidadDAO gvdao = new GrupoVulnerabilidadDAO();
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        List<CVE> lista = vdao.obtenerListaArchivo();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (CVE cve : lista) {
            if (cve.getPublished().after(sdf.parse("01/10/2014"))) {
                System.out.println(cve.getName());
            }
        }
    }
    
}
