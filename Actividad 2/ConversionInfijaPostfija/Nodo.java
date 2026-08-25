package conversioninfijapostfija;

/* @author friki */

/**
 * Nodo generico utilizado tanto por la pila de operadores
 * como por la lista ligada que almacena la expresion postfija.
 */
public class Nodo {
    char dato;
    Nodo siguiente;

    Nodo(char dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
