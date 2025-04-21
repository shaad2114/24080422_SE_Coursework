public class Main {
    public static void main(String[] args) {
        ClinicService clinic = new ClinicService();
        clinic.initializeSampleData();
        clinic.run();
    }
}