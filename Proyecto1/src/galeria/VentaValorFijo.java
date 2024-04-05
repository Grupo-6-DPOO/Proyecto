package galeria;

import java.util.HashMap;
import java.util.Map;

public class VentaValorFijo {
	private PiezaEnInventario pieza;
	private int valor;
	private boolean bloqueada;
	private Integer hash;
	private Comprador compradorActual;
	
	public static Map<Integer,VentaValorFijo> ventasSimplesDesbloqueadas = new HashMap<Integer,VentaValorFijo>();
	public static Map<Integer,VentaValorFijo> ventasSimplesBloqueadas = new HashMap<Integer,VentaValorFijo>();
	
	
	
	public VentaValorFijo(PiezaEnInventario pieza, int valor, boolean bloqueada, Integer hash, Comprador compradorActual) {
		super();
		this.pieza = pieza;
		this.valor = valor;
		this.bloqueada = bloqueada;
		this.hash = pieza.getHash();
		this.compradorActual = compradorActual;
		if (bloqueada) {
			ventasSimplesBloqueadas.put(hash, this);
		}
		else {
			ventasSimplesDesbloqueadas.put(hash, this);
		}
	}
	
	public Comprador getCompradorActual() {
		return compradorActual;
	}
	public void setCompradorActual(Comprador compradorActual) {
		this.compradorActual = compradorActual;
	}
	public Integer getHash() {
		return hash;
	}
	public PiezaEnInventario getPieza() {
		return pieza;
	}
	public void setPieza(PiezaEnInventario pieza) {
		this.pieza = pieza;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public boolean isBloqueada() {
		return bloqueada;
	}
	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}
	
	
}
