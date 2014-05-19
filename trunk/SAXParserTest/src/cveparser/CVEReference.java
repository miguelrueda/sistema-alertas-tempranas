package cveparser;

public class CVEReference {

    private String source;
    private String url;

    public CVEReference(String url, String source) {
        this.url = url;
        this.source = source;
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
