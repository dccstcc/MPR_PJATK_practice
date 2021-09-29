package unitOfWork.zad4.unitOfWork;

import JDBC.zad2.domain.Entity;

public interface IUnitOfWorkRepository {

	public void persistAdd(Entity entity);
	public void persistUpdate(Entity entity);
	public void persistDelete(Entity entity);
}
