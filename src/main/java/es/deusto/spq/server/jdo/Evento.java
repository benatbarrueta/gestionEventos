package es.deusto.spq.server.jdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
    @Persistent private String organizador;
    @Persistent private ArrayList<SectoresEvento> sectores;
    @Persistent private Map<SectoresEvento, Integer> precioSector;


    public Evento() {
    }

    public Evento(String nombre, String lugar, Date fecha, String descripcion, int aforo, Map<SectoresEvento, Integer> precio, String organizador, ArrayList<SectoresEvento> sector) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.aforo = aforo;
        this.precioSector = precio;
        this.organizador = organizador;
        this.sectores = sector;
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

    public Map<SectoresEvento, Integer> getPrecio() {
        return precioSector;
    }

    public void setPrecio(Map<SectoresEvento, Integer> precio) {
    this.precioSector = precio;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public ArrayList<SectoresEvento> getSector() {
        return sectores;
    }

    public void setSector(ArrayList<SectoresEvento> sector) {
        this.sectores = sector;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", aforo=" + aforo +
                ", organizador='" + organizador + '\'' +
                ", sectores=" + sectores +
                ", precio=" + precioSector +
                '}';
    }

}