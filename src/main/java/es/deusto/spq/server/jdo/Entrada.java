/**
 * Clase Entrada que representa una entrada para un evento.
 */
package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


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

    

    /**
     * Constructor vacio de la clase Entrada
     */
    public Entrada() {
        this.usuario = new Usuario();
        this.evento = new Evento();
    }
    /**
     * Constructor de la clase Entrada
     * @param usuario
     * @param evento
     * @param precio
     * @param sector
     */
    public Entrada(Usuario usuario , Evento evento, int precio, SectoresEvento sector) {
        this.usuario = usuario;
        this.evento = evento;
        this.precio = precio;
        this.sector = sector;
    }

    /**
     * Metodo para obtener el id de la entrada
     * @return
     */

    public long getId() {
        return id;
    }

    /**
     * Metodo para obtener el usuario de la entrada
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Metodo para establecer el usuario de la entrada
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Metodo para obtener el evento de la entrada
     * @return
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Metodo para establecer el evento de la entrada
     * @param evento
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Metodo para obtener el precio de la entrada
     * @return
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Metodo para establecer el precio de la entrada
     * @param precio
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Metodo para obtener el sector de la entrada
     * @return
     */
    public SectoresEvento getSector() {
        return sector;
    }

    /**
     * Metodo para establecer el sector de la entrada
     * @param sector
     */
    public void setSector(SectoresEvento sector) {
        this.sector = sector;
    }

    /**
     * Metodo para imprimir la entrada
     * @return
     */
    @Override
    public String toString() {
        return "Entrada" + id +
                ", usuario: " + usuario.getNombre() +
                ", evento: " + evento.getNombre();
    }

    public String toStringCorto() {
        return "Entrada " + id + " para evento -> "  + evento.getNombre();
    }
}

   


