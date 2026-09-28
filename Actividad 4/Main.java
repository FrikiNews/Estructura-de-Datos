package actividad4;

import java.util.Scanner;

/* @author friki */

/**
 * ACTIVIDAD 4: ARBOLES BINARIOS
 *
 * Clases del proyecto:
 *   Nodo             -> nodo con un dato y sus dos hijos.
 *   ArbolBinario     -> insertar, eliminar, buscar y los tres recorridos.
 *   Empleado         -> modelo del caso practico, comparable por ID.
 *   GestionEmpleados -> caso practico y comparacion contra busqueda secuencial.
 *
 * @author friki
 */
public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n==================================================");
        System.out.println("      ACTIVIDAD 4: ARBOLES BINARIOS");
        System.out.println("==================================================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n=============== MENU PRINCIPAL ===============");
            System.out.println("  1) Operaciones del arbol binario");
            System.out.println("  2) Caso practico: gestion de empleados");
            System.out.println("  3) Ejecutar casos de prueba");
            System.out.println("  0) Salir");
            System.out.println("----------------------------------------------");
            System.out.print("Selecciona una opcion: ");

            switch (leerEntero(sc)) {
                case 1: menuArbol(); break;
                case 2: GestionEmpleados.demo(sc); break;
                case 3: Pruebas.ejecutar(); break;
                case 0:
                    salir = true;
                    System.out.println("\nSaliendo del programa. Hasta luego.");
                    break;
                default:
                    System.out.println(">> Opcion invalida. Elige un numero del 0 al 3.");
            }
        }
        sc.close();
    }

    // ---------------- MENU DEL ARBOL ----------------

    /** Arbol de enteros que se opera desde el menu. */
    private static ArbolBinario<Integer> arbol = new ArbolBinario<>();

    private static void menuArbol() {
        if (arbol.estaVacio()) {
            cargarEjemplo();
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n---- OPERACIONES DEL ARBOL BINARIO ----");
            System.out.println("Nodos: " + arbol.getCantidad()
                    + " | altura: " + arbol.altura()
                    + " | hojas: " + arbol.contarHojas());
            System.out.println("  1) Insertar un numero");
            System.out.println("  2) Eliminar un numero");
            System.out.println("  3) Buscar un numero");
            System.out.println("  4) Ver los tres recorridos");
            System.out.println("  5) Ver el arbol dibujado");
            System.out.println("  6) Ver datos del arbol");
            System.out.println("  7) Vaciar y volver a cargar el ejemplo");
            System.out.println("  0) Volver al menu principal");
            System.out.print("Selecciona una opcion: ");

            switch (leerEntero(sc)) {
                case 1: insertar(); break;
                case 2: eliminar(); break;
                case 3: buscar(); break;
                case 4: recorridos(); break;
                case 5:
                    System.out.println("\n   ARBOL (raiz a la izquierda)");
                    System.out.print(arbol.dibujar());
                    break;
                case 6: datos(); break;
                case 7:
                    arbol.vaciar();
                    cargarEjemplo();
                    System.out.println(">> Arbol recargado con el ejemplo.");
                    break;
                case 0: volver = true; break;
                default: System.out.println(">> Opcion invalida. Elige un numero del 0 al 7.");
            }
        }
    }

    /**
     * Carga un arbol de ejemplo. Los valores estan puestos en un orden que
     * produce un arbol equilibrado y facil de leer al dibujarlo.
     */
    private static void cargarEjemplo() {
        int[] valores = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65};
        for (int i = 0; i < valores.length; i++) {
            arbol.insertar(valores[i]);
        }
    }

    private static void insertar() {
        int valor = pedirEntero(sc, "   Numero a insertar: ",
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (arbol.insertar(valor)) {
            System.out.println(">> Insertado. El arbol quedo: " + arbol.inorden());
        } else {
            System.out.println(">> El " + valor + " ya estaba en el arbol (no se aceptan duplicados).");
        }
    }

    private static void eliminar() {
        if (arbol.estaVacio()) {
            System.out.println(">> El arbol esta vacio.");
            return;
        }
        int valor = pedirEntero(sc, "   Numero a eliminar: ",
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("   Antes:   " + arbol.inorden());
        if (arbol.eliminar(valor)) {
            System.out.println(">> Eliminado.");
            System.out.println("   Despues: " + arbol.inorden());
            System.out.print(arbol.dibujar());
        } else {
            System.out.println(">> El " + valor + " no esta en el arbol.");
        }
    }

    private static void buscar() {
        int valor = pedirEntero(sc, "   Numero a buscar: ",
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        Integer encontrado = arbol.buscar(valor);
        if (encontrado == null) {
            System.out.println(">> El " + valor + " NO esta en el arbol.");
        } else {
            System.out.println(">> El " + valor + " SI esta en el arbol.");
        }
        System.out.println(">> Nodos visitados: " + arbol.getComparaciones()
                + " de " + arbol.getCantidad() + " que tiene el arbol.");
    }

    private static void recorridos() {
        if (arbol.estaVacio()) {
            System.out.println(">> El arbol esta vacio.");
            return;
        }
        System.out.println("\n   LOS TRES RECORRIDOS");
        System.out.println("   ------------------------------------------------------");
        System.out.println("   PREORDEN  (raiz, izq, der): " + arbol.preorden());
        System.out.println("   INORDEN   (izq, raiz, der): " + arbol.inorden());
        System.out.println("   POSTORDEN (izq, der, raiz): " + arbol.postorden());
        System.out.println("   ------------------------------------------------------");
        System.out.println("   El inorden sale ordenado de menor a mayor: esa es la");
        System.out.println("   propiedad que define a un arbol binario de busqueda.");
    }

    private static void datos() {
        System.out.println("\n   DATOS DEL ARBOL");
        System.out.println("   ------------------------------------------------------");
        System.out.println("   Nodos:          " + arbol.getCantidad());
        System.out.println("   Altura:         " + arbol.altura()
                + "  (la ideal equilibrada seria " + arbol.alturaIdeal() + ")");
        System.out.println("   Hojas:          " + arbol.contarHojas());
        System.out.println("   Valor minimo:   " + arbol.getMinimo());
        System.out.println("   Valor maximo:   " + arbol.getMaximo());
        System.out.println("   Raiz:           " + (arbol.getRaiz() == null ? "-" : arbol.getRaiz().getDato()));
        System.out.println("   ------------------------------------------------------");
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
