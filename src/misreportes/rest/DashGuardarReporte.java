package misreportes.rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misreportes.dao.DashboardDAO;
import misreportes.model.Rango;
import misreportes.model.Reporte;
import misreportes.model.Tienda;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;

/**
 * Servlet implementation class DashGetTiendas
 */
@WebServlet("/DashGuardarReporte")
public class DashGuardarReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			Usuario u = new ValidarSession().traerSession(request);

			String dash_tipo = request.getParameter("dash_tipo");
			String dash_reporte = request.getParameter("dash_reporte");
			String dash_orden = request.getParameter("dash_orden");
			String dash_rango = request.getParameter("dash_rango");
 			String[] dash_tiendas = request.getParameterValues("dash_tiendas");
			String dash_formato = request.getParameter("dash_formato");
			String dash_alias = request.getParameter("dash_alias");
			
			Reporte rd = new Reporte();
			rd.setUsuario_id( u.getId() );
			rd.setTipo( dash_tipo );
			if (dash_reporte != null){
				rd.setReporte_id( Integer.valueOf(dash_reporte) );
			}
			if (dash_orden != null){
				rd.setOrden( Integer.valueOf(dash_orden) );
			}
			if (dash_rango != null){
				Rango r = new Rango();
				r.setId(Integer.valueOf(dash_rango));
				rd.setRango( r );
			}
			if (dash_tiendas != null){
				ArrayList<Tienda> at = new ArrayList<Tienda>();
				for (String ip : dash_tiendas){
					Tienda tt = new Tienda();
					tt.setIp(ip);
					at.add( tt );
				}
				rd.setTiendas(at);
			}
			
			rd.setFormato(dash_formato);
			rd.setAlias(dash_alias);
			
			if (!new DashboardDAO().nuevoDash(rd))
				throw new Exception("Error al grabar reporte!");
			
			response.setStatus(200);
			response.getWriter().append("Reporte creado con exito");
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
