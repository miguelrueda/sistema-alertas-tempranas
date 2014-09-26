package org.banxico.ds.sisal.prueba;

import java.util.Arrays;
import java.sql.SQLException;
import org.banxico.ds.sisal.dao.GruposDAO;

public class PruebaEditarGrupo {
    
    public static void main(String[] args) {
        try {
            System.out.println("creando instancia");
            GruposDAO gdao = new GruposDAO();
            Integer [] llaves = {1, 2, 3, 100, 102, 47};
            System.out.println("arreglo: " + Arrays.toString(llaves));
            boolean flag = gdao.editarGrupo(72, "Javae", "Categoriae", llaves);
            if (flag) {
                System.out.println("Edicion correcta");
            } else {
                System.out.println("Edicion incorrecta");
            }
        } catch (SQLException ex) {
            System.out.println("Excepción de SQL: " + ex.getMessage());
        }
    }
    
}
