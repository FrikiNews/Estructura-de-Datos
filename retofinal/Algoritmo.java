package retofinal;

import retofinal.algoritmos.BubbleSort;
import retofinal.algoritmos.InsertionSort;
import retofinal.algoritmos.MergeSort;
import retofinal.algoritmos.QuickSort;
import retofinal.algoritmos.SelectionSort;
import retofinal.algoritmos.ShellSort;

import java.util.List;

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
