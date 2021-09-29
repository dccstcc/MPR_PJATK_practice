package unitOfWork.zad4.implement.localDb;

import java.util.ArrayList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;

public class LocalDb {
	
	private List<Orders> orders = new ArrayList<Orders>();
	private List<ClientDetails> clients = new ArrayList<ClientDetails>();
	private List<Address> addresses = new ArrayList<Address>();
	private List<OrderItem> items = new ArrayList<OrderItem>();
	
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public List<ClientDetails> getClients() {
		return clients;
	}
	public void setClients(List<ClientDetails> clients) {
		this.clients = clients;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	
}
