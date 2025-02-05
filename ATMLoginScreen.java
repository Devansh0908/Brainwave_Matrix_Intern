import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ATMLoginScreen {
    private static Map<String, String> userDatabase = new HashMap<>();
    private static Map<String, Double> accountBalances = new HashMap<>();

    public static void main(String[] args) {
        loadUserData();
        JFrame frame = new JFrame("ATM Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 50, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 90, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 90, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 130, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(240, 130, 100, 25);
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (validateCredentials(username, password)) {
                openAccountOptions(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        });

        registerButton.addActionListener(e -> openRegistrationForm());
    }

    private static boolean validateCredentials(String username, String password) {
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }

    private static void openAccountOptions(String username) {
        JFrame accountFrame = new JFrame("Account Options");
        accountFrame.setSize(300, 300);
        accountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        accountFrame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        JLabel welcomeLabel = new JLabel("Welcome " + username + "!");
        panel.add(welcomeLabel);

        JButton balanceButton = new JButton("Balance Inquiry");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdrawal");
        JButton changePinButton = new JButton("Change PIN");
        JButton logoutButton = new JButton("Logout");

        panel.add(balanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(changePinButton);
        panel.add(logoutButton);

        accountFrame.add(panel);
        accountFrame.setVisible(true);

        balanceButton.addActionListener(e -> JOptionPane.showMessageDialog(accountFrame, "Your balance is: $" + accountBalances.getOrDefault(username, 0.0)));
        
        depositButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(accountFrame, "Enter amount to deposit:");
            if (amountStr != null) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    accountBalances.put(username, accountBalances.getOrDefault(username, 0.0) + amount);
                    saveUserData();
                    JOptionPane.showMessageDialog(accountFrame, "Deposit successful! New balance: $" + accountBalances.get(username));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(accountFrame, "Invalid amount.");
                }
            }
        });
        
        withdrawButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(accountFrame, "Enter amount to withdraw:");
            if (amountStr != null) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    double currentBalance = accountBalances.getOrDefault(username, 0.0);
                    if (amount > currentBalance) {
                        JOptionPane.showMessageDialog(accountFrame, "Insufficient balance.");
                    } else {
                        accountBalances.put(username, currentBalance - amount);
                        saveUserData();
                        JOptionPane.showMessageDialog(accountFrame, "Withdrawal successful! New balance: $" + accountBalances.get(username));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(accountFrame, "Invalid amount.");
                }
            }
        });
        
        changePinButton.addActionListener(e -> {
            String newPin = JOptionPane.showInputDialog(accountFrame, "Enter new PIN:");
            if (newPin != null && !newPin.isEmpty()) {
                userDatabase.put(username, newPin);
                saveUserData();
                JOptionPane.showMessageDialog(accountFrame, "PIN changed successfully.");
            }
        });
        
        logoutButton.addActionListener(e -> accountFrame.dispose());
    }

    private static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (String username : userDatabase.keySet()) {
                writer.write(username + "," + userDatabase.get(username) + "," + accountBalances.getOrDefault(username, 0.0));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user data.");
        }
    }

    private static void openRegistrationForm() {
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JTextField balanceField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Initial Balance:"));
        panel.add(balanceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            double balance = Double.parseDouble(balanceField.getText());
            
            if (!userDatabase.containsKey(username)) {
                userDatabase.put(username, password);
                accountBalances.put(username, balance);
                saveUserData();
                JOptionPane.showMessageDialog(null, "Registration successful! Please log in.");
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists.");
            }
        }
    }

    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    userDatabase.put(parts[0], parts[1]);
                    accountBalances.put(parts[0], Double.parseDouble(parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user data found.");
        }
    }
}
