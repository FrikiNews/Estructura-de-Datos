package retofinal;

import java.util.Random;

public class GeneradorDatos {
    public static int[] generarAleatorios(int cantidad, int minimo, int maximo) {
        return generarAleatorios(cantidad, minimo, maximo, new Random());
    }

    public static int[] generarAleatorios(int cantidad, int minimo, int maximo, Random random) {
        int[] datos = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            datos[i] = minimo + random.nextInt(maximo - minimo + 1);
        }
        return datos;
    }
}
