package permissions.db;

import java.util.List;

import permissions.domain.Person;

public interface PersonRepository extends Repository<Person> {
	
	public List<Person> withSurname(String surname, PagingInfo page);
	public List<Person> withName(String name, PagingInfo page);
}
