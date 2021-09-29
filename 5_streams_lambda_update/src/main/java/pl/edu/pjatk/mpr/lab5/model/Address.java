package pl.edu.pjatk.mpr.lab5.model;

public class Address {
    private long id;
    private String street;
    private String buildingNumber;
    private String flatNumber;
    private String postalCode;
    private String city;
    private String country;

    
    
    public Address(long id, String street, String buildingNumber, String flatNumber, String postalCode, String city,
			String country) {
		super();
		this.id = id;
		this.street = street;
		this.buildingNumber = buildingNumber;
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

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
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
