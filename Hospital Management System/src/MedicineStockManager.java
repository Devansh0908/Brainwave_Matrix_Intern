import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MedicineStockManager {
    private HashMap<String, Integer> stock; // To track medicine stock

    public MedicineStockManager() {
        stock = new HashMap<>();
        // Initialize with some medicines if needed
    }

    public void addMedicine(String medicineName, int quantity, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Pharmacist")) {
            throw new SecurityException("Access denied: You do not have permission to add medicines.");
        }
        stock.put(medicineName, stock.getOrDefault(medicineName, 0) + quantity);
    }

    public void updateMedicine(String medicineName, int quantity, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Pharmacist")) {
            throw new SecurityException("Access denied: You do not have permission to update medicines.");
        }
        stock.put(medicineName, quantity);
    }

    public void removeMedicine(String medicineName, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Pharmacist")) {
            throw new SecurityException("Access denied: You do not have permission to remove medicines.");
        }
        stock.remove(medicineName);
    }

    public int checkStock(String medicineName) {
        return stock.getOrDefault(medicineName, 0);
    }

    public void issueMedicine(String medicineName, int quantity, String patientName) {
        if (checkStock(medicineName) < quantity) {
            throw new IllegalArgumentException("Insufficient stock for medicine: " + medicineName);
        }
        stock.put(medicineName, stock.get(medicineName) - quantity);
    }

    public List<String> searchByName(String name) {
        List<String> results = new ArrayList<>();
        for (String medicine : stock.keySet()) {
            if (medicine.toLowerCase().contains(name.toLowerCase())) {
                results.add(medicine);
            }
        }
        return results;
    }

    public List<String> searchByCategory(MedicineCategory category) {
        // Logic to filter medicines by category
        return new ArrayList<>(); // Placeholder
    }
}
