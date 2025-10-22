package ByteBuilders.Entidad;

import java.sql.Timestamp;
import java.util.List;

public class Venta {
    private int ventaID;
    private Usuario cliente;
    private Usuario empleado;
    private String tipoVenta; // PuntoDeVenta o EnLinea
    private String estado;    // Completada, Recibido, EnPreparacion, ListoParaRecoger
    private double montoTotal;
    private Timestamp fechaCreacion;
    private List<DetalleVenta> detalles;

    public Venta() {}

    public Venta(int ventaID, Usuario cliente, Usuario empleado, String tipoVenta, String estado,
                 double montoTotal, Timestamp fechaCreacion) {
        this.ventaID = ventaID;
        this.cliente = cliente;
        this.empleado = empleado;
        this.tipoVenta = tipoVenta;
        this.estado = estado;
        this.montoTotal = montoTotal;
        this.fechaCreacion = fechaCreacion;
    }

    public int getVentaID() {
        return ventaID;
    }

    public void setVentaID(int ventaID) {
        this.ventaID = ventaID;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Venta #" + ventaID + " - Total: $" + montoTotal;
    }
}
