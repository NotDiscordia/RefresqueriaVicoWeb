package ByteBuilders.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ✔️ Usamos Long para el ID

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    private BigDecimal total;

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
