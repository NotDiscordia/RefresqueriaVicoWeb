package ByteBuilders.Negocio;

import ByteBuilders.Entidad.CortesCaja;
import ByteBuilders.Entidad.EstadoCorte;
import ByteBuilders.Entidad.Venta;
import ByteBuilders.Entidad.Moneda;
import ByteBuilders.Persistencia.VentaDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VentaService {
    private final VentaDAO dao = new VentaDAO();
    private final UsuarioService usuarioService = new UsuarioService();

    public CortesCaja realizarCorteCaja(BigDecimal totalEfectivo, int usuarioId, Moneda moneda) throws SQLException {
        BigDecimal totalVentas = calcularTotalVentasDelDia();
        BigDecimal totalEfectivoTeorico = totalVentas;

        // Convertir USD a MXN si es necesario
        if (moneda == Moneda.USD) {
            totalEfectivo = convertirAPesos(totalEfectivo);
        }

        CortesCaja corte = new CortesCaja();
        corte.setFecha(LocalDate.now());
        corte.setTotalVentas(totalVentas);
        corte.setTotalEfectivo(totalEfectivo);
        corte.setUsuario(usuarioService.obtenerUsuarioPorId(usuarioId));
        corte.setMoneda(moneda);
        corte.setEstado(compararCorte(totalEfectivo, totalEfectivoTeorico));

        return dao.guardarCorteCaja(corte);
    }

    private BigDecimal convertirAPesos(BigDecimal dolares) {
        // Tasa de cambio: 1 USD = 20 MXN (ajustable)
        return dolares.multiply(new BigDecimal("20"));
    }

    public BigDecimal calcularTotalVentasDelDia() throws SQLException {
        LocalDate hoy = LocalDate.now();
        return dao.obtenerVentasDelDia(hoy).stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public EstadoCorte compararCorte(BigDecimal efectivoFisico, BigDecimal efectivoTeorico) {
        int comparacion = efectivoFisico.compareTo(efectivoTeorico);

        if (comparacion == 0) {
            return EstadoCorte.OK;
        } else if (comparacion < 0) {
            return EstadoCorte.FALTANTE;
        } else {
            return EstadoCorte.SOBRANTE;
        }
    }


    public List<CortesCaja> obtenerCortesHistoricos() throws SQLException {
        return dao.obtenerTodosCortesCaja();
    }

    public List<CortesCaja> obtenerCortesCaja() throws SQLException {
        return dao.obtenerTodosCortesCaja();
    }

    public void registrarVenta(Venta venta) throws SQLException {
        dao.guardarVenta(venta);
    }

    public List<Venta> obtenerTodasVentas() throws SQLException {
        return dao.listarTodas();
    }

    public Venta buscarVentaPorId(Long id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public void eliminarVenta(Long id) throws SQLException {
        dao.eliminar(id);
    }

    public void actualizarVenta(Venta venta) throws SQLException {
        dao.actualizar(venta);
    }
}
