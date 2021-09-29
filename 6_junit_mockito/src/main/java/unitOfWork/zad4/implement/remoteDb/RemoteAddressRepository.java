package unitOfWork.zad4.implement.remoteDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import unitOfWork.zad4.implement.entityBuilder.IEntityBuilder;
import unitOfWork.zad4.repositories.IAddressRepository;
import unitOfWork.zad4.unitOfWork.IUnitOfWork;

public class RemoteAddressRepository extends Repository<Address> implements IAddressRepository{

	private final String selectAddressIdWithOrderIdSql = "SELECT DeliveryAddress_id FROM Orders WHERE id=?";
	private String selectClientIdWithNameSql = "SELECT id FROM ClientDetails WHERE name=?";
	private String selectAddressIdWithClientIdSql = "SELECT DeliveryAddress_id FROM Orders WHERE ClientDetails_id=";
	private String selectOrderIdWithItemNameSql = "SELECT Order_id FROM OrderItem WHERE item=?";
	private String selectAddressIdWithOrderIdSql2 = "SELECT DeliveryAddress_id FROM Orders WHERE id=?";
	
	private PreparedStatement selectAddressIdWithOrderIdStmt;
	private PreparedStatement selectAddressWithAddressIdStmt;
	private PreparedStatement selectClientIdWithNameStmt;
	private PreparedStatement selectAddressIdWithClientIdStmt;
	private PreparedStatement selectAddressWithAddressIdStmt2;
	private PreparedStatement selectOrderIdWithItemNameStmt;
	private PreparedStatement selectAddressIdWithOrderIdStmt2;
	private PreparedStatement selectAddressWithAddressIdStmt3;
	
	private ResultSet selectAddressIdWithOrderIdResult;
	private ResultSet selectAddressWithAddressIdResult;
	private ResultSet selectClientIdWithNameResult;
	private ResultSet selectAddressIdWithClientIdResult;
	private ResultSet selectAddressWithAddressIdResult2;
	private ResultSet selectOrderIdWithItemNameResult;
	private ResultSet selectAddressIdWithOrderIdResult2;
	private ResultSet selectAddressWithAddressIdResult3;
	
	private int addressIdWithOrderId;
	private int clientIdWithName;
	private int addressIdWithClientId;
	private int orderIdWithItemName;
	private int addressIdWithOrderId2;
	
	public RemoteAddressRepository (Connection connection, IEntityBuilder<Address> entityBuilder, IUnitOfWork uow) {
		super(connection, entityBuilder, uow);
	}
	
	protected String getTableName() {
		return "Address";
	}
	
	protected String getUpdateQuery() {
		return "UPDATE Address SET street=?, buildingNumber=?, flatNumber=?, postalCode=?, city=?, country=? WHERE id=?";
	}
	
	protected String getInsertQuery() {
		return "INSERT INTO Address (street, buildingNumber, flatNumber, postalCode, city, country) VALUES (?, ?, ?, ?, ?, ?)";
	}
	
	protected void setUpUpdateQuery(Address entity) throws SQLException{
		update.setString(1, entity.getStreet());
		update.setString(2, entity.getBuilgingNumber());
		update.setString(3, entity.getFlatNumber());
		update.setString(4, entity.getPostalCode());
		update.setString(5, entity.getCity());
		update.setString(6, entity.getCountry());
		update.setInt(7, entity.getId());
	}
	
	protected void setUpInsertQuery (Address entity) throws SQLException {
		insert.setString(1, entity.getStreet());
		insert.setString(2, entity.getBuilgingNumber());
		insert.setString(3, entity.getFlatNumber());
		insert.setString(4, entity.getPostalCode());
		insert.setString(5, entity.getCity());
		insert.setString(6, entity.getCountry());
	}
	
	private String selectFromAddress(int id) {
		return "SELECT * FROM Address WERE id=" + id;
	}
	
	public List<Address> withOrder(int order) throws SQLException {
		this.selectAddressIdWithOrderIdStmt = connection.prepareStatement(selectAddressIdWithOrderIdSql);
		this.selectAddressIdWithOrderIdStmt.setInt(1, order);
		this.selectAddressIdWithOrderIdResult = selectAddressIdWithOrderIdStmt.executeQuery();
		this.addressIdWithOrderId = selectAddressIdWithOrderIdResult.getInt("DeliveryAddress_id");
		this.selectAddressWithAddressIdStmt = connection.prepareStatement(selectFromAddress(addressIdWithOrderId));
		this.selectAddressWithAddressIdResult = selectAddressWithAddressIdStmt.executeQuery();
		List<Address> listAddress = new LinkedList<Address>();
		Address address = new Address();
		address.setId(selectAddressWithAddressIdResult.getInt("id"));
		address.setStreet(selectAddressWithAddressIdResult.getString("street"));
		address.setBuilgingNumber(selectAddressWithAddressIdResult.getString("buildingNumber"));
		address.setFlatNumber(selectAddressWithAddressIdResult.getString("flatNumber"));
		address.setPostalCode(selectAddressWithAddressIdResult.getString("postalCode"));
		address.setCity(selectAddressWithAddressIdResult.getString("city"));
		address.setCountry(selectAddressWithAddressIdResult.getString("country"));
		listAddress.add(address);
		return listAddress;
	}
	
	public List<Address> withClient(String name) throws SQLException {
		this.selectClientIdWithNameSql += name;
		this.selectClientIdWithNameStmt = connection.prepareStatement(selectClientIdWithNameSql);
		this.selectClientIdWithNameResult = selectClientIdWithNameStmt.executeQuery();
		this.clientIdWithName = selectClientIdWithNameResult.getInt("id");
		this.selectAddressIdWithClientIdSql += clientIdWithName;
		this.selectAddressIdWithClientIdStmt = connection.prepareStatement(selectAddressIdWithClientIdSql);
		this.selectAddressIdWithClientIdResult = selectAddressIdWithClientIdStmt.executeQuery();
		this.addressIdWithClientId = selectAddressIdWithClientIdResult.getInt("DeliveryAddress_id");
		this.selectAddressWithAddressIdStmt2 = connection.prepareStatement(selectFromAddress(addressIdWithClientId));
		this.selectAddressWithAddressIdResult2 = selectAddressWithAddressIdStmt2.executeQuery();
		List<Address> listAddress = new LinkedList<Address>();
		Address address = new Address();
		address.setId(selectAddressWithAddressIdResult2.getInt("id"));
		address.setStreet(selectAddressWithAddressIdResult2.getString("street"));
		address.setBuilgingNumber(selectAddressWithAddressIdResult2.getString("buildingNumber"));
		address.setFlatNumber(selectAddressWithAddressIdResult2.getString("flatNumber"));
		address.setPostalCode(selectAddressWithAddressIdResult2.getString("postalCode"));
		address.setCity(selectAddressWithAddressIdResult2.getString("city"));
		address.setCountry(selectAddressWithAddressIdResult2.getString("country"));
		listAddress.add(address);
		return listAddress;
	}
	
	
	public List<Address> withItem(String item) throws SQLException {
		this.selectOrderIdWithItemNameSql += item;
		this.selectOrderIdWithItemNameStmt = connection.prepareStatement(selectOrderIdWithItemNameSql);
		this.selectOrderIdWithItemNameResult = selectOrderIdWithItemNameStmt.executeQuery();
		this.orderIdWithItemName = selectOrderIdWithItemNameResult.getInt("Order_id");
		this.selectAddressIdWithOrderIdSql2 += orderIdWithItemName;
		this.selectAddressIdWithOrderIdStmt2 = connection.prepareStatement(selectAddressIdWithOrderIdSql2);
		this.selectAddressIdWithOrderIdResult2 = selectAddressIdWithOrderIdStmt2.executeQuery();
		this.addressIdWithOrderId2 = selectAddressIdWithOrderIdResult2.getInt("DeliveryAddress_id");
		this.selectAddressWithAddressIdStmt3 = connection.prepareStatement(selectFromAddress(addressIdWithOrderId2));
		this.selectAddressWithAddressIdResult3 = selectAddressWithAddressIdStmt3.executeQuery();
		List<Address> listAddress = new LinkedList<Address>();
		Address address = new Address();
		address.setId(selectAddressWithAddressIdResult3.getInt("id"));
		address.setStreet(selectAddressWithAddressIdResult3.getString("street"));
		address.setBuilgingNumber(selectAddressWithAddressIdResult3.getString("buildingNumber"));
		address.setFlatNumber(selectAddressWithAddressIdResult3.getString("flatNumber"));
		address.setPostalCode(selectAddressWithAddressIdResult3.getString("postalCode"));
		address.setCity(selectAddressWithAddressIdResult3.getString("city"));
		address.setCountry(selectAddressWithAddressIdResult3.getString("country"));
		listAddress.add(address);
		return listAddress;
	}
}
