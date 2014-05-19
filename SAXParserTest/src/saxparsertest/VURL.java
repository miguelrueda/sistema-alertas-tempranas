package saxparsertest;

public class VURL {

    private String URL;
    private String Description;

    public VURL(String URL, String Description) {
        this.URL = URL;
        this.Description = Description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "VURL{" + "URL=" + URL + ", Description=" + Description + '}';
    }

}
