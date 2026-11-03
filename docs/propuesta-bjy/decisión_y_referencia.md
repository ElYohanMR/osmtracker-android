# Alternativas y decisión de diseño

## Dos enfoques
| Criterio | A: ficha de un waypoint | B: grupo de varios waypoints |
| --- | --- | --- |
| Unidad que entiende la persona | Un elemento con características. | Un grupo con miembros que deben interpretarse. |
| Ubicación | La del waypoint seleccionado. | Debe decidirse qué posición representa al grupo. |
| Capturas anteriores | No se agrupan automáticamente. | Puede permitir seleccionar capturas existentes, con revisión humana. |
| Interacción | Seleccionar, describir y revisar. | Seleccionar miembros, agrupar, corregir y desagrupar. |
| Riesgo principal | Editar la ficha equivocada. | Vincular objetos diferentes o perder el significado de cada miembro. |
| Dificultad técnica pendiente | Guardar atributos y reabrir la ficha. | Guardar relaciones y mantener consistencia entre miembros. |

## Alternativa elegida: A
Se propone agregar una ficha de atributos a un waypoint existente. Es más fácil
explicar «estoy describiendo este hidrante» que exigir a la persona administrar
un grupo de capturas. La selección debe ser explícita y la cabecera permanecer
visible. La decisión favorece claridad y facilidad de uso; no demuestra por sí
misma que sea más rápida. La facilidad debe validarse con personas usuarias.

El modelo `ElementDraft` usa el ID del waypoint y pares clave/valor en memoria.
No guarda coordenadas propias, ni crea otro waypoint por atributo. Las claves
repetidas reemplazan el valor anterior: la interfaz futura debe mostrar ese
reemplazo antes de confirmarlo. La edición de una ficha existente debe hacerse
en una copia para poder descartar los cambios.

## Revisión de la propuesta de Andrew López Herrera
Referencia: rama `feature/form` del repositorio
https://github.com/AndrewLopezHerrera/osmtracker-android/tree/feature/form.

En el código consultado, `GroupedDataManager` mantiene el estado de agrupación,
un UUID activo y un contador. `activateRecordGroupedData` inicia un waypoint;
`addGroupedData` envía datos asociados al UUID y actualiza la interfaz.
`DataHelper.addGroupedData` resuelve el waypoint y realiza una inserción mediante
`CONTENT_URI_GROUPED_DATA`. También hay componentes específicos de interfaz,
como `GroupedDataStatusLayout` y diálogos para notas numéricas y de selección.

De esa referencia tomamos la necesidad de una identidad activa y visible y de
una acción explícita para terminar la captura. Nuestra propuesta parte de un
waypoint seleccionado, en lugar de iniciar necesariamente uno al activar el modo.
No copiamos ni integramos la implementación de persistencia de esa rama: implica
componentes que exceden el alcance acordado para el equipo. La comparación es
de diseño y lectura de código, no una prueba de ejecución de esa versión.

La incidencia 707 plantea atributos asociados a una ubicación y menciona
extensiones GPX. Un GPX válido no garantiza que todos los programas interpreten
extensiones propias. La compatibilidad requerirá pruebas con lectores concretos.

## Consecuencias y límites
- Cambia la conversación de «agregar puntos» a «describir elementos».
- Ayuda a revisar atributos juntos, pero no mejora la exactitud física del GPS.
- No resuelve automáticamente la agrupación de capturas históricas.
- No se define una migración ni tablas de base de datos.
- Quedan pendientes persistencia, reapertura, eliminación y exportación.

Fuente adicional: https://github.com/labexp/osmtracker-android/issues/707.
