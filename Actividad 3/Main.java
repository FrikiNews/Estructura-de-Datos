package actividad3;

import java.util.Scanner;

/* @author friki */

/**
 * ACTIVIDAD 3: RECURSIVIDAD Y ALGORITMOS DE DIVIDE Y VENCERAS
 *
 * Cada problema vive en su propia clase:
 *
 *   Problema 1 -> Fibonacci.java   Serie de Fibonacci recursiva.
 *   Problema 2 -> SubsetSum.java   Suma de subconjuntos recursiva.
 *   Problema 3 -> Sudoku.java      Backtracking para resolver un Sudoku.
 *
 * @author friki
 */
public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n==================================================");
        System.out.println("   ACTIVIDAD 3: RECURSIVIDAD Y BACKTRACKING");
        System.out.println("==================================================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n=============== MENU PRINCIPAL ===============");
            System.out.println("  1) Problema 1: Serie de Fibonacci recursiva");
            System.out.println("  2) Problema 2: Suma de subconjuntos (Subset Sum)");
            System.out.println("  3) Problema 3: Sudoku con backtracking");
            System.out.println("  4) Ejecutar TODOS los casos de prueba");
            System.out.println("  0) Salir");
            System.out.println("----------------------------------------------");
            System.out.print("Selecciona una opcion: ");

            switch (leerEntero(sc)) {
                case 1: Fibonacci.demo(sc); break;
                case 2: SubsetSum.demo(sc); break;
                case 3: Sudoku.demo(sc); break;
                case 4: todasLasPruebas(); break;
                case 0:
                    salir = true;
                    System.out.println("\nSaliendo del programa. Hasta luego.");
                    break;
                default:
                    System.out.println(">> Opcion invalida. Elige un numero del 0 al 4.");
            }
        }
        sc.close();
    }

    /** Corre las pruebas de los tres problemas, una tras otra. */
    private static void todasLasPruebas() {
        System.out.println("\n==========================================================");
        System.out.println("     CASOS DE PRUEBA DE LA ACTIVIDAD 3");
        System.out.println("==========================================================");
        Fibonacci.pruebas();
        SubsetSum.pruebas();
        Sudoku.pruebas();
        System.out.println("\n==========================================================");
        System.out.println("  Fin de las pruebas.");
        System.out.println("==========================================================");
    }

    // ---------------- AUXILIARES DE ENTRADA ----------------

    /**
     * Lee un entero de la consola. Si no es un numero devuelve
     * Integer.MIN_VALUE, que ningun menu acepta como opcion valida.
     */
    public static int leerEntero(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Pide un entero dentro de un rango, repitiendo hasta que sea valido.
     *
     * @param mensaje texto que se muestra al usuario
     * @param minimo valor minimo aceptado, inclusive
     * @param maximo valor maximo aceptado, inclusive
     */
    public static int pedirEntero(Scanner sc, String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor < minimo || valor > maximo) {
                    System.out.println(">> Debe ser un numero entre " + minimo + " y " + maximo + ".");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println(">> Eso no es un numero entero valido.");
            }
        }
    }
}
