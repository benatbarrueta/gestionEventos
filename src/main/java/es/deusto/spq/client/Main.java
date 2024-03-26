
package es.deusto.spq.client;

import java.util.Date;

import java.awt.EventQueue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.server.jdo.Usuario;
import es.deusto.spq.client.gui.LoginWindow;
import es.deusto.spq.client.gui.MainWindow;
import es.deusto.spq.server.jdo.TipoUsuario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	protected static final Logger logger = LogManager.getLogger();

	public static LoginWindow loginWindow;
	public static MainWindow mainWindow;

	private Client client;
	private WebTarget webTarget;

	public Main(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
	}

	public void registroUsuario(String nombre, String apellidos, String nombreUsuario, String contrasenya, String email, String direccion, String telefono, Date fechaNacimiento, String dni) {
		WebTarget registerUserWebTarget = webTarget.path("register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Usuario userData = new Usuario(nombre, apellidos, nombreUsuario, contrasenya, email, direccion, telefono, TipoUsuario.CLIENTE, fechaNacimiento, dni);
		
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered");
		}
	}

	public Boolean loginUsuario(String nombreUsuario, String contrasenya) {
		WebTarget registerUserWebTarget = webTarget.path("login");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Usuario userData = new Usuario("", "", nombreUsuario, contrasenya, "", "", "", TipoUsuario.CLIENTE, null, "");
		
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return false;
		} else {
			logger.info("User correctly registered");
			return true;
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		Main exampleClient = new Main(hostname, port);
		loginWindow = new LoginWindow(exampleClient);
		mainWindow = new MainWindow(exampleClient);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginWindow.setVisible(true);
			}
		});
	}
}