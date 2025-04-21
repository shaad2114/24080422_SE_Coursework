package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    @Test
    public void testBooking() {
        Patient patient = new Patient(1, "Alice", "123 Street", "555-1234");
        Physiotherapist physiotherapist = new Physiotherapist(1, "Helen", "Rehabilitation");
        TreatmentAppointment appointment = new TreatmentAppointment(1, 1, 1, new Date());
        Booking booking = new Booking(1, patient, physiotherapist, appointment);

        assertNotNull(booking);
        assertEquals("Alice", booking.getPatient().getName());
        assertEquals("Helen", booking.getPhysiotherapist().getName());
    }
}