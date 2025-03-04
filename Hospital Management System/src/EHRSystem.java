import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class EHRSystem {
    private static EHRSystem instance;
    private HashMap<String, PatientRecord> patientRecords;
    private static final String RECORDS_DIR = "medical_records/";
    private AdmissionSystem admissionSystem; // Add admission system
    private SurgerySystem surgerySystem; // Add surgery system
    private MedicineSystem medicineSystem; // Add medicine system
    private MiscellaneousSystem miscellaneousSystem; // Add miscellaneous system

    private EHRSystem() {
        patientRecords = new HashMap<>();
        admissionSystem = new AdmissionSystem(); // Initialize admission system
        surgerySystem = new SurgerySystem(); // Initialize surgery system
        medicineSystem = new MedicineSystem(); // Initialize medicine system
        miscellaneousSystem = new MiscellaneousSystem(); // Initialize miscellaneous system

        // Ensure records directory exists
        new File(RECORDS_DIR).mkdirs();
    }

    public static EHRSystem getInstance() {
        if (instance == null) {
            instance = new EHRSystem();
        }
        return instance;
    }

    public void createPatientRecord(String patientId, String name, int age, String gender, String bloodGroup, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Doctor")) {
            throw new SecurityException("Access denied: You do not have permission to create patient records.");
        }

        if (!patientRecords.containsKey(patientId)) {
            patientRecords.put(patientId, new PatientRecord(patientId, name, age, gender, bloodGroup));
            saveRecordToFile(patientId);
        }
    }

    public void addConsultation(String patientId, String doctorName, String diagnosis, String treatment, String surgeryType, String medicineName, int medicineQuantity, String miscService, int miscQuantity, String roomType, int days) {
        PatientRecord record = patientRecords.get(patientId);
        if (record != null) {
            record.addConsultation(new Consultation(doctorName, diagnosis, treatment));
            if (surgeryType != null && !surgeryType.isEmpty()) {
                int surgeryCharge = surgerySystem.calculateSurgeryCharge(surgeryType);
                System.out.println("Surgery Charge for " + surgeryType + ": ₹" + surgeryCharge);
            }
            if (medicineName != null && !medicineName.isEmpty()) {
                int medicineCharge = medicineSystem.calculateMedicineCharge(medicineName, medicineQuantity, record.name, false); // Pass patient name and boolean
                System.out.println("Medicine Charge for " + medicineName + " (x" + medicineQuantity + "): ₹" + medicineCharge);
            }
            if (miscService != null && !miscService.isEmpty()) {
                int miscCharge = miscellaneousSystem.calculateMiscellaneousCharge(miscService, miscQuantity);
                System.out.println("Miscellaneous Charge for " + miscService + " (x" + miscQuantity + "): ₹" + miscCharge);
            }
            
            // Calculate and display total bill
            int totalBill = calculateTotalBill(patientId, roomType, days);

            System.out.println("Total Bill for Patient " + patientId + ": ₹" + totalBill);
            
            saveRecordToFile(patientId);
        } else {
            System.out.println("Patient record not found for ID: " + patientId);
        }
    }

    public String getPatientRecord(String patientId, String userRole) {
        if (!userRole.equals("Admin") && !userRole.equals("Doctor")) {
            throw new SecurityException("Access denied: You do not have permission to view patient records.");
        }

        PatientRecord record = patientRecords.get(patientId);
        if (record == null) {
            return "No record found";
        }
        
        if (userRole.equals("Doctor")) {
            return record.toString();
        } else {
            // Return basic info only for non-doctors
            return String.format("Patient ID: %s\nName: %s\nAge: %d\nGender: %s\nBlood Group: %s",
                record.patientId, record.name, record.age, record.gender, record.bloodGroup);
        }
    }

    public List<String> searchRecords(String searchTerm, String userRole) {
        List<String> results = new ArrayList<>();
        
        for (PatientRecord record : patientRecords.values()) {
            if (record.patientId.equals(searchTerm) || 
                record.name.toLowerCase().contains(searchTerm.toLowerCase())) {
                
                if (userRole.equals("Doctor")) {
                    results.add(record.toString());
                } else {
                    results.add(String.format("Patient ID: %s\nName: %s\nAge: %d\nGender: %s\nBlood Group: %s",
                        record.patientId, record.name, record.age, record.gender, record.bloodGroup));
                }
            }
        }
        
        return results;
    }

    private void saveRecordToFile(String patientId) {
        // Validate file path to prevent directory traversal
        if (!patientId.matches("[a-zA-Z0-9_-]+")) {
            throw new SecurityException("Invalid patient ID");
        }
        
        File recordFile = new File(RECORDS_DIR + patientId + "_record.txt");
        File invoiceFile = new File(RECORDS_DIR + patientId + "_invoice.txt");

        try {
            // Set file permissions to owner-only read/write
            recordFile.createNewFile();
            recordFile.setReadable(false, false);
            recordFile.setWritable(false, false);
            recordFile.setReadable(true, true);
            recordFile.setWritable(true, true);
            
            // Write encrypted data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(recordFile))) {
                String data = patientRecords.get(patientId).toString();
                String encryptedData = SecurityUtils.encrypt(data);
                writer.write(encryptedData);
            }
            
            // Generate and save invoice
            generateInvoice(patientId, invoiceFile);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save patient record", e);
        }
    }

    public int calculateTotalBill(String patientId, String roomType, int days) {
        PatientRecord record = patientRecords.get(patientId);
        if (record != null) {
            int total = 0;
            int admissionCharge = admissionSystem.calculateAdmissionCharge(roomType, days);
            int surgeryCharge = surgerySystem.calculateSurgeryCharge("surgeryType"); // This needs to be passed or retrieved
            int medicineCharge = medicineSystem.calculateMedicineCharge("medicineName", 0, record.name, false); // This needs to be passed or retrieved
            int miscCharge = miscellaneousSystem.calculateMiscellaneousCharge("miscService", 0); // This needs to be passed or retrieved

            total += admissionCharge + surgeryCharge + medicineCharge + miscCharge;

            return total;
        }
        throw new IllegalArgumentException("Patient record not found for ID: " + patientId);
    }

    public void testSurgery() {
        String patientId = "P001";
        String name = "John Doe";
        int age = 30;
        String gender = "Male";
        String bloodGroup = "O+";
        String surgeryType = "Appendicitis Surgery";
        String miscService = "Ambulance Service";
        int miscQuantity = 1;

        // Create a patient record
        createPatientRecord(patientId, name, age, gender, bloodGroup, "Doctor");

        // Add a consultation with surgery, medicine, and miscellaneous services
        addConsultation(patientId, "Dr. Smith", "Appendicitis", "Surgery required", 
            surgeryType, "Paracetamol 500mg", 10, miscService, miscQuantity, "General Ward", 5);
    }

    private void generateInvoice(String patientId, File invoiceFile) throws IOException {
        PatientRecord record = patientRecords.get(patientId);
        if (record == null) return;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(invoiceFile))) {
            writer.write("----------------------------------------\n");
            writer.write("HOSPITAL INVOICE\n");
            writer.write("Patient Name: " + record.name + "\n");
            writer.write("Patient ID: " + record.patientId + "\n");
            writer.write("Date of Admission: " + new SimpleDateFormat("dd-MMM-yyyy").format(new Date()) + "\n");
            writer.write("Date of Discharge: " + new SimpleDateFormat("dd-MMM-yyyy").format(new Date()) + "\n");
            writer.write("----------------------------------------\n");
            writer.write("Services Provided:\n\n");
            
            // Add logic to write individual charges here
            // Example:
            writer.write("✔ Consultation Fee (Dr. Mehta)     ₹800\n");
            writer.write("✔ General Ward Stay (5 days)          ₹1,500\n");
            writer.write("✔ Blood Test                          ₹10,000\n");
            writer.write("✔ X-Ray                               ₹2,500\n");
            writer.write("✔ Medicines                          ₹800\n");
            writer.write("✔ Nursing Charges (5 days)          ₹2,500\n");
            writer.write("✔ Ambulance Service                   ₹2,000\n");
            
            writer.write("----------------------------------------\n");
            writer.write("TOTAL BILL AMOUNT:                   ₹" + calculateTotalBill(patientId, "roomType", 5) + "\n");
            writer.write("----------------------------------------\n");
        }
    }

    private class PatientRecord {
        private String patientId;
        private List<Consultation> consultations;
        private String name;
        private int age;
        private String gender;
        private String bloodGroup;

        public PatientRecord(String patientId, String name, int age, String gender, String bloodGroup) {
            this.patientId = patientId;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.bloodGroup = bloodGroup;
            this.consultations = new ArrayList<>();
        }

        public void addConsultation(Consultation consultation) {
            consultations.add(consultation);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Patient ID: ").append(patientId).append("\n");
            sb.append("Name: ").append(name).append("\n");
            sb.append("Age: ").append(age).append("\n");
            sb.append("Gender: ").append(gender).append("\n");
            sb.append("Blood Group: ").append(bloodGroup).append("\n\n");
            sb.append("Medical History:\n");

            for (Consultation c : consultations) {
                sb.append(c).append("\n");
            }
            return sb.toString();
        }
    }
}
