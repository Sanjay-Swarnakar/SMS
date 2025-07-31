package model;

public class User {
    private int id;
    private String username, role, fullname, email, phone, address;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
	
	public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
	
	public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
	
	public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
	
	public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
