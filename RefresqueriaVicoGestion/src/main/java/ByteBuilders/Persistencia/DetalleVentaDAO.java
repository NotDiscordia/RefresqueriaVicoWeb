package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;
import java.util.*;

public class DetalleVentaDAO {

    public List<DetalleVenta> obtenerPorVenta(int ventaID) {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "SELECT * FROM DetallesVenta WHERE VentaID=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ventaID);
            ResultSet rs = stmt.executeQuery();

            ProductoDAO productoDAO = new ProductoDAO();
            Venta v = new Venta();
            v.setVentaID(ventaID);

            while (rs.next()) {
                Producto p = productoDAO.obtenerPorID(rs.getInt("ProductoID"));
                DetalleVenta d = new DetalleVenta(
                        rs.getInt("DetalleVentaID"),
                        v,
                        p,
                        rs.getInt("Cantidad"),
                        rs.getDouble("PrecioVenta")
                );
                detalles.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
