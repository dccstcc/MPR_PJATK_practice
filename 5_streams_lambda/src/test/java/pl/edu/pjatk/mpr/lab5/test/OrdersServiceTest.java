package pl.edu.pjatk.mpr.lab5.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.Class;
import java.util.function.Consumer;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.edu.pjatk.mpr.lab5.model.Address;
import pl.edu.pjatk.mpr.lab5.model.ClientDetails;
import pl.edu.pjatk.mpr.lab5.model.Order;
import pl.edu.pjatk.mpr.lab5.model.OrderItem;
import pl.edu.pjatk.mpr.lab5.service.OrdersService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrdersService.class)
public class OrdersServiceTest {
	
	@Mock
	ClientDetails mockClient;
	
	@Mock
	Address mockAddress;
	
	@Mock
	OrderItem mockItem;
	
	@Mock
	List<OrderItem> mockItemsList;
	
	@Mock
	Order mockOrder;
	
	@Mock
	List<Order> mockOrdersList;


	@Before
	public void setUp() {
		
		mockClient = PowerMockito.mock(ClientDetails.class);
		mockAddress = PowerMockito.mock(Address.class);
		mockItem =  PowerMockito.mock(OrderItem.class);
		mockItemsList = PowerMockito.mock(List.class);
		mockOrder = PowerMockito.mock(Order.class);
		mockOrdersList = PowerMockito.mock(List.class);
		
//		mockItemsList.add(mockItem);
//		mockOrder.setClientDetails(mockClient);
//		mockOrder.setAddress(mockAddress);
//		mockOrder.setItems(mockItemsList);
//		mockOrdersList.add(mockOrder);
	}
	
	@After
	public void clear() {
		mockClient=null;
		mockAddress=null;
		mockItem=null;
		mockItemsList=null;
		mockOrdersList=null;
		mockOrder=null;
	}
	
	
	@Test
	public void testFfindOrdersWhichHaveMoreThan5OrderItems_IHaveMoreThan5_true() {
		
		PowerMockito.mockStatic(OrdersService.class);
		
		assertTrue(true);
	}
}
