package pl.edu.pjatk.mpr.lab5.service;

import pl.edu.pjatk.mpr.lab5.model.ClientDetails;
import pl.edu.pjatk.mpr.lab5.model.Order;
import pl.edu.pjatk.mpr.lab5.model.OrderItem;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class OrdersService {
	
	
    public static List<Order> findOrdersWhichHaveMoreThan5OrderItems(List<Order> orders) {

    	return orders.stream().filter(s -> s.getItems().size() > 5).collect(Collectors.toList());
    }

    public static ClientDetails findOldestClientAmongThoseWhoMadeOrders(List<Order> orders) {
 
		List<ClientDetails> clientList = new LinkedList<ClientDetails>(); 
    	
		orders.stream().forEach(o -> {
    		clientList.add(o.getClientDetails());
    	}); 
    	
    	return clientList.stream()
    		.max(Comparator.comparing(ClientDetails::getAge)).get();
    	
    }
    

    public static Order findOrderWithLongestComments(List<Order> orders) {
    	
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	
    	orders.stream()
    		.forEach(o -> {
    			map.put(o.getComments().length(), o.getComments());
    			});

    	Object [] a;
    	Set<Integer> keys = map.keySet();
    	a = keys.toArray();
    	
    	int val=0;
    	for(int i=0; i<a.length; i++) {
    		if( (int) a[i] > val) {
    			val = (int) a[i];
    		}
    	}
    	
    	for(Order order : orders) {
    		if(order.getComments().equals(map.get(val))){
    			return order;
    		}
    	}
    	
    	return null;
    		
    	
    }

    public static String getNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld(List<Order> orders) {
    	
    	List<ClientDetails> clientList = new LinkedList<ClientDetails>(); 
    	
		orders.stream().forEach(o -> {
    		clientList.add(o.getClientDetails());
    	}); 
		
		String result;
		
		result = clientList.stream()
				.filter(c ->
					c.getAge()>18)
				.map(c -> 
					c.getName() + " " + c.getSurname())
				.collect(Collectors.joining(", ", "imiona i nazwiska osob pelnoletnich: ", ". #EOF"));
		return result;		
			
    }

    public static List<String> getSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA(List<Order> orders) {
    	
    	List<Order> list = new LinkedList<Order>();
    	
    	
    			list = orders.stream()
    				.filter(c -> c.getComments().startsWith("A"))
    				.collect(Collectors.toList());

    			List<OrderItem>itemList = new LinkedList<OrderItem>();
    			list.forEach((li) -> itemList.addAll(li.getItems()));
    			
    			List<OrderItem> itemListClone = itemList;

    			Iterator<OrderItem> itemListCloneIterable = itemListClone.iterator();
    			itemListCloneIterable.next();

    			List<OrderItem> objectsToRemove = new LinkedList<OrderItem>();
    			
    			itemList.forEach( (il) -> { if(itemListCloneIterable.hasNext()) {
    				if(il.getName().equals(itemListCloneIterable.next().getName())) {
    					objectsToRemove.add(il);
    				}
    			};
    		});
    			
    			objectsToRemove.stream().forEach( (i) -> itemList.remove(i));
    			
    			itemList.sort((x,y) -> x.getName().compareTo((y.getName())));
    			
    		return	itemList.stream()
    						.map((il) -> il.getName())
    						.collect(Collectors.toList());
    			
    }

    public static void printCapitalizedClientsLoginsWhoHasNameStartingWithS(List<Order> orders) {
    		
    	List<ClientDetails> clients = orders.stream()
    											.map(c -> c.getClientDetails())
    											.collect(Collectors.toList());
    	
    	clients.stream()
    				.filter( (c) -> c.getName().startsWith("S"))
    				.map( (l) -> l.getLogin())
    				.forEach(System.out::println);
    }

    public static Map<ClientDetails, List<Order>> groupOrdersByClient(List<Order> orders) {
    			Map<ClientDetails, List<Order>> hashMap = new HashMap<ClientDetails, List<Order>>();
    			
    			orders.stream().forEach( (i) -> {
    			
    			hashMap.put(
    						(orders.stream()
    									.map( (c) -> c.getClientDetails())
    									.collect(Collectors.toList())).get(0)
    											,
    						orders.stream()
    									.collect(Collectors.toList())    										

    						);
    			});
    			return hashMap;
    							
    }

    public static Map<Boolean, List<ClientDetails>> partitionClientsByUnderAndOver18(List<Order> orders) {
        	
    	return	orders.stream()
    					.map( (c) -> c.getClientDetails())
    					.collect(Collectors.partitioningBy( (ClientDetails m) -> m.getAge() > 18));
    }
    
   

}
