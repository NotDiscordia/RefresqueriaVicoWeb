package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;
import java.util.*;

public class ProductoDAO {

    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setProductoID(rs.getInt("ProductoID"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setInventario(rs.getInt("Inventario"));
                p.setUrlImagen(rs.getString("URLImagen"));
                p.setEstaActivo(rs.getBoolean("EstaActivo"));

                // Relación con Categoría (puede ser null)
                int categoriaID = rs.getInt("CategoriaID");
                if (categoriaID > 0) {
                    Categoria c = new CategoriaDAO().obtenerPorID(categoriaID);
                    p.setCategoria(c);
                }

                productos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public Producto obtenerPorID(int id) {
        String sql = "SELECT * FROM Productos WHERE ProductoID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto p = new Producto();
                p.setProductoID(rs.getInt("ProductoID"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setInventario(rs.getInt("Inventario"));
                p.setUrlImagen(rs.getString("URLImagen"));
                p.setEstaActivo(rs.getBoolean("EstaActivo"));
                int categoriaID = rs.getInt("CategoriaID");
                if (categoriaID > 0)
                    p.setCategoria(new CategoriaDAO().obtenerPorID(categoriaID));
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertar(Producto p) {
        String sql = "INSERT INTO Productos (Nombre, Descripcion, Precio, Inventario, URLImagen, CategoriaID, EstaActivo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getInventario());
            stmt.setString(5, p.getUrlImagen());
            stmt.setObject(6, (p.getCategoria() != null) ? p.getCategoria().getCategoriaID() : null);
            stmt.setBoolean(7, p.isEstaActivo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Producto p) {
        String sql = "UPDATE Productos SET Nombre=?, Descripcion=?, Precio=?, Inventario=?, URLImagen=?, CategoriaID=?, EstaActivo=? WHERE ProductoID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getInventario());
            stmt.setString(5, p.getUrlImagen());
            stmt.setObject(6, (p.getCategoria() != null) ? p.getCategoria().getCategoriaID() : null);
            stmt.setBoolean(7, p.isEstaActivo());
            stmt.setInt(8, p.getProductoID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Productos WHERE ProductoID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
