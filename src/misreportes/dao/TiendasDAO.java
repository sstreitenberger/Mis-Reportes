package misreportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import misreportes.model.Tienda;
import misreportes.model.Usuario;

public class TiendasDAO {

	/**
	 * El metodo extrae del usuario logueado la marca a la que pertenece y prepara la query de ejecucion
	 * dependiendo de esta.
	 * el caso "Alsea" es el unico que tendra a su disposicion todas la marcas anteriores.
	 * @param u
	 * @return
	 * @throws Exception
	 */
	private ResultSet getTiendas_Marca_Distrito(Usuario u) throws Exception {

		PreparedStatement pst = null;
		Connection conn = new Conexion().conexionBksrv4();
		// revisar que tablas usa chile y como las usa
		switch(u.getMarca()){
		
		case "BK":
			pst = conn.prepareStatement(
					"SELECT D.distrito AS ndist, 'Distrito-'+ CAST(D.distrito AS VARCHAR) AS distrito, T.nombre, T.ip "
					+ "FROM [Alsea].[DBO].[Usuarios_aplicaciones] AS A "
					+ "INNER JOIN micros.dbo.bkmaildistritos AS D ON A.email = D.email "
					+ "INNER JOIN micros.dbo.bktiendas AS T ON T.distrito = D.distrito WHERE A.nombre = ? "
					+ "UNION ALL SELECT * FROM ( "
					+ "SELECT TOP 10000 D.distrito AS ndist, 'Distrito-'+ CAST(D.distrito AS VARCHAR) AS distrito, T.nombre, T.ip "
					+ "FROM [Alsea].[DBO].[Usuarios_aplicaciones] AS A "
					+ "INNER JOIN micros.dbo.bkmaildistritos AS D ON A.email = D.email "
					+ "INNER JOIN micros.dbo.bktiendas AS T ON T.distrito = D.distrito "
					+ "WHERE A.nombre != ? ORDER BY D.distrito) AS F");
			pst.setString(1, u.getNombre());
			pst.setString(2, u.getNombre());
			break;
			
		case "SBX":
			pst = conn.prepareStatement(
					"SELECT D.distrito AS ndist, 'Distrito-'+ CAST(D.distrito AS VARCHAR) AS distrito, T.nombre, T.ip "
					+ "FROM [Alsea].[DBO].[Usuarios_aplicaciones] AS A "
					+ "INNER JOIN micros.dbo.sbxmaildistritos AS D ON A.email = D.email "
					+ "INNER JOIN micros.dbo.sbxtiendas AS T ON T.distrito = D.distrito "
					+ "WHERE A.nombre = ? UNION ALL SELECT * FROM ( "
					+ "SELECT TOP 10000 D.distrito AS ndist, 'Distrito-'+ CAST(D.distrito AS VARCHAR) AS distrito, T.nombre, T.ip "
					+ "FROM [Alsea].[DBO].[Usuarios_aplicaciones] AS A "
					+ "INNER JOIN micros.dbo.sbxmaildistritos AS D ON A.email = D.email "
					+ "INNER JOIN micros.dbo.sbxtiendas AS T ON T.distrito = D.distrito "
					+ "WHERE A.nombre != ? ORDER BY D.distrito) AS F");
			pst.setString(1, u.getNombre());
			pst.setString(2, u.getNombre());
			break;
		
		case "Alsea":
			pst = conn.prepareStatement(
				"SELECT 'BurgerKing' as marcas, 'BurgerKing' as marca,'BK-'+nombre as nombre,ip "
				+ "FROM micros.dbo.bktiendas where activa = 1 "
				+ "UNION ALL SELECT 'Starbucks' as marcas, 'Starbucks' as marca,'SBX-'+nombre as nombre,ip "
				+ "FROM micros.dbo.sbxtiendas where activa = 1 order by marca,nombre");
			break;
			
		default:
			throw new Exception("Marca no implementada!");
			
		}

		return pst.executeQuery();
	}

	
	/**
	 * El metodo hace uso de getTiendas_Marca_Distrito() para saber a que marca pertenece el usuario
	 * y posteriormente arma una lista de tiendas (clase misreportes.model.Tienda)
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArrayList<Tienda>> getTiendas(Usuario u) throws Exception {
		ArrayList<ArrayList<Tienda>> list = new ArrayList<ArrayList<Tienda>>();
		ArrayList<Tienda> dist = null;

		ResultSet rs = getTiendas_Marca_Distrito(u);
		String corte = "";
		while (rs.next()) {
			
			if (!corte.equals(rs.getString(2))) {
				corte = rs.getString(2);
				if (dist != null && !dist.isEmpty())
					list.add(dist);
				dist = new ArrayList<Tienda>();
			}

			Tienda reg = new Tienda();
			reg.setGrupo(rs.getString(2)); // distrito o marca
			reg.setNombre(rs.getString("nombre"));
			reg.setIp(rs.getString("ip"));
			dist.add(reg);

		}
		if (dist != null && !dist.isEmpty())
			list.add(dist);

		return list;
	}

	
	/**
	 * El metodo construye y devuelve una instancia de la clase Tienda a base de la infomracion suministrada
	 * por el usuario.
	 * En este caso usuario de tienda (app_nivel = 10)
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Tienda getTiendaPorLocal(Usuario u) throws Exception{
		String query = "SELECT distrito, marca+'-'+t.nombre 'tienda', ip FROM Alsea.dbo.Usuarios_aplicaciones A "
				+ "LEFT JOIN micros.dbo."+u.getMarca()+"tiendas T ON A.email = T.email "
				+ "WHERE A.nombre = ? AND app_id = 2 AND app_nivel = 10"; // el nivel 10 quiere decir Tienda
		
		Connection conn = new Conexion().conexionBksrv4();
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, u.getNombre());
		
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			Tienda t= new Tienda();
			t.setGrupo(rs.getString("distrito"));
			t.setIp(rs.getString("ip"));
			t.setNombre(rs.getString("tienda"));
			return t;
		} else { 
			return null;
		}
	}

}
