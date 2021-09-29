package Repository.zad3.catalogs;

import java.sql.Connection;

import Repository.zad3.db.OrderRepository;
import Repository.zad3.db.RepositoryCatalog;
import Repository.zad3.repos.HsqlOrderRepository;


public class HsqlRepositoryCatalog implements RepositoryCatalog{

	Connection connection;
	
	public HsqlRepositoryCatalog(Connection connection) {
		this.connection = connection;
	}

	public OrderRepository orders() {
		return new HsqlOrderRepository(connection);
	}

}
