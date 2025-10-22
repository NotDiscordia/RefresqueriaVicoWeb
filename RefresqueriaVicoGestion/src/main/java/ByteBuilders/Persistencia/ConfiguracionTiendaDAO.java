package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.*;
import java.sql.*;

public class ConfiguracionTiendaDAO {

    public ConfiguracionTienda obtenerConfiguracion() {
        String sql = "SELECT * FROM ConfiguracionTienda LIMIT 1";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new ConfiguracionTienda(
                        rs.getInt("ConfigID"),
                        rs.getString("NombreTienda"),
                        rs.getString("Direccion"),
                        rs.getString("Telefono"),
                        rs.getString("Horario")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(ConfiguracionTienda c) {
        String sql = "UPDATE ConfiguracionTienda SET NombreTienda=?, Direccion=?, Telefono=?, Horario=? WHERE ConfigID=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNombreTienda());
            stmt.setString(2, c.getDireccion());
            stmt.setString(3, c.getTelefono());
            stmt.setString(4, c.getHorario());
            stmt.setInt(5, c.getConfigID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
