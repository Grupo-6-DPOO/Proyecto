package logica;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Pintura extends Pieza {
	private String dimensiones;
	private String material;
	
	public Pintura(String titulo, String anio, String lugarCreacion, List<String> autores, String observaciones,
			String dimensiones, String material) {
		super(titulo, anio, lugarCreacion, autores, observaciones);
		this.dimensiones = dimensiones;
		this.material = material;
		this.tipo=Pieza.PINTURA;
	}

	public String getDimensiones() {
		return dimensiones;
	}

	public String getMaterial() {
		return material;
	}

	@Override
	public String toString() {
		String autoresString = "{";
		for (String nombreAutor:this.getAutores()) {
			autoresString+=nombreAutor;
		}
		autoresString += "}";
		return "Pintura [dimensiones=" + dimensiones + ", material=" + material + ", titulo=" + titulo + ", tipo="
				+ tipo + ", anio=" + anio + ", lugarCreacion=" + lugarCreacion + ", autores=" + autoresString
				+ ", observaciones=" + observaciones + "]";
	}

	@Override
	public JSONObject createJSONObject() {
		// TODO Auto-generated method stub
		JSONObject raiz = new JSONObject();
		raiz.put("tipo", "PINTURA");
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
		return raiz;
	}
	
	

	
}
