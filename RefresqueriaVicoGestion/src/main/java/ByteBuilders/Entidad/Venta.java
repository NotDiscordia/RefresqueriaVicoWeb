package ByteBuilders.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "moneda", nullable = false)
    private Moneda moneda;

    @Column(name = "monto_entregado")
    private BigDecimal montoEntregado;

    @Column(name = "cambio")
    private BigDecimal cambio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalleVentas;

    public void setDetalleVentas(List<DetalleVenta> detalles) {
        this.detalleVentas = detalles;
        if (detalles != null) {
            for (DetalleVenta detalle : detalles) {
                detalle.setVenta(this);
            }
        }
    }
}
