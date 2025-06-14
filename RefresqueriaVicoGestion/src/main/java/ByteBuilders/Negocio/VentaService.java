package ByteBuilders.Negocio;

import ByteBuilders.Entidad.CortesCaja;
import ByteBuilders.Entidad.Venta;
import ByteBuilders.Persistencia.VentaDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VentaService {
    private final VentaDAO dao = new VentaDAO();
    private final UsuarioService usuarioService = new UsuarioService();

    public CortesCaja realizarCorteCaja(BigDecimal totalEfectivo, int usuarioId) throws SQLException {
        // 1. Calcular total de ventas del día
        BigDecimal totalVentas = calcularTotalVentasDelDia();

        // 2. Crear registro de corte
        CortesCaja corte = new CortesCaja();
        corte.setFecha(LocalDate.now());
        corte.setTotalVentas(totalVentas);
        corte.setTotalEfectivo(totalEfectivo);
        corte.setUsuario(usuarioService.obtenerUsuarioPorId(usuarioId));

        // 3. Guardar en BD
        return dao.guardarCorteCaja(corte);
    }

    private BigDecimal calcularTotalVentasDelDia() {
        LocalDate hoy = LocalDate.now();
        return dao.obtenerVentasDelDia(hoy).stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CortesCaja> obtenerCortesCaja() {
        return dao.obtenerTodosCortesCaja();
    }

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
