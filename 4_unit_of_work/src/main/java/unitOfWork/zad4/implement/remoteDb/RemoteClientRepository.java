package unitOfWork.zad4.implement.remoteDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.ClientDetails;
import unitOfWork.zad4.implement.entityBuilder.IEntityBuilder;
import unitOfWork.zad4.repositories.IClientRepository;
import unitOfWork.zad4.unitOfWork.IUnitOfWork;

public class RemoteClientRepository extends Repository<ClientDetails> implements IClientRepository{
	
	
	private final String selectClientIdSql = "SELECT ClientDetails_id FROM Orders WHERE id=?";
	private final String selectAddressIdSql = "SELECT id FROM Address WHERE street=?";
	private String selectClientIdWithAddressSql = "SELECT ClientDetails_id FROM Orders WHERE DeliveryAddress_id=";
	private final String selectOrderIdWithItemSql = "SELECT Order_id FROM OrderItem WHERE item=?";
	private String selectClientIdIdWithOrderIdSql = "SELECT ClientDetails_id FROM Orders WHERE id=?";
	
	private PreparedStatement selectClientIdStmt;
	private PreparedStatement selectClientStmt;
	private PreparedStatement selectAddressIdStmt;
	private PreparedStatement selectClientIdWithAddressStmt;
	private PreparedStatement selectClientWithAddressStmt;
	private PreparedStatement selectOrderIdWithItemStmt;
	private PreparedStatement selectClientIdIdWithOrderIdStmt;
	private PreparedStatement selectClientWithClientDetailsIdStmt;
	
	private ResultSet selectClientIdResult;
	private ResultSet selectClientResult;
	private ResultSet selectAddressIdResult;
	private ResultSet selectClientIdWithAddressResult;
	private ResultSet selectClientWithAddressResult;
	private ResultSet selectOrderIdWithItemResult;
	private ResultSet selectClientIdIdWithOrderIdResult;
	private ResultSet selectClientWithClientDetailsIdResult;
	
	private int clientId;
	private int addressIdWithStreet;
	private int clientIdWithAddress;
	private int orderIdWithItem;
	private int clientIdWithOrderId;
	
	public RemoteClientRepository (Connection connection, IEntityBuilder<ClientDetails> entityBuilder, IUnitOfWork uow) {
		super(connection, entityBuilder, uow);
	}
	
	protected String getTableName() {
		return "ClientDetails";
	}
	
	protected String getUpdateQuery() {
		return "UPDATE ClientDetails SET name=?, surname=?, login=? WHERE id=?";
	}
	
	protected String getInsertQuery() {
		return "INSERT INTO ClientDetails (name, surname, login) VALUES (?, ?, ?)";
	}
	
	protected void setUpUpdateQuery(ClientDetails entity) throws SQLException{
		update.setString(1, entity.getName());
		update.setString(2, entity.getSurname());
		update.setString(3, entity.getLogin());
		update.setInt(4, entity.getId());
	}
	
	protected void setUpInsertQuery (ClientDetails entity) throws SQLException {
		insert.setString(1, entity.getName());
		insert.setString(2, entity.getSurname());
		insert.setString(3, entity.getLogin());
	}
	
	private String selectFromClient(int id) {
		return "SELECT * FROM ClientDetils WERE id=" + id;
	}
	
	public List<ClientDetails> withOrder(int order) throws SQLException {
		this.selectClientIdStmt = connection.prepareStatement(selectClientIdSql);
		this.selectClientIdStmt.setInt(1, order);
		this.selectClientIdResult = selectClientIdStmt.executeQuery();
		this.clientId = selectClientIdResult.getInt("ClientDetails_id");
		this.selectClientStmt = connection.prepareStatement(selectFromClient(clientId));
		this.selectClientResult = selectClientStmt.executeQuery();
		List<ClientDetails> listClientDetails = new LinkedList<ClientDetails>();
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setId(selectClientResult.getInt("id"));
		clientDetails.setName(selectClientResult.getString("name"));
		clientDetails.setSurname(selectClientResult.getString("surname"));
		clientDetails.setLogin(selectClientResult.getString("login"));
		listClientDetails.add(clientDetails);
		return listClientDetails;
	}
	
	public List<ClientDetails> withAddress(String street) throws SQLException {
		this.selectAddressIdStmt = connection.prepareStatement(selectAddressIdSql);
		this.selectAddressIdStmt.setString(1, street);
		this.selectAddressIdResult = selectAddressIdStmt.executeQuery();
		this.addressIdWithStreet = selectAddressIdResult.getInt("id");
		this.selectClientIdWithAddressSql += this.addressIdWithStreet;
		this.selectClientIdWithAddressStmt = connection.prepareStatement(selectClientIdWithAddressSql);
		this.selectClientIdWithAddressResult = selectClientIdWithAddressStmt.executeQuery();
		this.clientIdWithAddress = selectClientIdWithAddressResult.getInt("ClientDetails_id");
		this.selectClientWithAddressStmt = connection.prepareStatement(selectFromClient(clientIdWithAddress));
		this.selectClientWithAddressResult = this.selectClientWithAddressStmt.executeQuery();
		List<ClientDetails> listClientDetails = new LinkedList<ClientDetails>();
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setId(selectClientWithAddressResult.getInt("id"));
		clientDetails.setName(selectClientWithAddressResult.getString("name"));
		clientDetails.setSurname(selectClientWithAddressResult.getString("surname"));
		clientDetails.setLogin(selectClientWithAddressResult.getString("login"));
		listClientDetails.add(clientDetails);
		return listClientDetails;
	}
	
	
	public List<ClientDetails> withItem(String item) throws SQLException {
		this.selectOrderIdWithItemStmt = connection.prepareStatement(selectOrderIdWithItemSql);
		this.selectOrderIdWithItemStmt.setString(1, item);
		this.selectOrderIdWithItemResult = selectOrderIdWithItemStmt.executeQuery();
		this.orderIdWithItem = selectOrderIdWithItemResult.getInt("Order_id");
		this.selectClientIdIdWithOrderIdSql += orderIdWithItem;
		this.selectClientIdIdWithOrderIdStmt = connection.prepareStatement(selectClientIdIdWithOrderIdSql);
		this.selectClientIdIdWithOrderIdResult = selectClientIdIdWithOrderIdStmt.executeQuery();
		this.clientIdWithOrderId = selectClientIdIdWithOrderIdResult.getInt("ClientDetails_id");
		this.selectClientWithClientDetailsIdStmt = connection.prepareStatement(selectFromClient(clientIdWithOrderId));
		this.selectClientWithClientDetailsIdResult = selectClientWithClientDetailsIdStmt.executeQuery();
		List<ClientDetails> listClientDetails = new LinkedList<ClientDetails>();
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setId(selectClientWithClientDetailsIdResult.getInt("id"));
		clientDetails.setName(selectClientWithClientDetailsIdResult.getString("name"));
		clientDetails.setSurname(selectClientWithClientDetailsIdResult.getString("surname"));
		clientDetails.setLogin(selectClientWithClientDetailsIdResult.getString("login"));
		listClientDetails.add(clientDetails);
		return listClientDetails;
	}

	
}
