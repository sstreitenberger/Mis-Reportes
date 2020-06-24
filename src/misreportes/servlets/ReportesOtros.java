package misreportes.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misreportes.dao.QuerysDAO;
import misreportes.dao.TiendasDAO;
import misreportes.model.Usuario;
import misreportes.utiles.Info;
import misreportes.utiles.ValidarSession;

/**
 * Servlet implementation class MisReportes
 */
@WebServlet("/reportes_otros")
public class ReportesOtros extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			Usuario u = new ValidarSession().traerSession(request);

			request.setAttribute("menulist", new QuerysDAO().getListado(u,"otros"));
			request.setAttribute("abmRepoTipo", "otros");
			
			if (u.getApp_nivel().equalsIgnoreCase(Info.PERMISOS_STORE_MANAGER)) {	
				request.setAttribute("tienda", new TiendasDAO().getTiendaPorLocal(u));
			}
			request.getRequestDispatcher("/reportes_otros.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
