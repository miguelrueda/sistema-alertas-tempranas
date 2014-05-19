package cveparser;

public class CVEReference {

    private String source;
    private String url;

    public CVEReference(String source, String url) {
        this.source = source;
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CVEReference{" + "source=" + source + ", url=" + url + '}';
    }

}
