package galeria;

import java.util.List;

public class Pintura extends Pieza {
	

	private float[] dimensiones; /*Es un array con dos posiciones que representan las dimensiones en cm en x y y respectivamente*/
	private String material;
	private String observaciones;
	
	
	public Pintura(String titulo, String anio, String lugarCreacion, List<String> autores, float[] dimensiones,
			String material, String observaciones) {
		super(titulo, anio, lugarCreacion, autores);
		this.dimensiones = dimensiones;
		this.material = material;
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


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
	


}
