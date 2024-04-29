
package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Represents an entry for an event.
 */
@PersistenceCapable
public class Entrada {
    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    private long id;

    @Persistent(defaultFetchGroup = "true")
    private Usuario usuario;

    @Persistent(defaultFetchGroup = "true")
    private Evento evento;

    @Persistent private int precio;
    @Persistent private SectoresEvento sector;

    
    public Entrada() {
        this.usuario = new Usuario();
        this.evento = new Evento();
    }
    
    public Entrada(Usuario usuario , Evento evento, int precio, SectoresEvento sector) {
        this.usuario = usuario;
        this.evento = evento;
        this.precio = precio;
        this.sector = sector;
    }

    /**
     * Returns the ID of the entry.
     *
     * @return the ID of the entry
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the Usuario associated with this Entrada.
     *
     * @return the Usuario associated with this Entrada
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Sets the usuario associated with this Entrada.
     * 
     * @param usuario the usuario to be set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Returns the Evento associated with this Entrada.
     *
     * @return the Evento associated with this Entrada
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Sets the evento associated with this Entrada.
     * 
     * @param evento the evento to be set
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Returns the price of the ticket.
     *
     * @return the price of the ticket
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Sets the price of the ticket.
     * 
     * @param precio the price of the ticket
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Returns the sector of the event.
     *
     * @return the sector of the event
     */
    public SectoresEvento getSector() {
        return sector;
    }

    /**
     * Sets the sector of the event ticket.
     * 
     * @param sector the sector of the event ticket
     */
    public void setSector(SectoresEvento sector) {
        this.sector = sector;
    }

    /**
     * Represents a string of characters.
     * 
     * The String class represents character strings. All string literals in Java programs, such as "hello", are implemented as instances of this class.
     * Strings are constant; their values cannot be changed after they are created. String objects are immutable, which means that once created, their values cannot be modified.
     * 
     * @see java.lang.Object
     */
    @Override
    public String toString() {
        return "Entrada " + id +
                " usuario: " + usuario.getNombre() +
                ", evento: " + evento.getNombre();
    }

    /**
     * Returns a short string representation of the Entrada object.
     * 
     * @return A string representation of the Entrada object in the format "Entrada [id] para evento -> [nombre del evento]".
     */
    public String toStringCorto() {
        return "Entrada " + id + " para evento -> "  + evento.getNombre();
    }
}

   


