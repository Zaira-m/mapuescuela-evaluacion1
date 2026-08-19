package cl.mapuescuela.api.worker;

import org.flowable.external.client.AcquiredExternalWorkerJob;
import org.flowable.external.worker.WorkerResult;
import org.flowable.external.worker.WorkerResultBuilder;
import org.flowable.external.worker.annotation.FlowableWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class GenerarPedidoWorker {

    private final RestClient restClient;

    public GenerarPedidoWorker() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @FlowableWorker(topic = "generar-pedido")
    public WorkerResult procesarPedido(
            AcquiredExternalWorkerJob job,
            WorkerResultBuilder resultBuilder) {

        System.out.println("External Worker generar-pedido ejecutándose");
        System.out.println("Job ID: " + job.getId());

        Map<String, Object> variables = job.getVariables();

        Map<String, Object> nuevoPedido = new HashMap<>();

        nuevoPedido.put(
                "cliente",
                variables.getOrDefault("cliente", "Cliente Mapuescuela")
        );

        nuevoPedido.put(
                "producto",
                variables.getOrDefault("producto", "Producto Mapuescuela")
        );

        nuevoPedido.put(
                "cantidad",
                variables.getOrDefault("cantidad", 1)
        );

        nuevoPedido.put(
                "modalidadEntrega",
                variables.getOrDefault("modalidadEntrega", "RETIRO")
        );

        Map<?, ?> respuesta = restClient.post()
                .uri("/api/pedidos")
                .body(nuevoPedido)
                .retrieve()
                .body(Map.class);

        System.out.println("Pedido generado correctamente: " + respuesta);

        return resultBuilder.success()
                .variable("pedidoGenerado", true);
    }
}