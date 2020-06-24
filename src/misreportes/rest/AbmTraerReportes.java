package misreportes.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import misreportes.dao.QuerysDAO;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;


@WebServlet("/AbmTraerReportes")
public class AbmTraerReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Usuario u = new ValidarSession().traerSession(request);
			
			String tipo = request.getParameter("abmtipoRepo");

			QuerysDAO qd = new QuerysDAO();
			String json = new Gson().toJson(qd.traerReportesPorUsuario(u,tipo));
			
			response.setStatus(200);
			response.getWriter().append( json );

		} catch (Exception e) {
			response.setStatus(500); // Error
			response.getWriter().append(e.getMessage());
		}	
	}
}
