import java.util.ArrayList; 
import java.util.List; 

public class PatientRecord {
    private List<Consultation> consultations; 
    private List<String> prescriptions; 
    private List<Appointment> appointments; 
    private String bloodGroup; 


    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String email;
    private String aadhar;
    private String address;
    private String emergencyContact;

    // Constructor
    public PatientRecord(String patientId, String name, int age, String gender, String contact) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.consultations = new ArrayList<>(); 
        this.prescriptions = new ArrayList<>(); 
        this.appointments = new ArrayList<>(); 
        this.bloodGroup = ""; 
    }


    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getAddress() {
        return address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    // Method to update patient details
    public void updateDetails(String name, int age, String gender, String contact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    public List<Consultation> getConsultations() {
        return consultations; // Return the list of consultations
    }

    public List<String> getPrescriptions() {
        return prescriptions; // Return the list of prescriptions
    }

    public List<Appointment> getAppointments() {
        return appointments; // Return the list of appointments
    }

    public String getBloodGroup() {
        return bloodGroup; // Return the blood group
    }



}
