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

El proceso de gestión de ventas fue implementado en **Flowable Design** y publicado para su ejecución y validación en un entorno **Flowable Work Trial**.

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

### Respaldo ejecutable de Flowable

El archivo `Mapuescuela.zip`, ubicado en la raíz del repositorio, corresponde a la exportación completa de la aplicación desde Flowable Design. Incluye el modelo BPMN, los formularios y la configuración necesaria para importar y publicar la aplicación en otro entorno Flowable.

La integración fue ejecutada y validada en Flowable Work Trial. Para reproducirla, cada usuario debe importar la aplicación, publicarla y configurar su propio token mediante la variable de entorno `FLOWABLE_TOKEN`. Por seguridad, ningún token se almacena en el repositorio.

## Formularios

Se implementaron formularios en Flowable para permitir la interacción del usuario con distintas etapas del proceso.

Entre ellos:

- Selección de productos.
- Datos de compra.
- Selección de modalidad de entrega.
- Información de datos bancarios.
- Transferencia y adjunto de comprobante de pago.
- Revisión del comprobante de pago.
- Selección del tipo de despacho.
- Registro de retiro del pedido.
- Registro de datos de envío.

Los formularios permiten capturar variables utilizadas durante la ejecución del proceso BPMN y gestionar la interacción de los usuarios con las distintas etapas del flujo.

## External Workers

El modelo TO-BE diferencia las tareas humanas de las tareas automáticas.

Se configuraron External Worker Tasks para los siguientes procesos:

- `generar-pedido`
- `actualizar-inventario`
- `registrar-pago-rechazado`
- `registrar-cancelacion`
- `pedido-disponible-retiro`

Los External Workers fueron desarrollados en Java para integrar el proceso BPMN con los servicios de la aplicación.

La integración con Flowable se encuentra implementada y operativa mediante External Workers conectados con la API REST desarrollada en Spring Boot. Los cinco workers fueron probados durante la ejecución de las distintas rutas del proceso BPMN.

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

Las evidencias de estas pruebas se encuentran disponibles en la carpeta `evidencias-api/`.

## Ejecución en Flowable Work

El proceso TO-BE fue publicado y ejecutado en **Flowable Work Trial**.

Se comprobó la ejecución completa del proceso de inicio a fin, incluyendo tareas humanas, formularios, gateways de decisión y tareas automáticas mediante External Workers.

Se validaron las principales rutas del proceso:

1. Pago aprobado con modalidad de retiro, finalizando en `Pedido retirado`.
2. Pago aprobado con despacho mediante courier, finalizando en `Pedido despachado`.
3. Pago aprobado con despacho mediante voluntario, finalizando en `Pedido despachado`.
4. Pago rechazado, ejecutando el registro del rechazo y la cancelación del pedido.

Durante estas ejecuciones se comprobó la integración de los External Workers:

- `generar-pedido`
- `actualizar-inventario`
- `pedido-disponible-retiro`
- `registrar-pago-rechazado`
- `registrar-cancelacion`

Los External Workers fueron ejecutados desde la aplicación Java/Spring Boot y permitieron que Flowable continuara automáticamente por las distintas etapas del proceso.

También se comprobó el funcionamiento de los formularios asociados a las tareas humanas, incluyendo el ingreso de datos del cliente, modalidad de entrega, información bancaria, carga del comprobante de pago, revisión del pago, registro del retiro y datos de despacho.

Las evidencias de estas ejecuciones se encuentran disponibles en la carpeta `evidencias-flowable/`.

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
- `evidencias-flowable/`: capturas de diseño y ejecución del proceso en Flowable, formularios y External Workers.
- `evidencias-api/`: evidencias de las pruebas de los servicios REST realizadas mediante Postman.
- `gestion-proyecto/`: documentación de planificación, seguimiento y desarrollo individual.
- `pom.xml`: configuración y dependencias Maven.
- `README.md`: documentación general del proyecto.

## Estado actual del proyecto

- Modelo BPMN AS-IS: completado.
- Modelo BPMN TO-BE: completado.
- Publicación y ejecución en Flowable Work Trial: completada.
- Formularios Flowable: implementados y probados.
- API REST: implementada.
- Pruebas Postman: realizadas.
- External Workers: implementados y probados.
- Integración External Worker con Flowable: implementada y validada mediante la ejecución del proceso BPMN y su integración con la API REST en Spring Boot.
- Rutas de retiro, despacho y cancelación: ejecutadas y validadas.
- Evidencias de ejecución: incorporadas al repositorio.
- Repositorio GitHub: actualizado.

## Autora

**Zaira Manriquez**

Asignatura: **Integración de Plataformas**