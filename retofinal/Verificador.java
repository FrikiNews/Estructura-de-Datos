package retofinal;

import java.util.List;

/**
 * Comprueba que una coleccion quedo bien ordenada. En lugar de solo revisar
 * que este de menor a mayor, se compara contra una referencia: los mismos
 * datos originales ya ordenados (con Arrays.sort en Main).
 *
 * La diferencia importa: un ordenamiento con un error podria dejar la
 * coleccion "de menor a mayor" pero habiendo perdido o duplicado elementos
 * en el camino, y esa revision simple lo dejaria pasar. Comparar contra la
 * referencia confirma las dos cosas: el orden y que sigan siendo los mismos
 * valores.
 *
 * @author friki
 */
public class Verificador {

    public static boolean arregloCoincide(int[] arreglo, int[] esperado) {
        if (arreglo.length != esperado.length) {
            return false;
        }
        for (int i = 0; i < esperado.length; i++) {
            if (arreglo[i] != esperado[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean listaCoincide(List<Integer> lista, int[] esperado) {
        if (lista.size() != esperado.length) {
            return false;
        }
        for (int i = 0; i < esperado.length; i++) {
            if (lista.get(i) != esperado[i]) {
                return false;
            }
        }
        return true;
    }
}
