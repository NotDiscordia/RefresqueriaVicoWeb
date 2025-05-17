package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Negocio.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/alta-empleado")
public class AltaEmpleadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Obtener datos del formulario
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String celular = request.getParameter("celular");
        String contrasena = request.getParameter("numero");

        // Crear el objeto Usuario
        Usuario empleado = new Usuario();
        empleado.setNombre(nombre);
        empleado.setApellidos(apellidos);
        empleado.setCelular(celular);
        empleado.setContrasena(contrasena);

        try {
            // Llamar al servicio para guardar el usuario
            usuarioService.guardarUsuario(empleado);

            // Redireccionar o mostrar mensaje de éxito
            response.sendRedirect("Menu empleados vico.html"); // o una página de éxito
        } catch (Exception e) {
            e.printStackTrace();

            // En caso de error, mostrar mensaje
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3>Error al registrar el empleado</h3>");
        }
    }
}
