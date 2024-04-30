package logica;

import java.util.List;

public interface IPagos {

	List<Pago> getPagosPendientes();

	List<Pago> getPagosProcesados();

	void aprobarPago(Pago pago, int medioDePago, String fecha) throws Exception;

	void crearNuevoPagoHistorial(int medioDePago, boolean pagado, int IDPieza, String usernameComprador, int monto,
			int id, String fecha);

}