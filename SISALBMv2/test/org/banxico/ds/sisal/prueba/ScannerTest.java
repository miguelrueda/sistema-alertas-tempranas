package org.banxico.ds.sisal.prueba;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import org.banxico.ds.sisal.scanner.Result;
import org.banxico.ds.sisal.scanner.ScannerBean;

public class ScannerTest {
    
    private final static ScannerBean scanner = new ScannerBean();
    
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_WEEK, -1);
        Set<Result> resultados = scanner.doRecentScan(cal.getTime());
        System.out.println("Se encontraron: " + resultados.size() + " resultados.");
        for (Result result : resultados) {
            System.out.println(result);
        }
    }
    
}
