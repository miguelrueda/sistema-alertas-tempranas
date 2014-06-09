package jsf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jpa.entities.SaFuentes;

/**
 * Servicio para obtener las fuentes que alimentar al sistema
 *
 * @author t41507
 * @version 09.06.2014
 */
@ManagedBean(name = "sourcesService")
@SessionScoped
public class SourcesService implements java.io.Serializable {

    /**
     * Atributos de serialización y Logger
     */
    private static final long serialVersionUID = -1L;
    private static final Logger LOG = Logger.getLogger(SourcesService.class.getName());
    /**
     * Atributos del servicio
     */
    private List<SaFuentes> sourcesList;

    /**
     * Constructor sin parámetros
     */
    public SourcesService() {
    }

    /**
     * Mét odo para obtener las fuentes que alimentan al sistema
     *
     * @return List con las fuentes de vulnerabilidades
     */
    public List<SaFuentes> obtenerFuentes() {
        sourcesList = new ArrayList<>();
        sourcesList.add(new SaFuentes(1, "Vulnerabilidades recientes", "http://nvd.nist.gov/download/nvdcve-recent.xml", new Date()));
        sourcesList.add(new SaFuentes(2, "Vulnerabilidades actualizadas", "http://nvd.nist.gov/download/nvdcve-modified.xml", new Date()));
        sourcesList.add(new SaFuentes(3, "Listado de Vulnerabilidades", "http://nvd.nist.gov/download/nvdcve-2014.xml", new Date()));
        //sourcesList.add(new SaFuentes(4, "Listado de Productos soportados", "http://static.nvd.nist.gov/feeds/xml/cpe/dictionary/official-cpe-dictionary_v2.3.xml", new Date()));
        return sourcesList;
    }

}
