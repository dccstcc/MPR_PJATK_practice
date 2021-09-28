package repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import repositories.IRepositoryCatalog;
import unitofwork.IUnitOfWork;
import unitofwork.UnitOfWork;

public class RepositoryCatalogProvider {


	private static String url="jdbc:hsqldb:hsql://localhost/workdb";
	
	public static IRepositoryCatalog catalog()
	{

		try {
			Connection connection = DriverManager.getConnection(url);
			IUnitOfWork uow = new UnitOfWork(connection);
			IRepositoryCatalog catalog = new RepositoryCatalog(connection, uow);
		
			return catalog;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
