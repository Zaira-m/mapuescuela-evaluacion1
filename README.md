# Mapuescuela – Evaluación 1

Proyecto desarrollado para la asignatura **Integración de Plataformas**, orientado al análisis y mejora del proceso de gestión de ventas de Mapuescuela.

## Objetivo

Modelar el proceso actual de gestión de ventas de Mapuescuela, proponer mejoras mediante un modelo TO-BE e implementar un prototipo ejecutable utilizando Flowable y servicios web.

## Contenido del proyecto

### Modelos BPMN

En la carpeta `bpmn` se encuentran:

- `mapuescuela-as-is.bpmn`: representación del proceso actual de gestión de ventas.
- `mapuescuela-to-be.bpmn`: propuesta mejorada del proceso, incorporando automatización y optimización del flujo.

El modelo TO-BE fue implementado y probado en Flowable mediante diferentes rutas del proceso.

### Visualización de los modelos

#### Modelo BPMN AS-IS

El siguiente diagrama representa el proceso actual de gestión de ventas de Mapuescuela:

![Modelo BPMN AS-IS](bpmn/mapuescuela-as-is.png)

#### Modelo BPMN TO-BE

El siguiente diagrama representa la propuesta de mejora y automatización del proceso:

![Modelo BPMN TO-BE](bpmn/mapuescuela-to-be.png)

### Proceso ejecutable en Flowable

Se implementó el proceso de gestión de ventas considerando:

- Registro y generación del pedido.
- Revisión del comprobante de pago.
- Flujo de pago aprobado y rechazado.
- Actualización de inventario.
- Selección de modalidad de entrega.
- Retiro o despacho del pedido.
- Formularios para ingreso de información.
- Condiciones para controlar las distintas rutas del proceso.
- Evento temporizador asociado al proceso de pago.

### Web Services

Se desarrolló una API REST utilizando **Java y Spring Boot**.

Endpoints implementados:

- `GET /api/pedidos/123`: permite consultar información de un pedido.
- `POST /api/pedidos`: permite registrar los datos de un pedido.

Los servicios fueron probados mediante **Postman**, obteniendo respuestas HTTP `200 OK`.

## Tecnologías utilizadas

- BPMN 2.0
- Flowable
- Java 21
- Spring Boot
- Maven
- Postman
- Git
- GitHub

## Estructura del repositorio

- `bpmn/`: modelos BPMN AS-IS y TO-BE, junto con sus respectivas imágenes para visualización.
- `src/`: código fuente de la API REST desarrollada con Java y Spring Boot.
- `evidencias-flowable/`: capturas de la ejecución y validación del proceso TO-BE en Flowable Work.
- `evidencias-api/`: evidencias de las pruebas realizadas a la API REST desde navegador y Postman, incluyendo solicitudes GET y POST con respuesta HTTP 200 OK.
- `gestion-proyecto/`: documentación de seguimiento, organización y desarrollo individual del proyecto.
- `pom.xml`: configuración y dependencias del proyecto Maven.
- `README.md`: documentación general del proyecto.

## Autora

**Zaira Manriquez**

Evaluación 1 – Integración de Plataformas
