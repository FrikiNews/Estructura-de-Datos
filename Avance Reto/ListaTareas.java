package avancereto;

/* @author friki */

/**
 * Lista enlazada de tareas, usada para las tareas consultables por
 * departamento.
 *
 * A diferencia de Pila y Cola (que solo tocan los extremos), aqui se
 * necesita acceso arbitrario: insertar en cualquier posicion, eliminar
 * un elemento intermedio y buscar por departamento. Por eso maneja sus
 * propios nodos en lugar de reutilizar ListaEnlazada.
 *
 * No usa arreglos: la lista crece y se encoge enlazando nodos.
 *
 * Costos: insert al final e insert(pos) son O(n) porque hay que recorrer
 * hasta la posicion; delete y find son O(n); get(pos) es O(n).
 */
public class ListaTareas {

    private Nodo<Tarea> head;
    private int size;

    public ListaTareas() {
        head = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    /** Insertar al final de la lista. */
    public void insert(Tarea t) {
        insert(t, size);
    }

    /**
     * Insertar en una posicion arbitraria (0..size). Si la posicion queda
     * fuera de rango se inserta al final, para no perder la tarea.
     */
    public void insert(Tarea t, int posicion) {
        if (posicion < 0 || posicion > size) posicion = size;
        Nodo<Tarea> nuevo = new Nodo<>(t);

        if (posicion == 0) {
            nuevo.next = head;
            head = nuevo;
        } else {
            Nodo<Tarea> anterior = head;
            for (int i = 0; i < posicion - 1; i++) {
                anterior = anterior.next;
            }
            nuevo.next = anterior.next;
            anterior.next = nuevo;
        }
        size++;
    }

    /**
     * Inserta la tarea ya en su lugar segun urgencia (mayor primero) y,
     * a igual urgencia, departamento alfabetico.
     *
     * Recorre los nodos una sola vez hasta encontrar el punto de corte,
     * asi la insercion es O(n). Se usa para armar la vista combinada del
     * menu 4 sin tener que ordenar despues.
     */
    public void insertOrdenado(Tarea t) {
        Nodo<Tarea> nuevo = new Nodo<>(t);

        if (head == null || !vaAntes(head.data, t)) {
            nuevo.next = head;
            head = nuevo;
            size++;
            return;
        }

        Nodo<Tarea> actual = head;
        while (actual.next != null && vaAntes(actual.next.data, t)) {
            actual = actual.next;
        }
        nuevo.next = actual.next;
        actual.next = nuevo;
        size++;
    }

    /**
     * true si "a" debe quedar antes que "b": primero la urgencia mas alta,
     * y entre tareas de la misma urgencia, el departamento alfabetico.
     */
    private boolean vaAntes(Tarea a, Tarea b) {
        if (a.getUrgencia() != b.getUrgencia()) {
            return a.getUrgencia() > b.getUrgencia();
        }
        return a.getDepartamento().compareToIgnoreCase(b.getDepartamento()) <= 0;
    }

    /**
     * Eliminar la primera tarea cuya descripcion coincida (sin distinguir
     * mayusculas). Devuelve false si no existe.
     */
    public boolean delete(String descripcion) {
        if (head == null || descripcion == null) return false;

        if (head.data.getDescripcion().equalsIgnoreCase(descripcion)) {
            head = head.next;
            size--;
            return true;
        }

        Nodo<Tarea> actual = head;
        while (actual.next != null) {
            if (actual.next.data.getDescripcion().equalsIgnoreCase(descripcion)) {
                actual.next = actual.next.next;
                size--;
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    /**
     * Devuelve todas las tareas de un departamento en una lista nueva.
     * La lista original no se modifica.
     */
    public ListaEnlazada<Tarea> find(String departamento) {
        ListaEnlazada<Tarea> resultado = new ListaEnlazada<>();
        if (departamento == null) return resultado;

        Nodo<Tarea> actual = head;
        while (actual != null) {
            if (actual.data.getDepartamento().equalsIgnoreCase(departamento)) {
                resultado.insertar(actual.data);
            }
            actual = actual.next;
        }
        return resultado;
    }

    /**
     * Acceso por posicion: es el "acceso aleatorio" que pide el reto para
     * consultar una tarea puntual. Devuelve null si la posicion no existe.
     */
    public Tarea get(int posicion) {
        if (posicion < 0 || posicion >= size) return null;
        Nodo<Tarea> actual = head;
        for (int i = 0; i < posicion; i++) {
            actual = actual.next;
        }
        return actual.data;
    }

    /** Imprime la lista numerada, para que el usuario vea las posiciones. */
    public void mostrar() {
        if (head == null) {
            System.out.println("   (no hay tareas)");
            return;
        }
        Nodo<Tarea> actual = head;
        int i = 0;
        while (actual != null) {
            System.out.println("   [" + i + "] " + actual.data);
            actual = actual.next;
            i++;
        }
    }

    /** Expuesto dentro del paquete para recorrer la lista desde AvanceReto. */
    Nodo<Tarea> getHead() {
        return head;
    }
}
