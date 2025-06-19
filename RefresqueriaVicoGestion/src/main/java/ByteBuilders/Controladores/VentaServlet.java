package ByteBuilders.Controladores;

import ByteBuilders.DTO.VentaDTO;
import ByteBuilders.Entidad.Moneda;
import ByteBuilders.Entidad.Usuario;
import ByteBuilders.Entidad.Venta;
import ByteBuilders.Negocio.VentaService;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/api/ventas")
public class VentaServlet extends HttpServlet {
    private final VentaService ventaService = new VentaService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = request.getReader();
            VentaDTO ventaDTO = gson.fromJson(reader, VentaDTO.class);

            // Convertir VentaDTO a Venta
            Venta venta = new Venta();
            venta.setFechaHora(LocalDateTime.now());
            venta.setTotal(ventaDTO.getTotal());
            venta.setMetodoPago(ventaDTO.getMetodoPago());
            venta.setMoneda(Moneda.valueOf(ventaDTO.getMoneda()));
            venta.setMontoEntregado(ventaDTO.getMontoEntregado());
            venta.setCambio(ventaDTO.getCambio());

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"No hay usuario autenticado\"}");
                return;
            }
            Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");
            venta.setUsuario(usuarioSesion);

            // Puedes convertir productos aquí si los necesitas
            // ejemplo: venta.setDetalles(mapearProductos(ventaDTO.getProductos()));

            ventaService.registrarVenta(venta);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensaje\":\"Venta registrada correctamente\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Soporte CORS
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");

        try {
            List<Venta> ventas = ventaService.obtenerTodasVentas();
            String json = gson.toJson(ventas);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
