package ByteBuilders.Controladores;

import ByteBuilders.Negocio.UsuarioService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EliminarUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        UsuarioService servicio = new UsuarioService();
        boolean exito = servicio.eliminarUsuario(id);
        response.sendRedirect(exito ? "exito.jsp" : "error.jsp");
    }
}