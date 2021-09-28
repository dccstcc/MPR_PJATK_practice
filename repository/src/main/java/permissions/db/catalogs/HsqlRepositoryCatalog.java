package permissions.db.catalogs;

import java.sql.Connection;

import permissions.db.PersonRepository;
import permissions.db.RepositoryCatalog;
import permissions.db.repos.HsqlPersonRepository;

public class HsqlRepositoryCatalog implements RepositoryCatalog{

	Connection connection;
	
	public HsqlRepositoryCatalog(Connection connection) {
		this.connection = connection;
	}

	public PersonRepository people() {
		return new HsqlPersonRepository(connection);
	}

}
