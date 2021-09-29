package pl.edu.pjatk.mpr.lab5.model;

import java.util.List;

public class Order {
    private long id;
    private ClientDetails clientDetails;
    private Address address;
    private List<OrderItem> items;
    private String comments;

    public Order() {
    	
    }
    
    public Order(long id, ClientDetails clientDetails, Address address, List<OrderItem> items, String comments) {
		super();
		this.id = id;
		this.clientDetails = clientDetails;
		this.address = address;
		this.items = items;
		this.comments = comments;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
