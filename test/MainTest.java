
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import code.Appointment;
import code.Booking;
import code.ClinicService;
import code.Patient;
import code.Physiotherapist;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    private ClinicService clinicService;
    private Patient testPatient;
    private Physiotherapist testPhysio;

    @BeforeEach
    public void setUp() {
        clinicService = new ClinicService();
        clinicService.initializeSampleData();
        
        testPatient = new Patient(999, "Test Patient", "Test Address", "555-9999");
        testPhysio = new Physiotherapist(99, "Test Physio", "Test Clinic", "555-9999", 
                                       Arrays.asList("Test Therapy"));
        
        clinicService.patients.add(testPatient);
        clinicService.physiotherapists.add(testPhysio);
    }

    @Test
    public void testAddPatient() {
        int initialSize = clinicService.patients.size();
        clinicService.addPatient();
        assertEquals(initialSize + 1, clinicService.patients.size());
    }

    @Test
    public void testRemovePatient() {
        clinicService.patients.add(testPatient);
        int initialSize = clinicService.patients.size();
        clinicService.removePatient();
        assertEquals(initialSize - 1, clinicService.patients.size());
    }

    @Test
    public void testBookAppointment() {
        // Create a test appointment
        Appointment testAppointment = new Appointment("Test Therapy", 
            LocalDateTime.now().plusDays(1), testPhysio);
        clinicService.appointments.add(testAppointment);
        
        // Book the appointment
        clinicService.bookAppointment();
        
        // Verify booking was made
        assertNotNull(testAppointment.getBooking());
    }

    @Test
    public void testChangeOrCancelBooking() {
        // Create and book a test appointment
        Appointment testAppointment = new Appointment("Test Therapy", 
            LocalDateTime.now().plusDays(1), testPhysio);
        Booking testBooking = new Booking(999, testPatient, "booked");
        testAppointment.setBooking(testBooking);
        clinicService.appointments.add(testAppointment);
        
        // Test cancel functionality
        clinicService.changeOrCancelBooking();
        assertEquals("cancelled", testAppointment.getBooking().getStatus());
    }

    @Test
    public void testAttendAppointment() {
        // Create and book a test appointment
        Appointment testAppointment = new Appointment("Test Therapy", 
            LocalDateTime.now().plusDays(1), testPhysio);
        Booking testBooking = new Booking(999, testPatient, "booked");
        testAppointment.setBooking(testBooking);
        clinicService.appointments.add(testAppointment);
        
        // Test attend functionality
        clinicService.attendAppointment();
        assertEquals("attended", testAppointment.getBooking().getStatus());
    }

    @Test
    public void testPrintReport() {
        // This is more of a smoke test since printReport() outputs to console
        assertDoesNotThrow(() -> clinicService.printReport());
    }

    @Test
    public void testPatientConstructor() {
        Patient patient = new Patient(100, "John Doe", "123 Street", "555-1234");
        assertEquals(100, patient.getId());
        assertEquals("John Doe", patient.getName());
    }

    @Test
    public void testPhysiotherapistExpertise() {
        List<String> expertise = Arrays.asList("Massage", "Rehab");
        Physiotherapist physio = new Physiotherapist(1, "Dr. Smith", "Clinic", "555-1111", expertise);
        assertTrue(physio.getExpertise().contains("Massage"));
    }

    @Test
    public void testAppointmentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment("Therapy", now, testPhysio);
        assertEquals(now, appointment.getDateTime());
    }
}