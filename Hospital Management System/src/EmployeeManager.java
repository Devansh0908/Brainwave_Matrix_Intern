import java.util.ArrayList;

public class EmployeeManager {
    private ArrayList<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
    }

    public boolean hireEmployee(Employee employee, String role) {
        // Add logic to hire an employee
        employee.setRole(role);
        return employees.add(employee);
    }

    public boolean removeEmployee(String employeeId) {
        return employees.removeIf(employee -> employee.getEmployeeId().equals(employeeId));
    }

    public boolean updateEmployee(String employeeId, Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId().equals(employeeId)) {
                employees.set(i, updatedEmployee);
                return true;
            }
        }
        return false;
    }
}
