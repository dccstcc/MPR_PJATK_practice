package JDBC.zad2;

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
import JDBC.zad2.service.ReadFromDatabase;

public class App 
{
    public static void main( String[] args )
    {
    	
    	Connection connection = null;
    	
    	ConnectWithDatabase connectWithDatabase = new ConnectWithDatabase();
    	connection = connectWithDatabase.getConnection();
    	
    	CreateTables createTables = new CreateTables(connection);
    	createTables.createTableOrders().createTableClientDetails().createTableAddress().createTableOrderItem();
    	
    	InsertIntoTables insertIntoTables = new InsertIntoTables(connection);

    	
    	ClientDetails clientDetails_0 = new ClientDetails(0, "Jan", "Kowalski", "JanKo");
    	ClientDetails clientDetails_1 = new ClientDetails(1, "Adam", "Nowak", "AdaNo");
    	ClientDetails clientDetails_2 = new ClientDetails(2, "Ula", "Jedrzejak", "UlaJed");
    	insertIntoTables.InsertIntoClientDetails(clientDetails_0).InsertIntoClientDetails(clientDetails_1).InsertIntoClientDetails(clientDetails_2);
 
    	Address address_0 = new Address(0, "ul. Przemyslowa" , "20 C", "3", "22-100", "Chelm", "Polska");
    	Address address_1 = new Address(1, "ul. Chabrowa" , "3", "17", "81-595", "Gdynia", "Polska");
    	Address address_2 = new Address(2, "ul. Jesionowa" , "45", "28", "22-400", "Zamosc", "Polska");
    	insertIntoTables.InsertIntoAddress(address_0).InsertIntoAddress(address_1).InsertIntoAddress(address_2);

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
    	
    	insertIntoTables.InsertIntoOrderItem(orderItem_0, orders_1).InsertIntoOrderItem(orderItem_1, orders_1).InsertIntoOrderItem(orderItem_2, orders_1);
    	insertIntoTables.InsertIntoOrderItem(orderItem_3, orders_0).InsertIntoOrderItem(orderItem_4, orders_0).InsertIntoOrderItem(orderItem_5, orders_0);
    	insertIntoTables.InsertIntoOrderItem(orderItem_6, orders_2).InsertIntoOrderItem(orderItem_7, orders_2).InsertIntoOrderItem(orderItem_8, orders_2).InsertIntoOrderItem(orderItem_9, orders_2);

    	insertIntoTables.InsertIntoOrders(orders_0).InsertIntoOrders(orders_1).InsertIntoOrders(orders_2);


    	ReadFromDatabase readFromDatabase = new ReadFromDatabase(connection);
    	readFromDatabase.show();
    	
    	

    }
}
