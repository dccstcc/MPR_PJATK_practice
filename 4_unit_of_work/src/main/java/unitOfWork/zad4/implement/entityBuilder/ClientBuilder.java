package unitOfWork.zad4.implement.entityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.ClientDetails;

public class ClientBuilder implements IEntityBuilder<ClientDetails>{
	
	public List<ClientDetails> build(ResultSet rs) throws SQLException {
		List<ClientDetails> list = new LinkedList<ClientDetails>();
		while(rs.next()){
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setId(rs.getInt("id"));
		clientDetails.setName(rs.getString("name"));
		clientDetails.setSurname(rs.getString("surname"));
		clientDetails.setLogin(rs.getString("login"));
		list.add(clientDetails);
		}
		return list;

	}

}
