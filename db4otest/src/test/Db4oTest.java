package test;

import com.db4o.ObjectContainer;
import controlador.PilotoController;
import db.DBAccess;
import entidad.Piloto;

public class Db4oTest {
    
    public static void main(String[] args) {
        
        ObjectContainer db = DBAccess.connectionDB("Pilotos");
        //PilotoController.obtenerPiloto(db);
        Piloto piloto1 = new Piloto("Miguel", 2000);
        Piloto piloto2 = new Piloto("Carlos", 3);
        //PilotoController.nuevoPiloto(db, piloto1);
        //PilotoController.nuevoPiloto(db, piloto2);
        PilotoController.obtenerPilotos(db);
        //PilotoController.actualizarPiloto(db, "Miguel", "Miguel2", 5000);
        //PilotoController.eliminarPiloto(db, "Miguel");
        
        DBAccess.closeConnection(db);
    }
    
}
