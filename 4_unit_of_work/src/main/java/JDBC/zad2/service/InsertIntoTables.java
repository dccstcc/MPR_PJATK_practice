package JDBC.zad2.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;

public class InsertIntoTables {
	
	private Connection connection = null;
	
	private final String insertIntoClientDetailsSql = "INSERT INTO ClientDetails (name, surname, login) VALUES (?, ?, ?)";
	private final String insertIntoAddressSql = "INSERT INTO Address (street, buildingNumber, flatNumber, postalCode, city) VALUES (?, ?, ?, ?, ?)";
	private final String insertIntoOrdersSql = "INSERT INTO Orders (ClientDetails_id, DeliveryAddress_id) VALUES (?, ?)";
	private final String insertIntoOrderItemSql = "INSERT INTO OrderItem (Order_id, item, description, price) VALUES (?, ?, ?, ?)";

	private PreparedStatement insertIntoClientDetails;
	private PreparedStatement insertIntoAddress;
	private PreparedStatement insertIntoOrders;
	private PreparedStatement insertIntoOrderItem;

	public InsertIntoTables(Connection connection) {
		this.connection = connection;
		
	 try {
		 insertIntoClientDetails = this.connection.prepareStatement(insertIntoClientDetailsSql);
		 insertIntoAddress = this.connection.prepareStatement(insertIntoAddressSql);
		 insertIntoOrders = this.connection.prepareStatement(insertIntoOrdersSql);
		 insertIntoOrderItem = this.connection.prepareStatement(insertIntoOrderItemSql);

	 } catch(SQLException e) {
		 e.printStackTrace();
	 }
	}
	
	public InsertIntoTables InsertIntoClientDetails(ClientDetails clientDetails) {
		
		try {
			
			insertIntoClientDetails.setString(1, clientDetails.getName());
			insertIntoClientDetails.setString(2, clientDetails.getSurname());
			insertIntoClientDetails.setString(3, clientDetails.getLogin());

			insertIntoClientDetails.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public InsertIntoTables InsertIntoAddress(Address address) {
		
		try {
			
			insertIntoAddress.setString(1, address.getStreet());
			insertIntoAddress.setString(2, address.getBuilgingNumber());
			insertIntoAddress.setString(3, address.getFlatNumber());
			insertIntoAddress.setString(4, address.getPostalCode());
			insertIntoAddress.setString(5, address.getCity());

			insertIntoAddress.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public InsertIntoTables InsertIntoOrders(Orders orders) {
		
		try {
			
			insertIntoOrders.setLong(1, orders.getClient().getId());
			insertIntoOrders.setLong(2, orders.getDeliveryAddress().getId());

			insertIntoOrders.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	
public InsertIntoTables InsertIntoOrderItem(OrderItem orderItem, Orders orders) {
		
		try {
			
			insertIntoOrderItem.setLong(1, orders.getId());
			insertIntoOrderItem.setString(2, orderItem.getItem());
			insertIntoOrderItem.setString(3, orderItem.getDescription());
			insertIntoOrderItem.setDouble(4, orderItem.getPrice());

			insertIntoOrderItem.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
}
