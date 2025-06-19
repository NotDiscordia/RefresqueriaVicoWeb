package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // Guardar una venta (INSERT)
    public void guardarVenta(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (fecha_hora, total, metodo_pago, cambio, monto_entregado, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(venta.getFechaHora()));
            stmt.setBigDecimal(2, venta.getTotal());
            stmt.setString(3, venta.getMetodoPago());
            stmt.setBigDecimal(4, venta.getCambio());
            stmt.setBigDecimal(5, venta.getMontoEntregado());

            if (venta.getUsuario() != null && venta.getUsuario().getId() != null) {
                stmt.setLong(6, venta.getUsuario().getId());
            } else {
                stmt.setNull(6, java.sql.Types.BIGINT);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    venta.setId(rs.getLong(1));
                }
            }
        }
    }


    // Listar todas las ventas
    public List<Venta> listarTodas() throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getLong("id"));
                venta.setFechaHora(rs.getTimestamp("fecha_Hora").toLocalDateTime());
                venta.setTotal(rs.getBigDecimal("total"));
                venta.setMetodoPago(rs.getString("metodo_pago")); // Cambiado a metodo_pago
                venta.setCambio(rs.getBigDecimal("cambio"));
                venta.setMontoEntregado(rs.getBigDecimal("monto_entregado")); // Agregado monto_entregado
                lista.add(venta);
            }
        }
        return lista;
    }

    // Buscar venta por ID
    public Venta buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Venta venta = new Venta();
                    venta.setId(rs.getLong("id"));
                    venta.setFechaHora(rs.getTimestamp("fecha_Hora").toLocalDateTime());
                    venta.setTotal(rs.getBigDecimal("total"));
                    venta.setMetodoPago(rs.getString("metodo_pago")); // Cambiado a metodo_pago
                    venta.setCambio(rs.getBigDecimal("cambio"));
                    venta.setMontoEntregado(rs.getBigDecimal("monto_entregado")); // Agregado monto_entregado
                    return venta;
                }
            }
        }
        return null;
    }

    // Eliminar venta por ID
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM ventas WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // Actualizar venta
    public void actualizar(Venta venta) throws SQLException {
        String sql = "UPDATE ventas SET fecha_Hora = ?, total = ?, metodo_pago = ?, cambio = ?, monto_entregado = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(venta.getFechaHora()));
            stmt.setBigDecimal(2, venta.getTotal());
            stmt.setString(3, venta.getMetodoPago());
            stmt.setBigDecimal(4, venta.getCambio());
            stmt.setBigDecimal(5, venta.getMontoEntregado()); // Agregado monto_entregado
            stmt.setLong(6, venta.getId());

            stmt.executeUpdate();
        }
    }

    // Guardar corte de caja
    // Método para guardar corte de caja
    public CortesCaja guardarCorteCaja(CortesCaja corte) throws SQLException {
        String sql = "INSERT INTO cortes_caja (fecha, total_ventas, total_efectivo, usuario_id, moneda, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(corte.getFecha()));
            stmt.setBigDecimal(2, corte.getTotalVentas());
            stmt.setBigDecimal(3, corte.getTotalEfectivo());
            stmt.setInt(4, corte.getUsuario().getId());
            stmt.setString(5, corte.getMoneda().name());
            stmt.setString(6, corte.getEstado().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    corte.setId(rs.getInt(1));
                }
            }
            return corte;
        }
    }

    // Método auxiliar para mapear ventas
    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(rs.getLong("id"));
        venta.setFechaHora(rs.getTimestamp("fecha_Hora").toLocalDateTime());
        venta.setTotal(rs.getBigDecimal("total"));
        venta.setMetodoPago(rs.getString("metodo_pago"));
        venta.setCambio(rs.getBigDecimal("cambio"));
        venta.setMontoEntregado(rs.getBigDecimal("monto_entregado"));
        return venta;
    }

    // Obtener ventas de un día específico
    public List<Venta> obtenerVentasDelDia(LocalDate fecha) throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE DATE(fecha_Hora) = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = mapearVenta(rs);
                    lista.add(venta);
                }
            }
        }
        return lista;
    }

    // Obtener todos los cortes de caja ordenados por fecha descendente
    public List<CortesCaja> obtenerTodosCortesCaja() throws SQLException {
        List<CortesCaja> lista = new ArrayList<>();
        String sql = "SELECT * FROM cortes_caja ORDER BY fecha DESC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CortesCaja corte = new CortesCaja();
                corte.setId(rs.getInt("id"));
                corte.setFecha(rs.getDate("fecha").toLocalDate());
                corte.setTotalVentas(rs.getBigDecimal("total_ventas"));
                corte.setTotalEfectivo(rs.getBigDecimal("total_efectivo"));

                // Nuevos campos
                corte.setMoneda(Moneda.valueOf(rs.getString("moneda")));
                corte.setEstado(EstadoCorte.valueOf(rs.getString("estado")));

                // Cargar usuario
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                corte.setUsuario(usuario);

                lista.add(corte);
            }
        }
        return lista;
    }

    // Obtener producto por id
    public Producto obtenerProductoPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getBigDecimal("precio"));
                    // asigna otros campos según tu entidad Producto
                    return producto;
                }
            }
        }
        return null;
    }

    // Devuelve la suma total de ventas del día actual
    public BigDecimal obtenerTotalVentasDelDia() throws SQLException {
        String sql = "SELECT COALESCE(SUM(total), 0) AS total_dia FROM ventas WHERE DATE(fecha_Hora) = CURRENT_DATE";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getBigDecimal("total_dia");
            } else {
                return BigDecimal.ZERO;
            }
        }
    }

}
