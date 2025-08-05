package model;

public class Student {
    private int id;
    private String name;
    private String email;
    private String password, username;
    private String profilePicture;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String fname, String mname, String lname) { 
		if(mname==null)
		this.name = fname + " " + lname;
		else this.name = fname + " " + mname + " " + lname;
	}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
	
	public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}
