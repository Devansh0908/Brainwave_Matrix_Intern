import java.util.Date;

public class Appointment {
    private String patientId;
    private Date date;
    private String status;
    private String doctorName;
    private boolean scanIncluded; // New field to track scanning

    public Appointment(String patientId, Date date, boolean scanIncluded) {
        this.patientId = patientId;
        this.date = date;
        this.status = "Scheduled";
        this.doctorName = ""; // Will be set when added to doctor's list
        this.scanIncluded = scanIncluded; // Initialize the new field
    }

    public String getPatientId() { return patientId; }
    public Date getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setDate(Date date) { this.date = date; }
    
    // Helper method to get appointment time in milliseconds
    public long getTimeInMillis() {
        return date.getTime();
    }
}
