package softwareparser;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    
    public static void main(String[] args) {
        try {
            List<Software> softwareList = parseFileLineByLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static List<Software> parseFileLineByLine() throws FileNotFoundException, IOException {
        File file = new File(Parser.class.getResource("swsecunia.csv").getFile());
        CSVReader reader = new CSVReader(new FileReader(file));
        List<Software> swsList = new ArrayList<>();
        String [] record = null;
        reader.readNext();
        while ((record = reader.readNext()) != null) {
            Software sw = new Software();
            if (!(record[0].length() == 0)) {
                sw.setVendor(record[0]);
            } else {
                sw.setVendor("Not Defined");
            }
            sw.setProduct(record[1]);
            sw.setVersion(record[2]);
            if (!(record[3].length() == 0)) {
                sw.setType(Integer.parseInt(record[3]));
            }
            if (!(record[4].length() == 0)) {
                sw.setEndoflife(Integer.parseInt(record[4]));
            } else {
                sw.setEndoflife(-1);
            }
            
            swsList.add(sw);
        }
        reader.close();
        System.out.println(swsList);
        return swsList;
    }
    
}