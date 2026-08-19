package cl.mapuescuela.api;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final Map<Integer, Integer> stockPorProducto =
            new ConcurrentHashMap<>();

    public InventarioController() {
        // Stock inicial de ejemplo para pruebas
        stockPorProducto.put(1, 10);
        stockPorProducto.put(2, 5);
        stockPorProducto.put(3, 8);
    }

    @GetMapping("/{productoId}")
    public Map<String, Object> consultarStock(
            @PathVariable int productoId) {

        int stock = stockPorProducto.getOrDefault(productoId, 0);

        return Map.of(
                "productoId", productoId,
                "stockDisponible", stock
        );
    }

    @PutMapping("/{productoId}/descontar")
    public Map<String, Object> descontarStock(
            @PathVariable int productoId,
            @RequestBody Map<String, Integer> datos) {

        int cantidad = datos.getOrDefault("cantidad", 1);
        int stockActual = stockPorProducto.getOrDefault(productoId, 0);

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
        stockPorProducto.put(productoId, nuevoStock);

        return Map.of(
                "mensaje", "Inventario actualizado correctamente",
                "productoId", productoId,
                "cantidadDescontada", cantidad,
                "stockDisponible", nuevoStock
        );
    }
}