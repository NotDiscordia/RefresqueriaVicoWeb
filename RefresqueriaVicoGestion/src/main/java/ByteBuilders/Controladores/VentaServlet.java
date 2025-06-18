package ByteBuilders.Controladores;

import ByteBuilders.Entidad.Venta;
import ByteBuilders.Negocio.VentaService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.time.LocalDateTime;

@WebServlet("/api/ventas")
public class VentaServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Leer JSON de la petición y convertir a objeto Venta
        BufferedReader reader = request.getReader();
        Gson gson = new Gson(); // Aquí o usa el builder si tienes más configuraciones
        Venta venta = gson.fromJson(reader, Venta.class);

        // Asignar la fecha/hora actual aquí
        venta.setFechaHora(LocalDateTime.now());

        // Luego continúas con el guardado
        try {
            ventaService.registrarVenta(venta);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Venta registrada correctamente");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al registrar la venta: " + e.getMessage());
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
