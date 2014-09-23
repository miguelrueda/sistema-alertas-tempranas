package org.banxico.ds.sisal.prueba;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import org.banxico.ds.sisal.dao.VulnerabilityDAO;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class RecentsTest {

    public static void main(String[] args) {
        VulnerabilityDAO vdao = new VulnerabilityDAO();
        /*
        Ejemplo de obtener todas las vulnerabilidades recientes
         List<Vulnerabilidad> recientes = vdao.obtenerVulnerabilidadesRecientes();
         for (Vulnerabilidad vuln : recientes) {
         System.out.println(vuln);
         }
         */
        /* 
        Intento para generar JSON a partir de las estadisticas

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
        */        
        /*
        Iterar los valores dentro de un Mapa
         Iterator<String> keySetIterator = datos.keySet().iterator();
         while (keySetIterator.hasNext()) {
         String llave = keySetIterator.next();
         System.out.println("{llave:" + llave + ",valor:" + datos.get(llave) + "}");
         }
         */
        /*
        HashMap<String, Integer> datos = vdao.obtenerEstadisticasFabricantes();
        Iterator<String> iterator = datos.keySet().iterator();
        List<Estadistica> listaEstadistica = new ArrayList<Estadistica>();
        Estadistica e;
        int res = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (datos.get(key) > 50) {
                e = new Estadistica(key, datos.get(key));
                listaEstadistica.add(e);
                res += datos.get(key);
            }
            
        }
        Gson gson = new Gson();
        String json = gson.toJson(listaEstadistica);
        System.out.println(json);
        System.out.println("Resultado = " + res);
        */
        HashMap<String, Integer> datos = vdao.obtenerEstadisticasFabricantes();
        TreeMap sorted = new TreeMap(datos);
        Iterator<String> keysetIt = sorted.keySet().iterator();
        ArrayList<Object> chartDataSet = new ArrayList<Object>();
        while (keysetIt.hasNext()) {
            String key = keysetIt.next();
            if (datos.get(key) > 50) {
                JsonObject temp = new JsonObject();
                temp.addProperty("fabricante", key);
                temp.addProperty("cuenta", datos.get(key));
                chartDataSet.add(temp);
            }
        }
        System.out.println(chartDataSet.toString());
    }

    private static HashMap<String, Double> calcularPorcentajes(HashMap<String, Integer> datos, int total) {
        HashMap<String, Double> data = new HashMap<String, Double>();
        Iterator<String> keysetIterator = datos.keySet().iterator();
        while (keysetIterator.hasNext()) {
            String llave = keysetIterator.next();
            Double porcentaje = ((datos.get(llave).doubleValue() / total) * 100);
            DecimalFormat df = new DecimalFormat("#.##");
            data.put(llave, Double.valueOf(df.format(porcentaje)));
        }
        return data;
    }
}