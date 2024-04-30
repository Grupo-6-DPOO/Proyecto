package logica;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	public static final int ADMINISTRADOR=0;
	public static final int CLIENTE=1;
	public static final int EMPLEADO=2;
	public static final int OPERADOR=3;
	public static final int CAJERO=4;
	
	protected String username;
	protected String password;
	protected int rol;
	private static List<String> usernamesUsados = new ArrayList<String>();


	public Usuario(String username, String password, int rol)throws Exception {
		super();
		if (usernamesUsados.contains(username)) {
			throw new Exception("Username repetido");
		}
		this.rol =rol;
		this.username = username;
		this.password = password;
		usernamesUsados.add(username);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRol() {
		return rol;
	}
	
	
	
	
}
