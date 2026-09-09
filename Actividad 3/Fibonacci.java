package actividad3;

import java.util.Scanner;

/* @author friki */

/**
 * PROBLEMA 1: Serie de Fibonacci recursiva.
 *
 *     F(0) = 0, F(1) = 1          <- casos base
 *     F(n) = F(n-1) + F(n-2)      <- caso recursivo, para n >= 2
 *
 * Incluye dos versiones para comparar su costo: recursion pura y
 * recursion con memorizacion.
 */
public class Fibonacci {

    /** Numero maximo representable en un long: F(92) cabe, F(93) se desborda. */
    public static final int MAX_N = 92;

    /** Cuenta las llamadas recursivas para demostrar el costo del algoritmo. */
    private static long llamadas = 0;

    // ---------------- VERSION RECURSIVA PURA ----------------

    /**
     * Calcula F(n) con recursion pura. Complejidad O(2^n): cada llamada
     * genera otras dos.
     *
     * @param n posicion en la serie, debe ser mayor o igual a 0
     * @return el valor de F(n)
     */
    public static long calcular(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        llamadas++;

        // ----- CASO BASE -----
        if (n == 0) return 0;
        if (n == 1) return 1;

        // ----- CASO RECURSIVO -----
        return calcular(n - 1) + calcular(n - 2);
    }

    // ---------------- VERSION CON MEMORIA ----------------

    /**
     * Calcula F(n) guardando los resultados ya obtenidos para no repetir
     * trabajo. Complejidad O(n).
     *
     * @param n posicion en la serie, entre 0 y MAX_N
     * @return el valor de F(n)
     */
    public static long calcularConMemoria(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n no puede ser negativo: " + n);
        }
        if (n > MAX_N) {
            throw new IllegalArgumentException(
                    "n mayor a " + MAX_N + " se desborda en un long");
        }
        // -1 marca "todavia no calculado" (Fibonacci nunca es negativo)
        long[] memoria = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            memoria[i] = -1;
        }
        return calcularConMemoria(n, memoria);
    }

    /** Version interna que recibe la tabla de resultados ya calculados. */
    private static long calcularConMemoria(int n, long[] memoria) {
        llamadas++;

        // ----- CASO BASE -----
        if (n == 0) return 0;
        if (n == 1) return 1;

        if (memoria[n] != -1) return memoria[n]; // ya estaba calculado

        // ----- CASO RECURSIVO -----
        memoria[n] = calcularConMemoria(n - 1, memoria)
                   + calcularConMemoria(n - 2, memoria);
        return memoria[n];
    }

    // ---------------- APOYO ----------------

    /** Reinicia el contador de llamadas recursivas. */
    public static void reiniciarContador() {
        llamadas = 0;
    }

    /** Devuelve cuantas llamadas recursivas se hicieron. */
    public static long getLlamadas() {
        return llamadas;
    }

    /** Construye los primeros n+1 terminos de la serie como texto. */
    public static String serieHasta(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            sb.append(calcularConMemoria(i));
            if (i < n) sb.append(", ");
        }
        return sb.toString();
    }

    // ---------------- DEMOSTRACION ----------------

    /** Menu interactivo del problema 1. */
    public static void demo(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- PROBLEMA 1: SERIE DE FIBONACCI (RECURSIVA) ----");
            System.out.println("  1) Calcular F(n) con recursion pura");
            System.out.println("  2) Calcular F(n) con memorizacion");
            System.out.println("  3) Comparar el costo de las dos versiones");
            System.out.println("  4) Mostrar la serie completa hasta n");
            System.out.println("  5) Ejecutar casos de prueba");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (Main.leerEntero(sc)) {
                case 1: calcularPuro(sc); break;
                case 2: calcularMemo(sc); break;
                case 3: comparar(sc); break;
                case 4: mostrarSerie(sc); break;
                case 5: pruebas(); break;
                case 0: volver = true; break;
                default: System.out.println(">> Opcion invalida. Elige un numero del 0 al 5.");
            }
        }
    }

    private static void calcularPuro(Scanner sc) {
        int n = Main.pedirEntero(sc, "Valor de n (0 a 45, recursion pura): ", 0, 45);
        if (n > 40) {
            System.out.println(">> Aviso: con n mayor a 40 puede tardar varios segundos.");
        }
        reiniciarContador();
        long inicio = System.currentTimeMillis();
        long resultado = calcular(n);
        long ms = System.currentTimeMillis() - inicio;

        System.out.println(">> F(" + n + ") = " + resultado);
        System.out.println(">> Llamadas recursivas: " + getLlamadas());
        System.out.println(">> Tiempo: " + ms + " ms");
    }

    private static void calcularMemo(Scanner sc) {
        int n = Main.pedirEntero(sc, "Valor de n (0 a " + MAX_N + "): ", 0, MAX_N);
        reiniciarContador();
        long inicio = System.currentTimeMillis();
        long resultado = calcularConMemoria(n);
        long ms = System.currentTimeMillis() - inicio;

        System.out.println(">> F(" + n + ") = " + resultado);
        System.out.println(">> Llamadas recursivas: " + getLlamadas());
        System.out.println(">> Tiempo: " + ms + " ms");
    }

    private static void comparar(Scanner sc) {
        int n = Main.pedirEntero(sc, "Valor de n a comparar (0 a 40): ", 0, 40);

        reiniciarContador();
        long t1 = System.currentTimeMillis();
        long r1 = calcular(n);
        long ms1 = System.currentTimeMillis() - t1;
        long ll1 = getLlamadas();

        reiniciarContador();
        long t2 = System.currentTimeMillis();
        long r2 = calcularConMemoria(n);
        long ms2 = System.currentTimeMillis() - t2;
        long ll2 = getLlamadas();

        System.out.println("\n   Comparacion para n = " + n);
        System.out.println("   ---------------------------------------------");
        System.out.println("   Recursion pura    -> F(n) = " + r1);
        System.out.println("                        llamadas: " + ll1 + " | tiempo: " + ms1 + " ms");
        System.out.println("   Con memorizacion  -> F(n) = " + r2);
        System.out.println("                        llamadas: " + ll2 + " | tiempo: " + ms2 + " ms");
        System.out.println("   ---------------------------------------------");
        System.out.println("   Ambas versiones dan el mismo resultado: " + (r1 == r2));
        if (ll2 > 0) {
            System.out.println("   La version pura hizo " + (ll1 / ll2) + " veces mas llamadas.");
        }
    }

    private static void mostrarSerie(Scanner sc) {
        int n = Main.pedirEntero(sc, "Mostrar la serie hasta n (0 a " + MAX_N + "): ", 0, MAX_N);
        System.out.println(">> Serie: " + serieHasta(n));
    }

    /** Casos de prueba con valores conocidos de la serie. */
    public static void pruebas() {
        System.out.println("\n   CASOS DE PRUEBA - FIBONACCI");
        System.out.println("   ---------------------------------------------");

        int[] entradas  = {0, 1, 2, 3, 4, 5, 6, 7, 10, 15, 20};
        long[] esperados = {0, 1, 1, 2, 3, 5, 8, 13, 55, 610, 6765};

        int exitosas = 0;
        for (int i = 0; i < entradas.length; i++) {
            long obtenido = calcular(entradas[i]);
            boolean ok = (obtenido == esperados[i]);
            if (ok) exitosas++;
            System.out.println("   " + (ok ? "[OK]   " : "[FALLO]")
                    + " F(" + entradas[i] + ") = " + obtenido
                    + "  (esperado " + esperados[i] + ")");
        }

        // Las dos versiones deben coincidir siempre
        boolean coinciden = true;
        for (int i = 0; i <= 30; i++) {
            if (calcular(i) != calcularConMemoria(i)) coinciden = false;
        }
        if (coinciden) exitosas++;
        System.out.println("   " + (coinciden ? "[OK]   " : "[FALLO]")
                + " Las dos versiones coinciden de F(0) a F(30)");

        // Valor negativo: debe rechazarse
        boolean rechaza = false;
        try {
            calcular(-1);
        } catch (IllegalArgumentException e) {
            rechaza = true;
        }
        if (rechaza) exitosas++;
        System.out.println("   " + (rechaza ? "[OK]   " : "[FALLO]")
                + " Un valor negativo se rechaza con una excepcion");

        System.out.println("   ---------------------------------------------");
        System.out.println("   Resultado: " + exitosas + " de " + (entradas.length + 2)
                + " pruebas exitosas.");
    }
}
