import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TokenSystem {
    private static TokenSystem instance;
    private HashMap<String, Queue<String>> tokenQueues;
    private HashMap<String, Integer> lastTokenNumbers;

    private TokenSystem() {
        tokenQueues = new HashMap<>();
        lastTokenNumbers = new HashMap<>();
    }

    public static synchronized TokenSystem getInstance() {
        if (instance == null) {
            instance = new TokenSystem();
        }
        return instance;
    }

    public synchronized String getNextToken(String specialty) {
        // Ensure queue and counter exist
        tokenQueues.putIfAbsent(specialty, new LinkedList<>());
        lastTokenNumbers.putIfAbsent(specialty, 0);

        // Generate prefix based on specialty
        String prefix;
        switch (specialty.toLowerCase()) {
            case "cardiologist":
                prefix = "CAR";
                break;
            case "dermatologist":
                prefix = "DER";
                break;
            case "orthopedic":
                prefix = "ORT";
                break;
            case "pediatrician":
                prefix = "PED";
                break;
            default:
                prefix = "GEN"; // General Physician
        }

        // Generate new token number
        int nextNumber = lastTokenNumbers.get(specialty) + 1;
        lastTokenNumbers.put(specialty, nextNumber);

        // Format token with prefix and 3-digit number
        String token = String.format("%s%03d", prefix, nextNumber);

        // Add to queue
        tokenQueues.get(specialty).add(token);

        return token;
    }

    public synchronized String getCurrentToken(String specialty) {
        return tokenQueues.containsKey(specialty) && !tokenQueues.get(specialty).isEmpty()
                ? tokenQueues.get(specialty).peek()
                : "No active tokens";
    }

    public synchronized void completeCurrentToken(String specialty) {
        if (tokenQueues.containsKey(specialty) && !tokenQueues.get(specialty).isEmpty()) {
            tokenQueues.get(specialty).poll();
        }
    }

    public synchronized int getQueueSize(String specialty) {
        return tokenQueues.getOrDefault(specialty, new LinkedList<>()).size();
    }
}
