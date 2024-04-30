package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Subasta {
	private boolean terminada;
	private List<PiezaEnInventario> piezas; /*Es un mapa con las piezas en venta cuyas llaves son los hashes de la pieza y su valor es la pieza en el inventario*/
	private Map<PiezaEnInventario,Integer> valorActual;
	private Map<PiezaEnInventario,Integer> valorMinimo;
	private Map<PiezaEnInventario,Oferta> mayorOferta;
	private Map<PiezaEnInventario,List<Oferta>> ofertasPasadas;
	private Map<PiezaEnInventario,Boolean>  bloqueadaVenta;
	private List<PiezaEnInventario> piezasVentaSolicitada;
	private List<Cliente> participantes;
	
	public Subasta(List<PiezaEnInventario> piezas, Map<PiezaEnInventario, Integer> valorActual,
			Map<PiezaEnInventario, Integer> valorMinimo, Map<PiezaEnInventario, Oferta> mayorOferta,
			Map<PiezaEnInventario, List<Oferta>> ofertasPasadas, Map<PiezaEnInventario, Boolean> bloqueadaVenta,
			Map<PiezaEnInventario, Boolean> vendida, List<Cliente> participantes,boolean terminada,List<PiezaEnInventario> piezasVentaSolicitada) {
		super();
		this.piezas = piezas;
		this.valorActual = valorActual;
		this.valorMinimo = valorMinimo;
		this.mayorOferta = mayorOferta;
		this.ofertasPasadas = ofertasPasadas;
		this.bloqueadaVenta = bloqueadaVenta;
		this.participantes = participantes;
		this.terminada = terminada;
		this.piezasVentaSolicitada = piezasVentaSolicitada;
	}

	public List<PiezaEnInventario> getPiezasVentaSolicitada() {
		return piezasVentaSolicitada;
	}

	public List<PiezaEnInventario> getPiezas() {
		return piezas;
	}

	public Map<PiezaEnInventario, Integer> getValorActual() {
		return valorActual;
	}

	public Map<PiezaEnInventario, Integer> getValorMinimo() {
		return valorMinimo;
	}

	public Map<PiezaEnInventario, Oferta> getMayorOferta() {
		return mayorOferta;
	}

	public Map<PiezaEnInventario, List<Oferta>> getOfertasPasadas() {
		return ofertasPasadas;
	}

	public Map<PiezaEnInventario, Boolean> getBloqueadaVenta() {
		return bloqueadaVenta;
	}


	public List<Cliente> getParticipantes() {
		return participantes;
	}
	
	public void agregarOferta(Cliente comprador, int monto, PiezaEnInventario pieza) throws Exception {
		if (!this.getPiezas().contains(pieza)) {
			throw new Exception("Esta pieza no está en venta en esta subasta");
		}
		Integer valorActual = this.getValorActual().get(pieza);
		if (valorActual >= monto) {
			throw new Exception("Debe ofrecer más que el valor actual");
		}
		if (!this.getParticipantes().contains(comprador)) {
			throw new Exception("Este participante no está en la subasta");
		}
		if (this.getBloqueadaVenta().get(pieza) == true) {
			throw new Exception("No puede ofertarse sobre una pieza bloqueada");
		}
		Oferta oferta = new Oferta(pieza, comprador, monto);
		this.getValorActual().put(pieza, monto);
		this.getMayorOferta().put(pieza, oferta);
		this.getOfertasPasadas().get(pieza).add(oferta);
		
	}
	
	public void solicitarVentaSubastaPieza(PiezaEnInventario pieza) throws Exception {
		if (!this.getPiezas().contains(pieza)) {
			throw new Exception("Esta pieza no está en venta en esta subasta");
		}
		if (this.getMayorOferta().get(pieza)==null) {
			throw new Exception("Esta pieza no tiene ofertas");
		}
		Oferta oferta = this.getMayorOferta().get(pieza);
		Integer valorMinimo = this.getValorMinimo().get(pieza);
		Integer valorOferta = Integer.valueOf(oferta.getMonto());
		Cliente comprador = oferta.getComprador();
		if (valorOferta+comprador.getValorActualCompras()>comprador.getValorMaximoCompras()) {
			throw new Exception("No tiene cupo para comprar esta pieza");
		}
		if (valorOferta < valorMinimo) {
			throw new Exception("No puede vender esta pieza por menos del valor mínimo");
		}
		pieza.setPuedeSalir(false);
		this.bloqueadaVenta.put(pieza,true);
		this.piezasVentaSolicitada.add(pieza);
		
		
	
		
		
	}
	

	public boolean isTerminada() {
		return terminada;
	}

	public void setTerminada(boolean terminada) {
		this.terminada = terminada;
	}

	public String imprimirPiezas() {
	
			List<String> idPiezas = new ArrayList<String>();
			for (int j=0; j<this.getPiezas().size();j++) {
				idPiezas.add(Integer.toString(this.getPiezas().get(j).getId()));
			}
			
			return 	idPiezas.toString();		
	}

	
	
	
	
}
