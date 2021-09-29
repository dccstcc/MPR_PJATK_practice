package JDBC.zad2.domain;

public class ClientDetails {
	
	private int id;
	private String name;
	private String surname;
	private String login;
	
	public ClientDetails() {
	
	}
	
	public ClientDetails(int id, String name, String surname, String login) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
	
}
