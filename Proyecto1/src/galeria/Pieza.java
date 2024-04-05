package galeria;

import java.util.List;

public abstract class Pieza {
	
	protected String titulo;
	protected String anio;
	protected String lugarCreacion;
	protected List<String> autores;
	
	public Pieza(String titulo, String anio, String lugarCreacion, List<String> autores) {
		super();
		this.titulo = titulo;
		this.anio = anio;
		this.lugarCreacion = lugarCreacion;
		this.autores = autores;
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
	
	

}
