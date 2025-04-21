package code;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
//importing all libs
public class ClinicService {
    public List<Physiotherapist> physiotherapists;
    public List<Patient> patients;
    public List<Appointment> appointments;
    private int nextBookingId;
    private Scanner scanner;

    public ClinicService() {
        physiotherapists = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        nextBookingId = 1;
        scanner = new Scanner(System.in);
    }

    public void initializeSampleData() {
        // Initialize physiotherapists
        Physiotherapist p1 = new Physiotherapist(1, "Dr. Strange", "123 Main St", "555-0101", 
                Arrays.asList("Physiotherapy", "Rehabilitation"));
        Physiotherapist p2 = new Physiotherapist(2, "Dr. Alpha", "456 Oak Ave", "555-0202", 
                Arrays.asList("Osteopathy", "Massage"));
        Physiotherapist p3 = new Physiotherapist(3, "Dr. Chris Max", "789 Pine Rd", "555-0303", 
                Arrays.asList("Physiotherapy", "Pool rehabilitation"));
        
        physiotherapists.add(p1);
        physiotherapists.add(p2);
        physiotherapists.add(p3);

        // Initialize patients
        patients.add(new Patient(101, "Leonardo DiCaprio", "101 Elm St", "555-1001"));
        patients.add(new Patient(102, "Priyanka Chopra", "202 Maple Dr", "555-1002"));
        patients.add(new Patient(103, "BTS Jungkook", "303 Cedar Ln", "555-1003"));
        patients.add(new Patient(104, "Gal Gadot", "404 Birch Blvd", "555-1004"));
        patients.add(new Patient(105, "Cristiano Ronaldo", "505 Spruce Way", "555-1005"));

        // Initialize appointments for 4 weeks
        LocalDateTime baseDate = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);
        
        // Week 1 appointments
        appointments.add(new Appointment("Neural mobilisation", baseDate.plusDays(1).plusHours(1), p1));
        appointments.add(new Appointment("Acupuncture", baseDate.plusDays(1).plusHours(2), p1));
        appointments.add(new Appointment("Massage", baseDate.plusDays(2).plusHours(1), p2));
        appointments.add(new Appointment("Mobilisation of spine ", baseDate.plusDays(2).plusHours(2), p2));
        appointments.add(new Appointment("Pool rehabilitation", baseDate.plusDays(3).plusHours(1), p3));
        
        // Week 2 appointments
        appointments.add(new Appointment("Neural mobilisation", baseDate.plusDays(8).plusHours(1), p1));
        appointments.add(new Appointment("Acupuncture", baseDate.plusDays(8).plusHours(3), p1));
        appointments.add(new Appointment("Massage", baseDate.plusDays(9).plusHours(1), p2));
        appointments.add(new Appointment("Mobilisation of spine", baseDate.plusDays(9).plusHours(2), p2));
        appointments.add(new Appointment("Pool rehabilitation", baseDate.plusDays(10).plusHours(1), p3));
        
        // Week 3 appointments
        appointments.add(new Appointment("Neural mobilisation", baseDate.plusDays(15).plusHours(1), p1));
        appointments.add(new Appointment("Acupuncture", baseDate.plusDays(15).plusHours(2), p1));
        appointments.add(new Appointment("Massage", baseDate.plusDays(16).plusHours(1), p2));
        appointments.add(new Appointment("Mobilisation of spine", baseDate.plusDays(16).plusHours(2), p2));
        appointments.add(new Appointment("Pool rehabilitation", baseDate.plusDays(17).plusHours(1), p3));
        
        // Week 4 appointments
        appointments.add(new Appointment("Neural mobilisation", baseDate.plusDays(22).plusHours(1), p1));
        appointments.add(new Appointment("Acupuncture", baseDate.plusDays(22).plusHours(2), p1));
        appointments.add(new Appointment("Massage", baseDate.plusDays(23).plusHours(1), p2));
        appointments.add(new Appointment("Mobilisation of spine", baseDate.plusDays(23).plusHours(2), p2));
        appointments.add(new Appointment("Pool rehabilitation", baseDate.plusDays(24).plusHours(1), p3));
        
        // Add some overlapping appointments for testing
        appointments.add(new Appointment("Massage", baseDate.plusDays(1).plusHours(1), p2));
        appointments.add(new Appointment("Pool rehabilitation", baseDate.plusDays(1).plusHours(1), p3));
    }

    public void run() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n=== Shaad Physio Clinic Booking System ===");
            System.out.println("1. Add/Remove Patient");
            System.out.println("2. Book Treatment Appointment");
            System.out.println("3. Change/Cancel Booking");
            System.out.println("4. Attend Appointment");
            System.out.println("5. Print Report");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            
            int choice = getIntInput(1, 6);
            
            switch (choice) {
                case 1:
                    managePatients();
                    break;
                case 2:
                    bookAppointment();
                    break;
                case 3:
                    changeOrCancelBooking();
                    break;
                case 4:
                    attendAppointment();
                    break;
                case 5:
                    printReport();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting system...");
                    break;
            }
        }
    }

    private void managePatients() {
        System.out.println("\n=== Patient Management ===");
        System.out.println("1. Add Patient");
        System.out.println("2. Remove Patient");
        System.out.println("3. Back to Main Menu");
        System.out.print("Select an option: ");
        
        int choice = getIntInput(1, 3);
        
        switch (choice) {
            case 1:
                addPatient();
                break;
            case 2:
                removePatient();
                break;
            case 3:
                return;
        }
    }

    public void addPatient() {
        System.out.println("\n=== Add New Patient ===");
        
        int id;
        boolean idExists;
        do {
            System.out.print("Enter patient ID: ");
            id = getIntInput(1, Integer.MAX_VALUE);
            final int finalId = id;
            idExists = patients.stream().anyMatch(p -> p.getId() == finalId);
            if (idExists) {
                System.out.println("ID already exists. Please enter a unique ID.");
            }
        } while (idExists);
        
        System.out.print("Enter full name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter telephone number: ");
        String phone = scanner.nextLine();
        
        patients.add(new Patient(id, name, address, phone));
        System.out.println("Patient added successfully!");
    }

    public void removePatient() {
        System.out.println("\n=== Remove Patient ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        
        System.out.println("Registered Patients:");
        for (Patient p : patients) {
            System.out.println(p.getId() + ": " + p.getName());
        }
        
        System.out.print("Enter patient ID to remove: ");
        int id = getIntInput(1, Integer.MAX_VALUE);
        
        Optional<Patient> patient = patients.stream().filter(p -> p.getId() == id).findFirst();
        if (patient.isPresent()) {
            boolean hasBookings = appointments.stream()
                .anyMatch(a -> a.getBooking() != null && a.getBooking().getPatient().equals(patient.get()));
            
            if (hasBookings) {
                System.out.println("Cannot remove patient with active bookings. Cancel bookings first.");
            } else {
                patients.remove(patient.get());
                System.out.println("Patient removed successfully!");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }

    public void bookAppointment() {
        System.out.println("\n=== Book Treatment Appointment ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered. Please add a patient first.");
            return;
        }
        
        // Select patient
        System.out.println("Select a patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i+1) + ". " + patients.get(i).getName() + " (ID: " + patients.get(i).getId() + ")");
        }
        System.out.print("Enter patient number: ");
        int patientIndex = getIntInput(1, patients.size()) - 1;
        Patient patient = patients.get(patientIndex);
        
        // Select search method
        System.out.println("\nSearch appointments by:");
        System.out.println("1. Area of expertise");
        System.out.println("2. Physiotherapist name");
        System.out.print("Select an option: ");
        int searchMethod = getIntInput(1, 2);
        
        List<Appointment> availableAppointments = new ArrayList<>();
        
        if (searchMethod == 1) {
            // Search by expertise
            System.out.println("\nAvailable expertise areas:");
            Set<String> expertiseSet = new HashSet<>();
            for (Physiotherapist p : physiotherapists) {
                expertiseSet.addAll(p.getExpertise());
            }
            
            List<String> expertiseList = new ArrayList<>(expertiseSet);
            for (int i = 0; i < expertiseList.size(); i++) {
                System.out.println((i+1) + ". " + expertiseList.get(i));
            }
            System.out.print("Select expertise: ");
            int expertiseIndex = getIntInput(1, expertiseList.size()) - 1;
            String selectedExpertise = expertiseList.get(expertiseIndex);
            
            availableAppointments = appointments.stream()
                .filter(a -> a.getPhysiotherapist().getExpertise().contains(selectedExpertise))
                .filter(a -> a.getBooking() == null)
                .collect(Collectors.toList());
        } else {
            // Search by physiotherapist name
            System.out.println("\nAvailable physiotherapists:");
            for (int i = 0; i < physiotherapists.size(); i++) {
                System.out.println((i+1) + ". " + physiotherapists.get(i).getName());
            }
            System.out.print("Select physiotherapist: ");
            int physioIndex = getIntInput(1, physiotherapists.size()) - 1;
            Physiotherapist selectedPhysio = physiotherapists.get(physioIndex);
            
            availableAppointments = appointments.stream()
                .filter(a -> a.getPhysiotherapist().equals(selectedPhysio))
                .filter(a -> a.getBooking() == null)
                .collect(Collectors.toList());
        }
        
        if (availableAppointments.isEmpty()) {
            System.out.println("No available appointments found for the selected criteria.");
            return;
        }
        
        // Display available appointments
        System.out.println("\nAvailable Appointments:");
        for (int i = 0; i < availableAppointments.size(); i++) {
            Appointment a = availableAppointments.get(i);
            System.out.println((i+1) + ". " + a.getTreatmentName() + " with " + a.getPhysiotherapist().getName() + 
                             " at " + DateTimeUtils.formatDateTime(a.getDateTime()));
        }
        
        System.out.print("Select appointment to book: ");
        int appointmentIndex = getIntInput(1, availableAppointments.size()) - 1;
        Appointment selectedAppointment = availableAppointments.get(appointmentIndex);
        
        // Check for time conflicts
        boolean hasConflict = appointments.stream()
            .filter(a -> a.getBooking() != null && a.getBooking().getPatient().equals(patient))
            .anyMatch(a -> a.getDateTime().equals(selectedAppointment.getDateTime()));
        
        if (hasConflict) {
            System.out.println("Cannot book appointment. You already have another appointment at the same time.");
            return;
        }
        
        // Create booking
        Booking booking = new Booking(nextBookingId++, patient, "booked");
        selectedAppointment.setBooking(booking);
        System.out.println("Appointment booked successfully! Booking ID: " + booking.getBookingId());
    }

    public void changeOrCancelBooking() {
        System.out.println("\n=== Change/Cancel Booking ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        
        // Select patient
        System.out.println("Select a patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i+1) + ". " + patients.get(i).getName() + " (ID: " + patients.get(i).getId() + ")");
        }
        System.out.print("Enter patient number: ");
        int patientIndex = getIntInput(1, patients.size()) - 1;
        Patient patient = patients.get(patientIndex);
        
        // Get patient's bookings (excluding attended)
        List<Appointment> patientBookings = appointments.stream()
            .filter(a -> a.getBooking() != null && a.getBooking().getPatient().equals(patient))
            .filter(a -> !a.getBooking().getStatus().equals("attended"))
            .collect(Collectors.toList());
        
        if (patientBookings.isEmpty()) {
            System.out.println("No changeable bookings found for this patient.");
            return;
        }
        
        System.out.println("\nYour Bookings:");
        for (int i = 0; i < patientBookings.size(); i++) {
            Appointment a = patientBookings.get(i);
            System.out.println((i+1) + ". Booking ID: " + a.getBooking().getBookingId() + 
                             " - " + a.getTreatmentName() + " with " + a.getPhysiotherapist().getName() + 
                             " at " + DateTimeUtils.formatDateTime(a.getDateTime()) + 
                             " (Status: " + a.getBooking().getStatus() + ")");
        }
        
        System.out.print("Select booking to change/cancel: ");
        int bookingIndex = getIntInput(1, patientBookings.size()) - 1;
        Appointment oldAppointment = patientBookings.get(bookingIndex);
        int bookingId = oldAppointment.getBooking().getBookingId();
        
        // Prevent modifying attended appointments
        if (oldAppointment.getBooking().getStatus().equals("attended")) {
            System.out.println("Cannot modify an already attended appointment.");
            return;
        }
        
        System.out.println("\n1. Change booking");
        System.out.println("2. Cancel booking");
        System.out.print("Select an option: ");
        int option = getIntInput(1, 2);
        
        if (option == 1) {
            // Change booking - first cancel then book new one
            System.out.println("\n=== Change Booking ===");
            System.out.println("First, select a new appointment to replace the current one.");
            
            // Display all available appointments (excluding the one being changed)
            List<Appointment> availableAppointments = appointments.stream()
                .filter(a -> a.getBooking() == null || a.equals(oldAppointment))
                .filter(a -> !a.equals(oldAppointment))
                .collect(Collectors.toList());
            
            if (availableAppointments.isEmpty()) {
                System.out.println("No available appointments found.");
                return;
            }
            
            System.out.println("\nAvailable Appointments:");
            for (int i = 0; i < availableAppointments.size(); i++) {
                Appointment a = availableAppointments.get(i);
                System.out.println((i+1) + ". " + a.getTreatmentName() + " with " + a.getPhysiotherapist().getName() + 
                                 " at " + DateTimeUtils.formatDateTime(a.getDateTime()));
            }
            
            System.out.print("Select new appointment: ");
            int newAppointmentIndex = getIntInput(1, availableAppointments.size()) - 1;
            Appointment newAppointment = availableAppointments.get(newAppointmentIndex);
            
            // Check for time conflicts (excluding the old appointment)
            boolean hasConflict = appointments.stream()
                .filter(a -> a.getBooking() != null && a.getBooking().getPatient().equals(patient))
                .filter(a -> !a.equals(oldAppointment))
                .anyMatch(a -> a.getDateTime().equals(newAppointment.getDateTime()));
            
            if (hasConflict) {
                System.out.println("Cannot change booking. You already have another appointment at the same time.");
                return;
            }
            
            // Move booking to new appointment
            oldAppointment.setBooking(null);
            newAppointment.setBooking(new Booking(bookingId, patient, "booked"));
            System.out.println("Booking changed successfully! New appointment details:");
            System.out.println(newAppointment.getTreatmentName() + " with " + newAppointment.getPhysiotherapist().getName() + 
                             " at " + DateTimeUtils.formatDateTime(newAppointment.getDateTime()));
        } else {
            // Cancel booking
            oldAppointment.getBooking().setStatus("cancelled");
            System.out.println("Booking cancelled successfully!");
        }
    }

    public void attendAppointment() {
        System.out.println("\n=== Attend Appointment ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        
        // Select patient
        System.out.println("Select a patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i+1) + ". " + patients.get(i).getName() + " (ID: " + patients.get(i).getId() + ")");
        }
        System.out.print("Enter patient number: ");
        int patientIndex = getIntInput(1, patients.size()) - 1;
        Patient patient = patients.get(patientIndex);
        
        // Get patient's booked appointments (not cancelled or already attended)
        List<Appointment> patientBookings = appointments.stream()
            .filter(a -> a.getBooking() != null && a.getBooking().getPatient().equals(patient))
            .filter(a -> a.getBooking().getStatus().equals("booked"))
            .collect(Collectors.toList());
        
        if (patientBookings.isEmpty()) {
            System.out.println("No booked appointments found for this patient.");
            return;
        }
        
        System.out.println("\nYour Booked Appointments:");
        for (int i = 0; i < patientBookings.size(); i++) {
            Appointment a = patientBookings.get(i);
            System.out.println((i+1) + ". Booking ID: " + a.getBooking().getBookingId() + 
                             " - " + a.getTreatmentName() + " with " + a.getPhysiotherapist().getName() + 
                             " at " + DateTimeUtils.formatDateTime(a.getDateTime()));
        }
        
        System.out.print("Select appointment to mark as attended: ");
        int bookingIndex = getIntInput(1, patientBookings.size()) - 1;
        Appointment appointment = patientBookings.get(bookingIndex);
        
        appointment.getBooking().setStatus("attended");
        System.out.println("Appointment marked as attended. Thank you!");
    }

    public void printReport() {
        System.out.println("\n=== Shaad Physio Clinic Report ===");
        System.out.println("=== Treatment Appointments for the Term ===");
        
        // Group appointments by physiotherapist
        Map<Physiotherapist, List<Appointment>> appointmentsByPhysio = appointments.stream()
            .collect(Collectors.groupingBy(Appointment::getPhysiotherapist));
        
        // Print appointments for each physiotherapist
        for (Physiotherapist physio : physiotherapists) {
            System.out.println("\nPhysiotherapist: " + physio.getName());
            System.out.println("Expertise: " + String.join(", ", physio.getExpertise()));
            
            List<Appointment> physioAppointments = appointmentsByPhysio.getOrDefault(physio, Collections.emptyList());
            if (physioAppointments.isEmpty()) {
                System.out.println("No appointments scheduled.");
                continue;
            }
            
            System.out.println("Appointments:");
            for (Appointment a : physioAppointments) {
                String patientName = (a.getBooking() != null) ? a.getBooking().getPatient().getName() : "None";
                String status = (a.getBooking() != null) ? a.getBooking().getStatus() : "Available";
                
                System.out.println("- Treatment: " + a.getTreatmentName() + 
                                 ", Date/Time: " + DateTimeUtils.formatDateTime(a.getDateTime()) + 
                                 ", Patient: " + patientName + 
                                 ", Status: " + status);
            }
        }
        
        // Print physiotherapist ranking by attended appointments
        System.out.println("\n=== Physiotherapist Ranking by Attended Appointments ===");
        
        Map<Physiotherapist, Long> attendedCounts = physiotherapists.stream()
            .collect(Collectors.toMap(
                physio -> physio,
                physio -> appointments.stream()
                    .filter(a -> a.getPhysiotherapist().equals(physio))
                    .filter(a -> a.getBooking() != null && a.getBooking().getStatus().equals("attended"))
                    .count()
            ));
        
        // Sort by attended count in descending order
        List<Physiotherapist> rankedPhysios = physiotherapists.stream()
            .sorted(Comparator.comparingLong(physio -> -attendedCounts.get(physio)))
            .collect(Collectors.toList());
        
        for (int i = 0; i < rankedPhysios.size(); i++) {
            Physiotherapist physio = rankedPhysios.get(i);
            System.out.println((i+1) + ". " + physio.getName() + 
                             " - Attended appointments: " + attendedCounts.get(physio));
        }
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // Clear invalid input
            }
        }
    }
}