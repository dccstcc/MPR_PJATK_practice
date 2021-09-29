package unitOfWork.zad4.implement.entityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import JDBC.zad2.domain.Entity;



public interface IEntityBuilder<TEntity extends Entity> {

	public List<TEntity> build(ResultSet rs) throws SQLException;
	
}
