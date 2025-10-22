package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;
import java.util.*;

public class VentaDAO {

    public boolean registrarVenta(Venta venta) {
        String sqlVenta = "INSERT INTO Ventas (IDCliente, IDEmpleado, TipoVenta, Estado, MontoTotal) VALUES (?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO DetallesVenta (VentaID, ProductoID, Cantidad, PrecioVenta) VALUES (?, ?, ?, ?)";
        String sqlActualizarStock = "UPDATE Productos SET Inventario = Inventario - ? WHERE ProductoID = ? AND Inventario >= ?";

        Connection conn = null;
        PreparedStatement stmtVenta = null;
        PreparedStatement stmtDetalle = null;
        PreparedStatement stmtStock = null;

        try {
            conn = ConexionBD.obtenerConexion();
            conn.setAutoCommit(false); // inicia transacción

            // 1️⃣ Insertar cabecera de venta
            stmtVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            stmtVenta.setObject(1, venta.getCliente() != null ? venta.getCliente().getUsuarioID() : null);
            stmtVenta.setObject(2, venta.getEmpleado() != null ? venta.getEmpleado().getUsuarioID() : null);
            stmtVenta.setString(3, venta.getTipoVenta());
            stmtVenta.setString(4, venta.getEstado());
            stmtVenta.setDouble(5, venta.getMontoTotal());
            stmtVenta.executeUpdate();

            // Obtener ID generado
            ResultSet generatedKeys = stmtVenta.getGeneratedKeys();
            int ventaID = 0;
            if (generatedKeys.next()) {
                ventaID = generatedKeys.getInt(1);
                venta.setVentaID(ventaID);
            }

            // 2️⃣ Insertar detalles de la venta
            stmtDetalle = conn.prepareStatement(sqlDetalle);
            stmtStock = conn.prepareStatement(sqlActualizarStock);

            for (DetalleVenta d : venta.getDetalles()) {
                // Insertar detalle
                stmtDetalle.setInt(1, ventaID);
                stmtDetalle.setInt(2, d.getProducto().getProductoID());
                stmtDetalle.setInt(3, d.getCantidad());
                stmtDetalle.setDouble(4, d.getPrecioVenta());
                stmtDetalle.executeUpdate();

                // Actualizar stock del producto
                stmtStock.setInt(1, d.getCantidad());
                stmtStock.setInt(2, d.getProducto().getProductoID());
                stmtStock.setInt(3, d.getCantidad());

                int filas = stmtStock.executeUpdate();
                if (filas == 0) {
                    conn.rollback();
                    System.out.println("❌ No hay suficiente inventario para el producto ID: " + d.getProducto().getProductoID());
                    return false;
                }
            }

            conn.commit();
            System.out.println("✅ Venta registrada exitosamente (ID: " + ventaID + ")");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (stmtVenta != null) stmtVenta.close();
                if (stmtDetalle != null) stmtDetalle.close();
                if (stmtStock != null) stmtStock.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Venta> obtenerTodas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Ventas ORDER BY FechaCreacion DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            DetalleVentaDAO detalleDAO = new DetalleVentaDAO();

            while (rs.next()) {
                Usuario cliente = usuarioDAO.obtenerPorID(rs.getInt("IDCliente"));
                Usuario empleado = usuarioDAO.obtenerPorID(rs.getInt("IDEmpleado"));

                Venta v = new Venta(
                        rs.getInt("VentaID"),
                        cliente,
                        empleado,
                        rs.getString("TipoVenta"),
                        rs.getString("Estado"),
                        rs.getDouble("MontoTotal"),
                        rs.getTimestamp("FechaCreacion")
                );

                // Agregar detalles
                v.setDetalles(detalleDAO.obtenerPorVenta(v.getVentaID()));
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Venta obtenerPorID(int id) {
        String sql = "SELECT * FROM Ventas WHERE VentaID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario cliente = usuarioDAO.obtenerPorID(rs.getInt("IDCliente"));
                Usuario empleado = usuarioDAO.obtenerPorID(rs.getInt("IDEmpleado"));

                Venta v = new Venta(
                        rs.getInt("VentaID"),
                        cliente,
                        empleado,
                        rs.getString("TipoVenta"),
                        rs.getString("Estado"),
                        rs.getDouble("MontoTotal"),
                        rs.getTimestamp("FechaCreacion")
                );

                v.setDetalles(new DetalleVentaDAO().obtenerPorVenta(id));
                return v;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
