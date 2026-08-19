package cl.mapuescuela.api.worker;

import org.flowable.external.client.AcquiredExternalWorkerJob;
import org.flowable.external.worker.WorkerResult;
import org.flowable.external.worker.WorkerResultBuilder;
import org.flowable.external.worker.annotation.FlowableWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class PedidoDisponibleRetiroWorker {

    private final RestClient restClient;

    public PedidoDisponibleRetiroWorker() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @FlowableWorker(topic = "pedido-disponible-retiro")
    public WorkerResult registrarDisponibleRetiro(
            AcquiredExternalWorkerJob job,
            WorkerResultBuilder resultBuilder) {

        System.out.println("External Worker pedido-disponible-retiro ejecutándose");
        System.out.println("Job ID: " + job.getId());

        Map<String, Object> variables = job.getVariables();

        int idPedido = 123;

        if (variables.get("idPedido") instanceof Number numeroPedido) {
            idPedido = numeroPedido.intValue();
        }

        Map<?, ?> respuesta = restClient.put()
                .uri("/api/pedidos/{id}/disponible-retiro", idPedido)
                .retrieve()
                .body(Map.class);

        System.out.println("Pedido disponible para retiro: " + respuesta);

        return resultBuilder.success()
                .variable("pedidoDisponibleRetiro", true);
    }
}