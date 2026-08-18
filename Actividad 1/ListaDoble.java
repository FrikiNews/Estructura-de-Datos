package listaligada;

/* @author friki */

public class ListaDoble<T> {

    private Nodo<T> head;
    private Nodo<T> tail;
    private int size;

    public ListaDoble() {
        head = null;
        tail = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Insertar al final
    public void insertar(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (head == null) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.next = nuevo;
            nuevo.prev = tail;
            tail = nuevo;
        }
        size++;
    }

    // Eliminar primera ocurrencia
    public boolean eliminar(T data) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (actual.data.equals(data)) {
                if (actual.prev != null) {
                    actual.prev.next = actual.next;
                } else {
                    head = actual.next; // era la cabeza
                }
                if (actual.next != null) {
                    actual.next.prev = actual.prev;
                } else {
                    tail = actual.prev; // era la cola
                }
                size--;
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    // Buscar, devuelve posicion o -1
    public int buscar(T data) {
        Nodo<T> actual = head;
        int indice = 0;
        while (actual != null) {
            if (actual.data.equals(data)) return indice;
            actual = actual.next;
            indice++;
        }
        return -1;
    }

    public void mostrar() {
        if (head == null) {
            System.out.println("  (lista vacia)");
            return;
        }
        StringBuilder sb = new StringBuilder("  ");
        Nodo<T> actual = head;
        while (actual != null) {
            sb.append("[").append(actual.data).append("]");
            if (actual.next != null) sb.append(" <-> ");
            actual = actual.next;
        }
        System.out.println(sb.toString());
    }

    // Recorrido inverso, aprovecha el puntero prev
    public void mostrarInverso() {
        if (tail == null) {
            System.out.println("  (lista vacia)");
            return;
        }
        StringBuilder sb = new StringBuilder("  ");
        Nodo<T> actual = tail;
        while (actual != null) {
            sb.append("[").append(actual.data).append("]");
            if (actual.prev != null) sb.append(" <-> ");
            actual = actual.prev;
        }
        System.out.println(sb.toString());
    }
}
