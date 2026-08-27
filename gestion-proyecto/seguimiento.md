# Seguimiento y gestión del proyecto Mapuescuela

**Asignatura:** Integración de Plataformas  
**Proyecto:** Mapuescuela  
**Responsable:** Zaira Manriquez  
**Modalidad:** Trabajo individual

## Estructura del proyecto

Mapuescuela corresponde a un proyecto integral desarrollado progresivamente durante la asignatura Integración de Plataformas. La solución se construye y amplía a través de distintas etapas de evaluación, manteniendo continuidad entre cada entrega.

El proyecto se organiza en cuatro etapas:

- **Evaluación 1:** Primera etapa de análisis y modelamiento del proceso de negocio.
- **Evaluación 2:** Implementación ejecutable en Flowable, integración mediante servicios, External Workers, interfaces y pruebas de funcionamiento.
- **Evaluación 3:** Continuación y ampliación de la solución de acuerdo con los requerimientos de la tercera etapa.
- **Examen:** Integración, consolidación y presentación final del proyecto.

Cada evaluación continúa sobre el desarrollo realizado en la etapa anterior, manteniendo en un mismo proyecto los modelos, código fuente, servicios, pruebas, evidencias y documentación.

# Etapa 2 – Evaluación 2

## Estado y seguimiento de la Evaluación 2
La Evaluación 2 corresponde a la implementación y validación del proceso TO-BE de Mapuescuela. El desarrollo se realizó de manera individual, manteniendo la continuidad del trabajo iniciado en la Evaluación 1.

### Actividades desarrolladas

- Implementación del proceso BPMN TO-BE en Flowable.
- Configuración de tareas humanas y formularios.
- Implementación de gateways para las decisiones de pago, modalidad de entrega y tipo de despacho.
- Implementación de un evento temporizador para la cancelación del pedido.
- Desarrollo de una API REST mediante Java y Spring Boot.
- Implementación de servicios para la gestión de pedidos e inventario.
- Desarrollo de External Workers para integrar Flowable con la API REST.
- Pruebas de los servicios REST mediante Postman.
- Integración y ejecución de los External Workers con Flowable.
- Ejecución de las distintas rutas del proceso BPMN.
- Registro de evidencias de funcionamiento.
- Actualización de la documentación y repositorio GitHub.

### External Workers implementados

Se implementaron y probaron los siguientes External Workers:

- `generar-pedido`
- `actualizar-inventario`
- `registrar-pago-rechazado`
- `registrar-cancelacion`
- `pedido-disponible-retiro`

Estos componentes permiten comunicar las tareas automáticas del proceso BPMN con los servicios REST desarrollados en Spring Boot.

### Servicios REST implementados

La API permite realizar operaciones asociadas a:

- Creación y consulta de pedidos.
- Registro de pago aprobado.
- Registro de pago rechazado.
- Cancelación de pedidos.
- Registro de pedido disponible para retiro.
- Registro de retiro del pedido.
- Registro de despacho.
- Consulta de inventario.
- Descuento de unidades del inventario.

Las operaciones fueron verificadas mediante Postman, obteniendo respuestas satisfactorias durante las pruebas.

### Rutas del proceso validadas

Durante las pruebas en Flowable se validaron diferentes alternativas del proceso:

1. Pago aprobado → actualización de inventario → retiro → pedido retirado.
2. Pago aprobado → actualización de inventario → despacho por courier → pedido despachado.
3. Pago aprobado → actualización de inventario → despacho por voluntario → pedido despachado.
4. Pago rechazado → registro del rechazo → cancelación del pedido.

También se comprobó la ejecución de tareas humanas, formularios, gateways y tareas automáticas mediante External Workers.

### Evidencias

Las evidencias del desarrollo se encuentran organizadas en el repositorio:

- `evidencias-api/`: pruebas de los servicios REST realizadas mediante Postman.
- `evidencias-flowable/`: formularios, ejecución del BPMN e integración mediante External Workers.
- `bpmn/`: modelos AS-IS y TO-BE del proceso Mapuescuela.

### Estado actual

- BPMN AS-IS: completado.
- BPMN TO-BE: completado.
- Formularios Flowable: implementados y probados.
- API REST: implementada y probada.
- External Workers: implementados y probados.
- Integración Flowable–Spring Boot: operativa.
- Ruta de retiro: validada.
- Ruta de despacho por courier: validada.
- Ruta de despacho por voluntario: validada.
- Ruta de pago rechazado y cancelación: validada.
- Evidencias técnicas: incorporadas al repositorio.
- Documentación: actualizada.

La Evaluación 2 queda funcionalmente implementada y documentada, manteniendo la estructura del proyecto preparada para continuar con las siguientes etapas de la asignatura.


# Etapa 3 – Evaluación 3

## Estado y seguimiento de la Evaluación 3

La Evaluación 3 corresponde a la ampliación y consolidación de la solución Mapuescuela. El trabajo se realizó individualmente sobre la rama `main`, conservando las versiones anteriores mediante los tags `entrega-1` y `entrega-2`.

### Actividades desarrolladas

* Corrección de la propagación de la variable `idPedido` entre Flowable y la API REST.
* Corrección de la variable `nombreCliente` utilizada por el proceso.
* Eliminación del identificador fijo utilizado anteriormente por los External Workers.
* Integración de las rutas de retiro y despacho con la API REST.
* Incorporación de External Workers para registrar pedidos retirados y despachados.
* Actualización y publicación del modelo BPMN en Flowable.
* Desarrollo de una interfaz web independiente mediante HTML, CSS y JavaScript.
* Conexión de la interfaz web con los servicios REST de Spring Boot.
* Pruebas completas de las rutas de retiro, despacho por courier, despacho por voluntario y pago rechazado.
* Registro de nuevas evidencias técnicas.
* Exportación actualizada de la aplicación Flowable en `Mapuescuela.zip`.

### External Workers incorporados

Durante esta etapa se agregaron:

* `registrar-pedido-retirado`
* `registrar-pedido-despachado`

En total, la solución cuenta con siete External Workers conectados con el proceso BPMN y los servicios REST.

### Mejoras del proceso BPMN

La ruta de retiro fue ampliada para registrar en la API cuando el pedido se encuentra disponible y cuando finalmente es retirado.

Las alternativas de despacho por voluntario y courier convergen en la tarea automática `Registrar pedido despachado`, la cual actualiza el estado del pedido mediante la API antes de finalizar el proceso.

### Interfaz web

Se implementó una interfaz web independiente dentro de Spring Boot, disponible localmente en:

`http://localhost:8080/`

La interfaz permite:

* Registrar un pedido.
* Consultar un pedido mediante su identificador.
* Aprobar o rechazar el pago.
* Marcar el pedido como disponible para retiro.
* Registrar el retiro.
* Registrar el despacho.
* Cancelar el pedido.
* Visualizar la respuesta y el estado actualizado.

La conexión con la API fue validada registrando un pedido desde la interfaz y actualizando su estado a `PAGO_APROBADO`.

### Rutas validadas

1. Pago aprobado → retiro → estado `RETIRADO`.
2. Pago aprobado → despacho por voluntario → estado `DESPACHADO`.
3. Pago aprobado → despacho por courier → estado `DESPACHADO`.
4. Pago rechazado → cancelación → estado `CANCELADO`.

En cada prueba se verificó que el mismo `idPedido` se propagara correctamente entre Flowable, los External Workers y la API REST.

### Evidencias

Las capturas correspondientes a esta etapa se encuentran en:

* `evidencias-eva3/`

Esta carpeta incluye evidencias de las rutas ejecutadas en Flowable, los resultados mostrados en la terminal y el funcionamiento de la interfaz web.

### Estado actual

* Propagación de `idPedido`: corregida y validada.
* Integración de retiro: operativa.
* Integración de despacho: operativa.
* Ruta de pago rechazado y cancelación: operativa.
* BPMN actualizado: publicado y exportado.
* Interfaz web independiente: implementada y conectada.
* Pruebas de compilación: finalizadas con `BUILD SUCCESS`.
* Evidencias EVA3: incorporadas.
* Repositorio GitHub: actualizado.
* Exportación `Mapuescuela.zip`: actualizada.
La solución queda funcionalmente ampliada y preparada para la revisión final de la Evaluación 3.


