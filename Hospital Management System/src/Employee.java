public class Employee {
    private String employeeId;
    private String name;
    private EmployeeCategory category;
    private double salary;
    private boolean paid;
    private String username;
    private String password;
    private String role; // Added role field

    public Employee(String employeeId, String name, EmployeeCategory category, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.category = category;
        this.salary = salary;
        this.paid = false;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { // Added setName method
        this.name = name;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { // Added getRole method
        return role;
    }

    public void setRole(String role) { // Added setRole method
        this.role = role;
    }
}
