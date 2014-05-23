package cve.entidades;

import java.util.Date;
import java.util.List;

/**
 *
 * Clase que representa a la entidad CVE donde se contienen diversos atributos
 * como nombre, severidad, fechas, el score de cvss, la lista de referencias y
 * el software vulnerable
 *
 * @author t41507
 * @version 20.05.2014
 */
public class CVE {

    /**
     * Atributos
     */
    private String name;
    private String severity;
    private Date published;
    private Date modified;
    //private String CVSS_score;
    private CVSS cvss;
    private String description;
    private List<CVEReference> references;
    private List<VulnSoftware> vuln_soft;

    /**
     * Constructor que recibe el nombre del CVE
     *
     * @param name el identificador de la forma CVE-XXXX-XXXX
     */
    public CVE(String name) {
        this.name = name;
    }

    /**
     * Constructor que recibe todos los parametros para el CVE
     *
     * @param name identificador de la forma CVE-XXXX-XXXX
     * @param severity severidad del CVE
     * @param published fecha de publicación
     * @param modified fecha de modificación
     * @param cvss CVSS
     * @param description descripción de la vulnerabilidad
     * @param references lista de referencias
     * @param vuln_soft lista de software vulnerable
     */
    public CVE(String name, String severity, Date published, Date modified, CVSS cvss, String description, List<CVEReference> references, List<VulnSoftware> vuln_soft) {
        this.name = name;
        this.severity = severity;
        this.published = published;
        this.modified = modified;
        this.cvss = cvss;
        this.description = description;
        this.references = references;
        this.vuln_soft = vuln_soft;
    }

    /**
     * GETTER
     *
     * @return identificador
     */
    public String getName() {
        return name;
    }

    /**
     * SETTER
     *
     * @param name identificador
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * GETTER
     *
     * @return severidad
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * SETTER
     *
     * @param severity severidad
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * GETTER
     *
     * @return fecha de publicación
     */
    public Date getPublished() {
        return published;
    }

    /**
     * SETTER
     *
     * @param published fecha de publicación
     */
    public void setPublished(Date published) {
        this.published = published;
    }

    /**
     * GETTER
     *
     * @return fecha de modificación
     */
    public Date getModified() {
        return modified;
    }

    /**
     * SETTER
     *
     * @param modified fecha de modificación
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * GETTER
     *
     * @return CVSS
     */
    public CVSS getCVSS() {
        return cvss;
    }

    /**
     * SETTER
     *
     * @param cvss CVSS
     */
    public void setCVSS(CVSS cvss) {
        this.cvss = cvss;
    }

    /**
     * GETTER
     *
     * @return descripción del CVE
     */
    public String getDescription() {
        return description;
    }

    /**
     * SETTER
     *
     * @param description descripción del CVE
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * GETTER
     *
     * @return lista de referencias
     */
    public List<CVEReference> getReferences() {
        return references;
    }

    /**
     * SETTER
     *
     * @param references lista de referencias
     */
    public void setReferences(List<CVEReference> references) {
        this.references = references;
    }

    /**
     * GETTER
     *
     * @return lista de software vulnerable
     */
    public List<VulnSoftware> getVuln_soft() {
        return vuln_soft;
    }

    /**
     * SETTER
     *
     * @param vuln_soft lista de software vulnerable
     */
    public void setVuln_soft(List<VulnSoftware> vuln_soft) {
        this.vuln_soft = vuln_soft;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CVE{" + "name=" + name + "}").append("\n");
        sb.append("{CVE severity=").append(severity).append(", published=").append(published).append(", modified=").append(modified).append("\n").append(cvss).append("}").append("\n");
        sb.append("{CVE description=").append(description).append("}\n");
        if (!(references == null ||  references.isEmpty())) {
            sb.append("References List").append("\n");
            for (CVEReference ref : references) {
                sb.append(ref.toString()).append("\n");
            }
        } else {
            sb.append("No references Available");
        }
        if (!(vuln_soft == null || vuln_soft.isEmpty())) {
            sb.append("Vulnerable Sofware list\n");
            for (VulnSoftware soft: vuln_soft) {
                sb.append(soft.toString()).append("\n");
            }
        } else {
            sb.append("No Vulnerable SW Available\n");
        }
        return sb.toString();
    }

}
