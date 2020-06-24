package misreportes.model;


/**
 * Clase modelo. La instancia de esta clase es armada por el metodo Validate de la clase LoginDAO
 * @param int id
 * @param String nombre, marca, app_nivel;
 * @author Isshink
 *
 */

public class Usuario {
	
	private int id;
	private String nombre, marca, app_nivel;
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getMarca() {
		return marca;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getApp_nivel() {
		return app_nivel;
	}

	public void setApp_nivel(String app_nivel) {
		this.app_nivel = app_nivel;
	}
}
