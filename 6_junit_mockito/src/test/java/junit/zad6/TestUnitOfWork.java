package junit.zad6;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.EntityState;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import unitOfWork.zad4.implement.remoteDb.RemoteAddressRepository;
import unitOfWork.zad4.implement.remoteDb.RemoteClientRepository;
import unitOfWork.zad4.implement.remoteDb.RemoteItemRepository;
import unitOfWork.zad4.implement.remoteDb.RemoteOrderRepository;
import unitOfWork.zad4.unitOfWork.UnitOfWork;


@RunWith(MockitoJUnitRunner.class)
public class TestUnitOfWork {

	@Mock
	Connection connection;
	
	@Mock ClientDetails clientDetails;
	
	@Mock
	Address address;
	
	@Mock
	OrderItem orderItem;
	
	@Mock
	Orders orders;	
		
	@Mock
	RemoteClientRepository remoteClientRepository;
	
	@Mock
	RemoteAddressRepository remoteAddressRepository;
	
	@Mock
	RemoteItemRepository remoteItemRepository;
	
	@Mock
	RemoteOrderRepository remoteOrderRepository;
	
	@Mock
	PreparedStatement stmt;
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@InjectMocks
	 UnitOfWork uow; 
	
	public TestUnitOfWork() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setUp() throws SQLException{
		
		connection = Mockito.mock(Connection.class);
		clientDetails = Mockito.mock(ClientDetails.class);
		address = Mockito.mock(Address.class);
		orderItem = Mockito.mock(OrderItem.class);
		orders = Mockito.mock(Orders.class);
		remoteClientRepository = Mockito.mock(RemoteClientRepository.class);
		remoteAddressRepository = Mockito.mock(RemoteAddressRepository.class);
		remoteItemRepository = Mockito.mock(RemoteItemRepository.class);
		remoteOrderRepository = Mockito.mock(RemoteOrderRepository.class);
		stmt = Mockito.mock(PreparedStatement.class);
		
		Mockito.when(connection.prepareStatement(Matchers.anyString())).thenReturn(stmt);
		
		uow = new UnitOfWork(connection);
	}
	
	@After
	public void reset() {
		Mockito.reset(connection);
		Mockito.reset(clientDetails);
		Mockito.reset(address);
		Mockito.reset(orderItem);
		Mockito.reset(orders);
		Mockito.reset(remoteClientRepository);
		Mockito.reset(remoteAddressRepository);
		Mockito.reset(remoteItemRepository);
		Mockito.reset(remoteOrderRepository);	
		Mockito.reset(stmt);		
	}
	
	@Test
	public void testMarkAsNew_entityIsCorrectAndUnitOfWorkRepositoryIsCorrect_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(clientDetails).setState(EntityState.New);
		Mockito.doCallRealMethod().when(clientDetails).getState();
		uow.markAsNew(clientDetails, remoteClientRepository);
		uow.commit();
		ArgumentCaptor<ClientDetails> arg = ArgumentCaptor.forClass(ClientDetails.class);
		Mockito.verify(remoteClientRepository).persistAdd(arg.capture());
		assertEquals(clientDetails,arg.getValue());
	}
	
	//NullPointerException
	public void testMarkAsNew_entityIsNullAndUnitOfWorkRepositoryIsCorrect_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(clientDetails).setState(EntityState.New);
		Mockito.doCallRealMethod().when(clientDetails).getState();
		uow.markAsNew(null, remoteClientRepository);
		uow.commit();
	}
	
	
	//NullPointerException
	public void testMarkAsNew_entityIsCorrectAndUnitOfWorkRepositoryIsNull_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(clientDetails).setState(EntityState.New);
		Mockito.doCallRealMethod().when(clientDetails).getState();
		uow.markAsNew(clientDetails, null);
		uow.commit();
	}
	
	@Test
	public void testMarkAsNew_entityIsCorrectAndUnitOfWorkRepositoryIsWrong_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(clientDetails).setState(EntityState.New);
		Mockito.doCallRealMethod().when(clientDetails).getState();
		uow.markAsNew(clientDetails, remoteAddressRepository);
		uow.commit();
		ArgumentCaptor<ClientDetails> arg = ArgumentCaptor.forClass(ClientDetails.class);
		Mockito.verify(remoteAddressRepository).persistAdd(arg.capture());
		assertEquals(clientDetails, arg.getValue());
	}
	
	@Test
	public void testMarkAsChanged_entityIsCorrectAndUnitOfWorkRepositoryIsCorrect_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(address).setState(EntityState.Changed);
		Mockito.doCallRealMethod().when(address).getState();
		uow.markAsDirty(address, remoteAddressRepository);
		uow.commit();
		ArgumentCaptor<Address> arg = ArgumentCaptor.forClass(Address.class);
		Mockito.verify(remoteAddressRepository).persistUpdate(arg.capture());
		assertEquals(address, arg.getValue());
	}
	
	@Test
	public void testMarkAsChanged_entityIsCorrectAndUnitOfWorkRepositoryIsWrong_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(address).setState(EntityState.Changed);
		Mockito.doCallRealMethod().when(address).getState();
		uow.markAsDirty(address, remoteClientRepository);
		uow.commit();
		ArgumentCaptor<Address> arg = ArgumentCaptor.forClass(Address.class);
		Mockito.verify(remoteClientRepository).persistUpdate(arg.capture());
		assertEquals(address, arg.getValue());
	}
	
	@Test
	public void testMarkAsDeleted_entityIsCorrectAndUnitOfWorkRepositoryIsCorrect_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(orderItem).setState(EntityState.Deleted);
		Mockito.doCallRealMethod().when(orderItem).getState();
		uow.markAsDeleted(orderItem, remoteItemRepository);
		uow.commit();
		ArgumentCaptor<OrderItem> arg = ArgumentCaptor.forClass(OrderItem.class);
		Mockito.verify(remoteItemRepository).persistDelete(arg.capture());
		assertEquals(orderItem, arg.getValue());
	}
	
	@Test
	public void testMarkAsDeleted_entityIsCorrectAndUnitOfWorkRepositoryIsWrong_verifyOk() throws SQLException{
		Mockito.doCallRealMethod().when(orderItem).setState(EntityState.Deleted);
		Mockito.doCallRealMethod().when(orderItem).getState();
		uow.markAsDeleted(orderItem, remoteOrderRepository);
		uow.commit();
		ArgumentCaptor<OrderItem> arg = ArgumentCaptor.forClass(OrderItem.class);
		Mockito.verify(remoteOrderRepository).persistDelete(arg.capture());
		assertEquals(orderItem, arg.getValue());
	}
}
