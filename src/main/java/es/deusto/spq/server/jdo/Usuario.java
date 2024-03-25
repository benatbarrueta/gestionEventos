package es.deusto.spq.server.jdo;

import java.util.Date;

public class Usuario {
    private String nombre;
    private String apellidos; 
    private String nombreUsuario;  
    private String contrasenya;
    private String email;
    private String direccion;
    private String telefono;
    private String rol;   
    private Date fechaNacimiento; 
    private String dni;
    
    public Usuario(String nombre, String apellidos, String nombreUsuario, String contrasenya, String email, String direccion, String telefono, String rol, Date fechaNacimiento, String dni) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rol = rol;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;    
    }

    public Usuario() {
        this.nombre = "";
        this.apellidos = "";
        this.nombreUsuario = "";
        this.contrasenya = "";
        this.email = "";
        this.direccion = "";
        this.telefono = "";
        this.rol = "";
        this.fechaNacimiento = new Date();
        this.dni = "";
    } 


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol='" + rol + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", dni='" + dni + '\'' +
                '}';
    }
}
