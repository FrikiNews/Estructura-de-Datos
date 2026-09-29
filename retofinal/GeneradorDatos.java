package retofinal;

import java.util.Random;

/**
 * Genera los datos de prueba con Random. Se usa un solo arreglo "base" por
 * ejecucion y luego cada una de las 12 implementaciones recibe su propia
 * copia independiente (ver Main.crearHilos), para que ningun hilo modifique
 * una coleccion que otro hilo tambien este usando.
 *
 * @author friki
 */
public class GeneradorDatos {

    public static int[] generarAleatorios(int cantidad, int minimo, int maximo) {
        return generarAleatorios(cantidad, minimo, maximo, new Random());
    }

    /**
     * Version que recibe el Random ya creado. La usa el reto opcional, donde
     * cada hilo genera miles de colecciones en un ciclo: creando un solo
     * Random por hilo se evita que los 12 hilos esten pidiendo semillas
     * nuevas todo el tiempo y se estorben entre ellos.
     */
    public static int[] generarAleatorios(int cantidad, int minimo, int maximo, Random random) {
        int[] datos = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            datos[i] = minimo + random.nextInt(maximo - minimo + 1);
        }
        return datos;
    }
}
