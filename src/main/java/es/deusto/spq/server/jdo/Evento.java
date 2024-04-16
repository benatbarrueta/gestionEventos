package es.deusto.spq.server.jdo;

import java.text.SimpleDateFormat;
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
    @Persistent private int aforoTotal;
    @Persistent private String organizador;
    @Persistent private ArrayList<SectoresEvento> sectores;
    @Persistent private Map<SectoresEvento, Integer> precioSector;
    @Persistent private Map<SectoresEvento, Integer> entradasSector;


    public Evento() {
    }

    public Evento(String nombre, String lugar, Date fecha, String descripcion, int aforo, int aforoTotal, String organizador, ArrayList<SectoresEvento> sector, Map<SectoresEvento, Integer> precioSector, Map<SectoresEvento, Integer> entradasSector) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.aforo = aforo;
        this.aforoTotal = aforoTotal;
        this.organizador = organizador;
        this.sectores = sector;
        this.precioSector = precioSector;
        this.entradasSector = entradasSector;
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

    public int getAforoTotal() {
        return aforoTotal;
    }

    public void setAforoTotal(int aforoTotal) {
        this.aforoTotal = aforoTotal;
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

    public Map<SectoresEvento, Integer> getPrecioSector() {
        return precioSector;
    }

    public void setPrecioSector(Map<SectoresEvento, Integer> precioSector) {
        this.precioSector = precioSector;
    }

    public Map<SectoresEvento, Integer> getEntradasSector() {
        return entradasSector;
    }

    public void setEntradasSector(Map<SectoresEvento, Integer> entradasSector) {
        this.entradasSector = entradasSector;
    }

    @Override
    public String toString() {
        

        return "Evento " +
                "-> nombre: " + nombre +
                ", lugar: " + lugar +
                ", fecha: " + fecha +
                ", descripcion: " + descripcion +
                ", aforo: " + aforo +
                ", organizador: " + organizador + 
                ", sectores disponibles: " + sectores +
                ", precios disponibles: " + precioSector +
                ", entradas disponibles: " + entradasSector;
    }

    public String toStringCorto() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fechaFormateada = formato.format(this.fecha);

        return "ID: " + id +
                ", nombre: " + nombre +
                ", lugar: " + lugar +
                ", fecha: " + fechaFormateada;
                
    }

}