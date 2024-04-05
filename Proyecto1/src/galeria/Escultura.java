package galeria;

import java.util.List;

public class Escultura extends Pieza {

	private float[] dimensiones;/*Es un array con tres posiciones que representan las dimensiones en cm en x,y y z respectivamente*/
	private String material;
	private float peso;
	private boolean necesitaElectricidad;
	private String observaciones;
	
	public Escultura(String titulo, String anio, String lugarCreacion, List<String> autores, float[] dimensiones,
			String material, float peso, boolean necesitaElectricidad, String observaciones) {
		super(titulo, anio, lugarCreacion, autores);
		this.dimensiones = dimensiones;
		this.material = material;
		this.peso = peso;
		this.necesitaElectricidad = necesitaElectricidad;
		this.observaciones = observaciones;
	}

	public float[] getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(float[] dimensiones) {
		this.dimensiones = dimensiones;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public boolean isNecesitaElectricidad() {
		return necesitaElectricidad;
	}

	public void setNecesitaElectricidad(boolean necesitaElectricidad) {
		this.necesitaElectricidad = necesitaElectricidad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	

}
