import java.util.HashMap;

public class BillingSystem {
    private HashMap<String, Integer> serviceCharges; // To track service charges
    private HashMap<String, Integer> medicineCharges; // To track medicine charges

    public BillingSystem() {
        serviceCharges = new HashMap<>();
        serviceCharges.put("Consultation", 800);
        serviceCharges.put("General Ward Stay", 1500);
        serviceCharges.put("ICU Stay", 10000);
        // Add more services as needed

        medicineCharges = new HashMap<>();
        // Initialize with some medicine charges if needed
    }

public void generateInvoice(String patientId, String userRole, boolean scanIncluded) {

        if (!userRole.equals("Admin") && !userRole.equals("Receptionist")) {
            throw new SecurityException("Access denied: You do not have permission to generate invoices.");
        }
        // Logic to generate invoice for the patient
        if (scanIncluded) {
            // Integrate scanned documents or images into the invoice
        }

    }

public void viewBill(String patientId, String userRole, boolean scanIncluded) {

        if (!userRole.equals("Admin") && !userRole.equals("Doctor")) {
            throw new SecurityException("Access denied: You do not have permission to view bills.");
        }
        // Logic to view the bill for the patient
        if (scanIncluded) {
            // Integrate scanned documents or images into the bill view
        }

    }

    public void generateMedicineBill(String patientId, String medicineName, int quantity, String userRole) {
        if (!userRole.equals("Pharmacist")) {
            throw new SecurityException("Access denied: You do not have permission to generate medicine bills.");
        }
        // Logic to generate medicine bill
    }
}
