import java.util.HashMap;

public class SurgerySystem {
    private HashMap<String, Integer> surgeryCharges;

    public SurgerySystem() {
        surgeryCharges = new HashMap<>();
        surgeryCharges.put("Appendicitis Surgery", 50000);
        surgeryCharges.put("Heart Bypass Surgery", 250000);
        surgeryCharges.put("Knee Replacement", 120000);
    }

    public int calculateSurgeryCharge(String surgeryType) {
        Integer charge = surgeryCharges.get(surgeryType);
        if (charge != null) {
            return charge;
        } else {
            throw new IllegalArgumentException("Invalid surgery type: " + surgeryType);
        }
    }
}
