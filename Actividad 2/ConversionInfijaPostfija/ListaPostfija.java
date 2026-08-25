package conversioninfijapostfija;

/* @author friki */

/**
 * Lista ligada que almacena la expresion postfija resultante.
 * Se construye agregando nodos al final (no se usa String/StringBuilder).
 */
public class ListaPostfija {
    private Nodo cabeza;
    private Nodo cola;

    ListaPostfija() {
        cabeza = null;
        cola = null;
    }

    void agregar(char valor) {
        Nodo nuevo = new Nodo(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }
    }

    void imprimir() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.print(actual.dato);
            if (actual.siguiente != null) {
                System.out.print(' ');
            }
            actual = actual.siguiente;
        }
        System.out.println();
    }
}
