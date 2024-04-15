package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logica.Cliente;
import logica.Galeria;
import logica.VentaValorFijo;

public class InterfazCliente {
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
	 
	public static void menuCliente(Galeria galeria, Cliente cliente) {
		System.out.println("***********************");
		System.out.println("1-Revisar perfil");
		System.out.println("2-Solicitar compra pieza");
		System.out.println("3-Cerrar sesión");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
		if (r1==1) {
			menuPerfil(galeria, cliente);
		}
		else if (r1==2) {
			menuCompras(galeria, cliente);
		}
		else if (r1==3) {
			InterfazIniciarSesion.menuIniciarSesion(galeria);
		}
		else {
			menuCliente(galeria,cliente);
		}
	}
	
	public static void menuPerfil(Galeria galeria, Cliente cliente) {
		System.out.println("***********************");
		System.out.println(cliente.toString2());
		System.out.println("1-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
		if (r1==1) {
			menuCliente(galeria,cliente);
		}
		else {
			menuPerfil(galeria,cliente);
		}
	}
	public static void menuCompras(Galeria galeria,Cliente cliente) {
		System.out.println("***********************");
		System.out.println("0-Volver");
		List<VentaValorFijo> piezasEnVenta = galeria.getVentasActivas();
		for (int i = 0; i<piezasEnVenta.size();i++) {
			VentaValorFijo venta = piezasEnVenta.get(i);
			System.out.println(Integer.toString(i+1)+"-Titulo: "+venta.getPieza().getPieza().getTitulo()+"| Precio: "+Integer.toString(venta.getPrecio()));
		}
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Ingrese una opción válida: "));
		if (r1==0 | piezasEnVenta.size()==0) {
			menuCliente(galeria,cliente);
		}
		else if (1<=r1 & r1<=piezasEnVenta.size()) {
			VentaValorFijo venta = piezasEnVenta.get(r1-1);
			try {
				galeria.solicitarVentaValorFijo(venta, cliente);
				menuCompras(galeria, cliente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				menuCompras(galeria, cliente);
			}
		}
		else {
			menuCompras(galeria,cliente);
		}

	}
}
