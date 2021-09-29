package JDBC.zad2.domain;

import java.util.*;

public class Orders extends Entity{
	
	private ClientDetails client;
	private Address deliveryAddress;
	private List<OrderItem> items;
	
	public Orders() {
		
	}
	
	public Orders(int id, ClientDetails client, Address delivaryAddress, List<OrderItem> items) {
		super();
		super.setId(id);
		this.client = client;
		this.deliveryAddress = delivaryAddress;
		this.items = items;
	}


	public ClientDetails getClient() {
		return client;
	}

	public void setClient(ClientDetails client) {
		this.client = client;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address delivaryAddress) {
		this.deliveryAddress = delivaryAddress;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	
	
}
