package ByteBuilders.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/vicodb?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";

    private static final String CONTRASENA = "root";


    public static Connection obtenerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Esto te dirá el error exacto
            return null;
        }
    }
}
