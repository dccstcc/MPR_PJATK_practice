package unitOfWork.zad4.repositories;

import java.sql.SQLException;
import java.util.List;

import JDBC.zad2.domain.OrderItem;

public interface IItemRepository extends IRepository<OrderItem>{
	
	public List<OrderItem> withAddress(String street) throws SQLException;
	public List<OrderItem> withClient(String name) throws SQLException;
	public List<OrderItem> withOrder(int order) throws SQLException;
}
