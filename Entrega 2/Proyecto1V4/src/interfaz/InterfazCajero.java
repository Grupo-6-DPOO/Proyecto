package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logica.Galeria;
import logica.Pago;

public class InterfazCajero {
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
	
	public static void menuCajero(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Aprobar pagos pendientes");
		System.out.println("2-Cerrar sesión");
		int r1 =Integer.parseInt(pedirCadenaAlUsuario("Ingrese una opción válida"));
		if (r1==1) {
			menuRevisarPagos(galeria);
			
		}
		else if (r1==2) {
			InterfazIniciarSesion.menuIniciarSesion(galeria);
		}
		else {
			menuCajero(galeria);
		}
	}
	
	public static void menuRevisarPagos(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("0-Volver");
		List<Pago> pagosPendientes = galeria.getPagosPendientes();
		for (int i =0 ;i<pagosPendientes.size();i++) {
			Pago pago = pagosPendientes.get(i);
			System.out.println(Integer.toString(i+1)+"-"+pago.toString());
		}
		int r1 =Integer.parseInt(pedirCadenaAlUsuario("Ingrese una opción valida"));
		if (r1==0 | pagosPendientes.size()==0) {
			menuCajero(galeria);
		}
		else if (1<=r1 & r1<=pagosPendientes.size()) {
			Pago pagoSeleccionado = pagosPendientes.get(r1-1);
			System.out.println("1-Tarjeta");
			System.out.println("2-Transferencia"); 
			System.out.println("3-Efectivo");
			int r2 = Integer.parseInt(pedirCadenaAlUsuario("Ingrese una medio de pago"));
			if (r2==1) {
				try {
					galeria.aprobarPago(pagoSeleccionado, Pago.TARJETA);
					menuRevisarPagos(galeria);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					menuRevisarPagos(galeria);
				}
			}
			else if (r2==2) {
				try {
					galeria.aprobarPago(pagoSeleccionado, Pago.TRANSFERENCIA);
					menuRevisarPagos(galeria);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					menuRevisarPagos(galeria);
				}
			}
			else if (r2==3) {
				try {
					galeria.aprobarPago(pagoSeleccionado, Pago.EFECTIVO);
					menuRevisarPagos(galeria);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					menuRevisarPagos(galeria);
				}
			}
			else {
				menuRevisarPagos(galeria);
			}
		}
		else {
			menuRevisarPagos(galeria);
		}
	}
}
