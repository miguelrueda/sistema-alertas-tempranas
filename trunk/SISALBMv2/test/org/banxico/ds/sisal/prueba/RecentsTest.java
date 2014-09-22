package org.banxico.ds.sisal.prueba;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;

public class RecentsTest {

    public static void main(String[] args) {
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        /*
         List<Vulnerabilidad> recientes = vdao.obtenerVulnerabilidadesRecientes();
         for (Vulnerabilidad vuln : recientes) {
         System.out.println(vuln);
         }
         */
        int res = 0;
        HashMap<String, Integer> datos = vdao.obtenerEstadisticasFabricantes();
        System.out.println(datos);
        HashMap<String, Integer> estadistica = new HashMap<String, Integer>();
        Iterator<String> iterator = datos.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (datos.get(key) > 50) {
                estadistica.put(key, datos.get(key));
            }
        }
        System.out.println(estadistica);
        TreeMap sorted = new TreeMap(estadistica);
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(sorted);
        System.out.println("json = " + json);
        System.out.println("Resultado: " + res);
        
        /*
         Iterator<String> keySetIterator = datos.keySet().iterator();
         while (keySetIterator.hasNext()) {
         String llave = keySetIterator.next();
         System.out.println("{llave:" + llave + ",valor:" + datos.get(llave) + "}");
         }
         */
    }

}
