package listaligada;

/* @author friki */

/**
 * Cola (FIFO) construida reutilizando la ListaSimple de la Actividad 1.
 * Enqueue inserta al final y Dequeue remueve del inicio, ambas O(1)
 * gracias a que la lista mantiene un puntero tail.
 */
public class Cola<T> {

    private ListaSimple<T> lista;

    public Cola() {
        lista = new ListaSimple<>();
    }

    public void enqueue(T data) {
        lista.insertar(data);
    }

    public T dequeue() {
        return lista.eliminarInicio();
    }

    public T peek() {
        return lista.verInicio();
    }

    public boolean isEmpty() {
        return lista.isEmpty();
    }

    public int getSize() {
        return lista.getSize();
    }

    public void mostrar() {
        lista.mostrar();
    }
}
