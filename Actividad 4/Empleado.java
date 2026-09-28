package actividad4;

/* @author friki */

/**
 * Empleado del caso practico.
 *
 * Implementa Comparable comparando por ID, y eso es lo que permite
 * guardarlo en el ArbolBinario sin que el arbol sepa nada de empleados:
 * el arbol solo sabe comparar, y la clase decide que significa comparar.
 */
public class Empleado implements Comparable<Empleado> {

    private final int id;
    private final String nombre;
    private final String puesto;
    private final String departamento;

    public Empleado(int id, String nombre, String puesto, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.departamento = departamento;
    }

    /**
     * Crea un empleado con solo el ID, para usarlo como clave de busqueda.
     *
     * Como el arbol compara unicamente por ID, basta con este objeto para
     * encontrar al empleado completo: buscar() devuelve el que si tiene
     * todos los datos.
     *
     * @param id identificador a buscar
     * @return un empleado que solo sirve para comparar
     */
    public static Empleado clave(int id) {
        return new Empleado(id, null, null, null);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPuesto() { return puesto; }
    public String getDepartamento() { return departamento; }

    /** El orden dentro del arbol lo define el ID. */
    @Override
    public int compareTo(Empleado otro) {
        return Integer.compare(this.id, otro.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Empleado)) return false;
        return this.id == ((Empleado) obj).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /** Version corta, la que usan los recorridos del arbol. */
    @Override
    public String toString() {
        if (nombre == null) return String.valueOf(id);
        return id + " (" + nombre + ")";
    }

    /** Version completa, para mostrar el resultado de una busqueda. */
    public String ficha() {
        return "ID " + id
             + " | " + nombre
             + " | " + puesto
             + " | Depto: " + departamento;
    }
}
