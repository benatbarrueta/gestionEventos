package es.deusto.spq.pojo;

public class EventoData {
    private String nombre;
    private String lugar;
    private String fecha;
    private String hora;
    private String descripcion;
    private String aforo;
    private String precio;
    private String organizador;
 
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
 
     public String getFecha() {
         return fecha;
     }
 
     public void setFecha(String fecha) {
         this.fecha = fecha;
     }
 
     public String getHora() {
         return hora;
     }
 
     public void setHora(String hora) {
         this.hora = hora;
     }
 
     public String getDescripcion() {
         return descripcion;
     }
 
     public void setDescripcion(String descripcion) {
         this.descripcion = descripcion;
     }
 
     public String getAforo() {
         return aforo;
     }
 
     public void setAforo(String aforo) {
         this.aforo = aforo;
     }
 
     public String getPrecio() {
         return precio;
     }
 
     public void setPrecio(String precio) {
         this.precio = precio;
     }
 
     public String getOrganizador() {
         return organizador;
     }
 
     public void setOrganizador(String organizador) {
         this.organizador = organizador;
     }
 
     @Override
     public String toString() {
         return "Evento{" +
                 "nombre='" + nombre + '\'' +
                 ", lugar='" + lugar + '\'' +
                 ", fecha='" + fecha + '\'' +
                 ", hora='" + hora + '\'' +
                 ", descripcion='" + descripcion + '\'' +
                 ", aforo='" + aforo + '\'' +
                 ", precio='" + precio + '\'' +
                 ", organizador='" + organizador + '\'' +
                 '}';
     }
}