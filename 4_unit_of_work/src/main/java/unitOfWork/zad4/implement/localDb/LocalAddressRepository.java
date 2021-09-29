package unitOfWork.zad4.implement.localDb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import unitOfWork.zad4.repositories.IAddressRepository;

public class LocalAddressRepository implements IAddressRepository{

	private LocalDb localDb;

	public LocalAddressRepository(LocalDb localDb) {
		super();
		this.localDb = localDb;
	}
	
	public void save(Address address) {
		localDb.getAddresses().add(address);
	}
	
	public void update(Address address) {
		for (Address ad : localDb.getAddresses()) {
			if (ad.getId()==address.getId()) {
				int index = localDb.getAddresses().indexOf(ad);
				if (index > -1) {
					localDb.getAddresses().remove(index);
					localDb.getAddresses().add(ad);
				}
			}
		}
	}
	
	public void delete(Address address) {
		for (Address ad : localDb.getAddresses()) {
			if (ad.getId()==address.getId()) {
				int index = localDb.getAddresses().indexOf(ad);
				if (index > -1) {
					localDb.getAddresses().remove(index);
				}
			}
		
		}
	}
	
	public Address get(int id) {
		Address address = null;
		for (Address ad : localDb.getAddresses()) {
			if(ad.getId()==id) {
				address = ad;
				break;
			}
		}
		return address;
	}
	

	public List<Address> getAll() {
		return localDb.getAddresses();
	}
	
	public List<Address> withClient(String name) {
		List<Address> address = new ArrayList<Address>();
		for(ClientDetails cd : localDb.getClients()) {
			if(cd.getName().equals(name)) {
				int id = cd.getId();
				for(Orders od : localDb.getOrders()) {
					if(od.getClient().getId()==id) {
						address.add(od.getDeliveryAddress()); 
					}
				}
			}
		
		}
		return address;
		
	}
	
	public List<Address> withOrder(int order) {
		List<Address> address = new LinkedList<Address>();
		for(Address ad : localDb.getAddresses()) {
			if(ad.getId()==order) {
				address.add(ad);
			}
				
		}
		return address;
	}
	
	public List<Address> withItem(String item) {
		List<Address> address = new LinkedList<Address>();
		for(OrderItem oi : localDb.getItems()) {
			if(oi.getItem().equals(item)) {
				int id = oi.getId();
				for(Orders od : localDb.getOrders()) {
					for(OrderItem oitm : od.getItems())
					if(oitm.getId()==id) {
						address.add(od.getDeliveryAddress());
					}
				}
			}
		}
		return address;
	}
}
