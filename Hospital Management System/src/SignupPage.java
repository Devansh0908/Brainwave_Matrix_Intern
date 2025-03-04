import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignupPage {
    private JFrame signupFrame;

    public SignupPage() {
        signupFrame = new JFrame("Signup");
        signupFrame.setSize(400, 400);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        placeSignupComponents();
        signupFrame.setVisible(true);
    }

    private void placeSignupComponents() {
        JPanel panel = new JPanel();
        signupFrame.add(panel);
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

private static int adminCount = 0;
private static int doctorCount = 0;
private static int staffCount = 0;
private static int receptionistCount = 0;
private static int pharmacistCount = 0;

private String generateEmployeeId(String role) {
    String prefix = "";
    int count = 0;

    switch (role) {
        case "Admin":
            prefix = "ADM";
            count = ++adminCount;
            break;
        case "Doctor":
            prefix = "DOC";
            count = ++doctorCount;
            break;
        case "Ward Staff":
            prefix = "STF";
            count = ++staffCount;
            break;
        case "Receptionist":
            prefix = "REC";
            count = ++receptionistCount;
            break;
        case "Pharmacist":
            prefix = "PHA";
            count = ++pharmacistCount;
            break;
    }
    return prefix + count;

    }

    private void saveEmployeeData(String fullName, String age, String password, String employeeId, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) {
            writer.write(fullName + "," + age + "," + password + "," + employeeId + "," + role);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
