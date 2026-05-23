# 🌿 EcoRide - Módulo Central de Gestión

Este proyecto es el desarrollo del módulo central de la plataforma **EcoRide** para la empresa ficticia homónima, la cual requiere gestionar de manera eficiente el desbloqueo, cálculo de tarifas y cobro de vehículos eléctricos (monopatines y bicicletas) distribuidos en la vía pública.

---

## 🚀 Actualizaciones y Tecnologías

El proyecto ha sido recientemente configurado y validado para su correcto funcionamiento con:
*   **Java 21** (JDK 21)
*   **Spring Boot 4.0.6**
*   **Maven** para la gestión de dependencias y construcción
*   **Lombok** para la reducción de código boilerplate (constructores, getters/setters, builders)

---

## 🛠️ Nuevas Características de Prueba e Interfaz

Para simplificar la verificación de las reglas de negocio descritas en el trabajo práctico por parte de los profesores o del equipo, se agregaron las siguientes utilidades:

### 1. Panel de Control Interactivo (Web UI)
Se ha diseñado una interfaz web de simulación moderna, limpia y estética (estilo Dark Mode con Glassmorphism) que se sirve automáticamente al iniciar la aplicación.
*   **Acceso local:** [http://localhost:8080/](http://localhost:8080/)
*   **Características:** Puedes elegir haciendo clic en los distintos usuarios de prueba, vehículos con sus respectivos niveles de batería y métodos de pago. Al presionar **"Desbloquear y Simular Alquiler 🔓"**, se efectúa una llamada HTTP al backend en tiempo real, imprimiendo tanto la estructura del payload enviado como la respuesta JSON y el estado HTTP de vuelta en una consola interactiva integrada.

### 2. Endpoints Flexibles
Se modificó el controlador `AlquilerController` para brindar múltiples métodos de consumo sin romper los tests automatizados originales:
*   `GET /api/alquileres/desbloquear` (Cuerpo JSON - Usado por tests automatizados).
*   `POST /api/alquileres/desbloquear` (Cuerpo JSON - Estándar para clientes REST y la interfaz Web).
*   `GET /api/alquileres/desbloquear-params` (Acepta parámetros en formato Query Params).
    *   *Ejemplo de uso:* `http://localhost:8080/api/alquileres/desbloquear-params?idUsuario=1&patente=MONO1&metodoPago=TARJETA`

---

## 👥 Datos Inicializados (Mock Data)

Al iniciar, el sistema levanta automáticamente datos de ejemplo a través del `DataInitializer`:

### Usuarios
*   **ID [1]: Juan Perez** (Usuario Regular - Abona la tarifa base completa).
*   **ID [2]: Ana Gomez** (Usuario Premium - Recibe un **15% de descuento** sobre la tarifa base según diagrama).

### Vehículos y Estaciones
*   **Estación "Centro"**
    *   `MONO1` - Monopatín (Batería: 80%, Tarifa Base: $100.0). *Desbloqueo exitoso.*
    *   `BICI1` - Bicicleta Eléctrica (Batería: 50%, Tarifa Base: $200.0). *Desbloqueo exitoso.*
    *   `MONO_BAJO` - Monopatín (Batería: 10%, Tarifa Base: $100.0). *Lanza BateriaInsuficienteException (mínimo requerido 15%).*
*   **Estación "Norte"**
    *   `BICI2` - Bicicleta Eléctrica (Batería: 95%, Tarifa Base: $250.0). *Desbloqueo exitoso.*

### Métodos de Pago
*   `TARJETA` (Tarjeta de Crédito)
*   `BILLETERA` (Billetera Virtual)

---

## 💻 Instrucciones de Uso y Compilación

### Compilar y Ejecutar Servidor
Para levantar el servidor local en el puerto `8080`, ejecuta:
```bash
./mvnw spring-boot:run
```
Una vez iniciado, podrás ver en la consola un cuadro resumen con las URLs de acceso directo y los endpoints activos. Abre [http://localhost:8080/](http://localhost:8080/) en tu navegador para realizar pruebas interactivas.

### Ejecutar Pruebas Unitarias
Para correr la suite de pruebas unitarias automáticas:
```bash
./mvnw test
```
