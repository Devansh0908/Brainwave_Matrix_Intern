import javax.swing.*;
import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.util.List; // Import List for patientRecords
import java.util.ArrayList; // Import ArrayList for patientRecords

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PatientManagementPage {
    private JFrame frame;
    private JTable patientTable;
    private DefaultTableModel tableModel;
private List<PatientRecord> patientRecords = new ArrayList<>(); // Initialize the patient records list


    public PatientManagementPage() {
        frame = new JFrame("Patient Management");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        placeComponents(panel);
        
        frame.setVisible(true);
        loadPatientData();
    }

    private void placeComponents(JPanel panel) {
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Patient ID", "Name", "Contact Number"});
        
        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Table Panel
        String[] columns = {"Patient ID", "Name", "Age", "Gender", "Contact", "Email", "Aadhar", "Address", "Emergency Contact"};
        tableModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton viewButton = new JButton("View Details");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        // Add components to main panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        searchButton.addActionListener(e -> searchPatients(searchField.getText(), (String) searchCriteria.getSelectedItem()));
        viewButton.addActionListener(e -> viewPatientDetails());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
    }

    private void loadPatientData() {
        patientRecords = new ArrayList<>(); // Initialize the patient records list
        tableModel.setRowCount(0); // Clear existing data
        try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                PatientRecord record = new PatientRecord(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4]);
                patientRecords.add(record); // Add record to the list
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading patient data: " + e.getMessage());
        }
    }

private PatientRecord getPatientRecord(int row) {
    if (row < 0 || row >= patientRecords.size()) {
        throw new IndexOutOfBoundsException("Invalid row index");
    }
    return patientRecords.get(row); // Retrieve the patient record from the list
}




    private void searchPatients(String query, String criteria) {
        tableModel.setRowCount(0); // Clear existing data
        try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                switch (criteria) {
                    case "Patient ID":
                        if (data[0].toLowerCase().contains(query.toLowerCase())) {
                            tableModel.addRow(data);
                        }
                        break;
                    case "Name":
                        if (data[1].toLowerCase().contains(query.toLowerCase())) {
                            tableModel.addRow(data);
                        }
                        break;
                    case "Contact Number":
                        if (data[4].contains(query)) {
                            tableModel.addRow(data);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error searching patient data: " + e.getMessage());
        }
    }

    private void viewPatientDetails() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient first!");
            return;
        }
        
        // Create a tabbed pane for detailed view
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Personal Info Tab
        JPanel personalInfoPanel = createPersonalInfoPanel(selectedRow);
        tabbedPane.addTab("Personal Info", personalInfoPanel);
        
        // Medical History Tab
        JPanel medicalHistoryPanel = createMedicalHistoryPanel(selectedRow);
        tabbedPane.addTab("Medical History", medicalHistoryPanel);
        
        // Prescriptions Tab
        JPanel prescriptionsPanel = createPrescriptionsPanel(selectedRow);
        tabbedPane.addTab("Prescriptions", prescriptionsPanel);
        
        // Appointments Tab
        JPanel appointmentsPanel = createAppointmentsPanel(selectedRow);
        tabbedPane.addTab("Appointments", appointmentsPanel);
        
        JOptionPane.showMessageDialog(frame, tabbedPane, "Patient Details", JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel createPersonalInfoPanel(int row) {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            panel.add(new JLabel(tableModel.getColumnName(i) + ":"));
            panel.add(new JLabel(tableModel.getValueAt(row, i).toString()));
        }
        return panel;
    }

    private JPanel createMedicalHistoryPanel(int row) {
        JPanel panel = new JPanel();
        PatientRecord record = getPatientRecord(row);
        for (Consultation consultation : record.getConsultations()) {
            panel.add(new JLabel(consultation.toString()));
        }
        return panel;
    }

    private JPanel createPrescriptionsPanel(int row) {
        JPanel panel = new JPanel();
        PatientRecord record = getPatientRecord(row);
        for (String prescription : record.getPrescriptions()) {
            panel.add(new JLabel(prescription));
        }
        return panel;
    }

    private JPanel createAppointmentsPanel(int row) {
        JPanel panel = new JPanel();
        PatientRecord record = getPatientRecord(row);
        for (Appointment appointment : record.getAppointments()) {
            panel.add(new JLabel(appointment.toString()));
        }
        return panel;
    }

    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient first!");
            return;
        }
        
        PatientRecord record = getPatientRecord(selectedRow);
        String newName = JOptionPane.showInputDialog("Enter new name:", record.getName());
        int newAge = Integer.parseInt(JOptionPane.showInputDialog("Enter new age:", record.getAge()));
        String newGender = JOptionPane.showInputDialog("Enter new gender:", record.getGender());
        String newBloodGroup = JOptionPane.showInputDialog("Enter new blood group:", record.getBloodGroup());
        
        record.updateDetails(newName, newAge, newGender, newBloodGroup);
        JOptionPane.showMessageDialog(frame, "Patient details updated successfully!");
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient first!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(frame, 
            "Are you sure you want to delete this record?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            PatientRecord record = getPatientRecord(selectedRow);
            patientRecords.remove(record);

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(frame, "Patient record deleted successfully!");
        }
    }
}
