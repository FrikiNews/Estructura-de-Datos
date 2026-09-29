package retofinal.algoritmos;

import java.util.Collections;
import java.util.List;

/**
 * Burbuja: compara pares vecinos y los intercambia si estan en desorden.
 * Con bandera de "hubo intercambio" para cortar antes si ya quedo ordenado.
 *
 * Big O: mejor caso O(n) (arreglo ya ordenado, gracias a la bandera),
 * caso promedio y peor caso O(n^2).
 *
 * @author friki
 */
public class BubbleSort {

    public static void ordenarArreglo(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    huboIntercambio = true;
                }
            }
            if (!huboIntercambio) {
                break;
            }
        }
    }

    public static void ordenarLista(List<Integer> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (lista.get(j) > lista.get(j + 1)) {
                    Collections.swap(lista, j, j + 1);
                    huboIntercambio = true;
                }
            }
            if (!huboIntercambio) {
                break;
            }
        }
    }
}
