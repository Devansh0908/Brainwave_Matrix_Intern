import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MedicineSystem {
    private HashMap<String, Integer> medicinePrices;
    private MedicineStockManager stockManager;

    public MedicineSystem() {
        medicinePrices = new HashMap<>();
        medicinePrices.put("Paracetamol 500mg", 500);
        medicinePrices.put("Antibiotics (Amoxicillin 500mg)", 300);
        medicinePrices.put("Cough Syrup", 200);
        
        stockManager = new MedicineStockManager(); // Initialize stock manager
    }

    public int calculateMedicineCharge(String medicineName, int quantity, String patientName, boolean scanIncluded) {
        try {
            stockManager.issueMedicine(medicineName, quantity, patientName);
            Integer price = medicinePrices.get(medicineName);
            if (price != null) {
                return price * quantity;
            } else {
                throw new IllegalArgumentException("Invalid medicine name: " + medicineName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to issue medicine: " + e.getMessage());
        }
    }

    public void prescribeMedicine(String medicineName, int quantity, String userRole, boolean scanIncluded) {
        if (!userRole.equals("Doctor")) {
            throw new SecurityException("Access denied: You do not have permission to prescribe medicines.");
        }
        
        int availableStock = stockManager.checkStock(medicineName);
        if (availableStock < quantity) {
            throw new IllegalArgumentException("Insufficient stock for medicine: " + medicineName);
        }
        
        // Logic to prescribe the medicine
        if (scanIncluded) {
            // Integrate scanned documents or images into the medicine prescription
        }
    }

    public List<String> searchMedicinesByName(String name) {
        return stockManager.searchByName(name);
    }

    public List<String> getMedicinesByCategory(MedicineCategory category) {
        return stockManager.searchByCategory(category);
    }
}

