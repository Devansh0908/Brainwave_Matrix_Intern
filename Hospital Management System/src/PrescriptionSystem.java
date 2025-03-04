import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PrescriptionSystem {
    private static PrescriptionSystem instance;
    private static final String PRESCRIPTIONS_DIR = "prescriptions/";

    private PrescriptionSystem() {
        // Ensure prescriptions directory exists
        new File(PRESCRIPTIONS_DIR).mkdirs();
    }

    public static PrescriptionSystem getInstance() {
        if (instance == null) {
            instance = new PrescriptionSystem();
        }
        return instance;
    }

    public void createPrescription(String patientId, String patientName, String doctorName, 
                                 String medications, String followUpDate) {
        // Validate patient ID to prevent directory traversal
        if (!patientId.matches("[a-zA-Z0-9_-]+")) {
            throw new SecurityException("Invalid patient ID");
        }
        
        String fileName = PRESCRIPTIONS_DIR + patientId + "_" + 
            new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
            
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String prescription = String.format(
                "Patient ID: %s\nName: %s\nDate: %s\nPrescribed By: %s\nMedications:\n%s\nFollow-Up Date: %s",
                patientId,
                patientName,
                new SimpleDateFormat("dd MMM yyyy").format(new Date()),
                doctorName,
                medications,
                followUpDate);
                
            writer.write(prescription);
            
            // Notify pharmacy
            notifyPharmacy(patientId, patientName, medications);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create prescription", e);
        }
    }

    private void notifyPharmacy(String patientId, String patientName, String medications) {
        String pharmacyNotification = String.format(
            "New Prescription\nPatient ID: %s\nName: %s\nMedications:\n%s",
            patientId,
            patientName,
            medications);
            
        // TODO: Implement actual pharmacy notification mechanism
        System.out.println("Sending to pharmacy:\n" + pharmacyNotification);
    }

    public String getPrescription(String patientId, String prescriptionId) {
        // Validate inputs to prevent directory traversal
        if (!patientId.matches("[a-zA-Z0-9_-]+") || !prescriptionId.matches("[a-zA-Z0-9_-]+")) {
            throw new SecurityException("Invalid patient ID or prescription ID");
        }
        
        File prescriptionFile = new File(PRESCRIPTIONS_DIR + patientId + "_" + prescriptionId + ".txt");
        try {
            return new String(Files.readAllBytes(prescriptionFile.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read prescription", e);
        }
    }
}
