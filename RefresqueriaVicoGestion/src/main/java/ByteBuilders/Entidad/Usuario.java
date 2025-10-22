package ByteBuilders.Entidad;

import java.sql.Timestamp;

public class Usuario {
    private int usuarioID;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasenaHash;
    private Rol rol;
    private Timestamp fechaCreacion;

    public Usuario() {}

    public Usuario(int usuarioID, String nombre, String correo, String telefono, String contrasenaHash, Rol rol, Timestamp fechaCreacion) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
        this.fechaCreacion = fechaCreacion;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return nombre + " (" + rol.getNombreRol() + ")";
    }

    public int getIdUsuario() {
        return 0;
    }
}
