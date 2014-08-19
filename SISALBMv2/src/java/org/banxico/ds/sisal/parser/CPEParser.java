package org.banxico.ds.sisal.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;
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

public class CPEParser {
    
    private static final Logger LOG = Logger.getLogger(CPEParser.class.getName());
    private SAXParserFactory saxParserFactory;
    private SAXParser saxParser;
    
    public List<Software> getList() {
        try {
            InputStream flujo = CPEParser.class.getResourceAsStream("official-cpe-dictionary_v2.3.xml");
            saxParserFactory = SAXParserFactory.newInstance();
            List<Software> softwares = new ArrayList<Software>();
            if (flujo.available() != 0) {
                saxParser = saxParserFactory.newSAXParser();
                CPEHandler cpeHandler = new CPEHandler();
                Reader reader = new InputStreamReader(flujo, "UTF-8");
                InputSource issrc = new InputSource(reader);
                saxParser.parse(issrc, cpeHandler);
                softwares = cpeHandler.getSoftwares();
                return softwares;
            }
        } catch (IOException e) {
            LOG.log(Level.INFO, "Ocurrio un problema al realizar el parseo del software: {0}", e.getMessage());
        } catch (ParserConfigurationException e) {
            LOG.log(Level.INFO, "Ocurrio un problema al realizar el parseo del software: {0}", e.getMessage());
        } catch (SAXException e) {
            LOG.log(Level.INFO, "Ocurrio un problema al realizar el parseo del software: {0}", e.getMessage());
        }
        return new ArrayList<Software>();
    }
    
}