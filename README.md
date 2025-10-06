# Nata's Food: Sistema de Gestión de Pedidos (Java POO Avanzado)

## Descripción del Proyecto

Este proyecto es un sistema de gestión de pedidos de comida por consola desarrollado en Java. Fue diseñado para cumplir con los requisitos de un curso avanzado de Programación Orientada a Objetos (POO), integrando herencia, relaciones entre clases, patrones de diseño y programación funcional con Streams.

El sistema simula el flujo de una aplicación de comida rápida, permitiendo el registro dinámico de clientes, la creación de pedidos basados en un menú predefinido, y el procesamiento de pagos utilizando diferentes estrategias.

---

##  Tecnologías y Requisitos Cumplidos

El proyecto fue construido en Java y cumple rigurosamente con las siguientes especificaciones:

### 1. Programación Orientada a Objetos (POO)

| Requisito | Implementación |
| :--- | :--- |
| **Clases Principales** | `Usuario`, `Producto`, `Pedido`. |
| **Herencia** | La clase `Cliente` extiende la clase abstracta `Usuario`. |
| **Relaciones** | **Agregación:** Un `Pedido` contiene una lista de `Producto`s. |
| | **Asociación:** Un `Pedido` está asociado a un `Cliente`. |

### 2. Patrones de Diseño (Mínimo dos)

| Patrón | Clase Implementada | Propósito |
| :--- | :--- | :--- |
| **Singleton** | `GestorDePedidos` | Garantiza una única instancia centralizada para manejar el estado global del sistema (menú, clientes, pedidos). |
| **Strategy** | `EstrategiaDePago` (Interfaz), `PagoTarjeta`, `PagoEfectivo` | Permite cambiar el algoritmo de pago de manera flexible sin modificar la clase `Pedido`. |

### 3. Programación Funcional (Java Streams)

Se utiliza Java Streams (Java 8+) y expresiones lambda para procesar colecciones de objetos de manera eficiente:

* **`filter()`:** Usado para filtrar productos por precio y obtener listas de pedidos pendientes de pago.
* **`map()`:** Usado para transformar listas (ej., obtener solo los nombres de los clientes).
* **`forEach()`:** Usado para iterar y mostrar el menú y los detalles de los pedidos.
* **`average()`, `sum()`, `collect()`:** Usados para calcular estadísticas y totales.

### 4. Gestión de Errores y Validaciones

* Se utiliza **`try-catch`** para manejar errores de entrada del usuario (`InputMismatchException`) en la interacción por consola.
* El programa no se detiene abruptamente frente a datos no numéricos o inválidos.
* Mensajes de error personalizados (`System.err`) para guiar al usuario.

---

## Estructura del Proyecto

El código está organizado en paquetes para mantener la cohesión y el bajo acoplamiento:

| Paquete | Rol | Clases Clave |
| :--- | :--- | :--- |
| `modelo` | Contiene los objetos de dominio (entidades del negocio). | `Cliente`, `Pedido`, `Producto`. |
| `sistema` | Contiene el administrador central del sistema. | `GestorDePedidos` (Singleton). |
| `estrategia` | Contiene las clases del patrón Strategy. | `EstrategiaDePago`, `PagoTarjeta`, `PagoEfectivo`. |
| `main` (Raíz) | Contiene el punto de entrada y la lógica de la consola. | `NataFoodApp`. |

---

##  Configuración y Ejecución

### Requisitos

* **JDK 8 o superior** (Se recomienda Java 17+ para mejor soporte de Streams).

### Instrucciones de Ejecución

1.  Clonar este repositorio:
    ```bash
    git clone https://github.com/dev-gringo/natas_food.git
    ```
2.  Abrir el proyecto en un IDE (IntelliJ IDEA, Eclipse, VS Code).
3.  Ejecutar la clase principal `NataFoodApp.java` que se encuentra en el paquete `main`.

Una vez iniciado, el programa guiará al usuario a través del menú principal: registrar un cliente con ID automático, crear un pedido seleccionando productos por ID, y realizar el pago usando una estrategia a elección.

---

## Nota sobre Precios y Moneda

Los precios han sido ajustados para simular **Pesos Colombianos (COP)** con valores en miles.

* **Costo de Envío:** $2,000 COP
* **Formato de Salida:** Se utiliza `String.format("%,.0f")` para mostrar los totales sin decimales y con separador de miles (ej., $16,000).

---
**Desarrollado por:** Johan Orrego
