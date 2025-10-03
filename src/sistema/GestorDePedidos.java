package sistema;

import modelo.Cliente;
import modelo.Pedido;
import modelo.Producto;
import java.util.ArrayList;
import java.util.List;

// Implementa el Patrón Singleton
public class GestorDePedidos {
    // Instancia estática única
    private static GestorDePedidos instancia;

    private final List<Pedido> pedidos;
    private List<Producto> menu;
    private List<Cliente> clientes;

    // Constructor privado para evitar la instanciación externa
    private GestorDePedidos() {
        this.pedidos = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.clientes = new ArrayList<>();
        inicializarMenu();
    }

    // Método estático de acceso global (Eager Initialization para simplicidad)
    public static GestorDePedidos getInstancia() {
        if (instancia == null) {
            instancia = new GestorDePedidos();
        }
        return instancia;
    }

    private void inicializarMenu() {
        // PRECIOS AJUSTADOS PARA PESOS COLOMBIANOS (COP)
        // (18.50 * 1000 = 18500.00)
        menu.add(new Producto(1, "Hamburguesa Clásica", 18500.00));
        // (12.00 * 1000 = 12000.00)
        menu.add(new Producto(2, "Pizza Personal", 12000.00));
        // (2.00 * 1000 = 2000.00)
        menu.add(new Producto(3, "Gaseosa Coca Cola", 2000.00));
        // (3.50 * 1000 = 3500.00)
        menu.add(new Producto(4, "Papas Fritas Grandes", 3500.00));
    }

    // Métodos de gestión
    public void registrarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void agregarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        System.out.println("Pedido #" + pedido.getIdPedido() + " agregado al sistema.");
    }

    // Getters para Streams
    public List<Producto> getMenu() { return menu; }
    public List<Pedido> getPedidos() { return pedidos; }
    public List<Cliente> getClientes() { return clientes; }
}