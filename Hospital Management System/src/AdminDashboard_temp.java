import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList; // Importing only ArrayList to avoid ambiguity
import java.util.List; // Importing List specifically

public class AdminDashboard_temp {
    private static final String EMPLOYEE_FILE = "employees.txt"; // Ensure the file exists before operations

    public static void display() {
        JFrame adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(600, 400);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        adminFrame.add(panel);
        placeAdminComponents(panel);
        
        adminFrame.setVisible(true);
    }

    private static void placeAdminComponents(JPanel panel) {
        panel.setLayout(null);

        JButton addButton = new JButton("Add Employee");
        addButton.setBounds(10, 20, 150, 25);
        panel.add(addButton);

        JButton removeButton = new JButton("Remove Employee");
        removeButton.setBounds(10, 60, 150, 25);
        panel.add(removeButton);

        JButton updateButton = new JButton("Update Employee");
        updateButton.setBounds(10, 100, 150, 25);
        panel.add(updateButton);

        addButton.addActionListener(e -> openAddEmployeePage());
        removeButton.addActionListener(e -> removeEmployee());
        updateButton.addActionListener(e -> updateEmployee());
    }

    private static void openAddEmployeePage() {
        JFrame frame = new JFrame("Add Employee");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeAddEmployeeComponents(panel, frame);
        frame.setVisible(true);
    }

    private static void placeAddEmployeeComponents(JPanel panel, JFrame frame) {
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

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 80, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"Admin", "Doctor", "Ward Staff", "Receptionist", "Pharmacist"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(150, 80, 165, 25);
        panel.add(roleComboBox);

        JButton addButton = new JButton("Add Employee");
        addButton.setBounds(10, 120, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String fullName = nameText.getText();
            String age = ageText.getText();
            String role = (String) roleComboBox.getSelectedItem();
            String employeeId = String.valueOf(System.currentTimeMillis());
            saveEmployeeData(fullName, age, role, employeeId);
            JOptionPane.showMessageDialog(null, "Employee added successfully! Employee ID is: " + employeeId);
            frame.dispose();
        });
    }

    private static void saveEmployeeData(String name, String age, String role, String employeeId) {
        // Check if the file exists before writing
        File employeeFile = new File(EMPLOYEE_FILE);
        if (!employeeFile.exists()) {
            try {
                employeeFile.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating employee file: " + e.getMessage());
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE, true))) {
            writer.write(employeeId + "," + name + "," + age + "," + role);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeEmployee() {
        String employeeId = JOptionPane.showInputDialog("Enter Employee ID to remove:");
        List<String> employees = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(employeeId + ",")) {
                    employees.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE))) {
                for (String employee : employees) {
                    writer.write(employee);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Employee removed successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Employee ID not found!");
        }
    }

    private static void updateEmployee() {
        String employeeId = JOptionPane.showInputDialog("Enter Employee ID to update:");
        List<String> employees = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(employeeId + ",")) {
                    String[] parts = line.split(",");
                    String newName = JOptionPane.showInputDialog("Enter new name:", parts[1]);
                    String newAge = JOptionPane.showInputDialog("Enter new age:", parts[2]);
                    String newRole = JOptionPane.showInputDialog("Enter new role:", parts[3]);
                    String updatedData = employeeId + "," + newName + "," + newAge + "," + newRole;
                    employees.add(updatedData);
                    found = true;
                } else {
                    employees.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE))) {
                for (String employee : employees) {
                    writer.write(employee);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Employee updated successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Employee ID not found!");
        }
    }
}
