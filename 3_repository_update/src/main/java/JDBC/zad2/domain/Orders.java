package JDBC.zad2.domain;

import java.util.*;

public class Orders {
	
	private int id;
	private ClientDetails client;
	private Address delivaryAddress;
	private List<OrderItem> items;
	
	public Orders() {
		
	}
	
	public Orders(int id, ClientDetails client, Address delivaryAddress, List<OrderItem> items) {
		super();
		this.id = id;
		this.client = client;
		this.delivaryAddress = delivaryAddress;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ClientDetails getClient() {
		return client;
	}

	public void setClient(ClientDetails client) {
		this.client = client;
	}

	public Address getDelivaryAddress() {
		return delivaryAddress;
	}

	public void setDelivaryAddress(Address delivaryAddress) {
		this.delivaryAddress = delivaryAddress;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	
	
}
