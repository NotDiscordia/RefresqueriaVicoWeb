package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;
import java.util.*;

public class UsuarioDAO {

    public Usuario obtenerPorID(int id) {
        String sql = "SELECT * FROM Usuarios WHERE UsuarioID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Rol rol = new RolDAO().obtenerPorID(rs.getInt("RolID"));
                return new Usuario(
                        rs.getInt("UsuarioID"),
                        rs.getString("Nombre"),
                        rs.getString("Correo"),
                        rs.getString("Telefono"),
                        rs.getString("ContrasenaHash"),
                        rol,
                        rs.getTimestamp("FechaCreacion")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            RolDAO rolDAO = new RolDAO();
            while (rs.next()) {
                Rol rol = rolDAO.obtenerPorID(rs.getInt("RolID"));
                lista.add(new Usuario(
                        rs.getInt("UsuarioID"),
                        rs.getString("Nombre"),
                        rs.getString("Correo"),
                        rs.getString("Telefono"),
                        rs.getString("ContrasenaHash"),
                        rol,
                        rs.getTimestamp("FechaCreacion")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     */
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (Nombre, Correo, Telefono, ContrasenaHash, RolID, FechaCreacion) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getContrasenaHash());
            stmt.setInt(5, usuario.getRol().getRolID());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     */
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuarios SET Nombre=?, Correo=?, Telefono=?, ContrasenaHash=?, RolID=? WHERE UsuarioID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getContrasenaHash());
            stmt.setInt(5, usuario.getRol().getRolID());
            stmt.setInt(6, usuario.getIdUsuario());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un usuario por su ID.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Usuarios WHERE UsuarioID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
