package ByteBuilders.Negocio;

import ByteBuilders.Entidad.*;
import ByteBuilders.Persistencia.*;

public class ConfiguracionService {

    private final ConfiguracionTiendaDAO configuracionDAO = new ConfiguracionTiendaDAO();

    public ConfiguracionTienda obtenerConfiguracion() {
        return configuracionDAO.obtenerConfiguracion();
    }

    public boolean actualizarConfiguracion(ConfiguracionTienda config) {
        if (config.getNombreTienda() == null || config.getNombreTienda().isEmpty()) {
            System.out.println("⚠️ El nombre de la tienda no puede estar vacío.");
            return false;
        }
        return configuracionDAO.actualizar(config);
    }
}
