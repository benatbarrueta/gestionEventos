package es.deusto.spq.server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.Entrada;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.Resenya;
import es.deusto.spq.server.jdo.SectoresEvento;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

import org.apache.logging.log4j.LogManager;

/**
 * This class represents a resource in the server.
 * It handles login, registration, user management, and event management operations.
 */
@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

	protected static final Logger logger = LogManager.getLogger();

	private PersistenceManager pm=null;
	private Transaction tx=null;

	public static Map<Usuario, Long> tokens = new HashMap<Usuario, Long>();
	public static long token;
	public static Usuario usuario;

	//Credenciales de Twilio
    public static final String ACCOUNT_SID = "ACb22cb289b26ad38d2c5ed1d0ba302f06";
    public static final String AUTH_TOKEN = "18a51da79088cd192c44851e08653738";


	public Resource() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	
	/**
		* Logs in a user and returns a response.
		*
		* @param usuario The user to be logged in.
		* @return A response indicating the login status.
		*/
	@POST
	@Path("/login")
	public Response loginUser(Usuario usuario) {
		try {
			tx.begin();

			Usuario user = null;
			try {
				@SuppressWarnings("rawtypes")
				Query query = pm.newQuery(Usuario.class);
				query.setFilter("nombreUsuario == nombreUsuarioParam");
				query.declareParameters("String nombreUsuarioParam");
				@SuppressWarnings("unchecked")
				List<Usuario> results = (List<Usuario>) query.execute(usuario.getNombreUsuario());
				if (!results.isEmpty()) {
					user = results.get(0);
				}
			} catch (Exception e) {
				logger.error("Exception: {}", e.getMessage());
			}

			if (user != null && user.getNombreUsuario().equals(usuario.getNombreUsuario()) && user.getContrasenya().equals(usuario.getContrasenya())) {
				logger.info("User {} logged in successfully!", user.getNombreUsuario());
				long token = System.currentTimeMillis();
				Resource.token = token;
				Resource.tokens.put(user, token);
				usuario = user;
				tx.commit();

				return Response.ok(user).build();
			
			} else {
				logger.info("Invalid email or password");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity(false).build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
	
	
	
	/**
	 * Represents the HTTP response returned by the server.
	 */
	@POST
	@Path("/register")
	public Response registerUser(Usuario usuario) {
		try
        {	
            tx.begin();

			Usuario user = null;
			try {
				user = pm.getObjectById(Usuario.class, usuario.getDni());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if (user != null) {
				logger.info("User already exists!");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("User already exists").build();
			} else {
				logger.info("Creating user: {}", user);
				user = new Usuario(usuario.getNombre(), usuario.getApellidos(), usuario.getNombreUsuario(), usuario.getContrasenya(), usuario.getEmail(), usuario.getDireccion(), usuario.getTelefono(), TipoUsuario.CLIENTE, usuario.getFechaNacimiento(), usuario.getDni());
				pm.makePersistent(user);					 
				logger.info("User created: {}", user);
				tx.commit();
				return Response.ok().build();
			}
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
      
		}
	}

	/**
	 * Updates the role of a user based on their DNI (identification number).
	 * 
	 * @param dni The DNI of the user.
	 * @param rol The new role of the user.
	 * @return A Response object indicating the success or failure of the operation.
	 *         If the user is found and the role is updated successfully, the response
	 *         will have a status of 200 OK. If the user is not found, the response
	 *         will have a status of 404 Not Found.
	 */
	@GET
	@Path("/actualizarRolUsuario/{dni}/{rol}")
	public Response actualizarRolUsuario(@PathParam("dni") String dni, @PathParam("rol") TipoUsuario rol) {
		try {
			tx.begin();

			Usuario user = null;

			try {
				user = pm.getObjectById(Usuario.class, dni);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if (user != null) {
				user.setRol(rol);
				logger.info("User updated: {}", user);
				tx.commit();
				return Response.ok().build();
			} else {
				logger.info("User not found");
				tx.rollback();
				return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Deletes a user account based on the provided DNI (Documento Nacional de Identidad).
	 * 
	 * @param dni The DNI of the user to be deleted.
	 * @return A Response object indicating the status of the operation.
	 *         - If the user is found and successfully deleted, returns a Response with status 200 (OK).
	 *         - If the user is not found, returns a Response with status 404 (Not Found) and an error message.
	 */
	@DELETE
	@Path("/eliminarCuenta/{dni}")
	public Response eliminarCuenta(@PathParam("dni") String dni) {
		try {
			tx.begin();

			Usuario usuario = null;

			try {
				usuario = pm.getObjectById(Usuario.class, dni);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if (usuario != null) {
				logger.info("Deleting user: {}", usuario);
				pm.deletePersistent(usuario);
				tx.commit();
				return Response.ok().build();
			} else {
				logger.info("User not found");
				tx.rollback();
				return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	

	/**
	 * Retrieves the list of users from the database.
	 * 
	 * @return a Response object containing the list of users if found, or an error message if no users are found.
	 */
	@GET 
	@Path("/getUsuarios")
	public Response getUsuarios(){
		try {
			tx.begin();
			Query<Usuario> query = pm.newQuery(Usuario.class);
			@SuppressWarnings("unchecked")
			List<Usuario> usuarios = (List<Usuario>) query.execute();
			if (usuarios != null) {
				logger.info("{} users found", usuarios.size());
				tx.commit();
				return Response.ok(usuarios).build();
			} else {
				logger.info("No users found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("No users found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	/**
	 * Retrieves the user with the specified ID.
	 * 
	 * @param id the ID of the user
	 * @return a Response object containing the user if found, or an error message if not found
	 */
	@GET
	@Path("/getUsuarioId/{id}")
	public Response getUsuarioId(@PathParam("id") String id) {
		try {
			tx.begin();
			Usuario user = null;

			try {
				user = pm.getObjectById(Usuario.class, id);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			

			if (user != null) {
				logger.info("User found: {}", user.getNombre());
				tx.commit();
				System.out.println(user);
				return Response.ok(user).build();
			} else {
				logger.info("No user found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("User already exists").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	
	}

	/**
	 * Creates a new event in the system.
	 * 
	 * @param evento The event object containing the details of the event to be created.
	 * @return A Response object indicating the status of the operation.
	 *         If the event already exists, returns an UNAUTHORIZED response with an error message.
	 *         If the event is successfully created, returns an OK response.
	 */
	@POST
	@Path("/crearEvento")
	public Response crearEvento(Evento evento) {
		System.out.println(evento);
		try {
			tx.begin();
			Evento event = null;

			try {
				event = pm.getObjectById(Evento.class, evento.getId());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			} 

			if(event != null){
				logger.info("Event already exists!");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("Event already exists").build();
			}else{
				logger.info("Creating event: {}", event);
				event = new Evento(evento.getNombre(), evento.getLugar(), evento.getFecha(), evento.getDescripcion(), evento.getAforo(), evento.getAforoTotal(), evento.getOrganizador(), evento.getSector(), evento.getPrecioSector(), evento.getEntradasSector());
				pm.makePersistent(event);
				logger.info("Event created: {}", event);
				tx.commit();
				return Response.ok().build();
			}
		} 
		finally 
		{
			if (tx.isActive()) 
			{
				tx.rollback();
			}
		}
	}

	/**
	 * Retrieves a list of events.
	 * 
	 * @return a Response object containing the list of events if found, or an unauthorized status with an error message if no events are found.
	 */
	@GET
	@Path("/getEventos")
	public Response getEventos() {
		try {
			tx.begin();
			Query<Evento> query = pm.newQuery(Evento.class);
			
			@SuppressWarnings("unchecked")
			List<Evento> eventos = (List<Evento>) query.execute();
			

			if (eventos != null) {
				logger.info("{} events found", eventos.size());
				tx.commit();
				return Response.ok(eventos).build();
			} else {
				logger.info("No events found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("No events found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	
	}

	/**
	 * Retrieves the event with the specified ID.
	 * 
	 * @param id the ID of the event to retrieve
	 * @return a Response object containing the event if found, or an error message if not found
	 */
	@GET
	@Path("/getEventoId/{id}")
	public Response getEventos(@PathParam("id") String id) {
		try {
			tx.begin();
			Evento event = null;

			try {
				event = pm.getObjectById(Evento.class, id);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			

			if (event != null) {
				logger.info("Event found: {}", event.getNombre());
				tx.commit();
				System.out.println(event);
				return Response.ok(event).build();
			} else {
				logger.info("No events found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("Event already exists").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	
	}

	/**
	 * Deletes an event from the server.
	 * 
	 * @param id the ID of the event to be deleted
	 * @return a Response object indicating the status of the operation
	 */
	@DELETE
	@Path("/eliminarEvento/{id}")
	public Response eliminarEvento(@PathParam("id") String id) {
		try {
			tx.begin();

			Evento event = null;

			try {
				event = pm.getObjectById(Evento.class, id);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if (event != null) {
				logger.info("Deleting event: {}", event);
				pm.deletePersistent(event);
				tx.commit();
				return Response.ok().build();
			} else {
				logger.info("Event not found");
				tx.rollback();
				return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * This method is used to update an Evento object and return a Response.
	 * 
	 * @param evento The Evento object containing the updated information.
	 * @return A Response indicating the success or failure of the update operation.
	 */
	@POST
	@Path("/actualizarEvento")
	public Response actualizarEvento(Evento evento){
		try {
			tx.begin();

			Evento event = null;

			try {
				event = pm.getObjectById(Evento.class, evento.getId());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if (event != null) {
				Response response = eliminarEvento(String.valueOf(event.getId()));
				if(response.getStatus() == 200) {
					event.setNombre(evento.getNombre());
					event.setLugar(evento.getLugar());
					event.setFecha(evento.getFecha());
					event.setDescripcion(evento.getDescripcion());
					event.setAforo(evento.getAforo());
					event.setOrganizador(evento.getOrganizador());
					event.setSector(evento.getSector());
					event.setPrecioSector(evento.getPrecioSector());
					event.setEntradasSector(evento.getEntradasSector());

					logger.info("Event updated: {}", event);
					tx.commit();
					return Response.ok().build();
				} else {
					logger.info("Event not found");
					tx.rollback();
					return Response.status(Response.Status.NOT_FOUND).entity("Event not deleted").build();}
			} else {
				logger.info("Event not found");
				tx.rollback();
				return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * This method is used to handle the request for buying a ticket.
	 * It creates a new ticket based on the provided event ID, sector, and quantity.
	 * If the ticket already exists, it returns an unauthorized response.
	 * Otherwise, it creates the ticket, persists it, and returns a success response.
	 *
	 * @param eventId   The ID of the event for which the ticket is being purchased.
	 * @param sector    The sector of the event where the ticket will be located.
	 * @param cantidad  The quantity of tickets being purchased.
	 * @return          A Response object indicating the success or failure of the ticket purchase.
	 */
	@SuppressWarnings("null")
	@GET
	@Path("/comprarEntrada/{idEvento}/{sector}/{cantidad}")
	public Response comprarEntrada(@PathParam("idEvento") String eventId, @PathParam("sector") String sector, @PathParam("cantidad") String cantidad) {
		try{
			tx.begin();

			Entrada ticket = null;
			Evento event = null;

			try {
				event = pm.getObjectById(Evento.class, eventId);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			Entrada entrada = null;

			System.out.println(sector);

			if (SectoresEvento.VIP.toString().equals(sector.toUpperCase())){
				entrada = new Entrada(usuario, event, 100, SectoresEvento.VIP);
			} else if (SectoresEvento.GRADA_ALTA.toString().equals(sector.toUpperCase())){
				entrada = new Entrada(usuario, event, 20, SectoresEvento.GRADA_ALTA);
			} else if (SectoresEvento.GRADA_MEDIA.toString().equals(sector.toUpperCase())){
				entrada = new Entrada(usuario, event, 30, SectoresEvento.GRADA_MEDIA);
			} else if (SectoresEvento.GRADA_BAJA.toString().equals(sector.toUpperCase())){
				entrada = new Entrada(usuario, event, 40, SectoresEvento.GRADA_BAJA);
			} else if (SectoresEvento.PISTA.toString().equals(sector.toUpperCase())){
				entrada = new Entrada(usuario, event, 60, SectoresEvento.PISTA);
			} else if (SectoresEvento.FRONT_STAGE.toString().equals(sector.toUpperCase())){
				entrada = new Entrada(usuario, event, 80, SectoresEvento.FRONT_STAGE);
			}

			try{
				ticket = pm.getObjectById(Entrada.class, entrada.getId());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe){
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if(ticket != null){
				logger.info("Ticket already exists!");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("Ticket already exists").build();
			} else {
				if (SectoresEvento.VIP.toString().equals(sector.toUpperCase())){
					ticket = new Entrada(usuario, event, 100, SectoresEvento.VIP);
				} else if (SectoresEvento.GRADA_ALTA.toString().equals(sector.toUpperCase())){
					ticket = new Entrada(usuario, event, 20, SectoresEvento.GRADA_ALTA);
				} else if (SectoresEvento.GRADA_MEDIA.toString().equals(sector.toUpperCase())){
					ticket = new Entrada(usuario, event, 30, SectoresEvento.GRADA_MEDIA);
				} else if (SectoresEvento.GRADA_BAJA.toString().equals(sector.toUpperCase())){
					ticket = new Entrada(usuario, event, 40, SectoresEvento.GRADA_BAJA);
				} else if (SectoresEvento.PISTA.toString().equals(sector.toUpperCase())){
					ticket = new Entrada(usuario, event, 60, SectoresEvento.PISTA);
				} else if (SectoresEvento.FRONT_STAGE.toString().equals(sector.toUpperCase())){
					ticket = new Entrada(usuario, event, 80, SectoresEvento.FRONT_STAGE);
				}
				
				logger.info("Creating ticket: {}", ticket);
				
				pm.makePersistent(ticket);
				logger.info("Ticket created: {}", ticket);
				tx.commit();
				return Response.ok().build();
			}
		}
		finally{
			if(tx.isActive()){
				tx.rollback();
			}
		
		}

	}

	/**
	 * Retrieves the list of Entrada objects and returns a Response object.
	 * 
	 * @return a Response object containing the list of Entrada objects if found, or an error message if no tickets are found
	 */
	@GET
	@Path("/getEntradas")
	public Response getEntradas() {
		try {
			tx.begin();
			
			Query<Entrada> query = pm.newQuery(Entrada.class);
			
			@SuppressWarnings("unchecked")
			List<Entrada> entradas = (List<Entrada>) query.execute();

			if (entradas != null) {
				logger.info("{} tickets found", entradas.size());
				tx.commit();
				return Response.ok(entradas).build();
			} else {
				logger.info("No tickets found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("No tickets found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	
	}

	/**
	 * Deletes an entry from the database based on the provided ID.
	 * 
	 * @param id the ID of the entry to be deleted
	 * @return a Response object indicating the status of the operation
	 */
	@DELETE
	@Path("/eliminarEntrada/{id}")
	public Response eliminarEntrada(@PathParam("id") String id) {
		try {
			tx.begin();

			Entrada entrada = null;

			try {
				entrada = pm.getObjectById(Entrada.class, id);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			if (entrada != null) {
				logger.info("Deleting ticket: {}", entrada);
				pm.deletePersistent(entrada);
				tx.commit();
				return Response.ok().build();
			} else {
				logger.info("Ticket not found");
				tx.rollback();
				return Response.status(Response.Status.NOT_FOUND).entity("Ticket not found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Creates a new review based on the provided Resenya object.
	 * 
	 * @param resenya The Resenya object containing the review details.
	 * @return A Response object indicating the status of the operation.
	 */
	@POST 
	@Path("/crearResenya")
	public Response crearResenya(Resenya resenya) {
		try {
			tx.begin();
			Resenya review = null;

			try {
				review = pm.getObjectById(Resenya.class, resenya.getId());
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			} 

			if(review != null){
				logger.info("Review already exists!");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("Review already exists").build();
			}else{
				logger.info("Creating review: {}", review);
				review = new Resenya(resenya.getComentario(), resenya.getPuntuacion(), resenya.getUsuario(), resenya.getEvento());
				pm.makePersistent(review);
				logger.info("Review created: {}", review);
				tx.commit();
				eliminarEvento("" + resenya.getEvento().getId());
				return Response.ok().build();
			}
		} 
		finally 
		{
			if (tx.isActive()) 
			{
				tx.rollback();
			}
		}
	}

	/**
	 * Retrieves the list of reviews.
	 * 
	 * @return A Response object containing the list of reviews.
	 */
	@GET
	@Path("/getResenyas")
	public Response getResenyas() {
		try {
			tx.begin();
			
			Query<Resenya> query = pm.newQuery(Resenya.class);
			
			@SuppressWarnings("unchecked")
			List<Resenya> reseñas = (List<Resenya>) query.execute();

			if (reseñas != null) {
				logger.info("{} reviews found", reseñas.size());
				tx.commit();
				return Response.ok(reseñas).build();
			} else {
				logger.info("No reviews found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("No reviews found").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	
	}
	
	/**
	 * Retrieves the reviews of an event based on its ID.
	 * 
	 * @param id The ID of the event.
	 * @return A Response object containing the reviews of the event.
	 */
	@GET
	@Path("/getResenyasEvento/{id}")
	public Response getResenyasEvento(@PathParam("id") String id) {
		try {
			tx.begin();
			Resenya resenya = null;

			try {
				resenya = pm.getObjectById(Resenya.class, id);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			} 
			
			if (resenya != null) {
				logger.info("Reviews found: {}", resenya.getComentario());
				tx.commit();
				return Response.ok(resenya).build();
			} else {
				logger.info("No reviews found");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("No reviews found").build();
				
			}
			
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}


	@GET
	@Path("/sendMSG/{numero}/{mensaje}")
	public Response sendMSG(@PathParam("numero") String numero,@PathParam("mensaje") String mensaje) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		try {
       		@SuppressWarnings("unused")
			Message message = Message.creator(
                new PhoneNumber("whatsapp:" + numero),
                new PhoneNumber("whatsapp:+14155238886"), // Este es el número de Twilio sandbox para WhatsApp
                mensaje)
                .create();

        		logger.info("Msg sended to: {}",numero);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error sending message").build();
		}
	}

}