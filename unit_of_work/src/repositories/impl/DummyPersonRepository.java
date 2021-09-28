package repositories.impl;

import java.util.List;

import domain.Person;
import repositories.IRepository;

public class DummyPersonRepository implements IRepository<Person>{

	private DummyDb db;
	
	public DummyPersonRepository(DummyDb db) {
		this.db = db;
	}

	@Override
	public void save(Person entity) {

		db.persons.add(entity);
		
	}

	@Override
	public void update(Person entity) {
		
	}

	@Override
	public void delete(Person entity) {

		db.persons.remove(entity);
		
	}

	@Override
	public Person get(int id) {

		for(Person p: db.persons)
			if(p.getId()==id)
				return p;
		return null;
	}

	@Override
	public List<Person> getAll() {
		return db.persons;
	}

}
