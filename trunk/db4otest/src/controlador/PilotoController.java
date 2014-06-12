package controlador;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import entidad.Piloto;
import java.util.List;

public class PilotoController {
    
    //public static void nuevoPiloto(ObjectContainer db, String nombre, int puntos) {
    public static void nuevoPiloto(ObjectContainer db, Piloto piloto) {
        try {
            //Piloto piloto = new Piloto(nombre, puntos);
            db.store(piloto);
            System.out.println("Agregado: " + piloto.getNombre());
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //db.close();
        }
    }

    public static void obtenerPilotos(ObjectContainer db) {
        try {
            //Piloto proto = new Piloto(null, 0);
            //ObjectSet result = db.queryByExample(proto);
            ObjectSet result = db.queryByExample(Piloto.class);
            listar(result);
        } catch (Db4oIOException | DatabaseClosedException e) {
            System.out.println(e.getMessage());
        } finally {
            //db.close();
        }
    }

    private static void listar(List<?> result) {
        System.out.println("Pilotos: " + result.size());
        /*
        while (result.hasNext()) {
            System.out.println(result.next());
            System.out.println("------------------");
        }
        */
        for (Object o : result) {
            System.out.println(o);
        }
    }
    
    public static void actualizarPiloto(ObjectContainer db, String name, String newName, int newPoints) {
        try {
            ObjectSet result = db.queryByExample(new Piloto(name, 0));
            Piloto encontrado = (Piloto) result.next();
            if (newPoints != 0) {
                encontrado.actualizarPuntos(newPoints);
            }
            if (newName != null) {
                encontrado.actualizarNombre(newName);
            }
            db.store(encontrado);
            System.out.println("Actualizado: " + encontrado.getNombre());
        } catch (Db4oIOException | DatabaseClosedException e) {
            System.out.println(e.getMessage());
        } finally {
            //db.close();
        }
    }
    
    public static void eliminarPiloto(ObjectContainer db, String nombre) {
        try {
            ObjectSet result = db.queryByExample(new Piloto(nombre, 0));
            Piloto encontrado = (Piloto) result.next();
            db.delete(encontrado);
            System.out.println("Eliminado: " + encontrado.getNombre());
        } catch (Db4oIOException | DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println(e.getMessage());
           e.printStackTrace();
        } finally {
            //db.cloe();
        }
    }
    
    public static void getByName(ObjectContainer db, String nombre) {
        Piloto p = new Piloto(nombre, 0);
        ObjectSet result = db.queryByExample(p);
        listar(result);
    }
    
    public static void getByPoints(ObjectContainer db, int points) {
        Piloto p = new Piloto(null, points);
        ObjectSet result = db.queryByExample(p);
        listar(result);
    }
    
}
