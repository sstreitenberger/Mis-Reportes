package misreportes.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
	
	
	/**
	 * Este metodo se utiliza para hacer conexion con el servidor y Db  para encontrar tanto
	 * las querys como las informacion de la que haran uso los reportes.
	 * @return
	 * @throws Exception
	 */

	public Connection conexionBksrv4() throws Exception {
		String url = "jdbc:sqlserver://172.31.1.14:1433;databaseName=micros";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String userName = "qliksense";
		String password = ".$3n$3";
		Class.forName(driver).newInstance();
		return DriverManager.getConnection(url, userName, password);
	}
	
	

	public Connection conexionGenerica(String url, String user, String pass, String driver) throws Exception {
		Class.forName(driver).newInstance();
		return DriverManager.getConnection(url, user, pass);
	}
// Cambiar servidor para chile
	
	/**
	 * Este metodo se utiliza para hacer la conexion con el servidor y DB que aloja los usuario, del cual hara uso 
	 * el programa a la hora de que alguie quiera loguearse.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection conexionBksrv4full() throws Exception {
		String url = "jdbc:sqlserver://172.31.1.14:1433;databaseName=Alsea";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String userName = "mobile";
		String password = ".M0b1l3$.";
		Class.forName(driver).newInstance();
		return DriverManager.getConnection(url, userName, password);
	}

}
