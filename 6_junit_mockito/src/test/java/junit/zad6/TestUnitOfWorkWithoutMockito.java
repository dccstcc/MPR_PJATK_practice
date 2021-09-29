package junit.zad6;

/**
 *Tests from this class should be run with running Hsql database.
 * 
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import JDBC.zad2.service.ConnectWithDatabase;
import JDBC.zad2.service.CreateTables;
import JDBC.zad2.service.InsertIntoTables;
import unitOfWork.zad4.implement.entityBuilder.ClientBuilder;
import unitOfWork.zad4.implement.remoteDb.RemoteAddressRepository;
import unitOfWork.zad4.implement.remoteDb.RemoteClientRepository;
import unitOfWork.zad4.unitOfWork.UnitOfWork;

public class TestUnitOfWorkWithoutMockito {
	
	Connection connection;
	ClientDetails client;
	Address address;
	RemoteClientRepository remoteClientRepository;
	RemoteAddressRepository remoteAddressRepository;
	UnitOfWork uow;
	ResultSet rs;
	
	@BeforeClass
	public static void createTables() {
		ConnectWithDatabase connector = new ConnectWithDatabase();
		Connection connectionBefore = connector.getConnection();
		
		CreateTables createTables = new CreateTables(connectionBefore);
		createTables.createTableClientDetails().createTableAddress().createTableOrderItem().createTableOrders();
		
		InsertIntoTables insertIntoTables = new InsertIntoTables(connectionBefore);
		
		ClientDetails clientDetails_0 = new ClientDetails(0, "Jan", "Kowalski", "JanKo");
    	ClientDetails clientDetails_1 = new ClientDetails(1, "Adam", "Nowak", "AdaNo");
    	ClientDetails clientDetails_2 = new ClientDetails(2, "Ula", "Jedrzejak", "UlaJed");
    	insertIntoTables.InsertIntoClientDetails(clientDetails_0).InsertIntoClientDetails(clientDetails_1).InsertIntoClientDetails(clientDetails_2);
 
    	Address address_0 = new Address(0, "ul. Przemyslowa" , "20 C", "3", "22-100", "Chelm", "Polska");
    	Address address_1 = new Address(1, "ul. Chabrowa" , "3", "17", "81-595", "Gdynia", "Polska");
    	Address address_2 = new Address(2, "ul. Jesionowa" , "45", "28", "22-400", "Zamosc", "Polska");
    	insertIntoTables.InsertIntoAddress(address_0).InsertIntoAddress(address_1).InsertIntoAddress(address_2);

    	OrderItem orderItem_0 = new OrderItem(0, 1,"Lyzwy", "Zimowe akcesoria sportowe", 199.99);
    	OrderItem orderItem_1 = new OrderItem(1, 1,"Sanki", "Zimowe akcesoria sportowe", 159.20);
    	OrderItem orderItem_2 = new OrderItem(2, 1,"Koszula", "Odziez", 80.00);
    	OrderItem orderItem_3 = new OrderItem(3, 0,"Spodnie", "Odziez", 115.80);
    	OrderItem orderItem_4 = new OrderItem(4, 0,"Proszek do prania", "Chemia gospodarcza", 20.20);
    	OrderItem orderItem_5 = new OrderItem(5, 0,"Sephora perfumy", "Kosmetyki", 70.99);
    	OrderItem orderItem_6 = new OrderItem(6, 2,"Monitor LG", "Elektronika uzytkowa", 1124.30);
    	OrderItem orderItem_7 = new OrderItem(7, 2,"Smartphone Sony Xperia", "Elektronika uzytkowa", 1579.99);
    	OrderItem orderItem_8 = new OrderItem(8, 2,"klawiatura Dell", "Elektronika uzytkowa", 100.00);
    	OrderItem orderItem_9 = new OrderItem(9, 2,"Organizer na biurko", "Artykuly biurowe", 31.49);
    	
    	List<OrderItem> orderedItems_0 = new ArrayList<OrderItem>();
    	List<OrderItem> orderedItems_1 = new ArrayList<OrderItem>();
    	List<OrderItem> orderedItems_2 = new ArrayList<OrderItem>();
    	orderedItems_0.add(orderItem_0);
    	orderedItems_0.add(orderItem_1);
    	orderedItems_0.add(orderItem_2);
    	orderedItems_1.add(orderItem_3);
    	orderedItems_1.add(orderItem_4);
    	orderedItems_1.add(orderItem_5);
    	orderedItems_2.add(orderItem_6);
    	orderedItems_2.add(orderItem_7);
    	orderedItems_2.add(orderItem_8);
    	orderedItems_2.add(orderItem_9);
    	Orders orders_0 = new Orders(0, clientDetails_0, address_2, orderedItems_1);
    	Orders orders_1 = new Orders(1, clientDetails_1, address_0, orderedItems_0);
    	Orders orders_2 = new Orders(2, clientDetails_2, address_1, orderedItems_2);
    	
    	insertIntoTables.InsertIntoOrderItem(orderItem_0, orders_1).InsertIntoOrderItem(orderItem_1, orders_1).InsertIntoOrderItem(orderItem_2, orders_1);
    	insertIntoTables.InsertIntoOrderItem(orderItem_3, orders_0).InsertIntoOrderItem(orderItem_4, orders_0).InsertIntoOrderItem(orderItem_5, orders_0);
    	insertIntoTables.InsertIntoOrderItem(orderItem_6, orders_2).InsertIntoOrderItem(orderItem_7, orders_2).InsertIntoOrderItem(orderItem_8, orders_2).InsertIntoOrderItem(orderItem_9, orders_2);

    	insertIntoTables.InsertIntoOrders(orders_0).InsertIntoOrders(orders_1).InsertIntoOrders(orders_2);
		
		
		
	}
	
	@Before
	public void setUp() {
		ConnectWithDatabase connector = new ConnectWithDatabase();
		connection = connector.getConnection();
		
		uow = new UnitOfWork(connection);
		
		ClientBuilder clientBuilder = new ClientBuilder();
		
		remoteClientRepository = new RemoteClientRepository(connection, clientBuilder, uow);
	}
	
	@After
	public void reset() throws SQLException{
		connection.close();
	}
	
	@Test
	public void testUnitOfWorkCommit_emptyCommitWithoutMarkAs_noErrors() {
		uow.commit();
	}
	
	@Test
	public void testUnitOfWorkCommit_markAsUpdateOnNotExistingObjectAndAfterMarkAsNewForNewObject_noErrors() {
		ClientDetails client = new ClientDetails();
		client.setName("testing");
		uow.markAsDirty(client, remoteClientRepository);
		uow.markAsNew(client, remoteClientRepository);
		uow.commit();
	}
	
	@Test
	public void testUnitOfWorkCommit_addTwoNewClients_noErrors() {
		ClientDetails client1 = new ClientDetails();
		client1.setName("testing1");
		ClientDetails client2 = new ClientDetails();
		client2.setName("testing2");
		uow.markAsNew(client1, remoteClientRepository);
		uow.markAsNew(client2, remoteClientRepository);
		uow.commit();
	}
	
	@Test
	public void testUnitOfWorkRollback_rollbackWithoutMarkAs_noErrors() {
		uow.rollback();
	}
	
	@Test
	public void testUnitOfWorkRollback_rollbackIncorrectMarkAsAndCommitCorrectMarkAs_noErrors() {
		ClientDetails client3 = new ClientDetails();
		client3.setName("testing3");
		ClientDetails client4 = new ClientDetails();
		client4.setName("testing4");
		uow.markAsDirty(client3, remoteClientRepository);
		uow.rollback();
		uow.markAsNew(client4, remoteClientRepository);
		uow.commit();
	}
	
	@Test
	public void testUnitOfWorkRollback_rollbackTwoIncorrectMarkAsAndCommitEmpty_noErrors() {
		ClientDetails client5 = new ClientDetails();
		client5.setName("testing5");
		ClientDetails client6 = new ClientDetails();
		client6.setName("testing6");
		uow.markAsDirty(client5, remoteClientRepository);
		uow.markAsNew(client5, remoteClientRepository);
		uow.rollback();
		uow.commit();
	}
	
	@Test
	public void testUnitOfWorkMarkAsNewMarkAsChanged_MarkAsNewAndChangedFirstRollbackMarkAsNewAndChangedAfterCommit() {
		ClientDetails client7 = new ClientDetails();
		client7.setName("testing7");
		ClientDetails client8 = new ClientDetails();
		client8.setName("testing8");
		uow.markAsNew(client7, remoteClientRepository);
		uow.markAsDirty(client8, remoteClientRepository);
		uow.rollback();
		uow.markAsNew(client8, remoteClientRepository);
		uow.markAsDirty(client7, remoteClientRepository);
		uow.commit();
	}
	
	@Test
	public void testUnitOfWorkMarkAsChangedMarkAsDeleted_MarkAsChangedFirstAndMarkAsDeletedRollbackMarkAsChangedAndDeletedAfterCommit() {
		ClientDetails client9 = new ClientDetails();
		client9.setName("testing9");
		ClientDetails client10 = new ClientDetails();
		client10.setName("testing10");
		uow.markAsDirty(client9, remoteClientRepository);
		uow.markAsDeleted(client10, remoteClientRepository);
		uow.rollback();
		uow.markAsDirty(client10, remoteClientRepository);
		uow.markAsDeleted(client9, remoteClientRepository);
		uow.commit();
	}
	
}
