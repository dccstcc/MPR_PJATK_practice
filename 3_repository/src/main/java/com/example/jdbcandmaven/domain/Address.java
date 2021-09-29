package com.example.jdbcandmaven.domain;

public class Address {
	
	private long id;
	private String street;
	private String builgingNumber;
	private String flatNumber;
	private String postalCode;
	private String city;
	private String country;
	
	public Address() {
		
	}
	
	public Address(long id, String street, String builgingNumber, String flatNumber, String postalCode, String city,
			String country) {
		super();
		this.id = id;
		this.street = street;
		this.builgingNumber = builgingNumber;
		this.flatNumber = flatNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilgingNumber() {
		return builgingNumber;
	}

	public void setBuilgingNumber(String builgingNumber) {
		this.builgingNumber = builgingNumber;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
	
}
