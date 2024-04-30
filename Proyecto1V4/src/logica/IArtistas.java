package logica;

import java.util.List;

public interface IArtistas {

	abstract List<Artista> getArtistas();

	abstract Artista obtenerArtista(String nombre);

	abstract List<Artista> listaNombresAListaArtistas(List<String> listaNombres);

	abstract Artista buscarArtistaPorNombre(String nombre) throws Exception;

}