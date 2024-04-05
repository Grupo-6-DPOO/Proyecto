package galeria;

import java.util.HashMap;
import java.util.Map;

public class PiezaEnInventario {
	public static final int NOVENDIDA = 0; 
	public static final int VENDIDA = 1;
	public static final int NOALAVENTA =2;
	public static final int ENEXHIBICION =3;
	public static final int BODEGA = 4;
	public static final int FUERADEGALERIA =5;
	
	
	private Pieza pieza;
	private int estadoVenta;
	private int ubicacionGaleria;
	private String fechaIngresoGaleria; /* En formato dd/mm/yyyy */
	private Object[] parametrosConsignacion; /* Es un array con dos posiciones, la primera es un booleano que nos dice si la pieza está en consignacion, y la segunda es un string que nos dice la fecha de salida de la pieza en caso de estarlo*/
	private Duenio duenio;
	private String fechaSalidaGaleria;
	private Integer hash;

	
	public String getFechaSalidaGaleria() {
		return fechaSalidaGaleria;
	}

	public void setFechaSalidaGaleria(String fechaSalidaGaleria) {
		this.fechaSalidaGaleria = fechaSalidaGaleria;
	}

	public static Map<Integer,PiezaEnInventario> piezasEnExhibicion = new HashMap<Integer,PiezaEnInventario>(); /*Este mapa contiene parejas llave-valor de la forma <hash,PiezaEnInventario> de todas las piezas que la galería tiene exhibidas*/
	public static Map<Integer,PiezaEnInventario> piezasEnBodega = new HashMap<Integer,PiezaEnInventario>(); /*Este mapa contiene parejas llave-valor de la forma <hash,PiezaEnInventario> de todas las piezas que la galería tiene en bodega*/
	public static Map<Integer,PiezaEnInventario> piezasFueraDeGaleria = new HashMap<Integer,PiezaEnInventario>(); /*Este mapa contiene parejas llave-valor de la forma <hash,PiezaEnInventario> de todas las piezas que tuvo alguna vez la galería.*/
	
	public PiezaEnInventario(Pieza pieza, int estadoVenta, int ubicacionGaleria, String fechaIngresoGaleria,
			Object[] parametrosConsignacion, Duenio duenio, String fechaSalidaGaleria) {
		super();
		this.pieza = pieza;
		this.estadoVenta = estadoVenta;
		this.ubicacionGaleria = ubicacionGaleria;
		this.fechaIngresoGaleria = fechaIngresoGaleria;
		this.parametrosConsignacion = parametrosConsignacion;
		this.duenio = duenio;
		this.hash = Integer.valueOf(pieza.hashCode());
		this.fechaSalidaGaleria = fechaSalidaGaleria;
		
		if (ubicacionGaleria == ENEXHIBICION) {
			PiezaEnInventario.piezasEnExhibicion.put(hash, this);
		}
		else if (ubicacionGaleria == BODEGA){
			PiezaEnInventario.piezasEnBodega.put(hash, this);
		}
		else if (ubicacionGaleria == FUERADEGALERIA) {
			PiezaEnInventario.piezasFueraDeGaleria.put(hash, this);
		}
	}

	public Pieza getPieza() {
		return pieza;
	}

	public void setPieza(Pieza pieza) {
		this.pieza = pieza;
	}

	public int getEstadoVenta() {
		return estadoVenta;
	}

	public void setEstadoVenta(int estadoVenta) {
		this.estadoVenta = estadoVenta;
	}

	public int getUbicacionGaleria() {
		return ubicacionGaleria;
	}

	public void setUbicacionGaleria(int ubicacionGaleria) {
		this.ubicacionGaleria = ubicacionGaleria;
	}

	public String getFechaIngresoGaleria() {
		return fechaIngresoGaleria;
	}

	public void setFechaIngresoGaleria(String fechaIngresoGaleria) {
		this.fechaIngresoGaleria = fechaIngresoGaleria;
	}

	public Object[] getParametrosConsignacion() {
		return parametrosConsignacion;
	}

	public void setParametrosConsignacion(Object[] parametrosConsignacion) {
		this.parametrosConsignacion = parametrosConsignacion;
	}

	public Duenio getDuenio() {
		return duenio;
	}

	public void setDuenio(Duenio duenio) {
		this.duenio = duenio;
	}
	
	public Integer getHash() {
		return hash;
	}
	
	
	
	
}
