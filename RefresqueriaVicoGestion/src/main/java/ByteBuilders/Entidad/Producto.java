package ByteBuilders.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
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

    @Column(name = "costo", nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "vendidos", nullable = false)
    private Integer vendidos = 0; // Inicializado en 0 para nuevos productos
}
