package unitOfWork.zad4.repositories;

public interface IRepositoryCatalog {

	public IOrderRepository getOrders();
	public IClientRepository getClients();
	public IAddressRepository getAddresses();
	public IItemRepository getItems();
	public void commit();
	public void rollback();
}
