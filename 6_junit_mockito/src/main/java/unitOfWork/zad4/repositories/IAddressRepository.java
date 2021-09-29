package unitOfWork.zad4.repositories;

import java.sql.SQLException;
import java.util.List;

import JDBC.zad2.domain.Address;

public interface IAddressRepository extends IRepository<Address>{
	
	public List<Address> withOrder(int order) throws SQLException;
	public List<Address> withClient(String name) throws SQLException;
	public List<Address> withItem(String item) throws SQLException;
}
