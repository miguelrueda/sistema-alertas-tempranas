/*
REF
http://viralpatel.net/blogs/java-load-csv-file-to-database/
*/
package ejemplocsvparser;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Ejemplo2 {

    public static void main(String[] args) throws IOException {
        List<Employee> emps = parseCSVFileLineByLine();
        System.out.println("*************************");
        parseCSVFileAsList();
        System.out.println("*************************");
        parseCSVToBeanList();
        System.out.println("*************************");
        writeCSVData(emps);
    }

    private static List<Employee> parseCSVFileLineByLine() throws IOException {
        CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(Ejemplo2.class.getResourceAsStream("/resources/employees.csv"))), ',');
        List<Employee> emps = new ArrayList<>();
        String[] record = null;
        reader.readNext();
        while ((record = reader.readNext()) != null) {
            Employee emp = new Employee();
            emp.setId(record[0]);
            emp.setName(record[1]);
            emp.setRole(record[2]);
            emp.setSalary(record[3]);
            emps.add(emp);
        }
        reader.close();
        System.out.println(emps);
        return emps;
    }

    private static void parseCSVFileAsList() throws IOException {
        CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(Ejemplo2.class.getResourceAsStream("/resources/employees.csv"))), ',');
        List<Employee> emps = new ArrayList<>();
        List<String[]> records = reader.readAll();
        Iterator<String[]> iterator = records.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            String[] record = iterator.next();
            Employee emp = new Employee(record[0], record[1], record[2], record[3]);
            emps.add(emp);
        }
        reader.close();
        System.out.println(emps);
    }

    private static void parseCSVToBeanList() throws FileNotFoundException {
        HeaderColumnNameTranslateMappingStrategy<Employee> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<>();
        beanStrategy.setType(Employee.class);

        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("ID", "id");
        columnMapping.put("Name", "name");
        columnMapping.put("Role", "role");
        columnMapping.put("Salary", "salary");

        beanStrategy.setColumnMapping(columnMapping);

        CsvToBean<Employee> csvToBean = new CsvToBean<>();
        File file = new File(Ejemplo2.class.getResource("/resources/employees.csv").getFile());
        CSVReader reader = new CSVReader(new FileReader(file), ',');
        List<Employee> emps = csvToBean.parse(beanStrategy, reader);
        System.out.println(emps);
    }

    private static void writeCSVData(List<Employee> emps) throws IOException {
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, '#');
        List<String []> data = toStringArray(emps);
        csvWriter.writeAll(data);
        csvWriter.close();
        System.out.println(writer);
    }

    private static List<String[]> toStringArray(List<Employee> emps) {
        List<String []> records = new ArrayList<>();
        records.add(new String[]{"ID", "Name", "Role", "Salary"});
        Iterator<Employee> it = emps.iterator();
        while (it.hasNext()) {
            Employee emp = it.next();
            records.add(new String[]{emp.getId(), emp.getName(), emp.getRole(), emp.getSalary()});
        }
        return records;
    }

}