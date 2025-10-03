package modelo;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected String contacto;

    public Usuario(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = email;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return contacto; }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre;
    }
}
