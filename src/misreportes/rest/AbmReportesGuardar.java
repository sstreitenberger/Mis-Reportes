package misreportes.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misreportes.dao.QuerysDAO;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;

@WebServlet("/AbmReportesGuardar")
public class AbmReportesGuardar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Usuario u = new ValidarSession().traerSession(request);

			String[] multiple = request.getParameterValues("multiple");
			String tipo = request.getParameter("abmtipoRepo");

			QuerysDAO qd = new QuerysDAO();
			qd.borrarReportesPorUsuario(u, tipo);
			if (multiple != null)
				qd.guardarReportesPorUsuario(multiple, u);

			response.setStatus(200);

		} catch (SecurityException e) {

			response.setStatus(401); // Error login
			response.getWriter().append(e.getMessage());
			response.setContentType("application/json;charset=UTF-8");

		} catch (Exception e) {

			response.setStatus(500); // Error
			response.getWriter().append(e.getMessage());
			response.setContentType("application/json;charset=UTF-8");

		}
	}
}
