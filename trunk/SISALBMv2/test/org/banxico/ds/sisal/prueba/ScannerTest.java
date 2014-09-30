package org.banxico.ds.sisal.prueba;

import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banxico.ds.sisal.dao.GruposDAO;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
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
    private final static GruposDAO gdao = new GruposDAO();
    
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
        Set<Result> resultados = //scanner.doRecentScan();
        scanner.doMonthlyScan();
        System.out.println("Se encontraron: " + resultados.size() + " resultados");
        if (!resultados.isEmpty()) {
            //doPersist(resultados);
            for (Result result : resultados) {
                System.out.println("Vulnerabilidad: " + result.getVulnerabilidad().getName());
                for (String grupo : result.getGruposList()) {
                    Grupo found = gdao.obtenerGrupoPorNombre(grupo);
                    System.out.println("> (" + found.getIdGrupo() + ") en grupo: " + grupo + "/" + found.getNombre() + " responsable.responsable");
                }
            }
        } else {
            System.out.println("Sin Resultados");
        }
    }
    
    private static void doPersist(Set<Result> resultados) {
        int res = 0;
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        for (Result result : resultados) {
            try {
                //boolean flag = vdao.crearVulnerabilidad(result);
                res = vdao.comprobarExistenciaVulnerabilidad(result.getVulnerabilidad().getName());
                if (res == 0) {
                    System.out.println("Creando la vulnerabilidad: " + result.getVulnerabilidad().getName());
                    vdao.crearVulnerabilidad(result);
                } else {
                    System.out.println("Ya existe la vulnerabilidad: " + result.getVulnerabilidad().getName());
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScannerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}