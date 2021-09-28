package permissions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.hsqldb.lib.HsqlArrayHeap;

import permissions.db.PagingInfo;
import permissions.db.PersonDbManager;
import permissions.db.RepositoryCatalog;
import permissions.db.catalogs.HsqlRepositoryCatalog;
import permissions.domain.Person;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    	String url = "jdbc:hsqldb:hsql://localhost/workdb";
		try(Connection connection = DriverManager.getConnection(url)) 
		{
		
	    	RepositoryCatalog catalogOf = new HsqlRepositoryCatalog(connection);  	
	    	
	    	catalogOf.people().withName("jan", new PagingInfo());
	    	
	    	
	        System.out.println( "Hello World!" );
	        
	        PersonDbManager mgr = new PersonDbManager();
	        
	        List<Person> allPersons = mgr.getAll();
	        
	        for(Person p : allPersons){
	        	System.out.println(p.getId()+" "+p.getName()+" "+p.getSurname());
	        }
	        mgr.deleteById(3);
	        
	        Person toUpdate = new Person();
	        
	        toUpdate.setName("Bolesław");
	        toUpdate.setSurname("Prus");
	        toUpdate.setId(2);
	        
	        mgr.update(toUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
