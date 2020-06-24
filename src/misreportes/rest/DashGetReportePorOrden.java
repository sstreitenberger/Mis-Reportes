package misreportes.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import misreportes.dao.DashboardDAO;
import misreportes.dao.ReportesDAO;
import misreportes.model.Reporte;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;

/**
 * Servlet implementation class DashGetReportePorOrden
 */
@WebServlet("/DashGetReportePorOrden")

public class DashGetReportePorOrden extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			Usuario u = new ValidarSession().traerSession(request);

			String orden = request.getParameter("orden");
			if (orden == null)
				throw new Exception("Falta el orden del reporte dash.");
			
			DashboardDAO dash = new DashboardDAO();
			Reporte repo = dash.getReporteDash(u.getId(), Integer.valueOf(orden));
			
			if (repo != null){
			
				ReportesDAO rdao = new ReportesDAO();
				List<Map> json = rdao.ejecutarQuery(repo, dash.getTiendasReporteDash( repo.getId() ), true);
								
				List<Map> resList = new ArrayList<Map>();
				Map obj = new LinkedHashMap();
				obj.put("formato", repo.getFormato());
				
				if (!repo.getAlias().isEmpty())
					obj.put("titulo", repo.getAlias());
				else 
					obj.put("titulo", repo.getTitulo());
				
				obj.put("data", json );
				resList.add(obj);
				
				
				response.setStatus(200);
				response.getWriter().append( new Gson().toJson( resList ) );
				response.setContentType("application/json;charset=UTF-8");
				
			} else {
				
				response.setStatus(201);
				response.getWriter().append( "Sin reporte guardado" );
				response.setContentType("application/json;charset=UTF-8");

			}
			
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
