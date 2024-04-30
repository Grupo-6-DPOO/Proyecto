package logica;

import java.util.List;

public class Cliente extends Usuario {
	private String nombre;
	private String correo;
	private List<PiezaEnInventario> piezasCompradas;
	private int valorMaximoCompras; /*Valor máximo para las compras del comprador, en pesos*/
	private int valorActualCompras;
	private List<PiezaEnInventario> piezasEnPosesionVigente;
	private List<PiezaEnInventario> piezasEnPosesionNoVigente;/*Esta tabla contiene parejas llave-valor de la forma <documento,PiezaEnInventario> de piezas propiedad del dueño que no estén actualmente en la galería y/o que hayan sido vendidas por el cliente en algún momento*/
	private List<Pago> pagosRealizados;


	public Cliente(String username, String password, int rol, String nombre, String correo,
			List<PiezaEnInventario> piezasCompradas, int valorMaximoCompras, int valorActualCompras,
			List<PiezaEnInventario> piezasEnPosesionVigente,
			List<PiezaEnInventario> piezasEnPosesionNoVigente, List<Pago> pagosRealizados) throws Exception {
		super(username, password, Usuario.CLIENTE);
		this.nombre = nombre;
		this.correo = correo;
		this.piezasCompradas = piezasCompradas;
		this.valorMaximoCompras = valorMaximoCompras;
		this.valorActualCompras = valorActualCompras;
		this.piezasEnPosesionVigente = piezasEnPosesionVigente;
		this.piezasEnPosesionNoVigente = piezasEnPosesionNoVigente;
		this.pagosRealizados = pagosRealizados;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getValorMaximoCompras() {
		return valorMaximoCompras;
	}

	public void setValorMaximoCompras(int valorMaximoCompras) {
		this.valorMaximoCompras = valorMaximoCompras;
	}

	public int getValorActualCompras() {
		return valorActualCompras;
	}

	public void setValorActualCompras(int valorActualCompras) {
		this.valorActualCompras = valorActualCompras;
	}


	public List<PiezaEnInventario> getPiezasCompradas() {
		return piezasCompradas;
	}

	public List<PiezaEnInventario> getPiezasEnPosesionVigente() {
		return piezasEnPosesionVigente;
	}

	public List<PiezaEnInventario> getPiezasEnPosesionNoVigente() {
		return piezasEnPosesionNoVigente;
	}
	
	public void agregarPiezaComprada(PiezaEnInventario pieza) {
		this.getPiezasCompradas().add(pieza);
	}
	
	public void agregarPiezaPosesionVigente(PiezaEnInventario pieza) {
		this.getPiezasEnPosesionVigente().add(pieza);
	}
	
	public void eliminarPiezaPosesionVigente(PiezaEnInventario pieza) {
		this.getPiezasEnPosesionVigente().remove(pieza);
	}
	
	public void agregarPiezaPosesionNoVigente(PiezaEnInventario pieza) {
		this.getPiezasEnPosesionNoVigente().add(pieza);
	}
	
	public void eliminarPiezaPosesionNoVigente(PiezaEnInventario pieza) {
		this.getPiezasEnPosesionNoVigente().remove(pieza);
	}


	public String toString1() {
		return "Cliente [nombre=" + nombre + ", correo=" + correo + ", valorMaximoCompras="
				+ valorMaximoCompras + ", valorActualCompras=" + valorActualCompras + "]";
	}


	public String toString2() {
		String piezasEnPosesionVigenteString = "{";
		List<PiezaEnInventario> piezasEnPosesionVigente = this.getPiezasEnPosesionVigente();
		for (PiezaEnInventario pieza:piezasEnPosesionVigente) {
			piezasEnPosesionVigenteString += ("(ID: "+pieza.getId()+ ", Nombre: "+pieza.getPieza().getTitulo()+"), ");
		}
		piezasEnPosesionVigenteString +="}";
		
		String piezasEnPosesionNoVigenteString = "{";
		List<PiezaEnInventario> piezasEnPosesionNoVigente = this.getPiezasEnPosesionNoVigente();
		for (PiezaEnInventario pieza:piezasEnPosesionNoVigente) {
			piezasEnPosesionNoVigenteString += ("(ID: "+pieza.getId()+ ", Nombre: "+pieza.getPieza().getTitulo()+"), ");
		}
		piezasEnPosesionNoVigenteString +="}";
		
		String pagosRealizadosString = "{";
		List<Pago> pagosRealizados = this.getPagosRealizados();
		for (Pago pago:pagosRealizados) {
			pagosRealizadosString += ("(Nombre: "+pago.getPieza().getPieza().getTitulo()+ ", Precio pagado: "+ pago.getMonto()+", Fecha del pago: "+pago.getFecha()+"), ");
		}
		
		return "Cliente [nombre=" +nombre +", username="+ username  + ", correo=" + correo  + ", valorMaximoCompras=" + valorMaximoCompras + ", valorActualCompras="
				+ valorActualCompras + ", Piezas en galeria=" + piezasEnPosesionVigenteString
				+ ", Piezas en histórico de galería=" + piezasEnPosesionNoVigenteString + ", Piezas compradas = "+ pagosRealizadosString+"]";
	}

	public List<Pago> getPagosRealizados() {
		return pagosRealizados;
	}

	public void setPagosRealizados(List<Pago> pagosRealizados) {
		this.pagosRealizados = pagosRealizados;
	}
	
	public void agregarPago(Pago pago) {
		this.getPagosRealizados().add(pago);
	}
	
	public int calcularValorColeccion() {
		int valorColeccion = 0;
		for (int i = 0; i<this.getPagosRealizados().size(); i++) {
			Pago pago = this.getPagosRealizados().get(i);
			valorColeccion += pago.getMonto();
		}
		return valorColeccion;
	}
	
	
	

	
	
	
	
}
