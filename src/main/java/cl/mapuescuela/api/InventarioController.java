package cl.mapuescuela.api;

import cl.mapuescuela.api.model.Inventario;
import cl.mapuescuela.api.repository.InventarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Path("/api/inventario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventarioController {

    private final InventarioRepository inventarioRepository;

    public InventarioController(
            InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @PostConstruct
    public void inicializarInventario() {

        crearProductoSiNoExiste(1, 10);
        crearProductoSiNoExiste(2, 5);
        crearProductoSiNoExiste(3, 8);
    }

    @GET
    @Path("/{productoId}")
    public Map<String, Object> consultarStock(
            @PathParam("productoId") int productoId) {

        int stock = inventarioRepository
                .findById(productoId)
                .map(Inventario::getStockDisponible)
                .orElse(0);

        return Map.of(
                "productoId", productoId,
                "stockDisponible", stock
        );
    }

    @PUT
    @Path("/{productoId}/descontar")
    public Map<String, Object> descontarStock(
            @PathParam("productoId") int productoId,
            Map<String, Object> datos) {

        int cantidad = convertirCantidad(
                datos.getOrDefault("cantidad", 1)
        );

        Optional<Inventario> inventarioEncontrado =
                inventarioRepository.findById(productoId);

        int stockActual = inventarioEncontrado
                .map(Inventario::getStockDisponible)
                .orElse(0);

        if (cantidad <= 0) {
            return Map.of(
                    "mensaje",
                    "La cantidad debe ser mayor a cero",
                    "productoId",
                    productoId,
                    "stockDisponible",
                    stockActual
            );
        }

        if (inventarioEncontrado.isEmpty()
                || stockActual < cantidad) {

            return Map.of(
                    "mensaje",
                    "Stock insuficiente",
                    "productoId",
                    productoId,
                    "stockDisponible",
                    stockActual
            );
        }

        Inventario inventario = inventarioEncontrado.get();
        int nuevoStock = stockActual - cantidad;

        inventario.setStockDisponible(nuevoStock);
        inventarioRepository.save(inventario);

        return Map.of(
                "mensaje",
                "Inventario actualizado correctamente",
                "productoId",
                productoId,
                "cantidadDescontada",
                cantidad,
                "stockDisponible",
                nuevoStock
        );
    }

    private void crearProductoSiNoExiste(
            int productoId,
            int stockInicial) {

        if (!inventarioRepository.existsById(productoId)) {
            inventarioRepository.save(
                    new Inventario(productoId, stockInicial)
            );
        }
    }

    private int convertirCantidad(Object valor) {
        try {
            return Integer.parseInt(String.valueOf(valor));
        } catch (NumberFormatException error) {
            return 1;
        }
    }
}