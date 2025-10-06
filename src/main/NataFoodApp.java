package main;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Producto;
import sistema.GestorDePedidos;
import estrategia.EstrategiaDePago;
import estrategia.PagoEfectivo;
import estrategia.PagoTarjeta;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class NataFoodApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestorDePedidos gestor = GestorDePedidos.getInstancia(); // Singleton
    private static int siguienteIdCliente = 101; // Contador para ID automático

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("        BIENVENIDO A NATA'S FOOD         ");
        System.out.println("=========================================");

        // El sistema inicia sin clientes ni pedidos pre-registrados.
        mostrarMenuPrincipal();
    }

    // --- Registro de Cliente con Validación y Gestión de Errores ---

    private static Cliente registrarClienteDesdeTeclado() {
        System.out.println("\n---Registro de Nuevo Cliente ---");

        // Asignación automática del ID (Mejor práctica)
        int id = siguienteIdCliente++;
        System.out.println("ID asignado automáticamente: " + id);

        System.out.print("Ingrese Nombre completo: ");
        String nombre = scanner.nextLine();

        // Pedir y validar el número de celular
        String celular = "";
        boolean celularValido = false;
        while (!celularValido) {
            System.out.print("Ingrese Número de Celular: ");
            celular = scanner.nextLine();

            // VALIDACIÓN SIMPLE: Asegurar que el número no esté vacío
            if (celular.trim().isEmpty()) {
                System.err.println("Error: El número de celular no puede estar vacío.");
            } else {
                celularValido = true;
            }
        }

        System.out.print("¿direcion, donde enviamos tu pedido?: ");
        String direccion = scanner.nextLine();

        // Se llama al constructor del Cliente con el campo 'celular'
        Cliente nuevoCliente = new Cliente(id, nombre, celular, direccion);
        gestor.registrarCliente(nuevoCliente);
        System.out.println("Cliente " + nombre + " registrado con éxito.");

        return nuevoCliente;
    }

    // --- Menú Principal ---

    private static void mostrarMenuPrincipal() {
        int opcion = -1;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Realizar Nuevo Pedido");
            System.out.println("2. Ver Menu y Estadísticas (Streams)");
            System.out.println("3. Pagar Pedido (Strategy)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            // GESTIÓN DE ERRORES (try-catch) para el menú*****************************
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // esto es para Consumir el salto de línea

                switch (opcion) {
                    case 1: crearPedido(); break;
                    case 2: mostrarEstadisticas(); break;
                    case 3: realizarPago(); break;
                    case 0: System.out.println("Gracias por usar Nata's Food. ¡Hasta pronto!"); break;
                    default:
                        System.err.println("invalido. Por favor, pon una opcion correcta.");
                }
            } catch (InputMismatchException e) {
                // Personalización de mensajes de error y evitar detención abrupta
                System.err.println("Error: Ingrese un número para seleccionar una opción.");
                scanner.nextLine(); // aqui Limpiamos el buffer
                opcion = -1;
            }
        } while (opcion != 0);
    }

    // --- Lógica de Creación de Pedido ---

    private static void crearPedido() {
        System.out.println("\n------ Nuevo Pedido ------");

        // Se registra un nuevo cliente
        Cliente clienteActual = registrarClienteDesdeTeclado();

        Pedido nuevoPedido = new Pedido(clienteActual);

        System.out.println("\nProductos Disponibles:");
        // PROGRAMACIÓN FUNCIONAL: forEach para listar el menú
        gestor.getMenu().forEach(p -> System.out.println(p.getId() + ". " + p));

        boolean agregarMas = true;
        while (agregarMas) {
            System.out.print("Ingrese ID del producto a agregar (0 para terminar): ");
            try {
                final int idProducto = scanner.nextInt(); // Variable temporal (effectively final)
                scanner.nextLine();

                if (idProducto == 0) {
                    agregarMas = false;
                    continue;
                }

                // PROGRAMACIÓN FUNCIONAL (Streams): Buscar producto por ID
                Producto productoSeleccionado = gestor.getMenu().stream()
                        .filter(p -> p.getId() == idProducto) // filter() + lambda
                        .findFirst()
                        .orElse(null);

                if (productoSeleccionado != null) {
                    nuevoPedido.agregarProducto(productoSeleccionado);
                    System.out.println("gregado: " + productoSeleccionado.getNombre());
                } else {
                    System.err.println("Producto no encontrado. Intente otra vez.");
                }

            } catch (InputMismatchException e) {
                System.err.println("Error: ID debe ser un número entero.");
                scanner.nextLine(); // Limpiar buffer
            }
        }

        if (!nuevoPedido.getProductos().isEmpty()) {
            gestor.agregarPedido(nuevoPedido);
            // Muestra el costo de envío de 2,000
            System.out.printf("Total a pagar (incl. envío $2,000 COP): $%,.2f\n", nuevoPedido.calcularTotal());
        } else {
            System.out.println("Pedido cancelado: No se agregaron productos.");
        }
    }

    // --- Lógica de Pago (Patrón Strategy) ---

    private static void realizarPago() {
        System.out.println("\n------ Pagar Pedido ------");

        if (gestor.getPedidos().isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        // PROGRAMACIÓN FUNCIONAL (Streams): Filtrar pedidos PENDIENTES**********************************
        List<Pedido> pedidosPendientes = gestor.getPedidos().stream()
                .filter(p -> p.getEstado().equals("PENDIENTE"))
                .collect(Collectors.toList()); // collect()

        if (pedidosPendientes.isEmpty()) {
            System.out.println("No hay pedidos PENDIENTES para pagar.");
            return;
        }

        System.out.println("Pedidos pendientes de pago:");
        // Uso del formato de miles (%,.2f)
        pedidosPendientes.forEach(p -> System.out.println("ID: " + p.getIdPedido() + " | Cliente: " + p.getCliente().getNombre() + " | Total: $" + String.format("%,.2f", p.calcularTotal())));

        Pedido pedidoAPagar = null;
        while(pedidoAPagar == null) {
            System.out.print("\nIngrese el ID del pedido a pagar: ");
            try {
                final int idSeleccionado = scanner.nextInt();
                scanner.nextLine();

                // PROGRAMACIÓN FUNCIONAL (Streams): Buscar el pedido por ID y estado PENDIENTE
                pedidoAPagar = pedidosPendientes.stream()
                        .filter(p -> p.getIdPedido() == idSeleccionado)
                        .findFirst()
                        .orElse(null);

                if (pedidoAPagar == null) {
                    System.err.println("Error: Pedido ID #" + idSeleccionado + " no encontrado o ya está pagado.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Ingrese un número entero para el ID del pedido.");
                scanner.nextLine();
            }
        }

        double total = pedidoAPagar.calcularTotal();

        // Uso del formato de miles (%,.2f)
        System.out.println("\nEstrategias de Pago disponibles para Pedido #" + pedidoAPagar.getIdPedido() + " ($" + String.format("%,.2f", total) + "):");
        System.out.println("1. Tarjeta de Crédito/Débito");
        System.out.println("2. Efectivo");
        System.out.print("Seleccione un método de pago: ");

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();

            EstrategiaDePago estrategia;
            switch (opcion) {
                case 1: estrategia = new PagoTarjeta(); break;
                case 2: estrategia = new PagoEfectivo(); break;
                default:
                    System.err.println("Método de pago inválido. Cancelando pago.");
                    return;
            }

            // PATRÓN STRATEGY EN ACCIÓN
            estrategia.pagar(total);

            // Actualizar estado del pedido
            pedidoAPagar.setEstado("PAGADO");
            System.out.println("Pago exitoso. Pedido #" + pedidoAPagar.getIdPedido() + " ahora está PAGADO.");

        } catch (InputMismatchException e) {
            System.err.println("Error: Ingrese un número para seleccionar el método de pago.");
            scanner.nextLine();
        }
    }

    // --- Programación Funcional (Streams y Lambdas) ---

    private static void mostrarEstadisticas() {
        System.out.println("\n--- Estadísticas del Sistema---");

        // 1. Filtrar y contar productos caros (Precio > $10,000 COP)
        long productosCaros = gestor.getMenu().stream()
                .filter(p -> p.getPrecio() > 10000.00)
                .count();

        System.out.println("Productos con precio mayor a $10,000 COP: " + productosCaros);

        // 2. Calcular el precio promedio del menú ***************************************************
        OptionalDouble promedio = gestor.getMenu().stream()
                .mapToDouble(Producto::getPrecio) // map()
                .average();

        System.out.print("Precio promedio de un artículo del menú: ");
        // Uso del formato de miles (%,.2f)
        promedio.ifPresent(p -> System.out.printf("$%,.2f\n", p));

        // 3. Crear una lista con los nombres de los clientes
        List<String> nombresClientes = gestor.getClientes().stream()
                .map(Cliente::getNombre) // map()
                .collect(Collectors.toList()); // collect()
        System.out.println("Nombres de clientes registrados: " + String.join(", ", nombresClientes));

        // 4. Listar el detalle de todos los pedidos usando forEach
        System.out.println("\nDetalle de todos los Pedidos (forEach):");
        gestor.getPedidos().forEach(p -> {
            System.out.println("  - " + p);
        });
    }
}