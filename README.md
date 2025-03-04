# Task 1

# ATM Interface using Java Swing

## 📌 Project Overview
This is a **Java Swing-based ATM Interface** that provides essential banking functionalities such as **user authentication, balance inquiry, deposits, withdrawals, PIN changes, and secure data management.** The application uses file handling to store user credentials and account balances, ensuring persistent data across sessions.

## 🚀 Features
- **User Authentication**: Secure login system with username and password.
- **New User Registration**: Allows users to register with a username, password, and initial balance.
- **Account Management**:
  - Check balance
  - Deposit funds
  - Withdraw funds (with insufficient balance check)
  - Change PIN
  - Logout
- **Persistent Data Storage**: Uses a text file (`users.txt`) to save user credentials and account balances.
- **Graphical User Interface (GUI)**: Built using **Java Swing** for an interactive experience.

## 🛠️ Technologies Used
- **Java** (JDK 8+ recommended)
- **Swing** (for GUI development)
- **File Handling** (for storing user data)

## 📂 Project Structure
```
ATM-Interface/
├── src/
│   ├── ATMLoginScreen.java    # Main Java class for ATM functionalities
│   ├── users.txt              # User credentials and balances (generated at runtime)
├── README.md                  # Project documentation
├── LICENSE                    # License file (if applicable)
```

## 📥 Installation & Setup
1. **Clone the repository**:
   ```sh
   git clone https://github.com/your-username/ATM-Interface.git
   ```
2. **Open the project in an IDE** (Eclipse, IntelliJ IDEA, or VS Code with Java support).
3. **Run the `ATMLoginScreen.java` file**.
4. **Create an account** or log in with existing credentials.

## 📌 Usage
- **Login** with a registered username and password.
- If you are a new user, click "Register" to create an account.
- Once logged in, select an option:
  - View balance
  - Deposit money
  - Withdraw money
  - Change your PIN
  - Logout to return to the login screen

## 🔒 Security Considerations
- Passwords are currently stored in plain text in `users.txt`. **For enhanced security, consider hashing passwords (e.g., using BCrypt)**.
- Ensure the application runs in a controlled environment to prevent unauthorized access to the user data file.

## 🤝 Contributions
Contributions are welcome! If you’d like to improve this project:
- Fork the repository
- Create a new branch
- Make your changes and submit a pull request

## 🏆 Acknowledgments
This project was developed as part of an **internship at Brainwave Matrix Solutions**. Special thanks to the team for the guidance and learning opportunities!

## 📜 License
This project is licensed under the **MIT License** – feel free to use and modify it!

---
💡 **Stay connected!** If you found this project helpful, give it a ⭐ on GitHub and feel free to connect with me on LinkedIn!


-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Task-2


# Hospital Management System 🏥

## Overview
The **Hospital Management System** is a Java-based application developed for **Brainwave Matrix Solutions** to streamline hospital operations. This system provides role-based access control and efficient patient management, ensuring a secure and scalable healthcare management solution.

## Features
### 🔐 User Authentication & Role-Based Access Control
- Secure login & signup system
- Role-based permissions for Admin, Doctor, Receptionist, Pharmacist, and Ward Staff
- Auto-logout after inactivity for security

### 🏥 Patient Management
- Register new patients with unique Patient IDs
- Search patients by **ID, Name, Contact, Aadhar**
- Admission & Bed Management with real-time availability tracking
- Generate Discharge Summaries

### 📅 Appointment Scheduling
- Token-based appointment system
- Doctor-wise appointment lists
- Reschedule and cancel appointments with notifications
- Automated waitlist for urgent cases

### 📑 Electronic Health Records (EHR)
- Maintain patient medical history
- Digital prescriptions issued by doctors
- Secure storage and access control

### 🏥 Admission & Bed Management
- Automatic room & ward assignment
- ICU, General, Private, Emergency ward tracking
- Live bed occupancy dashboard
- Patient transfer & discharge tracking

### 💰 Billing & Invoicing
- Automated invoice generation for **consultations, admissions, medicines, and surgeries**
- Real-time cost calculation
- Printable & digital invoices

### 💊 Pharmacy & Inventory Management
- Track medicine stock levels
- Alerts for low inventory
- Prescription verification and medicine issuance

### 💼 Staff Management
- Employee registration and role assignment
- Salary management & payroll processing

## Technologies Used
- **Programming Language**: Java ☕
- **GUI**: Java Swing 🎨
- **Database**: File-based storage (can be upgraded to MySQL)
- **Authentication**: Role-based access control

## Installation & Setup
1. **Clone the Repository**
   ```sh
   git clone https://github.com/yourusername/hospital-management-system.git
   cd hospital-management-system
   ```
2. **Run the Application**
   - Open the project in **Eclipse/IntelliJ IDEA**
   - Compile and run the `Main.java` file

## Future Enhancements 🚀
- Migrate from text file storage to **SQL database**
- Improve UI with **JavaFX or web-based frontend**
- Implement cloud storage for better scalability

## Contributing 🤝
Feel free to fork the repository, submit issues, or suggest improvements!

## License
This project is open-source and available under the **MIT License**.

---
📢 Developed by Devansh Pandey. 🚀
