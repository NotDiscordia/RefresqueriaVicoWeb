package ByteBuilders.Controladores;

import ByteBuilders.Entidad.CortesCaja;
import ByteBuilders.Entidad.Moneda;
import ByteBuilders.Negocio.VentaService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/api/cortes-caja/*")
public class CorteCajaServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                var cortes = ventaService.obtenerCortesCaja();
                enviarJSON(response, gson.toJson(cortes));
            } else if (pathInfo.equals("/historial")) {
                List<CortesCaja> cortes = ventaService.obtenerCortesHistoricos();
                enviarJSON(response, gson.toJson(cortes));
            } else if (pathInfo.equals("/ventas/total-dia")) {
                BigDecimal total = ventaService.calcularTotalVentasDelDia(); // Asegúrate que este método esté implementado
                JsonObject json = new JsonObject();
                json.addProperty("total", total);
                enviarJSON(response, gson.toJson(json));
            } else {
                manejarError(response, HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
            }
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al obtener cortes de caja: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try {
            BigDecimal totalEfectivo = new BigDecimal(request.getParameter("totalEfectivo"));
            int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
            String moneda = request.getParameter("moneda");

            CortesCaja corte = ventaService.realizarCorteCaja(
                    totalEfectivo,
                    usuarioId,
                    Moneda.valueOf(moneda)
            );

            response.getWriter().write(gson.toJson(corte));
        } catch (Exception e) {
            response.sendError(400, "Datos inválidos");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            configurarCORS(response);
            BigDecimal fisico = new BigDecimal(request.getParameter("efectivoFisico"));
            BigDecimal teorico = new BigDecimal(request.getParameter("efectivoTeorico"));

            String resultado = ventaService.compararCorte(fisico, teorico);

            JsonObject json = new JsonObject();
            json.addProperty("resultado", resultado);
            response.getWriter().write(gson.toJson(json));
        } catch (Exception e) {
            response.sendError(400, "Error en comparación");
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
