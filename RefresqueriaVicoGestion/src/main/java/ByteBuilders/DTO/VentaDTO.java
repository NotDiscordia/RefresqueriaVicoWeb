package ByteBuilders.DTO;

import java.math.BigDecimal;
import java.util.List;

public class VentaDTO {
    private List<Item> productos;
    private BigDecimal total;
    private BigDecimal montoEntregado;
    private BigDecimal cambio;
    private String metodoPago;
    private String moneda;

    public static class Item {
        private Long id;
        private Integer cantidad;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }

    public List<Item> getProductos() { return productos; }
    public void setProductos(List<Item> productos) { this.productos = productos; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getMontoEntregado() { return montoEntregado; }
    public void setMontoEntregado(BigDecimal montoEntregado) { this.montoEntregado = montoEntregado; }

    public BigDecimal getCambio() { return cambio; }
    public void setCambio(BigDecimal cambio) { this.cambio = cambio; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}
