package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logica.Artista;
import logica.Cliente;
import logica.Galeria;
import logica.PiezaEnInventario;
import logica.Subasta;
import logica.VentaValorFijo;
import logica.Pieza;

public class InterfazAdministrador {
    private static String pedirCadenaAlUsuario( String mensaje )
    {
        try
        {
            System.out.print( mensaje + ": " );
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            String input = reader.readLine( );
            return input;
        }
        catch( IOException e )
        {
            System.out.println( "Error leyendo de la consola" );
        }
        return "error";
    }
	


    
	public static void menuAdministrador(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Manejar inventario");
		System.out.println("2-Manejar ventas");
		System.out.println("3-Manejar subastas");
		System.out.println("4-Manejar clientes");
		System.out.println("5-Consultar artistas");
		System.out.println("6-Cerrar sesión");
		int result = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
		
		if (result==1) {
			menuManejarInventario(galeria);
		}
		
		else if (result==2) {
			menuVentas(galeria);
		}
		
		else if (result==3) {
			menuSubastas(galeria);
		}
		
		else if (result==4) {
			menuClientes(galeria);
		}
		else if (result ==5) {
			menuArtistas(galeria);
		}
		else if (result ==6) {
			InterfazIniciarSesion.menuIniciarSesion(galeria);
		}
		else {
			menuAdministrador(galeria);
		}
		
		
	}
	
	private static void menuManejarInventario(Galeria galeria) {
		System.out.println("***********************");
		
		System.out.println("1-Buscar pieza por nombre");
		System.out.println("2-Agregar pieza");
		System.out.println("3-Manejar consignaciones");
		System.out.println("4-Volver");
		int r1 = Integer.valueOf(pedirCadenaAlUsuario("Seleccione una opción válida")).intValue();
		
		if (r1==1) {
			String r2 = pedirCadenaAlUsuario("Ingrese el nombre de la pieza");
			List<PiezaEnInventario> result= galeria.buscarPiezaNombre(r2);
			System.out.println("0-Volver");
			for (int i=0;i<result.size();i++) {
				PiezaEnInventario pieza = result.get(i);
				System.out.println(Integer.toString(i+1)+"- ID pieza: "+pieza.getId());
			}
			
			int r3 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opcion valida"));
			if (r3==0|result.size()==0) {
				menuManejarInventario(galeria);
					}
			else if (1<=r3 & r3<=result.size()) {
				PiezaEnInventario pieza = result.get(r3-1);
				menuManejarPieza(pieza,galeria);
			}
			else {
				menuManejarInventario(galeria);
			}
			

		}
		else if (r1==2) {
			menuAgregarPieza(galeria);
		}
		else if (r1==3) {
			menuManejarConsignaciones(galeria);
		}
		else if (r1==4) {
			menuAdministrador(galeria);
			}
		else {
			menuManejarInventario(galeria);
		}
	}
	
	private static void menuManejarConsignaciones(Galeria galeria) {
		System.out.println("***********************");
		String fecha = pedirCadenaAlUsuario("Ingrese la fecha de hoy");
		galeria.revisarConsignaciones(fecha);
		System.out.println("Estas piezas están en consignación");
		List<PiezaEnInventario> piezasConsVencida = galeria.getPiezasConsignacionVencida();
		System.out.println("0-Volver");
		for (int i=0;i<piezasConsVencida.size();i++) {
			PiezaEnInventario pieza = piezasConsVencida.get(i);
			System.out.println(Integer.toString(i+1)+"-Id: "+pieza.getId().toString());
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
		if (r1 ==0|piezasConsVencida.size()==0) {
			menuManejarInventario(galeria);
		}
		else if (1<=r1& r1<=piezasConsVencida.size()) {
			PiezaEnInventario piezaSeleccionada = piezasConsVencida.get(r1-1);
			System.out.println("1-Sacar de la galeria");
			System.out.println("2-Volver");
			int r2 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
			if (r2 ==1) {
				try {
					galeria.sacarPieza(piezaSeleccionada);
					menuManejarConsignaciones(galeria);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					menuManejarConsignaciones(galeria);
				}
				
			}
			else {
				menuManejarConsignaciones(galeria);
			}
		}
		else {
			menuManejarConsignaciones(galeria);
		}
	}
	
	private static void menuManejarPieza(PiezaEnInventario pieza,Galeria galeria) {
		System.out.println("***********************");
		System.out.println(pieza.toString());
		System.out.println("1-Sacar pieza de galería");
		System.out.println("2-Mover pieza en galería");
		System.out.println("3-Volver");

		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
	
		if (r1 == 1) {
			try {
				galeria.sacarPieza(pieza);
				 menuManejarInventario(galeria);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuManejarInventario(galeria);
			}
		}
		else if (r1 ==2) {
			System.out.println("1-Bodega");
			System.out.println("2-Exhibición");
			int r2 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
			int ubicacion = -1;
			if (r2 == 1) {
				ubicacion = PiezaEnInventario.BODEGA;
			}
			else if (r2 ==2) {
				ubicacion = PiezaEnInventario.ENEXHIBICION;
			}
			try {
				galeria.moverPiezaEnGaleria(pieza, ubicacion);
				menuManejarPieza(pieza,galeria);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuManejarPieza(pieza,galeria);
			}
		}
		else if (r1 ==3) {
			menuManejarInventario(galeria);
		}
		else {
			menuManejarPieza(pieza,galeria);
		}
	
	}
	
	private static void menuAgregarPieza(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Pintura");
		System.out.println("2-Escultura");
		System.out.println("3-Fotografía");
		System.out.println("4-Video");
		System.out.println("5-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
		if (r1 == 1) {
			String titulo = pedirCadenaAlUsuario("Título");
			List<String> autores= new ArrayList<String>(Arrays.asList(pedirCadenaAlUsuario("Ingrese los artistas (separados por coma)").split(",")));
			String anio = pedirCadenaAlUsuario("Año de creación");
			String lugarCreacion = pedirCadenaAlUsuario("Lugar de creación");
			String observaciones = pedirCadenaAlUsuario("Observaciones");
			
			String dimensiones = pedirCadenaAlUsuario("Dimensiones (en formato x,y y en cm)");
			String material = pedirCadenaAlUsuario("Material");
			
			Pieza pintura = galeria.nuevaPintura(titulo, anio, lugarCreacion, autores, observaciones, dimensiones, material);
			
			int ubicacion = Integer.parseInt(pedirCadenaAlUsuario("Ubicacion (1-Exhibición/2-Bodega)"));
			if (ubicacion==1) {
				ubicacion = PiezaEnInventario.ENEXHIBICION;
			}
			if (ubicacion==2) {
				ubicacion = PiezaEnInventario.BODEGA;
			}
			String enConsignacionstr = pedirCadenaAlUsuario("En consignación (SI/NO)");
			boolean enConsignacion= true;
			if (enConsignacionstr.equals("SI")) {
				enConsignacion=true;
			}
			else {
				enConsignacion=false;
			}
			
			String salidaConsignacion = pedirCadenaAlUsuario("Fecha salida consignación(formato DD/MM/YYY, si no está en consignación, digte _)");
			String usuarioDuenio = pedirCadenaAlUsuario("Usuario dueño");
			String puedeSalirDeGaleria = pedirCadenaAlUsuario("Puede salir de galería (SI/NO)");
			boolean puedeSalir= true;
			if (puedeSalirDeGaleria.equals("SI")) {
				puedeSalir=true;
			}
			else {
				puedeSalir=false;
			}
			
			try {
				galeria.crearPiezaInventario(pintura, ubicacion, enConsignacion, salidaConsignacion, usuarioDuenio, puedeSalir);
				menuManejarInventario(galeria);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuAgregarPieza(galeria); 
			}
			
			
			
		}
		else if (r1==2) {
			String titulo = pedirCadenaAlUsuario("Título");
			List<String> autores= new ArrayList<String>(Arrays.asList(pedirCadenaAlUsuario("Ingrese los artistas (separados por coma)").split(",")));
			String anio = pedirCadenaAlUsuario("Año de creación");
			String lugarCreacion = pedirCadenaAlUsuario("Lugar de creación");
			String observaciones = pedirCadenaAlUsuario("Observaciones");
			
			String dimensiones = pedirCadenaAlUsuario("Dimensiones (en formato x,y,z y en cm)");
			String material = pedirCadenaAlUsuario("Material");
			String peso = pedirCadenaAlUsuario("Peso");
			String necesitaElectricidadstr = pedirCadenaAlUsuario("Necesita electricidad (SI/NO)");
			
			boolean necesitaElectricidad= true;
			if (necesitaElectricidadstr.equals("SI")) {
				necesitaElectricidad=true;
			}
			else {
				necesitaElectricidad=false;
			}
			
			Pieza escultura = galeria.nuevaEscultura(titulo, anio, lugarCreacion, autores, observaciones, dimensiones, material,peso,necesitaElectricidad);
			
			int ubicacion = Integer.parseInt(pedirCadenaAlUsuario("Ubicacion (1-Exhibición/2-Bodega)"));
			if (ubicacion==1) {
				ubicacion = PiezaEnInventario.ENEXHIBICION;
			}
			if (ubicacion==2) {
				ubicacion = PiezaEnInventario.BODEGA;
			}
			String enConsignacionstr = pedirCadenaAlUsuario("En consignación (SI/NO)");
			boolean enConsignacion= true;
			if (enConsignacionstr.equals("SI")) {
				enConsignacion=true;
			}
			else {
				enConsignacion=false;
			}
			
			String salidaConsignacion = pedirCadenaAlUsuario("Fecha salida consignación(formato DD/MM/YYY, si no está en consignación, digte _)");
			String usuarioDuenio = pedirCadenaAlUsuario("Usuario dueño");
			String puedeSalirDeGaleria = pedirCadenaAlUsuario("Puede salir de galería (SI/NO)");
			boolean puedeSalir= true;
			if (puedeSalirDeGaleria.equals("SI")) {
				puedeSalir=true;
			}
			else {
				puedeSalir=false;
			}
			
			try {
				galeria.crearPiezaInventario(escultura, ubicacion, enConsignacion, salidaConsignacion, usuarioDuenio, puedeSalir);
				menuManejarInventario(galeria);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuAgregarPieza(galeria);
			}
		}
		else if (r1 ==3) {
			String titulo = pedirCadenaAlUsuario("Título");
			List<String> autores= new ArrayList<String>(Arrays.asList(pedirCadenaAlUsuario("Ingrese los artistas (separados por coma)").split(",")));
			String anio = pedirCadenaAlUsuario("Año de creación");
			String lugarCreacion = pedirCadenaAlUsuario("Lugar de creación");
			String observaciones = pedirCadenaAlUsuario("Observaciones");
			
			String resolucion = pedirCadenaAlUsuario("Resolución");
			
			Pieza fotografia = galeria.nuevaFotografia(titulo, anio, lugarCreacion, autores, observaciones, resolucion);
			
			int ubicacion = Integer.parseInt(pedirCadenaAlUsuario("Ubicacion (1-Exhibición/2-Bodega)"));
			if (ubicacion==1) {
				ubicacion = PiezaEnInventario.ENEXHIBICION;
			}
			if (ubicacion==2) {
				ubicacion = PiezaEnInventario.BODEGA;
			}
			String enConsignacionstr = pedirCadenaAlUsuario("En consignación (SI/NO)");
			boolean enConsignacion= true;
			if (enConsignacionstr.equals("SI")) {
				enConsignacion=true;
			}
			else {
				enConsignacion=false;
			}
			
			String salidaConsignacion = pedirCadenaAlUsuario("Fecha salida consignación(formato DD/MM/YYY, si no está en consignación, digte _)");
			String usuarioDuenio = pedirCadenaAlUsuario("Usuario dueño");
			String puedeSalirDeGaleria = pedirCadenaAlUsuario("Puede salir de galería (SI/NO)");
			boolean puedeSalir= true;
			if (puedeSalirDeGaleria.equals("SI")) {
				puedeSalir=true;
			}
			else {
				puedeSalir=false;
			}
			
			try {
				galeria.crearPiezaInventario(fotografia, ubicacion, enConsignacion, salidaConsignacion, usuarioDuenio, puedeSalir);
				menuManejarInventario(galeria);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuAgregarPieza(galeria); 
			}
		}
		else if (r1==4) {
			String titulo = pedirCadenaAlUsuario("Título");
			List<String> autores= new ArrayList<String>(Arrays.asList(pedirCadenaAlUsuario("Ingrese los artistas (separados por coma)").split(",")));
			String anio = pedirCadenaAlUsuario("Año de creación");
			String lugarCreacion = pedirCadenaAlUsuario("Lugar de creación");
			String observaciones = pedirCadenaAlUsuario("Observaciones");
			
			int duracion = Integer.parseInt(pedirCadenaAlUsuario("Resolució (en segundos)"));
			String resolucion = pedirCadenaAlUsuario("Resolución");
			
			
			Pieza video = galeria.nuevoVideo(titulo, anio, lugarCreacion, autores, observaciones, duracion, resolucion);
			
			int ubicacion = Integer.parseInt(pedirCadenaAlUsuario("Ubicacion (1-Exhibición/2-Bodega)"));
			if (ubicacion==1) {
				ubicacion = PiezaEnInventario.ENEXHIBICION;
			}
			if (ubicacion==2) {
				ubicacion = PiezaEnInventario.BODEGA;
			}
			String enConsignacionstr = pedirCadenaAlUsuario("En consignación (SI/NO)");
			boolean enConsignacion= true;
			if (enConsignacionstr.equals("SI")) {
				enConsignacion=true;
			}
			else {
				enConsignacion=false;
			}
			
			String salidaConsignacion = pedirCadenaAlUsuario("Fecha salida consignación(formato DD/MM/YYY, si no está en consignación, digte _)");
			String usuarioDuenio = pedirCadenaAlUsuario("Usuario dueño: ");
			String puedeSalirDeGaleria = pedirCadenaAlUsuario("Puede salir de galería (SI/NO): ");
			boolean puedeSalir= true;
			if (puedeSalirDeGaleria.equals("SI")) {
				puedeSalir=true;
			}
			else {
				puedeSalir=false;
			}
			
			try {
				galeria.crearPiezaInventario(video, ubicacion, enConsignacion, salidaConsignacion, usuarioDuenio, puedeSalir);
				menuManejarInventario(galeria);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuAgregarPieza(galeria);
			}
		}
		else if (r1==5) {
			menuManejarInventario(galeria);
		}
		else {
			menuAgregarPieza(galeria);
		}
		
	}


	
	private static void menuVentas(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Crear venta");
		System.out.println("2-Aprobar ventas");
		System.out.println("3-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
		if (r1== 1) {
			menuCrearVentaPieza(galeria);
		}
		else if (r1== 2) {
			menuCrearVentaSolicitudVenta(galeria);
		}
		else if (r1==3){
			menuAdministrador(galeria);
		}
		else{
			menuVentas(galeria);
		};
		
	}
	
	private static void menuCrearVentaPieza(Galeria galeria) {
		System.out.println("***********************");
		Integer idPieza = Integer.parseInt(pedirCadenaAlUsuario("Id pieza"));
		int precio = Integer.parseInt(pedirCadenaAlUsuario("Precio"));
		try {
			galeria.crearVenta(idPieza, precio);
			menuVentas(galeria);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			menuCrearVentaPieza(galeria);
		}
	}
	
	private static void menuCrearVentaSolicitudVenta(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("0-Volver");
		List<VentaValorFijo> VentasSolicitudVenta = galeria.getVentasSolicitudVenta();
		for (int i = 0; i<VentasSolicitudVenta.size();i++) {
			VentaValorFijo venta =VentasSolicitudVenta.get(i) ;
			System.out.println(Integer.toString(i+1) +"-"+ "Id pieza: "+ Integer.toString(venta.getPieza().getId())+"-Info Comprador: "+venta.getCompradorPotencial().toString1());
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opcion válida"));
		if (r1 == 0|VentasSolicitudVenta.size()==0) {
			menuVentas(galeria);
		}
		else if(1<=r1 & r1 <= VentasSolicitudVenta.size()) {
			VentaValorFijo venta = VentasSolicitudVenta.get(r1-1);
			System.out.println("Su venta es: "+"Id pieza: "+ Integer.toString(venta.getPieza().getId())+"-Info Comprador: "+venta.getCompradorPotencial().toString1());
			System.out.println("1-AprobarVenta");
			System.out.println("2-NegarVenta");
			System.out.println("3-Volver");
			int r2 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
			if (r2==1) {
				try {
					galeria.aprobarVentaValorFijo(venta);
					menuCrearVentaSolicitudVenta(galeria);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					menuCrearVentaSolicitudVenta(galeria);
				}
			}
			else if (r2==2) {
				try {
					galeria.rechazarVentaValorFijo(venta);
					menuCrearVentaSolicitudVenta(galeria);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					menuCrearVentaSolicitudVenta(galeria);
				}
			}}
		else {
			menuCrearVentaSolicitudVenta(galeria);
		}
		
	}
	
	
	private static void menuSubastas(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Crear subasta");
		System.out.println("2-Aprobar/rechazar solicitudes venta subasta");
		System.out.println("3-Agregar participante subasta");
		System.out.println("4-Terminar subasta");
		System.out.println("5-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción"));
		if (r1==1) {
			menuCrearSubastas(galeria);
		}
		else if (r1==2) {
			menuAprobarRechazarSolicitudSubasta(galeria);
		}
		else if (r1==3) {
			menuAgregarParticipanteSubasta(galeria);
		}
		else if (r1==4) {
			menuTerminarSubastas(galeria);
		}
		else if (r1==5) {
			menuAdministrador(galeria);
		}
		else {
			menuSubastas(galeria);
		}
		
	}
	
	private static void menuTerminarSubastas(Galeria galeria) {
		System.out.println("***********************");
		List<Subasta> subastasActivas = galeria.getSubastasActivas();
		System.out.println("0-Volver");
		for (int i=0;i<subastasActivas.size();i++) {
			List<String> idPiezas = new ArrayList<String>();
			Subasta subasta = subastasActivas.get(i);
			for (int j=0; j<subasta.getPiezas().size();j++) {
				idPiezas.add(Integer.toString(subasta.getPiezas().get(j).getId()));
			}
			System.out.println(Integer.toString(i+1)+"-"+idPiezas.toString());
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
		if (r1==0|subastasActivas.size()==0) {
			menuSubastas(galeria);
		}
		else if(1<=r1 & r1 <= subastasActivas.size()) {
			Subasta subasta = subastasActivas.get(r1-1);
			galeria.finalizarSubasta(subasta);
			menuTerminarSubastas(galeria);
		}
		else {
			menuTerminarSubastas(galeria);
		}
		
		
	}
	
	private static void menuCrearSubastas(Galeria galeria) {
		System.out.println("***********************");
		Subasta subasta = galeria.crearSubastaVacia();
		int numPiezas = Integer.parseInt(pedirCadenaAlUsuario("Cuántas piezas se venderán"));
		for (int i =0;i<numPiezas;i++) {
			Integer idPieza = Integer.parseInt(pedirCadenaAlUsuario("Id pieza"));
			Integer valorMinimo = Integer.parseInt(pedirCadenaAlUsuario("Valor mínimo"));
			Integer valorInicial = Integer.parseInt(pedirCadenaAlUsuario("Valor inicial"));
			try {
				galeria.agregarPiezaSubasta(subasta, idPieza, valorMinimo, valorInicial);
				menuSubastas(galeria);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuCrearSubastas(galeria);
			}
		}
		menuSubastas(galeria);
	}
	
	private static void menuAprobarRechazarSolicitudSubasta(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("0-Volver");
		List<Subasta> subastasActivas = galeria.getSubastasActivas();
		for (int i=0;i<subastasActivas.size();i++) {
			Subasta subasta = subastasActivas.get(i);
			System.out.println(Integer.toString(i+1)+"-"+subasta.imprimirPiezas());
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
		if (r1==0|subastasActivas.size()==0) {
			menuSubastas(galeria);
		}
		else if (1<=r1 & r1<= subastasActivas.size()) {
			Subasta subasta = subastasActivas.get(r1-1);
			for (int i =0;i<subasta.getPiezasVentaSolicitada().size();i++) {
				PiezaEnInventario pieza = subasta.getPiezasVentaSolicitada().get(i);
				System.out.println(Integer.toString(i)+"-"+pieza.getId());
			}
			int r2 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una pieza a la espera de aprobación"));
			subasta.getPiezasVentaSolicitada().get(r2);
			PiezaEnInventario pieza = subasta.getPiezasVentaSolicitada().get(r2);
			System.out.println("1-Aprobar");
			System.out.println("2-Rechazar");
			int r3 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
			
			if (r3 == 1) {
				try {
					galeria.aprobarVentaSubasta(subasta, pieza);
					menuAprobarRechazarSolicitudSubasta(galeria);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					menuAprobarRechazarSolicitudSubasta(galeria);
				}
			}
			else if (r3==2) {
				try {
					galeria.rechazarVentaSubasta(subasta, pieza);
					menuAprobarRechazarSolicitudSubasta(galeria);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					menuAprobarRechazarSolicitudSubasta(galeria);
				}
		}}
		else {
			menuSubastas(galeria);
		}
	}
	
	private static void menuAgregarParticipanteSubasta(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("0-Volver");
		List<Subasta> subastasActivas = galeria.getSubastasActivas();
		for (int i=0;i<subastasActivas.size();i++) {
			List<String> idPiezas = new ArrayList<String>();
			Subasta subasta = subastasActivas.get(i);
			for (int j=0; j<subasta.getPiezas().size();j++) {
				idPiezas.add(Integer.toString(subasta.getPiezas().get(j).getId()));
			}
			System.out.println(Integer.toString(i+1)+"-"+idPiezas.toString());
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción valida"));
		if (r1==0|subastasActivas.size()==0) {
			menuSubastas(galeria);
		}
		else if (1<=r1 & r1<=subastasActivas.size()) {
			Subasta subasta = subastasActivas.get(r1-1);
			String username = pedirCadenaAlUsuario("Ingrese un nombre de usuario");
			try {
				galeria.agregarParticipanteSubasta(subasta, username);
				menuAgregarParticipanteSubasta(galeria);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				menuAgregarParticipanteSubasta(galeria);
			}
		}
		else {
			menuAgregarParticipanteSubasta(galeria);
		}
		
 	}
	
	private static void menuClientes(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Buscar cliente por nombre");
		System.out.println("2-Agregar cliente");
		System.out.println("3-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Ingrese una opción válida"));
		if (r1 == 1) {
			menuBuscarClientes(galeria);
		}
		else if (r1==2) {
			menuAgregarCliente(galeria);
		}
		else if (r1==3) {
			menuAdministrador(galeria);
		}
		else {
			menuClientes(galeria);
		}
	}
	
	private static void menuBuscarClientes(Galeria galeria) {
		System.out.println("***********************");
		String nombre = pedirCadenaAlUsuario("Ingrese un nombre");
		List<Cliente> result = galeria.buscarClientePorNombre(nombre);
		for (int i=0;i<result.size();i++) {
			Cliente cliente = result.get(i);
			System.out.println(Integer.toString(i)+"-"+cliente.getUsername());
		}
		if (result.size()==0) {
			System.out.println("No hay clientes con ese nombre");
			menuClientes(galeria);
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione un cliente"));
		Cliente cliente= result.get(r1);
		System.out.println(cliente.toString2());
		System.out.println("1-Cambiar límite compras");
		System.out.println("2-Volver");
		int r2 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione un una opción válida"));
		if (r2 == 1) {
			int nuevoMonto = Integer.parseInt(pedirCadenaAlUsuario("Nuevo monto"));
			galeria.aumentarMontoCliente(cliente, nuevoMonto);
			menuClientes(galeria);
		}
		else if (r2 ==2) {
			menuClientes(galeria);
		}
		else {
			menuBuscarClientes(galeria);
		}
	}
	
	private static void menuAgregarCliente(Galeria galeria) {
		System.out.println("***********************");
		String username = pedirCadenaAlUsuario("Seleccione un nombre de usuario");
		String password = pedirCadenaAlUsuario("Asígnele una contrasña al usuario");
		String nombre = pedirCadenaAlUsuario("Nombre");
		String correo = pedirCadenaAlUsuario("Correo");
		int valorMaximoCompras = Integer.parseInt(pedirCadenaAlUsuario("Valor máximo compras"));
		
		try {
			galeria.crearNuevoCliente(username, password, nombre, correo, valorMaximoCompras);
			menuClientes(galeria);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			menuAgregarCliente(galeria);
		}
	}
	
	private static void menuArtistas(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("0-Buscar artista por nombre");
		System.out.println("1-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione un una opción válida"));
		if (r1 == 0) {
			menuBuscarArtistaPorNombre(galeria);
		}
		else if (r1 ==1) {
			menuAdministrador(galeria);
		}
		else {
			menuArtistas(galeria);
		}
	}
	
	private static void menuBuscarArtistaPorNombre(Galeria galeria) {
		String nombreArtista = pedirCadenaAlUsuario("Ingrese el nombre del artista");
		
		try {
			Artista artista = galeria.buscarArtistaPorNombre(nombreArtista);
			System.out.println(artista.toString());
			System.out.println("0-Volver");
			int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione un una opción válida"));
			if (r1==0) {
				menuArtistas(galeria);
			}
			else {
				menuBuscarArtistaPorNombre(galeria);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			menuArtistas(galeria);
		} 
	}
}