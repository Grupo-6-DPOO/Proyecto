package galeria;

import java.util.List;

public class Video extends Pieza {
	
	private int duracion; /* En segundos */
	private int[] resolucion; /* Es un array con dos posiciones que representan las dimensiones en pixeles en x y y respectivamente*/
	private String formato;
	private float peso; /* En bytes */
	private String observaciones;
	
	public Video(String titulo, String anio, String lugarCreacion, List<String> autores, int duracion,
			int[] resolucion, String formato, float peso, String observaciones) {
		super(titulo, anio, lugarCreacion, autores);
		this.duracion = duracion;
		this.resolucion = resolucion;
		this.formato = formato;
		this.peso = peso;
		this.observaciones = observaciones;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int[] getResolucion() {
		return resolucion;
	}

	public void setResolucion(int[] resolucion) {
		this.resolucion = resolucion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
	
}
