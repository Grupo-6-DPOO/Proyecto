package logica;

import java.util.List;

import org.json.JSONObject;

public abstract class Pieza {
	public static final int FOTOGRAFIA=0;
	public static final int PINTURA=1;
	public static final int ESCULTURA=2;
	public static final int VIDEO=3;
	
	
	
	protected String titulo;
	protected int tipo;
	protected String anio;
	protected String lugarCreacion;
	protected List<String> autores;
	protected String observaciones;
	
	public Pieza(String titulo, String anio, String lugarCreacion, List<String> autores, String observaciones) {
		super();
		this.titulo = titulo;
		this.anio = anio;
		this.lugarCreacion = lugarCreacion;
		this.autores = autores;
		this.observaciones = observaciones;
	}
	
	public int getTipo() {
		return tipo;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getLugarCreacion() {
		return lugarCreacion;
	}
	public void setLugarCreacion(String lugarCreacion) {
		this.lugarCreacion = lugarCreacion;
	}
	public List<String> getAutores() {
		return autores;
	}
	public void setAutores(List<String> autores) {
		this.autores = autores;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public abstract JSONObject createJSONObject();
		




	
	
}
