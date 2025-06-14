package ByteBuilders.Controladores;

import ByteBuilders.Entidad.CortesCaja;
import ByteBuilders.Negocio.VentaService;
import ByteBuilders.Entidad.Moneda;
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
            }
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al obtener cortes de caja: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            BigDecimal totalEfectivo = new BigDecimal(request.getParameter("totalEfectivo"));
            int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
            Moneda moneda = Moneda.valueOf(request.getParameter("moneda").toUpperCase());

            var corte = ventaService.realizarCorteCaja(totalEfectivo, usuarioId, moneda);
            enviarJSON(response, gson.toJson(corte));
        } catch (Exception e) {
            manejarError(response, 500, "Error al realizar corte: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        try {
            BigDecimal efectivoFisico = new BigDecimal(request.getParameter("efectivoFisico"));
            BigDecimal efectivoTeorico = new BigDecimal(request.getParameter("efectivoTeorico"));

            String resultado = ventaService.compararCorte(efectivoFisico, efectivoTeorico);
            enviarJSON(response, "{\"resultado\": \"" + resultado + "\"}");
        } catch (Exception e) {
            manejarError(response, 500, "Error al comparar corte: " + e.getMessage());
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