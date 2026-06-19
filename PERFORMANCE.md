# Anexo Técnico de Rendimiento - EcoRide Pro

Este documento contiene la explicación técnica y de rendimiento sobre las optimizaciones de algoritmos y estructuras de datos aplicadas en la versión **EcoRide Pro**.

---

## 1. Eficiencia de Búsqueda: Tabla Hash vs Búsqueda Lineal
* **Búsqueda Lineal Anterior ($O(N)$):**
  En la primera versión, para buscar un vehículo por su patente, se recorría una lista secuencial elemento por elemento desde el principio hasta el final. Si había $N$ vehículos registrados, en el peor de los casos se requerían $N$ comparaciones. Con miles de vehículos circulando, este proceso consume excesiva CPU y genera tiempos de respuesta lentos.
* **Búsqueda Indexada Actual ($O(1)$):**
  Al reestructurar el almacenamiento en `EstacionAnclaje` utilizando un `Map<String, Vehiculo>` (implementado como un `HashMap`), la búsqueda pasa a ser instantánea y de tiempo constante. La tabla hash utiliza el algoritmo de dispersión (*hashing*) para calcular matemáticamente la dirección exacta de memoria del vehículo a partir de su patente en un solo paso. Por tanto, el tiempo de respuesta es idéntico e instantáneo si hay 10 o 100.000 vehículos en la flota.

---

## 2. Deduplicación GPS sin Bucles Anidados
* **El Problema del Bucle Anidado ($O(N^2)$):**
  Si comparásemos cada reporte de alerta GPS contra todos los demás para eliminar duplicados, necesitaríamos dos bucles anidados (`for` dentro de `for`). Esto resulta en una complejidad cuadrática de $O(N^2)$. Si recibimos 10.000 alertas de posición, el servidor requeriría realizar **100 millones de comparaciones**, bloqueando los hilos del servidor y saturando la CPU.
* **La Solución en una Sola Pasada ($O(N)$):**
  El algoritmo de `GpsService` recorre la lista desordenada de alertas **una sola vez** (complejidad lineal $O(N)$) y almacena los valores en un conjunto único `HashSet` (`LinkedHashSet` para preservar el orden original). Dado que verificar e insertar en un conjunto hash toma tiempo constante $O(1)$, la deduplicación de 10.000 alertas se realiza en apenas **10.000 operaciones en total**, liberando recursos y garantizando la escalabilidad.

---

## 3. Ordenamiento Natural vs Ordenamiento por Tarifas Concurrentes
* **Ordenamiento Natural (Intrínseco):**
  La clase `Vehiculo` implementa de forma directa la interfaz estándar de Java `Comparable<Vehiculo>`. A través del método `compareTo()`, definimos que el orden predeterminado (o natural) de los vehículos de la flota es por **porcentaje de batería de menor a mayor** (prioridad crítica para el equipo de mantenimiento).
* **Ordenamiento Alternativo y Concurrente (Externo):**
  Para poder ordenar la flota de forma alternativa por **tarifa fija base (de mayor a menor)** para el departamento de finanzas, sin interferir con el criterio natural ni pisar la lógica en memoria, creamos la clase externa `CostoBaseVehiculoComparator` que implementa `Comparator<Vehiculo>`.
  Al ser este comparador un componente sin estado externo y desacoplado, diferentes hilos o peticiones en memoria pueden realizar ordenamientos concurrentes en sus propias listas locales utilizando `Collections.sort(lista, comparator)` sin que ocurran interferencias o colisiones de datos en memoria dinámica.
