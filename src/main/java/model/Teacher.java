package sms.model;

public class Teacher {
	private int id;
	private String name;
	private String email;
	private String password;
	private String contact;
	private String qualification;
	private String profilePicture;

	// Getters and Setters
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getContact() { return contact; }
	public void setContact(String contact) { this.contact = contact; }

	public String getQualification() { return qualification; }
	public void setQualification(String qualification) { this.qualification = qualification; }

	public String getProfilePicture() { return profilePicture; }
	public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
}
