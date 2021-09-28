package repositories;

import domain.*;

public interface IRepositoryCatalog {

	public IUserRepository getUsers();
	public IRepository<Person> getPersons();
	public IRepository<Role> getRoles();
	public void commit();
}
