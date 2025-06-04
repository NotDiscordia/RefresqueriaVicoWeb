package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Negocio.UsuarioService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioService usuarioService = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCORS(response);

        try (BufferedReader reader = request.getReader()) {
            Usuario usuarioLogin = gson.fromJson(reader, Usuario.class);

            int id = usuarioLogin.getId();
            String numeroIngresado = usuarioLogin.getNumero();

            Usuario usuarioBD = usuarioService.obtenerUsuarioPorId(id);

            if (usuarioBD != null && usuarioBD.getNumero().equals(numeroIngresado)) {
                // Login exitoso
                String json = gson.toJson(usuarioBD);
                enviarJSON(response, json);
            } else {
                // Login fallido
                manejarError(response, HttpServletResponse.SC_UNAUTHORIZED, "ID o número incorrecto");
            }

        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar login: " + e.getMessage());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configurarCORS(resp);
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
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
        System.err.println("ERROR LOGIN: " + mensaje);
    }
}
