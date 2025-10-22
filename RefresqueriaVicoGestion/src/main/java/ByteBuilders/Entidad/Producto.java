package ByteBuilders.Entidad;

public class Producto {
    private int productoID;
    private String nombre;
    private String descripcion;
    private double precio;
    private int inventario;
    private String urlImagen;
    private Categoria categoria;
    private boolean estaActivo;

    public Producto() {}

    public Producto(int productoID, String nombre, String descripcion, double precio, int inventario,
                    String urlImagen, Categoria categoria, boolean estaActivo) {
        this.productoID = productoID;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.inventario = inventario;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.estaActivo = estaActivo;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }

    public double getPrecioVenta() {
        return 0;
    }
}
