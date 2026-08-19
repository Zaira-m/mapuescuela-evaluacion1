package cl.mapuescuela.api;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    // Pedidos guardados en memoria mientras la aplicación esté encendida
    private final Map<Integer, Map<String, Object>> pedidos =
            new ConcurrentHashMap<>();

    // Generador automático de ID
    private final AtomicInteger contadorId = new AtomicInteger(123);

    @PostMapping
    public Map<String, Object> registrarPedido(
            @RequestBody Map<String, Object> nuevoPedido) {

        int idPedido = contadorId.getAndIncrement();

        nuevoPedido.put("idPedido", idPedido);
        nuevoPedido.putIfAbsent("estado", "PENDIENTE");

        pedidos.put(idPedido, nuevoPedido);

        return Map.of(
                "mensaje", "Pedido registrado correctamente",
                "pedido", nuevoPedido
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> consultarPedido(
            @PathVariable int id) {

        Map<String, Object> pedido = pedidos.get(id);

        if (pedido == null) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        return pedido;
    }

    @PutMapping("/{id}/cancelar")
    public Map<String, Object> cancelarPedido(
            @PathVariable int id) {

        Map<String, Object> pedido = pedidos.get(id);

        if (pedido == null) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        pedido.put("estado", "CANCELADO");

        return Map.of(
                "mensaje", "Pedido cancelado correctamente",
                "pedido", pedido
        );
    }

    @PutMapping("/{id}/pago-rechazado")
    public Map<String, Object> registrarPagoRechazado(
            @PathVariable int id) {

        Map<String, Object> pedido = pedidos.get(id);

        if (pedido == null) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        pedido.put("estado", "PAGO_RECHAZADO");

        return Map.of(
                "mensaje", "Pago rechazado registrado correctamente",
                "pedido", pedido
        );
    }

    @PutMapping("/{id}/disponible-retiro")
    public Map<String, Object> registrarDisponibleRetiro(
            @PathVariable int id) {

        Map<String, Object> pedido = pedidos.get(id);

        if (pedido == null) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        pedido.put("estado", "DISPONIBLE_RETIRO");

        return Map.of(
                "mensaje", "Pedido disponible para retiro",
                "pedido", pedido
        );
    }
}