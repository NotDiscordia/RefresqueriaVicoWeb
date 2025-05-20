package ByteBuilders.Controladores;

import ByteBuilders.Negocio.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class EliminarUsuarioServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        UsuarioService servicio = new UsuarioService();
        boolean exito = servicio.eliminarUsuario(id);
        response.sendRedirect(exito ? "exito.jsp" : "error.jsp");
    }
}