package conversioninfijapostfija;

/* @author friki */

/**
 * Pila de caracteres implementada con lista ligada (sin java.util.Stack).
 */
public class Pila {
    private Nodo tope;

    Pila() {
        tope = null;
    }

    boolean estaVacia() {
        return tope == null;
    }

    void apilar(char valor) {
        Nodo nuevo = new Nodo(valor);
        nuevo.siguiente = tope;
        tope = nuevo;
    }

    char desapilar() {
        char valor = tope.dato;
        tope = tope.siguiente;
        return valor;
    }

    char verTope() {
        return tope.dato;
    }
}
