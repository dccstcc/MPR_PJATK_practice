package unitOfWork.zad4.implement.entityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;

public class OrderBuilder implements IEntityBuilder<Orders>{
	
	public List <Orders> build(ResultSet rs) throws SQLException {
		List<Orders> list = new LinkedList<Orders>();
		
		Orders orders = new Orders();
		ClientDetails clientDetails = new ClientDetails();
		Address address = new Address();
		List <OrderItem> orderItem = new LinkedList<OrderItem>();
		
		while(rs.next()){
		int orderId = rs.getInt("id");
		clientDetails.setId(rs.getInt("ClientDetails_id"));
		address.setId(rs.getInt("DeliveryAddress_id"));
		orders.setId(orderId);
		orders.setClient(clientDetails);
		orders.setDeliveryAddress(address);
		orders.setItems(orderItem);
		list.add(orders);
		}
		return list;
	}
	
	public Orders build(ResultSet rsOrder, ResultSet rsClient, ResultSet rsAddress, ResultSet rsItem) throws SQLException {
		Orders orders = new Orders();
		ClientDetails clientDetails = new ClientDetails();
		Address address = new Address();
		List <OrderItem> orderItem = new LinkedList<OrderItem>();

		clientDetails.setId(rsClient.getInt("id"));
		clientDetails.setName(rsClient.getString("name"));
		clientDetails.setSurname(rsClient.getString("surname"));
		clientDetails.setLogin(rsClient.getString("login"));
		
		address.setId(rsAddress.getInt("id"));
		address.setStreet(rsAddress.getString("street"));
		address.setBuilgingNumber(rsAddress.getString("buildingNumber"));
		address.setFlatNumber(rsAddress.getString("flatNumber"));
		address.setStreet(rsAddress.getString("street"));
		address.setPostalCode(rsAddress.getString("postalCode"));
		address.setCity(rsAddress.getString("city"));
		address.setCountry(rsAddress.getString("country"));

		do {
		
			OrderItem oi = new OrderItem();
			oi.setId(rsItem.getInt("id"));
			oi.setItem(rsItem.getString("item"));
			oi.setDescription(rsItem.getString("description"));
			oi.setPrice(rsItem.getDouble("price"));
			orderItem.add(oi);
			
		} while(rsItem.next());
		
		orders.setId(rsOrder.getInt("id"));
		orders.setClient(clientDetails);
		orders.setDeliveryAddress(address);
		orders.setItems(orderItem);
		return orders;
	}

}
