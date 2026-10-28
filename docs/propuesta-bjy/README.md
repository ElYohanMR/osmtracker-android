# Agrupación de atributos de un elemento: propuesta BJY

Curso: Sistemas de Información Geográfica, II semestre de 2026.

Equipo: Bruno Barrantes Coto, Jaiden Avila Badilla y Yohan Morera Ramíres.

Fork: https://github.com/ElYohanMR/osmtracker-android

Rama de trabajo: `feature/propuesta-agrupacion-BJY`, creada desde `develop`.

## 1. Planteamiento del problema

Al describir un elemento físico mediante varias capturas independientes, sus
atributos pueden quedar repartidos entre distintos waypoints. El registro de
cada captura por separado no expresa por sí mismo qué atributos pertenecen al
mismo elemento. La cercanía espacial tampoco prueba esa relación: dos objetos
pueden estar muy cerca y las lecturas GPS pueden variar.

Un elemento es el objeto observado; un atributo es una característica de ese
objeto; un waypoint es un registro geográfico de la herramienta. Confundirlos
puede llevar a contar capturas como si fueran objetos diferentes.

En Tacares el equipo **no reportó dificultades** al registrar varios datos del
mismo elemento. No se atribuyen pérdidas, errores de asociación ni tiempos extra
al trabajo realizado. Como ejemplo para analizar la limitación potencial, el
informe anterior describe hidrantes con color, tipo, posición y acoples. Esa
descripción permite discutir cómo conservar juntos los datos de un hidrante,
sin afirmar que durante la visita quedaron separados.

## 2. Propuesta inicial y alternativas

La alternativa inicial es una ficha con una ubicación de referencia y varios
atributos. La persona seleccionaría un waypoint y agregaría características a su
ficha, revisando el conjunto antes de confirmar.

| Alternativa | Ventaja | Aspecto pendiente |
| --- | --- | --- |
| A. Agregar atributos a un waypoint | Hace explícito que se describe un solo elemento. | Definir edición, guardado y recuperación de los atributos. |
| B. Vincular varios waypoints en un grupo | Conserva las capturas individuales. | Exige seleccionar miembros, administrar el grupo y distinguir sus ubicaciones. |

Se toma A como punto de partida por su claridad para quienes hacen trabajo de
campo y para quienes interpretan los registros. La comparación y la decisión
definitiva se desarrollarán en los siguientes avances.

Flujo conceptual, todavía sin pantallas implementadas:

```text
Seleccionar un waypoint -> Abrir ficha -> Agregar atributos
                                      -> Revisar -> Confirmar
                                                -> Cancelar
```

Para el mapeo comunitario interesa que las personas puedan entender qué están
describiendo y revisar sus aportes. La ficha ayudaría a hablar de «elementos» y
«características», sin exigir que cada característica se entienda como un punto
geográfico distinto. Quienes analizan los datos necesitan una relación explícita,
no una asociación deducida únicamente por proximidad.

## 3. Alcance de este primer commit

- Documentar el problema, la experiencia real y la alternativa inicial.
- Introducir `ElementDraft`, una clase Java experimental que mantiene en memoria
  el identificador de un waypoint y pares de atributo/valor.
- Mostrar cómo crear instancias independientes sin conectarlas a la captura real.
- Identificar lugares del código que deberían revisarse en una implementación.

El identificador representa un waypoint previamente seleccionado. No se inventa
otra coordenada ni se asume que dos capturas cercanas describen el mismo objeto.
La clase no verifica que el waypoint exista; esa validación correspondería a la
integración futura con la aplicación.

**Estado:** no hay ficha visible, persistencia, exportación de atributos ni cambios
en la base de datos. Los datos de la clase se pierden cuando se descarta la
instancia o termina el proceso. Esto es un modelo de la propuesta, no una solución
completa ni una implementación del guardado.

De acuerdo con la indicación docente, no se propone un diseño de tablas ni una
migración de base de datos. La conservación de las fichas al cerrar la aplicación
queda planteada como requisito técnico pendiente. No se promete resolverla solo
con una pantalla o con una clase en memoria.

## 4. Creación de instancias: ejemplo ilustrativo

Clase: `app/src/main/java/net/osmtracker/proposal/ElementDraft.java`.

```java
import net.osmtracker.proposal.ElementDraft;

// Identificadores de ejemplo: en la integración se usarían los del waypoint real.
ElementDraft hydrant = new ElementDraft("waypoint-demo-1", "Hidrante");
hydrant.putAttribute("color", "yellow");
hydrant.putAttribute("fire_hydrant:type", "pillar");

ElementDraft busStop = new ElementDraft("waypoint-demo-2", "Parada");
busStop.putAttribute("shelter", "yes");

// Son fichas independientes: la parada no recibe los atributos del hidrante.
// Repetir una clave actualiza su valor dentro de la misma ficha.
```

Estos valores ilustran el modelo; no son nuevas observaciones de campo ni datos
que se hayan subido a OpenStreetMap.

## 5. Puntos de integración por revisar

Rutas relativas a la raíz del repositorio:

| Archivo o componente | Responsabilidad que habría que estudiar |
| --- | --- |
| `app/src/main/java/net/osmtracker/listener/TagButtonOnClickListener.java` | Actualmente crea un UUID por pulsación y envía la solicitud de captura. Definir una entrada explícita al modo ficha sin alterar las capturas actuales. |
| `app/src/main/java/net/osmtracker/activity/WaypointList.java` | Estudiar la selección del waypoint que se va a describir. |
| `app/src/main/java/net/osmtracker/activity/TrackLogger.java` | Estudiar cómo presentar y cancelar la ficha durante el trabajo de campo. |
| `app/src/main/java/net/osmtracker/db/DataHelper.java` | Revisar los límites actuales de creación y actualización; plantear la conservación futura sin modificar el esquema en este avance. |
| `app/src/main/java/net/osmtracker/gpx/ExportTrackTask.java` | Estudiar cómo representar los atributos en una exportación posterior y comprobar qué lectores los conservan. |

No basta con agregar la clase para que estos componentes se comuniquen. Tampoco
se garantiza que un lector GPX interprete atributos personalizados: eso deberá
verificarse si se desarrolla la exportación.

## 6. Plan orientativo de seis commits

La asignación es una propuesta de reparto, no evidencia de trabajo realizado.
Cada persona debe revisar y realizar sus commits con su identidad, y sincronizar
la rama antes de empezar y antes de subir cambios.

| Nº | Responsable propuesto | Entrega y mensaje orientativo |
| --- | --- | --- |
| 1 | Yohan | Base conceptual y clase en memoria: `feat: add BJY proposal and element draft model` |
| 2 | Bruno | Relación con Tacares, actores e impacto conceptual: `docs: analyze mapping concepts and Tacares use cases` |
| 3 | Jaiden | Comparación A/B y decisión argumentada: `docs: compare waypoint grouping approaches` |
| 4 | Bruno | Diagramas de pantallas, edición y cancelación: `docs: design element form interaction flow` |
| 5 | Jaiden | Comentarios en puntos reales de integración y criterios de verificación: `docs: annotate proposed grouping integration points` |
| 6 | Yohan | Consolidar documentación, resultados de compilación y enlaces reales: `docs: finalize BJY proposal and validation evidence` |

El objetivo no es dividir artificialmente un cambio para alcanzar un número,
sino producir seis avances revisables. El informe final PDF es una entrega
adicional y debe incluir los enlaces reales a los commits de los tres integrantes.

## 7. Verificación

Para comprobar la compilación desde la raíz en Windows, con JDK y SDK configurados:

```powershell
.\gradlew.bat assembleDebug
```

Verificación de este avance (27 de octubre de 2026): `assembleDebug` finalizó
con **BUILD SUCCESSFUL**, usando JDK 17. Esta comprobación valida la compilación;
no se ejecutaron pruebas en teléfono ni emulador.

La compilación no demuestra que la agrupación esté implementada. Los siguientes
avances deben distinguir el comportamiento existente de las pantallas y cambios
que solo se proponen.

## 8. Fuentes de trabajo

- [Repositorio oficial y guía de contribución](https://github.com/labexp/osmtracker-android/blob/develop/CONTRIBUTING.md).
- [Incidencia 707: agrupación de datos en un waypoint](https://github.com/labexp/osmtracker-android/issues/707).
- [Propuesta de referencia, rama feature/form](https://github.com/AndrewLopezHerrera/osmtracker-android/tree/feature/form), pendiente de comparación detallada en los siguientes avances.
- Consigna del curso: «Mejora al OSMTracker (evaluación 2)-1.pdf».
- Informe del equipo «Mapeo de Tacares», primera evaluación.

## 9. Uso de inteligencia artificial y revisión pendiente

Se utilizó Codex para apoyar la revisión del código y preparar el borrador del
planteamiento, el plan de trabajo y la clase experimental de este primer avance.
La revisión y aprobación del contenido y planteamiendo general se realizo por 
los 3 estudiantes.
