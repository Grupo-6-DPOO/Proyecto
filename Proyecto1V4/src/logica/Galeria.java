package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galeria implements IUsuarios, IPiezas, IVentas, ISubastas, IPagos, IArtistas {
	private Map<String, Cliente> clientes;
	private Map<Integer, PiezaEnInventario> inventarioActual;
	private Map<Integer, PiezaEnInventario> inventarioHistorico;
	private List<PiezaEnInventario> piezasConsignacionVencida;
	private Map<String, Usuario> usuarios;
	private List<VentaValorFijo> ventasActivas;
	private List<VentaValorFijo> ventasSolicitudVenta;
	private List<Subasta> subastasActivas;
	private List<Subasta> subastasTerminadas;
	private List<Pago> pagosPendientes;
	private List<Pago> pagosProcesados;
	private List<Artista> artistas;

	public Galeria() {
		super();
		this.clientes = new HashMap<String, Cliente>();
		this.inventarioActual = new HashMap<Integer, PiezaEnInventario>();
		this.inventarioHistorico = new HashMap<Integer, PiezaEnInventario>();
		this.piezasConsignacionVencida = new ArrayList<PiezaEnInventario>();
		this.usuarios = new HashMap<String, Usuario>();
		this.ventasActivas = new ArrayList<VentaValorFijo>();
		this.ventasSolicitudVenta = new ArrayList<VentaValorFijo>();
		this.subastasActivas = new ArrayList<Subasta>();
		this.subastasTerminadas = new ArrayList<Subasta>();
		this.pagosPendientes = new ArrayList<Pago>();
		this.pagosProcesados = new ArrayList<Pago>();
		this.artistas = new ArrayList<Artista>();
	}

	@Override
	public Map<String, Cliente> getClientes() {
		return clientes;
	}

	@Override
	public Map<Integer, PiezaEnInventario> getInventarioActual() {
		return inventarioActual;
	}

	@Override
	public Map<Integer, PiezaEnInventario> getInventarioHistorico() {
		return inventarioHistorico;
	}

	@Override
	public List<PiezaEnInventario> getPiezasConsignacionVencida() {
		return piezasConsignacionVencida;
	}

	@Override
	public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}

	@Override
	public List<VentaValorFijo> getVentasActivas() {
		return ventasActivas;
	}

	@Override
	public List<VentaValorFijo> getVentasSolicitudVenta() {
		return ventasSolicitudVenta;
	}

	@Override
	public List<Subasta> getSubastasActivas() {
		return subastasActivas;
	}

	@Override
	public List<Subasta> getSubastasTerminadas() {
		return subastasTerminadas;
	}

	@Override
	public List<Pago> getPagosPendientes() {
		return pagosPendientes;
	}

	@Override
	public List<Pago> getPagosProcesados() {
		return pagosProcesados;
	}

	@Override
	public void crearPiezaInventario(Pieza pieza, int ubicacionGaleria, boolean enConsignacion,
			String salidaConsignacion, String username, boolean puedeSalir) throws Exception {
		if (!this.getClientes().containsKey(username)) {
			throw new Exception("El documento no corresponde a ningún cliente");
		}
		if (ubicacionGaleria == PiezaEnInventario.FUERADEGALERIA) {
			throw new Exception("No puede crear una pieza y ubicarla fuera del inventario");
		}
		Cliente duenio = this.getClientes().get(username);
		List<Cliente> listaDuenios = new ArrayList<Cliente>();
		listaDuenios.add(duenio);
		List<Pago> listaPagos = new ArrayList<Pago>();
		PiezaEnInventario piezaInventario = new PiezaEnInventario(pieza, ubicacionGaleria, enConsignacion,
				salidaConsignacion, duenio, puedeSalir, listaDuenios, listaPagos);
		agregarPiezaInventario(piezaInventario);
	}

	@Override
	public void agregarPiezaInventario(PiezaEnInventario pieza) {
		Cliente duenio = pieza.getDuenio();
		this.getInventarioActual().put(pieza.getId(), pieza);
		duenio.agregarPiezaPosesionVigente(pieza);
	}

	@Override
	public List<PiezaEnInventario> buscarPiezaNombre(String nombre) {
		List<PiezaEnInventario> listResult = new ArrayList<PiezaEnInventario>();
		for (Integer id : this.getInventarioActual().keySet()) {
			PiezaEnInventario pieza = this.getInventarioActual().get(id);
			if (pieza.getPieza().getTitulo().equals(nombre)) {
				listResult.add(pieza);
			}
		}
		for (Integer id : this.getInventarioHistorico().keySet()) {
			PiezaEnInventario pieza = this.getInventarioHistorico().get(id);
			if (pieza.getPieza().getTitulo().equals(nombre)) {
				listResult.add(pieza);
			}
		}
		return listResult;
	}

	@Override
	public void sacarPieza(PiezaEnInventario pieza) throws Exception {
		if ((pieza.getUbicacionEnGaleria() == PiezaEnInventario.FUERADEGALERIA)
				|| (!this.getInventarioActual().containsKey(pieza.getId()))) {
			throw new Exception("Esta pieza está fuera de la galería");
		}
		if (!pieza.isPuedeSalir()) {
			throw new Exception("Esta pieza no puede salir de la galería");
		}
		pieza.setUbicacionEnGaleria(PiezaEnInventario.FUERADEGALERIA);
		Cliente duenio = pieza.getDuenio();
		duenio.eliminarPiezaPosesionVigente(pieza);
		duenio.agregarPiezaPosesionNoVigente(pieza);
		this.getInventarioActual().remove(pieza.getId());
		this.getInventarioHistorico().put(pieza.getId(), pieza);
		this.getPiezasConsignacionVencida().remove(pieza);
	}

	@Override
	public void cambiarDuenioPiezaPorCompra(PiezaEnInventario pieza, Cliente nuevoDuenio) throws Exception {
		Cliente duenioAntiguo = pieza.getDuenio();
		pieza.cambiarDuenio(nuevoDuenio);
		duenioAntiguo.eliminarPiezaPosesionVigente(pieza);
		nuevoDuenio.agregarPiezaComprada(pieza);
		nuevoDuenio.agregarPiezaPosesionVigente(pieza);
	}

	@Override
	public void moverPiezaEnGaleria(PiezaEnInventario pieza, int destino) throws Exception {
		if (!this.getInventarioActual().containsKey(pieza.getId())) {
			throw new Exception("No puede mover una pieza que no esté actualmente en la galería");
		}
		if ((destino != PiezaEnInventario.BODEGA) & (destino != PiezaEnInventario.ENEXHIBICION)) {
			throw new Exception("Ingrese un destino valido");
		}
		pieza.setUbicacionEnGaleria(destino);
	}

	@Override
	public Subasta crearSubastaVacia() {
		List<PiezaEnInventario> piezas = new ArrayList<PiezaEnInventario>();
		Map<PiezaEnInventario, Integer> valorActual = new HashMap<PiezaEnInventario, Integer>();
		Map<PiezaEnInventario, Integer> valorMinimo = new HashMap<PiezaEnInventario, Integer>();
		Map<PiezaEnInventario, List<Oferta>> ofertasPasadas = new HashMap<PiezaEnInventario, List<Oferta>>();
		Map<PiezaEnInventario, Oferta> mayorOferta = new HashMap<PiezaEnInventario, Oferta>();
		Map<PiezaEnInventario, Boolean> bloqueadaVenta = new HashMap<PiezaEnInventario, Boolean>();
		List<Cliente> participantes = new ArrayList<Cliente>();
		Map<PiezaEnInventario, Boolean> vendida = new HashMap<PiezaEnInventario, Boolean>();
		List<PiezaEnInventario> piezasVentaSolicitada = new ArrayList<PiezaEnInventario>();
		Subasta subasta = new Subasta(piezas, valorActual, valorMinimo, mayorOferta, ofertasPasadas, bloqueadaVenta,
				vendida, participantes, false, piezasVentaSolicitada);
		this.getSubastasActivas().add(subasta);
		return subasta;
	}

	@Override
	public void agregarPiezaSubasta(Subasta subasta, Integer idPieza, Integer valorMinimo, Integer valorInicial)
			throws Exception {
		if (!this.getInventarioActual().containsKey(idPieza)) {
			throw new Exception("No se encuentra ninguna pieza en la galería con ese id");
		}
		PiezaEnInventario pieza = this.getInventarioActual().get(idPieza);
		if (subasta.isTerminada()) {
			throw new Exception("No puede agregar una pieza a una subasta terminada");
		}
		for (VentaValorFijo venta : this.getVentasActivas()) {
			if (venta.getPieza().equals(pieza)) {
				throw new Exception("Esta pieza está en venta en otro lado");
			}
		}
		for (Subasta subastait : this.getSubastasActivas()) {

			if (subastait.getPiezas().contains(pieza)) {
				throw new Exception("Esta pieza está en venta en otro lado");
			}
		}
		subasta.getPiezas().add(pieza);
		subasta.getValorActual().put(pieza, valorInicial);
		subasta.getValorMinimo().put(pieza, valorMinimo);
		subasta.getMayorOferta().put(pieza, null);
		subasta.getOfertasPasadas().put(pieza, new ArrayList<Oferta>());
		subasta.getBloqueadaVenta().put(pieza, false);
	}

	@Override
	public void agregarParticipanteSubasta(Subasta subasta, String username) throws Exception {
		if (!this.getClientes().containsKey(username)) {
			throw new Exception("No hay un cliente registrado con ese documento");
		}
		Cliente cliente = this.getClientes().get(username);
		subasta.getParticipantes().add(cliente);
	}

	@Override
	public void crearOfertaSubasta(Subasta subasta, String username, int valor, Integer idPieza) throws Exception {
		if (!this.getClientes().containsKey(username)) {
			throw new Exception("No existe el cliente");
		}
		if (!this.getInventarioActual().containsKey(idPieza)) {
			throw new Exception("No existe la pieza en el inventario actual");
		}
		Cliente cliente = this.getClientes().get(username);
		PiezaEnInventario pieza = this.getInventarioActual().get(idPieza);
		subasta.agregarOferta(cliente, valor, pieza);
	}

	@Override
	public void solicitarVentaSubasta(Subasta subasta, Integer idPieza) throws Exception {
		if (!this.getInventarioActual().containsKey(idPieza)) {
			throw new Exception("No existe la pieza en el inventario actual");
		}
		PiezaEnInventario pieza = this.getInventarioActual().get(idPieza);
		subasta.solicitarVentaSubastaPieza(pieza);
	}

	@Override
	public void aprobarVentaSubasta(Subasta subasta, PiezaEnInventario pieza) throws Exception {
		if (!subasta.getPiezas().contains(pieza)) {
			throw new Exception("Esta pieza no está en la subasta");
		}

		if (!subasta.getBloqueadaVenta().get(pieza).equals(true)) {
			throw new Exception("Esta pieza no se ha solicitado su venta");
		}
		Oferta ofertaGanadora = subasta.getMayorOferta().get(pieza);
		Cliente comprador = ofertaGanadora.getComprador();
		int monto = ofertaGanadora.getMonto();
		Pago pago = new Pago(Pago.NOESPECIFICADO, false, pieza, comprador, monto,null);
		this.getPagosPendientes().add(pago);

	}

	@Override
	public void rechazarVentaSubasta(Subasta subasta, PiezaEnInventario pieza) throws Exception {
		if (!subasta.getPiezas().contains(pieza)) {
			throw new Exception("Esta pieza no está en la subasta");
		}

		if (!subasta.getBloqueadaVenta().get(pieza).equals(true)) {
			throw new Exception("Esta pieza no se ha solicitado su venta");
		}

		subasta.getBloqueadaVenta().put(pieza, false);
		subasta.getPiezasVentaSolicitada().remove(pieza);
	}

	@Override
	public void aprobarPago(Pago pago, int medioDePago, String fecha) throws Exception {
		pago.aprobarPago(medioDePago, fecha);
		this.getPagosPendientes().remove(pago);
		this.getPagosProcesados().add(pago);
		this.cambiarDuenioPiezaPorCompra(pago.getPieza(), pago.getComprador());
	}

	@Override
	public void finalizarSubasta(Subasta subasta) {
		subasta.setTerminada(true);
		this.getSubastasActivas().remove(subasta);
		this.getSubastasTerminadas().add(subasta);
	}

	@Override
	public void crearVenta(Integer idPieza, int precio) throws Exception {
		if (!this.getInventarioActual().containsKey(idPieza)) {
			throw new Exception("No hay pieza con este id");
		}
		PiezaEnInventario pieza = this.getInventarioActual().get(idPieza);
		for (VentaValorFijo venta : this.getVentasActivas()) {
			if (venta.getPieza().equals(pieza)) {
				throw new Exception("Esta pieza está en venta en otro lado");
			}
		}
		for (Subasta subastait : this.getSubastasActivas()) {

			if (subastait.getPiezas().contains(pieza)) {
				throw new Exception("Esta pieza está en venta en otro lado");
			}
		}

		VentaValorFijo venta = new VentaValorFijo(pieza, precio, false, null);
		this.ventasActivas.add(venta);
	}

	@Override
	public void solicitarVentaValorFijo(VentaValorFijo venta, Cliente comprador) throws Exception {
		venta.solicitarVentaValorFijo(comprador);
		this.getVentasActivas().remove(venta);
		this.getVentasSolicitudVenta().add(venta);
	}

	@Override
	public void aprobarVentaValorFijo(VentaValorFijo venta) throws Exception {
		if (!venta.isBloqueada()) {
			throw new Exception("Esta venta no está bloqueada");
		}
		Pago pago = new Pago(Pago.NOESPECIFICADO, false, venta.getPieza(), venta.getCompradorPotencial(),
				venta.getPrecio(),null);
		this.getPagosPendientes().add(pago);
		this.getVentasSolicitudVenta().remove(venta);

	}

	@Override
	public void rechazarVentaValorFijo(VentaValorFijo venta) throws Exception {
		if (!venta.isBloqueada()) {
			throw new Exception("Esta venta no está bloqueada");
		}
		venta.setBloqueada(false);
		this.getVentasActivas().add(venta);
		this.getVentasSolicitudVenta().remove(venta);
		venta.setCompradorPotencial(null);
	}

	@Override
	public void aumentarMontoCliente(Cliente cliente, int nuevoMonto) {
		cliente.setValorMaximoCompras(nuevoMonto);
	}

	@Override
	public List<Cliente> buscarClientePorNombre(String nombre) {
		List<Cliente> result = new ArrayList<Cliente>();
		for (String usernameCliente : this.getClientes().keySet()) {
			Cliente cliente = this.getClientes().get(usernameCliente);
			if (cliente.getNombre().equals(nombre)) {
				result.add(cliente);
			}
		}
		return result;
	}

	@Override
	public void crearNuevoCliente(String username, String password, String nombre, String correo,
			int valorMaximoCompras) throws Exception {
		Cliente cliente = new Cliente(username, password, Usuario.CLIENTE, nombre, correo,
				new ArrayList<PiezaEnInventario>(), valorMaximoCompras, 0, new ArrayList<PiezaEnInventario>(),
				new ArrayList<PiezaEnInventario>(), new ArrayList<Pago>());
		this.getClientes().put(username, cliente);
		this.getUsuarios().put(username, cliente);
	}

	@Override
	public List<Artista> getArtistas() {
		return artistas;
	}

	@Override
	public void crearNuevoPagoHistorial(int medioDePago, boolean pagado, int IDPieza, String usernameComprador,
			int monto, int id,String fecha) {
		Cliente comprador = this.getClientes().get(usernameComprador);

		PiezaEnInventario pieza = null;
		if (this.getInventarioActual().containsKey(Integer.valueOf(IDPieza))) {
			pieza = this.getInventarioActual().get(Integer.valueOf(IDPieza));
		} else {
			pieza = this.getInventarioHistorico().get(Integer.valueOf(IDPieza));
		}
		Pago pago = new Pago(medioDePago, pagado, pieza, comprador, monto, id,fecha);
		List<Artista> artistas = pieza.getPieza().getArtistas();
		for (Artista artista : artistas) {
			artista.agregarPago(pago);
		}
		pieza.agregarPago(pago);
		comprador.agregarPago(pago);

	}

	@Override
	public void crearNuevoUsuario(String username, String password, int rol) throws Exception {
		Usuario usuario = new Usuario(username, password, rol);
		this.getUsuarios().put(username, usuario);
	}

	@Override
	public void revisarConsignaciones(String fechaActual) {
		Map<Integer, PiezaEnInventario> inventario = this.getInventarioActual();
		for (Integer idPieza : inventario.keySet()) {
			PiezaEnInventario pieza = inventario.get(idPieza);
			if (pieza.revisarConsignacionVencidaPieza(fechaActual)) {
				this.getPiezasConsignacionVencida().add(pieza);
				pieza.setPuedeSalir(true);
			}
		}
	}

	@Override
	public Usuario verificarUsuario(String username, String password) throws Exception {
		if (!this.getUsuarios().containsKey(username)) {
			throw new Exception("El usuario no está registrado");
		}
		if (!this.getUsuarios().get(username).getPassword().equals(password)) {
			throw new Exception("La constraseña es incorrecta");
		}
		Usuario usuario = this.getUsuarios().get(username);
		return usuario;
	}

	@Override
	public Artista obtenerArtista(String nombre) {
		for (int i = 0; i < this.getArtistas().size(); i++) {
			Artista artista = this.getArtistas().get(i);
			if (artista.getNombre().equals(nombre)) {
				return artista;
			}
		}
		Artista artista = null;
		artista = new Artista(nombre, new ArrayList<Pieza>(), new ArrayList<Pago>());
		this.artistas.add(artista);
		return artista;
	}

	@Override
	public List<Artista> listaNombresAListaArtistas(List<String> listaNombres) {
		List<Artista> listaArtistas = new ArrayList<Artista>();
		for (String nombreArtista : listaNombres) {
			listaArtistas.add(obtenerArtista(nombreArtista));
		}
		return listaArtistas;
	}

	@Override
	public Escultura nuevaEscultura(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, String dimensiones, String material, String peso, boolean necesitaElectricidad) {
		List<Artista> listaArtistas = listaNombresAListaArtistas(nombresArtistas);
		return new Escultura(titulo, anio, lugarCreacion, listaArtistas, observaciones, dimensiones, material, peso,
				necesitaElectricidad);
	}

	@Override
	public Fotografia nuevaFotografia(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, String resolucion) {
		List<Artista> listaArtistas = listaNombresAListaArtistas(nombresArtistas);
		return new Fotografia(titulo, anio, lugarCreacion, listaArtistas, observaciones, resolucion);
	}

	@Override
	public Pintura nuevaPintura(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, String dimensiones, String material) {
		List<Artista> listaArtistas = listaNombresAListaArtistas(nombresArtistas);
		return new Pintura(titulo, anio, lugarCreacion, listaArtistas, observaciones, dimensiones, material);
	}

	@Override
	public Video nuevoVideo(String titulo, String anio, String lugarCreacion, List<String> nombresArtistas,
			String observaciones, int duracion, String resolucion) {
		List<Artista> listaArtistas = listaNombresAListaArtistas(nombresArtistas);
		return new Video(titulo, anio, lugarCreacion, listaArtistas, observaciones, duracion, resolucion);
	}

	@Override
	public Artista buscarArtistaPorNombre(String nombre) throws Exception {
		Artista artistaResult = null;
		for (Artista artista : this.getArtistas()) {
			if (artista.getNombre().equals(nombre)) {
				artistaResult = artista;
			}
		}
		if (artistaResult.equals(null)) {
			throw new Exception("No se encuentra ese artista");
		}
		return artistaResult;
	}

}
