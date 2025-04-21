
import org.junit.jupiter.api.*;

import code.Appointment;
import code.Booking;
import code.ClinicService;
import code.Patient;
import code.Physiotherapist;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MainTest {
    private ClinicService clinicService;

    @BeforeEach
    void setUp() {
        clinicService = new ClinicService();
        clinicService.initializeSampleData();
    }

    // Test Patient class
    @Test
    void testPatientCreation() {
        Patient patient = new Patient(100, "John Doe", "123 Main St", "555-1234");
        assertEquals(100, patient.getId());
        assertEquals("John Doe", patient.getName());
    }

    // Test Physiotherapist class
    @Test
    void testPhysiotherapistCreation() {
        Physiotherapist physio = new Physiotherapist(1, "Dr. Smith", "456 Oak Ave", "555-5678", 
            List.of("Physiotherapy", "Rehabilitation"));
        assertEquals("Dr. Smith", physio.getName());
        assertTrue(physio.getExpertise().contains("Physiotherapy"));
    }

    // Test Appointment class
    @Test
    void testAppointmentCreation() {
        Physiotherapist physio = clinicService.physiotherapists.get(0);
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = new Appointment("Massage", now, physio);
        
        assertEquals("Massage", appointment.getTreatmentName());
        assertEquals(physio, appointment.getPhysiotherapist());
    }

    // Test Booking class
    @Test
    void testBookingCreation() {
        Patient patient = clinicService.patients.get(0);
        Booking booking = new Booking(1, patient, "booked");
        
        assertEquals(1, booking.getBookingId());
        assertEquals(patient, booking.getPatient());
    }

    // Test ClinicService initialization
    @Test
    void testSampleDataInitialization() {
        assertEquals(3, clinicService.physiotherapists.size());
        assertEquals(5, clinicService.patients.size());
        assertTrue(clinicService.appointments.size() >= 15); // 4 weeks of sample data
    }

    // Test adding a patient
    @Test
    void testAddPatient() {
        int initialCount = clinicService.patients.size();
        Patient newPatient = new Patient(106, "New Patient", "789 Pine Rd", "555-4321");
        clinicService.patients.add(newPatient);
        
        assertEquals(initialCount + 1, clinicService.patients.size());
        assertTrue(clinicService.patients.contains(newPatient));
    }

    // Test booking an appointment
    @Test
    void testBookAppointment() {
        Appointment availableAppointment = clinicService.appointments.stream()
            .filter(a -> a.getBooking() == null)
            .findFirst()
            .orElse(null);
            
        assertNotNull(availableAppointment);
        
        Patient patient = clinicService.patients.get(0);
        Booking booking = new Booking(clinicService.nextBookingId++, patient, "booked");
        availableAppointment.setBooking(booking);
        
        assertNotNull(availableAppointment.getBooking());
        assertEquals(patient, availableAppointment.getBooking().getPatient());
    }

    // Test finding appointments by physiotherapist
    @Test
    void testFindAppointmentsByPhysiotherapist() {
        Physiotherapist physio = clinicService.physiotherapists.get(0);
        List<Appointment> physioAppointments = clinicService.appointments.stream()
            .filter(a -> a.getPhysiotherapist().equals(physio))
            .collect(Collectors.toList());
            
        assertFalse(physioAppointments.isEmpty());
    }

    // Test appointment status changes
    @Test
    void testAppointmentStatusFlow() {
        Appointment appointment = clinicService.appointments.get(0);
        Patient patient = clinicService.patients.get(0);
        
        // Book appointment
        Booking booking = new Booking(1, patient, "booked");
        appointment.setBooking(booking);
        assertEquals("booked", appointment.getBooking().getStatus());
        
        // Attend appointment
        appointment.getBooking().setStatus("attended");
        assertEquals("attended", appointment.getBooking().getStatus());
        
        // Cancel appointment
        appointment.getBooking().setStatus("cancelled");
        assertEquals("cancelled", appointment.getBooking().getStatus());
    }
}