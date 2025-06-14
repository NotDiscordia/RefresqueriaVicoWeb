package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (nombre, precio, stock, vendidos) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setBigDecimal(2, p.getPrecio());
            stmt.setInt(3, p.getStock());
            stmt.setInt(4, p.getVendidos() != null ? p.getVendidos() : 0);
            stmt.executeUpdate();
        }
    }

    public Producto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setVendidos(rs.getInt("vendidos"));
                return p;
            }
        }
        return null;
    }

    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection con = ConexionBD.obtenerConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setStock(rs.getInt("stock"));
                p.setVendidos(rs.getInt("vendidos"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, vendidos = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setBigDecimal(2, p.getPrecio());
            stmt.setInt(3, p.getStock());
            stmt.setInt(4, p.getVendidos());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        }
    }
}
