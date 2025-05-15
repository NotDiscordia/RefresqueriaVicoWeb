package ByteBuilders.Persistencia;

import ByteBuilders.ConexionBD;
import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Entidad.Venta;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public void insertar(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (fecha_hora, total, usuario_id) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, venta.getFechaHora() != null ? Timestamp.from(venta.getFechaHora()) : null);
            stmt.setBigDecimal(2, venta.getTotal());
            stmt.setInt(3, venta.getUsuario().getId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    venta.setId(rs.getInt(1));
                }
            }
        }
    }

    public Venta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                Timestamp ts = rs.getTimestamp("fecha_hora");
                v.setFechaHora(ts != null ? ts.toInstant() : null);
                v.setTotal(rs.getBigDecimal("total"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id")); // Solo el ID, si se desea más info, hay que consultar UsuarioDAO
                v.setUsuario(u);

                return v;
            }
        }
        return null;
    }

    public List<Venta> listar() throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        try (Connection con = ConexionBD.getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                Timestamp ts = rs.getTimestamp("fecha_hora");
                v.setFechaHora(ts != null ? ts.toInstant() : null);
                v.setTotal(rs.getBigDecimal("total"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("usuario_id"));
                v.setUsuario(u);

                lista.add(v);
            }
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM ventas WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void actualizar(Venta venta) throws SQLException {
        String sql = "UPDATE ventas SET fecha_hora = ?, total = ?, usuario_id = ? WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTimestamp(1, venta.getFechaHora() != null ? Timestamp.from(venta.getFechaHora()) : null);
            stmt.setBigDecimal(2, venta.getTotal());
            stmt.setInt(3, venta.getUsuario().getId());
            stmt.setInt(4, venta.getId());
            stmt.executeUpdate();
        }
    }
}
