package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import repositories.IRepositoryCatalog;
import repositories.impl.RepositoryCatalog;
import unitofwork.IUnitOfWork;
import unitofwork.UnitOfWork;

@WebServlet("/ShowUsers")
public class ShowUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserServletLogic logic = new UserServletLogic();
    public ShowUsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.getWriter().print(logic.showUsersInhtmlForm());
		
	}
}


























