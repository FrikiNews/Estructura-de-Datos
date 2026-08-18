package listaligada;

/* @author friki */

public class Contacto {
    private String nombre;
    private String direccion;
    private String telefono;

    public Contacto(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;
        Contacto otro = (Contacto) obj;
        return nombre.equalsIgnoreCase(otro.nombre);
    }

    @Override
    public String toString() {
        return nombre + " | Tel: " + telefono + " | Dir: " + direccion;
    }
}