package ByteBuilders.Controladores;

import ByteBuilders.Negocio.VentaService;
import com.google.gson.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/api/ventas/total-ventas")
public class TotalVentasServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            BigDecimal total = ventaService.calcularTotalVentasDelDia();
            JsonObject json = new JsonObject();
            json.addProperty("total", total);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            response.sendError(500, "Error al calcular total: " + e.getMessage());
        }
    }
}