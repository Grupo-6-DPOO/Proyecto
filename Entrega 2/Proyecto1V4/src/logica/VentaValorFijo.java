package logica;

public class VentaValorFijo {
	private PiezaEnInventario pieza;
	private int precio;
	private boolean bloqueada;
	private Cliente compradorPotencial;
	
	public VentaValorFijo(PiezaEnInventario pieza, int precio, boolean bloqueada, Cliente compradorPotencial) {
		super();
		this.pieza = pieza;
		this.precio = precio;
		this.bloqueada = bloqueada;
		this.compradorPotencial = compradorPotencial;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	public Cliente getCompradorPotencial() {
		return compradorPotencial;
	}

	public void setCompradorPotencial(Cliente compradorPotencial) {
		this.compradorPotencial = compradorPotencial;
	}

	public PiezaEnInventario getPieza() {
		return pieza;
	}

	public int getPrecio() {
		return precio;
	}
	
	public void solicitarVentaValorFijo(Cliente compradorPotencial) throws Exception {
		if (this.isBloqueada()) {
			throw new Exception("Esta pieza está bloqueada");
		}
		if (compradorPotencial.getValorActualCompras()+this.getPrecio()>compradorPotencial.getValorMaximoCompras()) {
			throw new Exception("No tiene cupo");
		}
		this.setCompradorPotencial(compradorPotencial);
		this.setBloqueada(true);
		this.getPieza().setPuedeSalir(false);
	}
	
}
