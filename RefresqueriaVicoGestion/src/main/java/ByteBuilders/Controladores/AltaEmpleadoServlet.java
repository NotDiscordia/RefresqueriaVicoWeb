package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Negocio.UsuarioService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AltaEmpleadoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Usuario empleado = new Usuario();
        empleado.setNombreCompleto(request.getParameter("nombre"));  // ✔️ Método correcto
        empleado.setEmail(request.getParameter("email"));
        empleado.setPassword(request.getParameter("password"));

        UsuarioService servicio = new UsuarioService();
        boolean exito = servicio.registrarUsuario(empleado);

        response.sendRedirect(exito ? "exito.jsp" : "error.jsp");
    }
}