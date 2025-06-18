package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.CortesCaja;
import ByteBuilders.Entidad.Producto;
import ByteBuilders.Entidad.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VentaDAO {
    private static EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("RefresqueriaVicoPU");
            } catch (Exception e) {
                throw new RuntimeException("Error al inicializar EntityManagerFactory: " + e.getMessage(), e);
            }
        }
        return emf.createEntityManager();
    }

    public void guardarVenta(Venta venta) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(); // Esto muestra el error real en consola
            throw e; // opcional: vuelve a lanzar para que el contenedor también lo vea
        } finally {
            em.close();
        }
    }


    public List<Venta> listarTodas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Venta buscarPorId(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public void eliminar(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Venta venta = em.find(Venta.class, id);
            if (venta != null) em.remove(venta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void actualizar(Venta venta) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(venta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public CortesCaja guardarCorteCaja(CortesCaja corte) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(corte);
            em.getTransaction().commit();
            return corte;
        } finally {
            em.close();
        }
    }

    public List<Venta> obtenerVentasDelDia(LocalDate fecha) {
        EntityManager em = getEntityManager();
        try {
            LocalDateTime inicio = fecha.atStartOfDay();
            LocalDateTime fin = fecha.plusDays(1).atStartOfDay();

            return em.createQuery(
                            "SELECT v FROM Venta v WHERE v.fechaHora >= :inicio AND v.fechaHora < :fin", Venta.class)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<CortesCaja> obtenerTodosCortesCaja() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM CortesCaja c ORDER BY c.fecha DESC", CortesCaja.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Producto obtenerProductoPorId(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

}
