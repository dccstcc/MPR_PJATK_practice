package unitOfWork.zad4.implement.localDb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;
import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;
import unitOfWork.zad4.repositories.IClientRepository;

public class LocalClientRepository implements IClientRepository{
	
	private LocalDb localDb;

	public LocalClientRepository(LocalDb localDb) {
		super();
		this.localDb = localDb;
	}
	
	public void save(ClientDetails clientDetails) {
		localDb.getClients().add(clientDetails);
	}
	
	public void update(ClientDetails clientDetails) {
		for (ClientDetails cd : localDb.getClients()) {
			if (cd.getId()==clientDetails.getId()) {
				int index = localDb.getClients().indexOf(cd);
				if (index > -1) {
					localDb.getClients().remove(index);
					localDb.getClients().add(cd);
				}
			}
		}
	}
	
	public void delete(ClientDetails clientDetails) {
		for (ClientDetails cd : localDb.getClients()) {
			if (cd.getId()==clientDetails.getId()) {
				int index = localDb.getClients().indexOf(cd);
				if (index > -1) {
					localDb.getClients().remove(index);
				}
			}
		
		}
	}
	
	public ClientDetails get(int id) {
		ClientDetails clientDetails = null;
		for (ClientDetails cd : localDb.getClients()) {
			if(cd.getId()==id) {
				clientDetails = cd;
				break;
			}
		}
		return clientDetails;
	}
	

	public List<ClientDetails> getAll() {
		return localDb.getClients();
	}
	
	public List<ClientDetails> withAddress(String street) {
		List<ClientDetails> clientDetails = new ArrayList<ClientDetails>();
		for(Address ad : localDb.getAddresses()) {
			if(ad.getStreet().equals(street)) {
				int id = ad.getId();
				for(Orders od : localDb.getOrders()) {
					if(od.getDeliveryAddress().getId()==id) {
						clientDetails.add(od.getClient()); 
					}
				}
			}
		
		}
		return clientDetails;
		
	}
	
	public List<ClientDetails> withOrder(int order) {
		List<ClientDetails> clientDetails = new LinkedList<ClientDetails>();
		for(ClientDetails cd : localDb.getClients()) {
			if(cd.getId()==order) {
				clientDetails.add(cd);
			}
				
		}
		return clientDetails;
	}
	
	public List<ClientDetails> withItem(String item) {
		List<ClientDetails> clientDetails = new LinkedList<ClientDetails>();
		for(OrderItem oi : localDb.getItems()) {
			if(oi.getItem().equals(item)) {
				int id = oi.getId();
				for(Orders od : localDb.getOrders()) {
					for(OrderItem oitm : od.getItems())
					if(oitm.getId()==id) {
						clientDetails.add(od.getClient());
					}
				}
			}
		}
		return clientDetails;
	}
}
