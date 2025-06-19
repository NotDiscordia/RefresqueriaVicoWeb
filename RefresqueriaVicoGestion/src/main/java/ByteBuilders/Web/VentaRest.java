package ByteBuilders.Web;

import ByteBuilders.DTO.VentaDTO;
import ByteBuilders.Entidad.Venta;
import ByteBuilders.Entidad.DetalleVenta;
import ByteBuilders.Entidad.Producto;
import ByteBuilders.Persistencia.VentaDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/ventas")
public class VentaRest {

    private VentaDAO ventaDAO = new VentaDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarVenta(VentaDTO ventaDTO) {
        try {
            Venta venta = new Venta();
            venta.setFechaHora(LocalDateTime.now());
            venta.setTotal(ventaDTO.getTotal());

            // Crear lista de detalles
            List<DetalleVenta> detalles = new ArrayList<>();

            for (VentaDTO.Item item : ventaDTO.getProductos()) {
                Producto producto = ventaDAO.obtenerProductoPorId(item.getId()); // crea este método si no existe

                DetalleVenta detalle = new DetalleVenta();
                detalle.setProducto(producto);
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioUnitario(producto.getPrecio()); // usa el precio real

                detalle.setVenta(venta);
                detalles.add(detalle);
            }


            venta.setDetalleVentas(detalles);

            ventaDAO.guardarVenta(venta);

            return Response.status(Response.Status.CREATED).entity(venta).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al registrar la venta: " + e.getMessage()).build();
        }
    }
}
