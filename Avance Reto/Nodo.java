package avancereto;

/* @author friki */

/**
 * Nodo generico: la unidad minima de almacenamiento del sistema.
 *
 * Guarda un dato y un enlace al siguiente nodo. Es lo que permite que
 * todas las estructuras crezcan dinamicamente sin arreglos: no se reserva
 * espacio por adelantado, cada nodo se crea cuando se necesita y se
 * enlaza al que le sigue.
 *
 * Los atributos son de paquete (sin private) a proposito, para que las
 * estructuras del mismo paquete manipulen los enlaces directamente sin
 * el ruido de getters y setters.
 */
public class Nodo<T> {

    T data;
    Nodo<T> next;

    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }
}
