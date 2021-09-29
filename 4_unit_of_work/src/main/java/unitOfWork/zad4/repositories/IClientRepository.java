package unitOfWork.zad4.repositories;

import java.sql.SQLException;
import java.util.List;

import JDBC.zad2.domain.ClientDetails;

public interface IClientRepository extends IRepository <ClientDetails>{
	
	public List<ClientDetails> withOrder(int order) throws SQLException;
	public List<ClientDetails> withAddress(String street) throws SQLException;
	public List<ClientDetails> withItem(String item) throws SQLException;
}
