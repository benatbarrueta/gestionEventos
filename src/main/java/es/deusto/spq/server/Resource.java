package es.deusto.spq.server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import java.util.List;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import es.deusto.spq.server.jdo.Entrada;
import es.deusto.spq.server.jdo.Evento;

import es.deusto.spq.server.jdo.SectoresEvento;
import es.deusto.spq.server.jdo.TipoUsuario;
import es.deusto.spq.server.jdo.Usuario;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

	protected static final Logger logger = LogManager.getLogger();

	private PersistenceManager pm=null;
	private Transaction tx=null;


	public Resource() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	

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
				tx.commit();

				return Response.status(200).entity(user).build();
			
			} else {
				logger.info("Invalid email or password");
				tx.rollback();
				return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
	
	
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
				user = new Usuario(usuario.getNombre(), usuario.getApellidos(), usuario.getNombreUsuario(), usuario.getContrasenya(), usuario.getEmail(), usuario.getDireccion(), usuario.getTelefono(), TipoUsuario.GERENTE, usuario.getFechaNacimiento(), usuario.getDni());
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


	@POST
	@Path("/crearEvento")
	public Response crearEvento(Evento evento) {
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
				return Response.status(Response.Status.UNAUTHORIZED).entity("Event already exists").build();
			}
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	
	}

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

	@POST
	@Path("/actualizarEvento")
	public Response actualizarEvento(Evento evento) {
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

	@GET
	@Path("/comprarEntrada/{idEvento}/{idUsuario}/{sector}/{cantidad}")
	public Response comprarEntrada(@PathParam("idEvento") String eventId, @PathParam("idUsuario") String userId, @PathParam("sector") String sector, @PathParam("cantidad") String cantidad) {
		try{
			tx.begin();

			Entrada ticket = null;
			Evento event = null;
			Usuario user = null;

			try {
				event = pm.getObjectById(Evento.class, eventId);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}
			
			try {
				user = pm.getObjectById(Usuario.class, userId);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				logger.info("Exception launched: {}", jonfe.getMessage());
			}

			Entrada entrada = null;
			

			if (SectoresEvento.VIP.toString().equals(sector)){
				entrada = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.VIP);
			} else if (SectoresEvento.GRADA_ALTA.toString().equals(sector)){
				entrada = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.GRADA_ALTA);
			} else if (SectoresEvento.GRADA_MEDIA.toString().equals(sector)){
				entrada = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.GRADA_MEDIA);
			} else if (SectoresEvento.GRADA_BAJA.toString().equals(sector)){
				entrada = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.GRADA_BAJA);
			} else if (SectoresEvento.PISTA.toString().equals(sector)){
				entrada = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.PISTA);
			} else if (SectoresEvento.FRONT_STAGE.toString().equals(sector)){
				entrada = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.FRONT_STAGE);
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
				logger.info("Creating ticket: {}", ticket);
				ticket = new Entrada(user, event, Integer.parseInt(cantidad), SectoresEvento.fromString(sector));			

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
}
