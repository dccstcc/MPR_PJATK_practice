package Repository.zad3.repos;

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
import JDBC.zad2.service.CreateTables;
import Repository.zad3.db.OrderRepository;
import Repository.zad3.db.PagingInfo;

public class HsqlOrderRepository implements OrderRepository {
	
	private Connection connection = null;
	
	private final String selectByIdSql = "SELECT * FROM Orders WHERE id=?";
	private final String insertIntoSql = "INSERT INTO Orders (ClientDetails_id, DeliveryAddress_id) VALUES (?, ?)";
	private final String updateSql = "UPDATE Orders SET ClientDetails_id=?, DeliveryAddress_id=? WHERE id=?";
	private final String deleteSql = "DELETE FROM Orders WHERE id=?";
	private final String selectSql ="SELECT * FROM OrderItem WHERE Order_id=? LIMIT ? OFFSET ?";

	
	private PreparedStatement selectById = null;
	private PreparedStatement insertInto = null;
	private PreparedStatement update = null;
	private PreparedStatement delete = null;
	private PreparedStatement select = null;

	ResultSet resultSelectById = null;
	ResultSet resultSelect = null;
	
	public HsqlOrderRepository(Connection connection) {
		this.connection = connection;
	}
	
	public Orders withId(int id) {
		try {
			selectById = connection.prepareStatement(selectByIdSql);
			selectById.setInt(1, id);
			resultSelectById = selectById.executeQuery();
			resultSelectById.next();
			
			Orders orders = new Orders();
			
			orders.setId(resultSelectById.getInt("id"));
			
			int clientId = resultSelectById.getInt("ClientDetails_id");
			String selectFromClientSql = "SELECT * FROM ClientDetails WHERE id=?";
			PreparedStatement selectFromClient = connection.prepareStatement(selectFromClientSql);
			selectFromClient.setInt(1, clientId);
			ResultSet resultSelectFromClient = selectFromClient.executeQuery();
			resultSelectFromClient.next();
			
			ClientDetails clientDetails = new ClientDetails();
			clientDetails.setId(resultSelectFromClient.getInt("id"));
			clientDetails.setName(resultSelectFromClient.getString("name"));
			clientDetails.setSurname(resultSelectFromClient.getString("surname"));
			clientDetails.setLogin(resultSelectFromClient.getString("login"));

			orders.setClient(clientDetails);

			int addressId = resultSelectById.getInt("DeliveryAddress_id");
			String selectFromAddressSql = "SELECT * FROM Address WHERE id=?";
			PreparedStatement selectFromAddress = connection.prepareStatement(selectFromAddressSql);
			selectFromAddress.setInt(1, addressId);
			ResultSet resultSelectFromAddress = selectFromAddress.executeQuery();
			resultSelectFromAddress.next();
			
			Address address = new Address();
			address.setId(resultSelectFromAddress.getInt("id"));
			address.setStreet(resultSelectFromAddress.getString("street"));
			address.setBuilgingNumber(resultSelectFromAddress.getString("buildingNumber"));
			address.setFlatNumber(resultSelectFromAddress.getString("flatNumber"));
			address.setPostalCode(resultSelectFromAddress.getString("postalCode"));
			address.setCity(resultSelectFromAddress.getString("city"));
			address.setCountry(resultSelectFromAddress.getString("country"));
			
			orders.setDelivaryAddress(address);
			
			int orderId = resultSelectById.getInt("id");
			String selectFromOrderItemSql = "SELECT * FROM OrderItem WHERE Order_id = ?";
			PreparedStatement selectFromOrderItem = connection.prepareStatement(selectFromOrderItemSql);
			selectFromOrderItem.setInt(1, orderId);
			ResultSet resultSelectFromOrderItem = selectFromOrderItem.executeQuery();
			
			List<OrderItem> listOrderItem = new ArrayList<OrderItem>();
			while(resultSelectFromOrderItem.next()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(resultSelectFromOrderItem.getInt("id"));
				orderItem.setItem(resultSelectFromOrderItem.getString("item"));
				orderItem.setDescription(resultSelectFromOrderItem.getString("description"));
				orderItem.setPrice(resultSelectFromOrderItem.getDouble("price"));
				listOrderItem.add(orderItem);
			}
			
			orders.setItems(listOrderItem);
			
			return orders;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void add(Orders orders, List<OrderItem> orderItem) {
		try{
			insertInto = connection.prepareStatement(insertIntoSql);
			insertInto.setInt(1, orders.getClient().getId());
			insertInto.setInt(2, orders.getDelivaryAddress().getId());
			insertInto.executeUpdate();
			
			String insertIntoOrderItemSql = "INSERT INTO OrderItem (Order_id, item, description, price) VALUES (?, ?, ?, ?)";
			PreparedStatement  insertIntoOrderItem = connection.prepareStatement(insertIntoOrderItemSql);
			
			String selectIdFromOrdersSql = "SELECT id FROM Orders WHERE ClientDetails_id=?";
			PreparedStatement selectIdFromOrders = connection.prepareStatement(selectIdFromOrdersSql);
			selectIdFromOrders.setInt(1, orders.getClient().getId());
			ResultSet resultSelectIdFromOrders = selectIdFromOrders.executeQuery();
			resultSelectIdFromOrders.next();
			int Order_id = resultSelectIdFromOrders.getInt("id");
						
			String selectFromOrderItemSql = "SELECT * FROM OrderItem";
			PreparedStatement selectFromOrderItem = connection.prepareStatement(selectFromOrderItemSql);
			ResultSet resultSelectFromOrderItem = selectFromOrderItem.executeQuery();
			List<OrderItem> listOrderItemNotUnique = new ArrayList<OrderItem>();
			//sprawdza czy dodawane Itemy istnieja juz w bazie danych
			while(resultSelectFromOrderItem.next()) {
				for (OrderItem oi : orderItem) {
					if (!(resultSelectFromOrderItem.getString("item").equals(oi.getItem()))) {
						continue;
					}
					listOrderItemNotUnique.add(oi) ;
				}
			}
			
			List <OrderItem> listOrderItemUnique = new ArrayList<OrderItem>();
			for(OrderItem oi : orderItem){
				boolean uniqueItemFlags = true;
					for (int i = 0; i<listOrderItemNotUnique.size(); i++) {
					if(oi.getItem().equals(listOrderItemNotUnique.get(i).getItem())) {
						uniqueItemFlags = false;
						}
					}
				if(uniqueItemFlags) {
					listOrderItemUnique.add(oi);
				}

			}
			
			for (OrderItem oi : orderItem) {
				insertIntoOrderItem.setInt(1, Order_id);
				insertIntoOrderItem.setString(2, oi.getItem());
				insertIntoOrderItem.setString(3, oi.getDescription());
				insertIntoOrderItem.setDouble(4, oi.getPrice());
				insertIntoOrderItem.executeUpdate();
			}
			
					
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void modify(Orders orders, List<OrderItem> orderItem) {
		try {
		update = connection.prepareStatement(updateSql);
		update.setInt(1, orders.getClient().getId());
		update.setInt(2, orders.getDelivaryAddress().getId());
		update.setInt(3, orders.getId());
		update.executeUpdate();
		
		String updateOderItemSql = "UPDATE OrderItem SET Order_id=?, item=?, description=?, price=? WHERE Order_id=? OR item=?";
		for(OrderItem oi : orderItem) {
			PreparedStatement updateOrderItem = connection.prepareStatement(updateOderItemSql);
			updateOrderItem.setInt(1, orders.getId());
			updateOrderItem.setString(2, oi.getItem());
			updateOrderItem.setString(3, oi.getDescription());
			updateOrderItem.setDouble(4, oi.getPrice());
			updateOrderItem.setInt(5, orders.getId());
			updateOrderItem.setString(6, oi.getItem());
			updateOrderItem.executeUpdate();
		}
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(Orders orders){
		try{
			delete = connection.prepareStatement(deleteSql);
			delete.setInt(1, orders.getId());
			delete.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<OrderItem> allOnPage(Orders orders, PagingInfo pagingInfo) {
		
		List<OrderItem> listOrderItem = new ArrayList <OrderItem>();
		
		try {
		select = connection.prepareStatement(selectSql);
		select.setInt(1, orders.getId());
		select.setInt(2, pagingInfo.getSize());
		select.setInt(3, pagingInfo.getCurrentPage());
		resultSelect = select.executeQuery();
		
			while(resultSelect.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setId(resultSelect.getInt("id"));
			orderItem.setItem(resultSelect.getString("item"));
			orderItem.setDescription(resultSelect.getString("description"));
			listOrderItem.add(orderItem);
			}
			
			return listOrderItem;
		
		}catch(SQLException e) {
			e.printStackTrace();
			return listOrderItem;
		}
	}
	
	
	 public List<ClientDetails> withItem(String item, PagingInfo pagingInfo) {
		 List <ClientDetails> listClientDetails = new ArrayList<ClientDetails>();
		 String selectOrdertIdSql = "SELECT Order_id FROM OrderItem WHERE item=?";
		 try {
			 PreparedStatement selectOrderId = connection.prepareStatement(selectOrdertIdSql);
			 selectOrderId.setString(1, item);
			 ResultSet resultSelectOrderId = selectOrderId.executeQuery();
			 resultSelectOrderId.next();
			 int orderId = resultSelectOrderId.getInt("Order_id");
			 
			 String selectFromOrdersSql = "SELECT * FROM Orders WHERE id=?";
			 PreparedStatement selectFromOrders = connection.prepareStatement(selectFromOrdersSql);
			 selectFromOrders.setInt(1, orderId);
			 ResultSet resultSelectFromOrders = selectFromOrders.executeQuery();
			 resultSelectFromOrders.next();
			 int clientId = resultSelectFromOrders.getInt("ClientDetails_id");
			 
			 String selectClientSql = "SELECT * FROM ClientDetails WHERE id=? LIMIT ? OFFSET ?";
			 PreparedStatement selectClient = connection.prepareStatement(selectClientSql);
			 selectClient.setInt(1, clientId);
			 selectClient.setInt(2, pagingInfo.getSize());
			 selectClient.setInt(3, pagingInfo.getCurrentPage());
			 ResultSet resultSelectClient = selectClient.executeQuery();
			 
			 while(resultSelectClient.next()) {
				 ClientDetails clientDetails = new ClientDetails();
				 clientDetails.setId(resultSelectClient.getInt("id"));
				 clientDetails.setName(resultSelectClient.getString("name"));
				 clientDetails.setSurname(resultSelectClient.getString("surname"));
				 clientDetails.setLogin(resultSelectClient.getString("login"));						 
				 listClientDetails.add(clientDetails);
			 }
			 
			 return listClientDetails;
			 
		 } catch(SQLException e) {
			 e.printStackTrace();
			 return listClientDetails;
		 }
	 }
	 
	 
	 public List<OrderItem> withClient(String login, PagingInfo pagingInfo) {
		 List<OrderItem> listOrderItem = new ArrayList<OrderItem>();
		 String selectClientIdSql = "SELECT id FROM ClientDetails WHERE login=?";
		 try {
			 PreparedStatement selectClientId = connection.prepareStatement(selectClientIdSql);
			 selectClientId.setString(1, login);
			 ResultSet resultSelectClientId = selectClientId.executeQuery();
			 resultSelectClientId.next();
			 int clientId = resultSelectClientId.getInt("id");
			 
			 String selectOrderIdSql = "SELECT id FROM Orders WHERE ClientDetails_id=?";
			 PreparedStatement selectOrderId = connection.prepareStatement(selectOrderIdSql);
			 selectOrderId.setInt(1, clientId);
			 ResultSet resultOrderId = selectOrderId.executeQuery();
			 resultOrderId.next();
			 int orderId = resultOrderId.getInt("id");
			 
			 String selectOrderItemSql = "SELECT * FROM OrderItem WHERE Order_id=? LIMIT ? OFFSET ?";
			 PreparedStatement selectOrderItem = connection.prepareStatement(selectOrderItemSql);
			 selectOrderItem.setInt(1, orderId);
			 selectOrderItem.setInt(2, pagingInfo.getSize());
			 selectOrderItem.setInt(3, pagingInfo.getCurrentPage());
			 ResultSet resultSelectOrderItem = selectOrderItem.executeQuery();
			 
			 while(resultSelectOrderItem.next()) {
				 OrderItem orderItem = new OrderItem();
				 orderItem.setId(resultSelectOrderItem.getInt("id"));
				 orderItem.setItem(resultSelectOrderItem.getString("item"));
				 orderItem.setDescription(resultSelectOrderItem.getString("description"));
				 orderItem.setPrice(resultSelectOrderItem.getDouble("price"));
				 listOrderItem.add(orderItem);
			 }
			 
			 return listOrderItem;
			 
		 }catch(SQLException e) {
			 e.printStackTrace();
			 return listOrderItem;
		 }
		 
	 }
	 
	 public void viewOrderSummary (int id) {
		 String selectFromOrdersSql = "SELECT * FROM Orders WHERE id=" + id;
		 try {
			 PreparedStatement selectFromOrders = connection.prepareStatement(selectFromOrdersSql);
		 	ResultSet resultSelectFromOrders = selectFromOrders.executeQuery();
		 	resultSelectFromOrders.next();
			int clientId = resultSelectFromOrders.getInt("ClientDetails_id");	 
			int addressId = resultSelectFromOrders.getInt("DeliveryAddress_id");
			
			String selectFromClientsSql = "SELECT * FROM ClientDetails WHERE id=" + clientId;
			PreparedStatement selectFromClient = connection.prepareStatement(selectFromClientsSql);
			ResultSet resultSelectFromClient = selectFromClient.executeQuery();
			
			String selectFromAddressSql = "SELECT * FROM Address WHERE id=" + addressId;
			PreparedStatement selectFromAddress = connection.prepareStatement(selectFromAddressSql);
			ResultSet resultSelectFromAddress = selectFromAddress.executeQuery();
			resultSelectFromAddress.next();
			
			String selectFromItemsSql = "SELECT * FROM OrderItem WHERE Order_id=" + id;
			PreparedStatement selectFromItems = connection.prepareStatement(selectFromItemsSql);
			ResultSet resultSelectFromItems = selectFromItems.executeQuery();
			resultSelectFromClient.next();
			
			ClientDetails client = new ClientDetails();
			client.setId(resultSelectFromClient.getInt("id"));
			client.setName(resultSelectFromClient.getString("name"));
			client.setSurname(resultSelectFromClient.getString("surname"));
			client.setLogin(resultSelectFromClient.getString("login"));
			
			Address address = new Address();
			address.setId(resultSelectFromAddress.getInt("id"));
			address.setStreet(resultSelectFromAddress.getString("street"));
			address.setBuilgingNumber(resultSelectFromAddress.getString("buildingNumber"));
			address.setFlatNumber(resultSelectFromAddress.getString("flatNumber"));
			address.setPostalCode(resultSelectFromAddress.getString("postalCode"));
			address.setCity(resultSelectFromAddress.getString("city"));
			address.setCountry(resultSelectFromAddress.getString("country"));

			List<OrderItem>listOrderItem = new ArrayList<OrderItem>();
			while(resultSelectFromItems.next()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(resultSelectFromItems.getInt("id"));
				orderItem.setItem(resultSelectFromItems.getString("item"));
				orderItem.setDescription(resultSelectFromItems.getString("description"));
				orderItem.setPrice(resultSelectFromItems.getDouble("price"));
				listOrderItem.add(orderItem);
			}
			System.out.println("Order_id = "+ id);
			System.out.println("");
			
			System.out.println("SELECT * FROM ClientDetails");
			System.out.println(client.getId() + "     #     " + client.getName() + "     #     " + client.getSurname() + "     #     " + client.getLogin());
			System.out.println("");
			System.out.println("");
			System.out.println("");

			System.out.println("SELECT * FROM Address");
			System.out.println(address.getId() + "     #     " + address.getStreet() + "     #     " + address.getBuilgingNumber() + "     #     " + address.getFlatNumber() + "     #     " + address.getPostalCode() + "     #     " + address.getCity() + "     #     " + address.getCountry());
			System.out.println("");
			System.out.println("");
			System.out.println("");

			System.out.println("SELECT * FROM Orderitem");
			for(OrderItem oi : listOrderItem) {
				System.out.println(oi.getId() + "     #     " + oi.getItem() + "     #     " + oi.getDescription() + "     #     " + oi.getPrice());
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");

		 } catch(SQLException e) {
			 e.printStackTrace();
		 }
		 
		 

		 
	 }



}
