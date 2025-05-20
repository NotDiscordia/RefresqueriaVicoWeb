package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Producto;
import ByteBuilders.Persistencia.ProductoDAO;

import java.sql.SQLException;
import java.util.List;

public class ProductoService {
    private ProductoDAO dao = new ProductoDAO();

    public boolean agregarProducto(Producto p) throws SQLException {
        if (p.getNombre().matches(".*[^a-zA-Z0-9 ].*")) return false; // evita caracteres especiales
        if (dao.buscarPorId(p.getId() != null ? p.getId() : -1) != null) return false; // evita duplicados por ID
        dao.insertar(p);
        return true;
    }

    public List<Producto> obtenerProductos() throws SQLException {
        return dao.listar();
    }

    public Producto obtenerProductoPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public boolean eliminarProducto(int id) throws SQLException {
        if (dao.buscarPorId(id) != null) {
            dao.eliminar(id);
            return true;
        }
        return false;
    }

    public boolean actualizarProducto(Producto p) throws SQLException {
        if (dao.buscarPorId(p.getId()) != null) {
            dao.actualizar(p);
            return true;
        }
        return false;
    }
}
