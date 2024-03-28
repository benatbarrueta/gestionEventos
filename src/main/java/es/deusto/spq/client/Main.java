
package es.deusto.spq.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.awt.EventQueue;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.server.jdo.Usuario;
import es.deusto.spq.client.gui.EventoWindow;
import es.deusto.spq.client.gui.LoginWindow;
import es.deusto.spq.client.gui.MainWindowClient;
import es.deusto.spq.client.gui.MainWindowWorker;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.SectoresEvento;
import es.deusto.spq.server.jdo.TipoUsuario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

	protected static final Logger logger = LogManager.getLogger();

	public static LoginWindow loginWindow;
	public static MainWindowClient mainWindowClient;
	public static MainWindowWorker mainWindowWorker;
	public static EventoWindow eventoWindow;

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

	public String loginUsuario(String nombreUsuario, String contrasenya) {
		WebTarget loginUserWebTarget = webTarget.path("login");
		Invocation.Builder invocationBuilder = loginUserWebTarget.request(MediaType.APPLICATION_JSON);

		Usuario userData = new Usuario(nombreUsuario, contrasenya);
		
		Response response = invocationBuilder.post(Entity.entity(userData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return "";
		} else {
			logger.info("User correctly logged");
			return response.readEntity(String.class);
		}
	}

	public void logout(){
		
	}

	public void newEvento(String nombre, String lugar, Date fecha, String descripcion, int aforo, Map<SectoresEvento, Integer> precio, String organizador, ArrayList<SectoresEvento> sector, Map<SectoresEvento, Integer> precioSector) {
		WebTarget newEventWebTarget = webTarget.path("crearEvento");
		Invocation.Builder invocationBuilder = newEventWebTarget.request(MediaType.APPLICATION_JSON);

		Evento eventoData = new Evento(nombre, lugar, fecha, descripcion, aforo, precio, organizador, sector, precioSector);
		
		Response response = invocationBuilder.post(Entity.entity(eventoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Event correctly registered");
		}
	}
	
	public List<Evento> getEventos() {
		WebTarget newEventWebTarget = webTarget.path("getEventos");
		Invocation.Builder invocationBuilder = newEventWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.get();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			logger.info("Event correctly registered");
			List<Evento> eventos = response.readEntity(new GenericType<List<Evento>>() {});
			return eventos;
		}
	}

	public void deleteEvento(Evento evento) {
		WebTarget newEventWebTarget = webTarget.path("eliminarEvento/" + evento.getId());
		Invocation.Builder invocationBuilder = newEventWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.delete();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Event correctly deleted");
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
		mainWindowClient = new MainWindowClient(exampleClient);
		mainWindowWorker = new MainWindowWorker(exampleClient);
		eventoWindow = new EventoWindow(exampleClient);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginWindow.setVisible(true);
			}
		});
	}
}