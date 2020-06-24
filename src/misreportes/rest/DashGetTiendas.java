package misreportes.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import misreportes.dao.TiendasDAO;
import misreportes.model.Usuario;
import misreportes.utiles.ValidarSession;

/**
 * Servlet implementation class DashGetTiendas
 */
@WebServlet("/DashGetTiendas")
public class DashGetTiendas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			Usuario u = new ValidarSession().traerSession(request);
			String json;

			if (u.getApp_nivel().equals("10")) {
				json = new Gson().toJson(new TiendasDAO().getTiendaPorLocal(u));
			} else {
				json = new Gson().toJson(new TiendasDAO().getTiendas(u));
			}

			response.setStatus(200);
			response.getWriter().append(json);
			response.setContentType("application/json;charset=UTF-8");

		} catch (Exception e) {
			response.setStatus(500); // Error
			response.getWriter().append(e.getMessage());
		}

	}

}
