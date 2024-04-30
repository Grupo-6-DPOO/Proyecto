package logica;

import java.util.List;

public interface ISubastas {

	List<Subasta> getSubastasActivas();

	List<Subasta> getSubastasTerminadas();

	Subasta crearSubastaVacia();

	void agregarPiezaSubasta(Subasta subasta, Integer idPieza, Integer valorMinimo, Integer valorInicial)
			throws Exception;

	void agregarParticipanteSubasta(Subasta subasta, String username) throws Exception;

	void crearOfertaSubasta(Subasta subasta, String username, int valor, Integer idPieza) throws Exception;

	void solicitarVentaSubasta(Subasta subasta, Integer idPieza) throws Exception;

	void aprobarVentaSubasta(Subasta subasta, PiezaEnInventario pieza) throws Exception;

	void rechazarVentaSubasta(Subasta subasta, PiezaEnInventario pieza) throws Exception;

	void finalizarSubasta(Subasta subasta);

}