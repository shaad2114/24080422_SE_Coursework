
public class Booking {
    private int bookingId;
    private Patient patient;
    private String status; // "booked", "cancelled", "attended"

    public Booking(int bookingId, Patient patient, String status) {
        this.bookingId = bookingId;
        this.patient = patient;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public Patient getPatient() { return patient; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}