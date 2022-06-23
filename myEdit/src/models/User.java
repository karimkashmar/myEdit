package models;

import java.io.Serializable;

/*
 * 
 */

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public String FirstName;
	public String LastName;
	public String Username;
	public String Password;
	public String EmailAddress;
	public boolean IsAdmin;

	// All properties needed in this specific message type needs to be defined here
	public User(String FirstName, String LastName, String EmailAddress, String Username, String Password, boolean IsAdmin) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Username = Username;
		this.Password = Password;
		this.EmailAddress = EmailAddress;
		this.IsAdmin = IsAdmin;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}

	public boolean isIsAdmin() {
		return IsAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		IsAdmin = isAdmin;
	}
	

}
