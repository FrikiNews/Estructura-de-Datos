package avancereto;

/* @author friki */

/**
 * Cola (FIFO: first in, first out) para las tareas programadas.
 *
 * Por que una cola aqui: las tareas rutinarias (respaldos, reportes) deben
 * respetar el orden en que fueron programadas; nadie se adelanta. Eso es
 * exactamente el comportamiento FIFO.
 *
 * Se implementa por composicion sobre ListaEnlazada: enqueue inserta al
 * final y dequeue elimina del inicio. Ambas son O(1) porque la lista
 * mantiene un puntero tail y no hay que recorrerla.
 *
 * Operaciones basicas requeridas: enqueue, dequeue y front.
 */
public class Cola<T> {

    private ListaEnlazada<T> lista;

    public Cola() {
        lista = new ListaEnlazada<>();
    }

    /** Forma al elemento al final de la cola. O(1). */
    public void enqueue(T data) {
        lista.insertar(data);
    }

    /** Atiende y devuelve el primero de la fila, o null si esta vacia. O(1). */
    public T dequeue() {
        return lista.eliminarInicio();
    }

    /** Consulta el primero de la fila sin atenderlo, o null si esta vacia. O(1). */
    public T front() {
        return lista.verInicio();
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }

    public int getSize() {
        return lista.getSize();
    }

    /** Muestra la cola desde el frente hacia el final. */
    public void mostrar() {
        lista.mostrar();
    }

    /** Expuesto dentro del paquete para recorrer la cola desde AvanceReto. */
    Nodo<T> getHead() {
        return lista.getHead();
    }
}
