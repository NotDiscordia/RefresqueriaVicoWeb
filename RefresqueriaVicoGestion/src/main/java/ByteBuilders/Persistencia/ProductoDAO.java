package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (nombre, precio, costo, stock, categoria, vendidos) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setBigDecimal(2, p.getPrecio());
            stmt.setBigDecimal(3, p.getCosto());
            stmt.setInt(4, p.getStock());
            stmt.setString(5, p.getCategoria());
            stmt.setInt(6, p.getVendidos() != null ? p.getVendidos() : 0);
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
                p.setCosto(rs.getBigDecimal("costo"));
                p.setStock(rs.getInt("stock"));
                p.setCategoria(rs.getString("categoria"));
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
                p.setCosto(rs.getBigDecimal("costo"));
                p.setStock(rs.getInt("stock"));
                p.setCategoria(rs.getString("categoria"));
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
        String sql = "UPDATE productos SET nombre = ?, precio = ?, costo = ?, stock = ?, categoria = ?, vendidos = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setBigDecimal(2, p.getPrecio());
            stmt.setBigDecimal(3, p.getCosto());
            stmt.setInt(4, p.getStock());
            stmt.setString(5, p.getCategoria());
            stmt.setInt(6, p.getVendidos());
            stmt.setInt(7, p.getId());
            stmt.executeUpdate();
        }
    }
}
