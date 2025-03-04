import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard {
    private JFrame frame;
    private EmployeeManager employeeManager;
    private List<Patient> patients; // List to manage patients


    public AdminDashboard() {
        employeeManager = new EmployeeManager(); // Initialize EmployeeManager
        patients = new ArrayList<>(); // Initialize patient list
        frame = new JFrame("Admin Dashboard");

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeAdminComponents(panel);
        
        frame.setVisible(true);
    }

    private void placeAdminComponents(JPanel panel) {
        panel.setLayout(null);

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setBounds(10, 20, 150, 25);
        panel.add(addEmployeeButton);

        JButton removeEmployeeButton = new JButton("Remove Employee");
        removeEmployeeButton.setBounds(10, 60, 150, 25);
        panel.add(removeEmployeeButton);

        JButton updateEmployeeButton = new JButton("Update Employee");
        updateEmployeeButton.setBounds(10, 100, 150, 25);
        panel.add(updateEmployeeButton);

        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.setBounds(10, 140, 150, 25);
        panel.add(addPatientButton);

        JButton removePatientButton = new JButton("Remove Patient");
        removePatientButton.setBounds(10, 180, 150, 25);
        panel.add(removePatientButton);

        JButton updatePatientButton = new JButton("Update Patient");
        updatePatientButton.setBounds(10, 220, 150, 25);
        panel.add(updatePatientButton);

        JButton viewBillsButton = new JButton("View Bills");
        viewBillsButton.setBounds(10, 260, 150, 25);
        panel.add(viewBillsButton);

        // Add action listeners
        addEmployeeButton.addActionListener(e -> openAddEmployeePage());
        removeEmployeeButton.addActionListener(e -> removeEmployee());
        updateEmployeeButton.addActionListener(e -> updateEmployee());
        addPatientButton.addActionListener(e -> addPatient());
        removePatientButton.addActionListener(e -> removePatient());
        updatePatientButton.addActionListener(e -> updatePatient());
        viewBillsButton.addActionListener(e -> viewBills());
    }

    private void openAddEmployeePage() {
        JFrame addEmployeeFrame = new JFrame("Add Employee");
        addEmployeeFrame.setSize(400, 400);
        addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel addEmployeePanel = new JPanel();
        addEmployeeFrame.add(addEmployeePanel);
        placeAddEmployeeComponents(addEmployeePanel, addEmployeeFrame);
        
        addEmployeeFrame.setVisible(true);
    }

    private void placeAddEmployeeComponents(JPanel panel, JFrame addEmployeeFrame) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 165, 25);
        panel.add(nameText);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(10, 50, 80, 25);
        panel.add(ageLabel);

        JTextField ageText = new JTextField(20);
        ageText.setBounds(150, 50, 165, 25);
        panel.add(ageText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 80, 165, 25);
        panel.add(passwordText);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(10, 110, 120, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(150, 110, 165, 25);
        panel.add(confirmPasswordText);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 140, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"Admin", "Doctor", "Ward Staff", "Receptionist", "Pharmacist"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(150, 140, 165, 25);
        panel.add(roleCombo);

        JButton addButton = new JButton("Add Employee");
        addButton.setBounds(150, 180, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String employeeName = nameText.getText();
            String employeeAge = ageText.getText();
            String employeePassword = new String(passwordText.getPassword());
            String employeeConfirmPassword = new String(confirmPasswordText.getPassword());
            String employeeRole = (String) roleCombo.getSelectedItem();

            if (employeeName.isEmpty() || employeeAge.isEmpty() || employeePassword.isEmpty() || employeeConfirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(addEmployeeFrame, "All fields are required!");
                return;
            }

            if (!employeePassword.equals(employeeConfirmPassword)) {
                JOptionPane.showMessageDialog(addEmployeeFrame, "Passwords do not match!");
                return;
            }

            // Create a new Employee object
            String employeeId = String.valueOf(System.currentTimeMillis()); // Generate a unique ID
            EmployeeCategory category = EmployeeCategory.valueOf(employeeRole.toUpperCase()); // Assuming EmployeeCategory is an enum
            Employee newEmployee = new Employee(employeeId, employeeName, category, 0.0); // Salary is set to 0.0 for now
            if (employeeManager.hireEmployee(newEmployee, "Admin")) { // Assuming the user role is Admin
                JOptionPane.showMessageDialog(addEmployeeFrame, "Employee added successfully!");
                addEmployeeFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(addEmployeeFrame, "Failed to add employee.");
            }
        });
    }

    private void removeEmployee() {
        String employeeId = JOptionPane.showInputDialog("Enter Employee ID to remove:");
        if (employeeId != null && !employeeId.trim().isEmpty()) {
            boolean success = employeeManager.removeEmployee(employeeId);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Employee removed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found!");
            }
        }
    }

    private void updateEmployee() {
        String employeeId = JOptionPane.showInputDialog("Enter Employee ID to update:");
        if (employeeId != null && !employeeId.trim().isEmpty()) {
            String newEmployeeName = JOptionPane.showInputDialog("Enter New Employee Name:");
            String newEmployeeRole = JOptionPane.showInputDialog("Enter New Employee Role:");
            if (newEmployeeName != null && newEmployeeRole != null) {
                Employee updatedEmployee = new Employee(employeeId, newEmployeeName, EmployeeCategory.valueOf(newEmployeeRole.toUpperCase()), 0.0); // Age and password can be updated later
                boolean success = employeeManager.updateEmployee(employeeId, updatedEmployee);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Employee updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Employee not found!");
                }
            }
        }
    }

    private void addPatient() {
        JFrame addPatientFrame = new JFrame("Add Patient");
        addPatientFrame.setSize(400, 400);
        addPatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel addPatientPanel = new JPanel();
        addPatientFrame.add(addPatientPanel);
        placeAddPatientComponents(addPatientPanel, addPatientFrame);
        
        addPatientFrame.setVisible(true);
    }

    private void placeAddPatientComponents(JPanel panel, JFrame addPatientFrame) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 165, 25);
        panel.add(nameText);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(10, 50, 80, 25);
        panel.add(ageLabel);

        JTextField ageText = new JTextField(20);
        ageText.setBounds(150, 50, 165, 25);
        panel.add(ageText);

        JLabel conditionLabel = new JLabel("Condition:");
        conditionLabel.setBounds(10, 80, 80, 25);
        panel.add(conditionLabel);

        JTextField conditionText = new JTextField(20);
        conditionText.setBounds(150, 80, 165, 25);
        panel.add(conditionText);

        JButton addButton = new JButton("Add Patient");
        addButton.setBounds(150, 120, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String patientName = nameText.getText();
            String patientAge = ageText.getText();
            String patientCondition = conditionText.getText();

            if (patientName.isEmpty() || patientAge.isEmpty() || patientCondition.isEmpty()) {
                JOptionPane.showMessageDialog(addPatientFrame, "All fields are required!");
                return;
            }

            // Create a new Patient object and add it to the list
            String patientId = String.valueOf(System.currentTimeMillis()); // Generate a unique ID
            Patient newPatient = new Patient(patientId, patientName, Integer.parseInt(patientAge), patientCondition); // Assuming Patient has a constructor
            patients.add(newPatient); // Add patient to the list
            JOptionPane.showMessageDialog(addPatientFrame, "Patient added successfully!");
            addPatientFrame.dispose();
        });
    }

    private void removePatient() {
        String patientId = JOptionPane.showInputDialog("Enter Patient ID to remove:");
        if (patientId != null && !patientId.trim().isEmpty()) {
            boolean success = patients.removeIf(patient -> patient.getPatientId().equals(patientId)); // Assuming Patient has getPatientId method
            if (success) {
                JOptionPane.showMessageDialog(frame, "Patient removed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Patient not found!");
            }
        }
    }

    private void updatePatient() {
        String patientId = JOptionPane.showInputDialog("Enter Patient ID to update:");
        if (patientId != null && !patientId.trim().isEmpty()) {
            String newPatientName = JOptionPane.showInputDialog("Enter New Patient Name:");
            String newPatientCondition = JOptionPane.showInputDialog("Enter New Patient Condition:");
            if (newPatientName != null && newPatientCondition != null) {
                for (Patient patient : patients) {
                    if (patient.getPatientId().equals(patientId)) { // Assuming Patient has getPatientId method
                        patient.setName(newPatientName); // Assuming Patient has setName method
                        patient.setCondition(newPatientCondition); // Assuming Patient has setCondition method
                        JOptionPane.showMessageDialog(frame, "Patient updated successfully!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Patient not found!");
            }
        }
    }

    private void viewBills() {
        // Logic to display bills
        StringBuilder billDetails = new StringBuilder("Bills:\n");
        // Assuming we have a method to get bills, for example:
        // List<Bill> bills = billManager.getAllBills();
        // For demonstration, let's assume we have some dummy data
        List<String> bills = List.of("Bill 1: $100", "Bill 2: $200"); // Dummy data
        for (String bill : bills) {
            billDetails.append(bill).append("\n");
        }
        JOptionPane.showMessageDialog(frame, billDetails.toString());

    }



    // Other methods for managing patients, bills, etc. would go here...

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
