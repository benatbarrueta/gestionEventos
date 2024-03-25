package es.deusto.spq.server.jdo;

public class Entrada {
    private Usuario usuario;
    //private Evento evento; 

    public Entrada() {
        this.usuario = new Usuario();
        //this.evento = new Evento();
    }

    public Entrada(Usuario usuario /*, Evento evento */) {
        this.usuario = usuario;
     // this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

   // public Evento getEvento() {
     //   return evento;
    //}

   //public void setEvento(Evento evento) {
     //   this.evento = evento;
    //}
}

   


