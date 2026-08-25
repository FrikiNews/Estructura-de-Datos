package listaligada;

/* @author friki */

/**
 * Pila (LIFO) construida reutilizando la ListaSimple de la Actividad 1.
 * Push y Pop operan sobre el inicio de la lista para que ambas
 * operaciones sean O(1).
 */
public class Pila<T> {

    private ListaSimple<T> lista;

    public Pila() {
        lista = new ListaSimple<>();
    }

    public void push(T data) {
        lista.insertarInicio(data);
    }

    public T pop() {
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
