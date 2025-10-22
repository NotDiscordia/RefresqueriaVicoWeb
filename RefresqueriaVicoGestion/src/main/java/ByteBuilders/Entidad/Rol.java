package ByteBuilders.Entidad;

public class Rol {
    private int rolID;
    private String nombreRol;
    private String descripcion;

    public Rol() {}

    public Rol(int rolID, String nombreRol, String descripcion) {
        this.rolID = rolID;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }

    public int getRolID() {
        return rolID;
    }

    public void setRolID(int rolID) {
        this.rolID = rolID;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombreRol;
    }
}