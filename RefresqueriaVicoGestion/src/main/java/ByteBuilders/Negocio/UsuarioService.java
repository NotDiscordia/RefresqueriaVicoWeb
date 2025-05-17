package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Persistencia.UsuarioDAO;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO dao = new UsuarioDAO();

    public boolean registrarUsuario(Usuario u) throws SQLException {
        if (u.getEmail() == null || u.getEmail().isEmpty()) return false;
        if (dao.buscarPorId(u.getId() != null ? u.getId() : -1) != null) return false; // Evitar duplicados
        dao.insertar(u);
        return true;
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        return dao.listar();
    }

    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public boolean eliminarUsuario(int id) throws SQLException {
        if (dao.buscarPorId(id) != null) {
            dao.eliminar(id);
            return true;
        }
        return false;
    }

    public boolean actualizarUsuario(Usuario u) throws SQLException {
        if (dao.buscarPorId(u.getId()) != null) {
            dao.actualizar(u);
            return true;
        }
        return false;
    }
}
