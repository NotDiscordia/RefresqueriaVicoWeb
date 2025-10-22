package ByteBuilders.Entidad;

public class ConfiguracionTienda {
    private int configID;
    private String nombreTienda;
    private String direccion;
    private String telefono;
    private String horario;

    public ConfiguracionTienda() {}

    public ConfiguracionTienda(int configID, String nombreTienda, String direccion, String telefono, String horario) {
        this.configID = configID;
        this.nombreTienda = nombreTienda;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horario = horario;
    }

    public int getConfigID() {
        return configID;
    }

    public void setConfigID(int configID) {
        this.configID = configID;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return nombreTienda;
    }
}
