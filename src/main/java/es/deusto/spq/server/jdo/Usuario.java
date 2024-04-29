package es.deusto.spq.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.*;

import java.util.Date;

/**
 * Represents a user in the system.
 */
@PersistenceCapable
public class Usuario {
    @Persistent private String nombre;
    @Persistent private String apellidos; 
    @Persistent private String nombreUsuario;  
    @Persistent private String contrasenya;
    @Persistent private String email;
    @Persistent private String direccion;
    @Persistent private String telefono;
    @Persistent private TipoUsuario rol;   
    @Persistent private Date fechaNacimiento; 
    @PrimaryKey private String dni;
    
    public Usuario(String nombre, String apellidos, String nombreUsuario, String contrasenya, String email, String direccion, String telefono, TipoUsuario rol, Date fechaNacimiento, String dni) {
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
        this.rol = TipoUsuario.CLIENTE;
        this.fechaNacimiento = new Date();
        this.dni = "";
    }

    public Usuario(String nombreUsuario, String contrasenya) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
    }


    /**
     * Returns the nombre of the Usuario.
     *
     * @return the nombre of the Usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre of the Usuario.
     * 
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param apellidos the last name to be set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Returns the nombreUsuario of the Usuario.
     *
     * @return the nombreUsuario of the Usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Sets the nombreUsuario of the Usuario.
     *
     * @param nombreUsuario the new nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Sets the password for the user.
     * 
     * @param contrasenya the password to set
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the direccion of the Usuario.
     *
     * @return the direccion of the Usuario
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the direccion of the Usuario.
     * 
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Returns the telephone number of the user.
     *
     * @return the telephone number of the user
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the telefono of the Usuario.
     * 
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Represents the type of user.
     */
    public TipoUsuario getRol() {
        return rol;
    }

    /**
     * Sets the role of the user.
     * 
     * @param rol the role to be set
     */
    public void setRol(TipoUsuario rol) {
        this.rol = rol;
    }

    /**
     * Returns the date of birth of the user.
     *
     * @return the date of birth of the user
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the date of birth for the user.
     * 
     * @param fechaNacimiento the date of birth to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Returns the DNI (Documento Nacional de Identidad) of the user.
     *
     * @return the DNI of the user
     */
    public String getDni() {
        return dni;
    }

    /**
     * Sets the DNI (Documento Nacional de Identidad) of the user.
     * 
     * @param dni the DNI to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
        * Returns a string representation of the Usuario object.
        *
        * @return a string representation of the Usuario object
        */
    @Override
    public String toString() {
        return "Usuario -> " +
                "nombre=" + nombre +
                ", apellidos=" + apellidos +
                ", nombreUsuario=" + nombreUsuario +
                ", contrasenya=" + contrasenya +
                ", email=" + email +
                ", direccion=" + direccion +
                ", telefono=" + telefono +
                ", rol=" + rol +
                ", fechaNacimiento=" + fechaNacimiento +
                ", dni=" + dni;
    }
}
