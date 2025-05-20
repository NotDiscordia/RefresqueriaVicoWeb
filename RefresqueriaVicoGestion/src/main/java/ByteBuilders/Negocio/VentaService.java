package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Venta;
import ByteBuilders.Persistencia.VentaDAO;
import java.util.List;

public class VentaService {
    private final VentaDAO dao = new VentaDAO();

    public void registrarVenta(Venta venta) {
        dao.guardarVenta(venta); // Usar método correcto
    }

    public List<Venta> obtenerTodasVentas() {
        return dao.listarTodas(); // Nombre de método corregido
    }

    public Venta buscarVentaPorId(Long id) { // ✅ Usar Long
        return dao.buscarPorId(id);
    }

    public void eliminarVenta(Long id) { // ✅ Usar Long
        dao.eliminar(id);
    }

    public void actualizarVenta(Venta venta) {
        dao.actualizar(venta);
    }
}
