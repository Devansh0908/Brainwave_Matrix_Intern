import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PharmacistDashboard {
    private JFrame frame;

    public PharmacistDashboard() {
        frame = new JFrame("Pharmacist Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Pharmacy Management
        JButton checkStockButton = new JButton("Check Stock");
        checkStockButton.setBounds(10, 20, 150, 25);
        panel.add(checkStockButton);

        JButton verifyPrescriptionButton = new JButton("Verify Prescription");
        verifyPrescriptionButton.setBounds(10, 60, 150, 25);
        panel.add(verifyPrescriptionButton);

        JButton issueMedicinesButton = new JButton("Issue Medicines");
        issueMedicinesButton.setBounds(10, 100, 150, 25);
        panel.add(issueMedicinesButton);

        JButton createInvoiceButton = new JButton("Create Invoice");
        createInvoiceButton.setBounds(10, 140, 150, 25);
        panel.add(createInvoiceButton);

        // Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 180, 150, 25);
        panel.add(logoutButton);

        // Add action listeners
        checkStockButton.addActionListener(e -> checkStock());
        verifyPrescriptionButton.addActionListener(e -> verifyPrescription());
        issueMedicinesButton.addActionListener(e -> issueMedicines());
        createInvoiceButton.addActionListener(e -> createInvoice());
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginPage();
        });
    }

    // Placeholder methods for functionality
    private void checkStock() {
        // Implementation for checking stock
    }

    private void verifyPrescription() {
        // Implementation for verifying prescriptions
    }

    private void issueMedicines() {
        // Implementation for issuing medicines
    }

    private void createInvoice() {
        // Implementation for creating invoices
    }
}
