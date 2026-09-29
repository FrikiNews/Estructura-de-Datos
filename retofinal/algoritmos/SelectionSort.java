package retofinal.algoritmos;

import java.util.Collections;
import java.util.List;

/**
 * Seleccion: en cada vuelta busca el minimo del resto y lo manda al frente.
 * Siempre recorre todo lo que queda, sin importar si ya esta ordenado, por
 * eso no tiene mejor caso distinto.
 *
 * Big O: O(n^2) en mejor, promedio y peor caso.
 *
 * @author friki
 */
public class SelectionSort {

    public static void ordenarArreglo(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }
            if (indiceMinimo != i) {
                int temp = arr[i];
                arr[i] = arr[indiceMinimo];
                arr[indiceMinimo] = temp;
            }
        }
    }

    public static void ordenarLista(List<Integer> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                if (lista.get(j) < lista.get(indiceMinimo)) {
                    indiceMinimo = j;
                }
            }
            if (indiceMinimo != i) {
                Collections.swap(lista, i, indiceMinimo);
            }
        }
    }
}
