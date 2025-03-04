import java.util.*;
import java.text.SimpleDateFormat;

public class AppointmentSystem {
    private static AppointmentSystem instance;
    private HashMap<String, List<Appointment>> doctorAppointments;
    private Queue<String> waitingList; // Queue for urgent cases
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private AppointmentSystem() {
        doctorAppointments = new HashMap<>();
        waitingList = new LinkedList<>();
    }

    public static AppointmentSystem getInstance() {
        if (instance == null) {
            instance = new AppointmentSystem();
        }
        return instance;
    }

    public void createAppointment(String doctorName, String patientId, Date date, boolean scanIncluded) {
        Appointment appointment = new Appointment(patientId, date, scanIncluded);
        doctorAppointments.computeIfAbsent(doctorName, k -> new ArrayList<>()).add(appointment);
    }

    public List<Appointment> getAppointmentsForDoctor(String doctorName, Date date) {
        List<Appointment> appointments = doctorAppointments.getOrDefault(doctorName, new ArrayList<>());
        String targetDate = dateFormat.format(date);
        
        return appointments.stream()
            .filter(a -> dateFormat.format(a.getDate()).equals(targetDate))
            .sorted(Comparator.comparing(Appointment::getDate))
            .toList();
    }

    public List<Appointment> searchAppointments(String doctorName, Date date, boolean scanIncluded) {
        if (doctorName != null && date != null) {
            return getAppointmentsForDoctor(doctorName, date);
        } else if (doctorName != null) {
            return doctorAppointments.getOrDefault(doctorName, new ArrayList<>());
        } else if (date != null) {
            String targetDate = dateFormat.format(date);
            return doctorAppointments.values().stream()
                .flatMap(List::stream)
                .filter(a -> dateFormat.format(a.getDate()).equals(targetDate))
                .toList();
        }
        return new ArrayList<>();
    }

    public boolean updateAppointmentStatus(String doctorName, String patientId, String status) {
        return doctorAppointments.getOrDefault(doctorName, new ArrayList<>()).stream()
            .filter(a -> a.getPatientId().equals(patientId))
            .findFirst()
            .map(a -> {
                a.setStatus(status);
                return true;
            }).orElse(false);
    }

    public boolean rescheduleAppointment(String doctorName, String patientId, Date newDate) {
        Optional<Appointment> appointmentOpt = doctorAppointments.getOrDefault(doctorName, new ArrayList<>()).stream()
            .filter(a -> a.getPatientId().equals(patientId))
            .findFirst();
            
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setDate(newDate);
            return true;
        }
        return false;
    }

    public String rescheduleDoctorAppointments(String doctorName, Date newDate, String reason) {
        List<Appointment> appointments = doctorAppointments.get(doctorName);
        if (appointments != null) {
            // Find next available doctor
            String alternateDoctor = findAlternateDoctor(doctorName);
            
            // Reschedule all appointments
            for (Appointment appointment : appointments) {
                if (alternateDoctor != null) {
                    // Move to alternate doctor
                    createAppointment(alternateDoctor, appointment.getPatientId(), newDate, false);
                } else {
                    // Just reschedule with new date
                    appointment.setDate(newDate);
                }
                // TODO: Send notification to patient
            }
            return "Appointments rescheduled successfully";
        }
        return "No appointments found for this doctor";
    }

    private String findAlternateDoctor(String originalDoctor) {
        // Simple implementation - find first available doctor
        for (String doctor : doctorAppointments.keySet()) {
            if (!doctor.equals(originalDoctor)) {
                return doctor;
            }
        }
        return null;
    }

    public boolean cancelAppointment(String doctorName, String patientId) {
        List<Appointment> appointments = doctorAppointments.get(doctorName);
        if (appointments != null) {
            Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .findFirst();
                
            if (appointmentOpt.isPresent()) {
                Appointment appointment = appointmentOpt.get();
                long timeDifference = appointment.getDate().getTime() - System.currentTimeMillis();
                long twoHoursInMillis = 2 * 60 * 60 * 1000;
                
                if (timeDifference > twoHoursInMillis) {
                    appointments.remove(appointment);
                    
                    // Check waiting list and assign appointment if available
                    if (!waitingList.isEmpty()) {
                        String waitingPatientId = waitingList.poll();
                        createAppointment(doctorName, waitingPatientId, appointment.getDate(), false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void addToWaitingList(String patientId) {
        waitingList.add(patientId);
    }

    public boolean isPatientInWaitingList(String patientId) {
        return waitingList.contains(patientId);
    }
}
