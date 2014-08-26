package org.banxico.ds.sisal.parser;

import org.banxico.ds.sisal.entities.Software;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * * Clase que realiza el parseo del XML a objetos de tipo CPE
 *
 * @author t41507
 * @version 26.08.2014
 */
public class CPEHandler extends DefaultHandler implements java.io.Serializable {

    /**
     * Atributo de Serialización
     */
    private static final long serialVersionUID = -1L;
    /**
     * Atributos del Manejador
     */
    private List<Software> softwares;
    private Software sw;
    private int id = 0;

    /**
     * Constructor
     */
    public CPEHandler() {
        softwares = new ArrayList<Software>();
    }

    /**
     * Getter
     *
     * @return lista de software encontrado
     */
    public List<Software> getSoftwares() {
        return softwares;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Si encuentra un elemento de tipo CPE generar el objeto CPE
        if (qName.equalsIgnoreCase("cpe-item")) {
            id++;
            sw = generarObjeto(id, attributes.getValue("name"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //Si se encuentra que cierra el elemento cpe agregarlo a la listay reiniciar la referencia del objeto
        if (qName.equalsIgnoreCase("cpe-item")) {
            softwares.add(sw);
            sw = new Software();
        }
    }

    /*
        
    */
    /**
     * Método que se encarga de generar objetos de tipo CPE a partir de la cadena del formato estandar
     * 
     * @param id llave del objeto
     * @param cpe cadena del CPE
     * @return objeto de tipo software con el contenido establecido
     * NOTA:
     * Los CPE Pueden ser del tipo:
     * > cpe:/a:1024cms:1024_cms:0.7
     * > cpe:/a:concrete5:concrete5:5.5.0:-:-:en-us
     * > cpe:/a:cross-rss_plugin_project:wp-cross-rss:1.7::~~~wordpress~~
     * Haciendo referencia al formato: cpe:/part:vendor:product:version:update:edition
     */
    private Software generarObjeto(int id, String cpe) {
        //Dividir la cadena de entrada por medio del token ':'
        StringTokenizer tokenizer = new StringTokenizer(cpe, ":");
        //Obtener el numero de tokens para inicializar un arreglo con la cantidad correspondiente
        int ntokens = tokenizer.countTokens();
        String [] tokens = new String[ntokens];
        int i = 0;
        //Iterar los tokens que se encuentran
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            tokens[i] = token;
            i++;
        }
        //A partir del numero de tokens generar el objeto de Software
        if (ntokens == 5 || ntokens == 6 || ntokens == 7 || ntokens == 8) {
            return new Software(id, tokens[2].replace("_", " "), tokens[3].replace("_", " "), tokens[4], -1, -1, "ND", "ND");
        }
        return new Software();
    }

}