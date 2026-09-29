package retofinal.algoritmos;

import java.util.List;

/**
 * Shell: como Insercion pero primero compara elementos separados por un
 * "salto" (gap) que se va reduciendo a la mitad, para mover los elementos
 * mal ubicados grandes distancias antes de hacer el ultimo paso con gap 1.
 *
 * Big O: mejor caso O(n log n), peor caso O(n^2) con esta secuencia de
 * saltos (n/2, n/4, ..., 1); con otras secuencias de gaps (Knuth, Hibbard)
 * el caso promedio mejora a aproximadamente O(n^1.3).
 *
 * @author friki
 */
public class ShellSort {

    public static void ordenarArreglo(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int actual = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > actual) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = actual;
            }
        }
    }

    public static void ordenarLista(List<Integer> lista) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int actual = lista.get(i);
                int j = i;
                while (j >= gap && lista.get(j - gap) > actual) {
                    lista.set(j, lista.get(j - gap));
                    j -= gap;
                }
                lista.set(j, actual);
            }
        }
    }
}

