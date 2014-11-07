package org.banxico.ds.sisal.prueba;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banxico.ds.sisal.entities.Grupo;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;

/**
 * Clase para probar el bean de analisis de vulnerabilidades
 *
 * @author t41507
 * @version 18-08-2014
 */
public class ScannerTest {

    /**
     * Bean de Escanner
     */
    private final static ScannerBean scanner = new ScannerBean();
    //private final static GruposDAO gdao = new GruposDAO();

    /**
     * Método que realiza la prueba
     *
     * @param args argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        /*
         //Obtener una instancia del calendario, establecer el tiempo y restar un dia
         Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());
         cal.add(Calendar.DAY_OF_WEEK, -1);
         //Realizar el analisis a travez del bean de escaneo
         Set<Result> resultados = scanner.doRecentScan(cal.getTime());
         //Mostrar los resultados encontraados
         System.out.println("Se encontraron: " + resultados.size() + " resultados.");
         for (Result result : resultados) {
         System.out.println(result);
         }
         */
        //Set<Result> resultados = scanner.doCompleteScan("21/09/2014", "25/09/2014");
        //Set<Result> resultados = //scanner.doRecentScan();
        //scanner.doMonthlyScan();
        Set<Result> resultados = scanner.doCompleteScan("01/10/2014", "20/10/2014");
        System.out.println("Se encontraron: " + resultados.size() + " resultados");
        if (!resultados.isEmpty()) {
            System.out.println("Guardando resultados");
            //doPersist(resultados);
            //registrarGrupoVulnerabilidad(resultados);
        } else {
            System.out.println("Sin Resultados");
        }
    }
/*
    private static void doPersist(Set<Result> resultados) {
        int res = 0;
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        for (Result result : resultados) {
            try {
                res = vdao.comprobarExistenciaVulnerabilidad(result.getVulnerabilidad().getName());
                if (res == 0) {
                    boolean flag = vdao.crearVulnerabilidad(result);
                    System.out.println(flag);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScannerTest.class.getName()).log(Level.SEVERE, "Excepci\u00f3n encontrada: {0}", ex.getMessage());
            }
        }
    }

    private static void registrarGrupoVulnerabilidad(Set<Result> resultados) {
        GruposDAO gdao = new GruposDAO();
        GrupoVulnerabilidadDAO gvdao = new GrupoVulnerabilidadDAO();
        for (Result result : resultados) {
            List<String> grupos = result.getGruposList();
            for (String grupo : grupos) {
                Grupo g = gdao.obtenerGrupoPorNombre(grupo);
                System.out.println("(" + g.getIdGrupo() + "," + result.getVulnerabilidad().getName() + ")");
                boolean flag = gvdao.registrarVulnerabilidadesEnGrupo(g.getIdGrupo(), result.getVulnerabilidad().getName());
                if (flag) {
                    System.out.println("Registro guardado");
                } else {
                    System.out.println("Registro no guardado");
                }
            }
        }
    }
    */

}
