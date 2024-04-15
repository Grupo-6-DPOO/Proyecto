package logica;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Fotografia extends Pieza {
	private String resolucion;

	public Fotografia(String titulo, String anio, String lugarCreacion, List<String> autores, String observaciones,
			String resolucion) {
		super(titulo, anio, lugarCreacion, autores, observaciones);
		this.resolucion = resolucion;
		this.tipo=Pieza.FOTOGRAFIA;
	}

	public String getResolucion() {
		return resolucion;
	}

	@Override
	public String toString() {
		String autoresString = "{";
		for (String nombreAutor:this.getAutores()) {
			autoresString+=nombreAutor;
		}
		autoresString += "}";
		return "Fotografia [resolucion=" + resolucion + ", titulo=" + titulo + ", tipo=" + tipo + ", anio=" + anio
				+ ", lugarCreacion=" + lugarCreacion + ", autores=" + autoresString + ", observaciones=" + observaciones
				+ "]";
	}

	@Override
	public JSONObject createJSONObject() {

			// TODO Auto-generated method stub
			JSONObject raiz = new JSONObject();
			raiz.put("tipo", "FOTOGRAFIA");
			raiz.put("titulo", this.getTitulo());
			raiz.put("anio",this.getAnio());
			raiz.put("lugarCreacion", this.getLugarCreacion());
			JSONArray autores = new JSONArray();
			for (String autor:this.getAutores()) {
				autores.put(autor);
			}
			raiz.put("autores", autores);
			raiz.put("observaciones", this.getObservaciones());
			raiz.put("resolucion", this.getResolucion());
			return raiz;
	}




	
}
