package misreportes.rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import misreportes.dao.QuerysDAO;
import misreportes.model.Reporte;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;

/**
 * Servlet implementation class DashGetTiendas
 */
@WebServlet("/DashGetReportes")
public class DashGetReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Usuario u = new ValidarSession().traerSession(request);
			
			String tipo = request.getParameter("dash_tipo");
			
			ArrayList<Reporte> aq = new QuerysDAO().traerReportesPorUsuario(u,tipo);
			String json = new Gson().toJson( aq );
			
			response.setStatus(200);
			response.getWriter().append( json );
			response.setContentType("application/json;charset=UTF-8");

		} catch (Exception e) {
			response.setStatus(500); // Error
			response.getWriter().append(e.getMessage());
		}	
		
	}

	
}
