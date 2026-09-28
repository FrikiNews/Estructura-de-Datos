package actividad4;

/* @author friki */

/**
 * Nodo de un arbol binario.
 *
 * Guarda un dato y los enlaces a sus dos hijos. A diferencia de una lista,
 * donde cada nodo apunta a uno solo, aqui cada nodo puede apuntar a dos,
 * y eso es lo que permite dividir la busqueda en dos caminos posibles.
 *
 * Los atributos son de paquete (sin private) a proposito, para que
 * ArbolBinario manipule los enlaces directamente sin el ruido de getters.
 */
public class Nodo<T> {

    T dato;
    Nodo<T> izquierdo;
    Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    public T getDato() { return dato; }
    public Nodo<T> getIzquierdo() { return izquierdo; }
    public Nodo<T> getDerecho() { return derecho; }

    /** Un nodo es hoja cuando no tiene ningun hijo. */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
