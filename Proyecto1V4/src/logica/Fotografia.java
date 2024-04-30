package logica;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Fotografia extends Pieza {
	private String resolucion;

	public Fotografia(String titulo, String anio, String lugarCreacion, List<Artista> artistas, String observaciones,
			String resolucion) {
		super(titulo, anio, lugarCreacion, artistas, observaciones);
		this.resolucion = resolucion;
		this.tipo=Pieza.FOTOGRAFIA;
	}

	public String getResolucion() {
		return resolucion;
	}

	@Override
	public String toString() {
		String artistasString = "{";
		for (Artista artista:this.getArtistas()) {
			String nombreArtista = artista.getNombre();
			artistasString+=nombreArtista;
		}
		artistasString += "}";
		return "Fotografia [resolucion=" + resolucion + ", titulo=" + titulo + ", tipo=" + tipo + ", anio=" + anio
				+ ", lugarCreacion=" + lugarCreacion + ", artistas=" + artistasString + ", observaciones=" + observaciones
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
			JSONArray artistas = new JSONArray();
			for (Artista artista:this.getArtistas()) {
				artistas.put(artista.getNombre());
			}
			raiz.put("artistas", artistas);
			raiz.put("observaciones", this.getObservaciones());
			raiz.put("resolucion", this.getResolucion());
			return raiz;
	}
	
	




	
}
