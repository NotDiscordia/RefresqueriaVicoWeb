package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellidos, celular, numero, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellidos());
            stmt.setString(3, u.getCelular());
            stmt.setString(4, u.getNumero());
            stmt.setString(5, u.getTipo());
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setNumero(rs.getString("numero"));
                usuario.setTipo(rs.getString("tipo"));
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = ConexionBD.obtenerConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setNumero(rs.getString("numero"));
                usuario.setTipo(rs.getString("tipo"));
                lista.add(usuario);
            }
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void actualizar(Usuario u) throws SQLException {
        String sql = """
            UPDATE usuarios 
            SET nombre = ?, 
                apellidos = ?, 
                celular = ?, 
                numero = ?, 
                tipo = ? 
            WHERE id = ?
            """;

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellidos());
            stmt.setString(3, u.getCelular());
            stmt.setString(4, u.getNumero());
            stmt.setString(5, u.getTipo());
            stmt.setInt(6, u.getId());
            stmt.executeUpdate();
        }
    }

    // Método adicional equivalente a buscarPorNombre del ProductoDAO
    public Usuario buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setCelular(rs.getString("celular"));
                usuario.setNumero(rs.getString("numero"));
                usuario.setTipo(rs.getString("tipo"));
                return usuario;
            }
        }
        return null;
    }
}