package ByteBuilders.Controladores;

import ByteBuilders.Negocio.UsuarioService;
import ByteBuilders.Entidad.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/usuarios/*")
public class UsuarioServlet extends HttpServlet {
    private final UsuarioService usuarioService = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                List<Usuario> usuarios = usuarioService.obtenerTodos();
                enviarJSON(response, gson.toJson(usuarios));
            } else {
                String idStr = pathInfo.replaceFirst("/", "");
                int id = Integer.parseInt(idStr);
                Usuario usuario = usuarioService.obtenerPorID(id);
                if (usuario == null) {
                    manejarError(response, HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado: " + id);
                    return;
                }
                enviarJSON(response, gson.toJson(usuario));
            }
        } catch (NumberFormatException e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar GET: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);
            boolean resultado = usuarioService.crearUsuario(usuario);

            JsonObject json = new JsonObject();
            json.addProperty("mensaje", resultado ? "Usuario creado correctamente" : "Error al crear usuario");
            enviarJSON(response, gson.toJson(json));
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST, "Error al crear usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);
            boolean resultado = usuarioService.actualizarUsuario(usuario);

            JsonObject json = new JsonObject();
            json.addProperty("mensaje", resultado ? "Usuario actualizado correctamente" : "Error al actualizar usuario");
            enviarJSON(response, gson.toJson(json));
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST, "Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                manejarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID de usuario requerido para eliminar");
                return;
            }
            String idStr = pathInfo.replaceFirst("/", "");
            int id = Integer.parseInt(idStr);

            boolean resultado = usuarioService.eliminarUsuario(id);
            JsonObject json = new JsonObject();
            json.addProperty("mensaje", resultado ? "Usuario eliminado correctamente" : "Error al eliminar usuario");
            enviarJSON(response, gson.toJson(json));
        } catch (NumberFormatException e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        configurarCORS(resp);
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void configurarCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void enviarJSON(HttpServletResponse response, String json) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

    private void manejarError(HttpServletResponse response, int codigo, String mensaje) throws IOException {
        response.sendError(codigo, mensaje);
        System.err.println("ERROR: " + mensaje);
    }
}
