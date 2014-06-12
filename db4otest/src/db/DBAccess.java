package db;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class DBAccess {
 
    public static ObjectContainer connectionDB(String dataBase) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dataBase);
        return db;
    }
    
    public static void closeConnection(ObjectContainer db) {
        try {
            db.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
