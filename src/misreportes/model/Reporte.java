package misreportes.model;

import java.util.ArrayList;


/**
 * Clase de modelo Reporte
 * @param Integer id, reporte_id, usuario_id, orden, app_nivel, tipo_id;
 * @param String alias, tipo, formato, titulo, marca, query;
 * @param Boolean aplic_graf, seleccionado;
 * @author Isshink
 *
 */
public class Reporte {

	private Integer id, reporte_id, usuario_id, orden, app_nivel, tipo_id;
	private String alias, tipo, formato, titulo, marca, query;
	private Boolean aplic_graf, seleccionado;

	private ArrayList<Tienda> tiendas;
	private Rango rango;
	
	private String dburl,dbdriver,dbuser,dbpass;
	
	public Integer getId() {
		return id;
	}
	public Integer getReporte_id() {
		return reporte_id;
	}
	public Integer getUsuario_id() {
		return usuario_id;
	}
	public Integer getOrden() {
		return orden;
	}
	public Integer getApp_nivel() {
		return app_nivel;
	}
	public Integer getTipo_id() {
		return tipo_id;
	}
	public String getAlias() {
		return alias;
	}
	public String getTipo() {
		return tipo;
	}
	public String getFormato() {
		return formato;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMarca() {
		return marca;
	}
	public String getQuery() {
		return query;
	}
	public Boolean getAplic_graf() {
		return aplic_graf;
	}
	public Boolean getSeleccionado() {
		return seleccionado;
	}
	public ArrayList<Tienda> getTiendas() {
		return tiendas;
	}
	public Rango getRango() {
		return rango;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setReporte_id(Integer reporte_id) {
		this.reporte_id = reporte_id;
	}
	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public void setApp_nivel(Integer app_nivel) {
		this.app_nivel = app_nivel;
	}
	public void setTipo_id(Integer tipo_id) {
		this.tipo_id = tipo_id;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setAplic_graf(Boolean aplic_graf) {
		this.aplic_graf = aplic_graf;
	}
	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	public void setTiendas(ArrayList<Tienda> tiendas) {
		this.tiendas = tiendas;
	}
	public void setRango(Rango rango) {
		this.rango = rango;
	}
	public String getDburl() {
		return dburl;
	}
	public String getDbdriver() {
		return dbdriver;
	}
	public String getDbuser() {
		return dbuser;
	}
	public String getDbpass() {
		return dbpass;
	}
	public void setDburl(String dburl) {
		this.dburl = dburl;
	}
	public void setDbdriver(String dbdriver) {
		this.dbdriver = dbdriver;
	}
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}
	public void setDbpass(String dbpass) {
		this.dbpass = dbpass;
	}

	

}
