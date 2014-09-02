package org.banxico.ds.sisal.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import org.xml.sax.InputSource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.banxico.ds.sisal.entities.Software;
import org.xml.sax.SAXException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.banxico.ds.sisal.db.ConnectionFactory;

/**
 * * Clase que se encarga de realizar el parseo del flujo de entrada desde la URL proporcionada
 *
 * @author t41507
 * @version 26.08.14
 */
public class CPEParser implements java.io.Serializable {
    
    /**
     * Atributos de serialización y Logger
     */
    private static final Logger LOG = Logger.getLogger(CPEParser.class.getName());
    private static final long serialVersionUID = -1L;
    /**
     * Atributos del Parser
     */
    private SAXParserFactory saxParserFactory;
    private SAXParser saxParser;
    /**
     * Atributos de la BD
     */
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private final static String sqlRetrieveDictionaryBytes = "SELECT * FROM FuenteApp WHERE idFuenteApp = ?;";
    
    /**
     * Método que se encarga de retornar la lista de Software obtenido del flujo
     *
     * @return
     */
    public List<Software> getList() {
        try {
            //Abrir el flujo y obtener la referencia del SAXParser
            //InputStream flujo = CPEParser.class.getResourceAsStream("official-cpe-dictionary_v2.3.xml");
            conn = getConnection();
            pstmt = conn.prepareStatement(sqlRetrieveDictionaryBytes);
            pstmt.setInt(1, 5);
            rs = pstmt.executeQuery();
            byte [] flujoxml = null;
            while (rs.next()) {
                flujoxml = rs.getBytes("contenido_xml");
            }
            InputStream flujo = new ByteArrayInputStream(flujoxml);
            saxParserFactory = SAXParserFactory.newInstance();
            List<Software> softwares = new ArrayList<Software>();
            //Si el flujo esta disponible realizar el parseo
            if (flujo.available() != 0) {
                //Iniciar SAXParser y el Manejador CPE
                saxParser = saxParserFactory.newSAXParser();
                CPEHandler cpeHandler = new CPEHandler();
                //Abrir un lector para establecerlo en la fuente de entrada
                Reader reader = new InputStreamReader(flujo, "UTF-8");
                InputSource issrc = new InputSource(reader);
                //Realizar el parseo de la entrada
                saxParser.parse(issrc, cpeHandler);
                //Obtener la lista de softwares y retornarla
                softwares = cpeHandler.getSoftwares();
                return softwares;
            }
        } catch (IOException e) {
            LOG.log(Level.INFO, "CPEParser#getList() - Ocurrio un problema con los flujos de entrada/salida: {0}", e.getMessage());
        } catch (ParserConfigurationException e) {
            LOG.log(Level.INFO, "CPEParser#getList() - Ocurrio un problema con la configuración del parser: {0}", e.getMessage());
        } catch (SAXException e) {
            LOG.log(Level.INFO, "CPEParser#getList() - Ocurrio un problema con el parser SAX: {0}", e.getMessage());
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "CPEParser#getList() - Ocurrio una excepci\u00f3n al preparar el statement: {0}", ex.getMessage());
        }
        return new ArrayList<Software>();
    }
    
    private static Connection getConnection() {
        Connection con = ConnectionFactory.getInstance().getConnection();
        return con;
    }
    
}