package retofinal;

import retofinal.algoritmos.BubbleSort;
import retofinal.algoritmos.InsertionSort;
import retofinal.algoritmos.MergeSort;
import retofinal.algoritmos.QuickSort;
import retofinal.algoritmos.SelectionSort;
import retofinal.algoritmos.ShellSort;

import java.util.List;

/**
 * Catalogo de los seis metodos de ordenamiento. Cada Algoritmo junta su
 * nombre con sus dos versiones (la que trabaja sobre int[] y la que trabaja
 * sobre ArrayList&lt;Integer&gt;), de ahi salen las 12 implementaciones.
 *
 * Tener la lista en un solo lugar evita repetir los seis registros en Main
 * y en RetoTiempoLimite: es lo unico que habria que tocar para agregar un
 * septimo metodo de ordenamiento.
 *
 * @author friki
 */
public class Algoritmo {

    private static final List<Algoritmo> TODOS = List.of(
            new Algoritmo("Bubble Sort", BubbleSort::ordenarArreglo, BubbleSort::ordenarLista),
            new Algoritmo("Selection Sort", SelectionSort::ordenarArreglo, SelectionSort::ordenarLista),
            new Algoritmo("Insertion Sort", InsertionSort::ordenarArreglo, InsertionSort::ordenarLista),
            new Algoritmo("Shell Sort", ShellSort::ordenarArreglo, ShellSort::ordenarLista),
            new Algoritmo("Merge Sort", MergeSort::ordenarArreglo, MergeSort::ordenarLista),
            new Algoritmo("Quick Sort", QuickSort::ordenarArreglo, QuickSort::ordenarLista));

    private final String nombre;
    private final OrdenadorArreglo ordenadorArreglo;
    private final OrdenadorLista ordenadorLista;

    private Algoritmo(String nombre, OrdenadorArreglo ordenadorArreglo, OrdenadorLista ordenadorLista) {
        this.nombre = nombre;
        this.ordenadorArreglo = ordenadorArreglo;
        this.ordenadorLista = ordenadorLista;
    }

    /** Los seis metodos de ordenamiento, en el orden en que se presentan. */
    public static List<Algoritmo> todos() {
        return TODOS;
    }

    public String getNombre() {
        return nombre;
    }

    public OrdenadorArreglo getOrdenadorArreglo() {
        return ordenadorArreglo;
    }

    public OrdenadorLista getOrdenadorLista() {
        return ordenadorLista;
    }
}
