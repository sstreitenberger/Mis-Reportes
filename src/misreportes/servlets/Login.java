package misreportes.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import misreportes.dao.LoginDAO;
import misreportes.model.Usuario;


/**
 * Servlet implementation class Login.
 * @author Isshink
 *
 */
@WebServlet("/login")
public class Login extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String user = request.getParameter("username");
			String pwd = request.getParameter("userpass");

			if(user.trim().isEmpty() || pwd.trim().isEmpty())
				throw new Exception("Por favor, complete los campos obligatoríos.");
			
			Usuario login = new LoginDAO().validate(user, pwd);

			if (login != null) {
				
				HttpSession session = request.getSession();
				session.setAttribute("user", login);
				session.setMaxInactiveInterval(30 * 60);
				
				System.out.println(login.getNombre() + "|" + login.getApp_nivel() + "|" + login.getMarca());

				response.sendRedirect("dashboard");
				
			} else {
				request.setAttribute("error", "Usuário o contraseña invalida!");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}

		} catch (Exception e) {
			//request.setAttribute("error", e.getMessage());
			request.setAttribute("error", "Usuário o contraseña invalida!");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private static final long serialVersionUID = 1L;

}