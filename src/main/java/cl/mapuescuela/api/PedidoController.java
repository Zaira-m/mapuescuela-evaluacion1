package cl.mapuescuela.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @GetMapping("/123")
    public Map<String, Object> consultarPedido() {

        Map<String, Object> pedido = new HashMap<>();

        pedido.put("idPedido", 123);
        pedido.put("cliente", "Cliente Mapuescuela");
        pedido.put("estado", "Pago aprobado");
        pedido.put("modalidadEntrega", "Retiro");

        return pedido;
    }

    @PostMapping
    public Map<String, Object> registrarPedido(
            @RequestBody Map<String, Object> nuevoPedido) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("mensaje", "Pedido registrado correctamente");
        respuesta.put("pedido", nuevoPedido);

        return respuesta;
    }
}