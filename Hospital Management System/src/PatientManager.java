import java.util.ArrayList;

public class PatientManager {
    private ArrayList<Patient> patients;

    public PatientManager() {
        patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public boolean removePatient(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(patientId)) {
                patients.remove(patient);
                return true;
            }
        }
        return false;
    }

    public boolean updatePatient(String patientId, Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId().equals(patientId)) {
                patients.set(i, updatedPatient);
                return true;
            }
        }
        return false;
    }
}
