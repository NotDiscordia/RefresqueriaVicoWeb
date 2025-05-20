package ByteBuilders.Persistencia;

import ByteBuilders.Entidad.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class VentaDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("RefresqueriaVicoPU");

    // Método para guardar una venta (nombre correcto)
    public void guardarVenta(Venta venta) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Método para listar todas las ventas
    public List<Venta> listarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Método para buscar por ID (usando Long)
    public Venta buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    // Método para eliminar (usando Long)
    public void eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Venta venta = em.find(Venta.class, id);
            if (venta != null) em.remove(venta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Método para actualizar
    public void actualizar(Venta venta) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(venta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}