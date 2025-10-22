package ByteBuilders.Negocio;

import ByteBuilders.Entidad.*;
import ByteBuilders.Persistencia.*;

import java.math.BigDecimal;
import java.util.*;

public class VentaService {

    private final VentaDAO ventaDAO = new VentaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();

    /**
     * Registra una venta si todos los productos tienen stock suficiente.
     */
    public boolean registrarVenta(Venta venta) {
        if (venta == null || venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            System.out.println("❌ No se puede registrar una venta vacía.");
            return false;
        }

        double total = 0;

        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoDAO.obtenerPorID(detalle.getProducto().getProductoID());

            if (producto == null) {
                System.out.println("❌ Producto no encontrado: " + detalle.getProducto().getProductoID());
                return false;
            }

            if (producto.getInventario() < detalle.getCantidad()) {
                System.out.println("⚠️ No hay stock suficiente para: " + producto.getNombre());
                return false;
            }

            // Calcular total y restar stock
            detalle.setPrecioVenta(producto.getPrecioVenta());
            total += detalle.getPrecioVenta() * detalle.getCantidad();

            producto.setInventario(producto.getInventario() - detalle.getCantidad());
            productoDAO.actualizar(producto);
        }

        venta.setMontoTotal(total);
        venta.setEstado("Completada");
        venta.setTipoVenta("PuntoDeVenta");

        return ventaDAO.registrarVenta(venta);
    }

    /**
     * Obtiene una venta con sus detalles.
     */
    public Venta obtenerVentaPorID(int id) {
        return ventaDAO.obtenerPorID(id);
    }

    /**
     * Devuelve todas las ventas registradas.
     */
    public List<Venta> listarVentas() {
        return ventaDAO.obtenerTodas();
    }

    /**
     * Calcula el total de ventas registradas.
     */
    public double calcularTotalVentas() {
        List<Venta> ventas = ventaDAO.obtenerTodas();
        return ventas.stream().mapToDouble(Venta::getMontoTotal).sum();
    }

    public List<Venta> obtenerTodas() {
        return List.of();
    }

    public BigDecimal calcularTotalVentasDelDia() {
        return null;
    }

    public boolean realizarVenta(Venta venta) {
        return false;
    }
}

