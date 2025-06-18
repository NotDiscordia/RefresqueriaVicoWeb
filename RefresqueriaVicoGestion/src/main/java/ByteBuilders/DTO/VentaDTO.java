package ByteBuilders.DTO;

import java.math.BigDecimal;
import java.util.List;

public class VentaDTO {
    private List<Item> productos;
    private BigDecimal total;
    private BigDecimal precio;
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }


    public static class Item {
        private Long id;
        private Integer cantidad;

        // Getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }

    // Getters y setters
    public List<Item> getProductos() { return productos; }
    public void setProductos(List<Item> productos) { this.productos = productos; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
