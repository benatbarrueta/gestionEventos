package es.deusto.spq.server.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Evento {
    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
    private long id;
    @Persistent private String nombre;
    @Persistent private String lugar;
    @Persistent private Date fecha;
    @Persistent private String descripcion;
    @Persistent private int aforo;
    @Persistent private int precio;
    @Persistent private String organizador;
    @Persistent private SectoresEvento sector;


    public Evento() {
    }

    public Evento(String nombre, String lugar, Date fecha, String descripcion, int aforo, int precio, String organizador, SectoresEvento sector) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.aforo = aforo;
        this.precio = precio;
        this.organizador = organizador;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
        
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
    this.precio = precio;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public SectoresEvento getSector() {
        return sector;
    }

    public void setSector(SectoresEvento sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "nombre='" + nombre + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aforo='" + aforo + '\'' +
                ", precio='" + precio + '\'' +
                ", organizador='" + organizador + '\'' +
                ", sector='" + sector + '\'' +
                '}';
    }

}