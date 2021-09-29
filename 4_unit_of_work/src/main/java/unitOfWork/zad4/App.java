package unitOfWork.zad4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.service.ConnectWithDatabase;
import JDBC.zad2.service.CreateTables;
import JDBC.zad2.service.InsertIntoTables;
import unitOfWork.zad4.implement.entityBuilder.IEntityBuilder;
import unitOfWork.zad4.implement.entityBuilder.ItemBuilder;
import unitOfWork.zad4.implement.localDb.LocalDb;
import unitOfWork.zad4.implement.localDb.LocalItemRepository;
import unitOfWork.zad4.implement.remoteDb.RepositoryCatalogProvider;
import unitOfWork.zad4.repositories.IItemRepository;
import unitOfWork.zad4.repositories.IRepositoryCatalog; 

public class App 
{
    public static void main( String[] args )
    {
    	    	
    	Connection connection; 
    	ConnectWithDatabase connect = new ConnectWithDatabase();
    	connection = connect.getConnection();
    	
    	CreateTables createTables = new CreateTables(connection);
    	createTables.createTableClientDetails().createTableAddress().createTableOrderItem().createTableOrders();
    	
    	IRepositoryCatalog repositoryCatalog = RepositoryCatalogProvider.catalog();
    
    	ClientDetails client = new ClientDetails();
    	client.setId(1);
    	client.setName("Piotr");
    	client.setSurname("Kowalski");
    	
    	repositoryCatalog.getClients().save(client);
    	repositoryCatalog.commit();
    	
    	repositoryCatalog.getClients().save(client);
    	repositoryCatalog.getClients().save(client);
    	repositoryCatalog.getClients().save(client);
    	repositoryCatalog.rollback();
    	
    	ClientDetails client2 = new ClientDetails();
    	client2.setId(2);
    	client2.setName("Adam");
    	client2.setSurname("Nowak");
    	
    	repositoryCatalog.getClients().save(client2);
    	repositoryCatalog.commit();
    	
    	List <ClientDetails> clients = repositoryCatalog.getClients().getAll();

    	for(ClientDetails cd : clients) {
    		System.out.println("name: " + cd.getName() +", surname: " + cd.getSurname());
    	}
    	
    	System.out.println ("");
    	
    	OrderItem orderItem_0 = new OrderItem(0, 0, "Lyzwy", "Zimowe akcesoria sportowe", 199.99);
    	OrderItem orderItem_1 = new OrderItem(1, 0, "Sanki", "Zimowe akcesoria sportowe", 159.20);
    	OrderItem orderItem_2 = new OrderItem(2, 0, "Koszula", "Odziez", 80.00);
    	OrderItem orderItem_3 = new OrderItem(3, 1, "Spodnie", "Odziez", 115.80);
    	OrderItem orderItem_4 = new OrderItem(4, 1, "Proszek do prania", "Chemia gospodarcza", 20.20);
    	OrderItem orderItem_5 = new OrderItem(5, 1, "Sephora perfumy", "Kosmetyki", 70.99);
    	OrderItem orderItem_6 = new OrderItem(6, 0, "Monitor LG", "Elektronika uzytkowa", 1124.30);
    	OrderItem orderItem_7 = new OrderItem(7, 0, "Smartphone Sony Xperia", "Elektronika uzytkowa", 1579.99);
    	OrderItem orderItem_8 = new OrderItem(8, 1, "klawiatura Dell", "Elektronika uzytkowa", 100.00);
    	OrderItem orderItem_9 = new OrderItem(9, 1, "Organizer na biurko", "Artykuly biurowe", 31.49);
    	
    	repositoryCatalog.getItems().save(orderItem_0);
    	repositoryCatalog.getItems().save(orderItem_1);
    	repositoryCatalog.getItems().save(orderItem_2);
    	repositoryCatalog.getItems().save(orderItem_3);
    	repositoryCatalog.getItems().save(orderItem_4);
    	repositoryCatalog.getItems().save(orderItem_5);
    	repositoryCatalog.getItems().save(orderItem_6);
    	repositoryCatalog.getItems().save(orderItem_7);
    	repositoryCatalog.getItems().save(orderItem_8);
    	repositoryCatalog.getItems().save(orderItem_9);
    	repositoryCatalog.commit();
    
    	String sql = "SELECT * FROM OrderItem";
    	List<OrderItem> items = new LinkedList<OrderItem>();
    	try {
    	PreparedStatement ps = connection.prepareStatement(sql);
    	ResultSet rs = ps.executeQuery();
    	IEntityBuilder<OrderItem> builder = new ItemBuilder();
    	items = builder.build(rs);
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	LocalDb db = new LocalDb();
    	db.setItems(items);
    	IItemRepository repo = new LocalItemRepository(db);
    	List<OrderItem> list = repo.getAll();
    	System.out.println(list.get(0).getDescription());
    	System.out.println("");
    	
    	OrderItem update = new OrderItem(0, 0, "update", "update", 199.99);
    	repositoryCatalog.getItems().update(update);
    	repositoryCatalog.commit();
    	
    	System.out.println(repositoryCatalog.getItems().getAll().get(1).getDescription());

    	
    }
}
