package cveparser;

import java.util.Date;
import java.util.List;

public class CVE {

    private String name;
    private String severity;
    private Date published;
    private Date modified;
    //Common Vulnerability Scoring System (CVSS) provides a universal open and standardized method for rating IT vulnerabilities.
    private double CVSS_score;
    private String description;
    private List<CVEReference> references;
    private List<VulnSoftware> vuln_soft;

    public CVE(String name) {
        this.name = name;
    }

    public CVE(String name, String severity, Date published, Date modified, double CVSS_score, String description, List<CVEReference> references, List<VulnSoftware> vuln_soft) {
        this.name = name;
        this.severity = severity;
        this.published = published;
        this.modified = modified;
        this.CVSS_score = CVSS_score;
        this.description = description;
        this.references = references;
        this.vuln_soft = vuln_soft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public double getCVSS_score() {
        return CVSS_score;
    }

    public void setCVSS_score(double CVSS_score) {
        this.CVSS_score = CVSS_score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CVEReference> getReferences() {
        return references;
    }

    public void setReferences(List<CVEReference> references) {
        this.references = references;
    }

    public List<VulnSoftware> getVuln_soft() {
        return vuln_soft;
    }

    public void setVuln_soft(List<VulnSoftware> vuln_soft) {
        this.vuln_soft = vuln_soft;
    }

    @Override
    public String toString() {
        return "CVE{" + "name=" + name + ", severity=" + severity + ", published=" + published + ", modified=" + modified + ", CVSS_score=" + CVSS_score + ", description=" + description + ", references=" + references + ", vuln_soft=" + vuln_soft + '}';
    }

}
