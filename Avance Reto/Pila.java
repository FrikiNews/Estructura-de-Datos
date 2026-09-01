package avancereto;

/* @author friki */

/**
 * Pila (LIFO: last in, first out) para las tareas urgentes.
 *
 * Por que una pila aqui: cuando algo se rompe, lo que acaba de reportarse
 * suele ser lo mas apremiante, asi que la ultima tarea que entra es la
 * primera que se atiende. Eso es exactamente el comportamiento LIFO.
 *
 * Se implementa por composicion sobre ListaEnlazada: push y pop trabajan
 * ambos sobre el inicio de la lista, por lo que las dos son O(1).
 *
 * Operaciones basicas requeridas: push, pop y peek.
 */
public class Pila<T> {

    private ListaEnlazada<T> lista;

    public Pila() {
        lista = new ListaEnlazada<>();
    }

    /** Apila un elemento en la cima. O(1). */
    public void push(T data) {
        lista.insertarInicio(data);
    }

    /** Saca y devuelve el elemento de la cima, o null si esta vacia. O(1). */
    public T pop() {
        return lista.eliminarInicio();
    }

    /** Consulta la cima sin sacarla, o null si esta vacia. O(1). */
    public T peek() {
        return lista.verInicio();
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }

    public int getSize() {
        return lista.getSize();
    }

    /** Muestra la pila desde la cima hacia la base. */
    public void mostrar() {
        lista.mostrar();
    }

    /** Expuesto dentro del paquete para recorrer la pila desde AvanceReto. */
    Nodo<T> getHead() {
        return lista.getHead();
    }
}
