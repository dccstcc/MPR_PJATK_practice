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
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class OrdersService {
	
	
    public static List<Order> findOrdersWhichHaveMoreThan5OrderItems(List<Order> orders) {

    	return orders.stream().filter(s -> s.getItems().size() > 5).collect(Collectors.toList());
    }

   
    
    public static ClientDetails findOldestClientAmongThoseWhoMadeOrders(List<Order> orders) {
 
		return orders.stream().
						map(c -> c.getClientDetails())
						.max((age1, age2) -> Integer.compare(age1.getAge(), age2.getAge()))
						.get();
						
    	
    }
    

    public static Order findOrderWithLongestComments(List<Order> orders) {
    	
    	return orders.stream()
    			.map(c -> c.getComments())
    			.max((cs1, cs2) -> Integer.compare(cs1.length() , cs2.length()))
    			.map(s -> orders.stream()
    								.filter(o -> o.getComments().equals(s)))
    			.get()
    			.findAny()
    			.get();
    					
    			
    	}
    	
    	
    		
    	
    

    public static String getNamesAndSurnamesCommaSeparatedOfAllClientsAbove18YearsOld(List<Order> orders) {
    	
    	return orders.stream()
    					.filter(o -> o.getClientDetails().getAge() > 18)
    					.map(o -> o.getClientDetails().getName() + " " + o.getClientDetails().getSurname())
    				    .collect(Collectors.joining(", "));			
    				
    }

    public static List<String> getSortedOrderItemsNamesOfOrdersWithCommentsStartingWithA(List<Order> orders) {
    	
    	return orders.stream()
    					.filter(o -> o.getComments().startsWith("A"))
    					.flatMap(o -> o.getItems().stream())
    					.map(i -> i.getName())
    					.distinct()
    					.sorted()
    					.collect(Collectors.toList());
    			
    }

    public static void printCapitalizedClientsLoginsWhoHasNameStartingWithS(List<Order> orders) {
    		
    	orders.stream()
    				.filter(o -> o.getClientDetails().getName().startsWith("S"))
    				.map(o -> o.getClientDetails().getLogin())
    				.distinct()
    				.forEach(System.out::println);
    }

    public static Map<ClientDetails, List<Order>> groupOrdersByClient(List<Order> orders) {
    			
    	return orders.stream()
    				.collect(Collectors.groupingBy(Order::getClientDetails, Collectors.mapping(Function.identity(), Collectors.toList())));
    
    							
    }

    public static Map<Boolean, List<ClientDetails>> partitionClientsByUnderAndOver18(List<Order> orders) {
        	
    	return	orders.stream()
    					.map( (c) -> c.getClientDetails())
    					.collect(Collectors.partitioningBy( (ClientDetails m) -> m.getAge() > 18));
    }
    
   

}
