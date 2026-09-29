package retofinal.algoritmos;

import java.util.List;

/**
 * Insercion: toma cada elemento y lo va corriendo hacia atras hasta su
 * lugar dentro de la parte ya ordenada.
 *
 * Big O: mejor caso O(n) (ya ordenado, el while nunca entra), caso
 * promedio y peor caso O(n^2) (arreglo al reves).
 *
 * @author friki
 */
public class InsertionSort {

    public static void ordenarArreglo(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int actual = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > actual) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = actual;
        }
    }

    public static void ordenarLista(List<Integer> lista) {
        int n = lista.size();
        for (int i = 1; i < n; i++) {
            int actual = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j) > actual) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
    }
}
