package unitOfWork.zad4.unitOfWork;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import JDBC.zad2.domain.Entity;
import JDBC.zad2.domain.EntityState;

public class UnitOfWork implements IUnitOfWork{

	
	private Connection connection;
	
	private Map<Entity, IUnitOfWorkRepository> entities = 
			new LinkedHashMap<Entity, IUnitOfWorkRepository>();
	
	public UnitOfWork(Connection connection) {
		super();
		this.connection = connection;
		
		try {
			connection.setAutoCommit(false);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void commit() {

		for(Entity entity: entities.keySet())
		{
			switch(entity.getState())
			{
			case Changed:
				entities.get(entity).persistUpdate(entity);
				break;
			case Deleted:
				entities.get(entity).persistDelete(entity);
				break;
			case New:
				entities.get(entity).persistAdd(entity);
				break;
			case Unchanged:
				break;
			default:
				break;}
		}
		
		try {
			connection.commit();
			entities.clear();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public void rollback() {

		entities.clear();
		
	}

	public void markAsNew(Entity entity, IUnitOfWorkRepository repository) {
		entity.setState(EntityState.New);
		entities.put(entity, repository);
		
	}

	public void markAsDirty(Entity entity, IUnitOfWorkRepository repository) {
		entity.setState(EntityState.Changed);
		entities.put(entity, repository);
		
	}

	public void markAsDeleted(Entity entity, IUnitOfWorkRepository repository) {
		entity.setState(EntityState.Deleted);
		entities.put(entity, repository);
		
	}

}
