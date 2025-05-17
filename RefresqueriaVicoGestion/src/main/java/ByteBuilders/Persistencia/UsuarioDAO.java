package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.ConexionBD;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email, contraseña, rol, fecha_registro, numero_celular) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getContraseña());
            stmt.setString(4, u.getRol());
            stmt.setTimestamp(5, u.getFechaRegistro() != null ? Timestamp.from(u.getFechaRegistro()) : null);
            stmt.setString(6, u.getNumeroCelular());
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(rs.getString("contraseña"));
                u.setRol(rs.getString("rol"));
                Timestamp ts = rs.getTimestamp("fecha_registro");
                u.setFechaRegistro(ts != null ? ts.toInstant() : null);
                u.setNumeroCelular(rs.getString("numero_celular"));
                return u;
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection con = ConexionBD.getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(rs.getString("contraseña"));
                u.setRol(rs.getString("rol"));
                Timestamp ts = rs.getTimestamp("fecha_registro");
                u.setFechaRegistro(ts != null ? ts.toInstant() : null);
                u.setNumeroCelular(rs.getString("numero_celular"));
                lista.add(u);
            }
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, email = ?, contraseña = ?, rol = ?, fecha_registro = ?, numero_celular = ? WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getContraseña());
            stmt.setString(4, u.getRol());
            stmt.setTimestamp(5, u.getFechaRegistro() != null ? Timestamp.from(u.getFechaRegistro()) : null);
            stmt.setString(6, u.getNumeroCelular());
            stmt.setInt(7, u.getId());
            stmt.executeUpdate();
        }
    }
}
