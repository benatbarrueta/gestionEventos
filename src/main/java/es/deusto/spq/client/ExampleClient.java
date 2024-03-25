package es.deusto.spq.client;

import java.util.Date;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.server.jdo.Usuario;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.Entrada;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleClient {

	protected static final Logger logger = LogManager.getLogger();

	private static final String USER = "dipina";
	private static final String PASSWORD = "dipina";


	private Client client;
	private WebTarget webTarget;

	public ExampleClient(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
	}

	public void registerUser(String nombre, String apellidos, String nombreUsuario, String contrasenya, String email, String direccion, String telefono, String rol, Date fechaNacimiento, String dni) {
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Usuario userData = new Usuario(nombre, apellidos, nombreUsuario, contrasenya, email, direccion, telefono, rol, fechaNacimiento, dni);
		
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered");
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		ExampleClient exampleClient = new ExampleClient(hostname, port);
		exampleClient.registerUser("David", "Pina", "dipina", "dipina", "a", "a", "a", "a", new Date(System.currentTimeMillis()), "12345678A");
	}
}