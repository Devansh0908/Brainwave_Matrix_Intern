import java.util.*;

public class WardManagementSystem {
    private static WardManagementSystem instance;
    private Map<String, Ward> wards;
    
    private WardManagementSystem() {
        wards = new HashMap<>();
        initializeWards();
    }
    
    public static WardManagementSystem getInstance() {
        if (instance == null) {
            instance = new WardManagementSystem();
        }
        return instance;
    }
    
    private void initializeWards() {
        // Initialize ICU ward
        wards.put("ICU", new Ward("Intensive Care Unit", 20, true));
        
        // Initialize emergency ward
        wards.put("ER", new Ward("Emergency Ward", 50, true));

        // Initialize private wards with amenities
        wards.put("P1", new Ward("Private Ward 1", 20, false, true, 5000));
        wards.put("P2", new Ward("Private Ward 2", 20, false, true, 5000));

        // Initialize maternity ward with NICU
        wards.put("MAT", new Ward("Maternity Ward", 50, false, true, 8000, false, true));
        
        // Initialize general wards with shared rooms
        wards.put("G1", new Ward("General Ward 1", 100, false, false, 1000, false, false));
        wards.put("G2", new Ward("General Ward 2", 100, false, false, 1000, false, false));
    }
    
    public String assignBed(String patientId, String wardType, boolean isEmergency) {
        Ward ward = wards.get(wardType);
        if (ward == null) {
            return "Invalid ward type";
        }
        
        // Emergency cases get priority
        if (isEmergency && ward.assignBed(patientId)) {
            return "Emergency case assigned to " + ward.getName() + ", Bed #" + ward.getLastAssignedBed();
        }
        
        // Normal admission
        if (ward.assignBed(patientId)) {
            return "Assigned to " + ward.getName() + ", Bed #" + ward.getLastAssignedBed();
        }
        
        // If ward is full, suggest alternatives
        StringBuilder alternatives = new StringBuilder();
        for (Map.Entry<String, Ward> entry : wards.entrySet()) {
            if (!entry.getKey().equals(wardType) && entry.getValue().hasAvailableBeds()) {
                alternatives.append("\n- ").append(entry.getValue().getName());
            }
        }
        
        if (alternatives.length() > 0) {
            return "No available beds in " + ward.getName() + 
                   ". Suggested alternatives:" + alternatives.toString();
        }
        
        // If no alternatives, add to waitlist
        addToWaitlist(patientId, wardType);
        return "No available beds. You've been added to the waitlist for " + ward.getName();
    }

    private boolean addToWaitlist(String patientId, String wardType) {
        Ward ward = wards.get(wardType);
        if (ward != null) {
            ward.addToWaitlist(patientId);
            return true;
        }
        return false;
    }

    public Map<String, String> getWardOccupancy() {
        Map<String, String> occupancy = new HashMap<>();
        for (Map.Entry<String, Ward> entry : wards.entrySet()) {
            Ward ward = entry.getValue();
            String status = ward.getOccupiedBeds() + "/" + ward.getTotalBeds() + 
                          " beds occupied (" + ward.getAvailableBeds() + " available)";
            occupancy.put(ward.getName(), status);
        }
        return occupancy;
    }

    public String transferPatient(String patientId, String fromWardType, String toWardType) {
        Ward fromWard = wards.get(fromWardType);
        Ward toWard = wards.get(toWardType);
        
        if (fromWard == null || toWard == null) {
            return "Invalid ward type";
        }
        
        // Check if patient is in the from ward
        if (!fromWard.hasPatient(patientId)) {
            return "Patient not found in " + fromWard.getName();
        }
        
        // Try to assign bed in new ward
        if (toWard.assignBed(patientId)) {
            // Successfully assigned, now discharge from old ward
            fromWard.dischargePatient(patientId);
            return "Patient transferred from " + fromWard.getName() + 
                   " to " + toWard.getName() + ", " + 
                   toWard.getBedNumber(toWard.getLastAssignedBed());
        }
        
        return "No available beds in " + toWard.getName();
    }
    
    public void dischargePatient(String patientId) {
        for (Ward ward : wards.values()) {
            if (ward.dischargePatient(patientId)) {
                return;
            }
        }
    }

    private class Ward {
        private String name;
        private int totalBeds;
        private boolean isEmergency;
        private Map<Integer, String> bedAssignments;
        private int lastAssignedBed;
        
        private boolean hasPrivateAmenities;
        private double dailyRate;
        private boolean isICU;
        private boolean isMaternity;
        
        private Queue<String> waitlist; // Declare waitlist here

        // Constructor for ICU and Emergency wards
        public Ward(String name, int totalBeds, boolean isEmergency) {
            this(name, totalBeds, isEmergency, false, 0, false, false);
            this.waitlist = new LinkedList<>(); // Initialize waitlist
        }

        // Constructor for Private wards
        public Ward(String name, int totalBeds, boolean isEmergency, boolean hasPrivateAmenities, double dailyRate) {
            this(name, totalBeds, isEmergency, hasPrivateAmenities, dailyRate, false, false);
            this.waitlist = new LinkedList<>(); // Initialize waitlist
        }

        // Full constructor
        public Ward(String name, int totalBeds, boolean isEmergency, boolean hasPrivateAmenities, 
                    double dailyRate, boolean isICU, boolean isMaternity) {
            this.name = name;
            this.totalBeds = totalBeds;
            this.isEmergency = isEmergency;
            this.hasPrivateAmenities = hasPrivateAmenities;
            this.dailyRate = dailyRate;
            this.bedAssignments = new HashMap<>();
            this.lastAssignedBed = 0;
            this.waitlist = new LinkedList<>(); // Initialize waitlist
        }

        public int getLastAssignedBed() {
            return lastAssignedBed;
        }

        public String getBedNumber(int bedIndex) {
            return "Bed #" + bedIndex;
        }

        public boolean assignBed(String patientId) {
            if (!hasAvailableBeds()) {
                return false;
            }
            
            // Assign bed
            lastAssignedBed = (lastAssignedBed % totalBeds) + 1;
            if (!bedAssignments.containsKey(lastAssignedBed)) {
                bedAssignments.put(lastAssignedBed, patientId);
                return true;
            }
            return false;
        }

        public boolean hasPatient(String patientId) {
            return bedAssignments.containsValue(patientId);
        }

        public boolean dischargePatient(String patientId) {
            boolean discharged = bedAssignments.values().removeIf(id -> id.equals(patientId));
            if (discharged) {
                // Logic to update the lastAssignedBed if necessary
                // Reset lastAssignedBed if no patients are assigned
                if (bedAssignments.isEmpty()) {
                    lastAssignedBed = 0; // Reset if no patients are assigned
                }
            }
            return discharged;
        }

        public void addToWaitlist(String patientId) {
            waitlist.add(patientId);
        }

        public boolean hasAvailableBeds() {
            return bedAssignments.size() < totalBeds;
        }

        public int getAvailableBeds() {
            return totalBeds - bedAssignments.size();
        }

        public int getOccupiedBeds() {
            return bedAssignments.size();
        }

        public int getTotalBeds() {
            return totalBeds;
        }

        public String getName() {
            return name + (hasPrivateAmenities ? " (Private)" : "");
        }
    }
}
