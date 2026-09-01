package avancereto;

/* @author friki */

/**
 * Representa una tarea del sistema. Es el tipo de dato que almacenan las
 * tres estructuras (Pila, Cola y Lista), por eso se mantiene independiente
 * de ellas: ninguna tarea sabe en que estructura vive.
 *
 * Campos:
 *   descripcion  - que hay que hacer, se usa como identificador en delete()
 *   departamento - area responsable, se usa como criterio en find()
 *   urgencia     - 1 (baja) a 5 (critica), criterio primario de ordenamiento
 *   origen       - se asigna solo al construir la vista combinada, para que
 *                  el usuario vea de que estructura proviene cada tarea
 */
public class Tarea {

    private String descripcion;
    private String departamento;
    private int urgencia;
    private String origen;

    public Tarea(String descripcion, String departamento, int urgencia) {
        this.descripcion = descripcion;
        this.departamento = departamento;
        this.urgencia = urgencia;
        this.origen = null;
    }

    public String getDescripcion() { return descripcion; }
    public String getDepartamento() { return departamento; }
    public int getUrgencia() { return urgencia; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    /**
     * Traduce la urgencia numerica a una etiqueta legible, para que el
     * usuario no tenga que recordar que significa cada numero.
     */
    public String getNivelUrgencia() {
        switch (urgencia) {
            case 5: return "CRITICA";
            case 4: return "ALTA";
            case 3: return "MEDIA";
            case 2: return "BAJA";
            default: return "MINIMA";
        }
    }

    @Override
    public String toString() {
        return "U" + urgencia + " (" + getNivelUrgencia() + ") | "
             + descripcion + " | Depto: " + departamento;
    }

    /**
     * Version extendida que ademas indica de que estructura proviene la
     * tarea. Solo la usa la vista combinada (menu 4); los menus de cada
     * estructura usan toString(), donde el origen seria redundante.
     */
    public String toStringConOrigen() {
        if (origen == null) return toString();
        return toString() + " | Origen: " + origen;
    }
}
