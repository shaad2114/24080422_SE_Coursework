import java.time.LocalDateTime;

public class Appointment {
    private String treatmentName;
    private LocalDateTime dateTime;
    private Physiotherapist physiotherapist;
    private Booking booking;

    public Appointment(String treatmentName, LocalDateTime dateTime, Physiotherapist physiotherapist) {
        this.treatmentName = treatmentName;
        this.dateTime = dateTime;
        this.physiotherapist = physiotherapist;
    }

    public String getTreatmentName() { return treatmentName; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Physiotherapist getPhysiotherapist() { return physiotherapist; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}