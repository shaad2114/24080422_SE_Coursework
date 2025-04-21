package code;

import java.util.List;

public class Physiotherapist {
    private int id;
    private String name;
    private String address;
    private String phone;
    private List<String> expertise;

    public Physiotherapist(int id, String name, String address, String phone, List<String> expertise) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.expertise = expertise;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public List<String> getExpertise() { return expertise; }
}