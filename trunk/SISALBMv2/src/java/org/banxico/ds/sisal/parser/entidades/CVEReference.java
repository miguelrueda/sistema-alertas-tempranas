package org.banxico.ds.sisal.parser.entidades;

/**
 * Clase que representa la entidad referencia sobre un CVE
 *
 * @author t41507
 * @version 20.05.2014
 */
public class CVEReference implements java.io.Serializable {

    private static final long serialVersionUID = -1L;
    
    /**
     * Atributos
     * 
     */
    private String source;
    private String url;

    /**
     * Constructor
     */
    public CVEReference() {
    }

    /**
     * Constructor
     *
     * @param url dirección de la referencia
     * @param source fuente de la referencia
     */
    public CVEReference(String url, String source) {
        this.url = url;
        this.source = source;
    }

    /**
     * GETTER
     *
     * @return fuente de la referencia
     */
    public String getSource() {
        return source;
    }

    /**
     * SETTER
     *
     * @param source fuente de la referencia
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * GETTER
     *
     * @return url de la referencia
     */
    public String getUrl() {
        return url;
    }

    /**
     * SETTER
     *
     * @param url url de la referencia
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CVEReference{" + "source=" + source + ", url=" + url + '}';
    }

}
