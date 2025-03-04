import java.util.HashMap;

public class MiscellaneousSystem {
    private HashMap<String, Integer> miscellaneousCharges;

    public MiscellaneousSystem() {
        miscellaneousCharges = new HashMap<>();
        miscellaneousCharges.put("Ambulance Service", 2000);
        miscellaneousCharges.put("Nursing Fees", 500);
        miscellaneousCharges.put("Oxygen Cylinder", 3000);
    }

    public int calculateMiscellaneousCharge(String serviceName, int quantity) {
        Integer charge = miscellaneousCharges.get(serviceName);
        if (charge != null) {
            return charge * quantity;
        } else {
            throw new IllegalArgumentException("Invalid service name: " + serviceName);
        }
    }
}
