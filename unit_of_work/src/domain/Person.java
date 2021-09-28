package domain;

import java.util.ArrayList;
import java.util.List;

public class Person extends Entity {

	public Person() {
		super();
		this.addresses = new ArrayList<Address>();
	}
	
	private String firstName;
	private String surname;
	private String pesel;
	
	private User user;
	private List<Address> addresses;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPesel() {
		return pesel;
	}
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		if(!this.equals(user.getPerson()))
			user.setPerson(this);
		this.user = user;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
