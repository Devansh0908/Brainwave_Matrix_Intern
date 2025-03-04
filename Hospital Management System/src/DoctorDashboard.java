import javax.swing.*;
import java.io.BufferedReader; // Import BufferedReader for reading files
import java.io.FileReader; // Import FileReader for file operations
import java.io.IOException; // Import IOException for handling exceptions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Date;

public class DoctorDashboard {
    private String doctorName;
    private JFrame frame; // Make frame an instance variable

    public DoctorDashboard(String doctorName) {
        this.doctorName = doctorName; // Store the doctor's name

        frame = new JFrame("Doctor Dashboard"); // Initialize the frame
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Patient Management
        JButton viewPatientsButton = new JButton("View Patients");
        viewPatientsButton.setBounds(10, 20, 150, 25);
        panel.add(viewPatientsButton);

        JButton addPrescriptionButton = new JButton("Add Prescription");
        addPrescriptionButton.setBounds(10, 60, 150, 25);
        panel.add(addPrescriptionButton);

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.setBounds(10, 100, 150, 25);
        panel.add(updateStatusButton);

        // Appointments
        JButton viewAppointmentsButton = new JButton("View Appointments");
        viewAppointmentsButton.setBounds(200, 20, 150, 25);
        panel.add(viewAppointmentsButton);

        JButton markAppointmentButton = new JButton("Mark Appointment");
        markAppointmentButton.setBounds(200, 60, 150, 25);
        panel.add(markAppointmentButton);

        // Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 140, 150, 25);
        panel.add(logoutButton);

        // Add action listeners
        viewPatientsButton.addActionListener(e -> viewPatients());
        addPrescriptionButton.addActionListener(e -> addPrescription());
        updateStatusButton.addActionListener(e -> updateStatus());
        viewAppointmentsButton.addActionListener(e -> viewAppointments());
        markAppointmentButton.addActionListener(e -> markAppointment());
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginPage(); // Assuming you have a LoginPage class
        });
    }

    private void viewPatients() {
        // Logic to retrieve and display patient information
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
        } catch (IOException e) {
            patientInfo.append("Error retrieving patient information: ").append(e.getMessage());
        }
        JOptionPane.showMessageDialog(frame, patientInfo.toString());
    }

    private void addPrescription() {
        // Logic to create and save a new prescription
        String prescriptionDetails = JOptionPane.showInputDialog(frame, "Enter prescription details:"); // Prompt for prescription details

        if (prescriptionDetails != null && !prescriptionDetails.trim().isEmpty()) {
            // Save the prescription (this is a placeholder for actual saving logic)
            JOptionPane.showMessageDialog(frame, "Prescription added: " + prescriptionDetails);
        } else {
            JOptionPane.showMessageDialog(frame, "Prescription details cannot be empty.");
        }
    }

    private void updateStatus() {
        // Logic to update the status of a patient
        String patientId = JOptionPane.showInputDialog(frame, "Enter Patient ID to update status:");
        String newStatus = JOptionPane.showInputDialog(frame, "Enter new status:");
        if (patientId != null && newStatus != null) {
            // Update the patient's status (this is a placeholder for actual updating logic)
            JOptionPane.showMessageDialog(frame, "Status for Patient ID " + patientId + " updated to: " + newStatus);
        } else {
            JOptionPane.showMessageDialog(frame, "Patient ID and status cannot be empty.");
        }
    }

    private void viewAppointments() {
        // Logic to retrieve and display appointments for the doctor
        List<Appointment> appointments = AppointmentSystem.getInstance().getAppointmentsForDoctor(doctorName, new Date());
        if (appointments == null || appointments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No appointments scheduled for today!");
            return;
        }

        StringBuilder appointmentInfo = new StringBuilder("Appointments for " + doctorName + ":\n");
        for (Appointment appointment : appointments) {
            appointmentInfo.append("Patient: ").append(appointment.getPatientId()).append(" - Status: ").append(appointment.getStatus()).append("\n");
        }
        JOptionPane.showMessageDialog(frame, appointmentInfo.toString());
    }

    private void markAppointment() {
        List<Appointment> appointments = AppointmentSystem.getInstance().getAppointmentsForDoctor(doctorName, new Date());
        if (appointments == null) {
            JOptionPane.showMessageDialog(frame, "Error retrieving appointments!");
            return;
        }

        if (appointments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No appointments scheduled for today!");
            return;
        }

        // Create appointment list
        String[] appointmentList = appointments.stream()
            .map(a -> "Patient: " + a.getPatientId() + " - Status: " + a.getStatus())
            .toArray(String[]::new);

        // Show selection dialog
        String selectedAppointment = (String) JOptionPane.showInputDialog(
                frame,
                "Select appointment to update:",
                "Manage Appointments",
                JOptionPane.PLAIN_MESSAGE,
                null,
                appointmentList,
                appointmentList[0]
        );

        if (selectedAppointment != null) {
            String[] splitAppointment = selectedAppointment.split(" ");
            if (splitAppointment.length < 2) {
                JOptionPane.showMessageDialog(frame, "Invalid appointment selection!");
                return;
            }

            String patientId = splitAppointment[1]; // Correctly retrieve patient ID from the selected appointment

            // Status selection
            String[] options = {"Completed", "Ongoing", "Missed"};
            String status = (String) JOptionPane.showInputDialog(
                    frame,
                    "Update appointment status:",
                    "Appointment Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (status != null) {
                boolean success = AppointmentSystem.getInstance().updateAppointmentStatus(doctorName, patientId, status);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Appointment status updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update appointment status.");
                }
            }
        }
    }
}