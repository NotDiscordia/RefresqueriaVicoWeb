package ByteBuilders.Entidad;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String celular;
    private String numero; // Contraseña
    private String tipo;   // "empleado", "admin", etc.

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellidos, String celular, String numero, String tipo, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.celular = celular;
        this.numero = numero;
        this.tipo = tipo;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEmail(String email) {

    }
}