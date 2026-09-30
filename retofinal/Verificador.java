package retofinal;

import java.util.List;

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
