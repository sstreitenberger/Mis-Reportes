package misreportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;

import misreportes.model.Usuario;
import misreportes.utiles.Info;

/**
 *
 * @author sdesouza
 */
public class LoginDAO {

	
	/**
	 * Los parametros requeridos (name, pass) por el metodo Validate son enviados desde el metodo doPost() en la clase Login.
	 * retorna una clase usuario
	 * Hace uso de la Db Alsea para buscar al usuario y corroborar si tiene permiso de entrada a la aplicacion.
	 * De tenerlo y haciendo uso de metodo ValidateDomain() prepara un usuario
	 * como estupula el modelo de la clase Usuario, para hacer tambien uso mas adelante.
	 * @param name 
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public Usuario validate(String name, String pass) throws Exception {
		
		Usuario resp = new Usuario();
		Connection conn = new Conexion().conexionBksrv4();
		// Cambiar statement para chile y revisar que campos son los usados por ellos
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Alsea.dbo.Usuarios_aplicaciones WHERE nombre = ? AND app_id = ?");
		pst.setString(1, name);
		pst.setString(2, Info.ID_APP);

		ResultSet rs = pst.executeQuery();

		if (rs.next()) {
			if (validateDomain(name, pass)) {
				resp.setId(rs.getInt("id"));
				resp.setNombre(rs.getString("nombre"));
				resp.setMarca(rs.getString("marca"));
				resp.setApp_nivel(rs.getString("app_nivel"));
				return resp;
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}

	private boolean validateDomain(String name, String pass) throws NamingException {

		Hashtable<String, String> props = new Hashtable<String, String>();

		props.put(Context.SECURITY_PRINCIPAL, name + "@alsea-netodp.local");
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.PROVIDER_URL, "ldap://172.31.1.16/");
		props.put(Context.SECURITY_CREDENTIALS, pass);
		

		InitialLdapContext ldap = new InitialLdapContext(props, null);

		return (ldap != null);
	}
}
