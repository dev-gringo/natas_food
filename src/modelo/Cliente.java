package modelo;

public class Cliente extends Usuario {
    private String celular; // <<-- CAMBIO: Antes era email
    private String direccion;

    // Se actualiza el constructor para recibir el celular
    public Cliente(int id, String nombre, String celular, String direccion) {
        // Llama al constructor de la superclase (Usuario). Usamos el celular en lugar del email.
        super(id, nombre, celular);
        this.celular = celular;
        this.direccion = direccion;
    }

    public String getCelular() { return celular; } // Nuevo getter
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        // Se actualiza la salida para mostrar el celular
        return "Cliente " + super.toString() + ", Celular: " + celular + ", Dirección: " + direccion;
    }
}