package logica;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Escultura extends Pieza {
	private String dimensiones;
	private String material;
	private String peso;
	private boolean necesitaElectricidad;
	
	public Escultura(String titulo, String anio, String lugarCreacion, List<String> autores, String observaciones,
			String dimensiones, String material, String peso, boolean necesitaElectricidad) {
		super(titulo, anio, lugarCreacion, autores, observaciones);
		this.dimensiones = dimensiones;
		this.material = material;
		this.peso = peso;
		this.necesitaElectricidad = necesitaElectricidad;
		this.tipo = Pieza.ESCULTURA;
	}

	public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public boolean isNecesitaElectricidad() {
		return necesitaElectricidad;
	}

	public void setNecesitaElectricidad(boolean necesitaElectricidad) {
		this.necesitaElectricidad = necesitaElectricidad;
	}

	@Override
	public String toString() {
		String autoresString = "{";
		for (String nombreAutor:this.getAutores()) {
			autoresString+=nombreAutor;
		}
		autoresString += "}";
		return "Escultura [dimensiones=" + dimensiones + ", material=" + material + ", peso=" + peso
				+ ", necesitaElectricidad=" + necesitaElectricidad + ", titulo=" + titulo + ", tipo=" + tipo + ", anio="
				+ anio + ", lugarCreacion=" + lugarCreacion + ", autores=" + autoresString + ", observaciones="
				+ observaciones + "]";
	}

	@Override
	public JSONObject createJSONObject() {
			// TODO Auto-generated method stub
		JSONObject raiz = new JSONObject();
		raiz.put("tipo", "ESCULTURA");
		raiz.put("titulo", this.getTitulo());
		raiz.put("anio",this.getAnio());
		raiz.put("lugarCreacion", this.getLugarCreacion());
		JSONArray autores = new JSONArray();
		for (String autor:this.getAutores()) {
			autores.put(autor);
		}
		raiz.put("autores", autores);
		raiz.put("observaciones", this.getObservaciones());
		raiz.put("dimensiones", this.getDimensiones());
		raiz.put("material", this.getMaterial());
		raiz.put("peso", this.getPeso());
		raiz.put("necesitaElectricidad", this.isNecesitaElectricidad());
		return raiz;
		
	}


	
	
	
}
