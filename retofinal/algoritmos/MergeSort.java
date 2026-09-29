package retofinal.algoritmos;

import java.util.ArrayList;
import java.util.List;

/**
 * Mezcla (Merge Sort): divide la coleccion a la mitad recursivamente hasta
 * quedarse con pedazos de tamano 1 y luego los va mezclando ya ordenados.
 * Usa una coleccion auxiliar del mismo tamano para poder mezclar sin
 * pisar los datos que todavia no se han copiado.
 *
 * Big O: O(n log n) en mejor, promedio y peor caso (siempre divide a la
 * mitad sin importar como vengan acomodados los datos). El costo es la
 * memoria extra O(n) del arreglo/lista auxiliar.
 *
 * @author friki
 */
public class MergeSort {

    public static void ordenarArreglo(int[] arr) {
        if (arr.length > 1) {
            int[] auxiliar = new int[arr.length];
            mergeSort(arr, auxiliar, 0, arr.length - 1);
        }
    }

    private static void mergeSort(int[] arr, int[] auxiliar, int inicio, int fin) {
        if (inicio < fin) {
            int medio = (inicio + fin) / 2;
            mergeSort(arr, auxiliar, inicio, medio);
            mergeSort(arr, auxiliar, medio + 1, fin);
            mezclar(arr, auxiliar, inicio, medio, fin);
        }
    }

    private static void mezclar(int[] arr, int[] auxiliar, int inicio, int medio, int fin) {
        for (int k = inicio; k <= fin; k++) {
            auxiliar[k] = arr[k];
        }
        int i = inicio;
        int j = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (i > medio) {
                arr[k] = auxiliar[j++];
            } else if (j > fin) {
                arr[k] = auxiliar[i++];
            } else if (auxiliar[i] <= auxiliar[j]) {
                arr[k] = auxiliar[i++];
            } else {
                arr[k] = auxiliar[j++];
            }
        }
    }

    public static void ordenarLista(List<Integer> lista) {
        if (lista.size() > 1) {
            List<Integer> auxiliar = new ArrayList<>(lista);
            mergeSort(lista, auxiliar, 0, lista.size() - 1);
        }
    }

    private static void mergeSort(List<Integer> lista, List<Integer> auxiliar, int inicio, int fin) {
        if (inicio < fin) {
            int medio = (inicio + fin) / 2;
            mergeSort(lista, auxiliar, inicio, medio);
            mergeSort(lista, auxiliar, medio + 1, fin);
            mezclar(lista, auxiliar, inicio, medio, fin);
        }
    }

    private static void mezclar(List<Integer> lista, List<Integer> auxiliar, int inicio, int medio, int fin) {
        for (int k = inicio; k <= fin; k++) {
            auxiliar.set(k, lista.get(k));
        }
        int i = inicio;
        int j = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (i > medio) {
                lista.set(k, auxiliar.get(j++));
            } else if (j > fin) {
                lista.set(k, auxiliar.get(i++));
            } else if (auxiliar.get(i) <= auxiliar.get(j)) {
                lista.set(k, auxiliar.get(i++));
            } else {
                lista.set(k, auxiliar.get(j++));
            }
        }
    }
}
