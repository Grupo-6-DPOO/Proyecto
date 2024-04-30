package logica;

import java.util.List;

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
	private String fecha;
	
	public Pago(int medioDePago, boolean pagado, PiezaEnInventario pieza, Cliente comprador, int monto, String fecha) {
		super();
		this.medioDePago = medioDePago;
		this.pagado = pagado;
		this.id = currentId;
		this.pieza = pieza;
		this.comprador = comprador;
		this.monto = monto;
		this.fecha = fecha;
		currentId++;
	}
	
	public Pago(int medioDePago, boolean pagado, PiezaEnInventario pieza, Cliente comprador, int monto, int id, String fecha) {
		super();
		this.medioDePago = medioDePago;
		this.pagado = pagado;
		this.id = currentId;
		this.pieza = pieza;
		this.comprador = comprador;
		this.monto = monto;
		this.fecha = fecha;
		currentId = Integer.max(currentId,id)+1;
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

	
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void aprobarPago(int medioDePago, String fecha) {
		this.setMedioDePago(medioDePago); 
		int valorActualCompras = this.getComprador().getValorActualCompras(); 
		this.getComprador().setValorActualCompras(valorActualCompras+this.getMonto());
		this.getPieza().setPuedeSalir(true);
		this.setPagado(true);
		this.setFecha(fecha);
		this.getPieza().agregarPago(this);
		this.getComprador().agregarPago(this);
		List<Artista> artistas = this.getPieza().getPieza().getArtistas();
		for (int i =0;i<artistas.size();i++) {
			Artista artista = null;
			artista = artistas.get(i);
			artista.agregarPago(this);
		}
	}

	@Override
	public String toString() {
		return "Pago [id=" + id + ", pieza=" + pieza.getPieza().toString()
				+ ", comprador=" + comprador.getUsername() + ", monto=" + monto + "]";
	}
	
	
	
	
	
	
}
