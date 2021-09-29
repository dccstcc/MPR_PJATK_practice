package unitOfWork.zad4.implement.localDb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import unitOfWork.zad4.repositories.IItemRepository;

public class LocalItemRepository implements IItemRepository{

	private LocalDb localDb;

	public LocalItemRepository(LocalDb localDb) {
		super();
		this.localDb = localDb;
	}
	
	public void save(OrderItem orderItem) {
		localDb.getItems().add(orderItem);
	}
	
	public void update(OrderItem orderItem) {
		for (OrderItem oi : localDb.getItems()) {
			if (oi.getId()==orderItem.getId()) {
				int index = localDb.getItems().indexOf(oi);
				if (index > -1) {
					localDb.getItems().remove(index);
					localDb.getItems().add(oi);
				}
			}
		}
	}
	
	public void delete(OrderItem orderItem) {
		for (OrderItem oi : localDb.getItems()) {
			if (oi.getId()==orderItem.getId()) {
				int index = localDb.getItems().indexOf(oi);
				if (index > -1) {
					localDb.getItems().remove(index);
				}
			}
		
		}
	}
	
	public OrderItem get(int id) {
		OrderItem orderItem = null;
		for (OrderItem oi : localDb.getItems()) {
			if(oi.getId()==id) {
				orderItem = oi;
				break;
			}
		}
		return orderItem;
	}
	

	public List<OrderItem> getAll() {
		return localDb.getItems();
	}
	
	public List<OrderItem> withClient(String name) {
		List<OrderItem> orderItem = new ArrayList<OrderItem>();
		for(ClientDetails cd : localDb.getClients()) {
			if(cd.getName().equals(name)) {
				int id = cd.getId();
				for(Orders od : localDb.getOrders()) {
					if(od.getClient().getId()==id) {
						orderItem = od.getItems(); 
					}
				}
			}
		
		}
		return orderItem;
		
	}
	
	public List<OrderItem> withOrder(int order) {
		List<OrderItem> orderItem = new LinkedList<OrderItem>();
		for(Orders od : localDb.getOrders()) {
			if(od.getId()==order) {
				orderItem = od.getItems();
			}
				
		}
		return orderItem;
	}
	
	public List<OrderItem> withAddress(String street) {
		List<OrderItem> orderItem = new LinkedList<OrderItem>();
		for(Address ad : localDb.getAddresses()) {
			if(ad.getStreet().equals(street)) {
				int id = ad.getId();
				for(Orders od : localDb.getOrders()) {
					if(od.getDeliveryAddress().getId()==id) {
						orderItem = od.getItems();
					}
				}
			}
		}
		return orderItem;
	}
}
