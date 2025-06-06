package ByteBuilders.Entidad;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String celular;
    private String contrasena; // Sin "ñ"
    private String rol;
    private String email;

    public Usuario() {}

    public Usuario(int id, String nombre, String apellidos, String celular, String contrasena, String rol, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.celular = celular;
        this.contrasena = contrasena;
        this.rol = rol;
        this.email = email;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
