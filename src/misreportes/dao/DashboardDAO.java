package misreportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import misreportes.model.Rango;
import misreportes.model.Reporte;
import misreportes.model.Tienda;

public class DashboardDAO {

	public ArrayList<Rango> traerRangos() throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst = conn.prepareStatement("SELECT id,nombre from alsea.dbo.MisReportes_Rangos");
		ResultSet rs = pst.executeQuery();
		ArrayList<Rango> list = new ArrayList<Rango>();
		while (rs.next()) {
			Rango reg = new Rango();
			reg.setId(rs.getInt("id"));
			reg.setNombre(rs.getString("nombre"));
			list.add(reg);
		}
		rs.close();
		return list;
	}

	public boolean nuevoDash(Reporte rd) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		
		PreparedStatement pst = conn.prepareStatement("INSERT INTO Alsea.dbo.MisReportes_Dashboard "
				+ "( reporte_id, usuario_id, ALIAS, rango_id, tipo, orden, formato ) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ? )", Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, rd.getReporte_id() );
		pst.setInt(2, rd.getUsuario_id() );
		pst.setString(3, rd.getAlias() );
		
		if (rd.getRango() == null || rd.getRango().getId() == null)
			pst.setNull(4, java.sql.Types.INTEGER );
		else 
			pst.setInt(4, rd.getRango().getId());

		pst.setString(5, rd.getTipo());
		pst.setInt(6, rd.getOrden());
		pst.setString(7, rd.getFormato());

		pst.executeUpdate();
		ResultSet rs = pst.getGeneratedKeys();
		if (rs.next()) {
			rd.setId(rs.getInt(1));
			rs.close();
			if (rd.getTiendas()!=null)
				return insertTiendasDash(rd);
			else 
				return true;
		} else{
			return false;
		}
	}

	private boolean insertTiendasDash(Reporte rd) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst;

		for (Tienda t : rd.getTiendas()) {
			pst = conn.prepareStatement("INSERT INTO Alsea.dbo.MisReportes_dash_tiendas ( id_dash, ip_tienda ) VALUES ( ?, ? )");
			pst.setInt(1, rd.getId());
			pst.setString(2, t.getIp());
			if (pst.executeUpdate() != 1)
				throw new Exception("Error al asociar las tiendas al reporte");
		}
		return true;

	}

	public Reporte getReporteDash(Integer user, Integer orden) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();

		PreparedStatement pst = conn.prepareStatement("SELECT * FROM alsea.dbo.MisReportes_Dashboard d LEFT JOIN alsea.dbo.misreportes_rangos r ON r.id = d.rango_id LEFT JOIN alsea.dbo.misreportes_reportes rp ON rp.id = d.reporte_id WHERE usuario_id = ? AND orden = ?");
		pst.setInt(1, user);
		pst.setInt(2, orden);

		ResultSet rs = pst.executeQuery();
		Reporte rd = null;
		if (rs.next()) {
			rd = new Reporte();
			rd.setId(rs.getInt(1));
			rd.setUsuario_id(rs.getInt("usuario_id"));
			rd.setAlias(rs.getString("alias"));
			rd.setTipo(rs.getString("tipo"));
			rd.setOrden(rs.getInt("orden"));
			rd.setFormato(rs.getString("formato"));
			
			Rango r = new Rango();
			r.setId(rs.getInt("rango_id"));
			r.setDesde(rs.getString("rango_desde"));
			r.setHasta(rs.getString("rango_hasta"));
			r.setNombre(rs.getString("nombre"));
			rd.setRango(r);
			
			rd.setReporte_id(rs.getInt("reporte_id"));
			rd.setTitulo(rs.getString("titulo"));
			rd.setMarca(rs.getString("marca"));
			rd.setApp_nivel(rs.getInt("app_nivel"));
			rd.setAplic_graf(rs.getBoolean("aplic_graf"));
			rd.setQuery(rs.getString("query"));
			rd.setDburl(rs.getString("dburl"));
			rd.setDbdriver(rs.getString("dbdriver"));
			rd.setDbuser(rs.getString("dbuser"));
			rd.setDbpass(rs.getString("dbpass"));
		}
		return rd;
	}

	public String[] getTiendasReporteDash(Integer id) throws Exception {
		ArrayList<Tienda> t = getArrayTiendasReporteDash(id);
		String[] ts = new String[t.size()];
		int x = 0;
		for (Tienda _t : t){
			ts[x] = _t.getIp();
			x++;
		}
		return ts;
	}
	
	public ArrayList<Tienda> getArrayTiendasReporteDash(Integer id) throws Exception {
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM alsea.dbo.MisReportes_dash_tiendas where id_dash = ?");
		pst.setInt(1, id);
		
		ResultSet rs = pst.executeQuery();
		ArrayList<Tienda> t = new ArrayList<Tienda>();
		while (rs.next()) {
			Tienda tt = new Tienda();
			tt.setIp( rs.getString("ip_tienda") );
			t.add( tt );
		}
		return t;
	}

	public boolean borrarDash(String dash_id) throws Exception {
		if (borrarDashboard(dash_id))
			return borrarDashTiendas(dash_id);
		else
			throw new Exception("error al borrar dashboard");
	}
	
	private boolean borrarDashboard(String dash_id) throws Exception{
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM alsea.dbo.MisReportes_Dashboard WHERE id = ?");
		pst.setString(1, dash_id);
		return pst.executeUpdate() > 0;
	}
	
	private boolean borrarDashTiendas(String dash_id) throws Exception{
		Connection conn = new Conexion().conexionBksrv4full();
		PreparedStatement pst = conn.prepareStatement("DELETE FROM alsea.dbo.MisReportes_Dash_tiendas WHERE id_dash = ?");
		pst.setString(1, dash_id);
		pst.executeUpdate();
		return true;
	}

}
