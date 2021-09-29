package Repository.zad3;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import JDBC.zad2.service.ConnectWithDatabase;
import JDBC.zad2.service.CreateTables;
import JDBC.zad2.service.InsertIntoTables;
import Repository.zad3.catalogs.HsqlRepositoryCatalog;
import Repository.zad3.db.PagingInfo;
import Repository.zad3.db.RepositoryCatalog;
import Repository.zad3.repos.HsqlOrderRepository;

public class App 
{
    public static void main( String[] args )
    {
    	ConnectWithDatabase connectWithDatabase = new ConnectWithDatabase();
    	Connection connection = connectWithDatabase.getConnection();
    	RepositoryCatalog catalogOf = new HsqlRepositoryCatalog(connection);
    	
    	ClientDetails clientDetails_0 = new ClientDetails(0, "Jan", "Kowalski", "JanKo");
    	ClientDetails clientDetails_1 = new ClientDetails(1, "Adam", "Nowak", "AdaNo");
    	ClientDetails clientDetails_2 = new ClientDetails(2, "Ula", "Jedrzejak", "UlaJed");
    	
    	Address address_0 = new Address(0, "ul. Przemyslowa" , "20 C", "3", "22-100", "Chelm", "Polska");
    	Address address_1 = new Address(1, "ul. Chabrowa" , "3", "17", "81-595", "Gdynia", "Polska");
    	Address address_2 = new Address(2, "ul. Jesionowa" , "45", "28", "22-400", "Zamosc", "Polska");
    	
    	OrderItem orderItem_0 = new OrderItem(0, "Lyzwy", "Zimowe akcesoria sportowe", 199.99);
    	OrderItem orderItem_1 = new OrderItem(1, "Sanki", "Zimowe akcesoria sportowe", 159.20);
    	OrderItem orderItem_2 = new OrderItem(2, "Koszula", "Odziez", 80.00);
    	OrderItem orderItem_3 = new OrderItem(3, "Spodnie", "Odziez", 115.80);
    	OrderItem orderItem_4 = new OrderItem(4, "Proszek do prania", "Chemia gospodarcza", 20.20);
    	OrderItem orderItem_5 = new OrderItem(5, "Sephora perfumy", "Kosmetyki", 70.99);
    	OrderItem orderItem_6 = new OrderItem(6, "Monitor LG", "Elektronika uzytkowa", 1124.30);
    	OrderItem orderItem_7 = new OrderItem(7, "Smartphone Sony Xperia", "Elektronika uzytkowa", 1579.99);
    	OrderItem orderItem_8 = new OrderItem(8, "klawiatura Dell", "Elektronika uzytkowa", 100.00);
    	OrderItem orderItem_9 = new OrderItem(9, "Organizer na biurko", "Artykuly biurowe", 31.49);
    	
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
    	
    	CreateTables createTables = new CreateTables(connection);
		createTables.createTableClientDetails().createTableAddress().createTableOrderItem().createTableOrders();
    	
    	InsertIntoTables insertIntoTables = new InsertIntoTables(connection);
    	insertIntoTables.InsertIntoClientDetails(clientDetails_0);
    	insertIntoTables.InsertIntoClientDetails(clientDetails_1);
    	insertIntoTables.InsertIntoClientDetails(clientDetails_2);

    	insertIntoTables.InsertIntoAddress(address_0);
    	insertIntoTables.InsertIntoAddress(address_1);
    	insertIntoTables.InsertIntoAddress(address_2);
    	
    	PagingInfo pagingInfo = new PagingInfo();
    	pagingInfo.setCurrentPage(1);
    	pagingInfo.setSize(10);
    	
    	catalogOf.orders().add(orders_0, orderedItems_1);
    	catalogOf.orders().add(orders_1, orderedItems_0);
    	catalogOf.orders().add(orders_2, orderedItems_2);
    	
    	Orders orders_3 = catalogOf.orders().withId(0);

    	System.out.println("wynik dla: Orders orders_3 = catalogOf.orders().withId(0)");
    	System.out.println("login: " + orders_3.getClient().getLogin() + ", item: " + orders_3.getItems().get(1).getItem());
    	System.out.println("");
    	
    	Orders orders_test = new Orders();
    	ClientDetails clientDetails_test = new ClientDetails(2, "test", "test", "test");
    	Address address_test = new Address(2, "ul. test" , "test", "test", "22-test", "test", "test");
    	OrderItem orderItem_test1 = new OrderItem(15, "test test test", "test test", 1579.99);
    	OrderItem orderItem_test2 = new OrderItem(16, "test test", "test test", 100.00);
    	OrderItem orderItem_test3 = new OrderItem(17, "test na test", "test test", 31.49);
    	List<OrderItem> orderedItems_test = new ArrayList<OrderItem>();
    	orderedItems_test.add(orderItem_test1);
    	orderedItems_test.add(orderItem_test2);
    	orderedItems_test.add(orderItem_test3);
    	orders_test.setId(1);
    	orders_test.setClient(clientDetails_test);
    	orders_test.setDelivaryAddress(address_test);
    	orders_test.setItems(orderedItems_test);
    	
    	
    	catalogOf.orders().add(orders_test, orderedItems_test);
    	
    	catalogOf.orders().remove(orders_0);
    	
    	Orders orders_4 = catalogOf.orders().withId(2);
    	System.out.println("wynik dla: Orders orders_4 = catalogOf.orders().withId(2)");
    	System.out.println(orders_4.getId() + "     #     " + orders_4.getClient() + "     #     " + orders_4.getItems().get(0).getItem());
    	System.out.println("");
    	
    	OrderItem orderItem_modify1 = new OrderItem(5, "modify modify modify", "modify modify", 1579.99);
    	OrderItem orderItem_modify2 = new OrderItem(6, "modify modify", "modify modify", 100.00);
    	OrderItem orderItem_modify3 = new OrderItem(7, "modify modify modify", "modify modify", 31.49);
    	List<OrderItem> orderedItems_modify = new ArrayList<OrderItem>();
    	orderedItems_modify.add(orderItem_modify1);
    	orderedItems_modify.add(orderItem_modify2);
    	orderedItems_modify.add(orderItem_modify3);
    	
    	catalogOf.orders().modify(orders_0, orderedItems_modify);
    	
    	List<OrderItem> orderItem;
    	orderItem = catalogOf.orders().allOnPage(orders_0, pagingInfo);
    	
    	System.out.println("Wynik dla: orderItem = catalogOf.orders().allOnPage(orders_0, pagingInfo)");
    	for(OrderItem oi : orderItem) {
    		System.out.println(oi.getId() + "     #     " + oi.getItem() + "     #     " + oi.getDescription());
    	}
    	System.out.println("");
    	
    	List<ClientDetails> clientDetails;
    	clientDetails = catalogOf.orders().withItem("klawiatura Dell", pagingInfo);
    	
    	System.out.println("Wynik dla: clientDetails = catalogOf.orders().withItem(\"klawiatura Dell\", pagingInfo)");
    			for(ClientDetails cd : clientDetails) {
    	    		System.out.println(cd.getId() + "     #     " + cd.getName() + "     #     " + cd.getLogin());
    	    	}
    	System.out.println("");
    			
    	List<OrderItem> listOrderItem;
    	listOrderItem = catalogOf.orders().withClient("AdaNo", pagingInfo);
    	
    	System.out.println("wynik dla: listOrderItem = catalogOf.orders().withClient(\"AdaNo\", pagingInfo");
    	for(OrderItem oi : listOrderItem) {
    		System.out.println(oi.getId() + "     #     " + oi.getItem() + "     #     " + oi.getDescription());
    	}
    	System.out.println("");
    	System.out.println("");
    	System.out.println("");
    	
    	catalogOf.orders().viewOrderSummary(1);
    }
}
