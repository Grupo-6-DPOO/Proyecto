package persistencia;

import logica.Cliente;
import logica.Escultura;
import logica.Fotografia;
import logica.Galeria;
import logica.Pieza;
import logica.PiezaEnInventario;
import logica.Pintura;
import logica.Usuario;
import logica.Video;

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

public class CentralPersistencia {
	
	public static Galeria cargarDatos(String archivoUsuarios, String archivoPiezas) throws Exception {
		Galeria galeria = new Galeria();
		guardarDatos(galeria,"./datosPersistencia/"+ archivoUsuarios, "./datosPersistencia/"+archivoPiezas);
		return galeria;
	}
	private static void guardarDatos(Galeria galeria,String pathArchivoUsuarios, String pathArchivoPiezas) throws Exception {
		Map<String, List<Integer>> piezasCompradas = new HashMap<String, List<Integer>>();
		Map<String, List<Integer>> piezasEnPosesionVigente = new HashMap<String,List<Integer>>();
		Map<String, List<Integer>> piezasEnPosesionNoVigente = new HashMap<String,List<Integer>>();
		
		Map<Integer,String> dueniosPiezas = new HashMap<Integer,String>();
		Map<Integer,List<String>> historialDueniosPiezas = new HashMap<Integer,List<String>>();
		
		cargarUsuarios(galeria, pathArchivoUsuarios, piezasCompradas, piezasEnPosesionVigente, piezasEnPosesionNoVigente);
		cargarPiezas(galeria, pathArchivoPiezas,dueniosPiezas,historialDueniosPiezas);
		combinarCargas(galeria,piezasCompradas,piezasEnPosesionVigente,piezasEnPosesionNoVigente,dueniosPiezas,historialDueniosPiezas);

	}
	
	private static void cargarUsuarios(Galeria galeria,String pathArchivoUsuarios,Map<String, List<Integer>> piezasCompradas,Map<String, List<Integer>> piezasEnPosesionVigente,Map<String, List<Integer>> piezasEnPosesionNoVigente) throws Exception {
		String jsonCompleto = new String(Files.readAllBytes(new File(pathArchivoUsuarios).toPath()));
		JSONObject raiz = new JSONObject( jsonCompleto );		
		JSONArray usuarios = raiz.getJSONArray("usuarios");
		int numUsuarios = usuarios.length();
		for (int i=0; i<numUsuarios;i++) {
			JSONObject usuario = usuarios.getJSONObject(i);
			String rolString = usuario.getString("rol");
			int rol = -1;
			if (rolString.equals("ADMINISTRADOR")) {
				rol = Usuario.ADMINISTRADOR;
			}
			else if (rolString.equals("CAJERO")) {
				rol = Usuario.CAJERO;
			}
			else if (rolString.equals("OPERADOR")) {
				rol = Usuario.ADMINISTRADOR;
			}
			else if (rolString.equals("EMPLEADO")) {
				rol = Usuario.EMPLEADO;
			}
			else if (rolString.equals("CLIENTE")) {
				rol = Usuario.CLIENTE;
			}
			
			String username = usuario.getString("username");
			String password = usuario.getString("password");
			if (rol == Usuario.CLIENTE) {
				String nombre = usuario.getString("nombre");
				String correo = usuario.getString("correo");
				JSONArray piezasCompradasJSONArray = usuario.getJSONArray("piezasCompradas");
				List<Integer> piezasCompradasInteger = new ArrayList<Integer>();
				
				for (int j=0; j<piezasCompradasJSONArray.length();j++) {
					Integer idPieza = piezasCompradasJSONArray.getInt(j);
					piezasCompradasInteger.add(idPieza);
				}
				piezasCompradas.put(username, piezasCompradasInteger);
				
				int valorMaximoCompras = usuario.getInt("valorMaximoCompras");
				int valorActualCompras = usuario.getInt("valorActualCompras");
				
				JSONArray piezasEnPosesionVigenteJSONArray = usuario.getJSONArray("piezasEnPosesionVigente");
				List<Integer> piezasEnPosesionVigenteInteger = new ArrayList<Integer>();
				for (int j=0; j<piezasEnPosesionVigenteJSONArray.length();j++) {
					Integer idPieza = piezasEnPosesionVigenteJSONArray.getInt(j);
					piezasEnPosesionVigenteInteger.add(idPieza);
				}
				piezasEnPosesionVigente.put(username, piezasEnPosesionVigenteInteger);
				
				
				JSONArray piezasEnPosesionNoVigenteJSONArray = usuario.getJSONArray("piezasEnPosesionNoVigente");
				List<Integer> piezasEnPosesionNoVigenteInteger = new ArrayList<Integer>();
				for (int j=0; j<piezasEnPosesionNoVigenteJSONArray.length();j++) {
					Integer idPieza = piezasEnPosesionNoVigenteJSONArray.getInt(j);
					piezasEnPosesionNoVigenteInteger.add(idPieza);
				}
				piezasEnPosesionNoVigente.put(username, piezasEnPosesionNoVigenteInteger);
				
				galeria.crearNuevoCliente(username, password, nombre, correo, valorMaximoCompras);
				galeria.getClientes().get(username).setValorActualCompras(valorActualCompras);
			}
			else {
				galeria.crearNuevoUsuario(username, password, rol);
			}
		}	
	}
	
	private static void cargarPiezas(Galeria galeria, String pathArchivoPiezas, Map<Integer,String> dueniosPiezas,Map<Integer,List<String>> historialDueniosPiezas) throws IOException {
		
		String jsonCompleto = new String(Files.readAllBytes( new File( pathArchivoPiezas ).toPath()));
		JSONObject raiz = new JSONObject( jsonCompleto );
		JSONArray piezas = raiz.getJSONArray("PiezasEnInventario");
		int numPiezas = piezas.length();
		for (int i=0;i<numPiezas;i++) {
			JSONObject piezaEnInventarioJson = piezas.getJSONObject(i);
		
			JSONObject piezaObj = piezaEnInventarioJson.getJSONObject("pieza");
			String tipo = piezaObj.getString("tipo");
			String titulo = piezaObj.getString("titulo");
			String anio = piezaObj.getString("anio");
			String lugarCreacion = piezaObj.getString("lugarCreacion");
			JSONArray autoresJson = piezaObj.getJSONArray("autores");
			List<String> autores = new ArrayList<String>();
			for (int j =0;j<autoresJson.length();j++) {
				autores.add(autoresJson.getString(j));
			}
			String observaciones = piezaObj.getString("observaciones");
			
			Pieza pieza = null;
			
			if (tipo.equals("PINTURA")) {
				String dimensiones = piezaObj.getString("dimensiones");
				String material = piezaObj.getString("material");
				pieza = new Pintura(titulo, anio, lugarCreacion, autores, observaciones, dimensiones, material);
			}
			else if (tipo.equals("ESCULTURA")) {
				String dimensiones = piezaObj.getString("dimensiones");
				String material = piezaObj.getString("material");
				String peso = piezaObj.getString("material");
				boolean necesitaElectricidad = piezaObj.getBoolean("necesitaElectricidad");
				pieza = new Escultura(titulo, anio, lugarCreacion, autores, observaciones, dimensiones, material,peso,necesitaElectricidad);
			}
			else if (tipo.equals("VIDEO")) {
				String duracion = piezaObj.getString("duracion");
				String resolucion = piezaObj.getString("resolucion");
				pieza = new Video(titulo, anio, lugarCreacion, autores, observaciones, Integer.parseInt(duracion),resolucion);
			}
			else if (tipo.equals("FOTOGRAFIA")) {
				String resolucion = piezaObj.getString("resolucion");
				pieza = new Fotografia(titulo, anio, lugarCreacion, autores, observaciones,resolucion);
			}
			
			String ubicacionEnGaleriaString = piezaEnInventarioJson.getString("ubicacionEnGaleria");
			int ubicacionEnGaleria = -1;
			
			if (ubicacionEnGaleriaString.equals("ENEXHIBICION")) {
				ubicacionEnGaleria = PiezaEnInventario.ENEXHIBICION;
			}
			else if (ubicacionEnGaleriaString.equals("BODEGA")) {
				ubicacionEnGaleria = PiezaEnInventario.BODEGA;
			}
			else if (ubicacionEnGaleriaString.equals("FUERADEGALERIA")) {
				ubicacionEnGaleria = PiezaEnInventario.FUERADEGALERIA;
			}
			
			boolean enConsignacion = piezaEnInventarioJson.getBoolean("enConsignacion");
			String salidaConsignacion = piezaEnInventarioJson.getString("salidaConsignacion");
			String usernameDuenio = piezaEnInventarioJson.getString("usernameDuenio");
			boolean puedeSalir = piezaEnInventarioJson.getBoolean("puedeSalir");
			JSONArray historialDueniosJson = piezaEnInventarioJson.getJSONArray("historialDuenios");
			List<String> historialDuenios = new ArrayList<String>();
			for (int j=0;j<historialDueniosJson.length();j++) {
				historialDuenios.add(historialDueniosJson.getString(j));
			}
			Integer id = piezaEnInventarioJson.getInt("id");
			dueniosPiezas.put(id, usernameDuenio);
			historialDueniosPiezas.put(id, historialDuenios);
			List<Cliente> historialDueniosCliente = new ArrayList<Cliente>();
			PiezaEnInventario piezaEnInventario = new PiezaEnInventario(pieza, ubicacionEnGaleria, enConsignacion, salidaConsignacion, null, puedeSalir, historialDueniosCliente, id);
			if (ubicacionEnGaleria == PiezaEnInventario.FUERADEGALERIA) {
				galeria.getInventarioHistorico().put(id, piezaEnInventario);
			}
			else {
				galeria.getInventarioActual().put(id, piezaEnInventario);
			}
			

		}
	}
	private static void combinarCargas(Galeria galeria,Map<String, List<Integer>> piezasCompradas,Map<String, List<Integer>> piezasEnPosesionVigente,Map<String, List<Integer>> piezasEnPosesionNoVigente,Map<Integer,String> dueniosPiezas,Map<Integer,List<String>> historialDueniosPiezas) {
		for (String username:piezasCompradas.keySet()){
			Cliente cliente = galeria.getClientes().get(username);
			for (Integer idPieza:piezasCompradas.get(username)) {
				if (galeria.getInventarioActual().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioActual().get(idPieza);
					cliente.getPiezasCompradas().add(pieza);
				}
				else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioHistorico().get(idPieza);
					cliente.getPiezasCompradas().add(pieza);
				}
				
			}
		}
		for (String username:piezasEnPosesionVigente.keySet()){
			Cliente cliente = galeria.getClientes().get(username);
			for (Integer idPieza:piezasEnPosesionVigente.get(username)) {
				if (galeria.getInventarioActual().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioActual().get(idPieza);
					cliente.getPiezasEnPosesionVigente().add(pieza);
				}
				else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioHistorico().get(idPieza);
					cliente.getPiezasEnPosesionVigente().add(pieza);
				}
				
			}
		}
		
		for (String username:piezasEnPosesionNoVigente.keySet()){
			Cliente cliente = galeria.getClientes().get(username);
			for (Integer idPieza:piezasEnPosesionNoVigente.get(username)) {
				if (galeria.getInventarioActual().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioActual().get(idPieza);
					cliente.getPiezasEnPosesionNoVigente().add(pieza);
				}
				else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
					PiezaEnInventario pieza = galeria.getInventarioHistorico().get(idPieza);
					cliente.getPiezasEnPosesionNoVigente().add(pieza);
				}
			}
		}
		
		for (Integer idPieza:dueniosPiezas.keySet()) {
			PiezaEnInventario pieza = null;
			String usernameDuenio = dueniosPiezas.get(idPieza);
			Cliente duenio = galeria.getClientes().get(usernameDuenio);
			if (galeria.getInventarioActual().containsKey(idPieza)) {
				pieza = galeria.getInventarioActual().get(idPieza);
				pieza.setDuenio(duenio);
			}
			else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
				pieza = galeria.getInventarioHistorico().get(idPieza);
				pieza.setDuenio(duenio);
			}
		}
		
		for (Integer idPieza:historialDueniosPiezas.keySet()) {
			PiezaEnInventario pieza = null;
			if (galeria.getInventarioActual().containsKey(idPieza)) {
				pieza = galeria.getInventarioActual().get(idPieza);
			}
			else if (galeria.getInventarioHistorico().containsKey(idPieza)) {
				pieza = galeria.getInventarioHistorico().get(idPieza);
			}
			
			for (String usernameDuenio:historialDueniosPiezas.get(idPieza)) {
				Cliente duenio = galeria.getClientes().get(usernameDuenio);
				pieza.getHistorialDuenios().add(duenio);
			}

		}
	}
	
	public static void salvarDatos(Galeria galeria, String nombreSalidaPiezas, String nombreSalidaUsuarios) throws FileNotFoundException {
		JSONObject piezas = salvarPiezasEnInventario(galeria);
		JSONObject usuarios = salvarUsuarios(galeria);
		PrintWriter archivoPiezas = new PrintWriter( "./datosPersistencia/"+nombreSalidaPiezas );
		PrintWriter archivoUsuarios = new PrintWriter( "./datosPersistencia/"+nombreSalidaUsuarios );
		piezas.write( archivoPiezas, 2, 0 );
		archivoPiezas.close();
		usuarios.write(archivoUsuarios,2,0);
		archivoUsuarios.close();
		
	}
	
	
	
	private static JSONObject salvarPiezasEnInventario(Galeria galeria) {
		JSONObject raiz = new JSONObject();
		JSONArray piezas = new JSONArray();
		Map<Integer,PiezaEnInventario>  inventarioActual = galeria.getInventarioActual();
		for (Integer idPieza: inventarioActual.keySet()) {
			PiezaEnInventario pieza = inventarioActual.get(idPieza);
			JSONObject piezaEnInventario = new JSONObject();
			piezaEnInventario.put("pieza", pieza.getPieza().createJSONObject());
			if (pieza.getUbicacionEnGaleria()==PiezaEnInventario.BODEGA) {
				piezaEnInventario.put("ubicacionEnGaleria", "BODEGA");
			}
			else if (pieza.getUbicacionEnGaleria()==PiezaEnInventario.ENEXHIBICION) {
				piezaEnInventario.put("ubicacionEnGaleria", "ENEXHIBICION");
			}
			piezaEnInventario.put("enConsignacion", pieza.isEnConsignacion());
			piezaEnInventario.put("usernameDuenio", pieza.getDuenio().getUsername());
			piezaEnInventario.put("puedeSalir", pieza.isPuedeSalir());
			JSONArray historialDueniosJSON = new JSONArray();
			for (Cliente duenio: pieza.getHistorialDuenios()) {
				historialDueniosJSON.put(duenio.getUsername());
			}
			piezaEnInventario.put("historialDuenios", historialDueniosJSON);
			piezas.put(piezaEnInventario);
		}
		Map<Integer,PiezaEnInventario>  inventarioHistorico = galeria.getInventarioHistorico();
		for (Integer idPieza: inventarioHistorico.keySet()) {
			PiezaEnInventario pieza = inventarioHistorico .get(idPieza);
			JSONObject piezaEnInventario = new JSONObject();
			piezaEnInventario.put("pieza", pieza.getPieza().createJSONObject());
			if (pieza.getUbicacionEnGaleria()==PiezaEnInventario.FUERADEGALERIA) {
				piezaEnInventario.put("ubicacionEnGaleria", "FUERADEGALERIA");
			}
			piezaEnInventario.put("enConsignacion", pieza.isEnConsignacion());
			piezaEnInventario.put("usernameDuenio", pieza.getDuenio().getUsername());
			piezaEnInventario.put("puedeSalir", pieza.isPuedeSalir());
			JSONArray historialDueniosJSON = new JSONArray();
			for (Cliente duenio: pieza.getHistorialDuenios()) {
				historialDueniosJSON.put(duenio.getUsername());
			}
			piezaEnInventario.put("historialDuenios", historialDueniosJSON);
			piezas.put(piezaEnInventario);
		}
		raiz.put("PiezasEnInventario", piezas);
		return raiz;
	}
	
	private static JSONObject salvarUsuarios(Galeria galeria) {
		JSONObject raiz = new JSONObject();
		JSONArray usuarios = new JSONArray();
		for (String usernameUsuario:galeria.getUsuarios().keySet()) {
			JSONObject usuarioJson = new JSONObject();
			Usuario usuario = galeria.getUsuarios().get(usernameUsuario);
			usuarioJson.put("username", usuario.getUsername());
			usuarioJson.put("password", usuario.getPassword());
			int rolInt = usuario.getRol();
			if (rolInt==Usuario.ADMINISTRADOR) {
				usuarioJson.put("rol", "ADMINISTRADOR");
			}
			else if (rolInt==Usuario.CAJERO) {
				usuarioJson.put("rol", "CAJERO");
			}
			else if (rolInt==Usuario.OPERADOR) {
				usuarioJson.put("rol", "OPERADOR");
			}
			else if (rolInt==Usuario.CLIENTE) {
				usuarioJson.put("rol", "CLIENTE");
				Cliente cliente = galeria.getClientes().get(usernameUsuario);
				usuarioJson.put("nombre", cliente.getNombre());
				usuarioJson.put("correo", cliente.getCorreo());
				
				JSONArray piezasCompradas = new JSONArray();
				for (PiezaEnInventario pieza: cliente.getPiezasCompradas()) {
					piezasCompradas.put(pieza.getId());
				}
				usuarioJson.put("piezasCompradas", piezasCompradas);
				
				usuarioJson.put("valorMaximoCompras", cliente.getValorMaximoCompras());
				usuarioJson.put("valorActualCompras", cliente.getValorActualCompras());
				
				JSONArray piezasEnPosesionVigente = new JSONArray();
				for (PiezaEnInventario pieza: cliente.getPiezasEnPosesionVigente()) {
					piezasEnPosesionVigente.put(pieza.getId());
				}
				usuarioJson.put("piezasEnPosesionVigente", piezasEnPosesionVigente);
				
				JSONArray piezasEnPosesionNoVigente = new JSONArray();
				for (PiezaEnInventario pieza: cliente.getPiezasEnPosesionNoVigente()) {
					piezasEnPosesionNoVigente.put(pieza.getId());
				}
				usuarioJson.put("piezasEnPosesionNoVigente", piezasEnPosesionNoVigente);
			}
			usuarios.put(usuarioJson);
		}
		raiz.put("usuarios", usuarios);
		return raiz;
	}
	

	

}
