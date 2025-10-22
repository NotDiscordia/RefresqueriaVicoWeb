package ByteBuilders.Controladores;

import ByteBuilders.Negocio.ProductoService;
import ByteBuilders.Entidad.Producto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/productos/*")
public class ProductoServlet extends HttpServlet {
    private final ProductoService productoService = new ProductoService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            List<Producto> productos = productoService.obtenerTodos();
            enviarJSON(response, gson.toJson(productos));
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            Producto producto = gson.fromJson(request.getReader(), Producto.class);
            boolean resultado = productoService.agregarProducto(producto);

            JsonObject json = new JsonObject();
            json.addProperty("mensaje", resultado ? "Producto agregado correctamente" : "Error al agregar producto");
            enviarJSON(response, gson.toJson(json));
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            Producto producto = gson.fromJson(request.getReader(), Producto.class);
            boolean resultado = productoService.actualizarProducto(producto);

            JsonObject json = new JsonObject();
            json.addProperty("mensaje", resultado ? "Producto actualizado correctamente" : "Error al actualizar producto");
            enviarJSON(response, gson.toJson(json));
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        configurarCORS(resp);
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
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
