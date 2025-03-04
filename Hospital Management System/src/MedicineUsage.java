import java.util.Date;

public class MedicineUsage {
    private String patientName;
    private String medicineName;
    private int quantity;
    private Date dateIssued;

    public MedicineUsage(String patientName, String medicineName, int quantity, Date dateIssued) {
        this.patientName = patientName;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.dateIssued = dateIssued;
    }

    public String getPatientName() { return patientName; }
    public String getMedicineName() { return medicineName; }
    public int getQuantity() { return quantity; }
    public Date getDateIssued() { return dateIssued; }
}
