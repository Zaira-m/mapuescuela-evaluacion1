package cl.mapuescuela.api.worker;

import org.flowable.external.client.AcquiredExternalWorkerJob;
import org.flowable.external.worker.WorkerResult;
import org.flowable.external.worker.WorkerResultBuilder;
import org.flowable.external.worker.annotation.FlowableWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class RegistrarCancelacionWorker {

    private final RestClient restClient;

    public RegistrarCancelacionWorker() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @FlowableWorker(topic = "registrar-cancelacion")
    public WorkerResult registrarCancelacion(
            AcquiredExternalWorkerJob job,
            WorkerResultBuilder resultBuilder) {

        System.out.println("External Worker registrar-cancelacion ejecutándose");
        System.out.println("Job ID: " + job.getId());

        Map<String, Object> variables = job.getVariables();

        int idPedido = 123;

        if (variables.get("idPedido") instanceof Number numeroPedido) {
            idPedido = numeroPedido.intValue();
        }

        Map<?, ?> respuesta = restClient.put()
                .uri("/api/pedidos/{id}/cancelar", idPedido)
                .retrieve()
                .body(Map.class);

        System.out.println("Pedido cancelado: " + respuesta);

        return resultBuilder.success()
                .variable("pedidoCancelado", true);
    }
}