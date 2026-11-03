# Análisis de la limitación y relación con Tacares

## Evidencia y límites de lo que afirmamos
En el primer trabajo el equipo describió hidrantes, paradas, bancas, comercios
y otros elementos de Tacares. Bruno confirmó que no tuvieron problemas al
capturar varios datos de un elemento. No presentamos como experiencia real una
dispersión de atributos, pérdida de información ni trabajo de limpieza que no
reportamos. Tampoco inferimos la dispersión a partir del informe: no se hizo una
auditoría de los GPX para demostrarla.

El informe previo sí proporciona un caso útil: un hidrante tiene tipo, color,
posición y cantidad de acoples. Como escenario hipotético, al registrar esas
características en capturas independientes se necesitaría una relación explícita
para saber si describen uno o varios hidrantes. La proximidad no basta: puede
haber objetos vecinos y variación de GPS. La ficha propuesta conserva la identidad
del elemento al agregar características, sin exigir otra coordenada por atributo.

## Cómo el código moldea lo que se entiende por punto
En `TagButtonOnClickListener.onClick`, cada pulsación genera un UUID y emite
`INTENT_TRACK_WP`. En `GPSLogger`, esa solicitud llega a `DataHelper.wayPoint`,
que guarda la posición y el nombre de la captura. Este flujo privilegia la
observación individual como unidad de registro, no una ficha de atributos.
El UUID identifica una captura, pero por sí solo no vincula capturas distintas.

Un punto puede significar una coordenada, una observación o un objeto de la
comunidad. Son conceptos diferentes. Cinco atributos no equivalen necesariamente
a cinco elementos. Si se cuentan waypoints sin comprobar qué representan, un
inventario podría sobrecontar objetos. Si se agrupa por distancia, podría unir
objetos diferentes. Son riesgos del modelo, no errores comprobados del equipo.

En campo, el flujo actual invita a pensar en pulsaciones sucesivas. Una ficha
invita a seleccionar primero qué objeto se describe y luego sus características.
Ese cambio requiere mostrar el elemento activo: una ficha oculta o sin nombre
podría provocar que se atribuyan observaciones al objeto equivocado.

## Actores e impactos
| Actor | Necesidad | Impacto y cuidado de diseño |
| --- | --- | --- |
| Personas de la comunidad | Reconocer los elementos con su propio vocabulario. | Etiquetas legibles; no imponer términos técnicos para toda característica. |
| Personas que capturan | Saber qué elemento están describiendo. | Mostrar nombre, referencia y resumen antes de confirmar. |
| Coordinación del mapeo | Acordar categorías y revisar aportes. | Distinguir dato desconocido de dato ausente; no completar por suposición. |
| Personas que analizan | Contar elementos y comparar atributos. | Identidad explícita; conservar procedencia y validar las exportaciones. |
| Mantenedores | Integrar una mejora sin dañar capturas anteriores. | Entrada opcional a la ficha, alcance acotado y pruebas de regresión futuras. |

En inventarios o encuestas pueden aparecer datos sensibles. No se propone
recoger identificadores personales: la ficha debe limitarse al propósito acordado
del mapeo. La mejora técnica no reemplaza los acuerdos de la comunidad.

## Casos para discutir con personas usuarias
1. Dos hidrantes cercanos: cada ficha conserva el identificador del suyo.
2. Dos atributos de un hidrante: la ficha los muestra juntos sin crear otro objeto.
3. Un dato desconocido: dejarlo sin registrar, sin inventar un valor.
4. Una ruta o un edificio: la ficha no convierte un punto en línea o polígono.
   El diseño de geometrías distintas queda fuera de esta propuesta.

Fuentes: informe del equipo «Mapeo de Tacares»; confirmación de Bruno;
consigna del curso; código base 93891dd; incidencia oficial
https://github.com/labexp/osmtracker-android/issues/707.
