package misreportes.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import misreportes.dao.QuerysDAO;
import misreportes.dao.ReportesDAO;
import misreportes.model.Rango;
import misreportes.model.Reporte;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;

@WebServlet("/GetReportes")
public class GetReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			Usuario u = new ValidarSession().traerSession(request);
			if (u == null)
				throw new Exception("Session expirada");

			String desde = request.getParameter("desde");
			String hasta = request.getParameter("hasta");
			String reporte = request.getParameter("reporte");
			String[] tiendas = request.getParameterValues("tiendas[]");
			Boolean top = Boolean.valueOf(request.getParameter("top"));
			
			if (tiendas == null){
				tiendas = request.getParameterValues("tienda");
			}

			Reporte reporteQuery = new QuerysDAO().GetQuery(reporte);
			
			if (desde != null && hasta != null){
				Rango rango = new Rango();
				rango.setDesde("'"+desde+"'");
				rango.setHasta("'"+hasta+"'");
				reporteQuery.setRango(rango);
			}

			List<Map> json = new ReportesDAO().ejecutarQuery(reporteQuery, tiendas, top);

			if (reporteQuery.getAplic_graf())
				response.setStatus(200); // con grafico
			else
				response.setStatus(201); // sin grafico

			response.getWriter().append( new Gson().toJson( json ) );
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
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}