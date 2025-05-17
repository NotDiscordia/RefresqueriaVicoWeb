package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Negocio.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AltaEmpleadoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario empleado = new Usuario();
        empleado.setNombreCompleto(request.getParameter("nombre"));  // ✔️ Método correcto
        empleado.setEmail(request.getParameter("email"));
        empleado.setPassword(request.getParameter("password"));

        UsuarioService servicio = new UsuarioService();
        boolean exito = servicio.registrarUsuario(empleado);

        response.sendRedirect(exito ? "exito.jsp" : "error.jsp");
    }
}