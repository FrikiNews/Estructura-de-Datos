package retofinal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RetoTiempoLimite {
    public static void ejecutar(int cantidad, boolean rangoRestringido, int segundosLimite) {
        System.out.println();
        System.out.println("=".repeat(72));
        System.out.println("RETO OPCIONAL: colecciones ordenadas en " + segundosLimite + " segundos");
        System.out.println("Elementos por colección: " + cantidad + (rangoRestringido ? "  (valores 1-5)" : ""));
        System.out.println("=".repeat(72));

        ConcurrentHashMap<String, ContadorReto> resultados = new ConcurrentHashMap<>();
        List<Thread> hilos = new ArrayList<>();

        for (Algoritmo algoritmo : Algoritmo.todos()) {
            hilos.add(new Thread(
                    () -> contarConArreglo(algoritmo, cantidad, rangoRestringido, segundosLimite, resultados),
                    algoritmo.getNombre() + "-Reto-Arreglo"));
            hilos.add(new Thread(
                    () -> contarConLista(algoritmo, cantidad, rangoRestringido, segundosLimite, resultados),
                    algoritmo.getNombre() + "-Reto-ArrayList"));
        }

        System.out.println("\nCorriendo " + hilos.size() + " hilos durante " + segundosLimite + " segundos...");
        for (Thread hilo : hilos) {
            hilo.start();
        }
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(">> El hilo principal fue interrumpido esperando a " + hilo.getName());
            }
        }

        List<ContadorReto> lista = new ArrayList<>(resultados.values());
        lista.sort(Comparator.comparingInt(ContadorReto::getColeccionesCompletadas).reversed());

        System.out.println();
        System.out.printf(Locale.ROOT, "%-16s %-12s %-14s %-18s%n",
                "Algoritmo", "Estructura", "Colecciones", "Tiempo prom. (ms)");
        System.out.println("-".repeat(64));
        for (ContadorReto c : lista) {
            System.out.printf(Locale.ROOT, "%-16s %-12s %-14d %-18.4f%n",
                    c.getAlgoritmo(), c.getEstructura(), c.getColeccionesCompletadas(),
                    c.getTiempoPromedioMs());
        }
        System.out.println("-".repeat(64));
        System.out.println("Nota: si el tiempo se acaba a media ordenada, esa colección se deja");
        System.out.println("terminar, así que con n grande los métodos lentos pueden pasarse del");
        System.out.println("límite y aun así reportar 1 colección.");
    }

    private static void contarConArreglo(Algoritmo algoritmo, int cantidad, boolean rangoRestringido,
            int segundosLimite, ConcurrentHashMap<String, ContadorReto> resultados) {
        long limiteNanos = segundosLimite * 1_000_000_000L;
        long inicioVentana = System.nanoTime();
        Random random = new Random();
        int completadas = 0;
        long sumaTiempoNanos = 0;

        while (System.nanoTime() - inicioVentana < limiteNanos) {
            int[] datos = generar(cantidad, rangoRestringido, random);
            long inicio = System.nanoTime();
            algoritmo.getOrdenadorArreglo().ordenar(datos);
            sumaTiempoNanos += System.nanoTime() - inicio;
            completadas++;
        }

        guardar(resultados, algoritmo.getNombre(), "Arreglo", completadas, sumaTiempoNanos);
    }

    private static void contarConLista(Algoritmo algoritmo, int cantidad, boolean rangoRestringido,
            int segundosLimite, ConcurrentHashMap<String, ContadorReto> resultados) {
        long limiteNanos = segundosLimite * 1_000_000_000L;
        long inicioVentana = System.nanoTime();
        Random random = new Random();
        int completadas = 0;
        long sumaTiempoNanos = 0;

        while (System.nanoTime() - inicioVentana < limiteNanos) {
            int[] datosBase = generar(cantidad, rangoRestringido, random);
            List<Integer> datos = new ArrayList<>(cantidad);
            for (int valor : datosBase) {
                datos.add(valor);
            }
            long inicio = System.nanoTime();
            algoritmo.getOrdenadorLista().ordenar(datos);
            sumaTiempoNanos += System.nanoTime() - inicio;
            completadas++;
        }

        guardar(resultados, algoritmo.getNombre(), "ArrayList", completadas, sumaTiempoNanos);
    }

    private static int[] generar(int cantidad, boolean rangoRestringido, Random random) {
        return rangoRestringido
                ? GeneradorDatos.generarAleatorios(cantidad, 1, 5, random)
                : GeneradorDatos.generarAleatorios(cantidad, 0, 1_000_000, random);
    }

    private static void guardar(ConcurrentHashMap<String, ContadorReto> resultados, String algoritmo,
            String estructura, int completadas, long sumaTiempoNanos) {
        double promedioMs = completadas > 0 ? (sumaTiempoNanos / 1_000_000.0) / completadas : 0.0;
        resultados.put(algoritmo + "_" + estructura,
                new ContadorReto(algoritmo, estructura, completadas, promedioMs));
    }
}
