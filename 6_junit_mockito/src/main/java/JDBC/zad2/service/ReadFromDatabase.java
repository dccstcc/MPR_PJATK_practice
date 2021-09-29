package JDBC.zad2.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;

public class ReadFromDatabase {
	
	private Connection connection = null;
	
	private final String selectAllFromTableClientDetailsSql = "SELECT * FROM ClientDetails";
	private final String selectAllFromTableAddressSql = "SELECT * FROM Address";
	private final String selectAllFromTableOrderItemSql = "SELECT * FROM OrderItem";
	private final String selectAllFromTableOrdersSql = "SELECT * FROM Orders";
	
	private PreparedStatement selectAllFromTableClientDetails = null;
	private PreparedStatement selectAllFromTableAddress = null;
	private PreparedStatement selectAllFromTableOrderItem = null;
	private PreparedStatement selectAllFromTableOrders = null;
	
	private ResultSet rs_ClientDetails = null;
	private ResultSet rs_Address = null;
	private ResultSet rs_Orderitem = null;
	private ResultSet rs_Orders = null;
	
	public ReadFromDatabase(Connection connection) {
		
		this.connection = connection;
	}
	
	private List<ClientDetails> selectAllFromTableClientDetails() {
				
		try {
		selectAllFromTableClientDetails = this.connection.prepareStatement(selectAllFromTableClientDetailsSql);
		rs_ClientDetails = selectAllFromTableClientDetails.executeQuery();
		List <ClientDetails> list = new ArrayList<ClientDetails>();
		while(rs_ClientDetails.next()) {
			ClientDetails clientDetails = new ClientDetails();
			clientDetails.setId(rs_ClientDetails.getInt("id"));
			clientDetails.setName(rs_ClientDetails.getString("name"));
			clientDetails.setSurname(rs_ClientDetails.getString("surname"));
			clientDetails.setLogin(rs_ClientDetails.getString("login"));
			list.add(clientDetails);
		}
		return list;
 		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private List<Address> selectAllFromTableAddress() {
		
		try {
		selectAllFromTableAddress = this.connection.prepareStatement(selectAllFromTableAddressSql);
		rs_Address = selectAllFromTableAddress.executeQuery();
		List <Address> list = new ArrayList<Address>();
		while(rs_Address.next()) {
			Address address = new Address();
			address.setId(rs_Address.getInt("id"));
			address.setBuilgingNumber(rs_Address.getString("buildingNumber"));
			address.setFlatNumber(rs_Address.getString("flatNumber"));
			address.setPostalCode(rs_Address.getString("postalCode"));
			address.setCity(rs_Address.getString("city"));
			address.setCountry(rs_Address.getString("country"));
			list.add(address);
		}
		return list;
 		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
private List<OrderItem> selectAllFromTableOrderItem() {
		
		try {
		selectAllFromTableOrderItem = this.connection.prepareStatement(selectAllFromTableOrderItemSql);
		rs_Orderitem = selectAllFromTableOrderItem.executeQuery();
		List <OrderItem> list = new ArrayList<OrderItem>();
		while(rs_Orderitem.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setId(rs_Orderitem.getInt("id"));
			orderItem.setItem(rs_Orderitem.getString("item"));
			orderItem.setDescription(rs_Orderitem.getString("description"));
;			orderItem.setPrice(rs_Orderitem.getDouble("price"));
			list.add(orderItem);
		}
		return list;
 		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

private List<Orders> selectAllFromTableOrders() {
	
	
	try {
	
	selectAllFromTableOrders = this.connection.prepareStatement(selectAllFromTableOrdersSql);
	rs_Orders = selectAllFromTableOrders.executeQuery();
	List <ClientDetails> list_ClientDetails = selectAllFromTableClientDetails();
	List <Address> list_Address = selectAllFromTableAddress();
	List <OrderItem> list_OrderItem = null;
	List <Orders> list_Orders = new ArrayList<Orders>();
	
	
	while(rs_Orders.next()) {
		
		Orders orders = new Orders();
		orders.setId(rs_Orders.getInt("id"));
		
		ClientDetails clientDetails = new ClientDetails(0, "null", "null", "null");
		for(ClientDetails cd : list_ClientDetails) {
			if(rs_Orders.getInt("ClientDetails_id") == cd.getId()) {
				clientDetails = cd;
				break;
			}
		}
		orders.setClient(clientDetails);
		
		Address address = new Address(0, "null", "null", "null", "null", "null", "null");
		for(Address ad : list_Address) {
			if(rs_Orders.getInt("DeliveryAddress_id") == ad.getId()) {
				address = ad;
				break;
			}
		}
		orders.setDeliveryAddress(address);
		
		PreparedStatement selectAllFromTableOrderItem_Secondary = null;
		selectAllFromTableOrderItem_Secondary = this.connection.prepareStatement(selectAllFromTableOrderItemSql);
		ResultSet rs_Items = selectAllFromTableOrderItem_Secondary.executeQuery();
		
		list_OrderItem = new ArrayList<OrderItem>();
		while(rs_Items.next()) {
			if(rs_Orders.getInt("id") == rs_Items.getInt("Order_id")) {
				OrderItem od = new OrderItem();
				od.setId(rs_Items.getInt("id"));
				od.setItem(rs_Items.getString("item"));
				od.setDescription(rs_Items.getString("description"));
				od.setPrice(rs_Items.getFloat("price"));
				list_OrderItem.add(od);
				
			}
			

		}
		
		orders.setItems(list_OrderItem);

		list_Orders.add(orders);
	}
	
	
	return list_Orders;
		} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	
}
	
	public void show() {
		List<ClientDetails> list_ClientDetails = new ArrayList<ClientDetails>();
		list_ClientDetails = selectAllFromTableClientDetails();
		System.out.println("SELECT * FROM ClientDetails");
		for (ClientDetails cd : list_ClientDetails) {
			System.out.println(cd.getId() + " # " + cd.getName() + " # " + cd.getSurname() + " # " + cd.getLogin());
		}
		System.out.println("");
		
		System.out.println("SELECT * FROM Address");
		List<Address> list_Address = new ArrayList<Address>();
		list_Address = selectAllFromTableAddress();
		for (Address ad : list_Address) {
			System.out.println(ad.getId() + " # " + ad.getBuilgingNumber() + " # " + ad.getFlatNumber() + " # " + ad.getPostalCode() + " # " + ad.getCity() + " # " + ad.getCountry());
		}
		System.out.println("");
		
		System.out.println("SELECT * FROM OrderItem");
		List<OrderItem> list_OrderItem = new ArrayList<OrderItem>();
		list_OrderItem = selectAllFromTableOrderItem();
		for (OrderItem od : list_OrderItem) {
			System.out.println(od.getId() + " # " + od.getItem() + " # " + od.getDescription() + " # " + od.getPrice());
		}
		System.out.println("");
		
		System.out.println("SELECT * FROM Orders");
		List<Orders> list_Orders = new ArrayList<Orders>();
		list_Orders = selectAllFromTableOrders();
		for (Orders order : list_Orders) {
			System.out.println ("#############################");
			System.out.println("Order ID: ");
			System.out.println(order.getId());
			System.out.println ("#############################");
			System.out.println("Client: ");
			System.out.println(order.getClient().getId() + " # " + order.getClient().getName() + " # " + order.getClient().getSurname() + " # " + order.getClient().getLogin() + " # ");
			System.out.println ("#############################");
			System.out.println("Address: ");
			System.out.println(order.getDeliveryAddress().getId() + " # " + order.getDeliveryAddress().getStreet() + " # " + order.getDeliveryAddress().getBuilgingNumber() + " # " + order.getDeliveryAddress().getFlatNumber() + " # " + order.getDeliveryAddress().getPostalCode() + " # " + order.getDeliveryAddress().getCity() + " # " + order.getDeliveryAddress().getCountry() + " # ");
			System.out.println ("#############################");
			System.out.println("Items: ");
			for(OrderItem oi : order.getItems()) {
				System.out.println(oi.getId() + " # " + oi.getItem() + " # " + oi.getDescription() + " # " + oi.getPrice() + " # ");
			}
			System.out.println ("#############################");
			System.out.println("");
			System.out.println("");
			System.out.println("");

		}
		System.out.println("");
	}
	
}
