# Integración técnica y criterios de aceptación

Las notas en `net.osmtracker.proposal.IntegrationNotes` señalan métodos reales
del código consultado sin cambiar las actividades existentes ni el almacenamiento.
El orden de una implementación futura sería selección, edición en memoria,
validación, persistencia revisada y finalmente exportación. Ninguna fase debe
presentarse como terminada solo porque la aplicación compile.

| Criterio | Comprobación | Estado en esta propuesta |
| --- | --- | --- |
| Referencia no vacía | Constructor rechaza nulos o blancos. | Modelo Java. |
| Independencia | Atributos de un borrador no aparecen en otro. | Prueba automatizada incluida en el cierre. |
| Corrección de valor | Repetir clave actualiza el par, sin duplicarlo. | Modelo Java. |
| Lectura estable | La copia devuelta no puede editarse y no cambia retroactivamente. | Modelo Java. |
| No crear puntos por atributo | No conectar `putAttribute` al evento de captura. | Decisión de diseño, pendiente de interfaz. |
| Cancelar sin alterar originales | Editar una copia y descartarla. | Flujo propuesto, pendiente de integración. |
| Reabrir ficha | Cerrar el proceso y recuperar todos los atributos. | Requisito futuro; no implementado. |
| Guardado fiable | Confirmar éxito solo después de persistir. | Requisito futuro. |
| Exportación interoperable | Leer archivo con herramientas concretas y verificar atributos. | Requisito futuro. |

## Decisiones que requieren trabajo posterior
La clase en memoria no basta para una funcionalidad de uso real. Si se conserva
la asociación entre sesiones, debe acordarse cómo almacenarla, cómo actualizarla
y qué hacer al eliminar el waypoint. Ese trabajo puede requerir conocimientos
de bases de datos; se deja planteado, sin diseñar tablas ni realizar migraciones.
No se reutiliza el campo de nombre como almacenamiento improvisado de una ficha.

Las fotos y audios tampoco se incorporan como simples pares de texto en esta
versión: asociación de archivos, permisos y eliminación necesitan revisión propia.
El primer modelo se limita a características textuales, no a toda clase de dato.