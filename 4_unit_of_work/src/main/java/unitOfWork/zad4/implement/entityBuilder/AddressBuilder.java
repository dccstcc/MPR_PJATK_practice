package unitOfWork.zad4.implement.entityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.Address;

public class AddressBuilder implements IEntityBuilder<Address>{
	
	public List<Address> build(ResultSet rs) throws SQLException {
		
		List <Address> list = new LinkedList<Address>();
		while(rs.next()) {
		Address address = new Address();
		address.setId(rs.getInt("id"));
		address.setBuilgingNumber(rs.getString("buildingNumber"));
		address.setFlatNumber(rs.getString("flatNumber"));
		address.setPostalCode(rs.getString("postalCode"));
		address.setCity(rs.getString("city"));
		address.setCountry(rs.getString("country"));
		list.add(address);
		}
		return list;
	}

}
