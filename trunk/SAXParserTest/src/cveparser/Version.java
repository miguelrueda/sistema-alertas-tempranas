package cveparser;

public class Version {

    private String number;
    private String edition;

    public Version(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Version{" + "number=" + number + ", edition=" + edition + '}';
    }

}
