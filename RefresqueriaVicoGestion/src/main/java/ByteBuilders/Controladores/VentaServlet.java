package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Venta;
import ByteBuilders.Negocio.VentaService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

@WebServlet("/api/ventas")
public class VentaServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Configurar CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");

        try {
            BufferedReader reader = req.getReader();
            Venta venta = gson.fromJson(reader, Venta.class); // <- Asegúrate de que tu clase `Venta` tenga setters

            ventaService.registrarVenta(venta); // <- Guarda en base de datos
            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObject json = new JsonObject();
            json.addProperty("mensaje", "Venta registrada correctamente");
            resp.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Soporte para preflight (opcional)
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
