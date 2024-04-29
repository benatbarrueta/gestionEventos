package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Represents a review for an event.
 */
@PersistenceCapable
public class Resenya {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long id;
    @Persistent
    private String comentario;
    @Persistent
    private int puntuacion;
    @Persistent(defaultFetchGroup = "true")
    private Usuario usuario;
    @Persistent(defaultFetchGroup = "true")
    private Evento evento;

    /**
     * Default constructor for Resenya class.
     * Initializes the comentario, puntuacion, usuario, and evento properties with default values.
     */
    public Resenya() {
        this.comentario = "";
        this.puntuacion = 0;
        this.usuario = new Usuario();
        this.evento = new Evento();
    }

    /**
     * Constructor for Resenya class.
     * Initializes the comentario, puntuacion, usuario, and evento properties with the given values.
     * 
     * @param comentario The comment for the review.
     * @param puntuacion The rating for the review.
     * @param usuario The user who made the review.
     * @param evento The event being reviewed.
     */
    public Resenya(String comentario, int puntuacion, Usuario usuario, Evento evento) {
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.usuario = usuario;
        this.evento = evento;
    }

    /**
     * Gets the ID of the review.
     * 
     * @return The ID of the review.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the comment of the review.
     * 
     * @return The comment of the review.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Gets the rating of the review.
     * 
     * @return The rating of the review.
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Gets the user who made the review.
     * 
     * @return The user who made the review.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Gets the event being reviewed.
     * 
     * @return The event being reviewed.
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Sets the comment of the review.
     * 
     * @param comentario The comment of the review.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Sets the rating of the review.
     * 
     * @param puntuacion The rating of the review.
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Sets the user who made the review.
     * 
     * @param usuario The user who made the review.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Sets the event being reviewed.
     * 
     * @param evento The event being reviewed.
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Returns a string representation of the Resenya object.
     * 
     * @return A string representation of the Resenya object.
     */
    @Override
    public String toString() {
        return "Resenya [id=" + id + ", comentario=" + comentario + ", puntuacion=" + puntuacion + ", usuario=" + usuario
                + ", evento=" + evento + "]";
    }
}
