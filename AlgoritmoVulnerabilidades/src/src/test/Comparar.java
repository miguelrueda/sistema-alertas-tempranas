package src.test;

import cve.entidades.CVE;
import cve.entidades.Version;
import cve.entidades.VulnSoftware;
import cve.parser.CVEParser;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

//TIPO 2 para SW y 1 para OS
public class Comparar {

    private static final Logger LOG = Logger.getLogger(Comparar.class.getName());
    private static final List<Software> swList;

    static {
        swList = new ArrayList<>();
        swList.add(new Software(1, "Adobe Systems", "Adobe Encore 5.x", "5.x", 2, 0, "OSI", "Miguel Rueda"));
        swList.add(new Software(2, "Adobe Systems", "Adobe Media Encoder CS6", "6.x", 2, 0, "RH", "Carlos Peña"));
        swList.add(new Software(3, "Adobe Systems", "Macromedia Flash 8 Video Encoder 1.x", "1.x", 2, 0, "OSI", "Miguel Rueda"));
        swList.add(new Software(4, "Apache Software Foundation", "Apache MyFaces 1.1.x", "1.1.x", 2, 0, "Sistemas", "Carlos Vela"));
        swList.add(new Software(5, "Apache Software Foundation", "Apache-SSL 1.3.x", "1.3.x", 2, 0, "OSI", "Miguel Rueda"));
        swList.add(new Software(6, "Apache Software Foundation", "Apache Maven 2.2.x", "2.2.x", 2, -1, "RH", "Carlos Peña"));
        swList.add(new Software(7, "Canonical Ltd.", "Ubuntu Linux 13.10", "13.10", 1, 0, "OSI", "Miguel Rueda"));
        swList.add(new Software(8, "Canonical Ltd.", "Ubuntu Linux 9.10", "9.10", 1, -1, "Sistemas", "Carlos Vela"));
        swList.add(new Software(9, "Canonical Ltd.", "Bazaar 2.x", "2.x", 2, -1, "OSI", "Miguel Rueda"));
        swList.add(new Software(10, "Canonical Ltd.", "Ubuntu Linux 14.04", "14.04", 1, 0, "RH", "Carlos Peña"));
        swList.add(new Software(11, "Canonical Ltd.", "Ubuntu Linux 13.04", "13.04", 1, 1, "OSI", "Miguel Rueda"));
        swList.add(new Software(12, "Cisco", "Cisco SAN-OS 3.x (MDS 9000 Switches)", "3.x", 1, -1, "Sistemas", "Carlos Vela"));
        swList.add(new Software(13, "Cisco", "Cisco Jabber 8.x", "8.x", 2, 0, "OSI", "Miguel Rueda"));
        swList.add(new Software(14, "Cisco", "Cisco IP Communicator 2.x", "2.x", 2, -1, "RH", "Carlos Peña"));
        swList.add(new Software(15, "Microsoft", "Microsoft Windows 7", "7", 1, -1, "OSI", "Miguel Rueda"));
        swList.add(new Software(16, "Microsoft", "Microsoft Windows 8", "8", 1, 0, "Sistemas", "Carlos Vela"));
        swList.add(new Software(17, "Apache Software Foundation", "Apache MyFaces 2.0.x", "2.0.x", 2, 0, "OSI", "Miguel Rueda"));
        swList.add(new Software(18, "Microsoft", "Microsoft Internet Explorer 11.x", "11.x", 2, 0));
        swList.add(new Software(19, "Microsoft", "Microsoft Internet Explorer 10.x", "10.x", 2, 0));
        swList.add(new Software(20, "Microsoft", "Microsoft Internet Explorer 9.x", "9.x", 2, 0));
        swList.add(new Software(21, "Cisco", "Cisco WebEx Meetings 1.x", "1.x", 2, 0, "RH", "Miguel Rueda"));
        swList.add(new Software(22, "Cisco", "Cisco WebEx Meetings Server 2.x", "2.x", 2, 0, "OSI", "Carlos Vela"));
        swList.add(new Software(23, "IBM", "security access manager for web appliance", "8.0", 2, -1, "RH", "Carlos Peña"));
        swList.add(new Software(24, "IBM", "security access manager for web 8.0 firmware", "8.0.x", 2, 0, "RH", "Miguel Rueda"));
        swList.add(new Software(25, "Apache Software Foundation", "Apache Maven 2.2.x", "2.2.x", 2, -1, "AB", "Carlos Peña"));
        swList.add(new Software(26, "Apache Software Foundation", "Apache Maven 2.2.x", "2.2.x", 2, -1, "CD", "Carlos Peña"));
        swList.add(new Software(27, "Canonical Ltd.", "Ubuntu Linux 14.04", "14.04", 1, 0, "AB", "Carlos Peña"));
        swList.add(new Software(28, "Canonical Ltd.", "Ubuntu Linux 14.04", "14.04", 1, 1, "EF", "Miguel Rueda"));
    }
    //private static final String fileName = "nvdcve-recent.xml";
    private static final String fileName = "nvdcve-2014.xml";
    private static List<CVE> cveList;

    public static void main(String[] args) {
        //imprimirLista();
        leerArchivo();
        doCompare();
        //compararCadenas();
    }

    private static void imprimirLista() {
        //LOG.log(Level.INFO, "1. Se obtiene todo el sw de la base de datos");
        for (Software sw : swList) {
            System.out.println(sw.toString());
        }
    }

    private static void leerArchivo() {
        //LOG.log(Level.INFO, "2. Se obtienen todos los cves recientes");
        CVEParser parser = new CVEParser();
        parser.setFiltro("");
        cveList = parser.getListCVE(Comparar.class.getResourceAsStream(fileName));
    }

    private static void doCompare() {
        //LOG.log(Level.INFO, "3. Se itera la lista de cves comparando con el sw existente");
        System.out.println("Existen: " + cveList.size() + " entradas de vulnerabilidad.");
        System.out.println("Voy a comparar con: " + swList.size() + " productos.");
        List<Result> result = new ArrayList<>();
        System.out.println("Leer todas las entradas de la lista de vulnerabilidades");
        int aux = 0;
        for (CVE vuln : cveList) {
            //System.out.println("Obtener la lista de sw vulnerable de cada vulnerabilidad");
            List<VulnSoftware> vulnSWList = vuln.getVuln_soft();
            if (!vulnSWList.isEmpty()) {
                for (VulnSoftware vulnsw : vulnSWList) {
                    for (int i = 0; i < swList.size(); i++) {
                        //Caso que el nombre es el mismo
                        if (vulnsw.getVendor().equalsIgnoreCase(swList.get(i).getFabricante())) {
                            //System.out.println(aux + " - Tengo lista con elementos del producto: " + vulnsw.getVendor());
                            //System.out.println(vulnsw);
                            //System.out.println("** Nombres: " + vulnsw.getName() + "/" + swList.get(i).getNombre());
                            String name = vulnsw.getName().replace("_", " ");
                            if (swList.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
                                //Comparar la o las versiones
                                for (Version version : vulnsw.getVersion()) {
                                    //System.out.println("*** Versiones: " + version.getNumber() + "/" + swList.get(i).getVersion());
                                    if (version.getNumber().equals(swList.get(i).getVersion())) {
                                        //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
                                        Result nResult = new Result(vuln, swList.get(i));
                                        result.add(nResult);
                                    } else if (version.getNumber().contains(swList.get(i).getVersion().replace("x", ""))) {
                                        //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
                                        Result nResult = new Result(vuln, swList.get(i));
                                        result.add(nResult);
                                    }
                                }
                            }
                        } else if (swList.get(i).getFabricante().toLowerCase().startsWith(vulnsw.getVendor().toLowerCase())) {
                            //System.out.println(aux + " - Tengo lista con elementos del producto: " + vulnsw.getVendor());
                            //System.out.println("** Nombres: " + vulnsw.getName() + "/" + swList.get(i).getNombre());
                            String name = vulnsw.getName().replace("_", " ");
                            if (swList.get(i).getNombre().toLowerCase().contains(name.toLowerCase())) {
                                for (Version version : vulnsw.getVersion()) {
                                    //System.out.println("*** Versiones: " + version.getNumber() + "/" + swList.get(i).getVersion());
                                    if (version.getNumber().equals(swList.get(i).getVersion())) {
                                        //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
                                        Result nResult = new Result(vuln, swList.get(i));
                                        result.add(nResult);
                                    } else if (version.getNumber().contains(swList.get(i).getVersion().replace("x", ""))) {
                                        //System.out.println("**** Agregado: " + swList.get(i).getFabricante() + "/" + swList.get(i).getNombre() + "/" + swList.get(i).getVersion());
                                        Result nResult = new Result(vuln, swList.get(i));
                                        result.add(nResult);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //System.out.println(aux + " ** Tengo lista Vacia: " + vuln.getName());
            }
            aux++;
        }
        System.out.println("Iterado: " + aux + " veces");
        procesarResultados(result);
    }

    private static void compararCadenas() {
        System.out.println("Cadena 1: 8.0.0.2");
        System.out.println("Cadena 2: 8.0.x");
        System.out.println("Cadena 3: 8.0.0.3");
        String cad1 = "8.0.0.2";
        String cad2 = "8.0.x".replace("x", " ").trim();
        if (cad1.contains(cad2)) {
            System.out.println("OK");
            System.out.println(cad1);
            System.out.println(cad2);
        } else {
            System.out.println("NON");
        }
    }

    private static void procesarResultados(List<Result> result) {
        Set<Result> items = new LinkedHashSet<>();
        Set<Result> duplicates = new LinkedHashSet<>();
        int aux = 0;
        for (Result res : result) {
            if (items.contains(res)) {
                aux++;
                duplicates.add(res);
            } else {
                items.add(res);
            }
        }
        /*
         for (Result res : items) {
         System.out.println(res.getVulnerabilidad().getName() + "/" + res.getSw().getNombre());
         }*/
        System.out.println("Lista Original con: " + items.size() + " elementos diferentes y: " + aux + " repetidos.");
        List<Result> nueva = new ArrayList<>();
        nueva.addAll(items);
        List<Result> listafinal = new ArrayList<>();
        int ib = 0, total = 0;
        for (int i = 0; i < nueva.size(); i++) {
            Result nuevo = new Result();
            Result actual = nueva.get(i);
            Result siguiente = new Result();
            List<Software> swlist = new ArrayList<>();

            if ((i + 1) < nueva.size()) {
                siguiente = nueva.get(i + 1);
                System.out.println("Actual: " + actual.getVulnerabilidad().getName() + " - Siguiente:" + siguiente.getVulnerabilidad().getName());
                if (actual.getVulnerabilidad().equals(siguiente.getVulnerabilidad())) {
                    System.out.println("SW Actual: " + actual.getSw().getNombre() + " a la lista: " + actual.getVulnerabilidad().getName());
                    swlist.add(actual.getSw());
                    ib++;
                } else {
                    System.out.println("SW: " + actual.getSw().getNombre() + " a la lista: " + actual.getVulnerabilidad().getName());
                    swlist.add(actual.getSw());
                    System.out.println("Añadir swlist");
                    System.out.println("Agregar: " + actual.getVulnerabilidad().getName());
                    nuevo.setVulnerabilidad(actual.getVulnerabilidad());
                    nuevo.setSwList(swlist);
                    System.out.println("IB: " + swlist.size());
                    listafinal.add(nuevo);
                    total++;
                }
            } else if (i < nueva.size()) {
                System.out.println("SW: " + actual.getSw().getNombre());
                swlist.add(actual.getSw());
                System.out.println("Añadir swlist");
                System.out.println("Agregar: " + actual.getVulnerabilidad().getName());
                nuevo.setVulnerabilidad(actual.getVulnerabilidad());
                nuevo.setSwList(swlist);
                System.out.println("IB: " + swlist.size());
                listafinal.add(nuevo);
                total++;
            }
        }
        System.out.println("Total: " + total);
        /*
         int auxT = 0;
         for (int i = 0; i < nueva.size(); i++) {
         Result actual = nueva.get(i);
         Result nuevo = new Result();
         if ((i + 1) < nueva.size()) {
         Result siguiente = nueva.get(i + 1);
         if (actual.getVulnerabilidad().equals(siguiente.getVulnerabilidad())) {
         //System.out.println("Juntar " + actual.getSw().getNombre() + " con: " + siguiente.getSw().getNombre() + " de vulnerabilidad: " + actual.getVulnerabilidad().getName());
         Software swactual = actual.getSw();
         Software swsiguiente = siguiente.getSw();
         List<Software> swlist = new ArrayList<>();
         swlist.add(swactual);
         swlist.add(swsiguiente);
         nuevo = new Result(actual.getVulnerabilidad(), swlist);
         listafinal.add(nuevo);
         auxT++;
         } else {
         //System.out.println("Agregar a la lista: " + actual.getVulnerabilidad().getName());
         List<Software> swlist = new ArrayList<>();
         Software swactual = actual.getSw();
         swlist.add(swactual);
         nuevo = new Result(actual.getVulnerabilidad(), swlist);
         listafinal.add(nuevo);
         auxT++;
         }
         }
         }
         System.out.println("Filtrar lista: Actual con " + listafinal.size() + " elementos");
         Set<Result> items2 = new LinkedHashSet<>();
         Set<Result> duplicates2 = new LinkedHashSet<>();
         int aux2 = 0;
         for (Result res : listafinal) {
         if (items2.contains(res)) {
         aux2++;
         duplicates2.add(res);
         } else {
         items2.add(res);
         }
         }
         for (Result temp : items2) {
         System.out.println(temp.getVulnerabilidad().getName());
         List<Software> swlist = temp.getSwList();
         for (Software software : swlist) {
         System.out.println(software.getNombre());
         }
         }
         System.out.println("Lista final con: " + items2.size() + " elementos");
         */
    }

}
//Crear lista nueva
//Iterar la lista resultante
//Comparar el nombre de la vulnerabilidad
//Si es la misma vulnerabilidad crear un resultado con la lista de los sw agregados
//Otro caso ignorar
