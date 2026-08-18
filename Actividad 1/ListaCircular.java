package listaligada;

/* @author friki */

public class ListaCircular<T> {

    private Nodo<T> head;
    private Nodo<T> tail;
    private int size;

    public ListaCircular() {
        head = null;
        tail = null;
        size = 0;
    }

    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Insertar al final; la cola siempre apunta a la cabeza
    public void insertar(T data) {
        Nodo<T> nuevo = new Nodo<>(data);
        if (head == null) {
            head = nuevo;
            tail = nuevo;
            nuevo.next = head; // se apunta a si mismo
        } else {
            tail.next = nuevo;
            nuevo.next = head; // cierra el circulo
            tail = nuevo;
        }
        size++;
    }

    // Eliminar primera ocurrencia
    public boolean eliminar(T data) {
        if (head == null) return false;

        if (head.data.equals(data)) {
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                tail.next = head; // reconectar el circulo
            }
            size--;
            return true;
        }

        Nodo<T> actual = head;
        int contados = 1;
        while (actual.next != head) {
            if (actual.next.data.equals(data)) {
                Nodo<T> aEliminar = actual.next;
                actual.next = aEliminar.next;
                if (aEliminar == tail) tail = actual;
                size--;
                return true;
            }
            actual = actual.next;
            contados++;
            if (contados > size) break; // seguridad
        }
        return false;
    }

    // Buscar, devuelve posicion o -1
    public int buscar(T data) {
        if (head == null) return -1;
        Nodo<T> actual = head;
        int indice = 0;
        do {
            if (actual.data.equals(data)) return indice;
            actual = actual.next;
            indice++;
        } while (actual != head && indice < size);
        return -1;
    }

    public void mostrar() {
        if (head == null) {
            System.out.println("  (lista vacia)");
            return;
        }
        StringBuilder sb = new StringBuilder("  ");
        Nodo<T> actual = head;
        int contados = 0;
        do {
            sb.append("[").append(actual.data).append("]");
            actual = actual.next;
            contados++;
            if (contados < size) sb.append(" -> ");
        } while (actual != head && contados < size);
        sb.append(" -> (vuelve al inicio)");
        System.out.println(sb.toString());
    }
}