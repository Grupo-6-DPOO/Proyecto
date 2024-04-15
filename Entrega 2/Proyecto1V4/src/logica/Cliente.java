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
	


	public Cliente(String username, String password, int rol, String nombre, String correo,
			List<PiezaEnInventario> piezasCompradas, int valorMaximoCompras, int valorActualCompras,
			List<PiezaEnInventario> piezasEnPosesionVigente,
			List<PiezaEnInventario> piezasEnPosesionNoVigente) throws Exception {
		super(username, password, Usuario.CLIENTE);
		this.nombre = nombre;
		this.correo = correo;
		this.piezasCompradas = piezasCompradas;
		this.valorMaximoCompras = valorMaximoCompras;
		this.valorActualCompras = valorActualCompras;
		this.piezasEnPosesionVigente = piezasEnPosesionVigente;
		this.piezasEnPosesionNoVigente = piezasEnPosesionNoVigente;
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
		
		
		return "Cliente [nombre=" +nombre +", username"+ username  + ", correo=" + correo + ", piezasCompradas="
				+ piezasCompradas.toString() + ", valorMaximoCompras=" + valorMaximoCompras + ", valorActualCompras="
				+ valorActualCompras + ", piezasEnPosesionVigente=" + piezasEnPosesionVigente.toString()
				+ ", piezasEnPosesionNoVigente=" + piezasEnPosesionNoVigente.toString() + "]";
	}
	

	
	
	
	
}
