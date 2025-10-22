package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;
import java.util.*;

public class CategoriaDAO {

    public Categoria obtenerPorID(int id) {
        String sql = "SELECT * FROM Categorias WHERE CategoriaID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categoria(rs.getInt("CategoriaID"), rs.getString("NombreCategoria"), rs.getString("Descripcion"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categoria> obtenerTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categorias";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categorias.add(new Categoria(
                        rs.getInt("CategoriaID"),
                        rs.getString("NombreCategoria"),
                        rs.getString("Descripcion")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}
