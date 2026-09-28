package actividad4;

/* @author friki */

/**
 * Arbol binario de busqueda (ABB) generico.
 *
 * La regla que ordena el arbol es: para cualquier nodo, todo lo que esta
 * en su subarbol izquierdo es menor, y todo lo que esta en el derecho es
 * mayor. Esa invariante es la que hace que buscar sea rapido, porque en
 * cada nodo se descarta la mitad de lo que queda.
 *
 * Costos (con n elementos):
 *   - Arbol equilibrado: insertar, buscar y eliminar son O(log n)
 *   - Arbol degenerado (datos insertados ya ordenados): O(n)
 *
 * Funciona con cualquier tipo que implemente Comparable, por lo que sirve
 * igual para numeros que para los empleados del caso practico.
 *
 * @param <T> tipo de dato almacenado, debe ser comparable
 */
public class ArbolBinario<T extends Comparable<T>> {

    private Nodo<T> raiz;
    private int cantidad;

    /** Comparaciones de la ultima busqueda; sirve para medir la eficiencia. */
    private int comparaciones;

    public ArbolBinario() {
        raiz = null;
        cantidad = 0;
        comparaciones = 0;
    }

    public boolean estaVacio() { return raiz == null; }
    public int getCantidad() { return cantidad; }
    public int getComparaciones() { return comparaciones; }
    public Nodo<T> getRaiz() { return raiz; }

    // ---------------- INSERCION ----------------

    /**
     * Inserta un dato respetando el orden del arbol. Los duplicados se
     * rechazan, porque el ID de un empleado debe ser unico.
     *
     * @param dato elemento a insertar
     * @return true si se inserto, false si ya existia
     */
    public boolean insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede insertar un dato nulo");
        }
        int antes = cantidad;
        raiz = insertarRec(raiz, dato);
        return cantidad > antes;
    }

    private Nodo<T> insertarRec(Nodo<T> nodo, T dato) {
        // Caso base: se llego a un hueco libre, aqui va el nuevo nodo
        if (nodo == null) {
            cantidad++;
            return new Nodo<>(dato);
        }

        int comparacion = dato.compareTo(nodo.dato);

        if (comparacion < 0) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, dato);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRec(nodo.derecho, dato);
        }
        // comparacion == 0 es un duplicado: no se inserta y no se cuenta

        return nodo;
    }

    // ---------------- BUSQUEDA ----------------

    /**
     * Busca un dato y devuelve el elemento almacenado en el arbol.
     *
     * Devolver el elemento guardado (y no solo un booleano) es lo que
     * permite buscar un empleado por su ID y recuperar su nombre y puesto.
     *
     * Deja en getComparaciones() cuantos nodos hubo que visitar.
     *
     * @param dato elemento buscado
     * @return el elemento almacenado, o null si no esta en el arbol
     */
    public T buscar(T dato) {
        comparaciones = 0;
        if (dato == null) return null;
        return buscarRec(raiz, dato);
    }

    private T buscarRec(Nodo<T> nodo, T dato) {
        // Caso base: se llego al final de una rama sin encontrarlo
        if (nodo == null) return null;

        comparaciones++;
        int comparacion = dato.compareTo(nodo.dato);

        if (comparacion == 0) return nodo.dato;

        // Solo se sigue por un lado: el otro subarbol queda descartado
        if (comparacion < 0) return buscarRec(nodo.izquierdo, dato);
        return buscarRec(nodo.derecho, dato);
    }

    /** Indica si el dato existe, sin alterar el contador de comparaciones. */
    public boolean contiene(T dato) {
        if (dato == null) return false;
        Nodo<T> actual = raiz;
        while (actual != null) {
            int comparacion = dato.compareTo(actual.dato);
            if (comparacion == 0) return true;
            actual = (comparacion < 0) ? actual.izquierdo : actual.derecho;
        }
        return false;
    }

    // ---------------- ELIMINACION ----------------

    /**
     * Elimina un dato del arbol manteniendo el orden.
     *
     * Hay tres situaciones posibles:
     *   1. El nodo es hoja: se quita directamente.
     *   2. Tiene un solo hijo: el hijo ocupa su lugar.
     *   3. Tiene dos hijos: se reemplaza por su sucesor inorden (el menor
     *      del subarbol derecho) y se elimina ese sucesor de abajo. Se usa
     *      ese valor porque es el unico que puede ocupar la posicion sin
     *      romper la regla de orden del arbol.
     *
     * @param dato elemento a eliminar
     * @return true si se elimino, false si no estaba
     */
    public boolean eliminar(T dato) {
        if (dato == null || !contiene(dato)) return false;
        raiz = eliminarRec(raiz, dato);
        cantidad--;
        return true;
    }

    private Nodo<T> eliminarRec(Nodo<T> nodo, T dato) {
        if (nodo == null) return null;

        int comparacion = dato.compareTo(nodo.dato);

        if (comparacion < 0) {
            nodo.izquierdo = eliminarRec(nodo.izquierdo, dato);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRec(nodo.derecho, dato);
        } else {
            // Casos 1 y 2: sin hijos o con uno solo
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;

            // Caso 3: dos hijos, se sube el sucesor inorden
            Nodo<T> sucesor = minimo(nodo.derecho);
            nodo.dato = sucesor.dato;
            nodo.derecho = eliminarRec(nodo.derecho, sucesor.dato);
        }
        return nodo;
    }

    /** Devuelve el nodo con el valor mas pequeno del subarbol. */
    private Nodo<T> minimo(Nodo<T> nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    /** Devuelve el dato mas pequeno del arbol, o null si esta vacio. */
    public T getMinimo() {
        if (raiz == null) return null;
        return minimo(raiz).dato;
    }

    /** Devuelve el dato mas grande del arbol, o null si esta vacio. */
    public T getMaximo() {
        if (raiz == null) return null;
        Nodo<T> actual = raiz;
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.dato;
    }

    // ---------------- RECORRIDOS ----------------

    /**
     * Recorrido PREORDEN: raiz, izquierdo, derecho.
     *
     * Visita el nodo antes que sus hijos. Sirve para copiar o guardar un
     * arbol, porque al reinsertar los datos en este orden se reconstruye
     * con la misma forma.
     *
     * @return los datos separados por comas
     */
    public String preorden() {
        StringBuilder sb = new StringBuilder();
        preordenRec(raiz, sb);
        return sb.toString().trim();
    }

    private void preordenRec(Nodo<T> nodo, StringBuilder sb) {
        if (nodo == null) return;
        agregar(sb, nodo.dato);
        preordenRec(nodo.izquierdo, sb);
        preordenRec(nodo.derecho, sb);
    }

    /**
     * Recorrido INORDEN: izquierdo, raiz, derecho.
     *
     * En un arbol binario de busqueda devuelve los datos ordenados de
     * menor a mayor. Es el recorrido mas util del caso practico, porque
     * lista a los empleados por ID sin tener que ordenarlos aparte.
     *
     * @return los datos separados por comas, en orden ascendente
     */
    public String inorden() {
        StringBuilder sb = new StringBuilder();
        inordenRec(raiz, sb);
        return sb.toString().trim();
    }

    private void inordenRec(Nodo<T> nodo, StringBuilder sb) {
        if (nodo == null) return;
        inordenRec(nodo.izquierdo, sb);
        agregar(sb, nodo.dato);
        inordenRec(nodo.derecho, sb);
    }

    /**
     * Recorrido POSTORDEN: izquierdo, derecho, raiz.
     *
     * Visita el nodo despues que sus hijos. Es el orden que se usa para
     * liberar o borrar un arbol completo, porque nunca elimina un nodo
     * antes que a sus descendientes.
     *
     * @return los datos separados por comas
     */
    public String postorden() {
        StringBuilder sb = new StringBuilder();
        postordenRec(raiz, sb);
        return sb.toString().trim();
    }

    private void postordenRec(Nodo<T> nodo, StringBuilder sb) {
        if (nodo == null) return;
        postordenRec(nodo.izquierdo, sb);
        postordenRec(nodo.derecho, sb);
        agregar(sb, nodo.dato);
    }

    private void agregar(StringBuilder sb, T dato) {
        if (sb.length() > 0) sb.append(", ");
        sb.append(dato);
    }

    // ---------------- MEDIDAS DEL ARBOL ----------------

    /**
     * Altura del arbol: el numero de niveles desde la raiz hasta la hoja
     * mas profunda. Un arbol vacio mide 0.
     *
     * La altura es lo que determina el costo de buscar: en el peor caso,
     * una busqueda visita tantos nodos como niveles tenga el arbol.
     */
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(Nodo<T> nodo) {
        if (nodo == null) return 0;
        int izquierda = alturaRec(nodo.izquierdo);
        int derecha = alturaRec(nodo.derecho);
        return 1 + Math.max(izquierda, derecha);
    }

    /** Cuenta las hojas, es decir los nodos sin hijos. */
    public int contarHojas() {
        return contarHojasRec(raiz);
    }

    private int contarHojasRec(Nodo<T> nodo) {
        if (nodo == null) return 0;
        if (nodo.esHoja()) return 1;
        return contarHojasRec(nodo.izquierdo) + contarHojasRec(nodo.derecho);
    }

    /**
     * Altura minima teorica para la cantidad actual de datos, es decir la
     * que tendria el arbol si estuviera perfectamente equilibrado.
     * Comparada con altura() indica que tan desequilibrado esta.
     */
    public int alturaIdeal() {
        int niveles = 0;
        int capacidad = 0;
        int nivelActual = 1;
        while (capacidad < cantidad) {
            capacidad += nivelActual;
            nivelActual *= 2;
            niveles++;
        }
        return niveles;
    }

    /** Vacia el arbol por completo. */
    public void vaciar() {
        raiz = null;
        cantidad = 0;
    }

    // ---------------- REPRESENTACION VISUAL ----------------

    /**
     * Dibuja el arbol de lado, con la raiz a la izquierda y los niveles
     * desplazados. Se muestra el subarbol derecho arriba y el izquierdo
     * abajo, que es como se lee mas naturalmente en consola.
     */
    public String dibujar() {
        if (raiz == null) return "   (arbol vacio)";
        StringBuilder sb = new StringBuilder();
        dibujarRec(raiz, "", sb);
        return sb.toString();
    }

    private void dibujarRec(Nodo<T> nodo, String sangria, StringBuilder sb) {
        if (nodo == null) return;
        dibujarRec(nodo.derecho, sangria + "      ", sb);
        sb.append("   ").append(sangria).append(nodo.dato).append('\n');
        dibujarRec(nodo.izquierdo, sangria + "      ", sb);
    }
}
