package logica;

import java.util.List;

public class PiezaEnInventario {

	
	public static final int ENEXHIBICION =0;
	public static final int BODEGA = 1;
	public static final int FUERADEGALERIA =2;
	
	public static Integer currentid = 0;
	


	private Pieza pieza;
	private int ubicacionEnGaleria;
	private boolean enConsignacion; 
	private String salidaConsignacion;
	private Cliente duenio;
	private boolean puedeSalir;
	private List<Cliente> historialDuenios;
	private Integer id;


	public PiezaEnInventario(Pieza pieza, int ubicacionEnGaleria, boolean enConsignacion,
			String salidaConsignacion, Cliente duenio, boolean puedeSalir, List<Cliente> historialDuenios, Integer id) {
		super();
		this.pieza = pieza;
		this.ubicacionEnGaleria = ubicacionEnGaleria;
		this.enConsignacion = enConsignacion;
		this.salidaConsignacion = salidaConsignacion;
		this.duenio = duenio;
		this.puedeSalir = puedeSalir;
		this.historialDuenios = historialDuenios;
		this.id = id;
		currentid = Integer.max(currentid,id)+1;
	}
	
	public PiezaEnInventario(Pieza pieza, int ubicacionEnGaleria, boolean enConsignacion,
			String salidaConsignacion, Cliente duenio, boolean puedeSalir, List<Cliente> historialDuenios) {
		super();
		this.pieza = pieza;
		this.ubicacionEnGaleria = ubicacionEnGaleria;
		this.enConsignacion = enConsignacion;
		this.salidaConsignacion = salidaConsignacion;
		this.duenio = duenio;
		this.puedeSalir = puedeSalir;
		this.historialDuenios = historialDuenios;
		this.id = currentid;
		currentid++;
	}
	


	public int getUbicacionEnGaleria() {
		return ubicacionEnGaleria;
	}
	public void setUbicacionEnGaleria(int ubicacionEnGaleria) {
		this.ubicacionEnGaleria = ubicacionEnGaleria;
	}
	public boolean isEnConsignacion() {
		return enConsignacion;
	}
	public void setEnConsignacion(boolean enConsignacion) {
		this.enConsignacion = enConsignacion;
	}
	public String getSalidaConsignacion() {
		return salidaConsignacion;
	}
	public void setSalidaConsignacion(String salidaConsignacion) {
		this.salidaConsignacion = salidaConsignacion;
	}
	public Cliente getDuenio() {
		return duenio;
	}
	public void setDuenio(Cliente documentoDuenio) {
		this.duenio = documentoDuenio;
	}
	public boolean isPuedeSalir() {
		return puedeSalir;
	}
	public void setPuedeSalir(boolean puedeSalir) {
		this.puedeSalir = puedeSalir;
	}
	public Pieza getPieza() {
		return pieza;
	}
	public List<Cliente> getHistorialDuenios() {
		return historialDuenios;
	}
	public Integer getId() {
		return id;
	}
	
	public static boolean compararFechas(String fecha1, String fecha2) { /*Retorna true si fecha1>=fecha2, y falso de lo contrario*/
		String[] fecha1split = fecha1.split("/");
		String[] fecha2split = fecha2.split("/");
		
		int fecha1dia = Integer.parseInt(fecha1split[0]);
		int fecha1mes = Integer.parseInt(fecha1split[1])*31;
		int fecha1anio = Integer.parseInt(fecha1split[2])*365;
		
		int fecha2dia = Integer.parseInt(fecha2split[0]);
		int fecha2mes = Integer.parseInt(fecha1split[1])*31;
		int fecha2anio = Integer.parseInt(fecha1split[2])*365;
		
		int fecha1total = fecha1dia+fecha1mes+fecha1anio;
		int fecha2total = fecha2dia+fecha2mes+fecha2anio;
		
		return fecha1total>=fecha2total;
	}
	public boolean revisarConsignacionVencidaPieza(String fechaActual) {
		if ((this.enConsignacion)) {
			if (compararFechas(fechaActual,this.getSalidaConsignacion())&(this.ubicacionEnGaleria!=PiezaEnInventario.FUERADEGALERIA)) {
				return true;
			}	
		}
		return false; }
	
	public void cambiarDuenio(Cliente nuevoDuenio) throws Exception{
		this.duenio = nuevoDuenio;
		this.historialDuenios.add(nuevoDuenio);
	}



	@Override
	public String toString() {
		String historialDueniosString = "{";
		for (Cliente duenio: this.getHistorialDuenios()) {
			historialDueniosString += duenio.getUsername();
		}
		historialDueniosString += "}";
		String ubicacionEnGaleriastr = "";
		if (ubicacionEnGaleria == 0) {
			ubicacionEnGaleriastr = "En exhibición";
		}
		else if (ubicacionEnGaleria == 1) {
			ubicacionEnGaleriastr = "En bodega";
		}
		else if (ubicacionEnGaleria == 2) {
			ubicacionEnGaleriastr = "Fuera de galería";
		}
		return "PiezaEnInventario [pieza=" + pieza.toString() + ", ubicacionEnGaleria=" + ubicacionEnGaleriastr + ", enConsignacion="
				+ enConsignacion + ", salidaConsignacion=" + salidaConsignacion + ", duenio=" + duenio.getUsername() + ", puedeSalir="
				+ puedeSalir + ", historialDuenios=" + historialDueniosString + ", id=" + id + "]";
	}
	
	
	
	
	
	
	
	
}
