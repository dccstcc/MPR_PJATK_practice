package unitOfWork.zad4.repositories;

import java.sql.SQLException;
import java.util.List;

import JDBC.zad2.domain.Orders;

public interface IOrderRepository extends IRepository<Orders>{
	
	public List<Orders> withAddress(String street) throws SQLException;
	public List<Orders> withClient(String name) throws SQLException;
	public List<Orders> withItem(String item) throws SQLException;
}
