package galeria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subasta {
	private Map<Integer,PiezaEnInventario> piezas; /*Es un mapa con las piezas en venta cuyas llaves son los hashes de la pieza y su valor es la pieza en el inventario*/
	private int valorMinimo;
	private int valorActual;
	private int valorInicial;
	private Integer hash;
	private Oferta ofertaGanadora;
	private List<Oferta> ofertasPasadas;
	private boolean enPausa;
	
	public static Map<Integer,Subasta> subastasProgramadas = new HashMap<Integer,Subasta>();
	public static Map<Integer,Subasta> subastasActivas = new HashMap<Integer,Subasta>();
	
	public Subasta(Map<Integer,PiezaEnInventario> piezas, int valorMinimo, int valorActual, int valorInicial, boolean enPausa) {
		super();
		this.piezas = piezas;
		this.valorMinimo = valorMinimo;
		this.valorActual = valorActual;
		this.valorInicial = valorInicial;
		this.enPausa = enPausa;
		this.hash = piezas.hashCode();
		this.ofertaGanadora = null;
		this.ofertasPasadas = new ArrayList<Oferta>();
		Subasta.subastasProgramadas.put(hash, this);
		
	}

	public int getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(int valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public int getValorActual() {
		return valorActual;
	}

	public void setValorActual(int valorActual) {
		this.valorActual = valorActual;
	}

	public int getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(int valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Oferta getOfertaGanadora() {
		return ofertaGanadora;
	}

	public void setOfertaGanadora(Oferta ofertaGanadora) {
		this.ofertaGanadora = ofertaGanadora;
	}

	public boolean isEnPausa() {
		return enPausa;
	}

	public void setEnPausa(boolean enPausa) {
		this.enPausa = enPausa;
	}

	public Map<Integer, PiezaEnInventario> getPiezas() {
		return piezas;
	}

	public Integer getHash() {
		return hash;
	}

	public List<Oferta> getOfertasPasadas() {
		return ofertasPasadas;
	}
	
	
	
	
	
	
	
}
