package actividad3;

import java.util.Scanner;

/* @author friki */

/**
 * PROBLEMA 2: Suma de subconjuntos (Subset Sum).
 *
 * Para cada elemento solo hay dos opciones, incluirlo o dejarlo fuera:
 *
 *     existe(i, falta) = existe(i-1, falta)               <- excluir
 *                     OR existe(i-1, falta - conjunto[i]) <- incluir
 *
 * Casos base: falta == 0 devuelve true, i < 0 devuelve false. El orden
 * importa, porque la suma puede completarse justo al agotar los elementos.
 *
 * Complejidad O(2^n).
 */
public class SubsetSum {

    /** Cuenta las llamadas recursivas para evidenciar el costo del algoritmo. */
    private static long llamadas = 0;

    // ---------------- ALGORITMO PRINCIPAL ----------------

    /**
     * Indica si existe un subconjunto que sume el objetivo.
     *
     * @param conjunto elementos disponibles, puede incluir negativos
     * @param objetivo suma que se desea alcanzar
     * @return true si algun subconjunto suma exactamente el objetivo
     */
    public static boolean existeSubconjunto(int[] conjunto, int objetivo) {
        if (conjunto == null) {
            throw new IllegalArgumentException("El conjunto no puede ser nulo");
        }
        return existeSubconjunto(conjunto, conjunto.length - 1, objetivo);
    }

    /**
     * Version recursiva interna.
     *
     * @param conjunto elementos disponibles
     * @param i indice del ultimo elemento sobre el que falta decidir
     * @param falta cuanto falta para completar el objetivo
     */
    private static boolean existeSubconjunto(int[] conjunto, int i, int falta) {
        llamadas++;

        // ----- CASO BASE 1: ya se junto la suma buscada -----
        if (falta == 0) return true;

        // ----- CASO BASE 2: se agotaron los elementos -----
        if (i < 0) return false;

        // ----- CASO RECURSIVO: excluir o incluir el elemento i -----
        boolean sinElemento = existeSubconjunto(conjunto, i - 1, falta);
        if (sinElemento) return true;

        return existeSubconjunto(conjunto, i - 1, falta - conjunto[i]);
    }

    // ---------------- RECONSTRUCCION DEL SUBCONJUNTO ----------------

    /**
     * Igual que existeSubconjunto, pero registra cuales elementos forman
     * la solucion.
     *
     * @param conjunto elementos disponibles
     * @param objetivo suma buscada
     * @param seleccion arreglo del mismo tamano que el conjunto; al terminar,
     *                  seleccion[i] es true si el elemento i forma parte
     * @return true si se encontro un subconjunto
     */
    public static boolean encontrarSubconjunto(int[] conjunto, int objetivo, boolean[] seleccion) {
        if (conjunto == null || seleccion == null) {
            throw new IllegalArgumentException("El conjunto y la seleccion no pueden ser nulos");
        }
        if (seleccion.length != conjunto.length) {
            throw new IllegalArgumentException("La seleccion debe medir lo mismo que el conjunto");
        }
        for (int i = 0; i < seleccion.length; i++) {
            seleccion[i] = false;
        }
        return encontrarSubconjunto(conjunto, conjunto.length - 1, objetivo, seleccion);
    }

    /** Version recursiva interna que marca los elementos elegidos. */
    private static boolean encontrarSubconjunto(int[] conjunto, int i, int falta, boolean[] seleccion) {
        // ----- CASOS BASE -----
        if (falta == 0) return true;
        if (i < 0) return false;

        // ----- CASO RECURSIVO -----
        if (encontrarSubconjunto(conjunto, i - 1, falta, seleccion)) {
            return true;
        }

        // Se marca antes de bajar y se desmarca si la rama falla: la misma
        // vuelta atras del backtracking del problema 3.
        seleccion[i] = true;
        if (encontrarSubconjunto(conjunto, i - 1, falta - conjunto[i], seleccion)) {
            return true;
        }
        seleccion[i] = false;
        return false;
    }

    // ---------------- APOYO ----------------

    public static void reiniciarContador() {
        llamadas = 0;
    }

    public static long getLlamadas() {
        return llamadas;
    }

    /** Devuelve el conjunto en formato { a, b, c }. */
    public static String conjuntoATexto(int[] conjunto) {
        StringBuilder sb = new StringBuilder("{ ");
        for (int i = 0; i < conjunto.length; i++) {
            sb.append(conjunto[i]);
            if (i < conjunto.length - 1) sb.append(", ");
        }
        return sb.append(" }").toString();
    }

    /** Devuelve solo los elementos marcados en la seleccion. */
    public static String subconjuntoATexto(int[] conjunto, boolean[] seleccion) {
        StringBuilder sb = new StringBuilder("{ ");
        boolean primero = true;
        int suma = 0;
        for (int i = 0; i < conjunto.length; i++) {
            if (seleccion[i]) {
                if (!primero) sb.append(", ");
                sb.append(conjunto[i]);
                suma += conjunto[i];
                primero = false;
            }
        }
        sb.append(" }  (suma = ").append(suma).append(")");
        return sb.toString();
    }

    // ---------------- DEMOSTRACION ----------------

    /** Menu interactivo del problema 2. */
    public static void demo(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- PROBLEMA 2: SUMA DE SUBCONJUNTOS (SUBSET SUM) ----");
            System.out.println("  1) Capturar un conjunto y buscar un objetivo");
            System.out.println("  2) Usar un conjunto de ejemplo");
            System.out.println("  3) Ejecutar casos de prueba");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (Main.leerEntero(sc)) {
                case 1: capturarYResolver(sc); break;
                case 2: ejemplo(sc); break;
                case 3: pruebas(); break;
                case 0: volver = true; break;
                default: System.out.println(">> Opcion invalida. Elige un numero del 0 al 3.");
            }
        }
    }

    private static void capturarYResolver(Scanner sc) {
        int n = Main.pedirEntero(sc, "Cuantos elementos tendra el conjunto (1 a 20): ", 1, 20);
        int[] conjunto = new int[n];
        for (int i = 0; i < n; i++) {
            conjunto[i] = Main.pedirEntero(sc, "  Elemento " + (i + 1) + ": ",
                    Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        System.out.print("Suma objetivo: ");
        int objetivo = Main.leerEntero(sc);
        resolver(conjunto, objetivo);
    }

    private static void ejemplo(Scanner sc) {
        int[] conjunto = {3, 34, 4, 12, 5, 2};
        System.out.println(">> Conjunto de ejemplo: " + conjuntoATexto(conjunto));
        System.out.print("Suma objetivo (9, 26 y 39 si tienen solucion; 30 y 61 no): ");
        int objetivo = Main.leerEntero(sc);
        resolver(conjunto, objetivo);
    }

    /** Ejecuta la busqueda y muestra el resultado con detalle. */
    private static void resolver(int[] conjunto, int objetivo) {
        System.out.println("\n   Conjunto: " + conjuntoATexto(conjunto));
        System.out.println("   Objetivo: " + objetivo);

        reiniciarContador();
        long inicio = System.currentTimeMillis();
        boolean existe = existeSubconjunto(conjunto, objetivo);
        long ms = System.currentTimeMillis() - inicio;

        if (existe) {
            boolean[] seleccion = new boolean[conjunto.length];
            encontrarSubconjunto(conjunto, objetivo, seleccion);
            System.out.println("   >> SI existe un subconjunto que suma " + objetivo);
            System.out.println("   >> Subconjunto encontrado: "
                    + subconjuntoATexto(conjunto, seleccion));
        } else {
            System.out.println("   >> NO existe ningun subconjunto que sume " + objetivo);
        }
        System.out.println("   >> Llamadas recursivas: " + getLlamadas()
                + " | tiempo: " + ms + " ms");
    }

    /** Casos de prueba, incluidos los limite. */
    public static void pruebas() {
        System.out.println("\n   CASOS DE PRUEBA - SUBSET SUM");
        System.out.println("   ---------------------------------------------");

        int exitosas = 0;
        int totales = 0;

        int[] a = {3, 34, 4, 12, 5, 2};
        totales++; exitosas += revisar("Suma alcanzable (9 = 4+5)", existeSubconjunto(a, 9), true);
        totales++; exitosas += revisar("Suma alcanzable (39 = 34+3+2)", existeSubconjunto(a, 39), true);
        totales++; exitosas += revisar("Suma alcanzable (26 = 3+4+12+5+2)", existeSubconjunto(a, 26), true);
        // Sin el 34 el maximo posible es 26, y con el 34 ya se rebasa: el 30 queda en medio
        totales++; exitosas += revisar("Suma no alcanzable (30, queda en el hueco)",
                existeSubconjunto(a, 30), false);
        totales++; exitosas += revisar("Suma mayor al total (61)", existeSubconjunto(a, 61), false);
        totales++; exitosas += revisar("Suma del conjunto completo (60)", existeSubconjunto(a, 60), true);

        totales++; exitosas += revisar("Objetivo 0 con el subconjunto vacio", existeSubconjunto(a, 0), true);

        int[] vacio = {};
        totales++; exitosas += revisar("Conjunto vacio con objetivo 0", existeSubconjunto(vacio, 0), true);
        totales++; exitosas += revisar("Conjunto vacio con objetivo 5", existeSubconjunto(vacio, 5), false);

        int[] uno = {7};
        totales++; exitosas += revisar("Un solo elemento que si coincide", existeSubconjunto(uno, 7), true);
        totales++; exitosas += revisar("Un solo elemento que no coincide", existeSubconjunto(uno, 8), false);

        int[] negativos = {-3, 5, 8, -2};
        totales++; exitosas += revisar("Con negativos (2 = -3+5)", existeSubconjunto(negativos, 2), true);
        totales++; exitosas += revisar("Con negativos (-5 = -3-2)", existeSubconjunto(negativos, -5), true);
        totales++; exitosas += revisar("Con negativos, objetivo inalcanzable (100)",
                existeSubconjunto(negativos, 100), false);

        int[] repetidos = {2, 2, 2};
        totales++; exitosas += revisar("Elementos repetidos (4 = 2+2)", existeSubconjunto(repetidos, 4), true);
        totales++; exitosas += revisar("Elementos repetidos (7 inalcanzable)",
                existeSubconjunto(repetidos, 7), false);

        // El subconjunto reconstruido debe sumar realmente el objetivo
        boolean[] seleccion = new boolean[a.length];
        boolean hallado = encontrarSubconjunto(a, 9, seleccion);
        int suma = 0;
        for (int i = 0; i < a.length; i++) {
            if (seleccion[i]) suma += a[i];
        }
        totales++; exitosas += revisar("El subconjunto reconstruido suma el objetivo",
                hallado && suma == 9, true);

        System.out.println("   ---------------------------------------------");
        System.out.println("   Resultado: " + exitosas + " de " + totales + " pruebas exitosas.");
    }

    /** Compara el resultado obtenido contra el esperado e imprime el veredicto. */
    private static int revisar(String descripcion, boolean obtenido, boolean esperado) {
        boolean ok = (obtenido == esperado);
        System.out.println("   " + (ok ? "[OK]   " : "[FALLO]") + " " + descripcion
                + "  ->  " + obtenido);
        return ok ? 1 : 0;
    }
}
