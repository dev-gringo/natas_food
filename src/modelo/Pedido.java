package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorPedidos = 1;
    private final int idPedido;
    private Cliente cliente; // Asociación
    private List<Producto> productos; // Agregación
    private String estado;
    private double costoEnvio = 2.000;

    public Pedido(Cliente cliente) {
        this.idPedido = contadorPedidos++;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.estado = "PENDIENTE";
    }

    // Método para agregar productos (parte de la Agregación)
    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    // Método para calcular el total
    public double calcularTotal() {
        // Uso de Streams para calcular la suma total de precios
        return productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum() + costoEnvio;
    }

    // Getters y Setters
    public int getIdPedido() { return idPedido; }
    public Cliente getCliente() { return cliente; }
    public List<Producto> getProductos() { return productos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return String.format("Pedido #%d para %s. Total: $%.2f. Estado: %s",
                idPedido, cliente.getNombre(), calcularTotal(), estado);
    }
}