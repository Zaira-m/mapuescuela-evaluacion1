package cl.mapuescuela.api.worker;

import org.flowable.external.client.AcquiredExternalWorkerJob;
import org.flowable.external.worker.WorkerResult;
import org.flowable.external.worker.WorkerResultBuilder;
import org.flowable.external.worker.annotation.FlowableWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class ActualizarInventarioWorker {

    private final RestClient restClient;

    public ActualizarInventarioWorker() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @FlowableWorker(topic = "actualizar-inventario")
    public WorkerResult actualizarInventario(
            AcquiredExternalWorkerJob job,
            WorkerResultBuilder resultBuilder) {

        System.out.println("External Worker actualizar-inventario ejecutándose");
        System.out.println("Job ID: " + job.getId());

        Map<String, Object> variables = job.getVariables();

        int productoId = 1;
        int cantidad = 1;

        if (variables.get("productoId") instanceof Number numeroProducto) {
            productoId = numeroProducto.intValue();
        }

        if (variables.get("cantidad") instanceof Number numeroCantidad) {
            cantidad = numeroCantidad.intValue();
        }

        Map<String, Integer> cuerpo = Map.of(
                "cantidad", cantidad
        );

        Map<?, ?> respuesta = restClient.put()
                .uri("/api/inventario/{productoId}/descontar", productoId)
                .body(cuerpo)
                .retrieve()
                .body(Map.class);

        System.out.println("Inventario actualizado: " + respuesta);

        return resultBuilder.success()
                .variable("inventarioActualizado", true);
    }
}