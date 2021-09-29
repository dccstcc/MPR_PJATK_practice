package junit.zad6;


import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.EntityState;
import unitOfWork.zad4.implement.entityBuilder.ClientBuilder;
import unitOfWork.zad4.implement.remoteDb.RemoteClientRepository;
import unitOfWork.zad4.unitOfWork.UnitOfWork;

@RunWith(MockitoJUnitRunner.class)
public class TestRemoteClientRepository {
    
	 	
	@Mock
	 Connection connection ;
	
	@Mock
	 ClientBuilder clientBuilder;
	
	@Mock
	 UnitOfWork uow;
	
	@Mock
	 PreparedStatement stmt;
	
	@Mock
	 ResultSet rs;
	
	@Mock
	 List<ClientDetails> listClient;
	
	@Mock
	 ClientDetails client;
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@InjectMocks
	 RemoteClientRepository remoteClientRepository;
	
	public TestRemoteClientRepository() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public  void start() {
		
		connection = Mockito.mock(Connection.class);
		clientBuilder = Mockito.mock(ClientBuilder.class);
		uow = Mockito.mock(UnitOfWork.class);
		stmt = Mockito.mock(PreparedStatement.class);
		rs = Mockito.mock(ResultSet.class);
		client = Mockito.mock(ClientDetails.class);

		try{
		Mockito.when(rs.getInt(Matchers.anyString())).thenReturn(1);
		Mockito.when(rs.getString(Matchers.anyString())).thenReturn("testValue");
		Mockito.when(connection.prepareStatement(Matchers.anyString())).thenReturn(stmt);

		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		
		Mockito.when(clientBuilder.build(Matchers.eq(rs))).thenReturn(listClient);
		}catch(SQLException exc){
			exc.printStackTrace();
		}
		
		
		remoteClientRepository = new RemoteClientRepository(connection, clientBuilder, uow);
	}
	
	@After
	public void clear() {
		Mockito.reset(connection); 
		Mockito.reset(clientBuilder); 
		Mockito.reset(uow);
		Mockito.reset(stmt);
		Mockito.reset(rs);
		Mockito.reset(client);
	}
	
	@Test
	public void testSave_addNewClient_ok() throws SQLException{
		remoteClientRepository.save(client);
		Mockito.verify(uow).markAsNew(client, remoteClientRepository);
	}
	 @Test
	 public void testSave_addNullValue_false() {
			remoteClientRepository.save(null);
			Mockito.verify(uow, Mockito.never()).markAsNew(client, remoteClientRepository);
	 }
	 
	@Test
	public void testUpdateClient_updateExistingClient_verifyOk() throws SQLException {
		remoteClientRepository.update(client);
		Mockito.verify(uow).markAsDirty(client, remoteClientRepository);
	}
	
	@Test
	public void testUpdateClient_updateWithNullValue_error() throws SQLException {
		remoteClientRepository.update(null);
		Mockito.verify(uow, Mockito.never()).markAsDirty(client, remoteClientRepository);
	}
	
	@Test
	public void testUpdateClient_updateNotExistingClient_ok() throws SQLException {
		Mockito.when(client.getLogin()).thenReturn("First Object");
		remoteClientRepository.update(client);
		Mockito.doCallRealMethod().when(client).setState(EntityState.Changed);
		Mockito.doCallRealMethod().when(uow).markAsDirty(client, remoteClientRepository);
		Mockito.doCallRealMethod().when(uow).commit();
		Mockito.verify(uow).markAsDirty(client, remoteClientRepository);
		remoteClientRepository.persistUpdate(client);
		Mockito.verify(stmt).executeUpdate();

	}
	
	@Test
	public void testDeleteClient_clientExist_assertEqualsTrue() {
		Mockito.when(client.getLogin()).thenReturn("clientWillBeDelete");
		ArgumentCaptor<ClientDetails> arg = ArgumentCaptor.forClass(ClientDetails.class);
		remoteClientRepository.delete(client);
		Mockito.verify(uow).markAsDeleted(arg.capture(), Mockito.eq(remoteClientRepository));
		String clientSpecification = arg.getValue().getLogin();
		assertEquals(clientSpecification, "clientWillBeDelete");
	}
	
	@Test 
	public void testDeleteClient_clientDoesntExist_error() {
		Mockito.when(client.getLogin()).thenReturn("canNotDelete_thisClientIsNewForDatabase");
		ArgumentCaptor<ClientDetails> arg = ArgumentCaptor.forClass(ClientDetails.class);
		remoteClientRepository.delete(client);
		Mockito.verify(uow).markAsDeleted(arg.capture(), Mockito.eq(remoteClientRepository));
		String clientSpecification = arg.getAllValues().get(0).getLogin();
		assertEquals(clientSpecification, "canNotDelete_thisClientIsNewForDatabase");
		
	}
	
	@Test
	public void testDeleteClient_withNullValue_assertNullTrue() {
		ArgumentCaptor<ClientDetails> arg = ArgumentCaptor.forClass(ClientDetails.class);
		remoteClientRepository.delete(null);
		Mockito.verify(uow).markAsDeleted(arg.capture(), Mockito.eq(remoteClientRepository));
		assertNull(arg.getValue());
	}
	
	@Test
	public void testPersistAdd_withCorrectValue_verifyOk() throws SQLException{
		Mockito.when(client.getName()).thenReturn("clientName");
		Mockito.when(client.getSurname()).thenReturn("clientSurname");
		Mockito.when(client.getLogin()).thenReturn("clientLogin");
		remoteClientRepository.persistAdd(client);
		Mockito.verify(stmt).executeUpdate();
	}
	
	//odwoluje sie do metody prywantej, ktorej nie da sie zamockowac
	public void testPersistAdd_withNullValue_verifyOk() throws SQLException {
		remoteClientRepository.persistAdd(null);		
		Mockito.doThrow(new RuntimeException()).when(stmt).setString(Matchers.anyInt(), Matchers.isNull(String.class));
		Mockito.verify(stmt, Mockito.never()).executeUpdate();
	}
	
	@Test
	public void testPersistUpdate_withCorrectExistingEntity_verifyAndAssertEqualsOk() throws SQLException{
		Mockito.when(client.getName()).thenReturn("clientNameThatExist");
		Mockito.when(client.getSurname()).thenReturn("clientNameThatExist");
		Mockito.when(client.getLogin()).thenReturn("clientNameThatExist");
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(stmt).setString(Matchers.anyInt(), arg.capture());
		remoteClientRepository.persistAdd(client);
		Mockito.verify(stmt).executeUpdate();
		assertEquals(arg.getValue(), "clientNameThatExist");
	}
	
	@Test
	public void testPersistUpdate_withIncorrectEntityThatIsntExist_verifyAndAssertEqualsOk() throws SQLException{
		Mockito.when(client.getName()).thenReturn("clientNameThatIsntExist");
		Mockito.when(client.getSurname()).thenReturn("clientNameThatIsntExist");
		Mockito.when(client.getLogin()).thenReturn("clientNameThatIsntExist");
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(stmt).setString(Matchers.anyInt(), arg.capture());
		remoteClientRepository.persistAdd(client);
		Mockito.verify(stmt).executeUpdate();
		assertEquals(arg.getValue(), "clientNameThatIsntExist");
	}
	
	//odwoluje sie do metody prywantej, ktorej nie da sie zamockowac
	public void testPersistUpdate_withNullValue_verifyOk() throws SQLException {
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(stmt).setString(Matchers.anyInt(), arg.capture());
		remoteClientRepository.persistAdd(null);
		Mockito.verify(stmt, Mockito.never()).executeUpdate();
		assertNull(arg.getValue());
	}
	
	@Test
	public void testPersistDelete_withCorrectExistingEntity_verifyAndAssertEqualsOk() throws SQLException{
		Mockito.when(client.getId()).thenReturn(100);
		remoteClientRepository.persistDelete(client);
		Mockito.verify(stmt).executeUpdate();
		
	}
	
	@Test
	public void testPersistDelete_withIncorrectEntityThatIsntExist_verifyAndAssertEqualsOk() throws SQLException{
		remoteClientRepository.persistDelete(client);
		Mockito.verify(stmt).executeUpdate();
	}
	
	//NullPointerException
	public void testPersistDelete_withNullValue_verifyOk() throws SQLException {
		remoteClientRepository.persistDelete(null);
		Mockito.verify(stmt.executeUpdate(), Mockito.never());
	} 
	
	@Test
	public void testWithOrder_getExistingClients_assertEqualsTrue() throws SQLException{
		Mockito.when(rs.getInt("ClientDetails_id")).thenReturn(100);
		List<ClientDetails> listClient;
		listClient = remoteClientRepository.withOrder(20);
		assertEquals(listClient.get(0).getId(), 1);		
	}
	
	@Test
	public void testWithOrder_getNotExistingClients_assertNotNullTrue() throws SQLException{
		Mockito.when(rs.getInt("ClientDetails_id")).thenReturn(20);
		List<ClientDetails> listClient;
		listClient = remoteClientRepository.withOrder(20);
		assertNotNull(listClient.get(0).getId());		
	}
	
	//nullPointerException
	public void testWithOrder_getWithNull_assertNullTrue() throws SQLException{
		Mockito.reset(stmt);
		Mockito.when(rs.getInt("ClientDetails_id")).thenReturn(100);
		List<ClientDetails> listClient;
		listClient = remoteClientRepository.withOrder(Integer.getInteger(null));
		assertNull(listClient);		
	}
	
	@Test
	public void testWithAddress_getExistingClientsForValidAddress_assertEqualsTrue() throws SQLException{
		Mockito.when(rs.getInt("id")).thenReturn(100);
		List<ClientDetails> listClient;
		listClient = remoteClientRepository.withAddress("validAddress");
		assertEquals(listClient.get(0).getId(),100);		
	}
	
	@Test
	public void testWithAddress_getExistingClientsForNotValidAddress_assertNullTrue() throws SQLException{
		Mockito.when(rs.getInt(Matchers.anyInt())).thenReturn(1);
		Mockito.when(rs.getString(Matchers.anyString())).thenReturn(null).thenReturn(null).thenReturn(null);
		List<ClientDetails> listClient = remoteClientRepository.withAddress("NotValidAddress");
		Mockito.verify(connection, Mockito.times(8)).prepareStatement(Matchers.anyString());
		Mockito.verify(stmt).setString(Matchers.anyInt(),  Matchers.anyString());
		Mockito.verify(rs, Mockito.times(3)).getInt(Matchers.anyString());
		assertNull(listClient.get(0).getLogin());		
	}	
	
	@Test
	public void testWithAddress_getExistingClientsForNullArg_assertNullTrue() throws SQLException{
		Mockito.when(rs.getInt(Matchers.anyInt())).thenReturn(1);
		Mockito.when(rs.getString(Matchers.anyString())).thenReturn(null).thenReturn(null).thenReturn(null);
		List<ClientDetails> listClient = remoteClientRepository.withAddress(null);
		Mockito.verify(connection, Mockito.times(8)).prepareStatement(Matchers.anyString());
		Mockito.verify(stmt).setString(Matchers.anyInt(),  Matchers.anyString());
		Mockito.verify(rs, Mockito.times(3)).getInt(Matchers.anyString());
		assertNull(listClient.get(0).getLogin());		
	}	
	
	@Test
	public void testWithItem_getExistingClientsForValidArg_assertEqualsTrue() throws SQLException{
		Mockito.when(rs.getInt(Matchers.anyInt())).thenReturn(1);
		Mockito.when(rs.getString(Matchers.anyString())).thenReturn("name").thenReturn("surname").thenReturn("login");
		List<ClientDetails> listClient = remoteClientRepository.withItem("validItem");
		Mockito.verify(connection, Mockito.times(8)).prepareStatement(Matchers.anyString());
		Mockito.verify(stmt).setString(Matchers.anyInt(),  Matchers.anyString());
		Mockito.verify(rs, Mockito.times(3)).getInt(Matchers.anyString());
		assertEquals("login", listClient.get(0).getLogin() );		
	}	
	
	@Test
	public void testWithItem_getExistingClientsForNotValidArg_assertNullTrue() throws SQLException{
		Mockito.when(rs.getInt(Matchers.anyInt())).thenReturn(1);
		Mockito.when(rs.getString(Matchers.anyString())).thenReturn(null).thenReturn(null).thenReturn(null);
		List<ClientDetails> listClient = remoteClientRepository.withItem("notValidItem");
		Mockito.verify(connection, Mockito.times(8)).prepareStatement(Matchers.anyString());
		Mockito.verify(stmt).setString(Matchers.anyInt(),  Matchers.anyString());
		Mockito.verify(rs, Mockito.times(3)).getInt(Matchers.anyString());
		assertNull(listClient.get(0).getSurname() );		
	}	
	
	@Test
	public void testWithItem_getExistingClientsForNullArgument_assertNullTrue() throws SQLException{
		Mockito.when(rs.getInt(Matchers.anyInt())).thenReturn(1);
		Mockito.when(rs.getString(Matchers.anyString())).thenReturn(null).thenReturn(null).thenReturn(null);
		List<ClientDetails> listClient = remoteClientRepository.withItem(null);
		Mockito.verify(connection, Mockito.times(8)).prepareStatement(Matchers.anyString());
		Mockito.verify(stmt).setString(Matchers.anyInt(),  Matchers.anyString());
		Mockito.verify(rs, Mockito.times(3)).getInt(Matchers.anyString());
		assertNull(listClient.get(0).getName() );		
	}	
	
	@Test
	public void testGet_withCorrectIdAsArgument_assertEqualsTrue() throws SQLException{
		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(clientBuilder.build(rs)).thenReturn(listClient);
		Mockito.when(listClient.get(Matchers.anyInt())).thenReturn(client);
		Mockito.when(client.getName()).thenReturn("correctName");
		ClientDetails clientDetailsNewObject = remoteClientRepository.get(1);
		Mockito.verify(stmt).setInt(Matchers.anyInt(), Matchers.anyInt());
		Mockito.verify(stmt).executeQuery();
		Mockito.verify(rs).next();
		Mockito.verify(clientBuilder).build(rs);
		assertEquals("correctName", clientDetailsNewObject.getName());
	}
	
	@Test
	public void testGet_withIncorrectIdAsArgument_assertNullTrue() throws SQLException{
		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(clientBuilder.build(rs)).thenReturn(listClient);
		Mockito.when(listClient.get(Matchers.anyInt())).thenReturn(client);
		Mockito.when(client.getName()).thenReturn(null);
		ClientDetails clientDetailsNewObject = remoteClientRepository.get(1);
		Mockito.verify(stmt).setInt(Matchers.anyInt(), Matchers.anyInt());
		Mockito.verify(stmt).executeQuery();
		Mockito.verify(rs).next();
		Mockito.verify(clientBuilder).build(rs);
		assertNull(clientDetailsNewObject.getName());
	}
	
	@Test
	public void testGetAll_databaseNotEmpty_assertEqualsTrue() throws SQLException{
		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(clientBuilder.build(rs)).thenReturn(listClient);
		Mockito.when(listClient.get(Matchers.anyInt())).thenReturn(client);
		Mockito.when(client.getSurname()).thenReturn("correctSurname");
		List<ClientDetails> clientDetailsNewList  = remoteClientRepository.getAll();
		Mockito.verify(stmt).executeQuery();
		Mockito.verify(rs).next();
		Mockito.verify(clientBuilder).build(rs);
		assertEquals("correctSurname", clientDetailsNewList.get(0).getSurname());
	}
	
	@Test
	public void testGetAll_databaseIsEmpty_assertNullTrue() throws SQLException{
		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(clientBuilder.build(rs)).thenReturn(listClient);
		Mockito.when(listClient.get(Matchers.anyInt())).thenReturn(client);
		Mockito.when(client.getSurname()).thenReturn(null);
		List<ClientDetails> clientDetailsNewList  = remoteClientRepository.getAll();
		Mockito.verify(stmt).executeQuery();
		Mockito.verify(rs).next();
		Mockito.verify(clientBuilder).build(rs);
		assertNull(clientDetailsNewList.get(0).getSurname());
	}
		
	
}
