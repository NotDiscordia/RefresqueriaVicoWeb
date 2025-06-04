package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Producto;
import ByteBuilders.Negocio.ProductoService;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/productos")
public class ProductoServlet extends HttpServlet {
    private final ProductoService service = new ProductoService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Producto> productos = service.obtenerProductos();
            String json = gson.toJson(productos);
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Error al obtener productos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Producto nuevo = gson.fromJson(request.getReader(), Producto.class);
            boolean exito = service.agregarProducto(nuevo);
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error al agregar producto");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Producto actualizado = gson.fromJson(request.getReader(), Producto.class);
            boolean exito = service.actualizarProducto(actualizado);
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error al actualizar producto");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean exito = service.eliminarProducto(id);
            response.getWriter().write("{\"exito\": " + exito + "}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error al eliminar producto");
        }
    }

    // Opcional para CORS preflight (si usas JS moderno)
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
