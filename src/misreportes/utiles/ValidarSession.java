package misreportes.utiles;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import misreportes.model.Usuario;

public class ValidarSession {

	
	/**
	 * Request de usuario en sesion
	 * @param request
	 * @return
	 * @throws SecurityException
	 */
	public Usuario traerSession(HttpServletRequest request) throws SecurityException{
		HttpSession session = request.getSession(false);
		if (session != null) {
			Usuario u = (Usuario) session.getAttribute("user");
			if (u != null) {
				return u;
			} else {
				throw new SecurityException("Session expirada");
			}
		} else {
			throw new SecurityException("Session expirada");
		}
	}
}
