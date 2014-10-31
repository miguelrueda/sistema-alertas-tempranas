package org.banxico.ds.sisal.prueba;

import java.util.List;
import java.util.ArrayList;
import org.banxico.ds.sisal.dao.SoftwareDAO;
import org.banxico.ds.sisal.entities.Software;
import org.banxico.ds.sisal.parser.CPEParser;

public class SoftwareDAOTest {
    
    public static void main(String[] args) {
        SoftwareDAO sdao = new SoftwareDAO();
        /*
        List<Software> swdisp = new ArrayList<Software>();
        CPEParser parser = new CPEParser();
        swdisp = parser.getList();
        List<String> lista = new ArrayList<String>();
        for (Software sw : swdisp) {
            if (sw.getFabricante().equalsIgnoreCase("cisco")) {
                lista.add(sw.getNombre());
            }
        }
        
        for (String string : lista) {
            System.out.println(string);
        }
                */

        List<String> productos = sdao.obtenerProductosPorFabricanteAC("cisco");
        for (String string : productos) {
            System.out.println(string);
        }
        
    }

    
}

