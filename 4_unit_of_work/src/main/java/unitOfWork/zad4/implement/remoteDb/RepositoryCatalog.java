package unitOfWork.zad4.implement.remoteDb;

import java.sql.Connection;

import unitOfWork.zad4.implement.entityBuilder.AddressBuilder;
import unitOfWork.zad4.implement.entityBuilder.ClientBuilder;
import unitOfWork.zad4.implement.entityBuilder.ItemBuilder;
import unitOfWork.zad4.implement.entityBuilder.OrderBuilder;
import unitOfWork.zad4.repositories.IAddressRepository;
import unitOfWork.zad4.repositories.IClientRepository;
import unitOfWork.zad4.repositories.IItemRepository;
import unitOfWork.zad4.repositories.IOrderRepository;
import unitOfWork.zad4.repositories.IRepositoryCatalog;
import unitOfWork.zad4.unitOfWork.IUnitOfWork;

public class RepositoryCatalog implements IRepositoryCatalog{
	
	private Connection connection;
	private IUnitOfWork uow;
	
	public RepositoryCatalog(Connection connection, IUnitOfWork uow) {
		super();
		this.connection = connection;
		this.uow = uow;
	}

	
	
	public IClientRepository getClients() {
		return new RemoteClientRepository(connection, new ClientBuilder(), uow);
	}

	public IAddressRepository getAddresses() {
		return new RemoteAddressRepository(connection, new AddressBuilder(), uow);
	}

	public IItemRepository getItems() {
		return new RemoteItemRepository(connection, new ItemBuilder(), uow);
	}
	
	public IOrderRepository getOrders() {
		return new RemoteOrderRepository(connection, new OrderBuilder(), uow);
	}
	
	public void commit() {
		uow.commit();
	}
	
	public void rollback(){
		uow.rollback();
	}

}
