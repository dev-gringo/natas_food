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
        menu.add(new Producto(1, "Hamburguesa Clásica", 18500.00));
        menu.add(new Producto(2, "Pizza Personal", 12000.00));
        menu.add(new Producto(3, "Gaseosa Coca Cola", 3500.00));
        menu.add(new Producto(4, "porcion Papas Fritas", 3500.00));
        menu.add(new Producto(5, "panzerotti",4000));
        menu.add(new Producto(6,"picada pequeña", 10000));
        menu.add(new Producto(7,"picada mediana", 15000));
        menu.add(new Producto(8,"picada pequeña", 22000));
        menu.add(new Producto(9,"perro caliente especial", 10500));
        menu.add(new Producto(10,"Alitas de Pollo (6 und)", 15000));
        menu.add(new Producto(11, "Jugo Natural de Naranja", 4500.00));
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