package listaligada;

/* @author friki */

public class Nodo<T> {
    T data;
    Nodo<T> next;
    Nodo<T> prev; // solo lo usa la lista doble

    public Nodo(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
