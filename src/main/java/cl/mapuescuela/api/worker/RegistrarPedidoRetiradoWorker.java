package cl.mapuescuela.api.worker;

import org.flowable.external.client.AcquiredExternalWorkerJob;
import org.flowable.external.worker.WorkerResult;
import org.flowable.external.worker.WorkerResultBuilder;
import org.flowable.external.worker.annotation.FlowableWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class RegistrarPedidoRetiradoWorker {

    private final RestClient restClient;

    public RegistrarPedidoRetiradoWorker() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @FlowableWorker(topic = "registrar-pedido-retirado")
    public WorkerResult registrarPedidoRetirado(
            AcquiredExternalWorkerJob job,
            WorkerResultBuilder resultBuilder) {

        System.out.println("External Worker registrar-pedido-retirado ejecutándose");
        System.out.println("Job ID: " + job.getId());

        Map<String, Object> variables = job.getVariables();

        int idPedido = Integer.parseInt(
                String.valueOf(variables.get("idPedido"))
        );

        Map<?, ?> respuesta = restClient.put()
                .uri("/api/pedidos/{id}/retirado", idPedido)
                .retrieve()
                .body(Map.class);

        System.out.println("Pedido retirado registrado: " + respuesta);

        return resultBuilder.success()
                .variable("pedidoRetiradoRegistrado", true);
    }
}