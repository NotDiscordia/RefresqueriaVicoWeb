package ByteBuilders.Entidad;

public class DetalleVenta {
    private int detalleVentaID;
    private Venta venta;
    private Producto producto;
    private int cantidad;
    private double precioVenta;

    public DetalleVenta() {}

    public DetalleVenta(int detalleVentaID, Venta venta, Producto producto, int cantidad, double precioVenta) {
        this.detalleVentaID = detalleVentaID;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    public int getDetalleVentaID() {
        return detalleVentaID;
    }

    public void setDetalleVentaID(int detalleVentaID) {
        this.detalleVentaID = detalleVentaID;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad + " ($" + precioVenta + ")";
    }
}
