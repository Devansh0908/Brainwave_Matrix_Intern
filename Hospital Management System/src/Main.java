import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;




public class Main {
    private static JFrame frame;
    
    // Initialize counts for each role

    private static int adminCount = 0;
    private static int doctorCount = 0;
    private static int staffCount = 0;
    private static int receptionistCount = 0;
    private static int pharmacistCount = 0;

    public static void main(String[] args) {
        // Create the main application window
        frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        
        // Create a panel for the login form
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Employee ID:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(150, 80, 80, 25);
        panel.add(signupButton);

        // Add action listeners for buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String employeeId = userText.getText();
                String password = new String(passwordText.getPassword());
        // Logic to validate login credentials
        String role = ""; // Placeholder for role determination

                if (isAdmin(employeeId, password)) {
                    role = "Admin";
                    new AdminDashboard();
                    frame.dispose();

                } else if (employeeId.startsWith("DOC")) {
                    role = "Doctor";
                    new DoctorDashboard(employeeId); // Pass employeeId to the DoctorDashboard

                    frame.dispose();
                } else if (employeeId.startsWith("STF")) {
                    role = "Ward Staff";
                    new WardStaffDashboard();
                    frame.dispose();
                } else if (employeeId.startsWith("PHA")) {
                    role = "Pharmacist";
                    new PharmacistDashboard();
                    frame.dispose();
                } else if (employeeId.startsWith("REC")) {



                    role = "Receptionist";
                    new ReceptionistDashboard();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials or role!");
                }

            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSignupPage();
            }
        });
    }

    private static boolean isAdmin(String employeeId, String password) {
        // Logic to check if the user is an Admin
        if (employeeId.startsWith("ADM")) {
            return true; // Admin user
        }
        return false; // Not an Admin

    }

    private static void openSignupPage() {
        JFrame signupFrame = new JFrame("Signup");
        signupFrame.setSize(400, 400);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel signupPanel = new JPanel();
        signupFrame.add(signupPanel);
        placeSignupComponents(signupPanel, signupFrame);
        
        signupFrame.setVisible(true);
    }

    private static void placeSignupComponents(JPanel panel, JFrame signupFrame) {
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
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(150, 140, 165, 25);
        panel.add(roleComboBox);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(10, 180, 80, 25);
        panel.add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fullName = nameText.getText();
                String age = ageText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());
                String role = (String) roleComboBox.getSelectedItem();
                String employeeId = generateEmployeeId(role);

                if (password.equals(confirmPassword)) {
                    saveEmployeeData(fullName, age, password, employeeId, role);
                    JOptionPane.showMessageDialog(null, "Signup successful! Your Employee ID is: " + employeeId);
                    signupFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                }
            }
        });
    }

    private static String generateEmployeeId(String role) {
        // Logic to generate a unique Employee ID
        String prefix = "";

        switch (role) {
            case "Admin":
                prefix = "ADM";
                return prefix + (++adminCount);
            case "Doctor":
                prefix = "DOC";
                return prefix + (++doctorCount);
            case "Ward Staff":
                prefix = "STF";
                return prefix + (++staffCount);
            case "Receptionist":
                prefix = "REC";
                return prefix + (++receptionistCount);
            case "Pharmacist":
                prefix = "PHA";
                return prefix + (++pharmacistCount);
            default:
                return null; // Should not reach here
        }
    }

    private static void saveEmployeeData(String fullName, String age, String password, String employeeId, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) {
            // Hash the password using SHA-256
            String hashedPassword = SecurityUtils.hashPassword(password);

            writer.write(fullName + "," + age + "," + hashedPassword + "," + employeeId + "," + role);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }
}
