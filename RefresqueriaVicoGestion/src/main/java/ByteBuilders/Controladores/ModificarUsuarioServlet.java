package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Negocio.UsuarioService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

public class ModificarUsuarioServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        UsuarioService servicio = new UsuarioService();
        Optional<Usuario> usuarioOpt = servicio.obtenerUsuarioPorId(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombreCompleto(nombre);
            usuario.setEmail(email);
            //servicio.actualizarUsuario(usuario); // Asegúrate de que este método esté implementado
            response.sendRedirect("exito.jsp");
        } else {
            response.sendRedirect("error.jsp?mensaje=Usuario no encontrado");
        }
    }
}