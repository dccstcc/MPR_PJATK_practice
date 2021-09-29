package JDBC.zad2.domain;

public class Address extends Entity{
	
	private String street;
	private String buildingNumber;
	private String flatNumber;
	private String postalCode;
	private String city;
	private String country;
	
	public Address() {
		
	}
	
	public Address(int id, String street, String builgingNumber, String flatNumber, String postalCode, String city,
			String country) {
		super();
		super.setId(id);
		this.street = street;
		this.buildingNumber = builgingNumber;
		this.flatNumber = flatNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}


	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilgingNumber() {
		return buildingNumber;
	}

	public void setBuilgingNumber(String builgingNumber) {
		this.buildingNumber = builgingNumber;
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
