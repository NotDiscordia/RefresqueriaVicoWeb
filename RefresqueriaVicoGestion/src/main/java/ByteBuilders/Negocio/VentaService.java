package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Venta;
import ByteBuilders.Persistencia.VentaDAO;

import java.sql.SQLException;
import java.util.List;

public class VentaService {
    private VentaDAO dao = new VentaDAO();

    public boolean registrarVenta(Venta venta) throws SQLException {
        if (venta.getUsuario() == null || venta.getUsuario().getId() == null) return false;
        dao.insertar(venta);
        return true;
    }

    public List<Venta> obtenerVentas() throws SQLException {
        return dao.listar();
    }

    public Venta obtenerVentaPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public boolean eliminarVenta(int id) throws SQLException {
        if (dao.buscarPorId(id) != null) {
            dao.eliminar(id);
            return true;
        }
        return false;
    }

    public boolean actualizarVenta(Venta venta) throws SQLException {
        if (dao.buscarPorId(venta.getId()) != null) {
            dao.actualizar(venta);
            return true;
        }
        return false;
    }
}
