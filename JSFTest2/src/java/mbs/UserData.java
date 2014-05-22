package mbs;

import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

@ManagedBean(name = "userData", eager = true)
@SessionScoped
public class UserData implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    private Date date;
    private String name;
    private String dept;
    private int age;
    private double salary;

    /*
    private static final ArrayList<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("John", "Marketing", 30, 2000.00),
            new Employee("Robert", "Sales", 25, 2500.00)
            ));
    */
    private static final Employee[] employees = new Employee[]{
        new Employee("John", "Marketing", 30, 2000.00),
        new Employee("Robert", "Marketing", 35, 3000.00),
        new Employee("Mark", "Sales", 25, 2500.00)
    };

    public UserData() {
    }
    
    
    private final DataModel<Employee> employeeDataModel = new ArrayDataModel<>(employees);

    public Employee[] getEmployees() {
        return employees;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    /*
    public String addEmployee() {
        Employee employee = new Employee(name, dept, age, salary);
        employees.add(employee);
        return null;
    }
    
    public String deleteEmployee(Employee employee) {
        employees.remove(employee);
        return null;
    }
    */
    public String editEmployee(Employee employee) {
        employee.setCanEdit(true);
        return null;
    }
    
    public String saveEmployees() {
        for (Employee employee : employees) {
            employee.setCanEdit(false);
        }
        return null;
    }
    
    public String getWelcomeMessage() {
        return "Hello " + name;
    }

}