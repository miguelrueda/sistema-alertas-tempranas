package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
    
    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            InputStream inputStream = ReadProperties.class.getResourceAsStream("db.properties");
            prop.load(inputStream);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            System.out.println(driver);
            System.out.println(url + user + password);
        } catch (IOException e) {
        }
    }
    
}