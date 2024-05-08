package es.deusto.spq.server.jdo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * The Evento class represents an event.
 * It contains information such as the event's name, location, date, description, capacity, organizer, sectors, and ticket prices.
 */
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

    /**
     * Returns the ID of the Evento.
     *
     * @return the ID of the Evento
     */
    public long getId() {
        return id;
    }
    /**
     * Sets the ID of the Evento.
     * @param id
     */

    public void setId(long id) {
        this.id = id;
    }
    /**
     * Returns the nombre of the Evento.
     *
     * @return the nombre of the Evento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre of the Evento.
     * 
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the location of the event.
     *
     * @return the location of the event
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Sets the location of the event.
     * 
     * @param lugar the location of the event
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Returns the date of the event.
     *
     * @return the date of the event
     */
    public Date getFecha() {
        return fecha;
        
    }

    /**
     * Sets the fecha (date) of the event.
     *
     * @param fecha the date to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Returns the description of the event.
     *
     * @return the description of the event
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the description of the event.
     * 
     * @param descripcion the description of the event
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Returns the maximum capacity of the event.
     *
     * @return the maximum capacity of the event
     */
    public int getAforo() {
        return aforo;
    }

    /**
     * Sets the aforo (capacity) of the event.
     * 
     * @param aforo the new aforo value to set
     */
    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    /**
     * Returns the total capacity of the event.
     *
     * @return the total capacity of the event
     */
    public int getAforoTotal() {
        return aforoTotal;
    }

    /**
     * Sets the total capacity of the event.
     * 
     * @param aforoTotal the total capacity of the event
     */
    public void setAforoTotal(int aforoTotal) {
        this.aforoTotal = aforoTotal;
    }

    /**
     * Returns the organizer of the event.
     *
     * @return the organizer of the event
     */
    public String getOrganizador() {
        return organizador;
    }

    /**
     * Sets the organizer of the event.
     * 
     * @param organizador the name of the organizer
     */
    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    /**
     * Returns the list of sectors for this event.
     *
     * @return The list of sectors for this event.
     */
    public ArrayList<SectoresEvento> getSector() {
        return sectores;
    }

    /**
     * Sets the sectors of the event.
     * 
     * @param sector the list of sectors to be set
     */
    public void setSector(ArrayList<SectoresEvento> sector) {
        this.sectores = sector;
    }

    /**
     * Returns the map of sector prices for the event.
     *
     * @return the map of sector prices, where the key is the sector and the value is the price
     */
    public Map<SectoresEvento, Integer> getPrecioSector() {
        return precioSector;
    }

    /**
     * Sets the prices for each sector of the event.
     *
     * @param precioSector a map containing the prices for each sector of the event
     */
    public void setPrecioSector(Map<SectoresEvento, Integer> precioSector) {
        this.precioSector = precioSector;
    }

    /**
     * Returns the map of sectors and the number of tickets available for each sector.
     *
     * @return the map of sectors and the number of tickets available for each sector
     */
    public Map<SectoresEvento, Integer> getEntradasSector() {
        return entradasSector;
    }

    /**
     * Sets the number of tickets available for each sector of the event.
     *
     * @param entradasSector a map containing the number of tickets available for each sector
     */
    public void setEntradasSector(Map<SectoresEvento, Integer> entradasSector) {
        this.entradasSector = entradasSector;
    }

    /**
     * Returns a string representation of the Evento object.
     *
     * @return a string representation of the Evento object.
     */
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

    /**
     * Returns a string representation of the object in a short format.
     * The format includes the ID, name, place, and formatted date of the event.
     *
     * @return a string representation of the object in a short format
     */
    public String toStringCorto() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fechaFormateada = formato.format(this.fecha);

        return "ID: " + id +
                ", nombre: " + nombre +
                ", lugar: " + lugar +
                ", fecha: " + fechaFormateada;
                
    }

}