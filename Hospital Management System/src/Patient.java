public class Patient {
    private String name;
    private String patientId;
    private String medicalHistory;
    private int age; // New attribute for age
    private String condition; // New attribute for condition

    public Patient(String patientId, String name, int age, String condition) { // Updated constructor
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.medicalHistory = "";
    }

    public String getName() {
        return name;
    }

    public String getCondition() { // New getter for condition
        return condition;
    }

    public int getAge() { // New getter for age
        return age;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void updateMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setName(String name) { // New setter for name
        this.name = name;
    }

    public void setCondition(String condition) { // New setter for condition
        this.condition = condition;
    }
}
