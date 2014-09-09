package org.banxico.ds.sisal.prueba;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
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
        Set<Result> resultados = scanner.doCompleteScan("05/07/2014", "01/08/2014");
        if (!resultados.isEmpty()) {
            doPersist(resultados);
        } else {
            System.out.println("Sin Resultados");
        }
    }

    private static void doPersist(Set<Result> resultados) {
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        for (Result result : resultados) {
            try {
                boolean flag = vdao.crearVulnerabilidad(result);
            } catch (SQLException ex) {
                Logger.getLogger(ScannerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}