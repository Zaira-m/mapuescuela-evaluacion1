package cl.mapuescuela.api.worker;

import org.flowable.external.client.AcquiredExternalWorkerJob;
import org.flowable.external.worker.WorkerResult;
import org.flowable.external.worker.WorkerResultBuilder;
import org.flowable.external.worker.annotation.FlowableWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class RegistrarPagoRechazadoWorker {

    private final RestClient restClient;

    public RegistrarPagoRechazadoWorker() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @FlowableWorker(topic = "registrar-pago-rechazado")
    public WorkerResult registrarPagoRechazado(
            AcquiredExternalWorkerJob job,
            WorkerResultBuilder resultBuilder) {

        System.out.println("External Worker registrar-pago-rechazado ejecutándose");
        System.out.println("Job ID: " + job.getId());

        Map<String, Object> variables = job.getVariables();

        int idPedido = Integer.parseInt(
        String.valueOf(variables.get("idPedido"))
);

        Map<?, ?> respuesta = restClient.put()
                .uri("/api/pedidos/{id}/pago-rechazado", idPedido)
                .retrieve()
                .body(Map.class);

        System.out.println("Pago rechazado registrado: " + respuesta);

        return resultBuilder.success()
                .variable("pagoRechazadoRegistrado", true);
    }
}