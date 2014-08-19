package org.banxico.ds.sisal.parser;

import org.banxico.ds.sisal.entities.Software;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CPEHandler extends DefaultHandler {

    private List<Software> softwares;
    private Software sw;
    private int id = 0;

    public CPEHandler() {
        softwares = new ArrayList<Software>();
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("cpe-item")) {
            id++;
            sw = generarObjeto(id, attributes.getValue("name"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("cpe-item")) {
            softwares.add(sw);
            sw = new Software();
        }
    }

    /*
        Los CPE Pueden ser del tipo:
        > cpe:/a:1024cms:1024_cms:0.7
        > cpe:/a:concrete5:concrete5:5.5.0:-:-:en-us
        > cpe:/a:cross-rss_plugin_project:wp-cross-rss:1.7::~~~wordpress~~
    */
    private Software generarObjeto(int id, String cpe) {
        StringTokenizer tokenizer = new StringTokenizer(cpe, ":");
        int ntokens = tokenizer.countTokens();
        String [] tokens = new String[ntokens];
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            tokens[i] = token;
            i++;
        }
        //cpe:/part:vendor:product:version:update:edition
        if (ntokens == 5 || ntokens == 6 || ntokens == 7 || ntokens == 8) {
            return new Software(id, tokens[2].replace("_", " "), tokens[3].replace("_", " "), tokens[4], -1, -1, "ND", "ND");
        }
        return new Software();
    }

}