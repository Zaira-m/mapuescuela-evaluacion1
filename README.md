# Mapuescuela – Integración de Plataformas

Proyecto desarrollado para la asignatura **Integración de Plataformas**, orientado al análisis, mejora, automatización e integración del proceso de gestión de ventas de Mapuescuela.

## Objetivo

Modelar el proceso actual de gestión de ventas de Mapuescuela mediante BPMN, diseñar una propuesta mejorada TO-BE e implementar un prototipo ejecutable utilizando **Flowable**, formularios de usuario, External Workers y servicios web REST desarrollados con Java y Spring Boot.

## Modelos BPMN

En la carpeta `bpmn` se encuentran los modelos desarrollados para el proyecto:

- `mapuescuela-as-is.bpmn`: representación del proceso actual de gestión de ventas.
- `mapuescuela-to-be.bpmn`: propuesta mejorada del proceso, incorporando automatización e integración con servicios externos.

### Modelo BPMN AS-IS

![Modelo BPMN AS-IS](bpmn/mapuescuela-as-is.png)

### Modelo BPMN TO-BE

![Modelo BPMN TO-BE](bpmn/mapuescuela-to-be.png)

## Proceso TO-BE en Flowable

El proceso de gestión de ventas fue implementado en **Flowable Design** y publicado para su ejecución en **Flowable Work Sandbox**.

El modelo considera:

- Selección de productos.
- Ingreso de datos del cliente.
- Selección de modalidad de entrega.
- Generación del pedido.
- Información de datos bancarios.
- Transferencia y comprobante de pago.
- Revisión y aprobación/rechazo del pago.
- Actualización de inventario.
- Preparación del pedido.
- Retiro o despacho.
- Cancelación de pedidos.
- Evento temporizador para cancelación por tiempo.
- Formularios asociados a tareas humanas.
- Automatización mediante External Worker Tasks.

## Formularios

Se implementaron formularios en Flowable para permitir la interacción del usuario con distintas etapas del proceso.

Entre ellos:

- Selección de productos.
- Datos de compra.
- Selección de modalidad de entrega.
- Revisión del comprobante de pago.
- Selección del tipo de despacho.
- Registro de retiro del pedido.
- Registro de datos de envío.

Los formularios permiten capturar variables utilizadas durante la ejecución del proceso BPMN.

## External Workers

El modelo TO-BE diferencia las tareas humanas de las tareas automáticas.

Se configuraron External Worker Tasks para los siguientes procesos:

- `generar-pedido`
- `actualizar-inventario`
- `registrar-pago-rechazado`
- `registrar-cancelacion`
- `pedido-disponible-retiro`

Los External Workers fueron desarrollados en Java para integrar el proceso BPMN con los servicios de la aplicación.

## API REST

Se desarrolló una API REST utilizando **Java 21 y Spring Boot** para gestionar pedidos e inventario.

### Pedidos

#### Registrar pedido

`POST /api/pedidos`

Permite crear un nuevo pedido con estado inicial `PENDIENTE`.

#### Consultar pedido

`GET /api/pedidos/{id}`

Permite consultar los datos y estado actual de un pedido.

#### Registrar pago aprobado

`PUT /api/pedidos/{id}/pago-aprobado`

Actualiza el estado del pedido a `PAGO_APROBADO`.

#### Registrar pago rechazado

`PUT /api/pedidos/{id}/pago-rechazado`

Actualiza el estado del pedido a `PAGO_RECHAZADO`.

#### Cancelar pedido

`PUT /api/pedidos/{id}/cancelar`

Actualiza el estado del pedido a `CANCELADO`.

#### Pedido disponible para retiro

`PUT /api/pedidos/{id}/disponible-retiro`

Actualiza el estado del pedido a `DISPONIBLE_RETIRO`.

#### Registrar retiro

`PUT /api/pedidos/{id}/retirado`

Actualiza el estado del pedido a `RETIRADO`.

#### Registrar despacho

`PUT /api/pedidos/{id}/despachado`

Actualiza el estado del pedido a `DESPACHADO`.

## Inventario

### Consultar stock

`GET /api/inventario/{productoId}`

Permite consultar el stock disponible de un producto.

### Descontar stock

`PUT /api/inventario/{productoId}/descontar`

Permite descontar una cantidad determinada del inventario.

Ejemplo de solicitud:

```json
{
  "cantidad": 2
}
```

## Pruebas realizadas

Los servicios REST fueron probados mediante **Postman**.

Se validaron las siguientes rutas del proceso:

### Pago aprobado y retiro

`PENDIENTE → PAGO_APROBADO → DISPONIBLE_RETIRO → RETIRADO`

### Pago rechazado y cancelación

`PENDIENTE → PAGO_RECHAZADO → CANCELADO`

### Pago aprobado y despacho

`PENDIENTE → PAGO_APROBADO → DESPACHADO`

### Inventario

Se validó la consulta de stock y su posterior actualización mediante el descuento de unidades.

## Ejecución en Flowable Work

El proceso TO-BE fue publicado y ejecutado en **Flowable Work Sandbox**.

Se comprobó correctamente la ejecución de las tareas humanas iniciales y sus formularios:

1. Seleccionar productos.
2. Confirmar compra e ingresar datos.
3. Seleccionar modalidad de entrega.
4. Llegada a la tarea automática `Generar pedido`.

Actualmente, la integración con la API de External Workers de la instancia académica se encuentra en revisión debido a una respuesta **HTTP 401 Unauthorized** al intentar adquirir los jobs desde el cliente Java.

El proceso BPMN, los formularios, los servicios REST y las pruebas independientes de la API se mantienen funcionales.

## Tecnologías utilizadas

- BPMN 2.0
- Flowable Design
- Flowable Work
- Flowable Inspect
- Java 21
- Spring Boot
- Jakarta REST
- Maven
- Postman
- Git
- GitHub

## Estructura del repositorio

- `bpmn/`: modelos BPMN AS-IS y TO-BE e imágenes de los diagramas.
- `src/`: código fuente de la API REST y External Workers.
- `evidencias-flowable/`: capturas de diseño, publicación y ejecución del proceso en Flowable.
- `evidencias-api/`: evidencias de las pruebas realizadas mediante Postman.
- `gestion-proyecto/`: documentación de planificación, seguimiento y desarrollo individual.
- `pom.xml`: configuración y dependencias Maven.
- `README.md`: documentación general del proyecto.

## Estado actual del proyecto

- Modelo BPMN AS-IS: completado.
- Modelo BPMN TO-BE: completado.
- Publicación en Flowable Sandbox: completada.
- Formularios Flowable: implementados.
- API REST: implementada.
- Pruebas Postman: realizadas.
- External Workers: implementados.
- Integración External Worker con Flowable Sandbox: pendiente de resolución de autenticación HTTP 401.
- Repositorio GitHub: actualizado.

## Autora

**Zaira Manriquez**

Asignatura: **Integración de Plataformas**