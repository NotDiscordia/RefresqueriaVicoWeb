package ByteBuilders.Entidad;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data // Genera automáticamente getters, setters, toString, etc.
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "vendidos", nullable = false)
    private Integer vendidos;
}