package misreportes.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import misreportes.model.Reporte;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReportesDAO {

	private ResultSet exec(Reporte repo) throws Exception {
		Connection conn = new Conexion().conexionGenerica(repo.getDburl(), repo.getDbuser(), repo.getDbpass(),
				repo.getDbdriver());
		Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		return statement.executeQuery(repo.getQuery());
	}

	private List<Map> rs2json(ResultSet rs) throws SQLException {
		List<Map> resList = new ArrayList<Map>();

		ResultSetMetaData rsMeta = rs.getMetaData();
		int columnCnt = rsMeta.getColumnCount();

		List<String> columnNames = new ArrayList<String>();
		for (int i = 1; i <= columnCnt; i++) {
			columnNames.add(rsMeta.getColumnName(i).toUpperCase());
		}

		while (rs.next()) {
			Map obj = new LinkedHashMap();
			for (int i = 1; i <= columnCnt; i++) {
				String column_name = columnNames.get(i - 1);
				obj.put(column_name, eliminarNull(rs.getString(i)));
			}
			resList.add(obj);
		}

		return resList;
	}

	private String eliminarNull(String x) {
		if (x == null)
			return "0";
		else
			return x;
	}

	
	/**
	 * El metodo setea parametros para armar la Query que traera el reporte solicitado.
	 * @param repo
	 * @param tiendas
	 * @param top
	 * @return
	 * @throws Exception
	 */
	public List<Map> ejecutarQuery(Reporte repo, String[] tiendas, Boolean top) throws Exception {
		String query = repo.getQuery();
		String URLEntrada ="jdbc:sqlserver://172.31.1.14:1433;databaseName=micros";
		
		

		if (repo.getRango() != null && repo.getRango().getDesde() != null) {
			query = query.replace("&FECHADESDE&", repo.getRango().getDesde());
		}
		if (repo.getRango() != null && repo.getRango().getHasta() != null) {
			query = query.replace("&FECHAHASTA&", repo.getRango().getHasta());
		}
		
		
		if (tiendas != null && tiendas.length > 0) {
			String TIENDA = "";
			String COLUMNA = "";
			for (String tienda : tiendas) {
				String[] t = tienda.split("\\.");
				TIENDA += "tienda = " + t[2] + " or ";
				COLUMNA += "codigo = " + t[2] + " or ";
			}
			if (TIENDA.endsWith("or ")) {
				TIENDA = TIENDA.substring(0, TIENDA.length() - 3);
			}
			if (COLUMNA.endsWith("or ")) {
				COLUMNA = COLUMNA.substring(0, COLUMNA.length() - 3);
			}
			query = query.replace("&TIENDA&", TIENDA);
			query = query.replace("&COLUMNAS&", COLUMNA);
			query = query.replace("&IP&", tiendas[0]); 		
		}

		if (top) {
			query = query.toLowerCase().replaceFirst("select", "select top 1000 ");
		}
		if (tiendas != null && tiendas.length == 1)
			repo.setDburl(repo.getDburl().replace("&IP&", tiendas[0].split("\\.")[2] ) );
		
		
		repo.setQuery(query);
		System.out.println(query);
		if ( repo.getDburl().contains("sqlserver"))
		{
		return rs2json(exec(repo));
		}
					
		else {
		List<Map> json = new ArrayList<Map>();
		
			for (int i = 0; tiendas.length > i;i++) {
				repo.setDburl("jdbc:sybase:Tds:"+tiendas[i]+":2638?ServiceName=micros");		
				json.addAll((rs2json(exec(repo))));
			}
			
			if (tiendas != null && tiendas.length == 1) {
				return rs2json(exec(repo));
				}
				else {
					
					return json;
				}			
		}			
	}
}
