import java.util.HashMap;

public class AdmissionSystem {
    private HashMap<String, Integer> roomFees;
    private HashMap<String, String> bedAssignments; // To track bed assignments

    public AdmissionSystem() {
        roomFees = new HashMap<>();
        roomFees.put("Emergency Ward", 1000);
        roomFees.put("General Ward", 2000);
        roomFees.put("Private Room", 5000);
        roomFees.put("ICU", 10000);
        bedAssignments = new HashMap<>(); // Initialize bed assignments
    }

    public int calculateAdmissionFee(String roomType, int days) {
        Integer feePerDay = roomFees.get(roomType);
        if (feePerDay != null) {
            return feePerDay * days;
        } else {
            throw new IllegalArgumentException("Invalid room type: " + roomType);
        }
    }

    public int calculateAdmissionCharge(String roomType, int days) {
        Integer feePerDay = roomFees.get(roomType);
        if (feePerDay != null) {
            return feePerDay * days;
        } else {
            throw new IllegalArgumentException("Invalid room type: " + roomType);
        }
    }

    public void admitPatient(String patientId, String userRole) {

        if (!userRole.equals("Admin") && !userRole.equals("Receptionist") && !userRole.equals("Doctor")) {
            throw new SecurityException("Access denied: You do not have permission to admit patients.");
        }
        // Logic to admit patient
    }

    public void dischargePatient(String patientId, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Doctor")) {
            throw new SecurityException("Access denied: You do not have permission to discharge patients.");
        }
        // Logic to discharge patient
    }

    public void assignBed(String patientId, String bedId, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Ward Staff")) {
            throw new SecurityException("Access denied: You do not have permission to assign beds.");
        }
        // Logic to assign bed
        bedAssignments.put(patientId, bedId);
    }

    public void updateBedAvailability(String bedId, boolean isAvailable, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Ward Staff")) {
            throw new SecurityException("Access denied: You do not have permission to update bed availability.");
        }
        // Logic to update bed availability
    }
}
