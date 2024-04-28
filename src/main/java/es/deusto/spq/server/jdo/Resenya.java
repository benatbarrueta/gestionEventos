package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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

    public Resenya() {
        this.comentario = "";
        this.puntuacion = 0;
        this.usuario = new Usuario();
        this.evento = new Evento();
    }

    public Resenya(String comentario, int puntuacion, Usuario usuario, Evento evento) {
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.usuario = usuario;
        this.evento = evento;
    }

    public long getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    

    @Override
    public String toString() {
        return "Resenya [id=" + id + ", comentario=" + comentario + ", puntuacion=" + puntuacion + ", usuario=" + usuario
                + ", evento=" + evento + "]";
    }
}
