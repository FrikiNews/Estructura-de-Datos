package listaligada;

/* @author friki */

public class ListaSimple<T> {

    private Nodo<T> head;
    private Nodo<T> tail;
    private int size;

    public ListaSimple() {
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
            tail = nuevo;
        }
        size++;
    }

    // Eliminar primera ocurrencia
    public boolean eliminar(T data) {
        if (head == null) return false;

        if (head.data.equals(data)) {
            head = head.next;
            if (head == null) tail = null;
            size--;
            return true;
        }

        Nodo<T> actual = head;
        while (actual.next != null) {
            if (actual.next.data.equals(data)) {
                Nodo<T> aEliminar = actual.next;
                actual.next = aEliminar.next;
                if (aEliminar == tail) tail = actual;
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
            if (actual.next != null) sb.append(" -> ");
            actual = actual.next;
        }
        System.out.println(sb.toString());
    }
}