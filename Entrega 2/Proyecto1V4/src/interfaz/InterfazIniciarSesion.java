package interfaz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import logica.Cliente;
import logica.Galeria;
import logica.Usuario;
import persistencia.CentralPersistencia;

public class InterfazIniciarSesion {


	public static void menuIniciarSesion(Galeria galeriaActual) {
		System.out.println("***********************");
		System.out.println("1-Cargar datos de archivo");
		System.out.println("2-Guardar datos de archivo");
		System.out.println("3-Crear nueva galeria");
		System.out.println("4-Agregar usuario galeria");
		System.out.println("5-Iniciar sesion");
		
		
		int r1 = Integer.parseInt(pedirCadenaAlUsuario( "Ingrese una opción válida" ));
		if (r1==1) {
			String pathUsuario = pedirCadenaAlUsuario("Ingrese el nombre del archivo JSON de usuarios" );
			String pathPiezas = pedirCadenaAlUsuario("Ingrese el nombre del archivo JSON de piezas" );
			try {
				galeriaActual = CentralPersistencia.cargarDatos(pathUsuario, pathPiezas);
				menuIniciarSesion(galeriaActual);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();;
				menuIniciarSesion(galeriaActual);
			}
		}
		else if (r1==2) {
			String pathUsuario = pedirCadenaAlUsuario("Ingrese el nombre del archivo de salida JSON de usuarios" );
			String pathPiezas = pedirCadenaAlUsuario("Ingrese el nombre del archivo de salida JSON de piezas" );
			
			try {
				CentralPersistencia.salvarDatos(galeriaActual, pathPiezas, pathUsuario);
				menuIniciarSesion(galeriaActual);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (r1==3){
			galeriaActual = new Galeria();
			menuIniciarSesion(galeriaActual);
		}
		else if (r1==4) {
			menuAgregarUsuario(galeriaActual);
		}
		else if (r1 == 5) {
			menuIngresoUsuario(galeriaActual);
		}
	}

	
    private static void menuIngresoUsuario(Galeria galeria) {
		System.out.println("***********************");
		// TODO Auto-generated method stub
		String username = pedirCadenaAlUsuario("Nombre de usuario" );
		String password = pedirCadenaAlUsuario("Contraseña");
		
		try {
			Usuario usuario = galeria.verificarUsuario(username, password);
			int rol = usuario.getRol();
			if (rol==Usuario.ADMINISTRADOR) {
				InterfazAdministrador.menuAdministrador(galeria);
			}
			else if (rol== Usuario.CAJERO) {
				InterfazCajero.menuCajero(galeria);
			}
			else if (rol==Usuario.CLIENTE) {
				Cliente cliente = galeria.getClientes().get(username);
				InterfazCliente.menuCliente(galeria, cliente);
			}
			else if (rol==Usuario.OPERADOR) {
				InterfazOperador.menuOperador(galeria);
			}
			else if (rol==Usuario.EMPLEADO) {
				InterfazEmpleado.menuEmpleado(galeria);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			menuIngresoUsuario(galeria);
		}

	}

	private static String pedirCadenaAlUsuario( String mensaje ){
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
    
    private static void menuAgregarUsuario(Galeria galeria) {
		System.out.println("***********************");
    	System.out.println("1-Crear nuevo usuario administrador");
    	System.out.println("2-Crear nuevo usuario cajero");
    	System.out.println("3-Crear nuevo usuario operador");
    	System.out.println("4-Crear nuevo usuario empleado");
    	System.out.println("5-Volver");
    	int r1 =  Integer.parseInt(pedirCadenaAlUsuario( "Ingrese una opción válida" ));
    	if (r1 == 1) {
    		String username = pedirCadenaAlUsuario("Ingrese un nombre de usuario");
    		String password = pedirCadenaAlUsuario("Ingrese una contraseña");
    		try {
				galeria.crearNuevoUsuario(username, password, Usuario.ADMINISTRADOR);
				menuAgregarUsuario(galeria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				menuAgregarUsuario(galeria);

			}
    	}
    	else if (r1==2) {
    		String username = pedirCadenaAlUsuario("Ingrese un nombre de usuario");
    		String password = pedirCadenaAlUsuario("Ingrese una contraseña");
    		try {
				galeria.crearNuevoUsuario(username, password, Usuario.CAJERO);
				menuAgregarUsuario(galeria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				menuAgregarUsuario(galeria);
				System.out.println(e.getMessage());
			}
    	}
    	else if (r1==3) {
    		String username = pedirCadenaAlUsuario("Ingrese un nombre de usuario");
    		String password = pedirCadenaAlUsuario("Ingrese una contraseña");
    		try {
				galeria.crearNuevoUsuario(username, password, Usuario.OPERADOR);
				menuAgregarUsuario(galeria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				menuAgregarUsuario(galeria);
				System.out.println(e.getMessage());
			}
    	}
    	else if (r1==4) {
    		String username = pedirCadenaAlUsuario("Ingrese un nombre de usuario");
    		String password = pedirCadenaAlUsuario("Ingrese una contraseña");
    		try {
				galeria.crearNuevoUsuario(username, password, Usuario.EMPLEADO);
				menuAgregarUsuario(galeria);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				menuAgregarUsuario(galeria);
				System.out.println(e.getMessage());
			}
    	}
    	else if (r1==5) {
    		menuIniciarSesion(galeria);
    	}
    	else {
    		menuAgregarUsuario(galeria);
    	}
    	
    }
    
}
