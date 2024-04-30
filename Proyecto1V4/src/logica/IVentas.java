package logica;

import java.util.List;

public interface IVentas {

	List<VentaValorFijo> getVentasActivas();

	List<VentaValorFijo> getVentasSolicitudVenta();

	void crearVenta(Integer idPieza, int precio) throws Exception;

	void solicitarVentaValorFijo(VentaValorFijo venta, Cliente comprador) throws Exception;

	void aprobarVentaValorFijo(VentaValorFijo venta) throws Exception;

	void rechazarVentaValorFijo(VentaValorFijo venta) throws Exception;

}