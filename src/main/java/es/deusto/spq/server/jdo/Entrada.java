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
    @Persistent
    private Usuario usuario;
    private Evento evento; 

    public Entrada() {
        this.usuario = new Usuario();
        this.evento = new Evento();
    }

    public Entrada(Usuario usuario , Evento evento ) {
        this.usuario = usuario;
        this.evento = evento;
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
        return "Entrada{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", evento=" + evento +
                '}';
    }
}

   


