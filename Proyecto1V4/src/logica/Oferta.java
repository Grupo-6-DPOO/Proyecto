package logica;

public class Oferta {
	private PiezaEnInventario pieza;
	private Cliente comprador;
	private int monto;
	
	public Oferta(PiezaEnInventario pieza, Cliente comprador, int monto) {
		super();
		this.pieza = pieza;
		this.comprador = comprador;
		this.monto = monto;
	}

	public PiezaEnInventario getPieza() {
		return pieza;
	}

	public Cliente getComprador() {
		return comprador;
	}

	public int getMonto() {
		return monto;
	}
	
	
	
	
}
