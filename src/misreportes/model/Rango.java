package misreportes.model;


/**
 * Clase modelo Rango
 * @param String desde,hasta,nombre
 * @param Integer id
 * @author Isshink
 *
 */
public class Rango {

	private String desde,hasta,nombre;
	private Integer id;
	
	public String getDesde() {
		return desde;
	}

	public String getHasta() {
		return hasta;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
