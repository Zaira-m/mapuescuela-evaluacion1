package cl.mapuescuela.api;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Path("/api/inventario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventarioController {

    private final Map<Integer, Integer> stockPorProducto =
            new ConcurrentHashMap<>();

    public InventarioController() {
        stockPorProducto.put(1, 10);
        stockPorProducto.put(2, 5);
        stockPorProducto.put(3, 8);
    }

    @GET
    @Path("/{productoId}")
    public Map<String, Object> consultarStock(
            @PathParam("productoId") int productoId) {

        int stock = stockPorProducto.getOrDefault(
                productoId,
                0
        );

        return Map.of(
                "productoId", productoId,
                "stockDisponible", stock
        );
    }

    @PUT
    @Path("/{productoId}/descontar")
    public Map<String, Object> descontarStock(
            @PathParam("productoId") int productoId,
            Map<String, Integer> datos) {

        int cantidad = datos.getOrDefault(
                "cantidad",
                1
        );

        int stockActual = stockPorProducto.getOrDefault(
                productoId,
                0
        );

        if (cantidad <= 0) {
            return Map.of(
                    "mensaje", "La cantidad debe ser mayor a cero",
                    "productoId", productoId,
                    "stockDisponible", stockActual
            );
        }

        if (stockActual < cantidad) {
            return Map.of(
                    "mensaje", "Stock insuficiente",
                    "productoId", productoId,
                    "stockDisponible", stockActual
            );
        }

        int nuevoStock = stockActual - cantidad;

        stockPorProducto.put(
                productoId,
                nuevoStock
        );

        return Map.of(
                "mensaje", "Inventario actualizado correctamente",
                "productoId", productoId,
                "cantidadDescontada", cantidad,
                "stockDisponible", nuevoStock
        );
    }
}