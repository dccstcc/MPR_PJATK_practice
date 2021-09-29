package unitOfWork.zad4.implement.entityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import JDBC.zad2.domain.OrderItem;

public class ItemBuilder implements IEntityBuilder<OrderItem>{

	public List<OrderItem> build(ResultSet rs) throws SQLException {
			
		List <OrderItem> list = new LinkedList<OrderItem>();
		while(rs.next()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setId(rs.getInt("id"));
			orderItem.setItem(rs.getString("item"));
			orderItem.setDescription(rs.getString("description"));
			orderItem.setPrice(rs.getDouble("price"));
			list.add(orderItem);
		}
			return list;
	}

}
