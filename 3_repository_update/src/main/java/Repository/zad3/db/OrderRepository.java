package Repository.zad3.db;

import java.util.List;

import JDBC.zad2.domain.ClientDetails;
import JDBC.zad2.domain.OrderItem;
import JDBC.zad2.domain.Orders;

public interface OrderRepository extends Repository<Orders, List<OrderItem>>{

		 public List<ClientDetails> withItem(String item, PagingInfo pagingInfo);
		 public List<OrderItem> withClient(String login, PagingInfo pagingInfo);
		 public void viewOrderSummary (int id);
}
