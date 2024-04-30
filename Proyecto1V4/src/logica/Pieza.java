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
	protected List<Artista> artistas;
	protected String observaciones;
	
	public Pieza(String titulo, String anio, String lugarCreacion, List<Artista> artistas, String observaciones) {
		super();
		this.titulo = titulo;
		this.anio = anio;
		this.lugarCreacion = lugarCreacion;
		this.artistas = artistas;
		this.observaciones = observaciones;
		for (Artista artista:artistas) {
			artista.agregarPieza(this);
		}
		
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
	public List<Artista> getArtistas() {
		return artistas;
	}
	public void setArtistas(List<Artista> autores) {
		this.artistas = autores;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public abstract JSONObject createJSONObject();
		




	
	
}
