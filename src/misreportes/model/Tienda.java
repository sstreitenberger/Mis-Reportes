package misreportes.model;


/**
 * Clase de modelo Tienda.
 * @param grupo, nombre, ip.
 * @author Isshink
 *
 */
public class Tienda {
	private String grupo,nombre,ip;

	public String getGrupo() {
		return grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getIp() {
		return ip;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
