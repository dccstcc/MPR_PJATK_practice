package com.example.jdbcandmaven.domain;

public class ClientDetails {
	
	private long id;
	private String name;
	private String surname;
	private String login;
	
	public ClientDetails() {
	
	}
	
	public ClientDetails(long id, String name, String surname, String login) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.login = login;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
