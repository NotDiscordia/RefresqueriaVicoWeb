package ByteBuilders.Entidad;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String celular;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String rol;

    private String email; // Este puede ser opcional (nullable por defecto)
}