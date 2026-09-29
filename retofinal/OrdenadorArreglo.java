package retofinal;

/**
 * Contrato comun para un metodo de ordenamiento que trabaja sobre un
 * arreglo de int. Permite pasar cada algoritmo como referencia a metodo
 * (por ejemplo BubbleSort::ordenarArreglo) al armar las 12 tareas.
 *
 * @author friki
 */
public interface OrdenadorArreglo {
    void ordenar(int[] arreglo);
}
