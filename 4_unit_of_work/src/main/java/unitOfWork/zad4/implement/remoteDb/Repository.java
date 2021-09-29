package unitOfWork.zad4.implement.remoteDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.zad2.domain.Entity;
import unitOfWork.zad4.implement.entityBuilder.IEntityBuilder;
import unitOfWork.zad4.repositories.IRepository;
import unitOfWork.zad4.unitOfWork.IUnitOfWork;
import unitOfWork.zad4.unitOfWork.IUnitOfWorkRepository;


public abstract class Repository<TEntity extends Entity> 
	implements IRepository<TEntity>, IUnitOfWorkRepository
{
	
	protected IUnitOfWork uow;
	protected Connection connection;
	protected PreparedStatement selectByID;
	protected PreparedStatement insert;
	protected PreparedStatement delete;
	protected PreparedStatement update;
	protected PreparedStatement selectAll;
	protected IEntityBuilder<TEntity> builder;

	protected String selectByIDSql=
			"SELECT * FROM "
			+ getTableName()
			+ " WHERE id=?";
	protected String deleteSql=
			"DELETE FROM "
			+ getTableName()
			+ " WHERE id=?";
	protected String selectAllSql=
			"SELECT * FROM " + getTableName();
	
	protected Repository(Connection connection,
			IEntityBuilder<TEntity> builder, IUnitOfWork uow)
	{
		this.uow=uow;
		this.builder=builder;
		this.connection = connection;
		try {
			selectByID=connection.prepareStatement(selectByIDSql);
			insert = connection.prepareStatement(getInsertQuery());
			delete = connection.prepareStatement(deleteSql);
			update = connection.prepareStatement(getUpdateQuery());
			selectAll = connection.prepareStatement(selectAllSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void save(TEntity entity)
	{
		uow.markAsNew(entity, this);
	}

	
	public void update(TEntity entity)
	{
		uow.markAsDirty(entity, this);
	}

	
	public void delete(TEntity entity)
	{
		uow.markAsDeleted(entity, this);
	}

	
	public void persistAdd(Entity entity) {

		try {
			setUpInsertQuery((TEntity)entity);
			insert.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public void persistUpdate(Entity entity) {

		try {
			setUpUpdateQuery((TEntity)entity);
			update.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void persistDelete(Entity entity) {

		try {
			delete.setInt(1, entity.getId());
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public TEntity get(int id) {

		try {
			selectByID.setInt(1, id);
			ResultSet rs = selectByID.executeQuery();
			while(rs.next())
			{
				return builder.build(rs).get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}



	
	public List<TEntity> getAll() {
		
		try {
			ResultSet rs= selectAll.executeQuery();
			while(rs.next())
			{
				return builder.build(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	protected abstract void setUpUpdateQuery(TEntity entity) throws SQLException;
	protected abstract void setUpInsertQuery(TEntity entity) throws SQLException;
	protected abstract String getTableName();
	protected abstract String getUpdateQuery();
	protected abstract String getInsertQuery();
}
