package ByteBuilders.Negocio;

import ByteBuilders.Entidad.*;
import ByteBuilders.Persistencia.*;
import java.util.*;

public class ProductoService {

    private final ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> listarProductos() {
        return productoDAO.obtenerTodos();
    }

    public Producto obtenerPorID(int id) {
        return productoDAO.obtenerPorID(id);
    }

    public boolean registrarProducto(Producto p) {
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            System.out.println("⚠️ El nombre del producto es obligatorio.");
            return false;
        }
        if (p.getPrecioVenta() <= 0) {
            System.out.println("⚠️ El precio de venta debe ser mayor a 0.");
            return false;
        }
        return productoDAO.insertar(p);
    }

    public boolean actualizarStock(int productoID, int nuevaCantidad) {
        Producto p = productoDAO.obtenerPorID(productoID);
        if (p == null) {
            System.out.println("❌ Producto no encontrado.");
            return false;
        }
        if (nuevaCantidad < 0) {
            System.out.println("⚠️ No se puede asignar stock negativo.");
            return false;
        }
        p.setInventario(nuevaCantidad);
        return productoDAO.actualizar(p);
    }

    public boolean agregarProducto(Producto producto) {
        return false;
    }

    public boolean actualizarProducto(Producto producto) {
        return false;
    }

    public List<Producto> obtenerTodos() {
        return List.of();
    }
}
