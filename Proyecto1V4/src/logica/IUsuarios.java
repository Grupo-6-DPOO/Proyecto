package logica;

import java.util.List;
import java.util.Map;

public interface IUsuarios {

	Map<String, Cliente> getClientes();

	Map<String, Usuario> getUsuarios();

	List<Cliente> buscarClientePorNombre(String nombre);

	void crearNuevoCliente(String username, String password, String nombre, String correo, int valorMaximoCompras)
			throws Exception;

	void crearNuevoUsuario(String username, String password, int rol) throws Exception;

	Usuario verificarUsuario(String username, String password) throws Exception;

	void aumentarMontoCliente(Cliente cliente, int nuevoMonto);

}