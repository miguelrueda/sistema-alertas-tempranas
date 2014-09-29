package org.banxico.ds.sisal.prueba;

import java.sql.SQLException;
import org.banxico.ds.sisal.dao.SoftwareDAO;
import org.banxico.ds.sisal.entities.Software;

public class PruebaSoftwareDAO {
    
    public static void main(String[] args) throws SQLException {
        SoftwareDAO sdao = new SoftwareDAO();
        Software s = new Software(0, "oracle", "oracle jdk 1.7.0", "1.7.0", 1, -1, "ND", "ND");
        //boolean existencia = sdao.validarExistencia(s);
        int [] llavegrupos = {73};
        boolean insercion = sdao.agregarSoftware_enGrupos(s, llavegrupos);
        if (insercion) {
            System.out.println("Software agregado al grupo");
        } else {
            System.out.println("No se agrego el sw al grupo");
        }
    }
    
}
