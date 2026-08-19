package cl.mapuescuela.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

    private final Map<Integer, Map<String, Object>> pedidos =
            new ConcurrentHashMap<>();

    private final AtomicInteger contadorId = new AtomicInteger(123);

    @POST
    public Map<String, Object> registrarPedido(
            Map<String, Object> nuevoPedido) {

        int idPedido = contadorId.getAndIncrement();

        nuevoPedido.put("idPedido", idPedido);
        nuevoPedido.putIfAbsent("estado", "PENDIENTE");

        pedidos.put(idPedido, nuevoPedido);

        return Map.of(
                "mensaje", "Pedido registrado correctamente",
                "pedido", nuevoPedido
        );
    }

    @GET
    @Path("/{id}")
    public Map<String, Object> consultarPedido(
            @PathParam("id") int id) {

        Map<String, Object> pedido = pedidos.get(id);

        if (pedido == null) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        return pedido;
    }

    @PUT
    @Path("/{id}/cancelar")
    public Map<String, Object> cancelarPedido(
            @PathParam("id") int id) {

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

    @PUT
    @Path("/{id}/pago-rechazado")
    public Map<String, Object> registrarPagoRechazado(
            @PathParam("id") int id) {

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

    @PUT
    @Path("/{id}/disponible-retiro")
    public Map<String, Object> registrarDisponibleRetiro(
            @PathParam("id") int id) {

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