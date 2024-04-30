package persistencia;

import logica.Cliente;
import logica.Galeria;
import logica.IArtistas;
import logica.IPagos;
import logica.IPiezas;
import logica.ISubastas;
import logica.IUsuarios;
import logica.IVentas;
import logica.Pago;
import logica.Pieza;
import logica.PiezaEnInventario;
import logica.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import org.json.JSONObject;

public class CentralPersistencia <T extends IPagos&IPiezas&ISubastas&IUsuarios&IVentas&IArtistas>{

	public static Galeria cargarDatos(String archivoUsuarios, String archivoPiezas, String archivoPagos) throws Exception {
		Galeria galeria = new Galeria();
		guardarDatos(galeria, "./datosPersistencia/" + archivoUsuarios, "./datosPersistencia/" + archivoPiezas,  "./datosPersistencia/" + archivoPagos);
		return galeria;
	}

	private static void guardarDatos(Galeria galeria, String pathArchivoUsuarios, String pathArchivoPiezas, String pathArchivoPagos)
			throws Exception {
		Map<String, List<Integer>> piezasEnPosesionVigente = new HashMap<String, List<Integer>>();
		Map<String, List<Integer>> piezasEnPosesionNoVigente = new HashMap<String, List<Integer>>();

		Map<Integer, String> dueniosPiezas = new HashMap<Integer, String>();
		Map<Integer, List<String>> historialDueniosPiezas = new HashMap<Integer, List<String>>();

		cargarUsuarios(galeria, pathArchivoUsuarios, piezasEnPosesionVigente,
				piezasEnPosesionNoVigente);
		cargarPiezas(galeria, pathArchivoPiezas, dueniosPiezas, historialDueniosPiezas);
		cargarHistorialPagos(galeria,pathArchivoPagos);
		combinarCargas(galeria, piezasEnPosesionVigente, piezasEnPosesionNoVigente, dueniosPiezas,
				historialDueniosPiezas);

	}

	private static void cargarUsuarios(IUsuarios galeria, String pathArchivoUsuarios,
			 Map<String, List<Integer>> piezasEnPosesionVigente,
			Map<String, List<Integer>> piezasEnPosesionNoVigente) throws Exception {
		String jsonCompleto = new String(Files.readAllBytes(new File(pathArchivoUsuarios).toPath()));
		JSONObject raiz = new JSONObject(jsonCompleto);
		JSONArray usuarios = raiz.getJSONArray("usuarios");
		int numUsuarios = usuarios.length();
		for (int i = 0; i < numUsuarios; i++) {
			JSONObject usuario = usuarios.getJSONObject(i);
			String rolString = usuario.getString("rol");
			int rol = -1;
			if (rolString.equals("ADMINISTRADOR")) {
				rol = Usuario.ADMINISTRADOR;
			} else if (rolString.equals("CAJERO")) {
				rol = Usuario.CAJERO;
			} else if (rolString.equals("OPERADOR")) {
				rol = Usuario.ADMINISTRADOR;
			} else if (rolString.equals("EMPLEADO")) {
				rol = Usuario.EMPLEADO;
			} else if (rolString.equals("CLIENTE")) {
				rol = Usuario.CLIENTE;
			}

			String username = usuario.getString("username");
			String password = usuario.getString("password");
			if (rol == Usuario.CLIENTE) {
				String nombre = usuario.getString("nombre");
				String correo = usuario.getString("correo");

				int valorMaximoCompras = usuario.getInt("valorMaximoCompras");
				int valorActualCompras = usuario.getInt("valorActualCompras");

				JSONArray piezasEnPosesionVigenteJSONArray = usuario.getJSONArray("piezasEnPosesionVigente");
				List<Integer> piezasEnPosesionVigenteInteger = new ArrayList<Integer>();
				for (int j = 0; j < piezasEnPosesionVigenteJSONArray.length(); j++) {
					Integer idPieza = piezasEnPosesionVigenteJSONArray.getInt(j);
					piezasEnPosesionVigenteInteger.add(idPieza);
				}
				piezasEnPosesionVigente.put(username, piezasEnPosesionVigenteInteger);

				JSONArray piezasEnPosesionNoVigenteJSONArray = usuario.getJSONArray("piezasEnPosesionNoVigente");
				List<Integer> piezasEnPosesionNoVigenteInteger = new ArrayList<Integer>();
				for (int j = 0; j < piezasEnPosesionNoVigenteJSONArray.length(); j++) {
					Integer idPieza = piezasEnPosesionNoVigenteJSONArray.getInt(j);
					piezasEnPosesionNoVigenteInteger.add(idPieza);
				}
				piezasEnPosesionNoVigente.put(username, piezasEnPosesionNoVigenteInteger);

				galeria.crearNuevoCliente(username, password, nombre, correo, valorMaximoCompras);
				galeria.getClientes().get(username).setValorActualCompras(valorActualCompras);
			} else {
				galeria.crearNuevoUsuario(username, password, rol);
			}
		}
	}

	private static void cargarPiezas(IPiezas galeria, String pathArchivoPiezas, Map<Integer, String> dueniosPiezas,
			Map<Integer, List<String>> historialDueniosPiezas) throws IOException {

		String jsonCompleto = new String(Files.readAllBytes(new File(pathArchivoPiezas).toPath()));
		JSONObject raiz = new JSONObject(jsonCompleto);
		JSONArray piezas = raiz.getJSONArray("PiezasEnInventario");
		int numPiezas = piezas.length();
		for (int i = 0; i < numPiezas; i++) {
			JSONObject piezaEnInventarioJson = piezas.getJSONObject(i);

			JSONObject piezaObj = piezaEnInventarioJson.getJSONObject("pieza");
			String tipo = piezaObj.getString("tipo");
			String titulo = piezaObj.getString("titulo");
			String anio = piezaObj.getString("anio");
			String lugarCreacion = piezaObj.getString("lugarCreacion");
			JSONArray autoresJson = piezaObj.getJSONArray("artistas");
			List<String> autores = new ArrayList<String>();
			for (int j = 0; j < autoresJson.length(); j++) {
				autores.add(autoresJson.getString(j));
			}
			String observaciones = piezaObj.getString("observaciones");

			Pieza pieza = null;

			if (tipo.equals("PINTURA")) {
				String dimensiones = piezaObj.getString("dimensiones");
				String material = piezaObj.getString("material");
				pieza = galeria.nuevaPintura(titulo, anio, lugarCreacion, autores, observaciones, dimensiones,
						material);
			} else if (tipo.equals("ESCULTURA")) {
				String dimensiones = piezaObj.getString("dimensiones");
				String material = piezaObj.getString("material");
				String peso = piezaObj.getString("material");
				boolean necesitaElectricidad = piezaObj.getBoolean("necesitaElectricidad");
				pieza = galeria.nuevaEscultura(titulo, anio, lugarCreacion, autores, observaciones, dimensiones,
						material, peso, necesitaElectricidad);
			} else if (tipo.equals("VIDEO")) {
				int duracion = piezaObj.getInt("duracion");
				String resolucion = piezaObj.getString("resolucion");
				pieza = galeria.nuevoVideo(titulo, anio, lugarCreacion, autores, observaciones,
						duracion, resolucion);
			} else if (tipo.equals("FOTOGRAFIA")) {
				String resolucion = piezaObj.getString("resolucion");
				pieza = galeria.nuevaFotografia(titulo, anio, lugarCreacion, autores, observaciones, resolucion);
			}

			String ubicacionEnGaleriaString = piezaEnInventarioJson.getString("ubicacionEnGaleria");
			int ubicacionEnGaleria = -1;

			if (ubicacionEnGaleriaString.equals("ENEXHIBICION")) {
				ubicacionEnGaleria = PiezaEnInventario.ENEXHIBICION;
			} else if (ubicacionEnGaleriaString.equals("BODEGA")) {
				ubicacionEnGaleria = PiezaEnInventario.BODEGA;
			} else if (ubicacionEnGaleriaString.equals("FUERADEGALERIA")) {
				ubicacionEnGaleria = PiezaEnInventario.FUERADEGALERIA;
			}

			boolean enConsignacion = piezaEnInventarioJson.getBoolean("enConsignacion");
			String salidaConsignacion = piezaEnInventarioJson.getString("salidaConsignacion");
			String usernameDuenio = piezaEnInventarioJson.getString("usernameDuenio");
			boolean puedeSalir = piezaEnInventarioJson.getBoolean("puedeSalir");


			JSONArray historialDueniosJson = piezaEnInventarioJson.getJSONArray("historialDuenios");
			List<String> historialDuenios = new ArrayList<String>();
			for (int j = 0; j < historialDueniosJson.length(); j++) {
				historialDuenios.add(historialDueniosJson.getString(j));
			}

			Integer id = Integer.valueOf(piezaEnInventarioJson.getInt("id"));
			dueniosPiezas.put(id, usernameDuenio);
			historialDueniosPiezas.put(id, historialDuenios);
			List<Cliente> historialDueniosCliente = new ArrayList<Cliente>();
			List<Pago> historialPagos = new ArrayList<Pago>();
			PiezaEnInventario piezaEnInventario = new PiezaEnInventario(pieza, ubicacionEnGaleria, enConsignacion,
					salidaConsignacion, null, puedeSalir, historialDueniosCliente, id, historialPagos);
			if (ubicacionEnGaleria == PiezaEnInventario.FUERADEGALERIA) {
				galeria.getInventarioHistorico().put(id, piezaEnInventario);
			} else {
				galeria.getInventarioActual().put(id, piezaEnInventario);
			}

		}
	}

	private static void cargarHistorialPagos(IPagos galeria, String pathArchivoPagos) throws IOException {
		String jsonCompleto= new String(Files.readAllBytes(new File(pathArchivoPagos).toPath()));
		JSONObject raiz = new JSONObject(jsonCompleto);
		JSONArray pagos = raiz.getJSONArray("pagosRegistrados");
		
		int numPagos = pagos.length();
		for (int i=0;i<numPagos;i++) {
			JSONObject pagoJson = pagos.getJSONObject(i);
			int id = pagoJson.getInt("id");
			String fecha = pagoJson.getString("fecha");
			boolean pagado = true;
			int idPieza = pagoJson.getInt("idPieza");
			String usernameComprador = pagoJson.getString("usernameComprador");
			int monto = pagoJson.getInt("monto");
			String medioDePagoString = pagoJson.getString("medioDePago");
			int medioDePago =-1;
			if (medioDePagoString.equals("TARJETA")) {
				medioDePago = Pago.TARJETA;
			}
			else if (medioDePagoString.equals("TRANSFERENCIA")) {
				medioDePago = Pago.TRANSFERENCIA;
			}
			else if (medioDePagoString.equals("EFECTIVO")) {
				medioDePago = Pago.EFECTIVO;
			}
			else {
				medioDePago = Pago.NOESPECIFICADO;
			}
			
			galeria.crearNuevoPagoHistorial(medioDePago, pagado, idPieza,  usernameComprador,
			monto,  id,fecha);
			

		}
	}

	private static void combinarCargas(Galeria galeria,
			Map<String, List<Integer>> piezasEnPosesionVigente, Map<String, List<Integer>> piezasEnPosesionNoVigente,
			Map<Integer, String> dueniosPiezas, Map<Integer, List<String>> historialDueniosPiezas) {

		for (String username : piezasEnPosesionVigente.keySet()) {
			Cliente cliente = galeria.getClientes().get(username);
			for (Integer idPieza : piezasEnPosesionVigente.get(username)) {
				if (galeria.getInventarioActual().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioActual().get(idPieza);
					cliente.getPiezasEnPosesionVigente().add(pieza);
				} else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioHistorico().get(idPieza);
					cliente.getPiezasEnPosesionVigente().add(pieza);
				}

			}
		}

		for (String username : piezasEnPosesionNoVigente.keySet()) {
			Cliente cliente = galeria.getClientes().get(username);
			for (Integer idPieza : piezasEnPosesionNoVigente.get(username)) {
				if (galeria.getInventarioActual().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioActual().get(idPieza);
					cliente.getPiezasEnPosesionNoVigente().add(pieza);
				} else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioHistorico().get(idPieza);
					cliente.getPiezasEnPosesionNoVigente().add(pieza);
				}
			}
		}

		for (Integer idPieza : dueniosPiezas.keySet()) {
			PiezaEnInventario pieza = null;
			String usernameDuenio = dueniosPiezas.get(idPieza);
			Cliente duenio = galeria.getClientes().get(usernameDuenio);
			if (galeria.getInventarioActual().containsKey(idPieza)) {
				pieza = galeria.getInventarioActual().get(idPieza);
				pieza.setDuenio(duenio);
			} else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
				pieza = galeria.getInventarioHistorico().get(idPieza);
				pieza.setDuenio(duenio);
			}
		}

		for (Integer idPieza : historialDueniosPiezas.keySet()) {
			PiezaEnInventario pieza = null;
			if (galeria.getInventarioActual().containsKey(idPieza)) {
				pieza = galeria.getInventarioActual().get(idPieza);
			} else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
				pieza = galeria.getInventarioHistorico().get(idPieza);
			}

			for (String usernameDuenio : historialDueniosPiezas.get(idPieza)) {
				Cliente duenio = galeria.getClientes().get(usernameDuenio);
				pieza.getHistorialDuenios().add(duenio);
			}

		}
	}

	public static void salvarDatos(Galeria galeria, String nombreSalidaUsuarios ,String nombreSalidaPiezas , String nombreSalidaPagos)
			throws FileNotFoundException {
		JSONObject piezas = salvarPiezasEnInventario(galeria);
		JSONObject usuarios = salvarUsuarios(galeria);
		JSONObject pagos = salvarPagosRegistrados(galeria);
		PrintWriter archivoPiezas = new PrintWriter("./datosPersistencia/" + nombreSalidaPiezas);
		PrintWriter archivoUsuarios = new PrintWriter("./datosPersistencia/" + nombreSalidaUsuarios);
		PrintWriter archivoPagos = new PrintWriter("./datosPersistencia/" + nombreSalidaPagos);
		piezas.write(archivoPiezas, 2, 0);
		archivoPiezas.close();
		usuarios.write(archivoUsuarios, 2, 0);
		archivoUsuarios.close();
		pagos.write(archivoPagos,2,0);
		archivoPagos.close();
		

	}

	private static JSONObject salvarPiezasEnInventario(IPiezas galeria) {
		JSONObject raiz = new JSONObject();
		JSONArray piezas = new JSONArray();
		Map<Integer, PiezaEnInventario> inventarioActual = galeria.getInventarioActual();
		for (Integer idPieza : inventarioActual.keySet()) {
			PiezaEnInventario pieza = inventarioActual.get(idPieza);
			JSONObject piezaEnInventario = new JSONObject();
			piezaEnInventario.put("pieza", pieza.getPieza().createJSONObject());
			if (pieza.getUbicacionEnGaleria() == PiezaEnInventario.BODEGA) {
				piezaEnInventario.put("ubicacionEnGaleria", "BODEGA");
			} else if (pieza.getUbicacionEnGaleria() == PiezaEnInventario.ENEXHIBICION) {
				piezaEnInventario.put("ubicacionEnGaleria", "ENEXHIBICION");
			}
			piezaEnInventario.put("id", pieza.getId());
			piezaEnInventario.put("enConsignacion", pieza.isEnConsignacion());
			piezaEnInventario.put("salidaConsignacion", pieza.getSalidaConsignacion());
			piezaEnInventario.put("usernameDuenio", pieza.getDuenio().getUsername());
			piezaEnInventario.put("puedeSalir", pieza.isPuedeSalir());
			
			JSONArray historialDueniosJSON = new JSONArray();
			for (Cliente duenio : pieza.getHistorialDuenios()) {
				historialDueniosJSON.put(duenio.getUsername());
			}
			piezaEnInventario.put("historialDuenios", historialDueniosJSON);
			
			JSONArray historialPagosJSON = new JSONArray();
			for (Pago pago:pieza.getPagosPieza()) {
				historialPagosJSON.put(pago.getId());
			}
			piezaEnInventario.put("pagosPorPieza", historialPagosJSON);
			
			piezas.put(piezaEnInventario);
		}
		Map<Integer, PiezaEnInventario> inventarioHistorico = galeria.getInventarioHistorico();
		for (Integer idPieza : inventarioHistorico.keySet()) {
			PiezaEnInventario pieza = inventarioHistorico.get(idPieza);
			JSONObject piezaEnInventario = new JSONObject();
			piezaEnInventario.put("pieza", pieza.getPieza().createJSONObject());

			piezaEnInventario.put("ubicacionEnGaleria", "FUERADEGALERIA");
		

			piezaEnInventario.put("id", pieza.getId());
			piezaEnInventario.put("enConsignacion", pieza.isEnConsignacion());
			piezaEnInventario.put("salidaConsignacion", pieza.getSalidaConsignacion());
			piezaEnInventario.put("usernameDuenio", pieza.getDuenio().getUsername());
			piezaEnInventario.put("puedeSalir", pieza.isPuedeSalir());
			
			JSONArray historialDueniosJSON = new JSONArray();
			for (Cliente duenio : pieza.getHistorialDuenios()) {
				historialDueniosJSON.put(duenio.getUsername());
			}
			piezaEnInventario.put("historialDuenios", historialDueniosJSON);
			
			JSONArray historialPagosJSON = new JSONArray();
			for (Pago pago:pieza.getPagosPieza()) {
				historialPagosJSON.put(pago.getId());
			}
			piezaEnInventario.put("pagosPorPieza", historialPagosJSON);
			
			piezas.put(piezaEnInventario);
		}
		raiz.put("PiezasEnInventario", piezas);
		return raiz;
	}

	private static JSONObject salvarUsuarios(IUsuarios galeria) {
		JSONObject raiz = new JSONObject();
		JSONArray usuarios = new JSONArray();
		for (String usernameUsuario : galeria.getUsuarios().keySet()) {
			JSONObject usuarioJson = new JSONObject();
			Usuario usuario = galeria.getUsuarios().get(usernameUsuario);
			usuarioJson.put("username", usuario.getUsername());
			usuarioJson.put("password", usuario.getPassword());
			int rolInt = usuario.getRol();
			if (rolInt == Usuario.ADMINISTRADOR) {
				usuarioJson.put("rol", "ADMINISTRADOR");
			} else if (rolInt == Usuario.CAJERO) {
				usuarioJson.put("rol", "CAJERO");
			} else if (rolInt == Usuario.OPERADOR) {
				usuarioJson.put("rol", "OPERADOR");
			} else if (rolInt == Usuario.CLIENTE) {
				usuarioJson.put("rol", "CLIENTE");
				Cliente cliente = galeria.getClientes().get(usernameUsuario);
				usuarioJson.put("nombre", cliente.getNombre());
				usuarioJson.put("correo", cliente.getCorreo());

				JSONArray piezasCompradas = new JSONArray();
				for (PiezaEnInventario pieza : cliente.getPiezasCompradas()) {
					piezasCompradas.put(pieza.getId());
				}
				usuarioJson.put("piezasCompradas", piezasCompradas);

				usuarioJson.put("valorMaximoCompras", cliente.getValorMaximoCompras());
				usuarioJson.put("valorActualCompras", cliente.getValorActualCompras());

				JSONArray historialPagosJSON = new JSONArray();
				for (Pago pago:cliente.getPagosRealizados()) {
					historialPagosJSON.put(pago.getId());
				}
				usuarioJson.put("pagosDelUsuario", historialPagosJSON);				
				
				JSONArray piezasEnPosesionVigente = new JSONArray();
				for (PiezaEnInventario pieza : cliente.getPiezasEnPosesionVigente()) {
					piezasEnPosesionVigente.put(pieza.getId());
				}
				usuarioJson.put("piezasEnPosesionVigente", piezasEnPosesionVigente);

				JSONArray piezasEnPosesionNoVigente = new JSONArray();
				for (PiezaEnInventario pieza : cliente.getPiezasEnPosesionNoVigente()) {
					piezasEnPosesionNoVigente.put(pieza.getId());
				}
				usuarioJson.put("piezasEnPosesionNoVigente", piezasEnPosesionNoVigente);
			}
			usuarios.put(usuarioJson);
		}
		raiz.put("usuarios", usuarios);
		return raiz;
	}
	
	private static JSONObject salvarPagosRegistrados(IPagos galeria) {
		JSONObject raiz = new JSONObject();
		JSONArray pagos = new JSONArray();
		for (Pago pago : galeria.getPagosProcesados()) {
			JSONObject pagoJson = new JSONObject();
			pagoJson.put("id", pago.getId());
			pagoJson.put("fecha", pago.getFecha());
			pagoJson.put("idPieza",pago.getPieza().getId());
			pagoJson.put("usernameComprador", pago.getComprador().getUsername());
			pagoJson.put("monto", pago.getMonto());
			int medioDePago = pago.getMedioDePago();
			if (medioDePago == Pago.TARJETA) {
				pagoJson.put("medioDePago", "TARJETA");
			}
			else if (medioDePago == Pago.TRANSFERENCIA) {
				pagoJson.put("medioDePago", "TRANSFERENCIA");
			}
			else if (medioDePago == Pago.EFECTIVO) {
				pagoJson.put("medioDePago", "EFECTIVO");
			}
			else {
				pagoJson.put("medioDePago", "NO ESPECIFICADO");
			}
			pagos.put(pagoJson);
		}
		raiz.put("pagosRegistrados", pagos);
		return raiz;
	}
	
}
