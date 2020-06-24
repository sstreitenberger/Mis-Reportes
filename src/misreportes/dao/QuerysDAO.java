package misreportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import misreportes.model.Reporte;
import misreportes.model.Usuario;

public class QuerysDAO {

	/**
	 * El metodo hace uso de la DB Alsea en el bksr4 para elegir el tipo de reporte buscado por el usuario,
	 * de donde extraera la respectiva Query y demas informacion para crear una instancia de la clase Reporte
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Reporte GetQuery(String id) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		String query = "SELECT * FROM Alsea.dbo.misreportes_reportes where id = ?";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			Reporte resp = new Reporte();
			resp.setQuery(rs.getString("query"));
			resp.setAplic_graf(rs.getBoolean("aplic_graf"));
			resp.setDburl(rs.getString("dburl"));
			resp.setDbdriver(rs.getString("dbdriver"));
			resp.setDbuser(rs.getString("dbuser"));
			resp.setDbpass(rs.getString("dbpass"));
			return resp;
		} else {
			return null;
		}
	}

	/**
	 * El metodo separa los reportes dependiendo de nivel del usaurio enla aplicacion y devukeve una lista de repotes.
	 * @param u
	 * @param tipo
	 * @return
	 * @throws Exception
	 */

	public ArrayList<Reporte> traerReportesPorUsuario(Usuario u, String tipo) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst;
		
		if (u.getApp_nivel().equalsIgnoreCase("30")){ //oficina
			
			pst = conn.prepareStatement(
					"SELECT r.id, r.titulo, r.marca, r.aplic_graf, u.usuarioid "
					+ "FROM misreportes_reportes r " 
					+ "join misreportes_tipos t on t.id = r.id_tipo and t.tipo = ? "
					+ "LEFT JOIN misreportes_reporte_usuario u ON r.id = u.reporteid AND u.usuarioid = ? "
					+ "WHERE r.app_nivel = ? ");
			
			pst.setString(1, tipo);
			pst.setInt(2, u.getId());
			pst.setString(3, u.getApp_nivel());
			
		} else {
			
			pst = conn.prepareStatement(
					"SELECT r.id, r.titulo, r.marca, r.aplic_graf, u.usuarioid "
					+ "FROM misreportes_reportes r " 
					+ "join misreportes_tipos t on t.id = r.id_tipo and t.tipo = ? "
					+ "LEFT JOIN misreportes_reporte_usuario u ON r.id = u.reporteid AND u.usuarioid = ? "
					+ "WHERE r.app_nivel = ? AND r.marca = ? ");
			
			pst.setString(1, tipo);
			pst.setInt(2, u.getId());
			pst.setString(3, u.getApp_nivel());
			pst.setString(4, u.getMarca());
			
		}

		ResultSet rs = pst.executeQuery();

		ArrayList<Reporte> list = new ArrayList<Reporte>();
		while (rs.next()) {
			Reporte reg = new Reporte();
			reg.setId(rs.getInt("id"));
			reg.setTitulo(rs.getString("titulo"));
			reg.setMarca(rs.getString("marca"));
			reg.setAplic_graf(rs.getBoolean("aplic_graf"));

			if (rs.getString("usuarioid") != null)
				reg.setSeleccionado(true);

			list.add(reg);
		}
		rs.close();
		return list;
	}

	
	/**
	 * Borrar un reporte que ha sido usado por el usuario.
	 * @param u
	 * @param tipo
	 * @throws Exception
	 */
	public void borrarReportesPorUsuario(Usuario u, String tipo) throws Exception {

		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst = conn.prepareStatement(
				"DELETE u FROM Alsea.dbo.misreportes_reporte_usuario u "
				+ "LEFT JOIN Alsea.dbo.misreportes_reportes r ON r.id = u.reporteid "
				+ "join misreportes_tipos t on t.id = r.id_tipo "
				+ "WHERE u.usuarioid = ? AND r.app_nivel = ? and t.tipo = ? ");
		
		pst.setInt(1, u.getId());
		pst.setString(2, u.getApp_nivel());
		pst.setString(3, tipo);

		pst.executeUpdate();
	}

	public int guardarReportesPorUsuario(String[] repos, Usuario u) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		int cant = 0;
		for (String rep : repos) {
			PreparedStatement pst = conn.prepareStatement(
					"INSERT INTO Alsea.dbo.misreportes_reporte_usuario ( reporteid, usuarioid ) "
					+ "VALUES ( ?, ? )");
			pst.setInt(1, Integer.valueOf(rep));
			pst.setInt(2, u.getId());
			cant += pst.executeUpdate();
		}
		return cant;
	}

	public ArrayList<Reporte> getListado(Usuario u, String tipo) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst;
		String query;
		
		if (Integer.valueOf(u.getApp_nivel()) >= 30){ //oficina
			
			query = "SELECT r.id, r.titulo, r.marca, r.aplic_graf FROM misreportes_reportes r "
					+ "join misreportes_tipos t on t.id = r.id_tipo and t.tipo = ? "
					+ "JOIN misreportes_reporte_usuario u ON r.id = u.reporteid AND u.usuarioid = ? "
					+ "WHERE r.app_nivel = ? ORDER BY marca, id";
					
			pst = conn.prepareStatement(query);
			pst.setString(1, tipo);
			pst.setInt(2, u.getId());
			pst.setString(3, u.getApp_nivel());
			
		} else {
			
			query = "SELECT r.id, r.titulo, r.marca, r.aplic_graf FROM misreportes_reportes r "
					+ "join misreportes_tipos t on t.id = r.id_tipo and t.tipo = ? "
					+ "JOIN misreportes_reporte_usuario u ON r.id = u.reporteid AND u.usuarioid = ? "
					+ "WHERE r.app_nivel = ? AND r.marca = ? ORDER BY marca, id";
					
			pst = conn.prepareStatement(query);
			pst.setString(1, tipo);
			pst.setInt(2, u.getId());
			pst.setString(3, u.getApp_nivel());
			pst.setString(4, u.getMarca());
			
		}
		
		return rs2array( pst.executeQuery() );
	}

	private ArrayList<Reporte> rs2array(ResultSet rs) throws Exception{
		ArrayList<Reporte> list = new ArrayList<Reporte>();
		while (rs.next()) {
			Reporte reg = new Reporte();
			reg.setId(rs.getInt("id"));
			reg.setTitulo(rs.getString("titulo"));
			reg.setMarca(rs.getString("marca"));
			reg.setAplic_graf(rs.getBoolean("aplic_graf"));
			list.add(reg);
		}
		rs.close();
		return list;
	}
	
}
