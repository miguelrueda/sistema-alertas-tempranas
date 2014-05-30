package jsf.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author t41507
 */
@ManagedBean
@ApplicationScoped
public class AssetsService implements java.io.Serializable {

    private static final Logger LOG = Logger.getLogger(AssetsService.class.getName());
    private final static String[] vendors;
    private final static String[] softwares;
    private final static String[] versions;

    static {
        vendors = new String[5];
        vendors[0] = "Microsoft";
        vendors[1] = "Apache";
        vendors[2] = "Oracle";
        vendors[3] = "Adobe";
        vendors[4] = "Canonical";
        softwares = new String[5];
        softwares[0] = "Windows 7";
        softwares[1] = "Adobe Reader";
        softwares[2] = "MySQL";
        softwares[3] = "Tomcat";
        softwares[4] = "Ubuntu";
        versions = new String[5];
        versions[0] = "1.0";
        versions[1] = "4.0.1";
        versions[2] = "5.0";
        versions[3] = "1.3";
        versions[4] = "1.1.3";
    }

    /**
     * Creates a new instance of AssetsBean
     */
    public AssetsService() {
    }

    public List<Asset> createList(int size) {
        List<Asset> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Asset((i + 1), getRandomVendor(), getRandomSoftware(), getRandomVersion()));
        }
        return list;
    }

    public String getRandomVendor() {
        return vendors[(int) (Math.random() * 5)];
    }

    public String getRandomSoftware() {
        return softwares[(int) (Math.random() * 5)];
    }

    public String getRandomVersion() {
        return versions[(int) (Math.random() * 5)];
    }

}
