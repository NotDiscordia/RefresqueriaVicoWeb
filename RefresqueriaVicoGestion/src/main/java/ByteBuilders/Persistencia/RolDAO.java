package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;

public class RolDAO {

    public Rol obtenerPorID(int id) {
        String sql = "SELECT * FROM Roles WHERE RolID=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rol(rs.getInt("RolID"), rs.getString("NombreRol"), rs.getString("Descripcion"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
