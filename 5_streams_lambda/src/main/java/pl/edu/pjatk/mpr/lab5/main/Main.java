package pl.edu.pjatk.mpr.lab5.main;

import java.util.Arrays;
import java.util.List;

import pl.edu.pjatk.mpr.lab5.model.Address;
import pl.edu.pjatk.mpr.lab5.model.ClientDetails;
import pl.edu.pjatk.mpr.lab5.model.Order;
import pl.edu.pjatk.mpr.lab5.model.OrderItem;
import pl.edu.pjatk.mpr.lab5.service.OrdersService;

public class Main {

	public static void main(String[] args) {
		ClientDetails client1 = new ClientDetails(0, "JanKo", "Jan", "Kowalski", 24);
		ClientDetails client2 = new ClientDetails(1, "AdNo", "Adam", "Nowak", 14);
		ClientDetails client3 = new ClientDetails(2, "PiJed", "Piotr", "Jedlinski", 16);
		ClientDetails client4 = new ClientDetails(3, "WoKru", "Wojciech", "Krol", 81);
		ClientDetails client5 = new ClientDetails(4, "izaWo", "Izabela", "Wojnarska", 24);
		ClientDetails client6 = new ClientDetails(5, "KaMu", "Karolina", "Mular", 42);
		ClientDetails client7 = new ClientDetails(6, "JanKo", "Stanislaw", "Goralski", 74);
		
		Address address1 = new Address(0, "Jaskowa", "24", "12", "22-100", "Chelm", "Polska");
		Address address2 = new Address(1, "Piaskowa", "2", "3", "22-400", "Zamosc", "Polska");
		Address address3 = new Address(2, "Jasminowa", "54", "4", "22-100", "Chelm", "Polska");
		Address address4 = new Address(3, "Piaskowa", "12", "24", "18-43", "Wojcieszyce", "Polska");
		Address address5 = new Address(4, "Rozowa", "18", "54", "22-100", "Chelm", "Polska");
		Address address6 = new Address(5, "Generalska", "13 D", "4", "00-343", "Warszawa", "Polska");
		Address address7 = new Address(6, "Chabrowa", "78", "45", "81-595", "Gdynia", "Polska");

		OrderItem orderItem1 = new OrderItem(0, "koszula", "odziez meska", 40.00);
		OrderItem orderItem2 = new OrderItem(1, "linijka", "akcesoria biurowe", 5);
		OrderItem orderItem3 = new OrderItem(2, "dlugopis", "akcesoria biurowe", 74.99);
		OrderItem orderItem4 = new OrderItem(3, "kubek", "artykuly wypozazenia domowego", 20.00);
		OrderItem orderItem5 = new OrderItem(4, "telewizor", "sprzet elektroniczny", 1789.80);
		OrderItem orderItem6 = new OrderItem(5, "komputer", "sprzet elektroniczny", 3000.00);
		OrderItem orderItem7 = new OrderItem(6, "wiertarka", "elektronarzedzia budowlane", 220.20);
		OrderItem orderItem8 = new OrderItem(7, "gwozdzie", "artykuly budowlane", 12.54);
		OrderItem orderItem9 = new OrderItem(8, "samochod", "motoryzacja", 80370.00);
		OrderItem orderItem10 = new OrderItem(9, "rower", "artukuly sportowe", 980.00);
		OrderItem orderItem11 = new OrderItem(10, "buty biegowe", "artykuly sportowe", 300.00);
		OrderItem orderItem12 = new OrderItem(11, "myszka komputerowa", "sprzet elektroniczny", 100.00);
		
		List<OrderItem> itemsList1 = Arrays.asList(orderItem1,orderItem2,orderItem3);
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4,orderItem5,orderItem6);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10,orderItem11,orderItem12);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "A Pierwsze zamowienie");
		Order order2 = new Order(1, client2, address2, itemsList2, "Drugie zamowienie");
		Order order3 = new Order(2, client3, address3, itemsList3, "A trzecie zamowienie");
		Order order4 = new Order(3, client4, address4, itemsList4, "Czwarte zamowienie");
		Order order5 = new Order(4, client5, address5, itemsList5, "A Piate zamowienie");
		Order order6 = new Order(5, client6, address6, itemsList6, "Szoste zamowienie");
		Order order7 = new Order(6, client7, address7, itemsList7, "A Siodme zamowienie");

		List<Order> orders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		System.out.println("liczba zamowien ponad 5 sztuk przedmiotow: " + OrdersService.findOrdersWhichHaveMoreThan5OrderItems(orders).size());
		System.out.println();
		
		System.out.println("Najstarszy klient: " + OrdersService.findOldestClientAmongThoseWhoMadeOrders(orders).getName());
		System.out.println();

		System.out.println("Zamówienie o najdluzszym komentarzu: " + OrdersService.findOrderWithLongestComments(orders).getComments());
		System.out.println();
		
		System.out.println(OrdersService.getNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld(orders));
		System.out.println();

		System.out.println("posortowana lista pozycji przedmiotow z komentarzem zamowienia na litere \"A\": ");
		OrdersService.getSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA(orders).forEach(System.out::println);
		System.out.println();
		
		System.out.println("loginy klientow, ktorych imie zaczyna sie na S: ");
		OrdersService.printCapitalizedClientsLoginsWhoHasNameStartingWithS(orders);
		System.out.println();
		
		System.out.println("Zamowienia z mapowania klient - zamowienie: ");
		OrdersService.groupOrdersByClient(orders).values().stream().findFirst().get().forEach((m) -> System.out.println(m.getClientDetails().getName()));
		System.out.println();
		
		System.out.println("Lista osob w wieku ponizej 18 roku zycia: ");
		OrdersService.partitionClientsByUnderAndOver18(orders).get(false).forEach( (i) -> System.out.println(i.getName() + "  " + i.getSurname() ));
	}

}
