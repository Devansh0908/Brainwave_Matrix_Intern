import java.util.Date;
import java.text.SimpleDateFormat;

public class Consultation {
    public String doctorName;
    public String diagnosis;
    public String treatment;
    private Date date;


    public Consultation(String doctorName, String diagnosis, String treatment) {
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = new Date();
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("Date: %s\nDoctor: %s\nDiagnosis: %s\nTreatment: %s\n",
            new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date),
            doctorName,
            diagnosis,
            treatment);
    }
}
