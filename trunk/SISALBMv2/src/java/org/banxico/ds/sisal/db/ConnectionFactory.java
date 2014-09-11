package org.banxico.ds.sisal.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa el patron singleton para devolver una instancia de la
 * conexión a BD
 *
 * @author t41507
 * @version 07.08.2014
 */
public class ConnectionFactory {

    /**
     * Atributo LOGGER
     */
    private static final Logger LOG = Logger.getLogger(ConnectionFactory.class.getName());
    /**
     * Instancia para retornar
     */
    private static ConnectionFactory instance = new ConnectionFactory();

    /**
     * Constructor de la clase que se encarga de inicializar el driver 
     *
     */
    private ConnectionFactory() {
        try {
            //Instanciar y un objeto de propiedades y leer las propiedades de la bd
            Properties prop = new Properties();
            InputStream is = ConnectionFactory.class.getResourceAsStream("db.properties");
            prop.load(is);
            //Obtener la clase del driver y el url de conexión
            String driverClass = prop.getProperty("driver");
            String connUrl = prop.getProperty("url");
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Ocurrio un error al buscar la clase del Driver: {0}", e.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al abrir el archivo de propiedades: {0}", ex.getMessage());
        }
    }

    /**
     * Método que devuelve la instancia de la clase connection Factory
     *
     * @return instancia de la conexión
     */
    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * Método que se encarga de obtener la conexión con la BD, obteniendo la url
     * de la BD de un archivo de propiedades localizado en el mismo paquete
     *
     * @return objeto de tipo connection que hace referncia a la conexión
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            //Instanciar un objeto de propiedades y cargar el archivo de propiedades
            Properties prop = new Properties();
            InputStream is = ConnectionFactory.class.getResourceAsStream("db.properties");
            prop.load(is);
            String connUrl = prop.getProperty("url");
            connection = DriverManager.getConnection(connUrl);
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "ConnectionFactory#getConnection() - Ocurrio una excepci\u00f3n al iniciar la conexi\u00f3n SQL: {0}", ex.getMessage());
        } catch (SecurityException ex) {
            LOG.log(Level.SEVERE, "ConnectionFactory#getConnection() - Ocurrio una excepci\u00f3n de seguridad: {0}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, "ConnectionFactory#getConnection() - Ocurrio una excepci\u00f3n de argumento invalido: {0}", ex.getMessage());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "ConnectionFactory#getConnection() -  Ocurrio un error al abrir el archivo de propiedades: {0}", ex.getMessage());
        }
        return connection;
    }

}
