package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Persistencia.UsuarioDAO;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean registrarUsuario(Usuario u) {
        if (usuarioDAO.existePorEmail(u.getEmail())) return false;
        usuarioDAO.guardarUsuario(u);
        return true;
    }

    /*public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioDAO.existePorEmail(email);
    }

     */

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {  // ✔️ Long
        return usuarioDAO.buscarPorId(id);
    }

    public boolean eliminarUsuario(Long id) {  // ✔️ Long
        return usuarioDAO.eliminarPorId(id);
    }
}