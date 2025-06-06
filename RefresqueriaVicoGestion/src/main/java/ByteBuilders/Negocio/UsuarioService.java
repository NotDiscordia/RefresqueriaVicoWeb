package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Persistencia.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    // Método para verificar existencia de nombre
    public boolean existeNombre(String nombre) throws SQLException {
        return dao.buscarPorNombre(nombre) != null;
    }

    // Método para registro de usuario
    public boolean agregarUsuario(Usuario u) throws SQLException {
        if (existeNombre(u.getNombre())) return false;
        dao.insertar(u);
        return true;
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() throws SQLException {
        return dao.listar();
    }

    public Usuario obtenerUsuarioPorCelular(String celular) throws SQLException {
        return dao.buscarPorCelular(celular);
    }

    // Método para buscar usuario por ID
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    // Método para eliminar usuario
    public boolean eliminarUsuario(int id) throws SQLException {
        if (dao.buscarPorId(id) == null) return false;
        dao.eliminar(id);
        return true;
    }

    // Método para actualizar usuario
    public boolean actualizarUsuario(Usuario u) throws SQLException {
        if (dao.buscarPorId(u.getId()) == null) return false;
        dao.actualizar(u);
        return true;
    }

    // Método para buscar por nombre (nuevo)
    public Usuario buscarPorNombre(String nombre) throws SQLException {
        return dao.buscarPorNombre(nombre);
    }
}