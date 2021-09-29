package unitOfWork.zad4.implement.localDb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import unitOfWork.zad4.repositories.IOrderRepository;

public class LocalOrderRepository implements IOrderRepository{
	
	private LocalDb localDb;

	public LocalOrderRepository(LocalDb localDb) {
		super();
		this.localDb = localDb;
	}
	
	public void save(Orders orders) {
		localDb.getOrders().add(orders);
	}
	
	public void update(Orders orders) {
		for (Orders od : localDb.getOrders()) {
			if (orders.getId()==od.getId()) {
				int index = localDb.getOrders().indexOf(od);
				if (index > -1) {
					localDb.getOrders().remove(index);
					localDb.getOrders().add(od);
				}
			}
		}
	}
	
	public void delete(Orders orders) {
		for (Orders od : localDb.getOrders()) {
			if (orders.getId()==od.getId()) {
				int index = localDb.getOrders().indexOf(od);
				if (index > -1) {
					localDb.getOrders().remove(index);
				}
			}
		
		}
	}
	
	public Orders get(int id) {
		Orders orders = null;
		for (Orders od : localDb.getOrders()) {
			if(od.getId()==id) {
				orders = od;
				break;
			}
		}
		return orders;
	}
	

	public List<Orders> getAll() {
		return localDb.getOrders();
	}
	
	public List<Orders> withAddress(String street) {
		List<Orders> orders = new ArrayList<Orders>();
		for(Address ad : localDb.getAddresses()) {
			if(ad.getStreet().equals(street)) {
				int id = ad.getId();
				for(Orders od : localDb.getOrders()) {
					if(od.getDeliveryAddress().getId()==id) {
						orders.add(od);
					}
				}
			}
		
		}
		return orders;
		
	}
	
	public List<Orders> withClient(String name) {
		List<Orders> orders = new LinkedList<Orders>();
		for(ClientDetails cd : localDb.getClients()) {
			if(cd.getName().equals(name)) {
				int id = cd.getId();
				for (Orders od : localDb.getOrders()) {
					if(od.getClient().getId()==id) {
						orders.add(od);
					}
				}
			}
		}
		return orders;
	}
	
	public List<Orders> withItem(String item) {
		List<Orders> orders = new LinkedList<Orders>();
		for(OrderItem oi : localDb.getItems()) {
			if(oi.getItem().equals(item)) {
				int id = oi.getId();
				for(Orders od : localDb.getOrders()) {
					for(OrderItem oitm : od.getItems())
					if(oitm.getId()==id) {
						orders.add(od);
					}
				}
			}
		}
		return orders;
	}
}
