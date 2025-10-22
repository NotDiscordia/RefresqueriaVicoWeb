package ByteBuilders.Negocio;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Persistencia.UsuarioDAO;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Obtiene todos los usuarios del sistema.
     */
    public List<Usuario> obtenerTodos() {
        return usuarioDAO.obtenerTodos();
    }

    /**
     * Obtiene un usuario por su ID.
     */
    public Usuario obtenerPorID(int id) {
        return usuarioDAO.obtenerPorID(id);
    }

    /**
     * Crea un nuevo usuario validando sus datos básicos.
     */
    public boolean crearUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("⚠️ Usuario no puede ser nulo.");
            return false;
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            System.out.println("⚠️ El nombre del usuario es obligatorio.");
            return false;
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            System.out.println("⚠️ El correo del usuario es obligatorio.");
            return false;
        }

        // Puedes agregar más validaciones según tu modelo (ej. contraseña, rol, etc.)
        return usuarioDAO.insertar(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     */
    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() <= 0) {
            System.out.println("⚠️ ID de usuario inválido o usuario nulo.");
            return false;
        }

        Usuario existente = usuarioDAO.obtenerPorID(usuario.getIdUsuario());
        if (existente == null) {
            System.out.println("❌ No se puede actualizar: usuario no encontrado.");
            return false;
        }

        return usuarioDAO.actualizar(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     */
    public boolean eliminarUsuario(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido para eliminar usuario.");
            return false;
        }

        Usuario usuario = usuarioDAO.obtenerPorID(id);
        if (usuario == null) {
            System.out.println("❌ No se puede eliminar: usuario no encontrado.");
            return false;
        }

        return usuarioDAO.eliminar(id);
    }
}
