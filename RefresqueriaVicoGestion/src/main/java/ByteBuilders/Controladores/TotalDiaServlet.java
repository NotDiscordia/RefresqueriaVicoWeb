package ByteBuilders.Controladores;

import ByteBuilders.Negocio.VentaService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/api/ventas/total-dia")
public class TotalDiaServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");

        try {
            BigDecimal total = ventaService.calcularTotalVentasDelDia(); // Asegúrate que este método exista
            JsonObject json = new JsonObject();
            json.addProperty("total", total);
            resp.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener total del día");
        }
    }
}

