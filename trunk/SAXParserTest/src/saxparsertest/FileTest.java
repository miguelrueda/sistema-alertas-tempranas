package saxparsertest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileTest {
    
    public static void main(String[] args) throws IOException {
        
        String url = SAXParserTest.class.getResource("").getPath();
        System.out.println("URL -->" + url);
        
        /*
        File f = new File("D:\\devenv\\trunk\\SAXParserTest\\src\\saxparsertest\\Test2.xml");
        System.out.println("[Absoulte Path] - " + f.getAbsolutePath());
        System.out.println("[Relative Path] - "  + f.getCanonicalPath());
        System.out.println("[Can read] - " + f.canRead());
        System.out.println("[Can write] - " + f.canWrite());
        */
        InputStream is = FileTest.class.getResourceAsStream("Test2.xml");
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }

    }
    
}
