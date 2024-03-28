package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.Column;
@PersistenceCapable
public class Entrada {
    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    private long id;

    @Persistent
    @ForeignKey(name="entrada_usuario_fk")
    @Column(name="usuario_dni")
    private Usuario usuario;

    @Persistent
    @ForeignKey(name="entrada_evento_fk")
    @Column(name="evento_id")
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

    public long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @Override
    public String toString() {
        return "Entrada" + id +
                ", usuario: " + usuario.getNombre() +
                ", evento: " + evento.getNombre();
    }

    public String toStringCorto() {
        return "Entrada para evento: " + evento.getNombre();
    }
}

   


