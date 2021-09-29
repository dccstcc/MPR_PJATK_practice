package unitOfWork.zad4.implement.remoteDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.Orders;
import unitOfWork.zad4.implement.entityBuilder.IEntityBuilder;
import unitOfWork.zad4.repositories.IOrderRepository;
import unitOfWork.zad4.unitOfWork.IUnitOfWork;

public class RemoteOrderRepository extends Repository<Orders> implements IOrderRepository{
	
	private String selectAddressIdWithStreetSql = "SELECT id FROM Address WHERE street=";
	private String selectOrdersWithAddressIdSql = "SELECT * FROM Orders WHERE DeliveryAddress_id=";
	private String selectClientIdWithNameSql = "SELECT id FROM ClientDetails WHERE name=";
	private String selectOrderWithClientIdSql = "SELECT * FROM Orders WHERE ClientDetails_id=";
	private String selectOrderIdWithItemSql = "SELECT Order_id FROM OrderItem WHERE item=?";
	
	private PreparedStatement selectAddressIdWithStreetStmt;
	private PreparedStatement selectOrdersWithAddressIdStmt;
	private PreparedStatement selectClientIdWithNameStmt;
	private PreparedStatement selectOrderWithClientIdStmt;
	private PreparedStatement selectOrderIdWithItemStmt;
	private PreparedStatement selectOrderWithOrderIdStmt;
	
	private ResultSet selectAddressIdWithStreetResult;
	private ResultSet selectOrdersWithAddressIdResult;
	private ResultSet selectClientIdWithNameResult;
	private ResultSet selectOrderWithClientIdResult;
	private ResultSet selectOrderIdWithItemResult;
	private ResultSet selectOrderWithOrderIdResult;
	
	private int addressIdWithStreet;
	private int clientIdWithName;
	private int orderIdWithItem;
	
	public RemoteOrderRepository (Connection connection, IEntityBuilder<Orders> entityBuilder, IUnitOfWork uow) {
		super(connection, entityBuilder, uow);
	}
	
	protected String getTableName() {
		return "Orders";
	}
	
	protected String getUpdateQuery() {
		return "UPDATE Orders SET ClientDetails_id=?, DeliveryAddress_id=? WHERE id=?";
	}
	
	protected String getInsertQuery() {
		return "INSERT INTO Address (ClientDetails_id, DeliveryAddress_id) VALUES (?, ?)";
	}
	
	protected void setUpUpdateQuery(Orders entity) throws SQLException{
		update.setInt(1, entity.getClient().getId());
		update.setInt(2, entity.getDeliveryAddress().getId());
		update.setInt(3, entity.getId());
	}
	
	protected void setUpInsertQuery (Orders entity) throws SQLException {
		insert.setInt(1, entity.getClient().getId());
		insert.setInt(2, entity.getDeliveryAddress().getId());
	}
	
	private String selectFromOrders(int id) {
		return "SELECT * FROM Orders WERE id=" + id;
	}
	
	public List<Orders> withAddress(String street) throws SQLException {
		this.selectAddressIdWithStreetSql += street;
		this.selectAddressIdWithStreetStmt = connection.prepareStatement(selectAddressIdWithStreetSql);
		this.selectAddressIdWithStreetResult = selectAddressIdWithStreetStmt.executeQuery();
		this.addressIdWithStreet = selectAddressIdWithStreetResult.getInt("id");
		this.selectOrdersWithAddressIdSql += addressIdWithStreet;
		this.selectOrdersWithAddressIdStmt = connection.prepareStatement(selectOrdersWithAddressIdSql);
		this.selectOrdersWithAddressIdResult = selectOrdersWithAddressIdStmt.executeQuery();
		List<Orders> listOrders = new LinkedList<Orders>();
		while (selectOrdersWithAddressIdResult.next()) {
			Orders orders = new Orders();
			ClientDetails client = new ClientDetails();
			Address address = new Address();
			orders.setId(selectOrdersWithAddressIdResult.getInt("id"));
			client.setId(selectOrdersWithAddressIdResult.getInt("ClientDetails_id"));
			address.setId(selectOrdersWithAddressIdResult.getInt("DeliveryAddress_id"));
			listOrders.add(orders);
		}
		return listOrders;
	}
	
	public List<Orders> withClient(String name) throws SQLException {
		this.selectClientIdWithNameSql += name;
		this.selectClientIdWithNameStmt = connection.prepareStatement(selectClientIdWithNameSql);
		this.selectClientIdWithNameResult = selectClientIdWithNameStmt.executeQuery();
		this.clientIdWithName = selectClientIdWithNameResult.getInt("id");
		this.selectOrderWithClientIdSql += clientIdWithName;
		this.selectOrderWithClientIdStmt = connection.prepareStatement(selectOrderWithClientIdSql);
		this.selectOrderWithClientIdResult = selectOrderWithClientIdStmt.executeQuery();
		List<Orders> listOrders = new LinkedList<Orders>();
		while (selectOrderWithClientIdResult.next()) {
			Orders orders = new Orders();
			ClientDetails client = new ClientDetails();
			Address address = new Address();
			orders.setId(selectOrderWithClientIdResult.getInt("id"));
			client.setId(selectOrderWithClientIdResult.getInt("ClientDetails_id"));
			address.setId(selectOrderWithClientIdResult.getInt("DeliveryAddress_id"));
			listOrders.add(orders);
		}
		return listOrders;
	}
	
	
	public List<Orders> withItem(String item) throws SQLException {
		this.selectOrderIdWithItemSql += item;
		this.selectOrderIdWithItemStmt = connection.prepareStatement(selectOrderIdWithItemSql);
		this.selectOrderIdWithItemResult = selectOrderIdWithItemStmt.executeQuery();
		this.orderIdWithItem = selectOrderIdWithItemResult.getInt("Order_id");
		this.selectOrderWithOrderIdStmt = connection.prepareStatement(selectFromOrders(orderIdWithItem));
		this.selectOrderWithOrderIdResult = selectOrderWithOrderIdStmt.executeQuery();
		List<Orders> listOrders = new LinkedList<Orders>();
		while (selectOrderWithOrderIdResult.next()) {
			Orders orders = new Orders();
			ClientDetails client = new ClientDetails();
			Address address = new Address();
			orders.setId(selectOrderWithOrderIdResult.getInt("id"));
			client.setId(selectOrderWithOrderIdResult.getInt("ClientDetails_id"));
			address.setId(selectOrderWithOrderIdResult.getInt("DeliveryAddress_id"));
			listOrders.add(orders);
		}
		return listOrders;
	}
}
