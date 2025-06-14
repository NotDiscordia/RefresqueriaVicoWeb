package ByteBuilders.Controladores;

import ByteBuilders.Negocio.VentaService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/api/ventas/total-dia")
public class TotalVentasServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        try {
            BigDecimal total = ventaService.calcularTotalVentasDelDia();
            response.getWriter().write("{\"total\": " + total + "}");
        } catch (Exception e) {
            response.sendError(500, "Error al calcular total de ventas: " + e.getMessage());
        }
    }
}