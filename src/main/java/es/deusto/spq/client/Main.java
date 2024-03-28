
package es.deusto.spq.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.awt.EventQueue;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Persistent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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
		WebTarget registerUserWebTarget = webTarget.path("login");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

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
	@Persistent private String nombre;
    @Persistent private String lugar;
    @Persistent private Date fecha;
    @Persistent private String descripcion;
    @Persistent private int aforo;
    @Persistent private String organizador;
    @Persistent private ArrayList<SectoresEvento> sectores;
    @Persistent private Map<SectoresEvento, Integer> precioSector;

	public void newEvento(String nombre, String lugar, Date fecha, String descripcion, int aforo, Map<SectoresEvento, Integer> precio, String organizador, ArrayList<SectoresEvento> sector) {
		WebTarget registerUserWebTarget = webTarget.path("crearEvento");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		Evento eventoData = new Evento(nombre, lugar, fecha, descripcion, aforo, precio, organizador, sector);
		
		Response response = invocationBuilder.post(Entity.entity(eventoData, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Event correctly registered");
		}
	}

	public List<Evento> getEventos() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Query<Evento> query = pm.newQuery(Evento.class);
			@SuppressWarnings("unchecked")
			List<Evento> eventos = (List<Evento>) query.execute();
			tx.commit();
			return eventos;
		} catch (Exception e) {
			logger.error("Error getting events: {}", e.getMessage());
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
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

	public void eliminarEvento(Evento evento) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try  {
			tx.begin();
			pm.deletePersistent(evento);
			tx.commit();
		} catch (Exception e) {
			if(tx.isActive()) {
				tx.rollback();
			}	
			e.printStackTrace();
		}finally{
			pm.close();
		}
	}
}