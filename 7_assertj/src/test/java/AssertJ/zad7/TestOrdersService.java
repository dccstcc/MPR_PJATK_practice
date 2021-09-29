package AssertJ.zad7;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Maps;
import org.junit.Test;

import pl.edu.pjatk.mpr.lab5.model.Address;
import pl.edu.pjatk.mpr.lab5.model.ClientDetails;
import pl.edu.pjatk.mpr.lab5.model.Order;
import pl.edu.pjatk.mpr.lab5.model.OrderItem;
import pl.edu.pjatk.mpr.lab5.service.OrdersService;


public class TestOrdersService {

	@Test(expected = NullPointerException.class)
	public void testFindOrdersWhichHaveMoreThan5OrderItems_methodArgumentIsNullValue_throwsException() {
		assertThat(OrdersService.findOrdersWhichHaveMoreThan5OrderItems(null)).isNull();
		assertThatThrownBy(() -> { throw new NullPointerException("Nie mozna wykonac operacji na wartosci null"); })
							.isInstanceOf(NullPointerException.class)
							.hasMessageContaining("Nie mozna wykonac operacji na wartosci null");
							
	}
	
	@Test
	public void testFindOrdersWhichHaveMoreThan5OrderItems_methodArgumentIsEmptyList_assertThatIsEmpty() {
		List<Order> listOrders = new LinkedList<Order>();
		assertThat(OrdersService.findOrdersWhichHaveMoreThan5OrderItems(listOrders)).isNullOrEmpty();
		
	}
	
	@Test
	public void testFindOrdersWhichHaveMoreThan5OrderItems_methodReturnEmptyList_assertThatIsEmpty() {
		
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
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
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
		
		assertThat(OrdersService.findOrdersWhichHaveMoreThan5OrderItems(orders)).isNullOrEmpty();
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testFindOldestClientAmongThoseWhoMadeOrders_methodArgumentIsNullValue_throwsNullPointerException() {
		assertThat(OrdersService.findOldestClientAmongThoseWhoMadeOrders(null)).isNull();
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindOldestClientAmongThoseWhoMadeOrders_methodArgumentIsEmptyList_throwsNoSuchElementException() {
		List<Order> listOrders = new LinkedList<Order>();
		assertThat(OrdersService.findOldestClientAmongThoseWhoMadeOrders(listOrders)).isNull();
	}
	
	@Test
	public void testFindOldestClientAmongThoseWhoMadeOrders_methodArgumentIsListWithoutAgeValue_assertThatIsNull() {
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "JanKo", "Jan", "Kowalski", 0);
		ClientDetails client2 = new ClientDetails(1, "AdNo", "Adam", "Nowak", 0);
		ClientDetails client3 = new ClientDetails(2, "PiJed", "Piotr", "Jedlinski", 0);
		ClientDetails client4 = new ClientDetails(3, "WoKru", "Wojciech", "Krol", 0);
		ClientDetails client5 = new ClientDetails(4, "izaWo", "Izabela", "Wojnarska", 0);
		ClientDetails client6 = new ClientDetails(5, "KaMu", "Karolina", "Mular", 0);
		ClientDetails client7 = new ClientDetails(6, "JanKo", "Stanislaw", "Goralski", 0);
		
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
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
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

		 listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		assertThat(OrdersService.findOldestClientAmongThoseWhoMadeOrders(listOrders)).extracting("Age").contains(0);
	}
	
	@Test(expected = NullPointerException.class)
	public void testFindOrderWithLongestComments_methodArgumentIsNullValue_throwsNullPointerException() {
		assertThat(OrdersService.findOrderWithLongestComments(null)).isNull();
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindOrderWithLongestComments_methodArgumentIsEmptyList_methodReturnNullValue() {
		List<Order> listOrders = new LinkedList<Order>();
		assertThat(OrdersService.findOrderWithLongestComments(listOrders)).isNull();;
	}
	
	@Test
	public void testFindOrderWithLongestComments_methodArgumentIsListWithEmptyComment_methodReturnEmptyValue() {
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "JanKo", "Jan", "Kowalski", 0);
		ClientDetails client2 = new ClientDetails(1, "AdNo", "Adam", "Nowak", 0);
		ClientDetails client3 = new ClientDetails(2, "PiJed", "Piotr", "Jedlinski", 0);
		ClientDetails client4 = new ClientDetails(3, "WoKru", "Wojciech", "Krol", 0);
		ClientDetails client5 = new ClientDetails(4, "izaWo", "Izabela", "Wojnarska", 0);
		ClientDetails client6 = new ClientDetails(5, "KaMu", "Karolina", "Mular", 0);
		ClientDetails client7 = new ClientDetails(6, "JanKo", "Stanislaw", "Goralski", 0);
		
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
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "");
		Order order2 = new Order(1, client2, address2, itemsList2, "");
		Order order3 = new Order(2, client3, address3, itemsList3, "");
		Order order4 = new Order(3, client4, address4, itemsList4, "");
		Order order5 = new Order(4, client5, address5, itemsList5, "");
		Order order6 = new Order(5, client6, address6, itemsList6, "");
		Order order7 = new Order(6, client7, address7, itemsList7, "");

		 listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		assertThat(OrdersService.findOrderWithLongestComments(listOrders)).extracting("comments").contains("");
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld_methodArgumentIsNullValue_throwsNullPointerException() {
		assertThat(OrdersService.getNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld(null)).isNull();
	}
	
	@Test
	public void testGetNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld_methodArgumentIsEmptyList_methodReturnEmptyValue() {
		List<Order> listOrders = new LinkedList<Order>();
		assertThat(OrdersService.getNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld(listOrders)).contains("");
	}
	
	@Test
	public void testGetNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld_methodArgumentIsListWithoutNamesSurnamesAndWithAgeUnder18_methodReturnEmptyValue() {
		
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "", "", "", 0);
		ClientDetails client2 = new ClientDetails(1, "", "", "", 0);
		ClientDetails client3 = new ClientDetails(2, "", "", "", 0);
		ClientDetails client4 = new ClientDetails(3, "", "", "", 0);
		ClientDetails client5 = new ClientDetails(4, "", "", "", 0);
		ClientDetails client6 = new ClientDetails(5, "", "", "", 0);
		ClientDetails client7 = new ClientDetails(6, "", "", "", 0);
		
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
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "");
		Order order2 = new Order(1, client2, address2, itemsList2, "");
		Order order3 = new Order(2, client3, address3, itemsList3, "");
		Order order4 = new Order(3, client4, address4, itemsList4, "");
		Order order5 = new Order(4, client5, address5, itemsList5, "");
		Order order6 = new Order(5, client6, address6, itemsList6, "");
		Order order7 = new Order(6, client7, address7, itemsList7, "");

		 listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		assertThat(OrdersService.getNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld(listOrders)).contains("");
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA_methodArgumentIsNullValue_throwsNullPointerexception() {
		assertThat(OrdersService.getSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA(null)).isNull();
	}
	
	@Test
	public void testGetSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA_methodArgumentIsEmptyList_methodReturnsEmptyList() {
		List<Order> listOrders = new LinkedList<Order>();
		assertThat(OrdersService.getSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA(listOrders)).isNullOrEmpty();
	}
	
	@Test
	public void testGetSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA_methodArgumentIsListWithoutCommentsWhichStartsWithA_methodReturnsAccidentalListOfElements() {
	
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "", "", "", 0);
		ClientDetails client2 = new ClientDetails(1, "", "", "", 0);
		ClientDetails client3 = new ClientDetails(2, "", "", "", 0);
		ClientDetails client4 = new ClientDetails(3, "", "", "", 0);
		ClientDetails client5 = new ClientDetails(4, "", "", "", 0);
		ClientDetails client6 = new ClientDetails(5, "", "", "", 0);
		ClientDetails client7 = new ClientDetails(6, "", "", "", 0);
		
		Address address1 = new Address(0, "Jaskowa", "24", "12", "22-100", "Chelm", "Polska");
		Address address2 = new Address(1, "Piaskowa", "2", "3", "22-400", "Zamosc", "Polska");
		Address address3 = new Address(2, "Jasminowa", "54", "4", "22-100", "Chelm", "Polska");
		Address address4 = new Address(3, "Piaskowa", "12", "24", "18-43", "Wojcieszyce", "Polska");
		Address address5 = new Address(4, "Rozowa", "18", "54", "22-100", "Chelm", "Polska");
		Address address6 = new Address(5, "Generalska", "13 D", "4", "00-343", "Warszawa", "Polska");
		Address address7 = new Address(6, "Chabrowa", "78", "45", "81-595", "Gdynia", "Polska");

		OrderItem orderItem1 = new OrderItem(0, "", "odziez meska", 40.00);
		OrderItem orderItem2 = new OrderItem(1, "", "akcesoria biurowe", 5);
		OrderItem orderItem3 = new OrderItem(2, "", "akcesoria biurowe", 74.99);
		OrderItem orderItem4 = new OrderItem(3, "", "artykuly wypozazenia domowego", 20.00);
		OrderItem orderItem5 = new OrderItem(4, "", "sprzet elektroniczny", 1789.80);
		OrderItem orderItem6 = new OrderItem(5, "", "sprzet elektroniczny", 3000.00);
		OrderItem orderItem7 = new OrderItem(6, "", "elektronarzedzia budowlane", 220.20);
		OrderItem orderItem8 = new OrderItem(7, "", "artykuly budowlane", 12.54);
		OrderItem orderItem9 = new OrderItem(8, "", "motoryzacja", 80370.00);
		OrderItem orderItem10 = new OrderItem(9, "", "artukuly sportowe", 980.00);
		OrderItem orderItem11 = new OrderItem(10, "", "artykuly sportowe", 300.00);
		OrderItem orderItem12 = new OrderItem(11, "", "sprzet elektroniczny", 100.00);
		
		List<OrderItem> itemsList1 = Arrays.asList(orderItem1,orderItem2,orderItem3);
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "A");
		Order order2 = new Order(1, client2, address2, itemsList2, "A");
		Order order3 = new Order(2, client3, address3, itemsList3, "A");
		Order order4 = new Order(3, client4, address4, itemsList4, "A");
		Order order5 = new Order(4, client5, address5, itemsList5, "A");
		Order order6 = new Order(5, client6, address6, itemsList6, "A");
		Order order7 = new Order(6, client7, address7, itemsList7, "A");

		 listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		assertThat(OrdersService.getSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA(listOrders)).contains("");
	}
	
	@Test(expected = NullPointerException.class)
	public void testPrintCapitalizedClientsLoginsWhoHasNameStartingWithS_methodArgumentIsNullValue_throwsNullPointerException(){
		OrdersService.printCapitalizedClientsLoginsWhoHasNameStartingWithS(null);	
	}
	
	@Test
	public void testPrintCapitalizedClientsLoginsWhoHasNameStartingWithS_methodArgumentIsEmptyList_NoErrors() {
		List<Order> listOrders = new LinkedList<Order>();
		OrdersService.printCapitalizedClientsLoginsWhoHasNameStartingWithS(listOrders);	
	}
	
	@Test
	public void testPrintCapitalizedClientsLoginsWhoHasNameStartingWithS_methodArgumentListHaventElementsToPrint_NoErrors() {
		
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "", "", "", 0);
		ClientDetails client2 = new ClientDetails(1, "", "", "", 0);
		ClientDetails client3 = new ClientDetails(2, "", "", "", 0);
		ClientDetails client4 = new ClientDetails(3, "", "", "", 0);
		ClientDetails client5 = new ClientDetails(4, "", "", "", 0);
		ClientDetails client6 = new ClientDetails(5, "", "", "", 0);
		ClientDetails client7 = new ClientDetails(6, "", "", "", 0);
		
		Address address1 = new Address(0, "Jaskowa", "24", "12", "22-100", "Chelm", "Polska");
		Address address2 = new Address(1, "Piaskowa", "2", "3", "22-400", "Zamosc", "Polska");
		Address address3 = new Address(2, "Jasminowa", "54", "4", "22-100", "Chelm", "Polska");
		Address address4 = new Address(3, "Piaskowa", "12", "24", "18-43", "Wojcieszyce", "Polska");
		Address address5 = new Address(4, "Rozowa", "18", "54", "22-100", "Chelm", "Polska");
		Address address6 = new Address(5, "Generalska", "13 D", "4", "00-343", "Warszawa", "Polska");
		Address address7 = new Address(6, "Chabrowa", "78", "45", "81-595", "Gdynia", "Polska");

		OrderItem orderItem1 = new OrderItem(0, "", "odziez meska", 40.00);
		OrderItem orderItem2 = new OrderItem(1, "", "akcesoria biurowe", 5);
		OrderItem orderItem3 = new OrderItem(2, "", "akcesoria biurowe", 74.99);
		OrderItem orderItem4 = new OrderItem(3, "", "artykuly wypozazenia domowego", 20.00);
		OrderItem orderItem5 = new OrderItem(4, "", "sprzet elektroniczny", 1789.80);
		OrderItem orderItem6 = new OrderItem(5, "", "sprzet elektroniczny", 3000.00);
		OrderItem orderItem7 = new OrderItem(6, "", "elektronarzedzia budowlane", 220.20);
		OrderItem orderItem8 = new OrderItem(7, "", "artykuly budowlane", 12.54);
		OrderItem orderItem9 = new OrderItem(8, "", "motoryzacja", 80370.00);
		OrderItem orderItem10 = new OrderItem(9, "", "artukuly sportowe", 980.00);
		OrderItem orderItem11 = new OrderItem(10, "", "artykuly sportowe", 300.00);
		OrderItem orderItem12 = new OrderItem(11, "", "sprzet elektroniczny", 100.00);
		
		List<OrderItem> itemsList1 = Arrays.asList(orderItem1,orderItem2,orderItem3);
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "A");
		Order order2 = new Order(1, client2, address2, itemsList2, "A");
		Order order3 = new Order(2, client3, address3, itemsList3, "A");
		Order order4 = new Order(3, client4, address4, itemsList4, "A");
		Order order5 = new Order(4, client5, address5, itemsList5, "A");
		Order order6 = new Order(5, client6, address6, itemsList6, "A");
		Order order7 = new Order(6, client7, address7, itemsList7, "A");

		 listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		OrdersService.printCapitalizedClientsLoginsWhoHasNameStartingWithS(listOrders);	
	}
	
	@Test(expected = NullPointerException.class)
	public void testGroupOrdersByClient_methodArgumentIsNullValue_throwsNullPointerException() {
		assertThat(OrdersService.groupOrdersByClient(null)).isNull();
	}
	
	@Test
	public void testGroupOrdersByClient_methodArgumentIsEmptyList_methodReturnsEmptyMap() {
		List<Order> listOrders = new LinkedList<Order>();
		AssertionInfo info = new WritableAssertionInfo();
		Maps map = Maps.instance();
		map.assertNullOrEmpty(info, OrdersService.groupOrdersByClient(listOrders));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGroupOrdersByClient_methodArgumentIsPreparecdListWithoutClientAndOrderItemsValues_methodReturnsEmptyMap() {
		
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "", "", "", 0);
		ClientDetails client2 = new ClientDetails(1, "", "", "", 0);
		ClientDetails client3 = new ClientDetails(2, "", "", "", 0);
		ClientDetails client4 = new ClientDetails(3, "", "", "", 0);
		ClientDetails client5 = new ClientDetails(4, "", "", "", 0);
		ClientDetails client6 = new ClientDetails(5, "", "", "", 0);
		ClientDetails client7 = new ClientDetails(6, "", "", "", 0);
		
		Address address1 = new Address(0, "Jaskowa", "24", "12", "22-100", "Chelm", "Polska");
		Address address2 = new Address(1, "Piaskowa", "2", "3", "22-400", "Zamosc", "Polska");
		Address address3 = new Address(2, "Jasminowa", "54", "4", "22-100", "Chelm", "Polska");
		Address address4 = new Address(3, "Piaskowa", "12", "24", "18-43", "Wojcieszyce", "Polska");
		Address address5 = new Address(4, "Rozowa", "18", "54", "22-100", "Chelm", "Polska");
		Address address6 = new Address(5, "Generalska", "13 D", "4", "00-343", "Warszawa", "Polska");
		Address address7 = new Address(6, "Chabrowa", "78", "45", "81-595", "Gdynia", "Polska");

		OrderItem orderItem1 = new OrderItem(0, "", "", 40.00);
		OrderItem orderItem2 = new OrderItem(1, "", "", 5);
		OrderItem orderItem3 = new OrderItem(2, "", "", 74.99);
		OrderItem orderItem4 = new OrderItem(3, "", "", 20.00);
		OrderItem orderItem5 = new OrderItem(4, "", "", 1789.80);
		OrderItem orderItem6 = new OrderItem(5, "", "", 3000.00);
		OrderItem orderItem7 = new OrderItem(6, "", "", 220.20);
		OrderItem orderItem8 = new OrderItem(7, "", "", 12.54);
		OrderItem orderItem9 = new OrderItem(8, "", "", 80370.00);
		OrderItem orderItem10 = new OrderItem(9, "", "", 980.00);
		OrderItem orderItem11 = new OrderItem(10, "", "", 300.00);
		OrderItem orderItem12 = new OrderItem(11, "", "", 100.00);
		
		List<OrderItem> itemsList1 = Arrays.asList(orderItem1,orderItem2,orderItem3);
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "A");
		Order order2 = new Order(1, client2, address2, itemsList2, "A");
		Order order3 = new Order(2, client3, address3, itemsList3, "A");
		Order order4 = new Order(3, client4, address4, itemsList4, "A");
		Order order5 = new Order(4, client5, address5, itemsList5, "A");
		Order order6 = new Order(5, client6, address6, itemsList6, "A");
		Order order7 = new Order(6, client7, address7, itemsList7, "A");

		listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		AssertionInfo info = new WritableAssertionInfo();
		Maps map = Maps.instance();
		map.assertContainsKeys(info, OrdersService.groupOrdersByClient(listOrders));
		map.assertContainsValues(info, OrdersService.groupOrdersByClient(listOrders));
	}
	
	@Test(expected = NullPointerException.class)
	public void testPartitionClientsByUnderAndOver18_methodArgumentIsNullValue_throwsNullPointerException() {
		OrdersService.partitionClientsByUnderAndOver18(null);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPartitionClientsByUnderAndOver18_methodArgumentIsEmptyList_ReturnsEmptyMap() {
		List<Order> listOrders = new LinkedList<Order>();
		AssertionInfo info = new WritableAssertionInfo();
		Maps map = Maps.instance();
		map.assertContainsKeys(info, OrdersService.partitionClientsByUnderAndOver18(listOrders));
		map.assertContainsValues(info, OrdersService.partitionClientsByUnderAndOver18(listOrders));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPartitionClientsByUnderAndOver18_methodArgumentIsPreparedListWithoutAgeVariable_ReturnsEmptyMap() {
		
		List<Order> listOrders = new LinkedList<Order>();
		
		ClientDetails client1 = new ClientDetails(0, "", "", "", 0);
		ClientDetails client2 = new ClientDetails(1, "", "", "", 0);
		ClientDetails client3 = new ClientDetails(2, "", "", "", 0);
		ClientDetails client4 = new ClientDetails(3, "", "", "", 0);
		ClientDetails client5 = new ClientDetails(4, "", "", "", 0);
		ClientDetails client6 = new ClientDetails(5, "", "", "", 0);
		ClientDetails client7 = new ClientDetails(6, "", "", "", 0);
		
		Address address1 = new Address(0, "Jaskowa", "24", "12", "22-100", "Chelm", "Polska");
		Address address2 = new Address(1, "Piaskowa", "2", "3", "22-400", "Zamosc", "Polska");
		Address address3 = new Address(2, "Jasminowa", "54", "4", "22-100", "Chelm", "Polska");
		Address address4 = new Address(3, "Piaskowa", "12", "24", "18-43", "Wojcieszyce", "Polska");
		Address address5 = new Address(4, "Rozowa", "18", "54", "22-100", "Chelm", "Polska");
		Address address6 = new Address(5, "Generalska", "13 D", "4", "00-343", "Warszawa", "Polska");
		Address address7 = new Address(6, "Chabrowa", "78", "45", "81-595", "Gdynia", "Polska");

		OrderItem orderItem1 = new OrderItem(0, "", "", 40.00);
		OrderItem orderItem2 = new OrderItem(1, "", "", 5);
		OrderItem orderItem3 = new OrderItem(2, "", "", 74.99);
		OrderItem orderItem4 = new OrderItem(3, "", "", 20.00);
		OrderItem orderItem5 = new OrderItem(4, "", "", 1789.80);
		OrderItem orderItem6 = new OrderItem(5, "", "", 3000.00);
		OrderItem orderItem7 = new OrderItem(6, "", "", 220.20);
		OrderItem orderItem8 = new OrderItem(7, "", "", 12.54);
		OrderItem orderItem9 = new OrderItem(8, "", "", 80370.00);
		OrderItem orderItem10 = new OrderItem(9, "", "", 980.00);
		OrderItem orderItem11 = new OrderItem(10, "", "", 300.00);
		OrderItem orderItem12 = new OrderItem(11, "", "", 100.00);
		
		List<OrderItem> itemsList1 = Arrays.asList(orderItem1,orderItem2,orderItem3);
		List<OrderItem> itemsList2 = Arrays.asList(orderItem1,orderItem2,orderItem3,orderItem4);
		List<OrderItem> itemsList3 = Arrays.asList(orderItem7,orderItem8,orderItem9,orderItem10);
		List<OrderItem> itemsList4 = Arrays.asList(orderItem2,orderItem4,orderItem6,orderItem8);
		List<OrderItem> itemsList5 = Arrays.asList(orderItem11,orderItem9,orderItem7,orderItem5);
		List<OrderItem> itemsList6 = Arrays.asList(orderItem7,orderItem12);
		List<OrderItem> itemsList7 = Arrays.asList(orderItem9);

		Order order1 = new Order(0, client1, address1, itemsList1, "A");
		Order order2 = new Order(1, client2, address2, itemsList2, "A");
		Order order3 = new Order(2, client3, address3, itemsList3, "A");
		Order order4 = new Order(3, client4, address4, itemsList4, "A");
		Order order5 = new Order(4, client5, address5, itemsList5, "A");
		Order order6 = new Order(5, client6, address6, itemsList6, "A");
		Order order7 = new Order(6, client7, address7, itemsList7, "A");

		listOrders = Arrays.asList(order1,order2,order3,order4,order5,order6,order7);
		
		AssertionInfo info = new WritableAssertionInfo();
		Maps map = Maps.instance();
		map.assertContainsKeys(info, OrdersService.partitionClientsByUnderAndOver18(listOrders));
		map.assertContainsValues(info, OrdersService.partitionClientsByUnderAndOver18(listOrders));
	}
}
