
package es.deusto.spq.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import es.deusto.spq.client.gui.EditUserWindow;
import es.deusto.spq.client.gui.EventoWindow;
import es.deusto.spq.client.gui.LoginWindow;
import es.deusto.spq.client.gui.MainWindowClient;
import es.deusto.spq.client.gui.MainWindowWorker;
import es.deusto.spq.server.jdo.Entrada;
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
	public static EditUserWindow editUserWindow;

	public static Usuario user;

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
			Usuario user = response.readEntity(Usuario.class);
			Main.user = user;
			return user.getRol().toString();
		}
	}

	public void logout(){
		
	}

	public void eliminarCuenta(){
		WebTarget deleteUserWebTarget = webTarget.path("eliminarCuenta/" + user.getDni());
		Invocation.Builder invocationBuilder = deleteUserWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.delete();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly deleted");
		}
	}

	public void editarUsuario(Usuario usuario) {
		List<Entrada> entradas = getEntradas();
		List<Entrada> entradasUsuario = new ArrayList<Entrada>();
		for (Entrada entrada : entradas) {
			if (entrada.getUsuario().getDni().equals(user.getDni())) {
				entradasUsuario.add(entrada);
				eliminarEntrada(entrada);
			}
		}
		
		eliminarCuenta();
		registroUsuario(usuario.getNombre(), usuario.getApellidos(), usuario.getNombreUsuario(), usuario.getContrasenya(), usuario.getEmail(), usuario.getDireccion(), usuario.getTelefono(), usuario.getFechaNacimiento(), user.getDni());
		
		for (Entrada entrada : entradasUsuario) {
			comprarEntrada(entrada.getEvento(), usuario, entrada.getSector(), entrada.getPrecio());
		}
	}
	

	public void newEvento(String nombre, String lugar, Date fecha, String descripcion, int aforo, int aforoTotal, String organizador, ArrayList<SectoresEvento> sector, Map<SectoresEvento, Integer> precioSector, Map<SectoresEvento, Integer> entradasSector) {
		WebTarget newEventWebTarget = webTarget.path("crearEvento");
		Invocation.Builder invocationBuilder = newEventWebTarget.request(MediaType.APPLICATION_JSON);

		Evento eventoData = new Evento(nombre, lugar, fecha, descripcion, aforo, aforoTotal, organizador, sector, precioSector, entradasSector);
		
		Response response = invocationBuilder.post(Entity.entity(eventoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Event correctly registered");
		}
	}


	
	
	public List<Evento> getEventos() {
		WebTarget getEventsWebTarget = webTarget.path("getEventos");
		Invocation.Builder invocationBuilder = getEventsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.get();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			List<Evento> eventos = response.readEntity(new GenericType<List<Evento>>() {});
			logger.info("{} events downloaded", eventos.size());
			
			ArrayList<SectoresEvento> sectores = new ArrayList<SectoresEvento>();
			HashMap<SectoresEvento, Integer> precioSector = new HashMap<SectoresEvento, Integer>();
			HashMap<SectoresEvento, Integer> entradasSector = new HashMap<SectoresEvento, Integer>();

			sectores.add(SectoresEvento.PISTA);
			sectores.add(SectoresEvento.FRONT_STAGE);
			sectores.add(SectoresEvento.VIP);
			sectores.add(SectoresEvento.GRADA_ALTA);
			sectores.add(SectoresEvento.GRADA_BAJA);
			sectores.add(SectoresEvento.GRADA_MEDIA);

			precioSector.put(SectoresEvento.PISTA, 60);
			precioSector.put(SectoresEvento.FRONT_STAGE, 80);
			precioSector.put(SectoresEvento.VIP, 100);
			precioSector.put(SectoresEvento.GRADA_ALTA, 20);
			precioSector.put(SectoresEvento.GRADA_BAJA, 40);
			precioSector.put(SectoresEvento.GRADA_MEDIA, 30);

			for (Evento evento : eventos) {
				entradasSector.put(SectoresEvento.PISTA, (int) (evento.getAforo() * 0.3));
				entradasSector.put(SectoresEvento.FRONT_STAGE, (int) (evento.getAforo() * 0.04));
				entradasSector.put(SectoresEvento.VIP, (int) (evento.getAforo() * 0.01));
				entradasSector.put(SectoresEvento.GRADA_ALTA, (int) (evento.getAforo() * 0.2));
				entradasSector.put(SectoresEvento.GRADA_BAJA, (int) (evento.getAforo() * 0.2));
				entradasSector.put(SectoresEvento.GRADA_MEDIA, (int) (evento.getAforo() * 0.25));

				evento.setSector(sectores);
				evento.setPrecioSector(precioSector);
				evento.setEntradasSector(entradasSector);
			}

			return eventos;
		}
	}

	public Evento getEvento(String id) {
		WebTarget deleteEventWebTarget = webTarget.path("getEventoId/" + id);
		Invocation.Builder invocationBuilder = deleteEventWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.get();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			logger.info("Event with id: {} downloaded", id);
			return response.readEntity(Evento.class);
		}
	}

	public void deleteEvento(Evento evento) {
		WebTarget deleteEventWebTarget = webTarget.path("eliminarEvento/" + evento.getId());
		Invocation.Builder invocationBuilder = deleteEventWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.delete();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Event correctly deleted");
		}
	}

	public void editarEvento(Evento evento) {
    	deleteEvento(evento);
    	newEvento(evento.getNombre(), evento.getLugar(), evento.getFecha(), evento.getDescripcion(), evento.getAforo(), evento.getAforoTotal(), evento.getOrganizador(), evento.getSector(), evento.getPrecioSector(), evento.getEntradasSector());
	}
	

	public void comprarEntrada(Evento evento, Usuario usuario, SectoresEvento sector, int precio) {
		WebTarget comprarEntradaWebTarget = webTarget.path("comprarEntrada/" + evento.getId() + "/" + usuario.getDni() + "/" + sector.toString() + "/" + precio);
		Invocation.Builder invocationBuilder = comprarEntradaWebTarget.request(MediaType.APPLICATION_JSON);
	
		Response response = invocationBuilder.get();

		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Ticket correctly registered");
		}
	}

	public List<Entrada> getEntradas() {
		WebTarget getEventsWebTarget = webTarget.path("getEntradas");
		Invocation.Builder invocationBuilder = getEventsWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.get();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
			return null;
		} else {
			List<Entrada> entradas = response.readEntity(new GenericType<List<Entrada>>() {});
			logger.info("{} tickets downloaded", entradas.size());
			return entradas;
		}
	}

	public void eliminarEntrada(Entrada entrada) {
		WebTarget deleteEventWebTarget = webTarget.path("eliminarEntrada/" + entrada.getId());
		Invocation.Builder invocationBuilder = deleteEventWebTarget.request(MediaType.APPLICATION_JSON);

		Response response = invocationBuilder.delete();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Ticket correctly deleted");
		}
	}

	public void editarCuenta(Usuario usuario){

	}

	

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		user = new Usuario();

		Main exampleClient = new Main(hostname, port);
		loginWindow = new LoginWindow(exampleClient);
		mainWindowClient = new MainWindowClient(exampleClient);
		mainWindowWorker = new MainWindowWorker(exampleClient);
		eventoWindow = new EventoWindow(exampleClient);
		editUserWindow = new EditUserWindow(exampleClient);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginWindow.setVisible(true);
			}
		});
	}
}