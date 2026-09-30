package retofinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static final int TOTAL_IMPLEMENTACIONES = Algoritmo.todos().size() * 2;

    private static final int CANTIDAD_GRANDE = 200_000;

    private static final int VALOR_MAXIMO = 1_000_000;

    private static final Thread.UncaughtExceptionHandler MANEJADOR_ERRORES =
            (hilo, error) -> System.out.println(">> El hilo " + hilo.getName() + " falló: " + error);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ejecutarComparacion(sc);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n=============== MENÚ ===============");
            System.out.println("  1) Repetir la comparación de las 12 implementaciones");
            System.out.println("  2) Reto opcional: colecciones ordenadas en un tiempo límite");
            System.out.println("  0) Salir");
            System.out.println("------------------------------------");
            System.out.print("Selecciona una opción: ");

            switch (sc.nextLine().trim()) {
                case "1":
                    ejecutarComparacion(sc);
                    break;
                case "2":
                    ejecutarRetoOpcional(sc);
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println(">> Opción inválida. Elige 1, 2 o 0.");
            }
        }

        System.out.println("\nPrograma finalizado.");
        sc.close();
    }

    private static void ejecutarComparacion(Scanner sc) {
        int cantidad = pedirCantidad(sc);
        boolean rangoRestringido = leerRespuestaSiNo(sc,
                "¿Restringir los valores al rango 1-5 (para ver el efecto de muchos duplicados)? (s/n): ");

        int[] datosOriginales = generar(cantidad, rangoRestringido);

        int[] esperado = Arrays.copyOf(datosOriginales, datosOriginales.length);
        Arrays.sort(esperado);

        List<Integer> baseLista = new ArrayList<>(datosOriginales.length);
        for (int valor : datosOriginales) {
            baseLista.add(valor);
        }

        System.out.println();
        System.out.println("=".repeat(72));
        System.out.println("RESULTADOS DE ORDENAMIENTO");
        System.out.println("Elementos: " + cantidad + (rangoRestringido
                ? "  (valores restringidos a 1-5)"
                : "  (valores aleatorios entre 0 y " + VALOR_MAXIMO + ")"));
        System.out.println("=".repeat(72));

        ConcurrentHashMap<String, ResultadoOrden> resultados = new ConcurrentHashMap<>();
        List<Thread> hilos = crearHilos(datosOriginales, baseLista, esperado, resultados);
        double tiempoRonda = correrHilos(hilos);

        if (resultados.size() != TOTAL_IMPLEMENTACIONES) {
            System.out.println(">> Atención: se esperaban " + TOTAL_IMPLEMENTACIONES
                    + " resultados y llegaron " + resultados.size() + ".");
        }

        List<ResultadoOrden> ordenados = new ArrayList<>(resultados.values());
        ordenados.sort(Comparator.comparingDouble(ResultadoOrden::getTiempoMs));

        imprimirTabla(ordenados);
        imprimirResumen(ordenados, tiempoRonda);
        imprimirComparacionPorEstructura(ordenados);
        mostrarComplejidadBigO();
    }

    private static List<Thread> crearHilos(int[] datosOriginales, List<Integer> baseLista, int[] esperado,
            ConcurrentHashMap<String, ResultadoOrden> resultados) {
        List<Thread> hilos = new ArrayList<>();
        for (Algoritmo algoritmo : Algoritmo.todos()) {
            int[] copiaArreglo = Arrays.copyOf(datosOriginales, datosOriginales.length);
            List<Integer> copiaLista = new ArrayList<>(baseLista);

            hilos.add(nuevoHilo(new TareaOrdenamiento(algoritmo.getNombre(), copiaArreglo, esperado,
                    algoritmo.getOrdenadorArreglo(), resultados), algoritmo.getNombre() + "-Arreglo"));
            hilos.add(nuevoHilo(new TareaOrdenamiento(algoritmo.getNombre(), copiaLista, esperado,
                    algoritmo.getOrdenadorLista(), resultados), algoritmo.getNombre() + "-ArrayList"));
        }
        return hilos;
    }

    private static Thread nuevoHilo(Runnable tarea, String nombre) {
        Thread hilo = new Thread(tarea, nombre);
        hilo.setUncaughtExceptionHandler(MANEJADOR_ERRORES);
        return hilo;
    }

    private static double correrHilos(List<Thread> hilos) {
        System.out.println();
        System.out.println("Hilos creados: " + hilos.size()
                + " (uno por implementación, cada uno con su propia copia de los datos).");
        System.out.println("Iniciando los hilos con start()...");

        long inicioTotal = System.nanoTime();
        for (Thread hilo : hilos) {
            hilo.start();
        }

        System.out.println("Esperando con join() a que las " + hilos.size() + " implementaciones terminen...");
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(">> El hilo principal fue interrumpido esperando a " + hilo.getName());
            }
        }
        long finTotal = System.nanoTime();

        System.out.println("Los " + hilos.size() + " hilos terminaron.");
        return (finTotal - inicioTotal) / 1_000_000.0;
    }

    private static void imprimirTabla(List<ResultadoOrden> ordenados) {
        System.out.println();
        System.out.printf(Locale.ROOT, "%-5s %-16s %-12s %-14s %-10s%n",
                "Pos.", "Algoritmo", "Estructura", "Tiempo (ms)", "¿Ordenó?");
        System.out.println("-".repeat(62));
        int posicion = 1;
        for (ResultadoOrden r : ordenados) {
            System.out.printf(Locale.ROOT, "%-5d %-16s %-12s %-14.3f %-10s%n",
                    posicion++, r.getAlgoritmo(), r.getEstructura(), r.getTiempoMs(),
                    r.isOrdenadoCorrectamente() ? "Sí" : "NO (error)");
        }
    }

    private static void imprimirResumen(List<ResultadoOrden> ordenados, double tiempoRonda) {
        if (ordenados.isEmpty()) {
            return;
        }
        ResultadoOrden masRapido = ordenados.get(0);
        ResultadoOrden masLento = ordenados.get(ordenados.size() - 1);

        System.out.printf(Locale.ROOT,
                "%nTiempo total de la ronda (con los %d hilos corriendo al mismo tiempo): %.2f ms%n",
                ordenados.size(), tiempoRonda);
        System.out.printf(Locale.ROOT, "Implementación con menor tiempo registrado: %s (%s) -> %.3f ms%n",
                masRapido.getAlgoritmo(), masRapido.getEstructura(), masRapido.getTiempoMs());
        System.out.printf(Locale.ROOT, "Implementación con mayor tiempo registrado: %s (%s) -> %.3f ms%n",
                masLento.getAlgoritmo(), masLento.getEstructura(), masLento.getTiempoMs());
        if (masRapido.getTiempoMs() > 0) {
            System.out.printf(Locale.ROOT, "La más lenta tardó %.1f veces lo que la más rápida.%n",
                    masLento.getTiempoMs() / masRapido.getTiempoMs());
        }
        System.out.println("(El menor tiempo de esta ejecución no prueba que ese algoritmo sea");
        System.out.println(" siempre el más rápido: conviene repetir la prueba varias veces.)");
    }

    private static void imprimirComparacionPorEstructura(List<ResultadoOrden> resultados) {
        Map<String, ResultadoOrden> porClave = new HashMap<>();
        for (ResultadoOrden r : resultados) {
            porClave.put(r.getAlgoritmo() + "|" + r.getEstructura(), r);
        }

        System.out.println();
        System.out.println("EL MISMO ALGORITMO EN LAS DOS ESTRUCTURAS");
        System.out.println("-".repeat(72));
        System.out.printf(Locale.ROOT, "%-16s %-16s %-16s %-18s%n",
                "Algoritmo", "Arreglo (ms)", "ArrayList (ms)", "ArrayList/Arreglo");
        for (Algoritmo algoritmo : Algoritmo.todos()) {
            ResultadoOrden conArreglo = porClave.get(algoritmo.getNombre() + "|Arreglo");
            ResultadoOrden conLista = porClave.get(algoritmo.getNombre() + "|ArrayList");
            if (conArreglo == null || conLista == null) {
                continue;
            }
            String factor = conArreglo.getTiempoMs() > 0
                    ? String.format(Locale.ROOT, "%.2fx", conLista.getTiempoMs() / conArreglo.getTiempoMs())
                    : "-";
            System.out.printf(Locale.ROOT, "%-16s %-16.3f %-16.3f %-18s%n",
                    algoritmo.getNombre(), conArreglo.getTiempoMs(), conLista.getTiempoMs(), factor);
        }
        System.out.println("-".repeat(72));
        System.out.println("La última columna dice cuántas veces más tardó la versión con");
        System.out.println("ArrayList<Integer> que la del arreglo int[] del mismo algoritmo.");
    }

    private static void mostrarComplejidadBigO() {
        System.out.println();
        System.out.println("COMPLEJIDAD TEMPORAL TEÓRICA (Big O)");
        System.out.println("-".repeat(72));
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n",
                "Algoritmo", "Mejor caso", "Caso promedio", "Peor caso");
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n", "Bubble Sort", "O(n)", "O(n^2)", "O(n^2)");
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n", "Selection Sort", "O(n^2)", "O(n^2)", "O(n^2)");
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n", "Insertion Sort", "O(n)", "O(n^2)", "O(n^2)");
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n", "Shell Sort", "O(n log n)", "O(n^1.3) *", "O(n^2)");
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n", "Merge Sort", "O(n log n)", "O(n log n)", "O(n log n)");
        System.out.printf(Locale.ROOT, "%-16s %-14s %-16s %-12s%n", "Quick Sort", "O(n log n)", "O(n log n)", "O(n^2) **");
        System.out.println("-".repeat(72));
        System.out.println("*  Depende de la secuencia de saltos (gaps); con gaps tipo Knuth ronda O(n^1.3).");
        System.out.println("** Esta implementación usa pivote aleatorio y recursión acotada al lado más");
        System.out.println("   chico de la partición, lo que en la práctica hace muy difícil topar con");
        System.out.println("   el peor caso y además evita un StackOverflowError en colecciones grandes.");
        System.out.println("Nota: el Big O describe cómo crece el trabajo en teoría; no sale de convertir");
        System.out.println("directamente los milisegundos medidos arriba.");
    }

    private static void ejecutarRetoOpcional(Scanner sc) {
        System.out.println("\nEn este modo, cada implementación ordena tantas colecciones nuevas");
        System.out.println("como alcance dentro del tiempo límite que indiques.");

        int cantidad = pedirCantidad(sc);
        boolean rangoRestringido = leerRespuestaSiNo(sc,
                "¿Restringir los valores al rango 1-5? (s/n): ");
        System.out.print("Tiempo límite en segundos para cada implementación: ");
        int segundos = leerEnteroPositivo(sc);

        RetoTiempoLimite.ejecutar(cantidad, rangoRestringido, segundos);
    }

    private static int[] generar(int cantidad, boolean rangoRestringido) {
        return rangoRestringido
                ? GeneradorDatos.generarAleatorios(cantidad, 1, 5)
                : GeneradorDatos.generarAleatorios(cantidad, 0, VALOR_MAXIMO);
    }

    private static int pedirCantidad(Scanner sc) {
        while (true) {
            System.out.print("\n¿Cuántos elementos vas a ordenar? ");
            int cantidad = leerEnteroPositivo(sc);
            if (cantidad < CANTIDAD_GRANDE) {
                return cantidad;
            }
            System.out.println(">> Aviso: con " + cantidad + " elementos, los métodos O(n^2) (burbuja,");
            System.out.println("   selección e inserción) pueden tardar varios minutos, y se reservan");
            System.out.println("   " + TOTAL_IMPLEMENTACIONES + " copias de la colección en memoria.");
            if (leerRespuestaSiNo(sc, "   ¿Continuar de todas formas? (s/n): ")) {
                return cantidad;
            }
        }
    }

    private static int leerEnteroPositivo(Scanner sc) {
        while (true) {
            String linea = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor > 0) {
                    return valor;
                }
                System.out.print(">> Debe ser un número entero positivo. Intenta de nuevo: ");
            } catch (NumberFormatException e) {
                System.out.print(">> Entrada inválida. Escribe un número entero positivo: ");
            }
        }
    }

    private static boolean leerRespuestaSiNo(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim().toLowerCase(Locale.ROOT).startsWith("s");
    }
}
