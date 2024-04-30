package logica;

import java.util.List;

public class Artista {
	private String nombre;
	private List<Pieza> piezas;
	private List<Pago> pagosPiezas;
	
	public String getNombre() {
		return nombre;
	}
	public List<Pieza> getPiezas() {
		return piezas;
	}
	public List<Pago> getPagosPiezas() {
		return pagosPiezas;
	}
	
	public Artista(String nombre, List<Pieza> piezas, List<Pago> pagosPiezas) {
		super();
		this.nombre = nombre;
		this.piezas = piezas;
		this.pagosPiezas = pagosPiezas;
	}
	
	public void agregarPieza(Pieza pieza) {
		this.getPiezas().add(pieza);
	}
	
	public void agregarPago (Pago pago) {
		this.getPagosPiezas().add(pago);
	}
	@Override
	public String toString() {
		List<Pieza> listPiezas = this.getPiezas();
		String listPiezasString = "{";
		for (Pieza pieza:listPiezas) {
			listPiezasString += ("(Nombre pieza: "+pieza.getTitulo()+", Fecha de creación: "+pieza.getAnio()+"), ");
		}
		listPiezasString+="}";
		
		List<Pago> listPagos = this.getPagosPiezas();
		String listPagosString = "{";
		for (Pago pago:listPagos) {
			listPagosString += ("(Nombre pieza: "+pago.getPieza().getPieza().getTitulo()+", Fecha de venta: "+pago.getFecha()+", Monto: "+pago.getMonto()+"), ");
		}
		listPagosString+="}";
		
		return "Artista [Nombre=" + nombre + ", Piezas registradas=" + listPiezasString + ", Piezas vendidas=" + listPagosString + "]";
	}
	
	

	
	
}
