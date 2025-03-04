import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WardStaffDashboard {
    private JFrame frame;

    public WardStaffDashboard() {
        frame = new JFrame("Ward Staff Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Ward Management
        JButton viewWardDetailsButton = new JButton("View Ward Details");
        viewWardDetailsButton.setBounds(10, 20, 150, 25);
        panel.add(viewWardDetailsButton);

        JButton viewPrescriptionsButton = new JButton("View Prescriptions");
        viewPrescriptionsButton.setBounds(10, 60, 150, 25);
        panel.add(viewPrescriptionsButton);

        JButton markMedicinesButton = new JButton("Mark Medicines");
        markMedicinesButton.setBounds(10, 100, 150, 25);
        panel.add(markMedicinesButton);

        // Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 140, 150, 25);
        panel.add(logoutButton);

        // Add action listeners
        viewWardDetailsButton.addActionListener(e -> viewWardDetails());
        viewPrescriptionsButton.addActionListener(e -> viewPrescriptions());
        markMedicinesButton.addActionListener(e -> markMedicines());
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginPage();
        });
    }

    // Placeholder methods for functionality
    private void viewWardDetails() {
        // Implementation for viewing ward details
    }

    private void viewPrescriptions() {
        // Implementation for viewing prescriptions
    }

    private void markMedicines() {
        // Implementation for marking medicines
    }
}
