package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Negocio.UsuarioService;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/usuarios")
public class AltaEmpleadoServlet extends HttpServlet {
    private final UsuarioService usuarioService = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCORS(response);
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuarios();
            enviarJSON(response, gson.toJson(usuarios));
        } catch (Exception e) {
            manejarError(response, 500, "Error al obtener usuarios: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCORS(response);
        try (BufferedReader reader = request.getReader()) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);

            // Si el email es una cadena vacía, lo convertimos en null
            if (usuario.getEmail() != null && usuario.getEmail().trim().isEmpty()) {
                usuario.setEmail(null);
            }

            // Validaciones básicas
            if (usuario.getNombre() == null || usuario.getApellidos() == null ||
                    usuario.getCelular() == null || usuario.getContrasena() == null ||
                    usuario.getRol() == null) {
                manejarError(response, 400, "Faltan campos obligatorios");
                return;
            }

            // Validación: No permitir duplicado por celular
            if (usuarioService.obtenerUsuarioPorCelular(usuario.getCelular()) != null) {
                manejarError(response, 400, "Ya existe un usuario con ese celular");
                return;
            }

            boolean exito = usuarioService.agregarUsuario(usuario);
            enviarJSON(response, "{\"exito\": " + exito + "}");

        } catch (Exception e) {
            manejarError(response, 500, "Error al registrar empleado: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCORS(response);
        try (BufferedReader reader = request.getReader()) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);

            // Si el campo email está vacío o solo espacios, lo convertimos a null
            if (usuario.getEmail() != null && usuario.getEmail().trim().isEmpty()) {
                usuario.setEmail(null);
            }

            boolean exito = usuarioService.actualizarUsuario(usuario);
            enviarJSON(response, "{\"exito\": " + exito + "}");
        } catch (Exception e) {
            manejarError(response, 500, "Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCORS(response);
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean exito = usuarioService.eliminarUsuario(id);
            enviarJSON(response, "{\"exito\": " + exito + "}");
        } catch (Exception e) {
            manejarError(response, 500, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarCORS(resp);
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // Métodos auxiliares
    private void configurarCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void enviarJSON(HttpServletResponse response, String json) throws IOException {
        response.getWriter().write(json);
    }

    private void manejarError(HttpServletResponse response, int codigo, String mensaje) throws IOException {
        response.sendError(codigo, mensaje);
        System.err.println("ERROR: " + mensaje);
    }
}
