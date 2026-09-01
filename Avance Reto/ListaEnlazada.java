package avancereto;

/* @author friki */

/**
 * Lista simplemente enlazada generica. Es la estructura base sobre la que
 * se construyen la Pila y la Cola por composicion: ambas delegan aqui el
 * manejo de nodos y solo exponen las operaciones que les corresponden.
 *
 * No usa arreglos. Mantiene punteros a head y tail para que insertar en
 * cualquiera de los dos extremos y eliminar del inicio sean O(1), que es
 * justo lo que necesitan push/pop y enqueue/dequeue.
 */
public class ListaEnlazada<T> {

    private Nodo<T> head;
    private Nodo<T> tail;
    private int size;

    public ListaEnlazada() {
        head = null;
        tail = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    /** Insertar al final. O(1) gracias al puntero tail. Lo usa Cola.enqueue(). */
    public void insertar(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (head == null) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.next = nuevo;
            tail = nuevo;
        }
        size++;
    }

    /** Insertar al inicio. O(1). Lo usa Pila.push(). */
    public void insertarInicio(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (head == null) {
            head = nuevo;
            tail = nuevo;
        } else {
            nuevo.next = head;
            head = nuevo;
        }
        size++;
    }

    /**
     * Eliminar y devolver el elemento del inicio. O(1).
     * Lo usan Pila.pop() y Cola.dequeue(). Devuelve null si esta vacia,
     * para que quien llama decida que mensaje mostrar.
     */
    public T eliminarInicio() {
        if (head == null) return null;
        T data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return data;
    }

    /**
     * Ver el elemento del inicio sin eliminarlo. O(1).
     * Lo usan Pila.peek() y Cola.front().
     */
    public T verInicio() {
        if (head == null) return null;
        return head.data;
    }

    /** Imprime los elementos numerados desde el inicio de la lista. */
    public void mostrar() {
        if (head == null) {
            System.out.println("   (no hay tareas)");
            return;
        }
        Nodo<T> actual = head;
        int i = 1;
        while (actual != null) {
            System.out.println("   " + i + ". " + actual.data);
            actual = actual.next;
            i++;
        }
    }

    /** Expuesto dentro del paquete para recorrer la lista desde AvanceReto. */
    Nodo<T> getHead() {
        return head;
    }
}
