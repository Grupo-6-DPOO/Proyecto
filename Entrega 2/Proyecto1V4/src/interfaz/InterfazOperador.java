package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logica.Galeria;
import logica.Subasta;

public class InterfazOperador {
	
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
	
	public static void menuOperador(Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Buscar subasta");
		System.out.println("2-Cerrar sesión");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Seleccione una opción válida"));
		if (r1==1) {
			menuBuscarSubasta(galeria);
		}
		else if (r1 ==2) {
			InterfazIniciarSesion.menuIniciarSesion(galeria);
		}
		else {
			menuOperador(galeria);
		}
	}	
	private static void menuBuscarSubasta(Galeria galeria) {
		List<Subasta> subastasActivas = galeria.getSubastasActivas();
		System.out.println("0-Volver");
		for (int i =0; i<subastasActivas.size();i++) {
			Subasta subasta = subastasActivas.get(i);
			System.out.println(Integer.toString(i+1)+"-"+subasta.imprimirPiezas());
		}
		
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Ingrese una opción válida"));
		if (r1 ==0 | subastasActivas.size()==0) {
			menuOperador(galeria);
		}
		else if (1<=r1 & r1<=subastasActivas.size()) {
			Subasta subasta = subastasActivas.get(r1-1);
			menuDirigirSubasta(subasta, galeria);
		}
		else {
			menuBuscarSubasta(galeria);
		}
		
	}
	
	private static void menuDirigirSubasta(Subasta subasta,Galeria galeria) {
		System.out.println("***********************");
		System.out.println("1-Registrar oferta venta");
		System.out.println("2-Solicitar venta pieza");
		System.out.println("3-Volver");
		int r1 = Integer.parseInt(pedirCadenaAlUsuario("Ingrese una opción válida"));
		if (r1==1) {
			String username = pedirCadenaAlUsuario("Ingrese el nombre de usuario del comprador participante");
			Integer idPieza = Integer.parseInt(pedirCadenaAlUsuario("Ingrese el identificador de la pieza"));
			int monto = Integer.parseInt(pedirCadenaAlUsuario("Ingrese el valor de la oferta"));
			try {
				galeria.crearOfertaSubasta(subasta, username, monto, idPieza);
				menuDirigirSubasta(subasta, galeria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				menuDirigirSubasta(subasta, galeria);
			}
			
		}
		else if (r1==2) {
			Integer idPieza = Integer.parseInt(pedirCadenaAlUsuario("Ingrese el identificador de la pieza a vender"));
			try {
				galeria.solicitarVentaSubasta(subasta, idPieza);
				menuDirigirSubasta(subasta, galeria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				menuDirigirSubasta(subasta, galeria);
			}
		}
		else if (r1==3) {
			menuBuscarSubasta(galeria);
		}
		else {
			menuDirigirSubasta(subasta,galeria);
		}
	}
}
