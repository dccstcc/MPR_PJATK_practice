package servlets;

import javax.servlet.http.HttpServletRequest;
import domain.User;
import repositories.IRepositoryCatalog;
import repositories.impl.RepositoryCatalogProvider;

public class UserServletLogic {

	IRepositoryCatalog catalog;
	
	public UserServletLogic() {
		catalog = RepositoryCatalogProvider.catalog();
	}
	
	public void addNewUser(HttpServletRequest request)
	{
		User u = new User();
		u.setLogin(request.getParameter("login"));
		u.setPassword(request.getParameter("password"));
		catalog.getUsers().save(u);
		catalog.commit();
	}
	
	public String showUsersInhtmlForm()
	{
		String html = "<ol>";
		for(User u: catalog.getUsers().getAll())
		{
			html+="<li>"
					+ u.getLogin()
					+ "</li>";
		}
		html+="</ol>";
		return html;
	}
}





















