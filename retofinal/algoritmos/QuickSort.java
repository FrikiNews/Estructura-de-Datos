package retofinal.algoritmos;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Rapido (Quick Sort): elige un pivote, acomoda menores a la izquierda y
 * mayores a la derecha (particion de Lomuto) y repite en cada mitad.
 *
 * Dos detalles importantes de esta implementacion, pensados para la prueba
 * con 100,000 elementos restringidos a valores entre 1 y 5 (muchos
 * duplicados):
 *   1) El pivote se elige al azar (ThreadLocalRandom, seguro entre hilos)
 *      en vez de usar siempre el ultimo elemento, para no depender del
 *      orden en que ya vengan los datos.
 *   2) Solo se hace la llamada recursiva sobre el lado mas chico de la
 *      particion; el lado grande se seguye resolviendo en el mismo ciclo
 *      while. Esto acota la profundidad de la pila a O(log n) incluso en
 *      el peor caso, evitando un StackOverflowError con colecciones
 *      grandes o con muchos valores repetidos.
 *
 * Big O: mejor caso y caso promedio O(n log n); peor caso teorico O(n^2)
 * si el pivote siempre cae en el extremo (mucho menos probable aqui por
 * ser aleatorio).
 *
 * @author friki
 */
public class QuickSort {

    public static void ordenarArreglo(int[] arr) {
        if (arr.length > 1) {
            quickSort(arr, 0, arr.length - 1);
        }
    }

    private static void quickSort(int[] arr, int inicio, int fin) {
        while (inicio < fin) {
            int pivote = particionar(arr, inicio, fin);
            if (pivote - inicio < fin - pivote) {
                quickSort(arr, inicio, pivote - 1);
                inicio = pivote + 1;
            } else {
                quickSort(arr, pivote + 1, fin);
                fin = pivote - 1;
            }
        }
    }

    private static int particionar(int[] arr, int inicio, int fin) {
        int indiceAleatorio = inicio + ThreadLocalRandom.current().nextInt(fin - inicio + 1);
        intercambiar(arr, indiceAleatorio, fin);
        int pivote = arr[fin];
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (arr[j] <= pivote) {
                i++;
                intercambiar(arr, i, j);
            }
        }
        intercambiar(arr, i + 1, fin);
        return i + 1;
    }

    private static void intercambiar(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void ordenarLista(List<Integer> lista) {
        if (lista.size() > 1) {
            quickSort(lista, 0, lista.size() - 1);
        }
    }

    private static void quickSort(List<Integer> lista, int inicio, int fin) {
        while (inicio < fin) {
            int pivote = particionar(lista, inicio, fin);
            if (pivote - inicio < fin - pivote) {
                quickSort(lista, inicio, pivote - 1);
                inicio = pivote + 1;
            } else {
                quickSort(lista, pivote + 1, fin);
                fin = pivote - 1;
            }
        }
    }

    private static int particionar(List<Integer> lista, int inicio, int fin) {
        int indiceAleatorio = inicio + ThreadLocalRandom.current().nextInt(fin - inicio + 1);
        Collections.swap(lista, indiceAleatorio, fin);
        int pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (lista.get(j) <= pivote) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, fin);
        return i + 1;
    }
}
