package galeria;

import java.util.HashMap;
import java.util.Map;

public class Duenio {
	
	private String nombre;
	private Integer documento;
	private String telefono;
	private String correo;
	private String direccion;
	private Map<Integer, PiezaEnInventario> piezasEnPosesionVigente; /*Esta tabla contiene parejas llave-valor de la forma <documento,PiezaEnInventario> de piezas propiedad del dueño que estén actualmente en la galería*/
	private Map<Integer, PiezaEnInventario> piezasEnPosesionNoVigente; /*Esta tabla contiene parejas llave-valor de la forma <documento,PiezaEnInventario> de piezas propiedad del dueño que no estén actualmente en la galería y/o que hayan sido vendidas por el cliente en algún momento*/

	
	public static Map<Integer,Duenio> duenios = new HashMap<Integer,Duenio>();
	
	public Duenio(String nombre,Integer documento, String telefono, String correo, String direccion) {
		super();
		this.nombre = nombre;
		this.documento=documento;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.piezasEnPosesionVigente = new HashMap<Integer,PiezaEnInventario>();
		this.piezasEnPosesionNoVigente = new HashMap<Integer,PiezaEnInventario>();
		duenios.put(documento, this);
		
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
	public Integer getDocuemnto(){
		return documento;
	}
	public Map<Integer, PiezaEnInventario> getPiezasEnPosesionVigente() {
		return piezasEnPosesionVigente;
	}
	public Map<Integer, PiezaEnInventario> getPiezasEnPosesionNoVigente() {
		return piezasEnPosesionNoVigente;
	}
	
	public void anadirPiezaPosesionVigente(PiezaEnInventario pieza) {
		Integer hash =  pieza.getHash();
		this.piezasEnPosesionVigente.put(hash, pieza);
	}
	
	public void anadirPiezaPosesionNoVigente(PiezaEnInventario pieza) {
		Integer hash =  pieza.getHash();
		this.piezasEnPosesionNoVigente.put(hash, pieza);
	}
	
	public void moverPiezaVigenteANoVigente(PiezaEnInventario pieza) {
		Integer hash =  pieza.getHash();
		this.piezasEnPosesionVigente.remove(hash);
		this.piezasEnPosesionNoVigente.put(hash, pieza);
	}
	
	
	
	
}
