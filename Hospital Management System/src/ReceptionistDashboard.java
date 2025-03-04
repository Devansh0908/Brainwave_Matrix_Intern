import javax.swing.*;
import java.io.BufferedReader; // Import BufferedReader for reading files
import java.io.FileReader; // Import FileReader for file operations
import java.io.IOException; // Import IOException for handling exceptions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistDashboard {
    private JFrame frame;

    public ReceptionistDashboard() {
        frame = new JFrame("Receptionist Dashboard");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Patient Registration Button
        JButton registerButton = new JButton("Register New Patient");
        registerButton.setBounds(10, 20, 200, 25);
        panel.add(registerButton);

        // View Patients Button
        JButton viewButton = new JButton("View Patients");
        viewButton.setBounds(10, 60, 200, 25);
        panel.add(viewButton);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 100, 200, 25);
        panel.add(logoutButton);

        // Add action listeners
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PatientRegistrationPage();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement view patients functionality
                StringBuilder patientInfo = new StringBuilder("Patient List:\n");
                try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length >= 5) { // Ensure there are enough fields
                            patientInfo.append("ID: ").append(data[0])
                                       .append(", Name: ").append(data[1])
                                       .append(", Age: ").append(data[2])
                                       .append(", Gender: ").append(data[3])
                                       .append(", Contact: ").append(data[4])
                                       .append("\n");
                        }
                    }
                } catch (IOException ex) {
                    patientInfo.append("Error retrieving patient information: ").append(ex.getMessage());
                }
                JOptionPane.showMessageDialog(frame, patientInfo.toString());

            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPage();
            }
        });
    }
}
