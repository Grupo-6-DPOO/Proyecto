package logica;

import java.util.List;
import java.util.Map;

public interface IPiezas {

	Map<Integer, PiezaEnInventario> getInventarioActual();

	Map<Integer, PiezaEnInventario> getInventarioHistorico();

	List<PiezaEnInventario> getPiezasConsignacionVencida();

	void crearPiezaInventario(Pieza pieza, int ubicacionGaleria, boolean enConsignacion, String salidaConsignacion,
			String username, boolean puedeSalir) throws Exception;

	void agregarPiezaInventario(PiezaEnInventario pieza);

	List<PiezaEnInventario> buscarPiezaNombre(String nombre);

	void sacarPieza(PiezaEnInventario pieza) throws Exception;

	void cambiarDuenioPiezaPorCompra(PiezaEnInventario pieza, Cliente nuevoDuenio) throws Exception;

	void moverPiezaEnGaleria(PiezaEnInventario pieza, int destino) throws Exception;

	Escultura nuevaEscultura(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, String dimensiones, String material, String peso, boolean necesitaElectricidad);

	Fotografia nuevaFotografia(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, String resolucion);

	Pintura nuevaPintura(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, String dimensiones, String material);

	Video nuevoVideo(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, int duracion, String resolucion);

	void revisarConsignaciones(String fechaActual);

}