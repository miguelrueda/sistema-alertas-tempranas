package ejemplocsvparser;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EjemploCSVParser {
    
    private static final String ADDRESS_FILE = "resources\\addresses.csv";
    private static final Logger LOG = Logger.getLogger(EjemploCSVParser.class.getName());

    public static void main(String[] args) throws IOException {
        try {
            InputStream is = EjemploCSVParser.class.getResourceAsStream("/resources/addresses.csv");
            CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(is)));
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("Name: [" + nextLine[0] + "]\nAddress: [" + nextLine[1] + "]\nEmail: [" + nextLine[2] + "]");
            }
            is.close();
            is = EjemploCSVParser.class.getResourceAsStream("/resources/addresses.csv");
            CSVReader reader2 = new CSVReader(new BufferedReader(new InputStreamReader(is)));
            List<String []> allElements = reader2.readAll();
            System.out.println("Tamaño: " + allElements.size());
            StringWriter sw = new StringWriter();
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            System.out.println("Generated CSV File:");
            System.out.println(sw.toString());
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, "Ocurrio un error: {0}", e.getMessage());
        }

    }
    
}