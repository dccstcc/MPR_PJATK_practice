package unitOfWork.zad4.implement.remoteDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.OrderItem;
import unitOfWork.zad4.implement.entityBuilder.IEntityBuilder;
import unitOfWork.zad4.repositories.IItemRepository;
import unitOfWork.zad4.unitOfWork.IUnitOfWork;

public class RemoteItemRepository extends Repository<OrderItem> implements IItemRepository{
	
	private String selectAddressIdWithStreetSql = "SELECT id FROM Address WHERE street=";
	private String selectOrderIdWithAddressIdSql = "SELECT id FROM Orders WHERE DeliveryAddress_id=";
	private String selectClientIdWithNameSql = "SELECT id FROM ClientDetails WHERE name=";
	private String selectOrderIdWithClientIdSql = "SELECT id FROM Orders WHERE ClientDetails_id=?";
	private String selectOrderItemWithOrderIdSql = "SELECT * FROM OrderItem WHERE Order_id=?";

	
	private PreparedStatement selectAddressIdWithStreetStmt;
	private PreparedStatement selectOrderIdWithAddressIdStmt;
	private PreparedStatement selectItemsWithOrderIdStmt;
	private PreparedStatement selectClientIdWithNameStmt;
	private PreparedStatement selectOrderIdWithClientIdStmt;
	private PreparedStatement selectOrderItemWithOrderIdStmt;
	private PreparedStatement selectOrderItemWithOrderIdStmt2;

	
	private ResultSet selectAddressIdWithStreetResult;
	private ResultSet selectOrderIdWithAddressIdResult;
	private ResultSet selectItemsWithOrderIdResult;
	private ResultSet selectClientIdWithNameResult;
	private ResultSet selectOrderIdWithClientIdResult;
	private ResultSet selectOrderItemWithOrderIdResult;
	private ResultSet selectOrderItemWithOrderIdResult2;
	
	private int addressIdWithStreet;
	private int orderIdWithAddressId;
	private int clientIdWithName;
	private int orderIdWithClientId;
	
	public RemoteItemRepository (Connection connection, IEntityBuilder<OrderItem> entityBuilder, IUnitOfWork uow) {
		super(connection, entityBuilder, uow);
	}
	
	protected String getTableName() {
		return "OrderItem";
	}
	
	protected String getUpdateQuery() {
		return "UPDATE OrderItem SET Order_id=?, item=?, description=?, price=? WHERE id=?";
	}
	
	protected String getInsertQuery() {
		return "INSERT INTO OrderItem (Order_id, item, description, price) VALUES (?, ?, ?, ?)";
	}
	
	protected void setUpUpdateQuery(OrderItem entity) throws SQLException{
		update.setInt(1, entity.getOrderId());
		update.setString(2, entity.getItem());
		update.setString(3, entity.getDescription());
		update.setDouble(4, entity.getPrice());
		update.setInt(5, entity.getId());
	}
	
	protected void setUpInsertQuery (OrderItem entity) throws SQLException {
		insert.setInt(1, entity.getOrderId());
		insert.setString(2, entity.getItem());
		insert.setString(3, entity.getDescription());
		insert.setDouble(4, entity.getPrice());
	}
	
	private String selectFromOrderItem(int id) {
		return "SELECT * FROM OrderItem WERE id=" + id;
	}
	
	public List<OrderItem> withAddress(String street) throws SQLException {
		this.selectAddressIdWithStreetSql += street;
		this.selectAddressIdWithStreetStmt = connection.prepareStatement(selectAddressIdWithStreetSql);
		this.selectAddressIdWithStreetResult = selectAddressIdWithStreetStmt.executeQuery();
		this.addressIdWithStreet = selectAddressIdWithStreetResult.getInt("id");
		this.selectOrderIdWithAddressIdSql += addressIdWithStreet;
		this.selectOrderIdWithAddressIdStmt = connection.prepareStatement(selectOrderIdWithAddressIdSql);
		this.selectOrderIdWithAddressIdResult = selectOrderIdWithAddressIdStmt.executeQuery();
		this.orderIdWithAddressId = selectOrderIdWithAddressIdResult.getInt("id");
		this.selectItemsWithOrderIdStmt = connection.prepareStatement(selectFromOrderItem(orderIdWithAddressId));
		this.selectItemsWithOrderIdResult = selectItemsWithOrderIdStmt.executeQuery();
		List<OrderItem> listOrderItem = new LinkedList<OrderItem>();
		while (selectItemsWithOrderIdResult.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setId(selectItemsWithOrderIdResult.getInt("id"));
			orderItem.setOrderId(selectItemsWithOrderIdResult.getInt("Order_id"));
			orderItem.setItem(selectItemsWithOrderIdResult.getString("item"));
			orderItem.setDescription(selectItemsWithOrderIdResult.getString("description"));
			orderItem.setPrice(selectItemsWithOrderIdResult.getDouble("price"));
			listOrderItem.add(orderItem);
		}
		return listOrderItem;
	}
	
	public List<OrderItem> withClient(String name) throws SQLException {
		this.selectClientIdWithNameSql += name;
		this.selectClientIdWithNameStmt = connection.prepareStatement(selectClientIdWithNameSql);
		this.selectClientIdWithNameResult = selectClientIdWithNameStmt.executeQuery();
		this.clientIdWithName = selectClientIdWithNameResult.getInt("id");
		this.selectOrderIdWithClientIdSql += clientIdWithName;
		this.selectOrderIdWithClientIdStmt = connection.prepareStatement(selectOrderIdWithClientIdSql);
		this.selectOrderIdWithClientIdResult = selectOrderIdWithClientIdStmt.executeQuery();
		this.orderIdWithClientId = selectOrderIdWithClientIdResult.getInt("id");
		this.selectOrderItemWithOrderIdStmt = connection.prepareStatement(selectFromOrderItem(orderIdWithClientId));
		this.selectOrderItemWithOrderIdResult = selectOrderItemWithOrderIdStmt.executeQuery();
		List<OrderItem> listOrderItem = new LinkedList<OrderItem>();
		while (selectOrderItemWithOrderIdResult.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setId(selectOrderItemWithOrderIdResult.getInt("id"));
			orderItem.setOrderId(selectOrderItemWithOrderIdResult.getInt("Order_id"));
			orderItem.setItem(selectOrderItemWithOrderIdResult.getString("item"));
			orderItem.setDescription(selectOrderItemWithOrderIdResult.getString("description"));
			orderItem.setPrice(selectOrderItemWithOrderIdResult.getDouble("price"));
			listOrderItem.add(orderItem);
		}
		return listOrderItem;
	}
	
	
	public List<OrderItem> withOrder(int order) throws SQLException {
		this.selectOrderItemWithOrderIdSql += order;
		this.selectOrderItemWithOrderIdStmt2 = connection.prepareStatement(selectOrderItemWithOrderIdSql);
		this.selectOrderItemWithOrderIdResult2 = selectOrderItemWithOrderIdStmt2.executeQuery();
		List<OrderItem> listOrderItem = new LinkedList<OrderItem>();
		while (selectOrderItemWithOrderIdResult2.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setId(selectOrderItemWithOrderIdResult2.getInt("id"));
			orderItem.setOrderId(selectOrderItemWithOrderIdResult2.getInt("Order_id"));
			orderItem.setItem(selectOrderItemWithOrderIdResult2.getString("item"));
			orderItem.setDescription(selectOrderItemWithOrderIdResult2.getString("description"));
			orderItem.setPrice(selectOrderItemWithOrderIdResult2.getDouble("price"));
			listOrderItem.add(orderItem);
		}
		return listOrderItem;
	}

}
