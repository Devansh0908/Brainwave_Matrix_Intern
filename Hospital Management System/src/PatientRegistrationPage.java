import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class PatientRegistrationPage {
    private JFrame frame;
    
    public PatientRegistrationPage() {
        frame = new JFrame("Patient Registration");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Full Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(10, 20, 100, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 200, 25);
        panel.add(nameText);

        // Age
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(10, 50, 100, 25);
        panel.add(ageLabel);

        JTextField ageText = new JTextField(20);
        ageText.setBounds(150, 50, 200, 25);
        panel.add(ageText);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(10, 80, 100, 25);
        panel.add(genderLabel);

        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderCombo = new JComboBox<>(genders);
        genderCombo.setBounds(150, 80, 200, 25);
        panel.add(genderCombo);

        // Contact Number
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setBounds(10, 110, 120, 25);
        panel.add(contactLabel);

        JTextField contactText = new JTextField(20);
        contactText.setBounds(150, 110, 200, 25);
        panel.add(contactText);

        // Email (optional)
        JLabel emailLabel = new JLabel("Email (optional):");
        emailLabel.setBounds(10, 140, 120, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(150, 140, 200, 25);
        panel.add(emailText);

        // Aadhar Number
        JLabel aadharLabel = new JLabel("Aadhar Number:");
        aadharLabel.setBounds(10, 170, 120, 25);
        panel.add(aadharLabel);

        JTextField aadharText = new JTextField(20);
        aadharText.setBounds(150, 170, 200, 25);
        panel.add(aadharText);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 200, 100, 25);
        panel.add(addressLabel);

        JTextArea addressText = new JTextArea();
        addressText.setBounds(150, 200, 200, 50);
        addressText.setLineWrap(true);
        panel.add(addressText);

        // Admission Status
        JLabel admissionLabel = new JLabel("Admission Status:");
        admissionLabel.setBounds(10, 260, 120, 25);
        panel.add(admissionLabel);

        String[] statusOptions = {"Not Admitted", "Admitted", "Here for Checkup"};
        JComboBox<String> admissionCombo = new JComboBox<>(statusOptions);
        admissionCombo.setBounds(150, 260, 200, 25);
        panel.add(admissionCombo);

        // Emergency Contact
        JLabel emergencyLabel = new JLabel("Emergency Contact:");
        emergencyLabel.setBounds(10, 300, 120, 25);
        panel.add(emergencyLabel);

        JTextField emergencyText = new JTextField(20);
        emergencyText.setBounds(150, 300, 200, 25);
        panel.add(emergencyText);

        // Submit Button
        JButton submitButton = new JButton("Register Patient");
        submitButton.setBounds(150, 340, 150, 25);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String patientId = generatePatientId();
                String fullName = nameText.getText();
                String age = ageText.getText();
                String gender = (String) genderCombo.getSelectedItem();
                String contact = contactText.getText();
                String email = emailText.getText();
                String aadhar = aadharText.getText();
                String address = addressText.getText();
                String admissionStatus = (String) admissionCombo.getSelectedItem();
                String emergencyContact = emergencyText.getText();

                if (isPatientExists(aadhar, contact)) {
                    JOptionPane.showMessageDialog(frame, "Patient Already Registered!");
                } else if (validateInput(fullName, age, contact, aadhar, address, emergencyContact)) {
                    savePatientData(patientId, fullName, age, gender, contact, email, aadhar, address, emergencyContact, admissionStatus);
                    int option = JOptionPane.showConfirmDialog(frame, 
                        "Patient Registered Successfully!\nPatient ID: " + patientId + 
                        "\nDo you want to edit the details?", 
                        "Registration Complete", 
                        JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.NO_OPTION) {
                        frame.dispose();
                    }
                }
            }
        });
    }

    private String generatePatientId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
            int count = 0;
            while (reader.readLine() != null) count++;
            return String.format("PID%03d", count + 1);
        } catch (IOException e) {
            return "PID001"; // Fallback if file doesn't exist
        }
    }

    private boolean isPatientExists(String aadhar, String contact) {
        try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && (parts[6].equals(aadhar) || parts[4].equals(contact))) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return false;
    }

    private boolean validateInput(String fullName, String age, String contact, 
                                String aadhar, String address, String emergencyContact) {
        if (fullName.isEmpty() || age.isEmpty() || contact.isEmpty() || 
            aadhar.isEmpty() || address.isEmpty() || emergencyContact.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields except email are required!");
            return false;
        }
        return true;
    }

    private void savePatientData(String patientId, String fullName, String age, String gender, 
                               String contact, String email, String aadhar, String address, 
                               String emergencyContact, String admissionStatus) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patients.txt", true))) {
            String wardBed = "N/A";
            if (admissionStatus.equals("Admitted")) {
                wardBed = JOptionPane.showInputDialog(frame, "Assign ward/bed (e.g., General Ward 101):");
                if (wardBed == null || wardBed.trim().isEmpty()) {
                    wardBed = "N/A";
                }
            }

            String data = String.join(",",
                patientId,
                fullName,
                age,
                gender,
                contact,
                email,
                aadhar,
                address.replace(",", ";"), // Replace commas in address to preserve CSV format
                emergencyContact,
                admissionStatus,
                wardBed
            );

            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving patient data: " + e.getMessage());
        }
    }
}
