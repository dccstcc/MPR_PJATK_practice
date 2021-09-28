package repositories.impl;

import java.util.List;

import domain.Role;
import repositories.IRepository;

public class DummyRoleRepository implements IRepository<Role>{

	DummyDb db;
	
	public DummyRoleRepository(DummyDb db) {
	
	}

	
	public void save(Role entity) {
		
		db.roles.add(entity);
	}

	@Override
	public void update(Role entity) {
		
	}

	@Override
	public void delete(Role entity) {
		db.roles.remove(entity);
		
	}

	@Override
	public Role get(int id) {
		for(Role r : db.roles)
			if(r.getId()==id)
				return r;
		return null;
	}

	@Override
	public List<Role> getAll() {

		return db.roles;
	}

}
