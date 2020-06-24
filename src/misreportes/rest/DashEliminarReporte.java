package misreportes.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misreportes.dao.DashboardDAO;
import misreportes.utiles.ValidarSession;

/**
 * Servlet implementation class DashEditarReporte
 */
@WebServlet("/DashEliminarReporte")
public class DashEliminarReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			new ValidarSession().traerSession(request);

			String dash_id = request.getParameter("dash_id");
			
			if (!new DashboardDAO().borrarDash(dash_id))
				throw new Exception("Error al borrar reporte!");
			
			response.setStatus(200);
			response.getWriter().append("Reporte borrado con exito");
			response.setContentType("application/json;charset=UTF-8");
			
		} catch (SecurityException e) {
			response.setStatus(401); // Error
			response.getWriter().append(e.getMessage());
			response.setContentType("application/json;charset=UTF-8");
		} catch (Exception e) {
			response.setStatus(500); // Error
			response.getWriter().append(e.getMessage());
			response.setContentType("application/json;charset=UTF-8");
		} 
	}

}
