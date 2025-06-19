package ByteBuilders.Controladores;

import ByteBuilders.Entidad.CortesCaja;
import ByteBuilders.Entidad.Moneda;
import ByteBuilders.Negocio.VentaService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/api/cortes_caja/*")
public class CorteCajaServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Solo devolver el total de ventas del día
                BigDecimal total = ventaService.calcularTotalVentasDelDia();
                JsonObject json = new JsonObject();
                json.addProperty("total", total);
                json.addProperty("mensaje", "Corte guardado correctamente");
                enviarJSON(response, gson.toJson(json));
            } else {
                manejarError(response, HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada: " + pathInfo);
            }
        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al procesar GET: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        configurarCORS(resp);

        try {
            // Leer el cuerpo JSON
            StringBuilder sb = new StringBuilder();
            String line;
            try (var reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            // Parsear JSON
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);

            BigDecimal totalEfectivo = jsonObject.get("totalEfectivo").getAsBigDecimal();
            int usuarioId = jsonObject.get("usuarioId").getAsInt();
            Moneda moneda = Moneda.valueOf(jsonObject.get("moneda").getAsString().toUpperCase());

            // Ejecutar lógica de corte
            CortesCaja corte = ventaService.realizarCorteCaja(totalEfectivo, usuarioId, moneda);

            // Evitar serialización de LocalDate directamente
            JsonObject respuesta = new JsonObject();
            respuesta.addProperty("mensaje", "Corte guardado correctamente");
            respuesta.addProperty("totalEfectivo", corte.getTotalEfectivo().toString());
            respuesta.addProperty("estado", corte.getEstado().toString());
            respuesta.addProperty("moneda", corte.getMoneda().toString());

            // Puedes agregar la fecha como string si la necesitas
            respuesta.addProperty("fecha", corte.getFecha().toString());

            enviarJSON(resp, gson.toJson(respuesta));
        } catch (Exception e) {
            manejarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al guardar corte: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        configurarCORS(response);

        try {
            BigDecimal fisico = new BigDecimal(request.getParameter("efectivoFisico"));
            BigDecimal teorico = new BigDecimal(request.getParameter("efectivoTeorico"));

            String resultado = ventaService.compararCorte(fisico, teorico).name();

            JsonObject json = new JsonObject();
            json.addProperty("resultado", resultado);
            enviarJSON(response, gson.toJson(json));

        } catch (Exception e) {
            manejarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Error al comparar corte: " + e.getMessage());
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
