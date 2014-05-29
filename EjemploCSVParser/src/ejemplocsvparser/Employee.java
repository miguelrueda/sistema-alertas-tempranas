package ejemplocsvparser;

public class Employee implements java.io.Serializable {

    private String id;
    private String name;
    private String role;
    private String salary;

    public Employee() {
    }

    public Employee(String Id, String name, String role, String salary) {
        this.id = Id;
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", role=" + role + ", salary=" + salary + '}';
    }

}
