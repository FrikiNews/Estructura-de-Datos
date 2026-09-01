package listaligada;

/* @author friki */

/**
 * Lista ligada que almacena la expresion postfija resultante de
 * ConversionInfijaPostfija. Reutiliza el mismo Nodo generico del resto
 * de la actividad; se construye agregando nodos al final (no se usa
 * String ni StringBuilder para representar la expresion).
 */
public class ListaPostfija {
    private Nodo<Character> cabeza;
    private Nodo<Character> cola;

    ListaPostfija() {
        cabeza = null;
        cola = null;
    }

    void agregar(char valor) {
        Nodo<Character> nuevo = new Nodo<>(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.next = nuevo;
            cola = nuevo;
        }
    }

    void imprimir() {
        Nodo<Character> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.data.charValue());
            if (actual.next != null) {
                System.out.print(' ');
            }
            actual = actual.next;
        }
        System.out.println();
    }
}
