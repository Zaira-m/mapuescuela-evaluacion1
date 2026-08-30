package cl.mapuescuela.api;

import cl.mapuescuela.api.model.Pedido;
import cl.mapuescuela.api.repository.PedidoRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @POST
    public Map<String, Object> registrarPedido(
            Map<String, Object> nuevoPedido) {

        Pedido pedido = new Pedido();

        pedido.setCliente(
                String.valueOf(
                        nuevoPedido.getOrDefault(
                                "cliente",
                                "Cliente Mapuescuela"
                        )
                )
        );

        pedido.setProducto(
                String.valueOf(
                        nuevoPedido.getOrDefault(
                                "producto",
                                "Producto Mapuescuela"
                        )
                )
        );

        pedido.setCantidad(
                convertirCantidad(
                        nuevoPedido.getOrDefault("cantidad", 1)
                )
        );

        pedido.setModalidadEntrega(
                String.valueOf(
                        nuevoPedido.getOrDefault(
                                "modalidadEntrega",
                                "RETIRO"
                        )
                )
        );

        pedido.setEstado("PENDIENTE");

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return Map.of(
                "mensaje", "Pedido registrado correctamente",
                "pedido", pedidoGuardado
        );
    }

    @GET
    @Path("/{id}")
    public Object consultarPedido(@PathParam("id") int id) {

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if (pedido.isEmpty()) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        return pedido.get();
    }

    @PUT
    @Path("/{id}/pago-aprobado")
    public Object registrarPagoAprobado(@PathParam("id") int id) {
        return actualizarEstado(
                id,
                "PAGO_APROBADO",
                "Pago aprobado registrado correctamente"
        );
    }

    @PUT
    @Path("/{id}/pago-rechazado")
    public Object registrarPagoRechazado(@PathParam("id") int id) {
        return actualizarEstado(
                id,
                "PAGO_RECHAZADO",
                "Pago rechazado registrado correctamente"
        );
    }

    @PUT
    @Path("/{id}/disponible-retiro")
    public Object registrarDisponibleRetiro(@PathParam("id") int id) {
        return actualizarEstado(
                id,
                "DISPONIBLE_RETIRO",
                "Pedido disponible para retiro"
        );
    }

    @PUT
    @Path("/{id}/retirado")
    public Object registrarPedidoRetirado(@PathParam("id") int id) {
        return actualizarEstado(
                id,
                "RETIRADO",
                "Retiro del pedido registrado correctamente"
        );
    }

    @PUT
    @Path("/{id}/despachado")
    public Object registrarPedidoDespachado(@PathParam("id") int id) {
        return actualizarEstado(
                id,
                "DESPACHADO",
                "Despacho del pedido registrado correctamente"
        );
    }

    @PUT
    @Path("/{id}/cancelar")
    public Object cancelarPedido(@PathParam("id") int id) {
        return actualizarEstado(
                id,
                "CANCELADO",
                "Pedido cancelado correctamente"
        );
    }

    private Object actualizarEstado(
            int id,
            String nuevoEstado,
            String mensaje) {

        Optional<Pedido> pedidoEncontrado =
                pedidoRepository.findById(id);

        if (pedidoEncontrado.isEmpty()) {
            return Map.of(
                    "mensaje", "Pedido no encontrado",
                    "idPedido", id
            );
        }

        Pedido pedido = pedidoEncontrado.get();
        pedido.setEstado(nuevoEstado);

        Pedido pedidoActualizado =
                pedidoRepository.save(pedido);

        return Map.of(
                "mensaje", mensaje,
                "pedido", pedidoActualizado
        );
    }

    private int convertirCantidad(Object valor) {
        try {
            return Integer.parseInt(String.valueOf(valor));
        } catch (NumberFormatException error) {
            return 1;
        }
    }
}