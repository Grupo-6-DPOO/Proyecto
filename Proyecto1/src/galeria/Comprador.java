package galeria;

import java.util.HashMap;
import java.util.Map;

public class Comprador {
	private String nombre;
	private String telefono;
	private String correo;
	private String direccion;
	private Map<Integer,PiezaEnInventario> piezasCompradas; /* Este mapa tiene parejas llave-valor de la forma <hash,PiezaEnInventario> y muestra todas las piezas que ha comprado un comprador.*/
	private int valorMaximoCompras; /*Valor máximo para las compras del comprador, en pesos*/
	
	public Comprador(String nombre, String telefono, String correo, String direccion, int valorMaximoCompras) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.piezasCompradas = new HashMap<Integer, PiezaEnInventario>();
		this.valorMaximoCompras = valorMaximoCompras;
	}

	public int getValorMaximoCompras() {
		return valorMaximoCompras;
	}

	public void setValorMaximoCompras(int valorMaximoCompras) {
		this.valorMaximoCompras = valorMaximoCompras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Map<Integer, PiezaEnInventario> getPiezasCompradas() {
		return piezasCompradas;
	} 
	
	
	public void anadirPiezaAPiezasCompradas(PiezaEnInventario pieza) {
		Integer hash =  pieza.getHash();
		this.piezasCompradas.put(hash, pieza);
		
	}
	
	
	
}
