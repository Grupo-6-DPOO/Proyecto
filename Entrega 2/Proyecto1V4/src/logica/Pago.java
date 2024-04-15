package logica;

public class Pago {
	public static final int TARJETA = 0;
	public static final int TRANSFERENCIA =1;
	public static final int EFECTIVO = 2;
	public static final int NOESPECIFICADO = 3;
	
	public static int currentId=0;
	
	private int medioDePago;
	private boolean pagado;
	private Integer id;
	private PiezaEnInventario pieza;
	private Cliente comprador;
	private int monto;
	
	public Pago(int medioDePago, boolean pagado, PiezaEnInventario pieza, Cliente comprador, int monto) {
		super();
		this.medioDePago = medioDePago;
		this.pagado = pagado;
		this.id = currentId;
		this.pieza = pieza;
		this.comprador = comprador;
		this.monto = monto;
		currentId++;
	}

	public int getMonto() {
		return monto;
	}

	public int getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(int medioDePago) {
		this.medioDePago = medioDePago;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public Integer getId() {
		return id;
	}

	public PiezaEnInventario getPieza() {
		return pieza;
	}

	public Cliente getComprador() {
		return comprador;
	}
	
	public void aprobarPago(int medioDePago) {
		this.setMedioDePago(medioDePago); 
		int valorActualCompras = this.getComprador().getValorActualCompras(); 
		this.getComprador().setValorActualCompras(valorActualCompras+this.getMonto());
		this.getPieza().setPuedeSalir(true);
		this.setPagado(true);
	}

	@Override
	public String toString() {
		return "Pago [id=" + id + ", pieza=" + pieza.getPieza().toString()
				+ ", comprador=" + comprador.getUsername() + ", monto=" + monto + "]";
	}
	
	
	
	
	
}
